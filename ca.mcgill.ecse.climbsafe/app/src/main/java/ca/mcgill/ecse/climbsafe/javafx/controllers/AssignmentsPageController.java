package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.beans.binding.Bindings;
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
	
	  // only show refund if cancelled or finished
	  var refundColumn = new TableColumn<TOAssignment, String>("Refund");
	  refundColumn.setCellValueFactory(data -> Bindings.createStringBinding(
	      () -> {
	        String status = data.getValue().getStatus();
	        if (status.equals("Finished") || status.equals("Cancelled")) {
	          return String.valueOf(data.getValue().getRefundedPercentageAmount());
	        }
	        else {
	          return null;
	        }
	      }));
	  overviewTable.getColumns().add(refundColumn);
	  
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
