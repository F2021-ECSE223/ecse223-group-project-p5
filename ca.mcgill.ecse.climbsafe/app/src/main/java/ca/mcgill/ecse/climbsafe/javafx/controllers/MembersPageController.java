package ca.mcgill.ecse.climbsafe.javafx.controllers;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import ca.mcgill.ecse.climbsafe.controller.TOEquipmentBundle;
import ca.mcgill.ecse.climbsafe.controller.TOMember;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * View controller for MembersPage
 * 
 * @author Jimmy Sheng
 *
 */
public class MembersPageController {
  @FXML
  private TextField regEmail;
  @FXML
  private PasswordField regPassword;
  @FXML
  private TextField regPasswordShow;
  @FXML
  private TextField regName;
  @FXML
  private TextField regContact;
  @FXML
  private Spinner<Integer> regWeeks;
  @FXML
  private RadioButton regNeedGuideYes;
  @FXML
  private ToggleGroup regNeedGuide;
  @FXML
  private RadioButton regNeedGuideNo;
  @FXML
  private RadioButton regNeedHotelYes;
  @FXML
  private ToggleGroup regNeedHotel;
  @FXML
  private RadioButton regNeedHotelNo;
  @FXML
  private TableView<TOEquipment> regEquipTable;
  @FXML
  private TableColumn<TOEquipment, String> regEquipTableName;
  @FXML
  private TableColumn<TOEquipment, Integer> regEquipTableWeight;
  @FXML
  private TableColumn<TOEquipment, Integer> regEquipTablePrice;
  @FXML
  private TableColumn<TOEquipment, Spinner<Integer>> regEquipTableQuantity;
  @FXML
  private TableView<TOEquipmentBundle> regBundleTable;
  @FXML
  private TableColumn<TOEquipmentBundle, String> regBundleTableName;
  @FXML
  private TableColumn<TOEquipmentBundle, Integer> regBundleTableDiscount;
  @FXML
  private TableColumn<TOEquipmentBundle, Integer> regBundleTablePrice;
  @FXML
  private TableColumn<TOEquipmentBundle, Spinner<Integer>> regBundleTableQuantity;
  @FXML
  private Button regClear;
  @FXML
  private Button regRegister;
  @FXML
  private TextField regTotalCost;
  @FXML
  private TextField modEmail;
  @FXML
  private PasswordField modPassword;
  @FXML
  private TextField modPasswordShow;
  @FXML
  private TextField modName;
  @FXML
  private TextField modContact;
  @FXML
  private Spinner<Integer> modWeeks;
  @FXML
  private RadioButton modNeedGuideYes;
  @FXML
  private ToggleGroup modNeedGuide;
  @FXML
  private RadioButton modNeedGuideNo;
  @FXML
  private RadioButton modNeedHotelYes;
  @FXML
  private ToggleGroup modNeedHotel;
  @FXML
  private RadioButton modNeedHotelNo;
  @FXML
  private TableView<TOEquipment> modEquipTable;
  @FXML
  private TableColumn<TOEquipment, String> modEquipTableName;
  @FXML
  private TableColumn<TOEquipment, Integer> modEquipTableWeight;
  @FXML
  private TableColumn<TOEquipment, Integer> modEquipTablePrice;
  @FXML
  private TableColumn<TOEquipment, Spinner<Integer>> modEquipTableQuantity;
  @FXML
  private TableView<TOEquipmentBundle> modBundleTable;
  @FXML
  private TableColumn<TOEquipmentBundle, String> modBundleTableName;
  @FXML
  private TableColumn<TOEquipmentBundle, Integer> modBundleTableDiscount;
  @FXML
  private TableColumn<TOEquipmentBundle, Integer> modBundleTablePrice;
  @FXML
  private TableColumn<TOEquipmentBundle, Spinner<Integer>> modBundleTableQuantity;
  @FXML
  private Button modClear;
  @FXML
  private Button modAutofill;
  @FXML
  private Button modModify;
  @FXML
  private TextField modTotalCost;
  @FXML
  private TableView<TOMember> delTable;
  @FXML
  private TableColumn<TOMember, String> delTableName;
  @FXML
  private TableColumn<TOMember, String> delTableEmail;
  @FXML
  private TableColumn<TOMember, String> delTableContact;
  @FXML
  private TableColumn<TOMember, Integer> delTableWeeks;
  @FXML
  private TableColumn<TOMember, Boolean> delTableGuide;
  @FXML
  private TableColumn<TOMember, Boolean> delTableHotel;
  @FXML
  private TableColumn<TOMember, String> delTableBanned;
  @FXML
  private Button delClearSelection;
  @FXML
  private Button delDelete;

