package de.lkrause.eio.ui;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SettingsInputs extends GridPane {

	public SettingsInputs(ComboBox<String> pStructure, ComboBox<String> pME, ComboBox<String> pTE, TextField pSystem) {
		add(pStructure, 0, 0, 2, 1);
		add(new Label("Rig ME: "), 2,  0, 1, 1);
		add(pME, 3,  0, 1, 1);
		if (pTE != null) {
			add(new Label("Rig TE: "), 4,  0, 1, 1);
			add(pTE, 5,  0, 1, 1);
		} else {
			Label lLabel = new Label("Rig TE: ");
			lLabel.setVisible(false);
			ComboBox<String> lTE = new ComboBox<>(FXCollections.observableArrayList("Tech 1"));
			lTE.setVisible(false);
			add(lLabel, 4, 0, 1, 1);
			add(lTE, 5, 0, 1, 1);
		}
		add(pSystem, 6, 0, 1, 1);
		
		getStyleClass().add("settings-inputs");
	}
	
	public SettingsInputs(ComboBox<String> pStructure, ComboBox<String> pME, TextField pSystem) {
		this(pStructure, pME, null, pSystem);
	}
}
