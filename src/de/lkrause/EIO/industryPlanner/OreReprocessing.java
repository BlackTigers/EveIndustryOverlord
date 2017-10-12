package de.lkrause.EIO.industryPlanner;


public class OreReprocessing {

	private static int[][] mRefining = { { 415, 0, 0, 0, 0, 0, 0, 0 },
										 { 346, 173, 0, 0, 0, 0, 0, 0 },
										 { 351, 25, 50, 0, 5, 0, 0, 0 },
										 { 107, 213, 107, 0, 0, 0, 0, 0 },
										 { 800, 100, 0, 85, 0, 0, 0, 0 },
										 { 134, 0, 267, 134, 0, 0, 0, 0 },
										 { 0, 0, 350, 0, 75, 8, 0, 0 },
										 { 2200, 0, 0, 100, 120, 15, 0, 0 },
										 { 0, 1000, 0, 200, 100, 19, 0, 0 },
										 { 0, 2200, 2400, 300, 0, 0, 0, 0 },
										 { 10000, 0, 0, 1600, 120, 0, 0, 0 },
										 { 21000, 0, 0, 0, 760, 135, 0, 0 },
										 { 56000, 12050, 2100, 450, 0, 0, 0, 0 },
										 { 0, 12000, 0, 0, 0, 450, 100, 0 },
										 { 22000, 0, 2500, 0, 0, 0, 320, 0 },
										 { 0, 0, 0, 0, 0, 0, 0, 300 }
									   };
	
	private static double[][] mRefiningPerM3 = new double[16][8];
	private static double[][] mISKPerMineral = new double[16][8]; // TODO Add ISK Per Mineral to the InitOres funciton
	private static double[] mMineralValue = new double[8]; // TODO API Access ISK value of Minerals
	
	private static double[] mOreVolumes = { 0.1, 0.15, 0.3, 0.35, 0.6, 1.2, 2, 3, 3, 5, 8, 16, 16, 16, 16, 40};
	
	private static double mMultiplier = 1;
	
	public static final double VARIANT1 = 1.05;
	public static final double VARIANT2 = 1.1;
	public static final double BASE = 1;
	
	
	public static void setVariant(double pVariant) {
		if (pVariant == VARIANT1 || pVariant == VARIANT2 || pVariant == BASE) {
			mMultiplier = pVariant;
		}
	}

	public static double getVariant() {
		return mMultiplier;
	}
	
	public static int getMineralsPerCompressed(int pOreID, int pMineralID) {
		return mRefining[pOreID][pMineralID];
	}
	
	public static int[] getMineralsPerCompressed(int pOreID) {
		return mRefining[pOreID];
	}

	public static double getMineralsPerM3(int pOreID, int pMineralID) {
		return mRefiningPerM3[pOreID][pMineralID];
	}
	
	public static double[] getMineralsPerM3(int pOreID) {
		return mRefiningPerM3[pOreID];
	}
	
	public static void initOres() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 8; j++) {
				mRefiningPerM3[i][j] = mRefining[i][j] / mOreVolumes[i];
			}
		}
	}
}
