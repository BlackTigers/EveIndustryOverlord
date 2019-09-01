package de.lkrause.eio.industryplanner;

import java.util.Collections;
import java.util.Map;

import de.lkrause.eio.collections.Activities;
import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.exceptions.BlueprintException;
import de.lkrause.eio.industryplanner.collections.Blueprints;

public class Blueprint {

	private int mTypeID;
	private String mTypeName;
		
	private Map<Integer, Integer> mProductionInputMaterials;
	
	private Map<Integer, Integer> mInventionMaterials;
	private Map<Integer, Integer> mResearchMaterials;
	private Map<Integer, Integer> mCopyMaterials;
	private Map<Integer, Integer> mProductionOutcome;
	
	private long mRunTime;
	private long mResearchLevel;
	private int[] mResearchTimes = {105, 250, 595, 1414, 3360, 8000, 19000, 45255, 107700, 256000};
	private long mCopyTime;
	private boolean mInventable;
		
	/**
	 * Initialize a Blueprint with given Parameters
	 * @param pBPID The Blueprint identifier (eve wise)
	 * @param pIsBPO True if the Blueprint is original
	 */
	public Blueprint(String pBPName) {
		mTypeName = pBPName;
		
		mTypeID = DatabaseConnection.getInstance().getTypeID(pBPName);
		mResearchLevel = DatabaseConnection.getInstance().getBlueprintTime(mTypeID, Activities.RESEARCHMATERIAL) / 105;
		
		mRunTime = Math.round(mResearchLevel * 105 / 0.35);
		mCopyTime = (long) (0.8 * mRunTime);
		
		if (pBPName.contains("Blueprint")) {
			mProductionOutcome = DatabaseConnection.getInstance().getBlueprintProducts(mTypeID, Activities.MANUFACTURING);
			mProductionInputMaterials = DatabaseConnection.getInstance().getBlueprintInput(mTypeID, Activities.MANUFACTURING);
			mInventionMaterials = DatabaseConnection.getInstance().getBlueprintInput(mTypeID, Activities.INVENTION);
			mResearchMaterials = DatabaseConnection.getInstance().getBlueprintInput(mTypeID, Activities.RESEARCHMATERIAL);
			mCopyMaterials = DatabaseConnection.getInstance().getBlueprintInput(mTypeID, Activities.COPY);
			
			mInventable = Blueprints.getInstance().isInventable(mTypeID);
		} else {
			mProductionInputMaterials = DatabaseConnection.getInstance().getBlueprintInput(mTypeID, Activities.REACTIONS);
			mProductionOutcome = DatabaseConnection.getInstance().getBlueprintProducts(mTypeID, Activities.REACTIONS);
		}
	}
	
	public Blueprint(String pTypeName, int pResearchLevel, Map<Integer,Integer> pProductionInputMaterials, boolean pInventable) {
		mTypeName = pTypeName;
		mTypeID = DatabaseConnection.getInstance().getTypeID(pTypeName);
				
		mResearchLevel = pResearchLevel;
		mRunTime = Math.round(mResearchLevel * 105 / 0.35);
		mCopyTime = (long) (0.8 * mRunTime);
		
		mProductionInputMaterials = pProductionInputMaterials;
		mInventable = pInventable;
	}


	public Map<Integer, Integer> getResearchMaterials() {
		return Collections.unmodifiableMap(mResearchMaterials);
	}
	
	public Map<Integer, Integer> getInventionMaterials() {
		return Collections.unmodifiableMap(mInventionMaterials);
	}
	
	public Map<Integer, Integer> getCopyMaterials() {
		return Collections.unmodifiableMap(mCopyMaterials);
	}
	
	public boolean isInventable() {
		return mInventable;
	}
	
	public Map<Integer, Integer> getInputMaterials() {
		return Collections.unmodifiableMap(mProductionInputMaterials);
	}

	public int getType() {
		if (mTypeName.contains("Blueprint")) {
			return BPTypes.BPO;
		}
		return BPTypes.REACTION;
	}

	public long getBaseRunTime() {
		return mRunTime;
	}

	public long getResearchTime(byte pStartLevel, byte pTargetLevel) throws BlueprintException {
		if (pTargetLevel >= 0 && pTargetLevel <= 10) {
			long lResult = 0;
			for (int i = pStartLevel; i < pTargetLevel; i++) {
				lResult += mResearchLevel * mResearchTimes[i];
			}
			return lResult;
		}
		throw new BlueprintException("Target Level ouf of bounds");
	}

	public long getResearchTime(byte pLevel) throws BlueprintException {
		if (pLevel <= 10 && pLevel > 0) {
			return mResearchTimes[pLevel - 1];
		}
		throw new BlueprintException("Level out of bounds, must be between 1 and 10");
	}
	
	public String getName() {
		return mTypeName;
	}
	
	public long getBaseCopyTime() {
		return mCopyTime;
	}

	public Map<Integer, Integer> getProductionOutcome() {
		return Collections.unmodifiableMap(mProductionOutcome);
	}

	public int getTypeID() {
		return mTypeID;
	}
}
