package de.lkrause.EIO.industryPlanner.rawMaterials;

import de.lkrause.EIO.exceptions.BlueprintException;

public class InputMaterial {

	private int mTypeID;
	private String mTypeName;
	private String mTypeGroup;
	private int mCachedValue;
	private boolean mValueIsCached;
	private boolean mCreatedByBlueprint;
	private int mBlueprintID;
	
	public int getBlueprintID() throws BlueprintException {
		if (mCreatedByBlueprint) {
			return mBlueprintID;
		}
		throw new BlueprintException("Item can't be created by blueprint");
	}
	
	public boolean isBuildable() {
		return mCreatedByBlueprint;
	}
	
	public int getCachedValue() {
		if (!mValueIsCached) {
			// TODO API Call
		}
		return mCachedValue;
	}
	
	public String getGroup() {
		return mTypeGroup;
	}
	
	public String getTypeName() {
		return mTypeName;
	}
	
	public int getTypeId() {
		return mTypeID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InputMaterial)) {
			return false;
		}
		return ((InputMaterial) obj).getTypeId() == mTypeID;
	}
}
