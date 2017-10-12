package de.lkrause.EIO.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseConnection {

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	private Connection mConnect = null;
	private Statement mStatement = null;
	private PreparedStatement mPreparedStatement = null;
	private ResultSet mResultSet = null;
	
	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			mConnect = DriverManager.getConnection("jdbc:mysql://localhost/EveIndustryOverlord?user=EIO&password=EveOvermind");
			// TODO Protect User Data
		} catch (Exception pException) {
			pException.printStackTrace();
		}
	}
	
	public static DatabaseConnection getInstance() {
		return INSTANCE;
	}
	
	// TODO Insert Queries
}
