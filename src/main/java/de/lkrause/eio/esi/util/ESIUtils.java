package de.lkrause.eio.esi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class ESIUtils {

	public static final String DATASOURCE_TQ = "tranquility"; // "singularity"

	/**
	 * Hidden Constructor
	 */
	private ESIUtils() {}
	
	public static List<Integer> convertJSONToList(String pInput) {
		String lIn = pInput.substring(1, pInput.length() - 1);
		String[] lNumbers = lIn.split(",");
		List<Integer> lIDs = new ArrayList<>();
		for (String lNumber : lNumbers) {
			lIDs.add(Integer.parseInt(lNumber));
		}
		return lIDs;
	}
	
	public static double getValue(int pTypeID) {
		
		try {
			// TODO Add Region and order type selection
			String lInput = getHTML("https://market.fuzzwork.co.uk/aggregates/?region=10000002&types=" + pTypeID);
			
			JSONObject lObj = new JSONObject(lInput);
			
			return lObj.getJSONObject(Integer.toString(pTypeID)).getJSONObject("sell").getDouble("min");			
		} catch (Exception e) {
			Logger.getLogger("API").log(Level.WARNING, "JSON parse or API Connection failed", e);
		}
		
		return 1;
	}
	
	public static String getHTML(String urlToRead) throws IOException {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
}
