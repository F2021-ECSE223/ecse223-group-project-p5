package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import java.util.ArrayList;


import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class EquipmentsPageController {
	@FXML
	private TabPane equipmentsPageTabPane;
	@FXML
	private Tab addEquipmentTab;
	@FXML
	private Tab updateEquipmentTab;
	@FXML
	private Tab delEquipmentTab;
	@FXML
	private TextField addName;
	@FXML
	private TextField addWeight;
	@FXML
	private TextField addPrice;
	@FXML
	private Button addClear;
	@FXML
	private Button addAdd;
	@FXML
	private TextField updateNewName;
	@FXML
	private TextField updateOldName;
	@FXML
	private TextField updateWeight;
	@FXML
	private TextField updatePrice;
	@FXML
	private Button updateClear;
	@FXML
	private Button updateAutofill;
	@FXML
	private Button updateUpdate;
	@FXML
	private TableView<TOEquipment> delTable;
	@FXML
	private TableColumn<TOEquipment, String> delTableName;
	@FXML
	private TableColumn<TOEquipment, Integer> delTableWeight;
	@FXML
	private TableColumn<TOEquipment, Integer> delTablePrice;
	@FXML
	private Button delClearSelection;
	@FXML
	private Button delDelete;
	@FXML
	private Button delModifySelected;
	
	  @FXML
	  public void initialize() {

	    initEquipmentTable();

	  }

	private void initEquipmentTable() {
	    /*
	     * Create table mappings
	     */
		delTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
	    delTableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
	    delTablePrice.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
	    
	    /*
	     * Update table on refresh event
	     */
	    delTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
	      delTable.setItems(ViewUtils.getEquipments());
	    });
	    ClimbSafeView.getInstance().registerRefreshEvent(delTable);
	    /*
	     * Enable multiple rows selection
	     */
	    delTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    /*
	     * Add double click handler
	     */
	    delTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      @Override
	      public void handle(MouseEvent mouseEvent) {
	        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
	          if (mouseEvent.getClickCount() == 2) {
	            if (delTable.getSelectionModel().getSelectedItem() != null) {
	              delEquipmentModifySelected();
	            }
	          }
	        }
	      }
        });
	  }
	
	private void delEquipmentModifySelected() {
		var selection = delTable.getSelectionModel().getSelectedItems();
	    if (selection.size() != 1) {
	      ViewUtils.showError("Only one equipment must be selected");
	      return;
	    }
	    TOEquipment equipment = selection.get(0);
	    // Fill the update tab
	    updateOldName.setText(equipment.getName());
	    updateAutofill();
	    // Swap to the update tab
	    equipmentsPageTabPane.getSelectionModel().select(updateEquipmentTab);
		
	}

	private void updateAutofill() {
	    var name = updateOldName.getText();
	    TOEquipment selectedEquipment = null;
	    // search for the equipment, return if not found
	    for (var equipment : ClimbSafeFeatureSet4Controller.getEquipments()) {
	      if (equipment.getName().equals(name)) {
	        selectedEquipment = equipment;
	      }
	    }
	    if (selectedEquipment == null) {
	      return;
	    }
	    updateNewName.setText(name);
	    updateWeight.setText(Integer.toString(selectedEquipment.getWeight()));
	    updatePrice.setText(Integer.toString(selectedEquipment.getPricePerWeek()));
	    
	  }
	// Event Listener on TextField[#addName].onAction
	@FXML
	public void addDoAdd(ActionEvent event) {
		String name = addName.getText();
		try {
			Integer weight = Integer.valueOf(addWeight.getText());
			Integer pricePerWeek = Integer.valueOf(addPrice.getText());
			// Create equipment
			if (ViewUtils.successful(
			        () -> ClimbSafeFeatureSet4Controller.addEquipment(name, weight, pricePerWeek))) {
			      addEquipmentDoClear();
			    }
			
		}catch (NumberFormatException e) {
			ViewUtils.showError("both weight and price per week must be numbers");
		}
    }
	
	
	private void addEquipmentDoClear() {
		// TODO Auto-generated method stub
		addName.setText("");
	    addWeight.setText("");
	    addPrice.setText("");
	}

	// Event Listener on Button[#addClear].onAction
	@FXML
	public void addDoClear(ActionEvent event) {
		// TODO Autogenerated
		addEquipmentDoClear();
	}

	// Event Listener on TextField[#updateName].onAction
	@FXML
	public void updateDoUpdate(ActionEvent event) {
		// TODO Autogenerated
		String newname = updateNewName.getText();
		String oldname = updateOldName.getText();
				
		try {
		  Integer weight = Integer.valueOf(updateWeight.getText());
		  Integer pricePerWeek = Integer.valueOf(updatePrice.getText());
		  
		  if (ViewUtils.successful(
		      () -> ClimbSafeFeatureSet4Controller.updateEquipment(oldname, newname, weight, pricePerWeek))) {
		    updateEquipmentDoClear();
		  }
		  
		  }catch (NumberFormatException e) {
		    ViewUtils.showError("both weight and price per week must be numbers");
		  }
	}

	private void updateEquipmentDoClear() {
		// TODO Auto-generated method stub
		updateOldName.setText("");
		updateNewName.setText("");
		updateWeight.setText("");
		updatePrice.setText("");
	}

	// Event Listener on Button[#updateClear].onAction
	@FXML
	public void updateDoClear(ActionEvent event) {
		// TODO Autogenerated
		updateEquipmentDoClear();
	}
	// Event Listener on Button[#updateAutofill].onAction
	@FXML
	public void updateDoAutofill(ActionEvent event) {
		// TODO Autogenerated
		updateAutofill();
	}

	// Event Listener on Button[#delClearSelection].onAction
	@FXML
	public void delDoClearSelection(ActionEvent event) {
		// TODO Autogenerated
		delEquipmentClearSelection();
		
		
	}
	
	private void delEquipmentClearSelection() {
		// TODO Auto-generated method stub
		delTable.getSelectionModel().clearSelection();
	}

	private void delEquipmentDoDelete() {
		// TODO Auto-generated method stub
		var selection = delTable.getSelectionModel().getSelectedItems();
	    var nameList = new ArrayList<String>();
	    /*
	     * Email need to be stored in another list since selection changes when a guide is deleted
	     */
	    if (selection != null) {
	      for (var equipment : selection) {
	        nameList.add(equipment.getName());
	      }
	    }
	    for (var name : nameList) {
	      ViewUtils.successful(() -> ClimbSafeFeatureSet6Controller.deleteEquipment(name));
	    }
	}

	// Event Listener on Button[#delDelete].onAction
	@FXML
	public void delDoDelete(ActionEvent event) {
		// TODO Autogenerated
		delEquipmentDoDelete();
	}
	// Event Listener on Button[#delModifySelected].onAction
	@FXML
	public void delDoModifySelected(ActionEvent event) {
		// TODO Autogenerated
		delEquipmentModifySelected();
	}
	
}
	
