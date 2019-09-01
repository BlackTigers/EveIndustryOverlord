package de.lkrause.eio.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.lkrause.eio.model.Structure;

public class Structures {

	private static final Structures INSTANCE = new Structures();
	
	private Map<String, Structure> mRefineries = new HashMap<>();
	private Map<String, Structure> mEngineering = new HashMap<>();
	private Map<String, Structure> mAll = new HashMap<>();
	
	private Structures() {
		Structure lAthanor = new Structure("Athanor");
		Structure lTatara = new Structure("Tatara");
		Structure lRaitaru = new Structure("Raitaru");
		Structure lAzbel = new Structure("Azbel");
		Structure lSotiyo = new Structure("Sotiyo");
		Structure lAstrahus = new Structure("Astrahus");
		Structure lFortizar = new Structure("Fortizar");
		Structure lKeepstar = new Structure("Keepstar");
		Structure lStation = new Structure("Station");
		
		mRefineries.put("Athanor", lAthanor);
		mRefineries.put("Tatara", lTatara);
		
		mEngineering.put("Raitaru", lRaitaru);
		mEngineering.put("Azbel", lAzbel);
		mEngineering.put("Sotiyo", lSotiyo);
		mEngineering.put("Astrahus", lAstrahus);
		mEngineering.put("Fortizar", lFortizar);
		mEngineering.put("Keepstar", lKeepstar);
		mEngineering.put("Station", lStation);
		
		mAll.putAll(mRefineries);
		mAll.putAll(mEngineering);
	}
	
	public static Structures getInstance() {
		return INSTANCE;
	}
	
	public Collection<Structure> getRefineries() {
		return Collections.unmodifiableCollection(mRefineries.values());
	}
	
	public Collection<Structure> getEngineering() {
		return Collections.unmodifiableCollection(mEngineering.values());
	}
	
	public Structure getStructure(String pName) {
		
		if (!mAll.containsKey(pName)) {
			return null;
		}
		return new Structure(pName);
	}
	
}
