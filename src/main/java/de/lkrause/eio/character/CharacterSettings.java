package de.lkrause.eio.character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lkrause.eio.industryplanner.collections.Materials;
import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;

public class CharacterSettings {

	private static final CharacterSettings INSTANCE = new CharacterSettings();
	
	private byte mReprocessing = 0;
	private byte mReprocessingEfficiency = 0;
	private Map<InputMaterial, Integer> mReprocessingMaterialEfficiencies = new HashMap<>();
	private byte mIndustry = 0;
	private byte mAdvancedIndustry = 0;
	private byte mMassProduction = 0;
	private byte mAdvancedMassProduction = 0;
	private byte mReprocessingImplant = 0;

	
	private CharacterSettings() {
		
	}
	
	public static CharacterSettings getInstance() {
		return INSTANCE;
	}
	
	public void addReprocessingEfficiency(int pInputMaterialID, byte pLevel) {
		if(pLevel >= 0 && pLevel <= 5) {
			mReprocessingMaterialEfficiencies.put(Materials.getInstance().getMaterial(pInputMaterialID), (int) pLevel);
		}
	}
	
	public void setReprocessingLevel(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mReprocessing = pLevel;
		}
	}
	
	public void setReprocessingEfficiencyLevel(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mReprocessingEfficiency = pLevel;
		}
	}
	
	public int getReprocessingLevel() {
		return mReprocessing;
	}
	
	public int getReprocessingEfficiencyLevel() {
		return mReprocessingEfficiency;
	}
	
	public int getReprocessingMaterialEfficiencyLevel(int pInputMaterialID) {
		return mReprocessingMaterialEfficiencies.get(Materials.getInstance().getMaterial(pInputMaterialID));
	}
	
	public void setIndustryLevel(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mIndustry = pLevel;
		}
	}
	
	public void setAdvancedIndustryLevel(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mAdvancedIndustry = pLevel;
		}
	}
	
	public void setMassProduction(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mMassProduction = pLevel;
		}
	}
	
	public void setAdvancedMassProduction(byte pLevel) {
		if (pLevel >= 0 && pLevel <= 5) {
			mAdvancedMassProduction= pLevel;
		}
	}
	
	public void setReprocessingImplant(byte pStrength) {
		if (!(pStrength == 0 || pStrength == 1 || pStrength == 2 || pStrength == 4)) {
			return;
		}
		mReprocessingImplant = pStrength;
	}
	
	public byte getReprocessingImplantMod() {
		return mReprocessingImplant;
	}
	
	public int getIndustryLevel() {
		return mIndustry;
	}
	
	public int getAdvancedIndustryLeve() {
		return mAdvancedIndustry;
	}
	
	public int getMassProduction() {
		return mMassProduction;
	}
	
	public int getAdvancedMassProduction() {
		return mAdvancedMassProduction;
	}
	
	public double getReprocessingRate(List<Integer> pMaterials) {
		
		double lFacilityValue = FacilitySettings.getInstance().getFacilityReprocessing();
		
		int lLevelSum = 0;
		
		for (int lMaterialID : pMaterials) {
			lLevelSum += mReprocessingMaterialEfficiencies.get(Materials.getInstance().getMaterial(lMaterialID));
		}
		double lLevelAverage = ((double) lLevelSum) / pMaterials.size();
		
		double lPlayerBonus = (1 + mReprocessing * 0.03)*(1 + mReprocessingEfficiency * 0.02)*(1 + lLevelAverage * 0.02)*(1 + mReprocessingImplant * 0.01);
		return lFacilityValue * lPlayerBonus;
	}
}
