package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AssignmentsPageController {
	@FXML
	private Button initiateAssignmentsButton;
	@FXML
	private TableView<TOAssignment> overviewTable;
	/*
	 * memberEmail;
  memberName;
  guideEmail;
  guideName;
  hotelName;
  Integer startWeek;
  Integer endWeek;
  Integer totalCostForGuide;
  Integer totalCostForEquipment;
  status;
  authorizationCode;
  Integer refundedPercentageAmount;
	 */
	
	@FXML
	public void initialize() {
	  overviewTable.getColumns().add(createTableColumn("Member Email", "memberEmail"));
	  overviewTable.getColumns().add(createTableColumn("Member Name", "memberName"));
	  overviewTable.getColumns().add(createTableColumn("Guide Email", "guideEmail"));
	  overviewTable.getColumns().add(createTableColumn("Guide Name", "guideName"));
	  overviewTable.getColumns().add(createTableColumn("Hotel Name", "hotelName"));
	  overviewTable.getColumns().add(createTableColumn("Start Week", "startWeek"));
	  overviewTable.getColumns().add(createTableColumn("End Week", "endWeek"));
	  overviewTable.getColumns().add(createTableColumn("Guide Cost", "totalCostForGuide"));
	  overviewTable.getColumns().add(createTableColumn("Equipment Cost", "totalCostForEquipment"));
	  overviewTable.getColumns().add(createTableColumn("Status", "status"));
	  overviewTable.getColumns().add(createTableColumn("Authorization Code", "authorizationCode"));
	  overviewTable.getColumns().add(createTableColumn("Refund", "refundedPercentageAmount"));
	
	  overviewTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> overviewTable.setItems(getAssignmentItems()));
	  ClimbSafeView.getInstance().registerRefreshEvent(overviewTable);
	}

	// Event Listener on Button[#initiateAssignmentsButton].onAction
	@FXML
	public void initiateAssignmentsPressed(ActionEvent event) {
	  System.out.println(ViewUtils.successful(() -> AssignmentController.initiateAssignment()));
	}
	
	private ObservableList<TOAssignment> getAssignmentItems() {
	  return FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments());
	}
	
	private static TableColumn<TOAssignment, String> createTableColumn(String header,
	      String propertyName) {
	    TableColumn<TOAssignment, String> column = new TableColumn<>(header);
	    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	    return column;
	}
}
