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
			PreparedStatement pstmt = connection.prepareStatement("select k.name, k.freiePlaetze, b.name from kurs k join benutzer b on b.bnummer=k.ersteller where k.freiePlaetze>0");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Kurs k = new Kurs();
				k.setName(rs.getString("name"));
				k.setErsteller(rs.getInt("ersteller"));
				k.setFreiePlaetze(rs.getInt("freiePlaetze"));
				result.add(k);
			}
		} catch(SQLException e) {
			throw new StoreException(e);
		}
		return result;
	}


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
