package de.lkrause.eio.inputs.fields;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class InputFields {

	private static JComboBox<String> mSelectedBlueprint;
	private static JSpinner mBlueprintME;
	private static JSpinner mBlueprintTE;

	private static JCheckBox mBuildComponents;
	private static JCheckBox mReactInputs;
	private static JSpinner mComponentME;
	private static JSpinner mComponentTE;

	private InputFields() {

	}

	public static void setSelectedBlueprint(JComboBox<String> pSelectedBlueprint) {

		mSelectedBlueprint = pSelectedBlueprint;
	}

	public static void setBlueprintME(JSpinner pBPME) {
		mBlueprintME = pBPME;
	}

	public static void setBlueprintTE(JSpinner pBPTE) {
		mBlueprintTE = pBPTE;
	}

	public static void setBuildComponents(JCheckBox pCheckBox) {

		mBuildComponents = pCheckBox;
	}

	public static void setReactInputs(JCheckBox pCheckBox) {

		mReactInputs = pCheckBox;
	}

	public static void setComponentME(JSpinner pComponentME) {

		mComponentME = pComponentME;
	}

	public static void setComponentTE(JSpinner pComponentTE) {

		mComponentTE = pComponentTE;
	}

	
	
	public static int getComponentME() {

		return (int) mComponentME.getValue();
	}

	public static int getComponentTE() {

		return (int) mComponentTE.getValue();
	}

}
