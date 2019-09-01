package de.lkrause.eio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.industryplanner.collections.Types;
import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;
import de.lkrause.eio.industryplanner.rawmaterials.Mineral;

public class OreType {

	/** Should the Ore be used or not? **/

	private int mOrePercent;
	private int mOreId;
	private String mOreName;
	private String mOreType;
	private Map<Integer, String> mOreNameBonusses = new HashMap<>();
	private Map<Integer, Integer> mBaseOreRefining = new HashMap<>();
	private Map<Integer, Integer> m5OreRefining = new HashMap<>();
	private Map<Integer, Integer> m10OreRefining = new HashMap<>();

	private Map<Integer, Integer> mOptimizedRefining = new HashMap<>();
	
	public OreType(String pOreTypeName) {
		// TODO Correct the better ores
		
		switch (pOreTypeName) {
		case "Arkonor":
			setOre("Arkonor", "Crimson Arkonor", "Prime Arkonor", "Flawless Arkonor");
			break;
		case "Bistot":
			setOre("Bistot", "Triclinic Bistot", "Monoclinic Bistot", "Cubic Bistot");
			break;
		case "Crokite":
			setOre("Crokite", "Sharp Crokite", "Crystalline Crokite", "Pellucid Crokite");
			break;
		case "Dark Ochre":
			setOre("Dark Ochre", "Onyx Ochre", "Obsidian Ochre", "Jet Ochre");
			break;
		case "Gneiss":
			setOre("Gneiss", "Iridescent Gneiss", "Prismatic Gneiss", "Brilliant Gneiss");
			break;
		case "Hedbergite":
			setOre("Hedbergite", "Vitric Hedbergite", "Glazed Hedbergite", "Lustrous Hedbergite");
			break;
		case "Hemorphite":
			setOre("Hemorphite", "Vivid Hemorphite", "Radiant Hemorphite", "Scintillating Hemorphite");
			break;
		case "Jaspet":
			setOre("Jaspet", "Pure Jaspet", "Pristine Jaspet", "Immaculate Jaspet");
			break;
		case "Kernite":
			setOre("Kernite", "Luminous Kernite", "Fiery Kernite", "Resplendant Kernite");
			break;
		case "Mercoxit":
			setOre("Mercoxit", "Magma Mercoxit", "Vitreous Mercoxit", "");
			break;
		case "Omber":
			setOre("Omber", "Silvery Omber", "Golden Omber", "Platinoid Omber");
			break;
		case "Plagioclase":
			setOre("Plagioclase", "Azure Plagioclase", "Rich Plagioclase", "Sparkling Plagioclase");
			break;
		case "Pyroxeres":
			setOre("Pyroxeres", "Solid Pyroxeres", "Viscous Pyroxeres", "Opulent Pyroxeres");
			break;
		case "Scordite":
			setOre("Scordite", "Condensed Scordite", "Massive Scordite", "Glossy Scordite");
			break;
		case "Spodumain":
			setOre("Spodumain", "Bright Spodumain", "Gleaming Spodumain", "Dazzling Spodumain");
			break;
		case "Veldspar":
			setOre("Veldspar", "Concentrated Veldspar", "Dense Veldspar", "Stable Veldspar");
			break;
		default:
			break;
		}
		
		calculateId();
	}
	
