package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			PreparedStatement pstmt = connection.prepareStatement("select k.name as Kurs_name, k.freiePlaetze, b.name from dbp155.kurs k join dbp155.benutzer b on b.bnummer=k.ersteller where k.freiePlaetze>0");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Kurs k = new Kurs();
				k.setName(rs.getString("kurs_name"));
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


