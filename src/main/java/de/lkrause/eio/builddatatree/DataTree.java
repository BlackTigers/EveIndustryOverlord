package de.lkrause.eio.builddatatree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.lkrause.eio.industryplanner.collections.Materials;

public class DataTree {

	private int mCount = -1;
	private String mName = "";
	
	List<DataTree> mChildren = new ArrayList<>();
	
	public DataTree(int pItemId, int pCount) {
		
		mCount = pCount;
		mName = Materials.getInstance().getMaterial(pItemId).getTypeName();
	}
	
	public DataTree() {
	}
	
	public List<DataTree> getChildren() {
		
		return Collections.unmodifiableList(mChildren);
	}
	
	public String getTypeName() {
		return mName;
	}
	
	public int getCount() {
		return mCount;
	}
	
	public void addChild(DataTree pChild) {
		mChildren.add(pChild);
	}
	
	public boolean isLeaf() {
		return mChildren.size() == 0;
	}
}
