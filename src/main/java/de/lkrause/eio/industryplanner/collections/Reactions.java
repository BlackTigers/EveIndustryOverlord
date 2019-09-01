package de.lkrause.eio.industryplanner.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lkrause.eio.industryplanner.Blueprint;
import de.lkrause.eio.industryplanner.ReactionFormula;

public class Reactions {

	private static final Reactions INSTANCE = new Reactions();

	private Map<String, ReactionFormula> mAvailableReactions = new HashMap<>();

	private List<String> mReactionNameList = new ArrayList<>();
	
	private Reactions() {

	}

	public static Reactions getInstance() {
		return INSTANCE;
	}

	public ReactionFormula getReaction(String pName) {
				
		if (!mAvailableReactions.containsKey(pName)) {
			addReaction(pName);
		}

		return mAvailableReactions.get(pName);
	}

	private void addReaction(String pName) {

		ReactionFormula lReaction = new ReactionFormula(pName);
		mAvailableReactions.put(pName, lReaction);
	}

	public void setReactionNames(List<String> pNames) {
		mReactionNameList.addAll(pNames);
	}

	public List<String> getNameList() {
		return Collections.unmodifiableList(mReactionNameList);
	}

}
