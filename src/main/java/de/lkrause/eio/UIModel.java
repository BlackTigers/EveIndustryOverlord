package de.lkrause.eio;

import de.lkrause.eio.collections.Structures;
import de.lkrause.eio.model.Structure;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

public class UIModel {

	private Spinner<Integer> mBlueprintME;
	private Spinner<Integer> mBlueprintTE;
	private Spinner<Integer> mRuns;

	private ComboBox<String> mBlueprintEC;
	private ComboBox<String> mComponentEC;
	private ComboBox<String> mReactionRefinery;

	private ComboBox<String> mBlueprintMERig;
	private ComboBox<String> mBlueprintTERig;

	private ComboBox<String> mComponentMERig;
	private ComboBox<String> mComponentTERig;

	private ComboBox<String> mReactionMERig;

	private Spinner<Integer> mComponentME = new Spinner<>(0, 10, 10, 1);
	private Spinner<Integer> mComponentTE = new Spinner<>(0, 20, 20, 2);

	private ComboBox<String> mReprocessingRefinery;
	private ComboBox<String> mReprocessingRig;

	private Structure mBlueprints;
	private Structure mComponents;
	private Structure mRefinery;
	private Structure mReactionFacility;

	private CheckBox mBuildComponents = new CheckBox("Build Components");
	private CheckBox mReactComposits = new CheckBox("React Composits");

	private static final UIModel INSTANCE = new UIModel();

	private UIModel() {

	}

	public static UIModel getInstance() {

		return INSTANCE;
	}

	public void setBlueprintME(Spinner<Integer> pBlueprintME) {
		mBlueprintME = pBlueprintME;
	}

	public void setBlueprintTE(Spinner<Integer> pBlueprintTE) {
		mBlueprintTE = pBlueprintTE;
	}

	public void setRuns(Spinner<Integer> pRuns) {
		mRuns = pRuns;
	}

	public void setBlueprintEC(ComboBox<String> pBlueprintEC) {
		mBlueprintEC = pBlueprintEC;
	}

	public void setComponentEC(ComboBox<String> pComponentEC) {
		mComponentEC = pComponentEC;
	}

	public void setReactionRefinery(ComboBox<String> pReactionRefinery) {
		mReactionRefinery = pReactionRefinery;
	}

	public void setBlueprintMERig(ComboBox<String> pBlueprintMERig) {
		mBlueprintMERig = pBlueprintMERig;
	}

	public void setBlueprintTERig(ComboBox<String> pBlueprintTERig) {
		mBlueprintTERig = pBlueprintTERig;
	}

	public void setComponentMERig(ComboBox<String> pComponentMERig) {
		mComponentMERig = pComponentMERig;
	}

	public void setComponentTERig(ComboBox<String> pComponentTERig) {
		mComponentTERig = pComponentTERig;
	}

	public void setReactionMERig(ComboBox<String> pReactionMERig) {
		mReactionMERig = pReactionMERig;
	}

	public void setBuildComponents(CheckBox pBuildComponents) {
		mBuildComponents = pBuildComponents;
	}

	public void setReactComposits(CheckBox pReactComposits) {
		mReactComposits = pReactComposits;
	}

	public int getComponentME() {
		return mComponentME.getValue();
	}

	public void setComponentME(Spinner<Integer> pComponentME) {
		mComponentME = pComponentME;
	}

	public int getComponentTE() {
		return mComponentTE.getValue();
	}

	public void setComponentTE(Spinner<Integer> pComponentTE) {
		mComponentTE = pComponentTE;
	}

	public void setReprocessingRefinery(ComboBox<String> pReprocessingRefinery) {
		mReprocessingRefinery = pReprocessingRefinery;
	}

	public int getBlueprintME() {
		return mBlueprintME.getValue();
	}

	public int getBlueprintTE() {
		return mBlueprintTE.getValue();
	}

	public int getRuns() {
		return mRuns.getValue();
	}

	public Structure getBlueprintEC() {
		if (mBlueprints == null || !mBlueprints.getName().equals(mBlueprintEC.getValue())) {
			mBlueprints = Structures.getInstance().getStructure(mBlueprintEC.getValue());
		}
		mBlueprints.setRigs(mBlueprintMERig.getValue(), mBlueprintTERig.getValue());
		return mBlueprints;
	}

	public Structure getComponentEC() {
		if (mComponents == null || !mComponents.getName().equals(mComponentEC.getValue())) {
			mComponents = Structures.getInstance().getStructure(mComponentEC.getValue());
		}
		mComponents.setRigs(mComponentMERig.getValue(), mComponentTERig.getValue());
		return mComponents;
	}

	public Structure getReactionRefinery() {
		if (mReactionFacility == null || !mReactionFacility.getName().equals(mReactionRefinery.getValue())) {
			mReactionFacility = Structures.getInstance().getStructure(mReactionRefinery.getValue());
		}
		mReactionFacility.setRigs(mReactionMERig.getValue(), Structure.NO_RIG);
		return mReactionFacility;
	}

	public String getComponentMERig() {
		return mComponentMERig.getValue();
	}

	public String getComponentTERig() {
		return mComponentTERig.getValue();
	}

	public String getReactionMERig() {
		return mReactionMERig.getValue();
	}

	public boolean buildComponents() {

		return mBuildComponents.isSelected();
	}

	public boolean reactComposits() {

		return mReactComposits.isSelected();
	}

}
