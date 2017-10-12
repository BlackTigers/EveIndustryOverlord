package de.lkrause.EIO;

import java.awt.GridLayout;
import java.security.KeyStore.LoadStoreParameter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.lkrause.EIO.industryPlanner.rawMaterials.OreReprocessing;

public class EveOverlord extends JFrame {

	private EveOverlord() {
		
		JPanel lMainPanel = new JPanel(new GridLayout());
		JPanel lJobOverviewPanel = new JPanel();
		
		
	}
	
	public static void main(String[] args) {
		loadStaticData();
		OreReprocessing.initOres();
		new EveOverlord();
	}
	
	private static void loadStaticData() {
		// TODO Load all Types + BPs from Database into model
	}
}
