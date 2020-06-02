package webserver.model;

import common.Massage;
import common.MassageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 郑志伟
 * @create 2020-05-27 15:56
 * 没有使用此类
 */
public class NotifyClient {
    String name;

    public NotifyClient(String name) {
        this.name = name;
    }

    public void notifyOther(){
        HashMap<String, AloneThreadConnClient> hashMap = ThreadInToHashMap.hashMap;
        Iterator it = hashMap.keySet().iterator();
        Massage mm = new Massage();
        mm.setOwner(name);
        mm.setMassageType(MassageType.MASSAGE_FRIEND);
        while (it.hasNext()){
            String  next = it.next().toString();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ThreadInToHashMap
                        .getClientThreas(next).s.getOutputStream());
                mm.setFriend(next);
                oos.writeObject(mm);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
