package de.lkrause.eio.collections;

import de.lkrause.eio.exceptions.UnknownElementException;

public class SecStatus {

	public static final int WORMHOLE = 0;
	public static final int NULLSEC = 1;
	public static final int LOWSEC = 2;
	public static final int HIGHSEC = 3;
	
	
	private static double mSecStatus = 1;
	private static double mSecStatusReprocessing = 1;
	
	/**
	 * Hidden Constructor
	 */
	private SecStatus() {}
	
	public static double getSecStatusMultiplier() {
		
		return mSecStatus;
	}
	
	public static double getSecStatusMultiplierReprocessing() {
		return mSecStatusReprocessing;
	}
	
	public static void setSecstatus(int pSecStatus) {
		switch(pSecStatus) {
		case WORMHOLE:
		case NULLSEC:
			mSecStatus = 2.1;
			mSecStatusReprocessing = 1.12;
			break;
		case LOWSEC:
			mSecStatus = 1.9;
			mSecStatusReprocessing = 1.06;
			break;
		case HIGHSEC:
			mSecStatus = 1;
			mSecStatusReprocessing = 1;
			break;
		default:
			throw new UnknownElementException("Unknown Security Status");
		}
	}
}
