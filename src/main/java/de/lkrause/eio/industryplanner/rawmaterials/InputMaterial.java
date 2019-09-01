package de.lkrause.eio.industryplanner.rawmaterials;

import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.esi.util.ESIUtils;

public class InputMaterial {

	private int mTypeID;
	private String mTypeName;
	private double mValue = -1;
	private double mVolume;
	
	public InputMaterial(int pTypeID) {
		mTypeID = pTypeID;
		
		mTypeName = DatabaseConnection.getInstance().getTypeName(mTypeID);
		mVolume = DatabaseConnection.getInstance().getVolume(mTypeID);
	}
	
	public InputMaterial(String pTypeName) {
		
		mTypeID = DatabaseConnection.getInstance().getTypeID(pTypeName);
		
		mTypeName = pTypeName;
		mVolume = DatabaseConnection.getInstance().getVolume(mTypeID);
	}
	
	public InputMaterial(int pTypeID, String pTypeName) {
		mTypeID = pTypeID;
		
		mTypeName = pTypeName;
	}
		
	public double getCachedValue() {
		if (mValue == -1) {
			mValue = ESIUtils.getValue(mTypeID);
		}
		
		return mValue;
	}
		
	public String getTypeName() {
		return mTypeName;
	}
	
	public int getTypeId() {
		return mTypeID;
	}
	
	public double getVolume() {
		return mVolume;
	}
	
	public InputMaterial duplicate() {
		return new InputMaterial(mTypeID);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InputMaterial)) {
			return false;
		}
		return ((InputMaterial) obj).getTypeId() == mTypeID;
	}
}
