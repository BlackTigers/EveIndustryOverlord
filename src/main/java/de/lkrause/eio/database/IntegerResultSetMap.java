package de.lkrause.eio.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ResultSetHandler for returning a List of Integers
 * @author HuntingFighter Oramara
 *
 */
public class IntegerResultSetMap implements ResultSetHandler<Map<Integer, Integer>> {
	
	/** The name of the column the List items should be pulled from. */
	String mColumIdentifier;
	String mColumIdentifier2;
	
	/**
	 * Initialize the IntegerResultSetList.
	 * @param pKeyColumnIdentifier The name of the key column
	 * @param pValueColumnIdentifier The name of the value column
	 */
	public IntegerResultSetMap(String pKeyColumnIdentifier, String pValueColumnIdentifier) {
		mColumIdentifier = pKeyColumnIdentifier;
		mColumIdentifier2 = pValueColumnIdentifier;
	}
	
	@Override
	public Map<Integer, Integer> handle(ResultSet pResultSet) throws SQLException {
		Map<Integer, Integer> lResults = new HashMap<>();
		
		while (pResultSet.next()) {
			lResults.put(pResultSet.getInt(mColumIdentifier), pResultSet.getInt(mColumIdentifier2));
		}
		return lResults;
	}

}
