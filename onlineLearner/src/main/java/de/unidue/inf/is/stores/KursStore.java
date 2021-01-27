package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.utils.DBUtil;

public final class KursStore implements Closeable {
    private Connection connection;
    private boolean complete;

    public KursStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public ArrayList<Kurs> getAvailableCourses() throws StoreException {
        ArrayList<Kurs> result = new ArrayList<>();

        try {
            //PreparedStatement pstmt = connection.prepareStatement("select * from kurs");
            PreparedStatement pstmt = connection.prepareStatement("select k.kid, k.name as kname , k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer = k.ersteller where k.name not in (select k.name from dbp155.kurs k join dbp155.einschreiben e on k.kid = e.kid where  e.bnummer = 1)");
            //PreparedStatement pstmt = connection.prepareStatement("select k.name as Kurs_name, k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer=k.ersteller where k.freiePlaetze>0 and k.ersteller !=1");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Kurs k = new Kurs();
                k.setKid(rs.getInt("kid"));
                k.setName(rs.getString("kname"));
                k.setFreiePlaetze(rs.getInt("freiePlaetze"));
                k.setErsteller_name(rs.getString("name"));
                result.add(k);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return result;
    }

    public void createNewCourse(Kurs k) throws StoreException {
        try {
            PreparedStatement pstmt = connection.prepareStatement("insert into dbp155.kurs (name, beschreibungstext, einschreibeschluessel, freiePlaetze, ersteller) values (?, ?, ?, ?, ?)");
            pstmt.setString(1, k.getName());
            pstmt.setString(2, k.getBeschreibungstext());
            pstmt.setString(3, k.getSchluessel());
            pstmt.setInt(4, k.getFreiePlaetze());
            pstmt.setInt(5, k.getErsteller());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public void sich_einschreiben(Kurs k) throws StoreException, SQLException {
        String s = Integer.toString(k.getKid());
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("insert into dbp155.einschreiben (bnummer, kid) values (?,?)");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, k.getKid());
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement("update dbp155.kurs k Set k.freiePlaetze = k.freiePlaetze-1 where k.kid = ?");
            pstmt.setString(1, s);
            pstmt.executeUpdate();
            //connection.commit();
        } catch (SQLException e) {
            //connection.rollback();
            throw new StoreException(e);
        } /*finally {
            connection.setAutoCommit(true);
        }*/
    }

    public ArrayList<Kurs> get_my_courses() throws StoreException {
        ArrayList<Kurs> result = new ArrayList<>();
        try {
            //PreparedStatement pstmt = connection.prepareStatement("select * from kurs");
            PreparedStatement pstmt = connection.prepareStatement("select k.kid, k.name as kname , k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer = k.ersteller where k.name in (select k.name from dbp155.kurs k join dbp155.einschreiben e on k.kid = e.kid where e.bnummer = 1)");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Kurs k = new Kurs();
                k.setKid(rs.getInt("kid"));
                k.setName(rs.getString("kname"));
                k.setFreiePlaetze(rs.getInt("freiePlaetze"));
                k.setErsteller_name(rs.getString("name"));
                result.add(k);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return result;
    }

    public boolean ist_eingeschrieben(int kid) throws StoreException, SQLException {
        boolean des = false;
        List<Integer> int_list = new ArrayList<Integer>();
        String s = Integer.toString(kid);

        try {
            PreparedStatement pstmt = connection.prepareStatement("select e.bnummer from dbp155.einschreiben e where e.kid = ?");
            pstmt.setString(1, s);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int_list.add(rs.getInt("bnummer"));
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        for (int x : int_list) {
            if (x == 1) {
                des = true;
                break;
            }
        }
        return des;
    }

    public Kurs get_kurs(int KID) throws StoreException {
        // it will be used for the seak of getting a kurs to show it new enroll page.
        Kurs k = new Kurs();
        String s = Integer.toString(KID);
        try {
            PreparedStatement pstmt = connection.prepareStatement("select k.name as kname, k.freiePlaetze, k.beschreibungstext, k.einschreibeschluessel, k.ersteller, b.name from dbp155.kurs k  join dbp155.benutzer b on b.bnummer = k.ersteller where k.kid= ?");
            pstmt.setString(1, s); //statt "+s" am Ende der SQL Anweisung
            ResultSet rs = pstmt.executeQuery();
            k.setKid(KID);
            while (rs.next()) {
                k.setName(rs.getString("kname"));
                k.setFreiePlaetze(rs.getInt("freiePlaetze"));
                k.setBeschreibungstext(rs.getString("beschreibungstext"));
                k.setErsteller_name(rs.getString("name"));
                k.setSchluessel(rs.getString("einschreibeschluessel"));
                k.setErsteller(rs.getInt("ersteller"));
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return k;
    }

    public void deleteCourse(int KID) throws StoreException {
    	try {
    		PreparedStatement pstmt;
    	
    		//Abgabe ID's durch gegebene Kurs ID kriegen
    		pstmt = connection.prepareStatement("SELECT e.aid FROM dbp155.einreichen e WHERE e.kid = ?");
    		pstmt.setInt(1, KID);
    		ResultSet rs = pstmt.executeQuery();
    		List<Integer> aufgaben = new ArrayList<Integer>();
    		while(rs.next()) {
    			aufgaben.add(rs.getInt(0));
    		}
    	
    		//Einträge aus Tabelle bewerten löschen
    		for(Integer aid : aufgaben) {
    			pstmt = connection.prepareStatement("DELETE FROM dbp155.bewerten b WHERE b.aid = ?");
    			pstmt.setInt(1, aid);
    			pstmt.executeUpdate();
    		}
    	
    		//Einträge aus Tabelle abgabe löschen
    		for(Integer aid : aufgaben) {
    			pstmt = connection.prepareStatement("DELETE FROM dbp155.abgabe a WHERE a.aid = ?");
    			pstmt.setInt(1, aid);
    			pstmt.executeUpdate();
    		}
    	
    		//Einträge aus Tabelle einreichen löschen
    		for(Integer aid : aufgaben) {
    			pstmt = connection.prepareStatement("DELETE FROM dbp155.einreichen e WHERE e.aid = ? AND e.kid = ?");
    			pstmt.setInt(1, aid);
    			pstmt.setInt(2, KID);
    			pstmt.executeUpdate();
    		}
    	
    		//Einträge aus Tabelle aufgabe löschen
    		pstmt = connection.prepareStatement("DELETE FROM dbp155.aufgabe a WHERE a.kid = ?");
    		pstmt.setInt(1, KID);
    		pstmt.executeUpdate();
    	
    		//Einträge aus Tabelle einschreiben löschen
    		pstmt = connection.prepareStatement("DELETE FROM dbp155.einschreiben k WHERE k.kid = ?");
    		pstmt.setInt(1, KID);
    		pstmt.executeUpdate();
    	
    		//Eintrag aus Tabelle Kurs löschen
    		pstmt = connection.prepareStatement("DELETE FROM dbp155.kurs k WHERE k.kid = ?");
    		pstmt.setInt(1, KID);
    		pstmt.executeUpdate();
    	} catch(SQLException e) {
    		throw new StoreException(e);
    	}
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
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                throw new StoreException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }

}


