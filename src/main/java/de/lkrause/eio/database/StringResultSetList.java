package de.lkrause.eio.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ResultSetHandler for returning a List of Strings
 * @author HuntingFighter Oramara
 *
 */
public class StringResultSetList implements ResultSetHandler<List<String>> {
	
	/** The name of the column the List items should be pulled from. */
	String mColumIdentifier;
	
	/**
	 * Initialize the IntegerResultSetList.
	 * @param pColumnIdentifier The name of the column the List items should be pulled from
	 */
	public StringResultSetList(String pColumnIdentifier) {
		mColumIdentifier = pColumnIdentifier;
	}
	
	@Override
	public List<String> handle(ResultSet pResultSet) throws SQLException {
		List<String> lResults = new ArrayList<>();
		
		while (pResultSet.next()) {
			lResults.add(pResultSet.getString(mColumIdentifier));
		}
		return lResults;
	}

}
