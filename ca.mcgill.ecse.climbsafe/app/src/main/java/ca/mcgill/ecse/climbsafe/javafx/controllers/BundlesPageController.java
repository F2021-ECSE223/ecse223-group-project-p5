package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet5Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import ca.mcgill.ecse.climbsafe.controller.TOEquipmentBundle;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;

import javafx.scene.control.Tab;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class BundlesPageController {
	@FXML
	private TabPane bundlesPageTabPane;
	@FXML
	private Tab addTab;
	@FXML
	private TextField addName;
	@FXML
	private TextField addDiscount;
	@FXML
	private TableView<TOEquipment> addEquipTable;
	@FXML
	private TableColumn<TOEquipment, String> addEquipTableName;
	@FXML
	private TableColumn<TOEquipment, String> addEquipTableWeight;
	@FXML
	private TableColumn<TOEquipment, String> addEquipTablePrice;
	@FXML
	private TableColumn<TOEquipment, String> addEquipTableQuantity;
	@FXML
	private Button addClear;
	@FXML
	private Button addADD;
	@FXML
	private TextField addTotalCost;
	@FXML
	private Tab updateTab;
	@FXML
	private TextField updateNewName;
	@FXML
    private TextField updateOldName;	
	@FXML
	private TextField updateDiscount;
	@FXML
	private TableView<TOEquipment> updateEquipTable;
	@FXML
	private TableColumn<TOEquipment, String> updateEquipTableName;
	@FXML
	private TableColumn<TOEquipment, String> updateEquipTableWeight;
	@FXML
	private TableColumn<TOEquipment, String> updateEquipTablePrice;
	@FXML
	private TableColumn<TOEquipment, String> updateEquipTableQuantity;
	@FXML
	private Button updateClear;
	@FXML
	private Button updateAutofill;
	@FXML
	private Button updateUpdate;
	@FXML
	private TextField updateTotalCost;
	@FXML
	private Tab delTab;
	@FXML
	private TableView<TOEquipmentBundle> delTable;
	@FXML
	private TableColumn<TOEquipmentBundle, String> delTableName;
	@FXML
	private TableColumn<TOEquipmentBundle, Integer> delTablePricePerWeek;
	@FXML
	private TableColumn<TOEquipmentBundle, Integer> delTableDiscount;
	@FXML
	private Button delClearSelection;
	@FXML
	private Button delDelete;
	@FXML
	private Button delModifySelected;
    
    // Fields
      private ObservableList<TOEquipment> curAddEquipments;
      private ObservableList<TOEquipment> curUpdateEquipments;
      private ObservableList<TOEquipmentBundle> curUpdateBundles;
    
      @FXML
      public void initialize() {
        this.curAddEquipments = ViewUtils.getEquipments();
        this.curUpdateEquipments = ViewUtils.getEquipments();
        
       // initSpinners();
        initDelTable();
        initAddEquipTable();
        initUpdateEquipTable();
       
      } 

      // Event Listener on Button[#addADD].onAction
      @FXML
      public void addDoAdd(ActionEvent event) {
          String name = addName.getText();
          int discount= Integer.valueOf(addDiscount.getText());
       
          List<String> itemNames = new ArrayList<String>();
          List<Integer> itemQuantities = new ArrayList<Integer>();
          
          for (var equipment : this.curAddEquipments) {
            int qty = (int) equipment.getMpQuantity().getValue();
            if (qty > 0) {
              itemNames.add(equipment.getName());
              itemQuantities.add(qty);
            }
          }
         
          // create the member
          if (ViewUtils.successful(() -> ClimbSafeFeatureSet5Controller.addEquipmentBundle(name, discount, itemNames, itemQuantities))) {
            addDoClear();
      }
      }
	// Event Listener on Button[#addClear].onAction
	@FXML
	public void addDoClear(ActionEvent event) {
	  addDoClear();
	}
	 // Event Listener on Button[#regUpdateCost].onAction
    @FXML
    public void addDoUpdateCost(ActionEvent event) {
      addDoUpdateCost();
    }

	// Event Listener on TextField[#updateName].onAction
	@FXML
	public void updateDoUpdate(ActionEvent event) {
	   String newName = updateNewName.getText();
	   String oldName = updateOldName.getText();
       int discount= Integer.parseInt(updateDiscount.getText());
             
       List<String> itemNames = new ArrayList<String>();
       List<Integer> itemQuantities = new ArrayList<Integer>();
       for (var equipment : this.curUpdateEquipments) {
         int qty = (int) equipment.getMpQuantity().getValue();
         if (qty > 0) {
           itemNames.add(equipment.getName());
           itemQuantities.add(qty);
         }
       }
       
       // modify the member
       if (ViewUtils.successful(() -> ClimbSafeFeatureSet5Controller.updateEquipmentBundle(oldName, newName, discount, itemNames, itemQuantities))) {
         updateDoClear();
       }
	}
	// Event Listener on Button[#updateClear].onAction
	@FXML
	public void updateDoClear(ActionEvent event) {
	  updateDoClear();
	}
	// Event Listener on Button[#updateAutofill].onAction
	@FXML
	public void updateDoAutofill(ActionEvent event) {
	  updateDoAutofill();
	}
	  // Event Listener on Button[#modUpdateCost].onAction
    @FXML
    public void updateDoUpdateCost(ActionEvent event) {
      updateDoUpdateCost();
    }
	// Event Listener on Button[#delClearSelection].onAction
	@FXML
	public void delDoClearSelection(ActionEvent event) {
	  delDoClearSelection();
	}
	// Event Listener on Button[#delDelete].onAction
	@FXML
	public void delDoDelete(ActionEvent event) {
	  delDoDelete();
	}
	// Event Listener on Button[#delModifySelected].onAction
	@FXML
	public void delDoModifySelected(ActionEvent event) {
	  delDoModifySelected();
	}

    /**
     * Helper method to setup the table on Delete tab
     */
    private void initDelTable() {
      /*
       * Create table mappings
       */
      delTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
      delTablePricePerWeek.setCellValueFactory(new PropertyValueFactory<>("noDiscountPrice"));
      delTableDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
      
      
      
      /*
       * Update table on refresh event
       */
      delTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
        this.delTable.setItems(ViewUtils.getEquipmentBundles());
        
       
        /*
         * Add handler for all spinners
         */
  
        
      });
      ClimbSafeView.getInstance().registerRefreshEvent(delTable);
      /*
       * Enable multi rows selection
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
                delDoModifySelected();
              }
            }
          }
        }
      });
    }
    /**
     * Helper method to auto fill the Modify tab when given a valid member email
     */
    private void updateDoAutofill() {
      var oldName = updateOldName.getText();
      TOEquipmentBundle selectedBundle = null;
      // search for the member, return if not found
      for (var bundle : ClimbSafeFeatureSet5Controller.getEquipmentBundles()) {
        if (bundle.getName().equals(oldName)) {
          selectedBundle = bundle;
        }
      }
      if (selectedBundle == null) {
        return;
      }
      updateDiscount.setText(Integer.toString(selectedBundle.getDiscount()));
      
      /*
       * Autofill bookedItems
       */
      try {
        var bookedItems = ClimbSafeFeatureSet5Controller.getBundleItems(oldName);
        for (var item : bookedItems) {
          for (var equipment : this.curUpdateEquipments) {
            if (item.getEquipmentName().equals(equipment.getName())) {
              equipment.getMpQuantity().getValueFactory().setValue(item.getQuantity());
            }
          }
      }   
        
      } catch (InvalidInputException e) {
        System.out.println(e.getMessage());
      }
      /*
       * Update totalCost
       */
      updateDoUpdateCost();
    }
    /**
     * Helper method to edit the selected member
     */
    private void delDoModifySelected() {
      var selection = delTable.getSelectionModel().getSelectedItems();
      if (selection.size() != 1) {
        ViewUtils.showError("Only one bundle must be selected");
        return;
      }
      TOEquipmentBundle bundle = (TOEquipmentBundle) selection.get(0);
      // Fill the modify tab
      updateOldName.setText(bundle.getName());
      updateDoAutofill();
      // Swap to the modify tab
      bundlesPageTabPane.getSelectionModel().select(updateTab);
    }
    /**
     * Helper method to setup the equipment table on Register tab
     */
    private void initAddEquipTable() {
      /*
       * Create table mappings
       */
      addEquipTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
      addEquipTableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
      addEquipTablePrice.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
      addEquipTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
      /*
       * Update table on refresh event
       */
      addEquipTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
        this.curAddEquipments = ViewUtils.getEquipments();
        addEquipTable.setItems(this.curAddEquipments);
        addDoUpdateCost();
        /*
         * Add handler for all spinners
         */
        for (var equipment : this.curAddEquipments) {
          equipment.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
            addDoUpdateCost();
          });
        }
      });
      ClimbSafeView.getInstance().registerRefreshEvent(addEquipTable);
    }
    /**
     * Helper method to setup the equipment table on Modify tab
     */
    private void initUpdateEquipTable() {
      /*
       * Create table mappings
       */
      updateEquipTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
      updateEquipTableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
      updateEquipTablePrice.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
      updateEquipTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
      /*
       * Update table on refresh event
       */
      updateEquipTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
        this.curUpdateEquipments = ViewUtils.getEquipments();
        updateEquipTable.setItems(this.curUpdateEquipments);
        updateDoUpdateCost();
        /*
         * Add handler for all spinners
         */
        for (var equipment : this.curUpdateEquipments) {
          equipment.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
            updateDoUpdateCost();
          });
        }
      });
      ClimbSafeView.getInstance().registerRefreshEvent(updateEquipTable);
    }
    
    /**
     * Helper method to update cost on Register tab
     */
    private void addDoUpdateCost() {
      int totalCost = 0;
      
      // Cost of equipments
      for (var equipment : this.curAddEquipments) {
        totalCost += equipment.getPricePerWeek() * (int) equipment.getMpQuantity().getValue();
      }
      addTotalCost.setText(Integer.toString(totalCost));
      
    }
    /**
     * Helper method to update cost on Modify tab
     */
    private void updateDoUpdateCost() {
      int totalCost = 0;
      
      // Cost of equipments
      for (var equipment : this.curUpdateEquipments) {
        totalCost += equipment.getPricePerWeek() * (int) equipment.getMpQuantity().getValue();
      }
      updateTotalCost.setText(Integer.toString(totalCost));
      
    }
    
    /**
     * Helper method to clear Register tab
     */
    private void addDoClear() {
      addName.setText("");
      addDiscount.setText("");
      for (var equipment : this.curAddEquipments) {
        equipment.getMpQuantity().getValueFactory().setValue(0);
      }
     
      addTotalCost.setText("0");
    }

    /**
     * Helper method to clear Modify tab
     */
    private void updateDoClear() {
     
      updateOldName.setText("");
      updateNewName.setText("");
      updateDiscount.setText("");
     
      for (var equipment : this.curUpdateEquipments) {
        equipment.getMpQuantity().getValueFactory().setValue(0);
      }
      
      updateTotalCost.setText("0");
    }
    
    /**
     * Helper method to clear Table selection
     */
    private void delDoClearSelection() {
      delTable.getSelectionModel().clearSelection();
    }
    
    /**
     * Helper method to delete members on selected rows
     */
    private void delDoDelete() {
      var selection = delTable.getSelectionModel().getSelectedItems();
      var bundleList = new ArrayList<String>();
      /*
       * Email need to be stored in another list since selection changes when a member is deleted
       */
      if (selection != null) {
        for (var bundle : selection) {
          bundleList.add(((TOEquipmentBundle)bundle).getName());
        }
      }
      for (var name : bundleList) {
        ViewUtils.successful(() -> ClimbSafeFeatureSet6Controller.deleteEquipmentBundle(name));
      }
    }

}
