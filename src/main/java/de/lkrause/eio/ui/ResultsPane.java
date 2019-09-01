package de.lkrause.eio.ui;

import java.util.HashMap;
import java.util.Map;

import de.lkrause.eio.industryplanner.collections.Types;
import de.lkrause.eio.model.MainModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultsPane extends BorderPane {

	public ResultsPane(ResultsTreeTable pResultsTable) {

		setTop(createCaption("Materials Needed"));
		setCenter(createResultsTable(pResultsTable));
		setBottom(createShoppingListButton(pResultsTable));

		getStyleClass().add("results-pane");
	}

	private Node createCaption(String pText) {

		FlowPane lPane = new FlowPane();
		lPane.getChildren().add(new Text(pText));

		lPane.getStyleClass().add("caption");

		return lPane;
	}
	
	private Node createResultsTable(ResultsTreeTable pResultsTable) {

		BorderPane lPane = new BorderPane();

		lPane.setCenter(pResultsTable);

		lPane.getStyleClass().add("table");

		return lPane;
	}

	private Node createShoppingListButton(ResultsTreeTable pResultsTable) {

		Button lShowShoppingList = new Button("Show Shopping List");
		Button lAddToBuildlist = new Button("Add to Buildlist");
		
		FlowPane lPane = new FlowPane(lAddToBuildlist, lShowShoppingList);
		lPane.setHgap(5);
		
		lShowShoppingList.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent pArg0) {

				Stage lDialog = new Stage();
				
				BorderPane lRootPane = new BorderPane();
				lRootPane.setPadding(new Insets(5));
				
				Button lOKButton = new Button("OK");
				HBox lButtonPane = new HBox(1);
				
				lButtonPane.setPadding(new Insets(5, 0, 0, 0));
				lButtonPane.getChildren().add(lOKButton);
				lButtonPane.setAlignment(Pos.BASELINE_RIGHT);
				
				lOKButton.setOnAction(pEvent -> lDialog.close());
				
				TextArea lText = new TextArea();
				lText.setPrefSize(300, 250);
				HBox lCenter = new HBox(1);
				lCenter.getChildren().add(lText);
				
				lRootPane.setBottom(lButtonPane);
				
				String lShoppingText = "";

				Map<Integer, Long> lShoppingList = new HashMap<>();
				
				lShoppingList = pResultsTable.getShoppingList();
				
				for (int lId : lShoppingList.keySet()) {
					
					String lName = Types.getInstance().getMaterial(lId).getTypeName();
					lShoppingText = lShoppingText + lName + "    " + lShoppingList.get(lId) + "\n";
				}
				lText.setText(lShoppingText);
				lText.setEditable(false);
				
				lButtonPane.setAlignment(Pos.CENTER_RIGHT);
				lButtonPane.getStyleClass().add("buttons");
				
				lRootPane.setCenter(lCenter);
				
				Scene lScene = new Scene(lRootPane);
				lDialog.setScene(lScene);
				lDialog.initOwner(MainModel.getInstance().getPrimaryStage());
				lDialog.initModality(Modality.APPLICATION_MODAL);
				lDialog.setTitle("Shopping List");
				lDialog.showAndWait();
				
			}
		});
		
		

		lPane.setAlignment(Pos.CENTER_RIGHT);

		lPane.getStyleClass().add("buttons");

		return lPane;
	}
}
