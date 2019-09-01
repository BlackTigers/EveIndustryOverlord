package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;

public class Materials {

	private static final Materials INSTANCE = new Materials();
	
	private Map<Integer, InputMaterial> mMaterials = new HashMap<Integer, InputMaterial>();
	
	private List<Integer> mIDList = new ArrayList<>();
	
	private Materials() {
	
	}
	
	public static Materials getInstance() {
		return INSTANCE;
	}
	
	public InputMaterial getMaterial(int pID) {
		addID(pID);
		return mMaterials.get(pID);
	}
	
	private void addMaterial(InputMaterial pMaterial) {
		
		mMaterials.put(pMaterial.getTypeId(), pMaterial);
	}
	
	public List<Integer> getMaterialIDs() {

		return Collections.unmodifiableList(mIDList);
	}
	
	private void addID(int pID) {
		if (!mIDList.contains(pID)) {
			mIDList.add(pID);
			
			addMaterial(new InputMaterial(pID));
		}
	}
}
