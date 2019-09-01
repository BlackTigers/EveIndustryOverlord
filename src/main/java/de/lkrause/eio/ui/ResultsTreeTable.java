package de.lkrause.eio.ui;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.lkrause.eio.UIModel;
import de.lkrause.eio.industryplanner.BPTypes;
import de.lkrause.eio.industryplanner.Blueprint;
import de.lkrause.eio.industryplanner.collections.Blueprints;
import de.lkrause.eio.industryplanner.collections.Materials;
import de.lkrause.eio.industryplanner.collections.Ores;
import de.lkrause.eio.industryplanner.collections.Types;
import de.lkrause.eio.industryplanner.rawmaterials.InputMaterial;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class ResultsTreeTable extends TreeTableView<ResultsTableModel> {

	private static final String NUMERIC = "numeric-column";

	private UIModel mUiModel = UIModel.getInstance();

	private TreeItem<ResultsTableModel> mRoot = new TreeItem<ResultsTableModel>();

	private Map<Integer, Long> mShoppingList = new HashMap<>();


	public ResultsTreeTable() {
		TreeTableColumn<ResultsTableModel, String> lItemColumn = new TreeTableColumn<>("Material");
		TreeTableColumn<ResultsTableModel, String> lQuantityColumn = new TreeTableColumn<>("Quantity");
		TreeTableColumn<ResultsTableModel, String> lWasteColumn = new TreeTableColumn<>("ME Waste");
		TreeTableColumn<ResultsTableModel, String> lVolumeColumn = new TreeTableColumn<>("Volume");
		TreeTableColumn<ResultsTableModel, String> lPricePerUnitColumn = new TreeTableColumn<>("Unit Price");
		TreeTableColumn<ResultsTableModel, String> lPriceSumColumn = new TreeTableColumn<>("Sum");

		getColumns().addAll(lItemColumn, lQuantityColumn, lWasteColumn, lVolumeColumn, lPricePerUnitColumn,
				lPriceSumColumn);

		lItemColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<ResultsTableModel, String> lCell) -> new ReadOnlyStringWrapper(
						lCell.getValue().getValue().getItem()));
		lQuantityColumn.setCellValueFactory(cellData -> {
			final SimpleStringProperty lProperty = new SimpleStringProperty();
			lProperty.setValue(DecimalFormat.getNumberInstance().format(cellData.getValue().getValue().getQuantity()));
			return lProperty;
		});
		lWasteColumn.setCellValueFactory(cellData -> {
			final SimpleStringProperty lProperty = new SimpleStringProperty();
			lProperty.setValue(DecimalFormat.getNumberInstance().format(cellData.getValue().getValue().getWaste()));
			return lProperty;
		});
		lVolumeColumn.setCellValueFactory(cellData -> {
			final SimpleStringProperty lProperty = new SimpleStringProperty();
			lProperty.setValue(
					DecimalFormat.getNumberInstance().format(cellData.getValue().getValue().getVolume()) + " m³");
			return lProperty;
		});
		lPricePerUnitColumn.setCellValueFactory(cellData -> {
			final SimpleStringProperty lProperty = new SimpleStringProperty();
			lProperty
			.setValue(DecimalFormat.getNumberInstance().format(cellData.getValue().getValue().getPricePerUnit())
					+ " ISK");
			return lProperty;
		});
		lPriceSumColumn.setCellValueFactory(cellData -> {
			final SimpleStringProperty lProperty = new SimpleStringProperty();
			lProperty.setValue(
					DecimalFormat.getNumberInstance().format(cellData.getValue().getValue().getPriceSum()) + " ISK");
			return lProperty;
		});

		lItemColumn.getStyleClass().add("item-column");
		lQuantityColumn.getStyleClass().add("quantity-column");
		lWasteColumn.getStyleClass().add("waste-column");
		lVolumeColumn.getStyleClass().add("volume-column");
		lPriceSumColumn.getStyleClass().add("sum-column");
		lPricePerUnitColumn.getStyleClass().add("unit-price-column");

		lQuantityColumn.getStyleClass().add(NUMERIC);
		lWasteColumn.getStyleClass().add(NUMERIC);
		lVolumeColumn.getStyleClass().add(NUMERIC);
		lPricePerUnitColumn.getStyleClass().add(NUMERIC);
		lPriceSumColumn.getStyleClass().add(NUMERIC);

		setShowRoot(false);

		setRoot(mRoot);

	}

	public Map<Integer, Long> getShoppingList() {

		mShoppingList.clear();
		analyze(mShoppingList, getRoot());

		return Collections.unmodifiableMap(Ores.getInstance().calculateOreAmounts(mShoppingList)); // update back to ore calculation
	}


	private void analyze(Map<Integer, Long> pShoppingList, TreeItem<ResultsTableModel> pCurrentNode) {

		if (!pCurrentNode.isLeaf()) {
			for (TreeItem<ResultsTableModel> lItem : pCurrentNode.getChildren()) {

				analyze(pShoppingList, lItem);
			}
		} else {
			if (pShoppingList.containsKey(Types.getInstance().getMaterial(pCurrentNode.getValue().getItem()).getTypeId())) {
				pShoppingList.put(Types.getInstance().getMaterial(pCurrentNode.getValue().getItem()).getTypeId(), pShoppingList.get(Types.getInstance().getMaterial(pCurrentNode.getValue().getItem()).getTypeId()) + pCurrentNode.getValue().getQuantity());
			} else {
				pShoppingList.put(Types.getInstance().getMaterial(pCurrentNode.getValue().getItem()).getTypeId(), pCurrentNode.getValue().getQuantity());
			}
		}
	}



	// ------------------------------------------------
	// ------------ Calculate Results -----------------
	// ------------------------------------------------

	public void calculateResults(String pBlueprintName) {

		Map<Integer, Integer> lMaterialAmounts = Blueprints.getInstance().getBlueprint(pBlueprintName).getInputMaterials();
		mRoot.getChildren().clear();
		mShoppingList.clear();

		addMaterials(lMaterialAmounts, Blueprints.getInstance().getBlueprint(pBlueprintName), false, mUiModel.getRuns(), mRoot);
	}

	/**
	 * Add Blueprints to the Shopping List.
	 * @param pMaterialAmounts Current shopping list
	 * @param pBlueprint The current blueprint that should be added
	 * @param pIsComponent Is the current material a component
	 * @param pRuns Run amount
	 * @param pRoot Tree item above
	 */
	private void addMaterials(Map<Integer, Integer> pMaterialAmounts, Blueprint pBlueprint, boolean pIsComponent,
			int pRuns, TreeItem<ResultsTableModel> pRoot) {

		String lBlueprintName = pBlueprint.getName();

		// Get the material Name of the outcome (while filtering all other secondary outcomes)
		String lMainMaterialOutcome = lBlueprintName.replaceAll(" Blueprint", "");
		lMainMaterialOutcome = lMainMaterialOutcome.replaceAll(" Reaction Formula", "");

		// If an error occurs then the database connection is probably not functional
		if (pBlueprint.getProductionOutcome() == null) {
			System.err.println("Production Outcome not initialized");
			return;
		}

		// Amount of Units being put out when producing one run
		int lBlueprintOutcomeAmount = pBlueprint.getProductionOutcome().get(Types.getInstance().getMaterial(lMainMaterialOutcome).getTypeId());

		for (int lMaterialID : pMaterialAmounts.keySet()) {
			ResultsTableModel lModel = new ResultsTableModel();

			InputMaterial lMaterial = Materials.getInstance().getMaterial(lMaterialID);

			double lME;
			double lEC;

			// Define in which of the structure this should be built
			if (!pIsComponent && !(pBlueprint.getType() == BPTypes.REACTION)) {
				lME = mUiModel.getBlueprintME();
				lEC = mUiModel.getBlueprintEC().getME();
			} else if (!(pBlueprint.getType() == BPTypes.REACTION)) {
				lME = mUiModel.getComponentME();
				lEC = mUiModel.getComponentEC().getME();
			} else {
				lEC = mUiModel.getReactionRefinery().getME();
				lME = 0;
			}
			int lAmount = 0;

			int lRunsRequired = pRuns % lBlueprintOutcomeAmount == 0 ? pRuns / lBlueprintOutcomeAmount : pRuns / lBlueprintOutcomeAmount + 1;

			lAmount = (int) Math.max(lRunsRequired,
					Math.ceil(Math
							.ceil(lRunsRequired * pMaterialAmounts.get(lMaterialID) * ((1 - lME / 100.0) * (1 - lEC)) * 100)
							/ 100.0));

			lModel.setItem(lMaterial.getTypeName());
			lModel.setQuantity(lAmount);
			lModel.setVolume(lMaterial.getVolume() * lAmount);
			lModel.setWaste(pRuns * pMaterialAmounts.get(lMaterialID) - lAmount);
			lModel.setPricePerUnit(lMaterial.getCachedValue());

			TreeItem<ResultsTableModel> lNode = new TreeItem<ResultsTableModel>(lModel);

			pRoot.getChildren().add(lNode);
			lNode.setExpanded(true);

			if (mUiModel.buildComponents()) {

				String lMaterialBlueprintName = Types.getInstance().getMaterial(lMaterialID).getTypeName() + " Blueprint";

				if (Blueprints.getInstance().getNameList().contains(lMaterialBlueprintName)) {
					addMaterials(Blueprints.getInstance().getBlueprint(lMaterialBlueprintName).getInputMaterials(),
							Blueprints.getInstance().getBlueprint(lMaterialBlueprintName), true, lAmount, lNode);
				}
			}
			if (mUiModel.reactComposits()) {
				String lMaterialReactionName = Types.getInstance().getMaterial(lMaterialID).getTypeName()	+ " Reaction Formula";

				if (Blueprints.getInstance().getNameList().contains(lMaterialReactionName)) {
					addMaterials(Blueprints.getInstance().getBlueprint(lMaterialReactionName).getInputMaterials(), Blueprints.getInstance().getBlueprint(lMaterialReactionName),
							true, lAmount, lNode);
				}
			}
		}
	}
}
