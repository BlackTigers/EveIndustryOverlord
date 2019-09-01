package de.lkrause.eio.model;

import de.lkrause.eio.collections.SecStatus;

public class Structure {

	public static final String TECH1 = "Tech 1";
	public static final String TECH2 = "Tech 2";
	public static final String NO_RIG = "No Rig";
	
	private String mStructureName = "";
	private double mBonus_Reprocessing = 0;
	private double mBonus_ManufacturingTime = 0;
	private double mBonus_ManufacturingMaterial = 0;
	private double mBonus_ScienceTime = 0;
	private double mBonus_ScienceCost = 0;
	
	private double mBonus_ManufacturingMaterial_Rigged = 0;
	private double mBonus_ManufacturingTime_Rigged = 0;
	private double mBonus_Reprocessing_Rigged = 0;
	
	private double mBonus_ReactionTime = 0;
	
	private boolean mCaps;
	private boolean mSupers;
	private boolean mRefinery;
	
	// TODO ADD POS
	
	public Structure(String pName) {
		mStructureName = pName;
		switch (pName) {
		case "Astrahus":
			mCaps = false;
			mSupers = false;
			mRefinery = false;
			break;
		case "Fortizar":
			mCaps = true;
			mSupers = false;
			mRefinery = false;
			break;
		case "Keepstar":
			mCaps = true;
			mSupers = true;
			mRefinery = false;
			break;
		case "Raitaru":
			mBonus_ManufacturingMaterial = 0.01;
			mBonus_ManufacturingTime = 0.15;
			mBonus_ScienceTime = 0.15;
			mBonus_ScienceCost = 0.03;
			mCaps = false;
			mSupers = false;
			mRefinery = false;
			break;
		case "Azbel":
			mBonus_ManufacturingMaterial = 0.01;
			mBonus_ManufacturingTime = 0.2;
			mBonus_ScienceTime = 0.2;
			mBonus_ScienceCost = 0.04;
			mCaps = true;
			mSupers = false;
			mRefinery = false;
			break;
		case "Sotiyo":
			mBonus_ManufacturingMaterial = 0.01;
			mBonus_ManufacturingTime = 0.3;
			mBonus_ScienceTime = 0.3;
			mBonus_ScienceCost = 0.05;
			mCaps = true;
			mSupers = true;
			mRefinery = false;
			break;

		case "Athanor":
			mBonus_Reprocessing = 0.02;
			mBonus_ReactionTime = 0;
			mCaps = false;
			mSupers = false;
			mRefinery = true;
			break;
		case "Tatara":
			mBonus_Reprocessing = 0.04;
			mBonus_ReactionTime = 0.25;
			mCaps = false;
			mSupers = false;
			mRefinery = true;
			break;
		default:
			break;
		}
		
		mBonus_Reprocessing_Rigged = 0.5 * (1+ mBonus_Reprocessing);
		mBonus_ManufacturingMaterial_Rigged = mBonus_ManufacturingMaterial;
		mBonus_ManufacturingTime_Rigged = mBonus_ManufacturingTime;
	}
	
	public String getName() {
		return mStructureName;
	}
	
	public void setRigs(String pME, String pTE) {
		switch (pME) {
		case NO_RIG:
			mBonus_ManufacturingMaterial_Rigged = mBonus_ManufacturingMaterial;
			break;
		case TECH1:
			mBonus_ManufacturingMaterial_Rigged = mBonus_ManufacturingMaterial + (0.02 * SecStatus.getSecStatusMultiplier());
			break;
		case TECH2:
			mBonus_ManufacturingMaterial_Rigged = mBonus_ManufacturingMaterial + (0.024 * SecStatus.getSecStatusMultiplier());
			break;
		default:
			throw new NullPointerException("Rig doesn't exist");
		}
		
		switch (pTE) {
		case NO_RIG:
			mBonus_ManufacturingTime_Rigged = mBonus_ManufacturingTime;
			break;
		case TECH1:
			mBonus_ManufacturingTime_Rigged = mBonus_ManufacturingTime + (0.20 * SecStatus.getSecStatusMultiplier());
			break;
		case TECH2:
			mBonus_ManufacturingTime_Rigged = mBonus_ManufacturingTime + (0.24 * SecStatus.getSecStatusMultiplier());
			break;
		default:
			throw new NullPointerException("Rig doesn't exist");
		}
	}
	
	public void setRefineryRig(String pRig) {
		switch (pRig) {
		case NO_RIG:
			mBonus_Reprocessing_Rigged = 0.5 * (1+ mBonus_Reprocessing);
			break;
		case TECH1:
			mBonus_Reprocessing_Rigged = 0.51 * (1+ mBonus_Reprocessing) * SecStatus.getSecStatusMultiplierReprocessing();
			break;
		case TECH2:
			mBonus_Reprocessing_Rigged = 0.53 * (1+mBonus_Reprocessing) * SecStatus.getSecStatusMultiplierReprocessing();
			break;
		default:
			throw new NullPointerException("Rig doesn't exist");
		}
	}
	
	public double getReprocesing() {
		return mBonus_Reprocessing_Rigged;
	}
	
	public double getME() {
		return mBonus_ManufacturingMaterial_Rigged;
	}
	
	public double getTE() {
		return mBonus_ManufacturingTime_Rigged;
	}
}
