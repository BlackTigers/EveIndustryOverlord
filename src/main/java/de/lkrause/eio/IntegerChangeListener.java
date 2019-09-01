package de.lkrause.eio;

import de.lkrause.eio.ui.ResultsTreeTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class IntegerChangeListener implements ChangeListener<Integer> {

	private ComboBox<String> mBlueprint;
	private ResultsTreeTable mTable;
	
	public IntegerChangeListener(ComboBox<String> pBlueprint, ResultsTreeTable pTable ) {
		mBlueprint = pBlueprint;
		mTable = pTable;
	}

	@Override
	public void changed(ObservableValue<? extends Integer> pObservable, Integer pOldValue, Integer pNewValue) {
		mTable.calculateResults(mBlueprint.getValue());
		
	}

	
}
