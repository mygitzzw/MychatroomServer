package common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 郑志伟
 * @create 2020-05-25 7:38
 * 用于传输的信息类
 */
public class Massage implements Serializable {
    private static final long serialVersionUID = 8475898535881670782L;
    private String massageType ;    //信息类型
    private String massage;         //信息
    private String owner;           //发送者
    private String friend;          //接收者
    private String date;            //当前时间


    public String getMassageType() {
        return massageType;
    }

    public void setMassageType(String massageType) {
        this.massageType = massageType;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getDate(){
      return date;
    }
    public void setDate(Date d){
        //对时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
         date = sdf.format(d);
    }
}
