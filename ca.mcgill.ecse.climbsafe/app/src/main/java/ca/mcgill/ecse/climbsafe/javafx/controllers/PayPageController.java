package ca.mcgill.ecse.climbsafe.javafx.controllers;

import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * Controller Class for the <code>PayPage.fxml</code>
 * 
 * @author Harrison Wang
 */
public class PayPageController {

  @FXML
  private AnchorPane rootPane;
  @FXML
  private TextField authCodeField;
  @FXML
  private TableView<TOAssignment> memberTable;
  @FXML
  private TableColumn<TOAssignment, String> emailColumn;
  @FXML
  private TableColumn<TOAssignment, String> nameColumn;
  @FXML
  private TableColumn<TOAssignment, String> statusColumn;
  @FXML
  private Button payButton;
  @FXML
  private Label selectedMemberLabel;
  @FXML
  private Label tripPriceLabel;

  /**
   * Event listener on {@link #payButton} for {@link ActionEvent}
   * 
   * @param event the <code>ActionEvent</code> instance received
   * @author Harrison Wang
   */
  @FXML
  void pay(ActionEvent event) {
    if (memberTable.getSelectionModel().getSelectedItem() == null) {
      ViewUtils.showError("Please select a Member Trip");
      return;
    }
    ViewUtils.callController(() -> {
      final String memberEmail = selectedMemberLabel.getText();
      final String authCode = authCodeField.getText();

      AssignmentController.confirmPayment(memberEmail, authCode);
    });
    authCodeField.setText(null);
  }

  /**
   * Helper method to initialize {@link #memberTable}. Sets up cell value factories for each column,
   * defines the behavior for MouseEvents on memberTable, and registers memberTable to be updated
   * when ClimbSafeView fires a REFRESH_EVENT.
   * 
   * @author Harrison Wang
   */
  private void initMemberTable() {
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    memberTable
        .setItems(FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments()));

    memberTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      memberTable
          .setItems(FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments()));
    });
    ClimbSafeView.getInstance().registerRefreshEvent(memberTable);
    memberTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    // Update view labels when a row is selected in memberTable
    memberTable.setOnMouseClicked(e -> {
      if (e.getButton().equals(MouseButton.PRIMARY)) {
        TOAssignment selectedTrip = memberTable.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
          selectedMemberLabel.setText(selectedTrip.getMemberEmail());
          tripPriceLabel.setText(Integer.toString(
              selectedTrip.getTotalCostForGuide() + selectedTrip.getTotalCostForEquipment()));
        }
      }
    });
  }

  /**
   * Helper method to initialize {@link #selectedMemberLabel} and {@link #tripPriceLabel}.
   * 
   * @author Harrison Wang
   */
  private void initLabels() {
    selectedMemberLabel.setText("None Selected");
    tripPriceLabel.setText("None Selected");
  }

  /**
   * Initialize method, called when this page is first created.
   * 
   * @author Harrison Wang
   */
  @FXML
  void initialize() {
    // deselect any focused elements when the user clicks away
    rootPane.setOnMouseClicked(e -> {
      rootPane.requestFocus();
    });
    initMemberTable();
    initLabels();
  }
}