  // Fields
  private ObservableList<TOEquipment> curRegEquipments;
  private ObservableList<TOEquipment> curModEquipments;
  private ObservableList<TOEquipmentBundle> curRegBundles;
  private ObservableList<TOEquipmentBundle> curModBundles;

  @FXML
  public void initialize() {
    initSpinners();
    initDelTable();
    initRegEquipTable();
    initRegBundleTable();
    initModEquipTable();
    initModBundleTable();
  }

  // Event Listener on Button[#regClear].onAction
  @FXML
  public void regDoClear(ActionEvent event) {
    regDoClear();
  }

  // Event Listener on Button[#regUpdateCost].onAction
  @FXML
  public void regDoUpdateCost(ActionEvent event) {
    regDoUpdateCost();
  }

  // Event Listener on Button[#regRegister].onAction
  @FXML
  public void regDoRegister(ActionEvent event) {
    String email = regEmail.getText();
    String password = regPassword.getText();
    String name = regName.getText();
    String contact = regContact.getText();
    int nrWeeks = regWeeks.getValue();
    Boolean guideRequired = regNeedGuideYes.isSelected();
    Boolean hotelRequired = regNeedHotelYes.isSelected();
    List<String> itemNames = new ArrayList<String>();
    List<Integer> itemQuantities = new ArrayList<Integer>();
    for (var equipment : this.curRegEquipments) {
      itemNames.add(equipment.getName());
      itemQuantities.add((int) equipment.getMpQuantity().getValue());
    }
    for (var bundle : this.curRegBundles) {
      itemNames.add(bundle.getName());
      itemQuantities.add((int) bundle.getMpQuantity().getValue());
    }
    // create the member
    if (ViewUtils.successful(() -> ClimbSafeFeatureSet2Controller.registerMember(email, password,
        name, contact, nrWeeks, guideRequired, hotelRequired, itemNames, itemQuantities))) {
      regDoClear();
    }
  }

  // Event Listener on Button[#modClear].onAction
  @FXML
  public void modDoClear(ActionEvent event) {
    modDoClear();
  }

  // Event Listener on Button[#modAutofill].onAction
  @FXML
  public void modDoAutofill(ActionEvent event) {
    modDoAutofill();
  }

  // Event Listener on Button[#modUpdateCost].onAction
  @FXML
  public void modDoUpdateCost(ActionEvent event) {
    modDoUpdateCost();
  }

