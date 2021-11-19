package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

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
  private TableView regEquipTable;
  @FXML
  private TableColumn regEquipTableName;
  @FXML
  private TableColumn regEquipTableWeight;
  @FXML
  private TableColumn regEquipTablePrice;
  @FXML
  private TableColumn regEquipTableQuantity;
  @FXML
  private TreeTableView regBundleTable;
  @FXML
  private TreeTableColumn regBundleTableName;
  @FXML
  private TreeTableColumn regBundleTableDiscount;
  @FXML
  private TreeTableColumn regBundleTablePrice;
  @FXML
  private TreeTableColumn regBundleTableQuantity;
  @FXML
  private Button regClear;
  @FXML
  private Button regUpdateCost;
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
  private TableView modEquipTable;
  @FXML
  private TableColumn modEquipTableName;
  @FXML
  private TableColumn modEquipTableWeight;
  @FXML
  private TableColumn modEquipTablePrice;
  @FXML
  private TableColumn modEquipTableQuantity;
  @FXML
  private TreeTableView modBundleTable;
  @FXML
  private TreeTableColumn modBundleTableName;
  @FXML
  private TreeTableColumn modBundleTableDiscount;
  @FXML
  private TreeTableColumn modBundleTablePrice;
  @FXML
  private TreeTableColumn modBundleTableQuantity;
  @FXML
  private Button modClear;
  @FXML
  private Button modAutofill;
  @FXML
  private Button modUpdateCost;
  @FXML
  private Button modModify;
  @FXML
  private TextField modTotalCost;
  @FXML
  private TableView delTable;
  @FXML
  private TableColumn delTableName;
  @FXML
  private TableColumn delTableEmail;
  @FXML
  private TableColumn delTableContact;
  @FXML
  private TableColumn delTableWeeks;
  @FXML
  private TableColumn delTableGuide;
  @FXML
  private TableColumn delTableHotel;
  @FXML
  private TableColumn delTableBanned;
  @FXML
  private Button delClearSelection;
  @FXML
  private Button delDelete;

  // Event Listener on Button[#regClear].onAction
  @FXML
  public void regDoClear(ActionEvent event) {
    regEmail.setText("");
    regPassword.setText("");
    regName.setText("");
    regContact.setText("");
    regNeedGuide.selectToggle(regNeedGuideNo);
    regNeedHotel.selectToggle(regNeedHotelNo);
  }

  // Event Listener on Button[#regUpdateCost].onAction
  @FXML
  public void regDoUpdateCost(ActionEvent event) {
    System.out.println("Reg Update Cost");
  }

  // Event Listener on Button[#regRegister].onAction
  @FXML
  public void regDoRegister(ActionEvent event) {
    System.out.println("Reg Register");
  }

  // Event Listener on Button[#modClear].onAction
  @FXML
  public void modDoClear(ActionEvent event) {
    modEmail.setText("");
    modPassword.setText("");
    modName.setText("");
    modContact.setText("");
    modNeedGuide.selectToggle(modNeedGuideNo);
    modNeedHotel.selectToggle(modNeedHotelNo);
  }

  // Event Listener on Button[#modAutofill].onAction
  @FXML
  public void modDoAutofill(ActionEvent event) {
    System.out.println("Mod Autofill");
  }

  // Event Listener on Button[#modUpdateCost].onAction
  @FXML
  public void modDoUpdateCost(ActionEvent event) {
    System.out.println("Mod Update Cost");
  }

  // Event Listener on Button[#modModify].onAction
  @FXML
  public void modDoModify(ActionEvent event) {
    System.out.println("Mod Modify");
  }

  // Event Listener on Button[#delClearSelection].onAction
  @FXML
  public void delDoClearSelection(ActionEvent event) {
    System.out.println("Del Clear Selection");
  }

  // Event Listener on Button[#delDelete].onAction
  @FXML
  public void delDoDelete(ActionEvent event) {
    System.out.println("Del Delete");
  }
}
