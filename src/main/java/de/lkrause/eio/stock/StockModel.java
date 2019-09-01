package de.lkrause.eio.stock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StockModel {
	
	/** static Singleton instance. */
	private static final StockModel INSTANCE = new StockModel();

	/** Map containing the current stock, initially empty. */
	private Map<Integer, Long> mStock = new HashMap<>();
	
	/**
	 * Private constructor to overwrite public one.
	 */
	private StockModel() {
	}

	/**
	 * Return the one instance.
	 */
	public static StockModel getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Deposit Materials in stock.
	 * @param pID The ID of the material that should be deposited
	 * @param pAmount The amount of that material that should be deposited
	 */
	public void putInStock(int pID, long pAmount) {
		if (mStock.containsKey(pID)) {
			mStock.put(pID, mStock.get(pID) + pAmount);
		} else {
			mStock.put(pID, pAmount);
		}
	}
	
	/**
	 * Take Materials away from Stock.
	 * @param pID The ID of the material taken away
	 * @param pAmount The amount of how much material should be taken away
	 * @return true if enough material is in stock, false if not
	 */
	public boolean takeFromStock(int pID, long pAmount) {
		if (!mStock.containsKey(pID)) {
			return false;
		}
		if (mStock.get(pID) < pAmount) {
			return false;
		}
		mStock.put(pID, mStock.get(pID) - pAmount);
		return true;
	}
	
	/**
	 * Get the Material Stock of a single Material.
	 * @param pID The ID of the Material
	 * @return The amount of that material that is in stock
	 */
	public long getMaterialStock(int pID) {
		return mStock.containsKey(pID) ? mStock.get(pID) : 0;
	}
	
	/**
	 * Get the whole stock.
	 * @return The Map with all Material ID -> Amounts in stock
	 */
	public Map<Integer, Long> getStock() {
		return Collections.unmodifiableMap(mStock);
	}
}
