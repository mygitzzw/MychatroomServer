package webserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 郑志伟
 * @create 2020-05-29 9:52
 */
public class SelectMysqlfriends {
    String friends = "";
    StringBuilder f = new StringBuilder();

    public String getfriends() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        try {
            conn = DBUtill.getConnection();

            String sql = "select loginName from t_user";

            ps = conn.prepareStatement(sql);

            res = ps.executeQuery();

            while (res.next()) {
                f.append("&" + res.getString("loginName"));
            }
            friends = f.toString();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static void main(String[] args) {
        SelectMysqlfriends smf = new SelectMysqlfriends();
        String d =  smf.getfriends();
        String[] split = d.split("&");
        for (int i = 0; i < split.length; i++) {
            String substring = split[i].substring(0);
            if (substring.contains("&")) {
                split[i] = substring;
            }
        }
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}

