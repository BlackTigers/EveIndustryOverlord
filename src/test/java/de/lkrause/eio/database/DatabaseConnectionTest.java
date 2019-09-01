package de.lkrause.eio.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.hsqldb.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lkrause.eio.collections.Activities;
import de.lkrause.eio.model.DbConstants;

public class DatabaseConnectionTest {

	private DataSource mDataSource = null;
	
	@Before
	public void setUp() throws Exception {
		
		BasicDataSource lDataSource = new BasicDataSource();

		lDataSource.setDriverClassName(DbConstants.DB_DRIVER);
		lDataSource.setUsername(DbConstants.DB_USER);
		lDataSource.setPassword(DbConstants.DB_LOGIN);
		lDataSource.setUrl("jdbc:hsqldb:mem:" + DbConstants.DEFAULT_DB_NAME);
		
		mDataSource = lDataSource;
		
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
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
		
		lRunner.execute("CREATE TABLE industryActivity (" + 
				" typeID int NOT NULL," + 
				" activityID int NOT NULL," + 
				" time int DEFAULT NULL," + 
				" PRIMARY KEY (typeID,activityID))");
		
		lRunner.execute("CREATE TABLE industryBlueprints (" + 
				" typeID int NOT NULL," + 
				" maxProductionLimit int DEFAULT NULL," + 
				" PRIMARY KEY (typeID))");
		
		lRunner.execute("CREATE TABLE industryActivityProducts (" + 
				" typeID int DEFAULT NULL," + 
				" activityID int DEFAULT NULL," + 
				" productTypeID int DEFAULT NULL," + 
				" quantity int DEFAULT NULL)");
		
		lRunner.execute("CREATE TABLE invTypeReactions (" + 
				" reactionTypeID int NOT NULL," + 
				" input tinyint NOT NULL," + 
				" typeID int NOT NULL," + 
				" quantity int DEFAULT NULL," + 
				" PRIMARY KEY (reactionTypeID,input,typeID)" + 
				")");
		
		lRunner.execute("CREATE TABLE industryActivityMaterials (" + 
				" typeID int DEFAULT NULL," + 
				" activityID int DEFAULT NULL," + 
				" materialTypeID int DEFAULT NULL," + 
				" quantity int DEFAULT NULL)");
		
		lRunner.execute("CREATE TABLE invTypeMaterials (typeID int DEFAULT NULL, materialTypeID int DEFAULT NULL, quantity int DEFAULT NULL)");
	}

	@After
	public void tearDown() throws Exception {
		QueryRunner lRunner = new QueryRunner(mDataSource);


		lRunner.execute("SHUTDOWN");
	}
	
	@Test
	public void databaseExistantTest() {
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		assertTrue(DatabaseConnection.getInstance().isDatabaseExistant());
		
	}

