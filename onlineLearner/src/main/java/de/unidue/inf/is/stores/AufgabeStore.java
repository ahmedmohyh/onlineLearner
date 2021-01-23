package de.unidue.inf.is.stores;
import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.domain.einreichen;
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

            PreparedStatement psmt = connection.prepareStatement("select a.name , a.anummer from dbp155.Aufgabe a where a.kid =? order by a.anummer asc");
            psmt.setInt(1, k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                Aufgabe a = new Aufgabe();
                a.setName(rs.getString("name"));
                a.setAnummer(rs.getInt("anummer"));
                Af.add(a);
            }
        }catch (SQLException e){
            throw new StoreException(e);
        }
      return  Af;
    }
    public ArrayList<einreichen> get_abgabe_eineskurses(Kurs k){
        ArrayList<einreichen> ei = new ArrayList<>();
        try {
            PreparedStatement psmt = connection.prepareStatement("select  k.anummer , ab.abgabetext , ab.aid from dbp155.einreichen k join dbp155.abgabe ab on k.aid = ab.aid where k.kid = ? and k.bnummer =1");
            psmt.setInt(1,k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                einreichen e = new einreichen();
                e.setAbgabetext(rs.getString("abgabetext"));
                e.setAnummer(rs.getInt("anummer"));
                e.setKid(k.getKid());
                e.setAid(rs.getInt("aid"));
                ei.add(e);
            }

        }catch (SQLException e){
            throw new StoreException(e);
        }
        return ei;
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
