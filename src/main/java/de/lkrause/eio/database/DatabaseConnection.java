package de.lkrause.eio.database;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DatabaseConnection {

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	private DataSource mDataSource = null;

	private static final int HSQL_ERROR_LOCKED = -451;

	private static final int HSQL_ERROR_NOT_FOUND = -465;
	
	private static final String TYPEID = "typeID";
	private static final String TYPENAME = "typeName";
	private static final String VOLUME = "volume";
	private static final String GROUPID = "groupID";
	private static final String MAXPRODUCTIONLIMIT = "maxProductionLimit";
	private static final String QUANTITY = "quantity";
	private static final String TIME = "time";
	private static final String MATERIALTYPEID = "materialTypeID";
	private static final String PRODUCTTYPEID = "productTypeID";
	
	private Logger mLogger = Logger.getLogger("DatabaseConnection");
	

	/**
	 * Initialize the empty private constructor so this is a singleton
	 */
	private DatabaseConnection() {

	}
	
	
	public void createDatabase() {
		String lErrorCommand = "";
		try {

			mLogger.log(Level.FINE, "DBConnection::Creating Database");

			QueryRunner lRunner = new QueryRunner(mDataSource);
			
			List<String> lStatements = getStatements();

			for (String lCommand : lStatements) {
				lErrorCommand = lCommand;
				lRunner.execute(lCommand);
			}

			mLogger.log(Level.FINE, "Database Created");
		} catch (final Exception lError) {
			mLogger.log(Level.WARNING, "Command Failed");
			mLogger.log(Level.WARNING, lErrorCommand);
		}
	}

	public boolean checkDatabaseValidity() {
		try {
			List<String> lResults = new ArrayList<>();
			QueryRunner lRunner = new QueryRunner(mDataSource);
			
//			lResults.addAll(lRunner.query("SHOW TABLES", new StringResultSetList("Tables_in_EveIndustryOverlord")));
//			
//			for (String lResult : lResults) {
//				mLogger.log(Level.INFO, lResult);
//			}
			
			lResults.addAll(lRunner.query("SELECT * FROM chrRaces;", new StringResultSetList("raceName")));
			
			for (String lResult : lResults) {
				mLogger.log(Level.INFO, lResult);
			}
			
			lResults.clear();
			lResults.addAll(lRunner.query("SELECT typeName FROM invTypes;", new StringResultSetList("raceName")));
			
			for (String lResult : lResults) {
				mLogger.log(Level.INFO, lResult);
			}
			
			
		} catch (SQLException pException) {
			return false;
		}
		mLogger.log(Level.FINE, "Database ok");
		return true;
	}

	private List<String> getStatements() {

		InputStream lIn = DatabaseConnection.class.getResourceAsStream("/init.sql");

		try (Scanner lScanner = new Scanner(lIn)) {
			System.out.println("Test");
			
			lScanner.useDelimiter("\n");
			List<String> lParsedLines = new ArrayList<>();

			while (lScanner.hasNext()) {
				lParsedLines.add(lScanner.next());
			}

			List<String> lNewCommands = new ArrayList<>();

			for (String lCommand : lParsedLines) {
				if (lCommand.startsWith("--")) {
				} else if (lCommand.startsWith("/*!")) {
				} else if (lCommand.trim().equals("")) {
				} else if (lCommand.startsWith("LOCK TABLE") || lCommand.startsWith("UNLOCK TABLE")) {
				} else if (lCommand.startsWith("  KEY")) {
				} else {
					lCommand = lCommand.replaceAll(" ENGINE=InnoDB DEFAULT CHARSET=utf8;", ";");
					lCommand = lCommand.replaceAll("int\\(11\\)", "int");
					lCommand = lCommand.replaceAll("tinyint\\(1\\)", "tinyint");
					lCommand = lCommand.replaceAll("\\\\'", "''");
					lCommand = lCommand.replaceAll("text", "varchar(8000)");

					lNewCommands.add(lCommand);
					// }
				}
			}
			List<String> lCommands = new ArrayList<>();
			int i = 0;
			while (i < lNewCommands.size()) {
				String lCommand = lNewCommands.get(i);

				String lParsedCommand = "";
				int lLines = 1;

				while (!lCommand.endsWith(";")) {

					lParsedCommand += lCommand;
					lCommand = lNewCommands.get(i + lLines);

					lLines++;
				}
				if (lParsedCommand.endsWith(",")) {
					lParsedCommand = lParsedCommand.substring(0, lParsedCommand.length() - 1);
				}
				lParsedCommand += lCommand;
				lParsedCommand = lParsedCommand.replaceAll("`", "");
				i += lLines;
				lCommands.add(lParsedCommand);
			}
			lScanner.close();

			return lCommands;
		} catch (Exception pException) {
			mLogger.log(Level.SEVERE, "Database Initialization file missing", pException);
		}
		return new ArrayList<>();
	}


	
	// TODO Insert Queries
	
	// --------------------------------------
	//        Database Initialization
	// --------------------------------------

	
	/**
	 * Get the instance of the class.
	 * @return the singleton instance of this class
	 */
	public static DatabaseConnection getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Set the data source for the Database.
	 * @param pDataSource The used DataSource
	 */
	public void setDataSource(DataSource pDataSource) {
		mDataSource = pDataSource;
	}
	
	
	/**
	 * Check if Database is existant, returns fals if the database is not connectable.
	 * @return Is the database existant?
	 */
	public boolean isDatabaseExistant() {

		try {
			mLogger.log(Level.WARNING, "Checking Database");

			
			
			mDataSource.getConnection().close();
			
		} catch (final SQLException lError) {
			if (lError instanceof SQLException && ((SQLException) lError).getErrorCode() == HSQL_ERROR_NOT_FOUND) {
				mLogger.log(Level.FINE, "DBConnection::Check Completed");

				return false;
			} else if (lError instanceof SQLException && ((SQLException) lError).getErrorCode() == HSQL_ERROR_LOCKED) {
			} else {
				mLogger.log(Level.WARNING, "SQL Error", lError);
			}
		}
		mLogger.log(Level.FINE, "Database Check Completed");

		return true;
	}

	/**
	 * Disconnect from the database.
	 */
	public void disconnectFromDatabase() {
		try {
			QueryRunner lRunner = new QueryRunner(mDataSource);
			lRunner.execute("SHUTDOWN");
			mLogger.log(Level.FINE, "Database shutdown initiated");
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "Database Shutdown failed", pException);
		}
	}
	
	// --------------------------------------
	//               Queries                
	// --------------------------------------
	
	/**
	 * Get the typeID of the item called pTypeName.
	 * @param pTypeName Name of the requested Item
	 * @return typeID of the requested Item
	 */
	public int getTypeID(String pTypeName) {

		int lID = -1;
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lID = lRunner.query("SELECT typeID FROM invTypes WHERE typeName = ?", (ResultSet pResults) -> pResults.next() ? pResults.getInt(TYPEID) : -1, pTypeName);
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getTypeID Query failed", e);
		}
		return lID;
	}

	/**
	 * Get the typeName of the item identified by pID.
	 * @param pTypeId typeID of the requested item
	 * @return typeName of the item identified by pTypeID
	 */
	public String getTypeName(int pTypeId) {

		String lName = "";
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lName = lRunner.query("SELECT typeName FROM invTypes WHERE typeId = ?", new ScalarHandler<String>(), pTypeId);

		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getTypeName Query failed", e);
		}
		if (lName == null) {
			return "";
		}
		return lName;
	}

	
	/**
	 * Get the groupID of the item identified by pTypeId.
	 * @param pTypeId typeID of the requested item
	 * @return The GroupID of the item with the typeID pTypeId
	 */
	public int getTypeGroup(int pTypeId) {

		int lGroup = 0;
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lGroup = lRunner.query("SELECT groupID FROM invTypes WHERE typeID = ?", (ResultSet pResults) -> pResults.next() ? pResults.getInt(GROUPID) : -1, pTypeId);
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getTypeGroup Query failed", e);
		}
		return lGroup;
	}
	
	/**
	 * Get the volume of the item identified by pTypeId.
	 * @param pTypeId typeID of the requested item
	 * @return The volume of the item identified by pTypeID
	 */
	public double getVolume(int pTypeId) {

		double lVolume = -1;
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lVolume = lRunner.query("SELECT volume FROM invTypes WHERE typeID = ?", (ResultSet pResults) -> pResults.next() ? pResults.getDouble(VOLUME) : -1, pTypeId);
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getVolume Query failed", e);
		}
		return lVolume;
	}
	
	/**
	 * Get a list of all published blueprint IDs.
	 * @return The list of all blueprint IDs which blueprints are published
	 */
	public List<Integer> getAllBlueprintIDs() {

		List<Integer> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {			
			lResults.addAll(lRunner.query("SELECT typeID FROM invTypes WHERE typeName LIKE '%Blueprint%' AND published = 1", new IntegerResultSetList(TYPEID)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllBlueprintIDs Query failed", pException);
			throw new DataAccessException(pException);
		}
		return lResults;
	}
	
	/**
	 * Get a list of all published blueprint Names.
	 * @return The list of all blueprint name which blueprints are published
	 */
	public List<String> getAllBlueprintNames() {

		List<String> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);		
		try {
			lResults.addAll(lRunner.query("SELECT typeName FROM invTypes WHERE typeName LIKE '%Blueprint%' AND published = 1", new ColumnListHandler<>(TYPENAME)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllBlueprintNames Query failed", pException);
			throw new DataAccessException(pException);
		}
		return lResults;
	}

	/**
	 * Get a list of all published Reaction Formula IDs.
	 * @return The list of all Reaction Formulas that are published (IDs)
	 */
	public List<Integer> getAllReactionIDs() {

		List<Integer> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeID FROM invTypes WHERE typeName LIKE '%Reaction Formula%' AND published = 1", new IntegerResultSetList(TYPEID)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllReactionIDs Query failed", pException);
		}
		return lResults;
	}

	
	/**
	 * Get a list of all published Reaction Formula names.
	 * @return The list of all Reaction Formulas that are published (Names)
	 */
	public List<String> getAllReactionNames() {

		List<String> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeName FROM invTypes WHERE typeName LIKE '%Reaction Formula%' AND published = 1", new StringResultSetList(TYPENAME)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllReactionNames Query failed", pException);
		}
		return lResults;
	}
	
	/**
	 * Get a list of all typeIDs in the database.
	 * @return The List of all typeIDs in the database
	 */
	public List<Integer> getAllItems() {

		List<Integer> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeID FROM invTypes", new IntegerResultSetList(TYPEID)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllItems Query failed", pException);
		}
		return lResults;
	}
	
	/**
	 * Get a list of all typeNames in the database.
	 * @return The List of all typeNames in the database
	 */
	public List<String> getAllItemNames() {

		List<String> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeName FROM invTypes", new StringResultSetList(TYPENAME)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllItemNames Query failed", pException);
		}
		return lResults;
	}

	/**
	 * Get all Items that are not blueprints or reaction formulas.
	 * @return List of all typeIDs that are neither Reaction Formulas nor Blueprints
	 */
	public List<Integer> getAllNonBlueprints() {

		List<Integer> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeID FROM invTypes WHERE typeName NOT LIKE '%Blueprint%' AND typeName NOT LIKE '%Reaction Formula%' AND published = 1", new IntegerResultSetList(TYPEID)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getAllNonBlueprints Query failed", pException);
		}
		return lResults;
	}
	
	/**
	 * Get the list of all items where an invention can run on.
	 * @return List of the typeIDs of those items
	 */
	public List<Integer> getInventableBlueprints() {
		List<Integer> lResults = new ArrayList<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lResults.addAll(lRunner.query("SELECT typeID FROM industryActivity WHERE activityID=8", new IntegerResultSetList(TYPEID)));
		} catch (SQLException pException) {
			mLogger.log(Level.WARNING, "getInventableBlueprints Query failed", pException);
		}
		return lResults;
	}
	
	/**
	 * Get the time a blueprint runs to completion (use Activities statics for activity IDs).
	 * @param pTypeId typeID of the blueprint
	 * @param pActivity activityID (see Activities)
	 * @return Time the blueprint runs to completion in seconds
	 */
	public int getBlueprintTime(int pTypeId, int pActivity) {

		int lTime = -1;
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lTime = lRunner.query("SELECT time FROM industryActivity WHERE typeId = ? AND activityID = ?", (ResultSet pResults) -> pResults.next() ? pResults.getInt(TIME) : -1, pTypeId, pActivity);
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getBlueprintTime Query failed", e);
		}
		return lTime;
	}
	
	/**
	 * Get the maximum runs of a blueprint.
	 * @param pTypeId The typeID of the questioned blueprint
	 * @return The maximum amount of runs you can use on a blueprint
	 */
	public int getBlueprintMaxRuns(int pTypeId) {
		int lRuns = -1;
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lRuns = lRunner.query("SELECT maxProductionLimit FROM industryBlueprints WHERE typeId = ?", (ResultSet pResults) -> pResults.next() ? pResults.getInt(MAXPRODUCTIONLIMIT) : -1, pTypeId);
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getBlueprintMaxRuns Query failed", e);
		}
		return lRuns;
	}
	
	/**
	 * Get the input materials of the blueprint identified by pTypeId when running the activity pActivity.
	 * @param pTypeId typeID of the item run
	 * @param pActivity activity run
	 * @return A map of the input material IDs and their amounts
	 */
	public Map<Integer, Integer> getBlueprintInput(int pTypeId, int pActivity) {

		Map<Integer, Integer> lInput = new HashMap<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lInput.putAll(lRunner.query("SELECT materialTypeID, quantity FROM industryActivityMaterials WHERE typeId = ? AND activityID = ?", new IntegerResultSetMap(MATERIALTYPEID, QUANTITY), pTypeId, pActivity));
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getBlueprintInput Query failed", e);
		}
		return lInput;
	}
	
	/**
	 * Get the products of the item identified by pTypeId while running the activity pActivity (see Activities).
	 * @param pTypeId The typeID of the item running
	 * @param pActivity The activity that is being run (see Activities)
	 * @return The outcome materials when running the activity pActivity on the type identified by pTypeId
	 */
	public Map<Integer, Integer> getBlueprintProducts(int pTypeId, int pActivity) {
		Map<Integer, Integer> lOutcome = new HashMap<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lOutcome.putAll(lRunner.query("SELECT productTypeID, quantity FROM industryActivityProducts WHERE typeID = ? AND activityID = ?", new IntegerResultSetMap(PRODUCTTYPEID, QUANTITY), pTypeId, pActivity));
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getBlueprintProducts Query failed", e);
		}
		return lOutcome;
	}
	
	/**
	 * Get the inputs of a Reaction Formula identified by pTypeId.
	 * @param pTypeId Identifier of the Reaction Formula
	 * @return A map of the input material IDs and their amounts
	 */
	public Map<Integer, Integer> getReactionInputs(int pTypeId) {

		Map<Integer, Integer> lOutcome = new HashMap<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lOutcome.putAll(lRunner.query("SELECT typeID, quantity FROM invTypeReactions WHERE reactionTypeID = ? AND input = 1", new IntegerResultSetMap(TYPEID, QUANTITY), pTypeId));
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getReactionInputs Query failed", e);
		}
		return lOutcome;
	}

	/**
	 * Get the products of a Reaction Formula identified by pTypeId.
	 * @param pTypeId Identifier of the Reaction Formula
	 * @return A map of outcome material IDs and their amounts
	 */
	public Map<Integer, Integer> getReactionProducts(int pTypeId) {

		Map<Integer, Integer> lOutcome = new HashMap<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lOutcome.putAll(lRunner.query("SELECT typeID, quantity FROM invTypeReactions WHERE reactionTypeID = ? AND input = 0", new IntegerResultSetMap(TYPEID, QUANTITY), pTypeId));
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getReactionOutputs Query failed", e);
		}
		return lOutcome;
	}
	
	
	public Map<Integer, Integer> getRefiningOutputs(int pTypeId) {
		Map<Integer, Integer> lOutcome = new HashMap<>();
		QueryRunner lRunner = new QueryRunner(mDataSource);
		try {
			lOutcome.putAll(lRunner.query("SELECT materialTypeId, quantity FROM invTypeMaterials WHERE typeId = ?", new IntegerResultSetMap(MATERIALTYPEID, QUANTITY), pTypeId));
		} catch (SQLException e) {
			mLogger.log(Level.WARNING, "getRefiningOutputs Query failed", e);
		}
		return lOutcome;
	}


}
