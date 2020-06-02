package common;

import java.io.Serializable;

/**
 * @author 郑志伟
 * @create 2020-05-25 8:12
 * 存放用户注册信息
 */
public class UserLogup implements Serializable {
    private static final long serialVersionUID = -7374548021862742674L;
    String count,name = null;
    String Password1,Password2 = null;


    public UserLogup(String count, String name, String password1, String password2) {
        this.count = count;
        this.name = name;
        Password1 = password1;
        Password2 = password2;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword1() {
        return Password1;
    }

    public void setPassword1(String password1) {
        Password1 = password1;
    }

    public String getPassword2() {
        return Password2;
    }

    public void setPassword2(String password2) {
        Password2 = password2;
    }
}
