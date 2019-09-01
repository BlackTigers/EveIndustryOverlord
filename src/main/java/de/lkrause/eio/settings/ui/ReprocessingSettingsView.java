package de.lkrause.eio.settings.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.lkrause.eio.collections.Structures;
import de.lkrause.eio.industryplanner.collections.Ores;
import de.lkrause.eio.model.MainModel;
import de.lkrause.eio.model.ReprocessingModel;
import de.lkrause.eio.model.Structure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReprocessingSettingsView extends Stage {

	private static final String REPROCESSING = "Reprocessing";
	private static final String REPROEFFICIENCY = "Reprocessing Efficiency";
	
	
	public ReprocessingSettingsView() {
				
		GridPane lRootPane = new GridPane();
		
		lRootPane.setPadding(new Insets(10));
		
		lRootPane.setVgap(5);
		lRootPane.setHgap(5);
		
		Map<String, Spinner<Integer>> lReprocessingSpinners = new HashMap<>();
		
		// TODO Set default to read from properties file
		lReprocessingSpinners.put(REPROCESSING, new Spinner<Integer>(0, 5, 5));
		lReprocessingSpinners.put(REPROEFFICIENCY, new Spinner<Integer>(0, 5, 5));
		
		
		for (String lOre : Ores.getInstance().getOreList()) {
			// TODO Set default to read from properties file
			lReprocessingSpinners.put(lOre, new Spinner<Integer>(0, 5, 5));
		}
		
		int lRow = 0;
		
		for (Entry<String, Spinner<Integer>> lElement : lReprocessingSpinners.entrySet()) {
			
			lRootPane.add(new Label(lElement.getKey()), 0, lRow);
			lRootPane.add(lElement.getValue(), 1, lRow);
			lRow++;
		}
		
		lReprocessingSpinners.put("Ice", new Spinner<Integer>(0, 5, 5));
		lRootPane.add(new Label("Ice"), 0, lRow);
		lRootPane.add(lReprocessingSpinners.get("Ice"), 1, lRow);
		lRow++;
		
		ObservableList<Integer> lImplantList = FXCollections.observableArrayList();
		lImplantList.addAll(0, 1, 2, 4);
		
		ComboBox<Integer> lImplant = new ComboBox<>(lImplantList);
		
		lImplant.setValue(4);
		
		lRootPane.add(new Label("Implant"), 0, lRow);
		lRootPane.add(lImplant, 1, lRow);
		lRow++;
		
		ObservableList<String> lRefineries = FXCollections.observableArrayList();
		for (Structure lRefinery : Structures.getInstance().getRefineries()) {
			lRefineries.add(lRefinery.getName());
		}
		
		ComboBox<String> lReprocessingRefinery = new ComboBox<>(lRefineries);
		// TODO Set default to read from properties file
		lReprocessingRefinery.setValue("Athanor");

		lRootPane.add(new Label("Refinery"), 0, lRow);
		lRootPane.add(lReprocessingRefinery, 1, lRow);
		lRow++;
		
		ObservableList<String> lRigs = FXCollections.observableArrayList();
		lRigs.addAll(Structure.NO_RIG, Structure.TECH1, Structure.TECH2);
		
		ComboBox<String> lRig = new ComboBox<>(lRigs);
		lRig.setValue("Tech 2");
		
		lRootPane.add(new Label("Reprocessing Rig"), 0, lRow);
		lRootPane.add(lRig, 1, lRow);
		lRow++;
		
		HBox lButtonBox = new HBox(2);
		lRootPane.add(lButtonBox, 1, lRow);
		lButtonBox.setAlignment(Pos.BASELINE_RIGHT);
		
		Button lSetAll5 = new Button("All 5");
		Button lSave = new Button("Save");
		Button lCancel = new Button("Cancel");
		
		lCancel.setOnAction(pAction -> close());
		lSave.setOnAction(pAction -> saveSettings(lReprocessingSpinners, lImplant, lReprocessingRefinery, lRig));
		lSetAll5.setOnAction(pAction -> setAll5(lReprocessingSpinners));
		
		lButtonBox.getChildren().addAll(lSetAll5, lSave, lCancel);
		
		Scene lScene = new Scene(lRootPane);
		
		setScene(lScene);
		initOwner(MainModel.getInstance().getPrimaryStage());
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Reprocessing Settings");
		showAndWait();
		
	}
	
	private void saveSettings(Map<String, Spinner<Integer>> pSettings, ComboBox<Integer> pImplant, ComboBox<String> pRefinery, ComboBox<String> pRig) {
		
		// TODO Add Facility and Properties File
		Map<String, Integer> lOres = new HashMap<>();
		
		for (Entry<String, Spinner<Integer>> lElement : pSettings.entrySet()) {
			if (!lElement.getKey().equals(REPROCESSING) && !lElement.getKey().equals(REPROEFFICIENCY)) {
				lOres.put(lElement.getKey(), lElement.getValue().getValue());
			}
		}
		
		ReprocessingModel.getInstance().setReprocessingRefinery(pRefinery.getValue(), pRig.getValue());
		
		ReprocessingModel.getInstance().setReprocessingLevels(pSettings.get(REPROCESSING).getValue(), pSettings.get(REPROEFFICIENCY).getValue(), lOres, pImplant.getValue());
		this.close();
	}
	
	private void setAll5(Map<String, Spinner<Integer>> pReprocesingSpinners) {
		
		for (Entry<String, Spinner<Integer>> lSpinner : pReprocesingSpinners.entrySet()) {
			lSpinner.getValue().getValueFactory().setValue(5);
		}
	}
}
