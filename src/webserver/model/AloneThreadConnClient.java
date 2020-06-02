package webserver.model;

import common.Massage;
import common.MassageType;
import webserver.view.ServerBuildFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 郑志伟
 * @create 2020-05-26 11:31
 * 客户端与服务器启用的线程
 */
public class AloneThreadConnClient extends Thread{
    public Socket s ;
    public  String friend = "";

    public  String getFriend() {
        return this.friend;
    }

    public AloneThreadConnClient(Socket s) {
        this.s = s;

    }

    @Override
    public void run() {
        //notifyOther();
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

                Massage ma = (Massage) ois.readObject();
                friend = ma.getFriend();
                //取得接收人的通讯线程
                System.out.println("我是："+ma.getOwner()+":聊天对象："+friend);
                //判断用户的聊天对象和消息类型
                if ("多人聊天室".equals(ma.getFriend())&&ma.getMassageType().equals(MassageType.MASSAGE_COMM_AVG)){

                    /**
                     *通过迭代器迭代在线用户获取用户账号并转发用户发送的消息
                     *@return
                     */
                    Iterator<String> iterator = ThreadInToHashMap.hashMap.keySet().iterator();
                    String speak = "";
                    while (iterator.hasNext()){
                        //获取用户的账号
                        String sn = iterator.next().toString();
                        //获取对应的用户通讯线程
                        AloneThreadConnClient clientThreas = ThreadInToHashMap.getClientThreas(sn);
                        //获取对应用户的聊天对象
                        String fq = clientThreas.getFriend();
                        //System.out.println(sn+"_在线上，在和_"+fq+"聊天______________");
                        if ((!(sn.equals(ma.getOwner())))&&("多人聊天室".equals(fq))){

                            //如果用户在多人聊天室，则将消息转发给此用户
                        AloneThreadConnClient thread = ThreadInToHashMap.getClientThreas(sn);
                        ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                        ma.setMassageType(MassageType.MASSAGE_COMM_AVG);
                        oos.writeObject(ma);
                        }
                         speak = "多人聊天室:"+ma.getMassage()+"  ["+ma.getDate()+"]";
                    }
                    ServerBuildFrame.serverJframe.taLog.append(speak+"\r\n");
                }else
                    //判断消息类型为普通消息
                if (ma.getMassageType().equals(MassageType.MASSAGE_COMM_AVG)){

                    String mass = ma.getOwner() + "正在对" + ma.getFriend() + "说" + ma.getMassage()+"  ["+ma.getDate()+"]";

                    //消息打印到服务器面板上
                    ServerBuildFrame.serverJframe.taLog.append("用户:"+mass+"\r\n");

                    if (ma.getMassageType().equals(MassageType.MASSAGE_COMM_AVG)&&
                            "我上线啦!!!".equals(ma.getMassage())){
                        if (ThreadInToHashMap.hashMap.containsKey(ma.getFriend())) {
                            AloneThreadConnClient atc = ThreadInToHashMap.getClientThreas(ma.getFriend());
                            ObjectOutputStream ooss = new ObjectOutputStream(atc.s.getOutputStream());
                            ma.setFriend("你");
                            ooss.writeObject(ma);
                        }
                    }else{
                    //判断消息的对象是否在线，在线则发送不在则提示
                if (ThreadInToHashMap.hashMap.containsKey(ma.getFriend())) {
                    AloneThreadConnClient atc = ThreadInToHashMap.getClientThreas(ma.getFriend());
                    ObjectOutputStream ooss = new ObjectOutputStream(atc.s.getOutputStream());

                    ooss.writeObject(ma);
                }else{
                    ObjectOutputStream oosss = new ObjectOutputStream(s.getOutputStream());
                    Massage mm = new Massage();
                    mm.setOwner("系统消息");
                    mm.setMassage("对方暂时不在线。");
                    mm.setDate(new Date());
                    mm.setMassageType(MassageType.MASSAGE_COMM_AVG);
                    oosss.writeObject(mm);
                }}}else if (ma.getMassageType().equals(MassageType.MASSAGE_ON_LINE)){

                    SelectMysqlfriends smf = new SelectMysqlfriends();
                    String friends = smf.getfriends();
//                    //判断消息类型为请求好友在线列表
                    String oo = ThreadInToHashMap.getOlineFriend();
                    //获取当前的好友在线列表
                    System.out.println("在线："+oo);
                    Massage massage = new Massage();
                    massage.setDate(new Date());
                    massage.setOwner("server");
                    massage.setMassageType(MassageType.MASSAGE_FRIEND);
                    if ("null".equals(oo)){
                        oo = "@";
                    }
                    //为所有在线用户回送消息
                    massage.setMassage(oo);
                    massage.setFriend(friends);
                    Iterator<String> iterator = ThreadInToHashMap.hashMap.keySet().iterator();
                    while (iterator.hasNext()){
                        String sd = iterator.next().toString();
                        AloneThreadConnClient thread = ThreadInToHashMap.getClientThreas(sd);
                        ObjectOutputStream oos = new ObjectOutputStream(thread.s.getOutputStream());
                        oos.writeObject(massage);

                        }
                    }
            } catch (IOException e) {
                try {
                    s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                try {
                    s.close();
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
