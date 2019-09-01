package de.lkrause.eio;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import de.lkrause.eio.collections.SecStatus;
import de.lkrause.eio.database.DataAccessException;
import de.lkrause.eio.database.DatabaseConnection;
import de.lkrause.eio.industryplanner.collections.Blueprints;
import de.lkrause.eio.model.DbConstants;
import de.lkrause.eio.model.MainModel;
import de.lkrause.eio.model.ReprocessingModel;
import de.lkrause.eio.reprocessing.ReprocessingCalculatorView;
import de.lkrause.eio.settings.ActiveOreTypes;
import de.lkrause.eio.settings.ui.ReprocessingSettingsView;
import de.lkrause.eio.ui.AutoCompleteComboBoxListener;
import de.lkrause.eio.ui.ResultsPane;
import de.lkrause.eio.ui.ResultsTreeTable;
import de.lkrause.eio.ui.SettingsInputs;
import de.lkrause.eio.ui.SettingsPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EveOverlord extends Application {

	private int mBlueprintMEValue = 0;
	private int mBlueprintTEValue = 0;
	private int mComponentMEValue = 0;
	private int mComponentTEValue = 0;
	private static Logger mLogger = Logger.getLogger("Main");
	private String mDefaultSystem = "C4C-Z4";

	private static final String NO_RIG = "No Rig";
	private static final String T1RIG = "Tech 1";
	private static final String T2RIG = "Tech 2";

	@Override
	public void start(Stage primaryStage) {
		try {
			MainModel.getInstance().setPrimaryStage(primaryStage);

			BorderPane lRootPane = new BorderPane();
			GridPane lInputTop = new GridPane();

			MenuBar lMenu = createMenuBar();

			Spinner<Integer> lBlueprintME = new Spinner<>(0, 10, 10, 1);
			Spinner<Integer> lBlueprintTE = new Spinner<>(0, 20, 20, 2);
			Spinner<Integer> lRuns = new Spinner<>(1, Integer.MAX_VALUE, 1);
			ComboBox<String> lBlueprint = new ComboBox<>(getBlueprintList());
			new AutoCompleteComboBoxListener(lBlueprint);

			lBlueprint.setStyle("-fx-min-width: 350;\n" + "-fx-max-width: 350;");

			lBlueprint.setEditable(true);

			lRuns.setEditable(true);

			CheckBox lBuildComponents = new CheckBox("Build Components");
			CheckBox lReactComposits = new CheckBox("React Composits");

			Spinner<Integer> lComponentME = new Spinner<>(0, 10, 10, 1);
			Spinner<Integer> lComponentTE = new Spinner<>(0, 20, 20, 2);

			ComboBox<String> lBlueprintEC = new ComboBox<>(FXCollections.observableArrayList("Raitaru", "Azbel",
					"Sotiyo", "Atrahus", "Fortizar", "Keepstar", "Station"));
			ComboBox<String> lComponentEC = new ComboBox<>(FXCollections.observableArrayList("Raitaru", "Azbel",
					"Sotiyo", "Atrahus", "Fortizar", "Keepstar", "Station"));
			ComboBox<String> lReactionRefinery = new ComboBox<>(
					FXCollections.observableArrayList("Athanor", "Tatara"));

			ComboBox<String> lBlueprintMERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));
			ComboBox<String> lBlueprintTERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));

			ComboBox<String> lComponentMERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));
			ComboBox<String> lComponentTERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));

			ComboBox<String> lReactionMERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));
			ComboBox<String> lReactionTERig = new ComboBox<>(
					FXCollections.observableArrayList(NO_RIG, T1RIG, T2RIG));

			UIModel.getInstance().setBlueprintME(lBlueprintME);
			UIModel.getInstance().setBlueprintTE(lBlueprintTE);

			UIModel.getInstance().setBlueprintEC(lBlueprintEC);
			UIModel.getInstance().setBlueprintMERig(lBlueprintMERig);
			UIModel.getInstance().setBlueprintTERig(lBlueprintTERig);

			UIModel.getInstance().setRuns(lRuns);

			UIModel.getInstance().setBuildComponents(lBuildComponents);
			UIModel.getInstance().setReactComposits(lReactComposits);

			UIModel.getInstance().setComponentEC(lComponentEC);
			UIModel.getInstance().setComponentMERig(lComponentMERig);
			UIModel.getInstance().setComponentTERig(lComponentTERig);

			UIModel.getInstance().setComponentME(lComponentME);
			UIModel.getInstance().setComponentTE(lComponentTE);

			UIModel.getInstance().setReactionRefinery(lReactionRefinery);
			UIModel.getInstance().setReactionMERig(lReactionMERig);

			lBlueprintEC.getSelectionModel().selectFirst();
			lComponentEC.getSelectionModel().selectFirst();
			lReactionRefinery.getSelectionModel().selectFirst();

			lBlueprintMERig.getSelectionModel().selectFirst();
			lBlueprintTERig.getSelectionModel().selectFirst();

			lComponentMERig.getSelectionModel().selectFirst();
			lComponentTERig.getSelectionModel().selectFirst();

			lReactionMERig.getSelectionModel().selectFirst();
			lReactionTERig.getSelectionModel().selectFirst();

			// TODO Combobox for systems
			TextField lBlueprintSystem = new TextField(mDefaultSystem);
			TextField lComponentSystem = new TextField(mDefaultSystem);
			TextField lReactionSystem = new TextField(mDefaultSystem);

			lInputTop.setHgap(8);
			lInputTop.setVgap(10);
			lInputTop.setAlignment(Pos.BASELINE_CENTER);

			Label lBlueprintMELabel = new Label("Blueprint ME: ");
			lBlueprintMELabel.setLabelFor(lBlueprintME);

			lInputTop.add(lBlueprint, 0, 0, 2, 1);
			lInputTop.add(lBlueprintMELabel, 2, 0, 1, 1);
			lInputTop.add(lBlueprintME, 3, 0, 1, 1);
			lInputTop.add(new Label("TE: "), 4, 0, 1, 1);
			lInputTop.add(lBlueprintTE, 5, 0, 1, 1);

			FlowPane lBuildReactFlow = new FlowPane();

			lBuildReactFlow.getChildren().addAll(lBuildComponents, lReactComposits);
			lBuildReactFlow.setHgap(8);
			lBuildReactFlow.setAlignment(Pos.BASELINE_CENTER);

			lInputTop.add(lRuns, 0, 1, 6, 1);

			lInputTop.add(lBuildReactFlow, 0, 2, 6, 1);

			lInputTop.add(new Label("Components (Adv.)"), 0, 3, 2, 1);
			lInputTop.add(new Label("Blueprint ME: "), 2, 3, 1, 1);
			lInputTop.add(lComponentME, 3, 3, 1, 1);
			lInputTop.add(new Label("TE: "), 4, 3, 1, 1);
			lInputTop.add(lComponentTE, 5, 3, 1, 1);

			SettingsPane lBlueprintECPane = new SettingsPane("Blueprint Engineering Complex",
					new SettingsInputs(lBlueprintEC, lBlueprintMERig, lBlueprintTERig, lBlueprintSystem));
			SettingsPane lComponentECPane = new SettingsPane("Component Engineering Complex",
					new SettingsInputs(lComponentEC, lComponentMERig, lComponentTERig, lComponentSystem));
			SettingsPane lReactionRefineryPane = new SettingsPane("Reaction Refinery",
					new SettingsInputs(lReactionRefinery, lReactionMERig, lReactionSystem));

			FlowPane lFlowPane = new FlowPane();
			lFlowPane.getChildren().add(new Text("Blueprints"));

			lFlowPane.getStyleClass().add("caption");

			VBox lLeftBox = new VBox(9);
			lLeftBox.getChildren().addAll(lFlowPane, lInputTop, lBlueprintECPane);
			lLeftBox.setPadding(new Insets(10, 0, 0, 0));


			BorderPane lLeftBorder = new BorderPane();
			lLeftBorder.setTop(lLeftBox);

			VBox lMultiBuild = new VBox(3);
			lMultiBuild.setPadding(new Insets(5));

			HBox lMultiBuildButtons = new HBox();


			Button lParseMultibuild = new Button("Parse");

			lMultiBuildButtons.setPadding(new Insets(5, 0, 5, 0));

			lMultiBuildButtons.getChildren().add(lParseMultibuild);
			lMultiBuildButtons.setAlignment(Pos.BASELINE_RIGHT);

			Label lMultibuildLabel = new Label("Multibuild 10/20 BPOs");
			TextArea lMultiBuildArea = new TextArea();

			lMultiBuildArea.setPrefHeight(500);

			lMultiBuild.getChildren().add(lMultibuildLabel);
			lMultiBuild.getChildren().add(lMultiBuildArea);
			lMultiBuild.getChildren().add(lMultiBuildButtons);
			lMultiBuild.setPadding(new Insets(5));

			lLeftBorder.setBottom(lMultiBuild);

			ResultsTreeTable lResultsTable = new ResultsTreeTable();

			lRootPane.setLeft(lLeftBorder);
			lRootPane.setCenter(new ResultsPane(lResultsTable));
			lRootPane.setTop(lMenu);

			lRootPane.getStyleClass().add("mainPane");

			lRootPane.setPadding(new Insets(5));

			// Actions

			lBuildComponents.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (lBuildComponents.isSelected()) {
						lLeftBox.getChildren().add(2, lComponentECPane);
					} else {
						lLeftBox.getChildren().remove(lComponentECPane);
					}
					calculate(lBlueprint, lResultsTable);
				}
			});

			lReactComposits.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (lReactComposits.isSelected()) {
						lLeftBox.getChildren().add(lReactionRefineryPane);
					} else {
						lLeftBox.getChildren().remove(lReactionRefineryPane);
					}
					calculate(lBlueprint, lResultsTable);
				}
			});

			lBlueprint.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> pObservable, String pOldValue, String pNewValue) {

					lResultsTable.calculateResults(pNewValue);

					if (pNewValue.length() > 16 && pNewValue.substring(pNewValue.length() - 16, pNewValue.length())
							.equalsIgnoreCase("Reaction Formula")) {
						mLogger.log(Level.FINE, "Reaction Formula found");

						if (lBlueprintME.getValue() != 0) {
							mBlueprintMEValue = lBlueprintME.getValue();
						}
						if (lBlueprintTE.getValue() != 0) {
							mBlueprintTEValue = lBlueprintTE.getValue();

						}
						if (lComponentME.getValue() != 0) {
							mComponentMEValue = lComponentME.getValue();

						}
						if (lComponentTE.getValue() != 0) {
							mComponentTEValue = lComponentTE.getValue();
						}


						lBlueprintME.setDisable(true);
						lBlueprintTE.setDisable(true);
						lBlueprintME.getValueFactory().setValue(0);
						lBlueprintTE.getValueFactory().setValue(0);

						lComponentME.setDisable(true);
						lComponentTE.setDisable(true);
						lComponentME.getValueFactory().setValue(0);
						lComponentTE.getValueFactory().setValue(0);

						lReactComposits.setSelected(true);
						lReactComposits.setDisable(true);

						if (!lLeftBox.getChildren().contains(lReactionRefineryPane)) {
							lLeftBox.getChildren().add(lReactionRefineryPane);
						}
					} else if (pOldValue != null && pOldValue.length() > 16
							&& pOldValue.substring(pOldValue.length() - 16, pOldValue.length())
							.equalsIgnoreCase("Reaction Formula")) {
						lBlueprintME.setDisable(false);
						lBlueprintTE.setDisable(false);
						lBlueprintME.getValueFactory().setValue(mBlueprintMEValue);
						lBlueprintTE.getValueFactory().setValue(mBlueprintTEValue);

						lComponentME.setDisable(false);
						lComponentTE.setDisable(false);
						lComponentME.getValueFactory().setValue(mComponentMEValue);
						lComponentTE.getValueFactory().setValue(mComponentTEValue);

						lReactComposits.setSelected(false);
						lReactComposits.setDisable(false);

						if (lLeftBox.getChildren().contains(lReactionRefineryPane)) {
							lLeftBox.getChildren().remove(lReactionRefineryPane);
						}

						mBlueprintMEValue = 0;
						mBlueprintTEValue = 0;
						mComponentMEValue = 0;
						mComponentTEValue = 0;
					}
				}
			});

			lBlueprintEC.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lBlueprintME.valueProperty().addListener(new IntegerChangeListener(lBlueprint, lResultsTable));

			lBlueprintTE.valueProperty().addListener(new IntegerChangeListener(lBlueprint, lResultsTable));

			lBlueprintMERig.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lBlueprintTERig.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lComponentMERig.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lComponentTERig.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lComponentME.valueProperty().addListener(new IntegerChangeListener(lBlueprint, lResultsTable));

			lComponentTE.valueProperty().addListener(new IntegerChangeListener(lBlueprint, lResultsTable));

			lComponentEC.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lReactionRefinery.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lReactionMERig.valueProperty().addListener(new StringChangeListener(lBlueprint, lResultsTable));

			lRuns.valueProperty().addListener(new IntegerChangeListener(lBlueprint, lResultsTable));

			// TODO SETTINGS
			SecStatus.setSecstatus(SecStatus.NULLSEC);

			Scene lScene = new Scene(lRootPane, 1920, 1280);
			lScene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			primaryStage.setScene(lScene);
			primaryStage.setTitle("Eve Industry Overlord");
			primaryStage.show();
		} catch (Exception e) {
			mLogger.log(Level.WARNING, "Problem with GUI Initialization (CSS is probably missing)", e);
		}
	}

	public static void main(String[] args) {

		try {
			DatabaseConnection.getInstance().setDataSource(getDataSource());
			//				DatabaseConnection.getInstance().createDatabase();

			DatabaseConnection.getInstance().checkDatabaseValidity();

			//			Types.getInstance().initializeTypeNames();

			Map<String, Integer> lOreReprocessing = new HashMap<>();

			for (String lOre : ActiveOreTypes.getInstance().getActiveOres()) {
				lOreReprocessing.put(lOre, 5);
			}

			ReprocessingModel.getInstance().setReprocessingLevels(5, 5, lOreReprocessing, 4);

			//			Ores.getInstance().outputBest();
			// Completed:
			// Initialize Blueprints with:
			initBlueprints();
			launch(args);

		} catch (DataAccessException lError) {
			new Alert(AlertType.ERROR, "Error while calling main: " + lError.getMessage(), ButtonType.CLOSE).showAndWait();
		}
		// TODO Add update function
	}

	private static DataSource getDataSource() {
		mLogger.log(Level.WARNING, "DBConnection::Connecting to Database");

		BasicDataSource lDataSource = new BasicDataSource();

		lDataSource.setDriverClassName(DbConstants.DB_DRIVER);
		lDataSource.setUsername(DbConstants.DB_USER);
		lDataSource.setPassword(DbConstants.DB_LOGIN);
		lDataSource.setUrl(DbConstants.DB_CONNECT_PREFIX + DbConstants.DEFAULT_DB_NAME);

		mLogger.log(Level.WARNING, "DBConnection::Connected to Database");

		return lDataSource;
	}

	private static void initBlueprints() {
		List<Integer> lInventables = DatabaseConnection.getInstance().getInventableBlueprints();

		List<String> lBlueprintNames = DatabaseConnection.getInstance().getAllBlueprintNames();
		List<String> lReactionNames = DatabaseConnection.getInstance().getAllReactionNames();

		lBlueprintNames.addAll(lReactionNames);

		mLogger.fine("Found " + lBlueprintNames.size() + " Blueprint Names");

		Blueprints.getInstance().setBlueprintNames(lBlueprintNames);
		Blueprints.getInstance().setInventables(lInventables);

		mLogger.log(Level.FINE, "Init::Blueprints Initialized");
	}

	/**
	 * Test Function
	 */

	public static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				mLogger.log(Level.WARNING, "Connection to updater failed, desktop type not supported",
						e);
			}
		}
	}

	private ObservableList<String> getBlueprintList() {

		ObservableList<String> lList = FXCollections.observableArrayList();

		lList.addAll(Blueprints.getInstance().getNameList());

		return lList;
	}

	private void calculate(ComboBox<String> pBlueprint, ResultsTreeTable pResultsTable) {

		pResultsTable.calculateResults(pBlueprint.getValue());

	}

	private MenuBar createMenuBar() {

		Menu lFileMenu = new Menu("File");
		Menu lSettingsMenu = new Menu("Settings");

		MenuItem lReprocessing = new MenuItem("Reprocessing");
		MenuItem lStock = new MenuItem("Mineral Stock");
		MenuItem lQuit = new MenuItem("Exit");
		MenuItem lReprocessingSettings = new MenuItem("Reprocessing");


		lReprocessing.setOnAction(pEvent -> new ReprocessingCalculatorView());
		lQuit.setOnAction(pActionEvent -> Platform.exit());

		lReprocessingSettings.setOnAction(pActionEvent -> new ReprocessingSettingsView());

		lFileMenu.getItems().add(lReprocessing);
		lFileMenu.getItems().add(lQuit);
		lSettingsMenu.getItems().add(lStock);
		lSettingsMenu.getItems().add(lReprocessingSettings);


		MenuBar lMenu = new MenuBar(lFileMenu, lSettingsMenu);
		lMenu.setUseSystemMenuBar(true);
		return lMenu;
	}
}