	private void setOre(String pName0, String pName5, String pName10, String pName15) {
		String lPrefix = "Compressed ";
		mOreType = pName0;
		mOreNameBonusses.put(0, lPrefix + pName0);
		mOreNameBonusses.put(5, lPrefix + pName5);
		mOreNameBonusses.put(10, lPrefix + pName10);
		if (!pName15.equals("")) {
//			mOreNameBonusses.put(15, "Compressed " + pName15);
		}
		List<Integer> lMinerals = new ArrayList<>();
		lMinerals.add(Mineral.TRITANIUM);
		lMinerals.add(Mineral.PYERITE);
		lMinerals.add(Mineral.MEXALLON);
		lMinerals.add(Mineral.ISOGEN);
		lMinerals.add(Mineral.NOCXIUM);
		lMinerals.add(Mineral.ZYDRINE);
		lMinerals.add(Mineral.MEGACYTE);
		lMinerals.add(Mineral.MORPHITE);
		
		for (Integer lMineral : lMinerals) {
			mBaseOreRefining.put(lMineral, 0);
			m5OreRefining.put(lMineral, 0);
			m10OreRefining.put(lMineral, 0);
		}
		
		Map<Integer, Integer> lOutputs = new HashMap<>();
		
		lOutputs.putAll(DatabaseConnection.getInstance().getRefiningOutputs(Types.getInstance().getMaterial(mOreNameBonusses.get(0)).getTypeId()));
		for (Entry<Integer, Integer> lMineral : lOutputs.entrySet()) {
			mBaseOreRefining.put(lMineral.getKey(), lMineral.getValue());
		}
		lOutputs.putAll(DatabaseConnection.getInstance().getRefiningOutputs(Types.getInstance().getMaterial(mOreNameBonusses.get(5)).getTypeId()));
		for (Entry<Integer, Integer> lMineral : lOutputs.entrySet()) {
			m5OreRefining.put(lMineral.getKey(), lMineral.getValue());
		}
		lOutputs.putAll(DatabaseConnection.getInstance().getRefiningOutputs(Types.getInstance().getMaterial(mOreNameBonusses.get(10)).getTypeId()));
		for (Entry<Integer, Integer> lMineral : lOutputs.entrySet()) {
			m10OreRefining.put(lMineral.getKey(), lMineral.getValue());
		}
	}
	
	public void calculateId() {
		int lMinPercent = 0;
		double lMinPricePerValue = Double.MAX_VALUE;
		List<Integer> lPercents = new ArrayList<>();
		lPercents.addAll(mOreNameBonusses.keySet());
		for (int lPercent : lPercents) {
			InputMaterial lMaterial = Types.getInstance().getMaterial(mOreNameBonusses.get(lPercent));
			
			double lValueModifier = (100.0 + lPercent) / 100.0;
			
			// Only change if the current minimum per value is undercutted
			if (lMaterial.getCachedValue() < (lMinPricePerValue * lValueModifier)) {
				if (lMaterial.getCachedValue() > 0) {
					lMinPricePerValue = lMaterial.getCachedValue() / lValueModifier;
					lMinPercent = lPercent;
				}
			}
		}
		mOreId = Types.getInstance().getMaterial(mOreNameBonusses.get(lMinPercent)).getTypeId();
		mOreName = Types.getInstance().getMaterial(mOreNameBonusses.get(lMinPercent)).getTypeName();
		mOrePercent = lMinPercent;
		
		switch (mOrePercent) {
		case 0:
			mOptimizedRefining.putAll(mBaseOreRefining);
			break;
		case 5:
			mOptimizedRefining.putAll(m5OreRefining);
			break;
		case 10:
			mOptimizedRefining.putAll(m10OreRefining);
			break;
		default:
			break;
		}
	}
	
	public int getBestId() {
		
		return mOreId;
	}
	
	public String getBestName() {
		
		return mOreName;
	}
	
	public int getReprocessedAmount(int pMineral) {
		return (int) Math.floor(mOptimizedRefining.get(pMineral) * ReprocessingModel.getInstance().getReprocessingRate(mOreType));
	}
	
	public Map<Integer, Integer> getReprocessingMap() {
		return Collections.unmodifiableMap(mOptimizedRefining);
	}
	
	@Override
	public String toString() {
		String[] lOreNames = new String[3];
		mOreNameBonusses.values().toArray(lOreNames);
		String lResult = "";
		for (int i = 0; i < 3; i++) {
			lResult = lResult + lOreNames[i] + ": ";
			List<Integer> lMinerals = new ArrayList<>();
			lMinerals.addAll(mBaseOreRefining.values());
			for (int lValue : lMinerals) {
				lResult = lResult + ((int) (Math.ceil(lValue * (1 + i * 0.05)))) + ", ";
			}
			lResult = lResult.substring(0, lResult.length() - 2);
			lResult += "; ";
		}
		lResult = lResult.substring(0, lResult.length() - 2);
		
		return lResult;
	}
}
