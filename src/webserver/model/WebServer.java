package webserver.model;

import common.Massage;
import common.MassageType;
import common.User;
import common.UserLogup;
import webserver.view.ServerBuildFrame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author 郑志伟
 * @create 2020-05-25 10:15
 * 服务器端
 */
public class WebServer {
    Object o;
    ServerBuildFrame serverBuildFrame;
    AloneThreadConnClient thread;
    public WebServer(ServerBuildFrame serverBuildFrame) {
            this.serverBuildFrame = serverBuildFrame;

        try {
            //实例化ServerSocket并创建端口
            ServerSocket serverSocket = new ServerSocket(8787);
            //将服务器信息显示到服务器面板
            serverBuildFrame.txtIP.setText("127.0.0.1");
            serverBuildFrame.txtServerName.setText("WebChat");
            serverBuildFrame.txtPort.setText(String.valueOf(serverSocket.getLocalPort()));
            while (true){
            System.out.println("服务器监听中···········");
                //获取socket
                Socket s = serverSocket.accept();
                //读取输入流对象
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                 o = ois.readObject();
                System.out.println("服务器接收到object");
                if (o instanceof User){
                User u = (User) o;
                Massage ma = new Massage();
                String username = u.getName();
                String userpwd =u.getPassword();
                if (new ChackCorrect(username,userpwd).ifright()) {
                    if (!ThreadInToHashMap.hashMap.containsKey(username)&&u.getMassage().getMassageType().equals(MassageType.MASSAGE_COMM)) {
                        ma.setMassageType(MassageType.MASSAGE_COMM);
                        //登陆成功，创建一个线程单独与此客户端保持联系
                         thread = new AloneThreadConnClient(s);
                        //将线程放入hashmap
                        ThreadInToHashMap.addClientThread(u.getName(), thread);
                        serverBuildFrame.txtNumber.setText(ThreadInToHashMap.hashMap.size() + "人");
                        serverBuildFrame.taLog.append(username + "已上线" + "  [" + u.getMassage().getDate() + "]" + "\r\n");

                        thread.start();

                    }else{
                        //设置登陆失败的信息
                        ma.setMassageType(MassageType.MASSAGE_LOGIN_FAIL);
                        ma.setOwner("server");
                        ma.setFriend(username);
                        ma.setMassage(MassageType.MASSAGE_LOGIN_ISFAIL);
                        ma.setDate(new Date());
                        serverBuildFrame.taLog.append("用户'"+u.getName()+"'注册失败！  ["+ma.getDate()+"]。");

                    }
                } else {
                    //设置登陆失败的信息
                    ma.setMassageType(MassageType.MASSAGE_LOGIN_FAIL);
                    ma.setOwner("server");
                    ma.setFriend(username);
                    ma.setMassage(MassageType.MASSAGE_LOGIN_ISFAIL);
                    ma.setDate(new Date());
                    serverBuildFrame.taLog.append("用户'"+u.getName()+"'注册失败！  ["+ma.getDate()+"]。");

                }
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(ma);
            }
           /**
             *判断客户端的行为
             *@return
             */

            if (o instanceof UserLogup){
                UserLogup u = (UserLogup)o;
                Massage ma = new Massage();
                //如果读取到的对象是UserLogup，对UserLogup的信息在数据库进行注册
                if ( new ToMysqlLogup().toMysqlLogup(u)){

                    //注册成功后，回送一条信息给客户端
                    ma.setMassageType(MassageType.MASSAGE_LOGIN_SUCCESS);
                    Massage massage = new Massage();
                    massage.setDate(new Date());
                    //将注册消息打印到服务器日志
                    serverBuildFrame.taLog.append("用户'"+u.getName()+"'注册成功！  ["+massage.getDate()+"]。");
                }else{
                    ma.setMassageType(MassageType.MASSAGE_LOGIN_ISFAIL);

                }
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(ma);
            }
        }
     } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new WebServer(new ServerBuildFrame());
    }
}
