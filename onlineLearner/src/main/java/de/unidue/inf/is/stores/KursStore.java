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
	
	
	public KursStore() throws StoreException
	{
		try {
			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);
		}
		catch(SQLException e) {
			throw new StoreException(e);
		}
	}

	public ArrayList<Kurs> getAvailableCourses() throws StoreException
	{
		ArrayList<Kurs> result = new ArrayList<>();
		try {
			//PreparedStatement pstmt = connection.prepareStatement("select * from kurs");
			PreparedStatement pstmt = connection.prepareStatement("select k.kid, k.name as kname , k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer = k.ersteller where k.name not in (select k.name from dbp155.kurs k join dbp155.einschreiben e on k.kid = e.kid where  e.bnummer = 1)");
			//PreparedStatement pstmt = connection.prepareStatement("select k.name as Kurs_name, k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer=k.ersteller where k.freiePlaetze>0 and k.ersteller !=1");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Kurs k = new Kurs();
				k.setKid(rs.getInt("kid"));
				k.setName(rs.getString("kname"));
				k.setFreiePlaetze(rs.getInt("freiePlaetze"));
				k.setErsteller_name(rs.getString("name"));
				result.add(k);
			}
		} catch(SQLException e) {
			throw new StoreException(e);
		}

		return result;
	}
	
	public void createNewCourse(Kurs k) throws StoreException{
		try {
			PreparedStatement pstmt = connection.prepareStatement("insert into dbp155.kurs (name, beschreibungstext, einschreibeschluessel, freiePlaetze, ersteller) values (?, ?, ?, ?, ?)");
			pstmt.setString(1,  k.getName());
			pstmt.setString(2, k.getBeschreibungstext());
			pstmt.setString(3, k.getSchluessel());
			pstmt.setInt(4, k.getFreiePlaetze());
			pstmt.setInt(5, k.getErsteller());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new StoreException(e);
		}
	}
	public ArrayList<Kurs> get_my_courses () throws StoreException {
		ArrayList<Kurs> result = new ArrayList<>();
		try {
			//PreparedStatement pstmt = connection.prepareStatement("select * from kurs");
			PreparedStatement pstmt = connection.prepareStatement("select k.kid, k.name as kname , k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer = k.ersteller where k.name in (select k.name from dbp155.kurs k join dbp155.einschreiben e on k.kid = e.kid where e.bnummer = 1)");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Kurs k = new Kurs();
				k.setKid(rs.getInt("kid"));
				k.setName(rs.getString("kname"));
				k.setFreiePlaetze(rs.getInt("freiePlaetze"));
				k.setErsteller_name(rs.getString("name"));
				result.add(k);
			}
		} catch(SQLException e) {
			throw new StoreException(e);
		}

		return result;
	}
	public boolean ist_eingeschrieben(int kid) throws StoreException, SQLException {
		boolean des = false;
		List<Integer> int_list = new ArrayList<Integer>();
		String s = Integer.toString(kid);

		try {
			PreparedStatement pstmt = connection.prepareStatement("select e.bnummer from dbp155.einschreiben e where e.kid = " + s);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				int_list.add(rs.getInt("bnummer"));

			}
		}catch (SQLException e) {
			throw new StoreException(e);
		}
		for (int x: int_list){
			if (x==1){
				des = true;
				break;
			}
		}


		return des;
	}
public Kurs get_kurs (int KID) throws StoreException ,  SQLException{
		// it will be used for the seak of getting a kurs to show it new enroll page.
		Kurs k = new Kurs();
	String s = Integer.toString(KID);
	try {
		PreparedStatement pstmt = connection.prepareStatement("select k.name, k.freiePlaetze from dbp155.kurs k where k.kid= " + s);
		ResultSet rs = pstmt.executeQuery();
		k.setKid(KID);
		while (rs.next()) {
			k.setName(rs.getString("name"));
			k.setFreiePlaetze(rs.getInt("freiePlaetze"));
		}


	}catch(SQLException e) {
		throw new StoreException(e);
	}
		return  k;
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


