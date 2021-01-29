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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ArrayList<einreichen> get_abgabe_eineskurses(Kurs k) throws StoreException {
        ArrayList<einreichen> ei = new ArrayList<>();

        try {
            PreparedStatement psmt = connection.prepareStatement("select  k.anummer , ab.abgabetext , ab.aid from dbp155.einreichen k join dbp155.abgabe ab on k.aid = ab.aid where k.kid = ? and k.bnummer =1");
            psmt.setInt(1, k.getKid());
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                einreichen e = new einreichen();
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
