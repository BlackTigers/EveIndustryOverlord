package de.lkrause.eio.model;

import java.util.ArrayList;
import java.util.List;

public class BuildList {

	private static final BuildList INSTANCE = new BuildList();
	
	private List<BuildJob> mBuildList = new ArrayList<>();
	
	private BuildList() {
		
	}
	
	public static BuildList getInstance() {
		return INSTANCE;
	}
	
	
}
