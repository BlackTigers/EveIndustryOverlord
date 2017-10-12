package de.lkrause.EIO.industryPlanner;

import java.util.Collections;
import java.util.Map;

import de.lkrause.EIO.industryPlanner.interfaces.BlueprintInterface;
import de.lkrause.EIO.industryPlanner.rawMaterials.InputMaterial;

public class Reaction implements BlueprintInterface {

	private int mRunsLeft;
	private Map<InputMaterial, Integer> mInputMaterials;
	private boolean mIsBPO;
	private long mRunTime;
	private long[] mResearchTimes = new long[10];
	private long mCopyTime;
	private InputMaterial mOutcomeMaterial;
	private int mOutcomeAmount;
	
	public Reaction(int pBPID) {
		// TODO Database Call for blueprint info

	}

	@Override
	public int getME() {
		return 0;
	}

	@Override
	public int getTE() {
		return 0;
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
		return BPTypes.REACTION;
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
