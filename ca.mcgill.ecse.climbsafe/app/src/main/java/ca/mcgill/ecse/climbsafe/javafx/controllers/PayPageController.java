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

public class PayPageController {

  @FXML
  private TextField authCodeField;
  @FXML
  private TableView<TOAssignment> memberTable;
  @FXML
  private TableColumn<TOAssignment, String> emailColumn;
  @FXML
  private TableColumn<TOAssignment, String> nameColumn;
  @FXML
  private Button payButton;
  @FXML
  private Label selectedMemberLabel;
  @FXML
  private Label tripPriceLabel;

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
  }

  private void initMemberTable() {
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
    memberTable
        .setItems(FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments()));

    memberTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      memberTable
          .setItems(FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments()));
    });
    ClimbSafeView.getInstance().registerRefreshEvent(memberTable);
    memberTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    memberTable.setOnMouseClicked(e -> {
      TOAssignment selectedTrip = memberTable.getSelectionModel().getSelectedItem();

      selectedMemberLabel.setText(selectedTrip.getMemberEmail());
      tripPriceLabel.setText(Integer
          .toString(selectedTrip.getTotalCostForGuide() + selectedTrip.getTotalCostForEquipment()));
    });
  }

  private void initLabels() {
    selectedMemberLabel.setText("None Selected");
    tripPriceLabel.setText("None Selected");
  }

  @FXML
  void initialize() {
    initMemberTable();
    initLabels();
  }
}
