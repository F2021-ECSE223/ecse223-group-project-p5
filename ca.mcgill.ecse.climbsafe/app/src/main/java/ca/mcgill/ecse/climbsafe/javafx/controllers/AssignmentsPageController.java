package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AssignmentsPageController {
	@FXML
	private Button initiateAssignmentsButton;
	@FXML
	private Button manageTripButton;
	@FXML
	private TableView<TOAssignment> assignmentsOverviewTable;
	@FXML
	private Label selectedMemberLabel;
	@FXML
    private Label selectedGuideLabel;
	@FXML
    private Label selectedHotelLabel;
	@FXML
    private Label selectedDurationLabel;
	@FXML
    private Label selectedGuideCostLabel;
	@FXML
    private Label selectedEquipmentCostLabel;
	@FXML
    private Label selectedPaymentCodeLabel;
	@FXML
    private Label selectedStatusLabel;
	
	@FXML
	public void initialize() {
	  // assign member name and email to columns of overview table
	  assignmentsOverviewTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberName"));
	  assignmentsOverviewTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
	  
	  // double click listener on overview table
	  assignmentsOverviewTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	      if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
	        if (mouseEvent.getClickCount() == 1) {
	          // display information for selected assignment
	          TOAssignment target = assignmentsOverviewTable.getSelectionModel().getSelectedItem();
	          if (target != null) {
	            updateDetailsWindow(target);
	          }
	        }
	        else if (mouseEvent.getClickCount() == 2) {
	          manageTripPressed(null);
	        }
	      }
	    }
	  });
	  
	  // set table to refresh upon trigger
	  assignmentsOverviewTable.addEventHandler(ClimbSafeView.REFRESH_EVENT,
	      e -> assignmentsOverviewTable.setItems(ViewUtils.getAssignments()));
	  ClimbSafeView.getInstance().registerRefreshEvent(assignmentsOverviewTable);
	}

	// Event Listener on Button[#initiateAssignmentsButton].onAction
	@FXML
	public void initiateAssignmentsPressed(ActionEvent event) {
	  ViewUtils.callController(() -> AssignmentController.initiateAssignment());
	}
	
	// Event Listener on Button[#manageTripButton].onAction
	@FXML
	public void manageTripPressed(ActionEvent event) {
	  // open manage trip page for selected trip
	  TOAssignment target = assignmentsOverviewTable.getSelectionModel().getSelectedItem();
	  if (target != null) {
	    ClimbSafeView.getInstance().activateTripsPage(target.getMemberEmail());
	  }
	}
	
	private void updateDetailsWindow(TOAssignment target) {
	  selectedMemberLabel.setText(String.format("%s (%s)", target.getMemberName(), target.getMemberEmail()));
	  
	  if (target.getGuideEmail() != null && !target.getGuideEmail().isEmpty()) {
	    selectedGuideLabel.setText(String.format("%s (%s)", target.getGuideName(), target.getGuideEmail()));
	  }
	  else {
	    selectedGuideLabel.setText("Not assigned");
	  }
	  
	  if (target.getHotelName() != null && !target.getHotelName().isEmpty()) {
        selectedHotelLabel.setText(String.format("%s", target.getHotelName()));
      }
      else {
        selectedHotelLabel.setText("Not assigned");
      }
	  
	  selectedDurationLabel.setText(String.format("Week %d -> Week %d", target.getStartWeek(), target.getEndWeek()));
	  
	  selectedGuideCostLabel.setText(String.format("$%dNEP", target.getTotalCostForGuide()));
	  
	  selectedEquipmentCostLabel.setText(String.format("$%dNEP", target.getTotalCostForEquipment()));
	  
	  if (target.getAuthorizationCode() != null && !target.getAuthorizationCode().isEmpty()) {
	    selectedPaymentCodeLabel.setText(String.format("%s", target.getAuthorizationCode()));
      }
      else {
        selectedPaymentCodeLabel.setText("Not paid yet");
      }
	  
	  switch (target.getStatus()) {
	    case "Unassigned":
	    case "Assigned":
	    case "Paid":
	    case "Started":
	      selectedStatusLabel.setText(target.getStatus());
	      break;
	    case "Finished":
	    case "Cancelled":
	      selectedStatusLabel.setText(String.format("%s (%d%% refund)", target.getStatus(), target.getRefundedPercentageAmount()));
	  }
	}
}
