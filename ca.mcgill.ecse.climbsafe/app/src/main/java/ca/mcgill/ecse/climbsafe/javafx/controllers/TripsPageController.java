package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
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
	  // bound week selector
	  startTripsWeekField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
	}

	// Event Listener on Button[#startTripsButton].onAction
	@FXML
	public void startTripsPushed(ActionEvent event) {
	  Integer weekInteger = startTripsWeekField.getValue();
	  System.out.println("Start trips for week " + weekInteger.toString());
	  
	  startTripsSuccessLabel.setText("x trips started");
	  startTripsFailureLabel.setText("x trips failed");
	  startTripsResultField.setText("More details here");
	}
	
	// Event Listener on Button[#manageTripSearchButton].onAction
	@FXML
	public void searchEmailPushed(ActionEvent event) {
	  String emailString = manageTripEmailField.getText();
	  System.out.println("Manage trip for member " + emailString);
	  this.targetEmail = emailString;
	  
	  manageTripCancelButton.setDisable(false);
	  manageTripFinishButton.setDisable(false);
	}
	
	// Event Listener on Button[#manageTripCancelButton].onAction
	@FXML
	public void cancelTripPushed(ActionEvent event) {
	  System.out.println("Cancel trip for " + targetEmail);
	  targetEmail = null;
	  clearManageInputs();
	}
	
	// Event Listener on Button[#manageTripFinishButton].onAction
	@FXML
	public void finishTripPushed(ActionEvent event) {
	  System.out.println("Cancel trip for " + targetEmail);
      targetEmail = null;
      clearManageInputs();
	}
	
	private void clearManageInputs() {
	  manageTripEmailField.setText(null);
	  manageTripCancelButton.setDisable(true);
      manageTripFinishButton.setDisable(true);
	}
}
