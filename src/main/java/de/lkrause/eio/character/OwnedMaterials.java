package de.lkrause.eio.character;

import java.util.HashMap;
import java.util.Map;

public class OwnedMaterials {

	private static Map<Integer, Integer> mAmounts = new HashMap<>();
	
	private OwnedMaterials() {
	}
	
	public static int getOwnedMaterialAmount(int pTypeId) {
		
		if (mAmounts.containsKey(pTypeId)) {
			return mAmounts.get(pTypeId);
		}
		return 0;
	}
	
	public static void setOwnedMaterialAmount(int pTypeId, int pAmount) {
		mAmounts.put(pTypeId, pAmount);
	}
}
