package de.lkrause.EIO;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.lkrause.EIO.industryPlanner.OreReprocessing;

public class EveOverlord extends JFrame {

	private EveOverlord() {
		
		JPanel lMainPanel = new JPanel(new GridLayout());
		JPanel lJobOverviewPanel = new JPanel();
		
		
	}
	
	public static void main(String[] args) {
		new EveOverlord();
		OreReprocessing.initOres();
	}
}
