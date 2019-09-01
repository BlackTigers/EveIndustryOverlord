package de.lkrause.eio.industryplanner;

import java.util.Collections;
import java.util.Map;

import de.lkrause.eio.collections.Activities;
import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.industryplanner.interfaces.BlueprintInterface;

public class ReactionFormula {

	
	private int mTypeID;
	private String mTypeName;
	
	private Map<Integer, Integer> mProductionInputMaterials;
	
	private Map<Integer, Integer> mProductionOutcome;
	
	private long mRunTime;
	private long mResearchLevel;
	
	/**
	 * Initialize a Blueprint with given Parameters
	 * @param pBPID The Blueprint identifier (eve wise)
	 * @param pIsBPO True if the Blueprint is original
	 */
	public ReactionFormula(String pReactionName) {
		mTypeName = pReactionName;
		
		mTypeID = DatabaseConnection.getInstance().getTypeID(mTypeName);
				
		mRunTime = Math.round(mResearchLevel * 105 / 0.35);
		
		mProductionInputMaterials = DatabaseConnection.getInstance().getReactionInputs(mTypeID);
		
		mProductionOutcome = DatabaseConnection.getInstance().getReactionProducts(mTypeID);
		
//		System.out.println("TypeID: " + mTypeID + " Run Time: " + mRunTime + " Copy Time: " + mCopyTime);
	}
	
	public Map<Integer, Integer> getInputMaterials() {

		return Collections.unmodifiableMap(mProductionInputMaterials);
	}

	public int getTypeID() {
		return mTypeID;
	}

	public long getBaseRunTime() {
		return mRunTime;
	}
	
	public Map<Integer, Integer> getProductionOutcome() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableMap(mProductionOutcome);
	}
}
