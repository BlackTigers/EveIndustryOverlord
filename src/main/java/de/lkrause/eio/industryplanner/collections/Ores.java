package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quantego.clp.CLP;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

import de.lkrause.eio.industryplanner.rawmaterials.Mineral;
import de.lkrause.eio.model.OreType;
import de.lkrause.eio.settings.ActiveOreTypes;

public class Ores {

	public static final String ARKONOR = "Arkonor";
	public static final String BISTOT = "Bistot";
	public static final String CROKITE = "Crokite";
	public static final String OCHRE = "Dark Ochre";
	public static final String GNEISS = "Gneiss";
	public static final String HEDBERGITE = "Hedbergite";
	public static final String HEMORPHITE = "Hemorphite";
	public static final String JASPET = "Jaspet";
	public static final String KERNITE = "Kernite";
	public static final String MERCOXIT = "Mercoxit";
	public static final String OMBER = "Omber";
	public static final String PLAGIOCLASE = "Plagioclase";
	public static final String PYROXERES = "Pyroxeres";
	public static final String SCORDITE = "Scordite";
	public static final String SPODUMAIN = "Spodumain";
	public static final String VELDSPAR = "Veldspar";
	
	private static final Ores INSTANCE = new Ores();

	private Map<String, OreType> mOres = new HashMap<>();

	List<Integer> mCompressedOresAvailable = new ArrayList<>();

	Map<String, int[]> mOreRead = new HashMap<>();

	private Ores() {
			mOres.put(ARKONOR, new OreType(ARKONOR));
			mOres.put(BISTOT, new OreType(BISTOT));
			mOres.put(CROKITE, new OreType(CROKITE));
			mOres.put(OCHRE, new OreType(OCHRE));
			mOres.put(GNEISS, new OreType(GNEISS));
			mOres.put(HEDBERGITE, new OreType(HEDBERGITE));
			mOres.put(HEMORPHITE, new OreType(HEMORPHITE));
			mOres.put(JASPET, new OreType(JASPET));
			mOres.put(KERNITE, new OreType(KERNITE));
			mOres.put(MERCOXIT, new OreType(MERCOXIT));
			mOres.put(OMBER, new OreType(OMBER));
			mOres.put(PLAGIOCLASE, new OreType(PLAGIOCLASE));
			mOres.put(PYROXERES, new OreType(PYROXERES));
			mOres.put(SCORDITE, new OreType(SCORDITE));
			mOres.put(SPODUMAIN, new OreType(SPODUMAIN));
			mOres.put(VELDSPAR, new OreType(VELDSPAR));

	}

	public Map<Integer, Long> calculateOreAmounts(Map<Integer, Long> pInputRequirements) {


		Map<Integer, Long> lWorkMap = new HashMap<>();
		
		List<String> lActiveOres = ActiveOreTypes.getInstance().getActiveOres();
		lWorkMap.putAll(pInputRequirements);

		// Create a list of all Minerals
		List<Integer> lMinerals = new ArrayList<>();
		lMinerals.add(Mineral.TRITANIUM);
		lMinerals.add(Mineral.PYERITE);
		lMinerals.add(Mineral.MEXALLON);
		lMinerals.add(Mineral.NOCXIUM);
		lMinerals.add(Mineral.ISOGEN);
		lMinerals.add(Mineral.ZYDRINE);
		lMinerals.add(Mineral.MEGACYTE);
		
		
		// LP Solving ----------------------------
		CLP lModel = new CLP();
		
		Map<String, CLPVariable> lCLPVars = new HashMap<>();
		
		for (String lOre : lActiveOres) {
			lCLPVars.put(lOre, lModel.addVariable().lb(0));
		}
		
		// Create Mineral Constraints
		for (int lMineral : lMinerals) {

			long lMineralAmount = 0;
			if (pInputRequirements.containsKey(lMineral)) {
				lMineralAmount = pInputRequirements.get(lMineral);
			}
			CLPExpression lExpression = lModel.createExpression();
			
			for (String lOre : lActiveOres) {
				lExpression = lExpression.add(mOres.get(lOre).getReprocessedAmount(lMineral), lCLPVars.get(lOre));
			}
			
			lExpression.geq(lMineralAmount);
		}

		// Create Objective (Ore * Price)
		CLPExpression lObjective = lModel.createExpression();
		for (String lOre : lActiveOres) {
			lObjective = lObjective.add(lCLPVars.get(lOre), Materials.getInstance().getMaterial(mOres.get(lOre).getBestId()).getCachedValue());
		}
		lObjective.asObjective();
		
		// Solve LP
		lModel.minimization();
		lModel.solve();

		lModel.printModel();
		
		System.out.println(Materials.getInstance().getMaterial(mOres.get("Arkonor").getBestId()).getCachedValue());
		
		// Remove Minerals from the Map
		for (int lMineral : lMinerals) {
			lWorkMap.remove(lMineral);
		}

		// Put all Ore Results
		for (String lOre : lActiveOres) {
			
			lWorkMap.put(mOres.get(lOre).getBestId(), (long) Math.ceil(lCLPVars.get(lOre).getSolution()));
		}
		
		List<Integer> lKeys = new ArrayList<>();
		lKeys.addAll(lWorkMap.keySet());

		for (int lKey : lKeys) {
			if (lWorkMap.get(lKey) == 0) {
				lWorkMap.remove(lWorkMap.remove(lKey));
			}
		}
		return lWorkMap;
	}


	public static Ores getInstance() {
		return INSTANCE;
	}

	public void outputBest() {
		for (OreType lOre : mOres.values()) {
			System.out.println(lOre.getBestName());
		}
	}

	public OreType getOre(String pName) {
		if (mOres.containsKey(pName)) {
			return mOres.get(pName);
		}
		return null;
	}

	public List<String> getOreList() {
		
		List<String> lReturnList = new ArrayList<>();
		lReturnList.addAll(mOres.keySet());
		return lReturnList;
	}
}
