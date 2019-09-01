package de.lkrause.eio.model;

import javafx.stage.Stage;

public class MainModel {

	private static MainModel INSTANCE = new MainModel();
	private Stage mMainClass;
	
	private MainModel() {
		
	}
	
	public static MainModel getInstance() {
		return INSTANCE;
	}
	
	public void setPrimaryStage(Stage pStage) {
		
		mMainClass = pStage;
	}
	
	public Stage getPrimaryStage() {
		
		return mMainClass;
	}
}
