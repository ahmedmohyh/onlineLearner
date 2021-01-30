package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Aufgabe;
import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.Einreichen;
import de.unidue.inf.is.domain.Kurs;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AufgabeStore implements Closeable {
    private Connection connection;
    private boolean complete;

    public AufgabeStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    //Diese Methode "bringt" eine Aufgabe in den Kurs anhand eines Kurses k
    public ArrayList<Aufgabe> get_A_einesKurses(Kurs k) throws StoreException {
        ArrayList<Aufgabe> Af = new ArrayList<Aufgabe>();
        try {
            PreparedStatement psmt = connection.prepareStatement("select a.name , a.anummer from dbp155.Aufgabe a where a.kid =? order by a.anummer asc");
            psmt.setInt(1, k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Aufgabe a = new Aufgabe();
                a.setName(rs.getString("name"));
                a.setAnummer(rs.getInt("anummer"));
                a.setKid(k.getKid());
                Af.add(a);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return Af;
    }

    //Diese Methode "bringt" die Abgaben die der Benutzer abgegeben hat
    public ArrayList<Einreichen> get_abgabe_eineskurses(Kurs k) throws StoreException {
        ArrayList<Einreichen> ei = new ArrayList<>();
        try {
            PreparedStatement psmt;
            psmt = connection.prepareStatement("select  k.anummer , ab.abgabetext , ab.aid from dbp155.einreichen k join dbp155.abgabe ab on k.aid = ab.aid where k.kid = ? and k.bnummer =1");
            psmt.setInt(1, k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Einreichen e = new Einreichen();
                e.setAbgabetext(rs.getString("abgabetext"));
                e.setAnummer(rs.getInt("anummer"));
                e.setKid(k.getKid());
                e.setAid(rs.getInt("aid"));
                ei.add(e);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return ei;
    }
    public Bewertung get_Bewertung_einerabgabe(int aid) {
        Bewertung bw= new Bewertung();
        try {
            PreparedStatement psmt;
            psmt = connection.prepareStatement("select avg(bw.note) as av_note from dbp155.bewerten bw where bw.bnummer =1 and bw.aid =? group by bw.aid");
            psmt.setInt(1,aid);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                System.out.println("I got into bewertung");
               // b.setAid(rs.getInt("aid"));
                bw.setNote(rs.getInt("av_note"));
            }
        }catch (SQLException e){
            throw new StoreException(e);
        }
        return bw;
    }

    //Gibt eine spezifische Aufgabe eines Kurses aus
    public Aufgabe getAufgabe(int kid, int aNum) throws StoreException
    {
    	Aufgabe res = new Aufgabe();
    	try {
    		PreparedStatement pstmt = connection.prepareStatement("SELECT a.name, a.beschreibung FROM dbp155.aufgabe a WHERE a.kid=? AND a.anummer=?");
    		pstmt.setInt(1, kid);
    		pstmt.setInt(2, aNum);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			res.setKid(kid);
    			res.setAnummer(aNum);
    			res.setName(rs.getString("name"));
    			res.setBeschreibung(rs.getString("beschreibung"));
    		}
    	} catch(SQLException e) {
    		throw new StoreException(e);
    	}
    	return res;
    }

    public void createNewRate(int bnummer, int aid, int note, String kommentar) throws StoreException {
        try {
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement("INSERT INTO dbp155.bewerten (bnummer, aid, note, kommentar) values (?, ?, ?, ?)");
            pstmt.setInt(1, bnummer);
            pstmt.setInt(2, aid);
            pstmt.setInt(3, note);
            pstmt.setString(4, kommentar);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    
    public boolean checkAssignment(int bnummer, int kid, int anummer) throws StoreException
    {
    	boolean alreadyExists = false;
    	try {
    		PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM dbp155.einreichen e WHERE e.bnummer=? AND e.kid=? AND e.anummer=?");
    		pstmt.setInt(1, bnummer);
    		pstmt.setInt(2, kid);
    		pstmt.setInt(3, anummer);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			alreadyExists=true;
    		}
    		return alreadyExists;
    	} catch(SQLException e) {
    		throw new StoreException(e);
    	}
    }
    
    public void neueAbgabe(int bnummer, int kid, int anummer, String text) throws StoreException
    {
    	int aid = 0;
    	try {
    		PreparedStatement pstmt;
    		pstmt = connection.prepareStatement("INSERT INTO dbp155.abgabe (abgabetext) VALUES (?)", new String[] {"aid"});
    		pstmt.setString(1, text);
    		pstmt.executeUpdate();
    		ResultSet rs = pstmt.getGeneratedKeys();
    		if(rs.next()) {
    			aid = rs.getInt("aid");
    		}
    		else {
    			System.out.println("Returning of generated key seems to not work");
    		}
    		
    		pstmt = connection.prepareStatement("INSERT INTO dbp155.einreichen (bnummer, kid, anummer, aid) VALUES (?, ?, ?, ?)");
    		pstmt.setInt(1, bnummer);
    		pstmt.setInt(2, kid);
    		pstmt.setInt(3, anummer);
    		pstmt.setInt(4, aid);
    		pstmt.executeUpdate();
    		
    	}
    	catch(SQLException e) {
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
