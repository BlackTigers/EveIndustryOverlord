package de.lkrause.eio.ui;

public class ResultsTableModel {

	private String mItem;
	private long mQuantity;
	private int mWaste;
	private double mVolume;
	private double mPricePerUnit;

	public String getItem() {
		return mItem;
	}

	public void setItem(String pItem) {
		mItem = pItem;
	}

	public long getQuantity() {
		return mQuantity;
	}

	public void setQuantity(int pQuantity) {
		mQuantity = pQuantity;
	}

	public int getWaste() {
		return mWaste;
	}

	public void setWaste(int pWaste) {
		mWaste = pWaste;
	}

	public double getVolume() {
		return mVolume;
	}

	public void setVolume(double pVolume) {
		mVolume = pVolume;
	}

	public double getPricePerUnit() {
		return mPricePerUnit;
	}

	public void setPricePerUnit(double pPricePerUnit) {
		mPricePerUnit = pPricePerUnit;
	}

	public double getPriceSum() {
		return Math.max(mPricePerUnit * mQuantity, -1);
	}

}
