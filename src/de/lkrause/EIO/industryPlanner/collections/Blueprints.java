package de.lkrause.EIO.industryPlanner.collections;

import java.util.HashMap;
import java.util.Map;

import de.lkrause.EIO.industryPlanner.Blueprint;

public class Blueprints {

	private static final Blueprints INSTANCE = new Blueprints();
	
	private Map<Integer, Blueprint> mAvailableBlueprints = new HashMap<Integer, Blueprint>();
	
	private Blueprints() {
	
	}
	
	public static Blueprints getInstance() {
		return INSTANCE;
	}
	
	public Blueprint getMaterial(int pID) {
		if (!mAvailableBlueprints.containsKey(pID)) {
			// TODO Database Call
		}
		return mAvailableBlueprints.get(pID);
	}
}
