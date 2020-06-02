package common;

import java.io.Serializable;

/**
 * @author 郑志伟
 * @create 2020-05-25 7:31
 * 用户类，存放用户登陆信息
 */
public class User implements Serializable {

    private static final long serialVersionUID = -996420290072824355L;
    String name ;
    String password;

    public Massage getMassage() {
        return massage;
    }

    public void setMassage(Massage massage) {
        this.massage = massage;
    }

    private Massage massage = new Massage();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
