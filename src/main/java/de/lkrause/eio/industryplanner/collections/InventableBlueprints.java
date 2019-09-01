package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lkrause.eio.industryplanner.Blueprint;

public class InventableBlueprints {

	private static List<Integer> mInventableBlueprints = new ArrayList<Integer>();
	
	public static void addInventable(int pID) {
		mInventableBlueprints.add(pID);
	}
	
	public static boolean isInventable(int pID) {
		
		return mInventableBlueprints.contains(pID);
	}
}
