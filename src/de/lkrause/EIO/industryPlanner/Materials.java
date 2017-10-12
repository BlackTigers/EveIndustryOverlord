package de.lkrause.EIO.industryPlanner;

import java.util.HashMap;
import java.util.Map;

public class Materials {

	private static final Materials INSTANCE = new Materials();
	
	private Map<Integer, InputMaterial> mMaterials = new HashMap<Integer, InputMaterial>();
	
	private Materials() {
	
	}
	
	public static Materials getInstance() {
		return INSTANCE;
	}
	
	public InputMaterial getMaterial(int pID) {
		if (!mMaterials.containsKey(pID)) {
			// TODO Database Call
		}
		return mMaterials.get(pID);
	}
}
