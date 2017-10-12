package de.lkrause.EIO.industryPlanner;

public class Component {

	private int mTypeID;
	private int mRace;
	private boolean mCapital;
	private boolean mT2;
	private int mRequiredBlueprintID;
	
	public Component(int pTypeID) {
		mTypeID = pTypeID;
		// TODO Database Call
	}
}
