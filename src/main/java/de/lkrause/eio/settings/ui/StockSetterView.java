package de.lkrause.eio.settings.ui;

import de.lkrause.eio.model.MainModel;
import de.lkrause.eio.reprocessing.ReprocessingCalculatorLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StockSetterView extends Stage {

	
	public StockSetterView() {

		GridPane lRootPane = new GridPane();
		
		BorderPane lLeft = new BorderPane();
		BorderPane lRight = new BorderPane();
		
		TextArea lInputTextArea = new TextArea();
		Label lInputLabel = new Label("Input");
		Button lCalculateButton = new Button("Set Stock");
		lCalculateButton.setAlignment(Pos.BASELINE_RIGHT);
		Button lExit = new Button("OK");
		
		lExit.setOnAction(pEvent -> setStock(lInputTextArea));
				
		lLeft.setPadding(new Insets(5));
		lRight.setPadding(new Insets(5));
		
		
		lLeft.setTop(lInputLabel);
		lLeft.setCenter(lInputTextArea);
		lLeft.setBottom(lCalculateButton);
		
		lRight.setBottom(lExit);
		
		lLeft.setMargin(lInputTextArea, new Insets(5, 0,5, 0));
		
		lRootPane.add(lLeft, 0, 0);
		lRootPane.add(lRight, 1, 0);
		
		setScene(new Scene(lRootPane));
		initOwner(MainModel.getInstance().getPrimaryStage());
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Set Stock (WIP)");
		showAndWait();
	}
	
	private void setStock(TextArea pStock) {
		
		
	}
}
