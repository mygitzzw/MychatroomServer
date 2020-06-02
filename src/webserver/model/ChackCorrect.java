package webserver.model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 郑志伟
 * @create 2020-05-25 15:05
 * 使用JDBC接口在MYSQL数据库验证用户是否已经注册
 */
public class ChackCorrect {
    String name;
    String pwd;

    public ChackCorrect(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public boolean ifright() {
        boolean chack = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        try {
            //获取连接
            conn = DBUtill.getConnection();
            //获取预编译的数据库操作对象
            //String sql = "insert into t_user (loginName,loginPwd,realName) values (?,?,?)?";
            String sql = "select realName from t_user where loginName = ? and loginPwd = ?";
            ps = conn.prepareStatement(sql);
            //对占位符赋值
            ps.setString(1, name);
            ps.setString(2, pwd);

            //执行SQL语句
            res = ps.executeQuery();
            boolean aas = res.next();


            if (aas) {
                //满足条件，则生成一个新的界面
                /*javax.swing.JFrame jff = new javax.swing.JFrame();
                jff.setTitle("聊天界面");
                jff.setSize(400, 600);//只对顶级容器有效
                jff.setDefaultCloseOperation(3);//窗体关闭时结束程序
                jff.setLocationRelativeTo(null);//居中
                jff.setResizable(false);  //不可改变窗体大小
                jff.setVisible(true);  //设置可见*/
                chack = true;

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            //关闭资源
            DBUtill.close(conn, ps, res);
        }
        return chack;
    }
}
