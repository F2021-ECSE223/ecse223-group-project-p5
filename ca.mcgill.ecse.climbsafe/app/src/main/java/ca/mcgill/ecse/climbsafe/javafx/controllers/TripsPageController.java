package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TripsPageController {
	@FXML
	private Button startTripsButton;
	@FXML
	private Spinner<Integer> startTripsWeekField;
	@FXML
	private Label startTripsSuccessLabel;
	@FXML
	private Label startTripsFailureLabel;
	@FXML
	private TextArea startTripsResultField;
	@FXML
	private Button manageTripSearchButton;
	@FXML
	private TextField manageTripEmailField;
	@FXML
	private Label manageTripStatusLabel;
	@FXML
	private Button manageTripCancelButton;
	@FXML
	private Button manageTripFinishButton;
	
	private String targetEmail;
	
	@FXML
	public void initialize() {
	  // register elements to respond to navigation event
	  // email field should be populated with message contents
	  manageTripEmailField.addEventHandler(ClimbSafeView.NAVIGATE_TRIP_EVENT,
	      e -> manageTripEmailField.setText(e.getMessage()));
	  ClimbSafeView.getInstance().registerNavigationTripResponse(manageTripEmailField);
	  // search button should be triggered
	  manageTripSearchButton.addEventHandler(ClimbSafeView.NAVIGATE_TRIP_EVENT,
	      e -> manageTripSearchButton.fire());
	  ClimbSafeView.getInstance().registerNavigationTripResponse(manageTripSearchButton);
	  
	  // bound week selector
	  startTripsWeekField.setValueFactory(
	      new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClimbSafeFeatureSet1Controller.getNrWeeks()));
	}

	// Event Listener on Button[#startTripsButton].onAction
	@FXML
	public void startTripsPushed(ActionEvent event) {
	  Integer weekInteger = startTripsWeekField.getValue();
	  
	  try {
	    // call controller and get results
	    String[] results = AssignmentController.startTripsForWeekReturnDetails(weekInteger);
	    
	    startTripsSuccessLabel.setText(results[0]);
	    startTripsFailureLabel.setText(results[1]);
	    startTripsResultField.setText(results[2]);
	    
	    ClimbSafeView.getInstance().refresh();
	  } catch (InvalidInputException e) {
	    ViewUtils.showError(e.getMessage());
	  }
	}
	
	// Event Listener on Button[#manageTripSearchButton].onAction
	@FXML
	public void searchEmailPushed(ActionEvent event) {
	  String emailString = manageTripEmailField.getText();
	  
	  try {
	    TOAssignment assignment = ClimbSafeFeatureSet6Controller.getAssignment(emailString);
	    
	    this.targetEmail = emailString;
	    
	    manageTripStatusLabel.setText(assignment.getStatus());
	      
	    // determine which buttons should be disabled
	    boolean disableCancel = false;
	    boolean disableFinish = false;
	    
	    switch (assignment.getStatus()) {
	      // can do neither when unassigned, finished, or cancelled
	      case "Unassigned":
	      case "Finished":
	      case "Cancelled":
	        disableCancel = true;
	      // cannot finish when assigned or paid
	      case "Assigned":
          case "Paid":
            disableFinish = true;
            break;
	    }
	      
	    manageTripCancelButton.setDisable(disableCancel);
	    manageTripFinishButton.setDisable(disableFinish);
	  } catch (InvalidInputException e) {
	    ViewUtils.showError(e.getMessage());
	  }
	}
	
	// Event Listener on Button[#manageTripCancelButton].onAction
	@FXML
	public void cancelTripPushed(ActionEvent event) {
	  if (ViewUtils.successful(() -> AssignmentController.cancelTrip(targetEmail))) {
	    ViewUtils.makePopupWindow("Success", String.format("Trip for %s cancelled", targetEmail));
	  }
	  
	  targetEmail = null;
	  clearManageInputs();
	}
	
	// Event Listener on Button[#manageTripFinishButton].onAction
	@FXML
	public void finishTripPushed(ActionEvent event) {
	  if (ViewUtils.successful(() -> AssignmentController.cancelTrip(targetEmail))) {
        ViewUtils.makePopupWindow("Success", String.format("Trip for %s finished", targetEmail));
      }
	  
      targetEmail = null;
      clearManageInputs();
	}
	
	private void clearManageInputs() {
	  manageTripEmailField.setText(null);
	  manageTripStatusLabel.setText(null);
	  manageTripCancelButton.setDisable(true);
      manageTripFinishButton.setDisable(true);
	}
}
