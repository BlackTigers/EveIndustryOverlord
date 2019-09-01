package de.lkrause.eio.ui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SettingsPane extends BorderPane {

	public SettingsPane(String pTitle, Node pContent) {
		Text lTitleText = new Text(pTitle);
		HBox lTitlePane = new HBox(lTitleText);
		
		setTop(lTitlePane);
		setCenter(pContent);

		lTitlePane.getStyleClass().add("title");
		pContent.getStyleClass().add("content");
		getStyleClass().add("settings-pane");
		
	}
}
