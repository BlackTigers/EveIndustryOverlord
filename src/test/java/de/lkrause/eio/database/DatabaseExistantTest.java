package de.lkrause.eio.database;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import de.lkrause.eio.model.DbConstants;

public class DatabaseExistantTest {

	private DataSource mDataSource = null;
	
	@Before
	public void setUp() throws Exception {
		
		BasicDataSource lDataSource = new BasicDataSource();

		lDataSource.setDriverClassName(DbConstants.DB_DRIVER);
		lDataSource.setUsername(DbConstants.DB_USER);
		lDataSource.setPassword(DbConstants.DB_LOGIN);
		lDataSource.setUrl("jdbc:hsqldb:mem:" + DbConstants.DEFAULT_DB_NAME);
		
		mDataSource = lDataSource;
	}
	@Test
	public void databaseExistantTest() {
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		assertTrue(DatabaseConnection.getInstance().isDatabaseExistant());
		
	}
}
