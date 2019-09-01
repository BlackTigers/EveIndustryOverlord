package de.lkrause.eio.model;

import de.lkrause.eio.exceptions.HaulingException;

public class HaulingModel {

	private static final HaulingModel INSTANCE = new HaulingModel();
	
	private int mIskPerM3;
	private double mPercent;
	private double mMultiplicator;
	
	private HaulingModel() {
	}
	
	public static HaulingModel getInstance() {
		
		return INSTANCE;
	}
	
	public void setHaulingCost(int pIskPerM3, double pPercent) {
		mIskPerM3 = pIskPerM3;
		mPercent = pPercent;
		mMultiplicator = mPercent / 100.0;
	}
	
	public double getHaulingCost(double pIskValue, double pVolume) throws HaulingException {
		
		
		if (pVolume > 320000) {
			throw new HaulingException("Warning, your cargo can't fit into a Jump Freighter.");
		}
		
		double lHaulingCost = pVolume * mIskPerM3 + pIskValue * mMultiplicator;
		
		return lHaulingCost;
	}
	
}
