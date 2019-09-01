package de.lkrause.eio.character;

public class FacilitySettings {

	private static final FacilitySettings INSTANCE = new FacilitySettings();
	
	public static final double NULL_WORMHOLE = 1.12;
	public static final double LOWSEC = 1.06;
	public static final double HIGHSEC = 1.0;
	public static final double RIG_T1 = 0.02;
	public static final double RIG_T2 = 0.04;
	
	private double mReprocessingRate = 0;
	
	private FacilitySettings() {
		
	}
	
	public static FacilitySettings getInstance() {
		return INSTANCE;
	}
	
	public void setFacilityReprocessingSettings(double pBase, double pRig, double pSecurity) {
		
		mReprocessingRate = (pBase + pRig) * pSecurity;
	}
	
	public double getFacilityReprocessing() {
		
		return mReprocessingRate;
	}
	
	
}
