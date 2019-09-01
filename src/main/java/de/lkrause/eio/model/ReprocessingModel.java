package de.lkrause.eio.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ReprocessingModel {

	// TODO Verify Reprocessing calcs are right
	
	private static final ReprocessingModel INSTANCE = new ReprocessingModel();
	
	private Map<String, Double> mReprocessing = new HashMap<>();
	
	Structure mRefinery = null;
	
	private ReprocessingModel() {
	}
	
	public static ReprocessingModel getInstance() {
		
		return INSTANCE;
	}
	
	public void setReprocessingLevels(int pReprocessing, int pReproEfficiency, Map<String, Integer> pReprocessingLevels, int pImplant) {
		if (mRefinery == null) {
			mRefinery = new Structure("Athanor");
			mRefinery.setRefineryRig(Structure.TECH2);
		}
		for (Entry<String, Integer> lOre : pReprocessingLevels.entrySet()) {
			mReprocessing.put(lOre.getKey(), (1 + pReprocessing * 0.03) * (1 + pReproEfficiency * 0.02) * (1 + lOre.getValue() * 0.02) * mRefinery.getReprocesing() * (1 + pImplant * 0.01));
		}
	}
	
	public double getReprocessingRate(String pOre) {
		
		return mReprocessing.get(pOre);
	}
	
	public void setStructure(Structure pStructure) {
		mRefinery = pStructure;
	}
	
	public void setReprocessingRefinery(String pRefinery, String pRig) {
		mRefinery = new Structure(pRefinery);
		mRefinery.setRefineryRig(pRig);
	}
}
