package de.lkrause.EIO.industryPlanner.collections;

import java.util.HashMap;
import java.util.Map;

import de.lkrause.EIO.industryPlanner.Blueprint;
import de.lkrause.EIO.industryPlanner.Reaction;

public class Reactions {

	private static final Reactions INSTANCE = new Reactions();
	
	private Map<Integer, Reaction> mAvailableBlueprints = new HashMap<Integer, Reaction>();
	
	private Reactions() {
	
	}
	
	public static Reactions getInstance() {
		return INSTANCE;
	}
	
	public Reaction getMaterial(int pID) {
		if (!mAvailableBlueprints.containsKey(pID)) {
			// TODO Database Call
		}
		return mAvailableBlueprints.get(pID);
	}
}