  // Event Listener on Button[#modModify].onAction
  @FXML
  public void modDoModify(ActionEvent event) {
    String email = modEmail.getText();
    String password = modPassword.getText();
    String name = modName.getText();
    String contact = modContact.getText();
    int nrWeeks = modWeeks.getValue();
    Boolean guideRequired = modNeedGuideYes.isSelected();
    Boolean hotelRequired = modNeedHotelYes.isSelected();
    List<String> itemNames = new ArrayList<String>();
    List<Integer> itemQuantities = new ArrayList<Integer>();
    for (var equipment : this.curModEquipments) {
      itemNames.add(equipment.getName());
      itemQuantities.add((int) equipment.getMpQuantity().getValue());
    }
    for (var bundle : this.curModBundles) {
      itemNames.add(bundle.getName());
      itemQuantities.add((int) bundle.getMpQuantity().getValue());
    }
    // modify the member
    if (ViewUtils.successful(() -> ClimbSafeFeatureSet2Controller.updateMember(email, password,
        name, contact, nrWeeks, guideRequired, hotelRequired, itemNames, itemQuantities))) {
      modDoClear();
    }
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

  /**
   * Helper method to initialize weeks spinners The max number is nrWeeks of the model
   */
  private void initSpinners() {
    Integer nrWeeks = ClimbSafeFeatureSet1Controller.getNrWeeks();
    /*
     * Update max number on refresh event
     */
    regWeeks.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      if (nrWeeks > 0) {
        var regWeeksSpinnerFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, nrWeeks, 1);
        regWeeksSpinnerFactory.setWrapAround(true);
        regWeeks.setValueFactory(regWeeksSpinnerFactory);
      } else {
        var regWeeksSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        regWeeksSpinnerFactory.setWrapAround(true);
        regWeeks.setValueFactory(regWeeksSpinnerFactory);
      }
    });
    modWeeks.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      if (nrWeeks > 0) {
        var modWeeksSpinnerFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, nrWeeks, 1);
        modWeeksSpinnerFactory.setWrapAround(true);
        modWeeks.setValueFactory(modWeeksSpinnerFactory);
      } else {
        var modWeeksSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        modWeeksSpinnerFactory.setWrapAround(true);
        modWeeks.setValueFactory(modWeeksSpinnerFactory);
      }
    });
    ClimbSafeView.getInstance().registerRefreshEvent(regWeeks, modWeeks);
    /*
     * Update cost when mouse clicked on spinners
     */
    regWeeks.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      regDoUpdateCost();
    });
    modWeeks.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      modDoUpdateCost();
    });
  }

  /**
   * Helper method to setup the table on Delete tab
   */
  private void initDelTable() {
    /*
     * Create table mappings
     */
    delTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    delTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    delTableContact.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));
    delTableWeeks.setCellValueFactory(new PropertyValueFactory<>("nrWeeks"));
    delTableGuide.setCellValueFactory(new PropertyValueFactory<>("guideRequired"));
    delTableHotel.setCellValueFactory(new PropertyValueFactory<>("hotelRequired"));
    delTableBanned.setCellValueFactory(new PropertyValueFactory<>("banStatusFullName"));
    /*
     * Update table on refresh event
     */
    delTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      delTable.setItems(ViewUtils.getMembers());
    });
    ClimbSafeView.getInstance().registerRefreshEvent(delTable);
    /*
     * Enable multi rows selection
     */
    delTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }


  /**
   * Helper method to setup the equipment table on Register tab
   */
  private void initRegEquipTable() {
    /*
     * Create table mappings
     */
    regEquipTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    regEquipTableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
    regEquipTablePrice.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
    regEquipTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
    /*
     * Update table on refresh event
     */
    regEquipTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      this.curRegEquipments = ViewUtils.getEquipments();
      regEquipTable.setItems(this.curRegEquipments);
      /*
       * Add handler for all spinners
       */
      for (var equipment : this.curRegEquipments) {
        equipment.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
          regDoUpdateCost();
        });
      }
    });
    ClimbSafeView.getInstance().registerRefreshEvent(regEquipTable);
  }

  /**
   * Helper method to setup the bundle table on Register tab
   */
  private void initRegBundleTable() {
    /*
     * Create tree table mappings
     */
    regBundleTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    regBundleTablePrice.setCellValueFactory(new PropertyValueFactory<>("noDiscountPrice"));
    regBundleTableDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
    regBundleTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
    /*
     * Update table on refresh event
     */
    regBundleTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      this.curRegBundles = ViewUtils.getEquipmentBundles();
      regBundleTable.setItems(this.curRegBundles);
      /*
       * Add handler for all spinners
       */
      for (var bundle : this.curRegBundles) {
        bundle.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
          regDoUpdateCost();
        });
      }
    });
    ClimbSafeView.getInstance().registerRefreshEvent(regBundleTable);
  }

  /**
   * Helper method to setup the equipment table on Modify tab
   */
  private void initModEquipTable() {
    /*
     * Create table mappings
     */
    modEquipTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    modEquipTableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
    modEquipTablePrice.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
    modEquipTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
    /*
     * Update table on refresh event
     */
    modEquipTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      this.curModEquipments = ViewUtils.getEquipments();
      modEquipTable.setItems(this.curModEquipments);
      /*
       * Add handler for all spinners
       */
      for (var equipment : this.curModEquipments) {
        equipment.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
          modDoUpdateCost();
        });
      }
    });
    ClimbSafeView.getInstance().registerRefreshEvent(modEquipTable);
  }

  /**
   * Helper method to setup the bundle table on Modify tab
   */
  private void initModBundleTable() {
    /*
     * Create tree table mappings
     */
    modBundleTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    modBundleTablePrice.setCellValueFactory(new PropertyValueFactory<>("noDiscountPrice"));
    modBundleTableDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
    modBundleTableQuantity.setCellValueFactory(new PropertyValueFactory<>("mpQuantity"));
    /*
     * Update table on refresh event
     */
    modBundleTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      this.curModBundles = ViewUtils.getEquipmentBundles();
      modBundleTable.setItems(this.curModBundles);
      /*
       * Add handler for all spinners
       */
      for (var bundle : this.curModBundles) {
        bundle.getMpQuantity().addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
          modDoUpdateCost();
        });
      }
    });
    ClimbSafeView.getInstance().registerRefreshEvent(modBundleTable);
  }

  /**
   * Helper method to update cost on Register tab
   */
  private void regDoUpdateCost() {
    int totalCost = 0;
    for (var equipment : this.curRegEquipments) {
      totalCost += equipment.getPricePerWeek() * (int) equipment.getMpQuantity().getValue();
    }
    for (var bundle : this.curRegBundles) {
      int bundlePrice = bundle.getNoDiscountPrice();
      if (regNeedGuideYes.isSelected()) {
        totalCost += ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();
        bundlePrice = Math.round(bundlePrice * (100 - bundle.getDiscount()) / 100);
      }
      totalCost += bundlePrice * (int) bundle.getMpQuantity().getValue();
    }
    totalCost = this.regWeeks.getValue() * totalCost;
    regTotalCost.setText(Integer.toString(totalCost));
  }

  /**
   * Helper method to update cost on Modify tab
   */
  private void modDoUpdateCost() {
    int totalCost = 0;
    for (var equipment : this.curModEquipments) {
      totalCost += equipment.getPricePerWeek() * (int) equipment.getMpQuantity().getValue();
    }
    for (var bundle : this.curModBundles) {
      int bundlePrice = bundle.getNoDiscountPrice();
      if (modNeedGuideYes.isSelected()) {
        totalCost += ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();
        bundlePrice = Math.round(bundlePrice * (100 - bundle.getDiscount()) / 100);
      }
      totalCost += bundlePrice * (int) bundle.getMpQuantity().getValue();
    }
    totalCost = this.modWeeks.getValue() * totalCost;
    modTotalCost.setText(Integer.toString(totalCost));
  }

  /**
   * Helper method to clear Register tab
   */
  private void regDoClear() {
    regEmail.setText("");
    regPassword.setText("");
    regName.setText("");
    regContact.setText("");
    regWeeks.getValueFactory().setValue(1);
    regNeedGuide.selectToggle(regNeedGuideNo);
    regNeedHotel.selectToggle(regNeedHotelNo);
    for (var equipment : this.curRegEquipments) {
      equipment.getMpQuantity().getValueFactory().setValue(0);
    }
    for (var bundle : this.curRegBundles) {
      bundle.getMpQuantity().getValueFactory().setValue(0);
    }
    regTotalCost.setText("0");
  }

  /**
   * Helper method to clear Modify tab
   */
  private void modDoClear() {
    modEmail.setText("");
    modPassword.setText("");
    modName.setText("");
    modContact.setText("");
    modWeeks.getValueFactory().setValue(1);
    modNeedGuide.selectToggle(modNeedGuideNo);
    modNeedHotel.selectToggle(modNeedHotelNo);
    for (var equipment : this.curModEquipments) {
      equipment.getMpQuantity().getValueFactory().setValue(0);
    }
    for (var bundle : this.curModBundles) {
      bundle.getMpQuantity().getValueFactory().setValue(0);
    }
    modTotalCost.setText("0");
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
    var emailList = new ArrayList<String>();
    /*
     * Email need to be stored in another list since selection changes when a member is deleted
     */
    if (selection != null) {
      for (var member : selection) {
        emailList.add(member.getEmail());
      }
    }
    for (var email : emailList) {
      ViewUtils.successful(() -> ClimbSafeFeatureSet1Controller.deleteMember(email));
    }
  }

  /**
   * Helper method to auto fill the Modify tab when given a valid member email
   */
  private void modDoAutofill() {
    var email = modEmail.getText();
    TOMember selectedMember = null;
    // search for the member, return if not found
    for (var member : ClimbSafeFeatureSet2Controller.getMembers()) {
      if (member.getEmail().equals(email)) {
        selectedMember = member;
      }
    }
    if (selectedMember == null) {
      return;
    }
    modPassword.setText(selectedMember.getPassword());
    modName.setText(selectedMember.getName());
    modContact.setText(selectedMember.getEmergencyContact());
    if (selectedMember.getNrWeeks() <= ClimbSafeFeatureSet1Controller.getNrWeeks()) {
      modWeeks.getValueFactory().setValue(selectedMember.getNrWeeks());
    } else {
      modWeeks.getValueFactory().setValue(ClimbSafeFeatureSet1Controller.getNrWeeks());
    }
    if (selectedMember.getGuideRequired()) {
      modNeedGuide.selectToggle(modNeedGuideYes);
    } else {
      modNeedGuide.selectToggle(modNeedGuideNo);
    }
    if (selectedMember.getHotelRequired()) {
      modNeedHotel.selectToggle(modNeedHotelYes);
    } else {
      modNeedHotel.selectToggle(modNeedHotelNo);
    }
    /*
     * Autofill bookedItems
     */
    try {
      var bookedItems = ClimbSafeFeatureSet2Controller.getBookedItems(email);
      for (var item : bookedItems) {
        for (var equipment : this.curModEquipments) {
          if (item.getBookableItemName().equals(equipment.getName())) {
            equipment.getMpQuantity().getValueFactory().setValue(item.getQuantity());
          }
        }
        for (var bundle : this.curModBundles) {
          if (item.getBookableItemName().equals(bundle.getName())) {
            bundle.getMpQuantity().getValueFactory().setValue(item.getQuantity());
          }
        }
      }
    } catch (InvalidInputException e) {
      System.out.println(e.getMessage());
    }
    /*
     * Update totalCost
     */
    modDoUpdateCost();
  }

}
