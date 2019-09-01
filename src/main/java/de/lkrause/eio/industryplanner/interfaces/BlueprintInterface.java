package de.lkrause.eio.industryplanner.interfaces;

import java.util.Map;

import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;

public interface BlueprintInterface {
	
	/**
	 * Get Material Efficiency
	 * @return The Material Efficiency
	 */
	public int getME();
	
	/**
	 * Get Time Efficiency
	 * @return The Time Efficiency
	 */
	public int getTE();
	
	/**
	 * Return the amount of Runs left on a BPC or the Max Runs of a BPO
	 * @return Max Runs of BPO or Runs left of BPC
	 */
	public int getRunsLeft();
	
	/**
	 * Return the Map with all Input Materials
	 * @return A map with the Input Material and the Amount needed for ME 0
	 */
	public Map<Integer, Integer> getInputMaterials();
	
	/**
	 * Add pRuns runs to the Queue (and to Material Calculation)
	 * @param pRuns The amount of runs done
	 */
	public void run(int pRuns);
	
	/**
	 * Get the type of the Blueprint
	 * @return Type of Blueprint (PBTypes.*)
	 */
	public int getType();
	
	/**
	 * Get the internal Type ID of the Blueprint
	 * @return Internal TypeID
	 */
	public int getTypeID();
	
	/**
	 * Get the base time to make one Run
	 * @return Time to make one run of this blueprint in seconds
	 */
	public long getBaseRunTime();
	
	/**
	 * Get the base copy time for one run
	 * @return Base copy time for one run in seconds
	 */
	public long getBaseCopyTime();
	
	/**
	 * Get the outcome amount of the Blueprint
	 * @return The outcome amount of the Blueprint
	 */
	public Map<Integer, Integer> getProductionOutcome();
}
