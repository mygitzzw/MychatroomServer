package common;

/**
 * @author 郑志伟
 * @create 2020-05-27 8:01
 * 定义包的种类
 */
public interface MassageType {
    String MASSAGE_COMM = "1";  //登录成功
    String MASSAGE_LOGIN_FAIL = "2";  //登录失败
    String MASSAGE_COMM_AVG = "3";   //普通信息包
    String MASSAGE_ON_LINE = "4";     //要求得到好友在线
    String MASSAGE_FRIEND = "5" ;     //返回在线好友
    String MASSAGE_LOGIN_SUCCESS = "6";   //注册成功
    String MASSAGE_LOGIN_ISFAIL = "7";      //注册失败
}
