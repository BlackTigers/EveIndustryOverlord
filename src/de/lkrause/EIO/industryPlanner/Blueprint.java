package de.lkrause.EIO.industryPlanner;

import java.util.Collections;
import java.util.Map;

import de.lkrause.EIO.exceptions.BlueprintException;
import de.lkrause.EIO.industryPlanner.interfaces.BlueprintInterface;
import de.lkrause.EIO.industryPlanner.rawMaterials.InputMaterial;

public class Blueprint implements BlueprintInterface {


	private int mMatEfficiency;
	private int mTimeEfficiency;
	private int mRunsLeft;
	private Map<InputMaterial, Integer> mInputMaterials;
	private boolean mIsBPO;
	private long mRunTime;
	private long mResearchLevel;
	private long[] mResearchTimes = new long[10];
	private long mCopyTime;
	private InputMaterial mOutcomeMaterial;
	private int mOutcomeAmount;
	
	/**
	 * Initialize a Blueprint with given Parameters
	 * @param pBPID The Blueprint identifier (eve wise)
	 * @param pIsBPO True if the Blueprint is original
	 */
	public Blueprint(int pBPID, boolean pIsBPO) {
		mIsBPO = pIsBPO;
		// TODO Database Call for blueprint info
		mRunTime = 0; // TODO Get from Database
		mCopyTime = (long) (0.8 * mRunTime);
		mResearchLevel = 0; // TODO Get from Database
		mResearchTimes[0] = mResearchLevel * 105;
		mResearchTimes[1] = mResearchLevel * 250;
		mResearchTimes[2] = mResearchLevel * 595;
		mResearchTimes[3] = mResearchLevel * 1414;
		mResearchTimes[4] = mResearchLevel * 3360;
		mResearchTimes[5] = mResearchLevel * 8000;
		mResearchTimes[6] = mResearchLevel * 19000;
		mResearchTimes[7] = mResearchLevel * 45255;
		mResearchTimes[8] = mResearchLevel * 107700;
		mResearchTimes[9] = mResearchLevel * 256000;
	}
	
	/**
	 * Set the Material Efficiency of the blueprint
	 * @param pME The targeted ME
	 */
	public void setME(int pME) throws BlueprintException {
		if (pME <= 10 && pME >= 0) {
			mMatEfficiency = pME;
		}
		throw new BlueprintException("Level out of bounds, must be between 1 and 10");

	}

	/**
	 * Set the Time Efficiency of the blueprint
	 * @param pTE The targeted TE
	 */
	public void setTE(int pTE) throws BlueprintException {
		if (pTE <= 20 && pTE >= 0 && pTE % 2 == 0) {
			mTimeEfficiency = pTE;
		}
		throw new BlueprintException("Level out of bounds, must be between 1 and 10");
	}

	@Override
	public int getME() {
		return mMatEfficiency;
	}

	@Override
	public int getTE() {
		return mTimeEfficiency;
	}

	@Override
	public int getRunsLeft() {
		return mRunsLeft;
	}

	@Override
	public Map<InputMaterial, Integer> getInputMaterials() {
		return Collections.unmodifiableMap(mInputMaterials);
	}

	@Override
	public void run(int pRuns) {
		if (pRuns <= mRunsLeft) {
			if (!mIsBPO) {
				mRunsLeft -= pRuns;
			}
			// TODO Add Starting a new run to simulation (Materials + Time)
		}
		
	}

	@Override
	public int getType() {
		if (mIsBPO) {
			return BPTypes.BPO;
		}
		return BPTypes.BPC;
	}

	@Override
	public long getBaseRunTime() {
		return mRunTime;
	}

	@Override
	public long[] getBaseResearchTimes() {
		return mResearchTimes;
	}

	public long getResearchTime(byte pLevel) throws BlueprintException {
		if (pLevel <= 10 && pLevel > 0) {
			return mResearchTimes[pLevel - 1];
		}
		throw new BlueprintException("Level out of bounds, must be between 1 and 10");
	}
	
	@Override
	public long getBaseCopyTime() {
		return mCopyTime;
	}

	@Override
	public int getOutcomeAmount() {
		return mOutcomeAmount;
	}

	@Override
	public InputMaterial getOutcome() {
		return mOutcomeMaterial;
	}


}
