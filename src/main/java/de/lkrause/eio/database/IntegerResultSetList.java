package de.lkrause.eio.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ResultSetHandler for returning a List of Integers
 * @author HuntingFighter Oramara
 *
 */
public class IntegerResultSetList implements ResultSetHandler<List<Integer>> {
	
	/** The name of the column the List items should be pulled from. */
	String mColumIdentifier;
	
	/**
	 * Initialize the IntegerResultSetList.
	 * @param pColumnIdentifier The name of the column the List items should be pulled from
	 */
	public IntegerResultSetList(String pColumnIdentifier) {
		mColumIdentifier = pColumnIdentifier;
	}
	
	@Override
	public List<Integer> handle(ResultSet pResultSet) throws SQLException {
		List<Integer> lResults = new ArrayList<>();
		
		while (pResultSet.next()) {
			lResults.add(pResultSet.getInt(mColumIdentifier));
		}
		return lResults;
	}

}
