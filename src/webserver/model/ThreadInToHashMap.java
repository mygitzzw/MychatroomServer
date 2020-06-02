package webserver.model;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 郑志伟
 * @create 2020-05-26 14:38
 * 存放所有与客户端连接的线程
 */
public class ThreadInToHashMap {

    public static HashMap<String,AloneThreadConnClient> hashMap = new HashMap<>();

    /**
     *向hashmap中加入线程
     *@return
     */
    public static void addClientThread(String name,AloneThreadConnClient thread){
        hashMap.put(name, thread);
    }

    public static AloneThreadConnClient getClientThreas (String name){
        return hashMap.get(name);
    }

    //返回在线好友
    public static String getOlineFriend(){
        String friendList = "";
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            friendList += next + "@";
        }
        return friendList;
    }
}
