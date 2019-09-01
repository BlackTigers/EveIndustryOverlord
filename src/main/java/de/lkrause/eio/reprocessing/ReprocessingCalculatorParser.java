package de.lkrause.eio.reprocessing;

import java.util.HashMap;
import java.util.Map;

public class ReprocessingCalculatorParser {

	private static final ReprocessingCalculatorParser INSTANCE = new ReprocessingCalculatorParser();
	
	private ReprocessingCalculatorParser() {
	}
	
	protected static ReprocessingCalculatorParser getInstance() {
		return INSTANCE;
	}
	
	protected Map<String, Long> getParsedMaterials(String pInput) {

		Map<String, Long> lOutputMap = new HashMap<>();
		
		String lInput = pInput.replaceAll("\\.", "");
		lInput = lInput.replaceAll("\\,", "");
		String[] lInputLines = lInput.split("\n");		
		
		for (String lLine : lInputLines) {
			if (48 <= lLine.charAt(lLine.length()-1) && lLine.charAt(lLine.length()-1) <= 57) {
				int lIndex = lLine.length()-1;
				while (lIndex > 0 && (lLine.charAt(lIndex) != ' ')) {
					lIndex--;
				}
				String lNumber = lLine.substring(lIndex, lLine.length()).trim();
				
				while (lLine.charAt(lIndex) == ' ') {
					lIndex--;
				}
				String lName = lLine.substring(0, lIndex + 1).trim();
				
				lOutputMap.put(lName, Long.parseLong(lNumber));		
			} else {
				int lIndex = 0;
				boolean lString = true;
				while (lString) {
					while (lLine.charAt(lIndex) != ' ') {
						lIndex++;
					}
					lIndex++;
					if (lLine.charAt(lIndex) <= 57 && lLine.charAt(lIndex) >= 48) {
						lString = false;
					}
				}
				
				int lIndex2 = lIndex;
				while (lLine.charAt(lIndex2) != ' ') {
					lIndex2++;
				}
				
				String lNumber = lLine.substring(lIndex, lIndex2).trim();
				String lName = lLine.substring(0, lIndex).trim();
				
				lOutputMap.put(lName, Long.parseLong(lNumber));
			}
		}		
		
		return lOutputMap;
	}
}
