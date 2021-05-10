package Java.Dao;

import Java.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {
    //neu user co tai khoan trong database thi tra ve user, neu khong thi tra ve null
    public static User login(String name, String pass) throws SQLException {
        Conn conn = new Conn();
        User user = null;
        String query = "select * from user where Ten='"+name+"' and MatKhau='"+pass+"'";
        ResultSet rs = conn.s.executeQuery(query);
        if(rs.next())
        {
            boolean isAdmin = rs.getBoolean("IsAdmin");
            int daMua = rs.getInt("DaMua");
            user = new User(rs.getInt("Id"), name, pass, isAdmin, daMua);
        }
        return user;
    }

    //them user vao databases
    public static boolean register(String name, String pass) throws SQLException {
        Conn conn = new Conn();
        String query = "select * from user where Ten='"+name+"' and MatKhau='"+pass+"'";
        ResultSet rs = conn.s.executeQuery(query);
        if(rs.next())
            return false;
        String q = "insert into user(Ten, MatKhau, IsAdmin, DaMua) values('"+name+"','"+pass+"', 0, 0)";
        conn.s.executeUpdate(q);
        return true;
    }
}
