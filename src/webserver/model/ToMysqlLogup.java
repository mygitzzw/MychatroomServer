package webserver.model;

import common.UserLogup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 郑志伟
 * @create 2020-05-25 22:31
 * 通过MySQL数据库对用户信息进行注册
 */
public class ToMysqlLogup {

    private boolean chack = false;

    public boolean toMysqlLogup(UserLogup u){


        Connection conn;
        PreparedStatement ps ;

        try {
            //获取连接
            conn = DBUtill.getConnection();
            String sql = "insert into t_user (loginName,loginPwd,realName) values (?,?,?)";
            //获取预编译的数据库操作对象
            ps = conn.prepareStatement(sql);
            //对占位符赋值
            ps.setString(1,u.getCount());
            ps.setString(2,u.getPassword1());
            ps.setString(3 ,u.getPassword2());

            //对SQL语句进行处理
            int i = ps.executeUpdate();
            if (i==1){
                chack = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chack;
    }
}
