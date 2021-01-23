
import java.sql.*;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.StoreException;
import de.unidue.inf.is.utils.DBUtil;


public class main {
    private static Connection connection;
    private boolean complete;

    public static void main (String[] args){

        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statment = connection.createStatement();
            String s = "select name from dbp155.kurs";
            ResultSet rs =  statment.executeQuery(s);
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
        e.printStackTrace();
        } finally {
            System.out.println("Ya rab");
        }
    }
}
