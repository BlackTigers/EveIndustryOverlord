package de.lkrause.eio.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BuildJob {
	
	private String mMaterialName;
	private int mAmount;
	private Map<Integer, Integer> mMineralMap = new HashMap<>();

	public BuildJob(String pMaterial, int pAmount, Map<Integer, Integer> pMinerals) {
		mMaterialName = pMaterial;
		mAmount = pAmount;
		mMineralMap.putAll(pMinerals);
	}
	
	public Map<Integer, Integer> getMinerals() {
		return Collections.unmodifiableMap(mMineralMap);
	}
	
	public int getAmount() {
		return mAmount;
	}
	
	public String getName() {
		return mMaterialName;
	}
}
