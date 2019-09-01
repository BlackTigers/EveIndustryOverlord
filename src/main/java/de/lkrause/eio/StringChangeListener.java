package de.lkrause.eio;

import de.lkrause.eio.ui.ResultsTreeTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class StringChangeListener implements ChangeListener<String> {

	private ComboBox<String> mBlueprint;
	private ResultsTreeTable mTable;
	
	public StringChangeListener(ComboBox<String> pBlueprint, ResultsTreeTable pTable ) {
		mBlueprint = pBlueprint;
		mTable = pTable;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> pObservable, String pOldValue, String pNewValue) {
		
		mTable.calculateResults(mBlueprint.getValue());

	}

	
}
