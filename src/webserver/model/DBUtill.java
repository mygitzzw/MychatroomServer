package webserver.model;
import java.sql.*;

/**
 * @author 郑志伟
 * @create 2020-05-21 15:15
 * 自定义JDBC工具类，简化JDBC编程
 */
public class DBUtill {

    //静态代码块注册驱动
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    工具类的构造方法都是私有的
    因为类中的方法都是静态的
     */
    private DBUtill(){}

    /**
     *获取数据库连接对象
     *@return 连接对象
     */
     
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "aw1591151176");
    }
    
    /**
     *释放资源，适用于DQL语句
     *@return
     */
    public static void close(Connection conn, Statement sta, ResultSet result){
        if ( result!= null) {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if ( sta!= null) {
            try {
                sta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *close方法重载，适用于DML语句
     *@return
     */

    public static void close(Connection conn, Statement sta){
        if ( sta!= null) {
            try {
                sta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
