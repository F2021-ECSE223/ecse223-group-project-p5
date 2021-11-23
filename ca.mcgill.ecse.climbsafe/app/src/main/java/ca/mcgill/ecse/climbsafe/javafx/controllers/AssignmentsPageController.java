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
	private TableView<TOAssignment> overviewTable;
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
	  overviewTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberName"));
	  overviewTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
	  
	  // double click listener on overview table
	  overviewTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	      if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
	        if (mouseEvent.getClickCount() == 1) {
	          // display information for selected assignment
	          TOAssignment target = overviewTable.getSelectionModel().getSelectedItem();
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
	  overviewTable.addEventHandler(ClimbSafeView.REFRESH_EVENT,
	      e -> overviewTable.setItems(ViewUtils.getAssignments()));
	  ClimbSafeView.getInstance().registerRefreshEvent(overviewTable);
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
	  TOAssignment target = overviewTable.getSelectionModel().getSelectedItem();
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
	
	/**
	 * helper function to link a table column to a specific property in each
	 * TOAssignment instance
	 * 
	 * Based off of function from Tutorial 10
	 * 
	 * @param header the name to appear in the table
	 * @param propertyName the corresponding property name in TOAssignment
	 * @return the table column
	 * @author Michael Grieco
	 */
	private static TableColumn<TOAssignment, String> createTableColumn(String header,
	      String propertyName) {
	    TableColumn<TOAssignment, String> column = new TableColumn<>(header);
	    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	    return column;
	}
}