	@Test
	public void getTypeNameTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, typeName) VALUES (34, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals("Tritanium", DatabaseConnection.getInstance().getTypeName(34));
		
	}
	
	@Test
	public void getTypeNameNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, typeName) VALUES (34, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals("", DatabaseConnection.getInstance().getTypeName(0));
		
	}
	
	@Test
	public void getTypeIdTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, typeName) VALUES (34, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(34, DatabaseConnection.getInstance().getTypeID("Tritanium"));
		
	}
	
	@Test
	public void getTypeIdNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, typeName) VALUES (34, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(-1, DatabaseConnection.getInstance().getTypeID(""));
		
	}
	
	@Test
	public void getGroupIdTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, groupID, typeName) VALUES (34, 18, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(18, DatabaseConnection.getInstance().getTypeGroup(34));
		
	}
	
	@Test
	public void getGroupIdNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, groupID, typeName) VALUES (34, 18, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(-1, DatabaseConnection.getInstance().getTypeGroup(0));
		
	}

	@Test
	public void getVolumeIdTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName) VALUES (34, 0.01, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0.01, DatabaseConnection.getInstance().getVolume(34), 0.01);
		
	}
	
	@Test
	public void getVolumeIdNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName) VALUES (34, 0.01, 'Tritanium')");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(-1.0, DatabaseConnection.getInstance().getVolume(0), 0.01);
		
	}

	@Test
	public void getBlueprintIdsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Blueprint', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(3, DatabaseConnection.getInstance().getAllBlueprintIDs().size());
		
	}
	
	@Test
	public void getBlueprintIdsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		

		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllBlueprintIDs().size());
		
	}
	@Test
	public void getBlueprintNamesTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Blueprint', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(3, DatabaseConnection.getInstance().getAllBlueprintNames().size());
		
	}
	
	@Test
	public void getBlueprintNamesNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		

		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllBlueprintNames().size());
		
	}
	
	@Test
	public void getReactionNamesTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Reaction Formula', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(3, DatabaseConnection.getInstance().getAllReactionNames().size());
		
	}
	
	@Test
	public void getReactionNamesNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		

		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllReactionNames().size());
		
	}
	
	@Test
	public void getReactionIDsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Reaction Formula', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(3, DatabaseConnection.getInstance().getAllReactionIDs().size());
		
	}
	
	@Test
	public void getReactionIDsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		

		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllReactionIDs().size());
		
	}
	
	@Test
	public void getAllItemsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Reaction Formula', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(5, DatabaseConnection.getInstance().getAllItems().size());
		
	}
	
	@Test
	public void getAllItemsNegativeTest() throws SQLException {		
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllItems().size());
		
	}

	@Test
	public void getNonBlueprintsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Blueprint', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(2, DatabaseConnection.getInstance().getAllNonBlueprints().size());
		
	}
	
	@Test
	public void getNonBlueprintsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		

		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (12, 0.01, 'Dragoon Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (34, 0.01, 'Tritanium Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (13, 0.01, 'Punisher Reaction Formula', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (15, 0.01, 'Pyerite Blueprint', 1)");
		lRunner.execute("INSERT INTO invTypes (typeID, volume, typeName, published) VALUES (14, 0.01, 'Tristan Blueprint', 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getAllNonBlueprints().size());
	}
	
	@Test
	public void getAllInventableBlueprintsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (34, 8)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (35, 7)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (38, 8)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (22, 8)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(3, DatabaseConnection.getInstance().getInventableBlueprints().size());
	}
	
	
	@Test
	public void getAllInventableBlueprintsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (34, 3)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (35, 7)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (38, 5)");
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID) VALUES (22, 4)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(0, DatabaseConnection.getInstance().getInventableBlueprints().size());
	}
	
	@Test
	public void getMaxBlueprintRunsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryBlueprints (typeID, maxProductionLimit) VALUES (34, 10)");
		
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(10, DatabaseConnection.getInstance().getBlueprintMaxRuns(34));
	}
	
	@Test
	public void getMaxBlueprintRunsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryBlueprints (typeID, maxProductionLimit) VALUES (34, 10)");
		
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(-1, DatabaseConnection.getInstance().getBlueprintMaxRuns(0));
	}

	@Test
	public void getBlueprintTimeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID, time) VALUES (34, " + Activities.MANUFACTURING + ", 1000)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(1000, DatabaseConnection.getInstance().getBlueprintTime(34, Activities.MANUFACTURING));
	}
	
	@Test
	public void getBlueprintTimeNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivity (typeID, activityID, time) VALUES (34, " + Activities.MANUFACTURING + ", 1000)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		assertEquals(-1, DatabaseConnection.getInstance().getBlueprintTime(0, Activities.MANUFACTURING));
		assertEquals(-1, DatabaseConnection.getInstance().getBlueprintTime(34, 10000));
	}

	@Test
	public void getBlueprintProductsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 35, 1000, " + Activities.MANUFACTURING + ")");
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 36, 1000, " + Activities.MANUFACTURING + ")");
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 37, 1000, " + Activities.MANUFACTURING + ")");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getBlueprintProducts(34, Activities.MANUFACTURING));
		
		assertEquals(1000, lOutputs.get(35), 0.01);
		assertEquals(1000, lOutputs.get(36), 0.01);
		assertEquals(1000, lOutputs.get(37), 0.01);
		assertEquals(3, lOutputs.size());
	}
	
	@Test
	public void getBlueprintProductsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 35, 1000, " + Activities.MANUFACTURING + ")");
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 36, 1000, " + Activities.MANUFACTURING + ")");
		lRunner.execute("INSERT INTO industryActivityProducts (typeID, productTypeID, quantity, activityID) VALUES (34, 37, 1000, " + Activities.MANUFACTURING + ")");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getBlueprintProducts(33, Activities.MANUFACTURING));
		
		assertEquals(0, lOutputs.size());
	}
	
	@Test
	public void getReactionInputsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (13, 100, 34, 1)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (14, 100, 34, 1)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (15, 100, 34, 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getReactionInputs(34));
		
		assertEquals(100, lOutputs.get(13), 1);
		assertEquals(100, lOutputs.get(14), 1);
		assertEquals(100, lOutputs.get(15), 1);
		assertEquals(3, lOutputs.size());
	}	
	
	@Test
	public void getReactionInputsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (13, 100, 34, 1)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (14, 100, 34, 1)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (15, 100, 34, 1)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getReactionInputs(33));
		
		assertEquals(0, lOutputs.size());
	}
	
	@Test
	public void getBlueprintInputsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 10, 100)");
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 11, 110)");
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 12, 120)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
				
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getBlueprintInput(34, Activities.MANUFACTURING));
		
		assertEquals(100, lOutputs.get(10), 1);
		assertEquals(110, lOutputs.get(11), 1);
		assertEquals(120, lOutputs.get(12), 1);
		assertEquals(3, lOutputs.size());
	}
	
	@Test
	public void getBlueprintInputsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 10, 100)");
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 11, 110)");
		lRunner.execute("INSERT INTO industryActivityMaterials (typeID, activityID, materialTypeID, quantity) VALUES (34, " + Activities.MANUFACTURING + ", 12, 120)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
				
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getBlueprintInput(33, Activities.MANUFACTURING));
		
		assertEquals(0, lOutputs.size());
	}
	
	
	@Test
	public void getReactionOutputsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (13, 100, 34, 0)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (14, 100, 34, 0)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (15, 100, 34, 0)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getReactionProducts(34));
		
		assertEquals(100, lOutputs.get(13), 1);
		assertEquals(100, lOutputs.get(14), 1);
		assertEquals(100, lOutputs.get(15), 1);
		assertEquals(3, lOutputs.size());
	}	
	
	@Test
	public void getReactionOutputsNegativeTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (13, 100, 34, 0)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (14, 100, 34, 0)");
		lRunner.execute("INSERT INTO invTypeReactions (typeID, quantity, reactionTypeID, input) VALUES (15, 100, 34, 0)");
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		lOutputs.putAll(DatabaseConnection.getInstance().getReactionProducts(33));
		
		assertEquals(0, lOutputs.size());
	}
	
	@Test
	public void getReprocessedMaterialsTest() throws SQLException {
		QueryRunner lRunner = new QueryRunner(mDataSource);
		
		lRunner.execute("INSERT INTO invTypeMaterials (typeID, materialTypeID, quantity) VALUES (28432, 34, 415)");
		lRunner.execute("INSERT INTO invTypeMaterials (typeID, materialTypeID, quantity) VALUES (28432, 35, 15)");
		
		DatabaseConnection.getInstance().setDataSource(mDataSource);
		
		Map<Integer, Integer> lResult = new HashMap<>();
		
		lResult.putAll(DatabaseConnection.getInstance().getRefiningOutputs(28432));
		
		assertEquals(2, lResult.size());
		assertEquals(415, (int) lResult.get(34));
		assertEquals(15, (int) lResult.get(35));
	}
	
	
}
