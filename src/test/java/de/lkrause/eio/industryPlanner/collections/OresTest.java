package de.lkrause.eio.industryPlanner.collections;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.model.DbConstants;
import de.lkrause.eio.model.OreType;

public class OresTest {

	BasicDataSource mDataSource;
	
	@Before
	public void setUp() throws Exception {
		
		BasicDataSource lDataSource = new BasicDataSource();

		lDataSource.setDriverClassName(DbConstants.DB_DRIVER);
		lDataSource.setUsername(DbConstants.DB_USER);
		lDataSource.setPassword(DbConstants.DB_LOGIN);
		lDataSource.setUrl("jdbc:hsqldb:mem:" + DbConstants.DEFAULT_DB_NAME);
		
		DatabaseConnection.getInstance().setDataSource(lDataSource);
		
		mDataSource = lDataSource;
		
		QueryRunner lRunner = new QueryRunner(lDataSource);
		
		lRunner.execute("CREATE TABLE invTypes (" + 
				" typeID int NOT NULL," + 
				" groupID int DEFAULT NULL," + 
				" typeName varchar(100) DEFAULT NULL," + 
				" description varchar(2500)," + 
				" mass double DEFAULT NULL," + 
				" volume double DEFAULT NULL," + 
				" capacity double DEFAULT NULL," + 
				" portionSize int DEFAULT NULL," + 
				" raceID int DEFAULT NULL," + 
				" basePrice decimal(19,4) DEFAULT NULL," + 
				" published tinyint DEFAULT NULL," + 
				" marketGroupID int DEFAULT NULL," + 
				" iconID int DEFAULT NULL," + 
				" soundID int DEFAULT NULL," + 
				" graphicID int DEFAULT NULL," + 
				" PRIMARY KEY (typeID))");
		
		lRunner.execute("CREATE TABLE invTypeMaterials (typeID int DEFAULT NULL, materialTypeID int DEFAULT NULL, quantity int DEFAULT NULL)");

	}
	
	@After
	public void tearDown() throws Exception {
		QueryRunner lRunner = new QueryRunner(mDataSource);


		lRunner.execute("SHUTDOWN");
	}

	
	@Test
	public void testCalculateOreAmounts() {
		Map<Integer, Integer> lMinerals = new HashMap<>();
		lMinerals.put(35, 103754320);
		lMinerals.put(36, 23458162);
		lMinerals.put(37, 8387854);
		lMinerals.put(38, 1355493);
		lMinerals.put(39, 387291);
		lMinerals.put(40, 139843);
	}

	@Test
	public void testOreTypeToString() throws SQLException {
		
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeMaterials (typeID, materialTypeID, quantity) VALUES (28432, 34, 415)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, mass, volume, published) VALUES (28432, 'Compressed Veldspar', 4000, 0.15, 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (34, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (35, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (36, 'Mexallon', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (37, 'Isogen', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (38, 'Nocxium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (39, 'Zydrine', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (40, 'Megacyte', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, typeName, published) VALUES (11399, 'Morphite', 1)");
		
		
		OreType lOre = new OreType("Veldspar");
		assertEquals("Compressed Veldspar: 415, 0, 0, 0, 0, 0, 0, 0; Compressed Concentrated Veldspar: 436, 0, 0, 0, 0, 0, 0, 0; Compressed Dense Veldspar: 457, 0, 0, 0, 0, 0, 0, 0", lOre.toString());
	}
}
