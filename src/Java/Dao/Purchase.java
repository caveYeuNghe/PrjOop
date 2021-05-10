package Java.Dao;

import Java.Model.Bill;
import Java.Model.DeviceTf;
import Java.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Purchase {
    private User user;
    private Bill bill;
    private Conn conn;

    public Purchase(User user)
    {
        this.user = user;
        bill = new Bill(user.getId());
        conn = new Conn();
    }

    //day hoa don len database
    public void action(ArrayList<DeviceTf> cart) throws SQLException {
        if(cart.isEmpty())
            return;
        for (DeviceTf device:
             cart) {
            int id = device.getId();
            int soluong = device.getSoLuong();
            bill.action(device.getPrice(), soluong);
            int soluongmoi = device.getConLai() - soluong;
            if (soluongmoi < 1) {
                String q = "Delete from device where id = '" + id + "'";
                conn.s.executeUpdate(q);
            }
            else {
                String q = "update device set CONLAI = '" + soluongmoi + "' where id = '" + id + "'";
                conn.s.executeUpdate(q);
            }
        }
        int idUser = user.getId();
        int gia = bill.getMoney();

        String queryBill = "insert into bill(UserId, Gia, ThoiGian) values ('" + idUser + "', '" + gia +"',CURDATE())";
        conn.s.executeUpdate(queryBill);

        String query = "select * from user where Id='" + idUser + "'";
        ResultSet rs = conn.s.executeQuery(query);
        if(rs.next()) {
            int daMua = rs.getInt("DaMua");
            daMua += gia;
            System.out.println(daMua);
            String queryUser = "update user set DaMua = '" + daMua + "' where Id = '" + idUser + "'";
            conn.s.executeUpdate(queryUser);
        }

    }

}
