package de.lkrause.eio.reprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.industryplanner.collections.Types;
import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;

public class ReprocessingCalculatorLogic {

	private static final ReprocessingCalculatorLogic INSTANCE = new ReprocessingCalculatorLogic();
	
	private ReprocessingCalculatorLogic() {
	}
	
	protected static ReprocessingCalculatorLogic getInstance() {
		return INSTANCE;
	}
	
	protected String calculate(String pInput) {
		Map<String, Long> lInputMaterials = ReprocessingCalculatorParser.getInstance().getParsedMaterials(pInput);
		Map<Integer, Long> lOutputMaterials = new HashMap<>();

		double lInputMaterialValue = 0;
		double lOutputMaterialValue = 0;
		
		List<String> lInvalidMaterials = new ArrayList<>();
		
		for (Entry<String, Long> lEntry : lInputMaterials.entrySet()) {
			if (Types.getInstance().materialExists(lEntry.getKey())) {
				InputMaterial lMaterial = Types.getInstance().getMaterial(lEntry.getKey());
				lInputMaterialValue += (lMaterial.getCachedValue() * lEntry.getValue());
				
				for (Entry<Integer, Integer> lRefining : DatabaseConnection.getInstance().getRefiningOutputs(lMaterial.getTypeId()).entrySet()) {
					if (!lOutputMaterials.containsKey(lRefining.getKey())) {
						lOutputMaterials.put(lRefining.getKey(), (long) (lRefining.getValue() * lEntry.getValue()));
					} else {
						lOutputMaterials.put(lRefining.getKey(), lOutputMaterials.get(lRefining.getKey()) + (lRefining.getValue() * lEntry.getValue()));
					}
				}
			} else {
				lInvalidMaterials.add(lEntry.getKey());
			}
		}
		
		String lOutputString = "";
		
		for (Entry<Integer, Long> lEntry : lOutputMaterials.entrySet()) {
			InputMaterial lMaterial = Types.getInstance().getMaterial(lEntry.getKey());
			lOutputMaterialValue += (lMaterial.getCachedValue() * lEntry.getValue());
			lOutputString = lOutputString + lMaterial.getTypeName() + "\t" + lOutputMaterials.get(lMaterial.getTypeId()) + "\n";
		}
		
		return lOutputString;
	}
}
