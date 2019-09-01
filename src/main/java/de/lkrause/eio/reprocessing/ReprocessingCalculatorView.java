package de.lkrause.eio.reprocessing;

import de.lkrause.eio.model.MainModel;
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

public class ReprocessingCalculatorView extends Stage {

	public ReprocessingCalculatorView() {
		
		GridPane lRootPane = new GridPane();
		
		BorderPane lLeft = new BorderPane();
		BorderPane lRight = new BorderPane();
		
		TextArea lInputTextArea = new TextArea();
		Label lInputLabel = new Label("Input");
		Button lCalculateButton = new Button("Calculate");
		lCalculateButton.setAlignment(Pos.BASELINE_RIGHT);
		
		TextArea lOutputArea = new TextArea();
		Label lOutputLabel = new Label("Output");
		Button lExit = new Button("OK");
		
		lExit.setOnAction(pEvent -> close());
		lCalculateButton.setOnAction(pEvent -> lOutputArea.setText(ReprocessingCalculatorLogic.getInstance().calculate(lInputTextArea.getText())));
		
		lLeft.setPadding(new Insets(5));
		lRight.setPadding(new Insets(5));
		
		
		lLeft.setTop(lInputLabel);
		lLeft.setCenter(lInputTextArea);
		lLeft.setBottom(lCalculateButton);
		
		lRight.setTop(lOutputLabel);
		lRight.setCenter(lOutputArea);
		lRight.setBottom(lExit);
		
		lLeft.setMargin(lInputTextArea, new Insets(5, 0,5, 0));
		lRight.setMargin(lOutputArea, new Insets(5, 0,5, 0));

		lOutputArea.setEditable(false);
		
		lRootPane.add(lLeft, 0, 0);
		lRootPane.add(lRight, 1, 0);
		
		setScene(new Scene(lRootPane));
		initOwner(MainModel.getInstance().getPrimaryStage());
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Reprocessing Calculator");
		showAndWait();

	}
	
	
}
