package de.unidue.inf.is.stores;
import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.utils.DBUtil;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class AufgabeStore  implements Closeable {
    private Connection connection;
    private boolean complete;
    public AufgabeStore() throws StoreException{

        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        }
        catch(SQLException e) {
            throw new StoreException(e);
        }
    }
    public ArrayList<Aufgabe> get_A_einesKurses(Kurs k){
        ArrayList<Aufgabe> Af = new ArrayList<Aufgabe>();
        try {

            PreparedStatement psmt = connection.prepareStatement("select a.name from dbp155.Aufgabe a where a.kid =? order by a.anummer asc");
            psmt.setInt(1, k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                Aufgabe a = new Aufgabe();
                a.setName(rs.getString("name"));
                Af.add(a);
            }
        }catch (SQLException e){
            throw new StoreException(e);
        }
      return  Af;
    }




    public void complete() {
        complete = true;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }

}
