package de.lkrause.EIO.industryPlanner;

import java.util.Collections;
import java.util.Map;

public class Reaction implements BlueprintInterface {

	private int mMatEfficiency;
	private int mTimeEfficiency;
	private int mRunsLeft;
	private Map<InputMaterial, Integer> mInputMaterials;
	private boolean mIsBPO;
	private long mRunTime;
	private long[] mResearchTimes = new long[10];
	private long mCopyTime;
	private InputMaterial mOutcomeMaterial;
	private int mOutcomeAmount;
	

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
