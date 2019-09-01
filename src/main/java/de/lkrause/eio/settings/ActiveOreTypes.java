package de.lkrause.eio.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ActiveOreTypes {

	private static ActiveOreTypes INSTANCE = new ActiveOreTypes();

	private Map<String, Boolean> mActiveOres = new HashMap<>();

	private ActiveOreTypes() {
		//		for (String lOreName : Ores.getInstance().getOreList()) {
		//			mActiveOres.put(lOreName, true);
		//		}
		////		Default Nullsec Ore filter:
		mActiveOres.put("Arkonor", true);
		mActiveOres.put("Bistot", true);
		mActiveOres.put("Crokite", true);
		mActiveOres.put("Gneiss", true);
		mActiveOres.put("Dark Ochre", true);
		mActiveOres.put("Spodumain", true);
	}

	public static ActiveOreTypes getInstance() {

		return INSTANCE;
	}

	public void setOreActivity(String pOreName, boolean pActive) {
		if (mActiveOres.containsKey(pOreName)) {
			mActiveOres.put(pOreName, pActive);
		} else {
			throw new NullPointerException("Ore doesn't exist");
		}
	}

	public List<String> getActiveOres() {
		List<String> lActiveOres = new ArrayList<>();

		for (Entry<String, Boolean> lEntry : mActiveOres.entrySet()) {
			if (lEntry.getValue()) {
				lActiveOres.add(lEntry.getKey());
			}
		}

		return lActiveOres;
	}
}
