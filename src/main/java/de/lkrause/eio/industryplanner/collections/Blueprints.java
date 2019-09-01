package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lkrause.eio.industryplanner.Blueprint;

public class Blueprints {

	private static final Blueprints INSTANCE = new Blueprints();

	private Map<String, Blueprint> mAvailableBlueprints = new HashMap<>();

	private List<Integer> mInventables = new ArrayList<>();

	private List<String> mBlueprintNames = new ArrayList<>();
	
	private Map<String, String> mNameMap = new HashMap<>();

	private Blueprints() {

	}

	public static Blueprints getInstance() {
		return INSTANCE;
	}

	public Blueprint getBlueprint(String pName) {
		
		String lName = mNameMap.get(pName.toLowerCase());
		
		if (!mAvailableBlueprints.containsKey(lName)) {
			addBlueprint(lName);
		}

		return mAvailableBlueprints.get(lName);
	}
	
	public String getBlueprintName(String pName) {
		return mNameMap.get(pName.toLowerCase());
	}

	private void addBlueprint(String pName) {

		Blueprint lBlueprint = new Blueprint(pName);
		mAvailableBlueprints.put(pName, lBlueprint);
	}

	public void setInventables(List<Integer> pInventables) {
		mInventables.addAll(pInventables);
	}

	public void setBlueprintNames(List<String> pNames) {
		mBlueprintNames.addAll(pNames);
		
		for (String lName : pNames) {
			mNameMap.put(lName.toLowerCase(), lName);
		}
	}

	public boolean isInventable(int pBPId) {
		return mInventables.contains(pBPId);
	}

	public List<String> getNameList() {
		return Collections.unmodifiableList(mBlueprintNames);
	}
}
