package de.lkrause.EIO.industryPlanner;

import java.util.Collections;
import java.util.Map;

public class Blueprint implements BlueprintInterface {


	private int mMatEfficiency;
	private int mTimeEfficiency;
	private int mRunsLeft;
	private Map<InputMaterial, Integer> mInputMaterials;
	private boolean mIsBPO;
	private long mRunTime;
	private long[] mResearchTimes;
	private long mCopyTime;
	private InputMaterial mOutcomeMaterial;
	private int mOutcomeAmount;
	
	/**
	 * Initialize a Blueprint with given Parameters
	 * @param pME The Material Efficiency of the Blueprint
	 * @param pTE The Time Efficiency of the Blueprint
	 * @param pIsBPO True if the Blueprint is original
	 * @param pInputMaterials Map of all Input Materials and amounts
	 * @param pRunTime Base Time needed per run
	 * @param pResearchTimes Unbonussed research times for each level
	 * @param pCopyTime Base Copy Time per run
	 */
	public Blueprint(int pME, int pTE, boolean pIsBPO, Map<InputMaterial, Integer> pInputMaterials, long pRunTime, long[] pResearchTimes, long pCopyTime) {
		mInputMaterials.putAll(pInputMaterials);
		mMatEfficiency = pME;
		mTimeEfficiency = pTE;
		mIsBPO = pIsBPO;
		mRunTime = pRunTime;
		mCopyTime = pCopyTime;
		if (pResearchTimes.length == 10) {
			mResearchTimes = pResearchTimes;			
		}
	}
	
	/**
	 * Set the Material Efficiency of the blueprint
	 * @param pME The targeted ME
	 * @return True if successfull, False if out of bounds
	 */
	public boolean setME(int pME) {
		if (pME <= 10 && pME >= 0) {
			mMatEfficiency = pME;
			return true;
		}
		return false;
	}

	/**
	 * Set the Time Efficiency of the blueprint
	 * @param pTE The targeted TE
	 * @return True if successfull, False if out of bounds
	 */
	public boolean setTE(int pTE) {
		if (pTE <= 20 && pTE >= 0 && pTE % 2 == 0) {
			mTimeEfficiency = pTE;
			return true;
		}
		return false;
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
