package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.exceptions.InvalidTypeException;
import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;

public class Types {

	private static final Types INSTANCE = new Types();

	private Map<Integer, InputMaterial> mAvailableTypesId = new HashMap<>();
	private Map<String, InputMaterial> mAvailableTypesName = new HashMap<>();
	private List<String> mValidTypeNames = new ArrayList<>();

	private Types() {

	}

	public static Types getInstance() {
		return INSTANCE;
	}

	public InputMaterial getMaterial(int pId) {
		
		if (!mAvailableTypesId.containsKey(pId)) {
			addMaterial(pId);
		}

		return mAvailableTypesId.get(pId);
	}
	
	public void initializeTypeNames() {
		for (String lItemName : DatabaseConnection.getInstance().getAllItemNames()) {
			mValidTypeNames.add(lItemName.toLowerCase());
		}
	}
	
	public boolean materialExists(String pTypeName) {
		return mValidTypeNames.contains(pTypeName.toLowerCase());
	}
	
	public InputMaterial getMaterial(String pTypeName) throws InvalidTypeException {
		
		// TODO validate
		
		if (!mAvailableTypesName.containsKey(pTypeName.toLowerCase())) {
//			if (!mValidTypeNames.contains(pTypeName.toLowerCase())) {
//				throw new InvalidTypeException(pTypeName);
//			}
			addMaterial(pTypeName);
		}
		
		return mAvailableTypesName.get(pTypeName.toLowerCase());
	}
	
	private void addMaterial(int pId) {

		InputMaterial lMaterial = new InputMaterial(pId);
		mAvailableTypesId.put(pId, lMaterial);
		mAvailableTypesName.put(lMaterial.getTypeName().toLowerCase(), lMaterial);
	}
	
	private void addMaterial(String pName) {

		InputMaterial lMaterial = new InputMaterial(pName);
		mAvailableTypesId.put(lMaterial.getTypeId(), lMaterial);
		mAvailableTypesName.put(lMaterial.getTypeName().toLowerCase(), lMaterial);
	}
}
