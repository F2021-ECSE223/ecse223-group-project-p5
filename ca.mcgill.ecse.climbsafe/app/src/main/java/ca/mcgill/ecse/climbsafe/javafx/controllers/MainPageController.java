package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * View controller for the MainPage
 * 
 * @author Jimmy Sheng
 *
 */
public class MainPageController {
  @FXML
  private Button topBarCloseButton;
  @FXML
  private ToggleGroup dashboardMenuGroup;
  @FXML
  private ToggleButton dashboardSetupNMCButton;
  @FXML
  private ToggleButton dashboardMembersButton;
  @FXML
  private ToggleButton dashboardGuidesButton;
  @FXML
  private ToggleButton dashboardEquipmentsButton;
  @FXML
  private ToggleButton dashboardBundlesButton;
  @FXML
  private ToggleButton dashboardAssignmentsButton;
  @FXML
  private ToggleButton dashboardPayButton;
  @FXML
  private ToggleButton dashboardTripsButton;
  @FXML
  private Pane setupNMCPane;
  @FXML
  private Pane membersPane;
  @FXML
  private Pane guidesPane;
  @FXML
  private Pane equipmentsPane;
  @FXML
  private Pane bundlesPane;
  @FXML
  private Pane assignmentsPane;
  @FXML
  private Pane payPane;
  @FXML
  private Pane tripsPane;

  @FXML
  public void initialize() {
    // always keep one button selected
    dashboardMenuGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
      if (newVal == null)
        oldVal.setSelected(true);
    });
  }

  /**
   * Exit program when close button is pressed
   * 
   * @param event listener on Button[#topBarCloseButton].onAction
   */
  @FXML
  public void topBarCloseClicked(ActionEvent event) {
    Platform.exit();
  }

  /**
   * Bring Setup NMC window to front
   * 
   * @param event listener on ToggleButton[#dashboardSetupNMCButton].onAction
   */
  @FXML
  public void dashboardSetupNMCSelected(ActionEvent event) {
    setupNMCPane.toFront();
  }

  /**
   * Bring Members window to front
   * 
   * @param event listener on ToggleButton[#dashboardMembersButton].onAction
   */
  @FXML
  public void dashboardMembersSelected(ActionEvent event) {
    membersPane.toFront();
  }

  /**
   * Bring Guides window to front
   * 
   * @param event listener on ToggleButton[#dashboardGuidesButton].onAction
   */
  @FXML
  public void dashboardGuidesSelected(ActionEvent event) {
    guidesPane.toFront();
  }

  /**
   * Bring Equipments window to front
   * 
   * @param event listener on ToggleButton[#dashboardEquipmentsButton].onAction
   */
  @FXML
  public void dashboardEquipmentsSelected(ActionEvent event) {
    equipmentsPane.toFront();
  }

  /**
   * Bring Bundles window to front
   * 
   * @param event listener on ToggleButton[#dashboardBundlesButton].onAction
   */
  @FXML
  public void dashboardBundlesSelected(ActionEvent event) {
    bundlesPane.toFront();
  }

  /**
   * Bring Assignments window to front
   * 
   * @param event listener on ToggleButton[#dashboardAssignmentsButton].onAction
   */
  @FXML
  public void dashboardAssignmentsSelected(ActionEvent event) {
    assignmentsPane.toFront();
  }

  /**
   * Bring Pay window to front
   * 
   * @param event listener on ToggleButton[#dashboardPayButton].onAction
   */
  @FXML
  public void dashboardPaySelected(ActionEvent event) {
    payPane.toFront();
  }

  /**
   * Bring Trips window to front
   * 
   * @param event listener on ToggleButton[#dashboardTripsButton].onAction
   */
  @FXML
  public void dashboardTripsSelected(ActionEvent event) {
    tripsPane.toFront();
  }
}
