package ca.mcgill.ecse.climbsafe.javafx.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet3Controller;

import ca.mcgill.ecse.climbsafe.controller.TOGuide;

import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class GuidesPageController {
  @FXML
  private TabPane guidesPageTabPane;
  @FXML
  private Tab regGuideTab;
  @FXML
  private Tab modGuideTab;
  @FXML
  private Tab delGuideTab;
  @FXML
  private TextField regGuideEmail;
  @FXML
  private PasswordField regGuidePassword;
  @FXML
  private TextField regPasswordShow;
  @FXML
  private TextField regGuideName;
  @FXML
  private TextField regGuideContact;
  @FXML
  private Button regGuideClear;
  @FXML
  private Button regGuideRegister;

  @FXML
  private TextField modGuideEmail;
  @FXML
  private PasswordField modGuidePassword;
  @FXML
  private TextField modPasswordShow;
  @FXML
  private TextField modGuideName;
  @FXML
  private TextField modGuideContact;
  @FXML
  private Button modGuideClear;
  @FXML
  private Button modGuideAutofill;
  @FXML
  private Button modGuideModify;

  @FXML
  private TableView<TOGuide> delGuideTable;
  @FXML
  private TableColumn<TOGuide, String> delGuideTableName;
  @FXML
  private TableColumn<TOGuide, String> delGuideTableEmail;
  @FXML
  private TableColumn<TOGuide, String> delGuideTableContact;
  @FXML
  private Button delGuideClearSelection;
  @FXML
  private Button delGuideDelete;
  @FXML
  private Button delGuideModifySelected;


  @FXML
  public void initialize() {

    initGuideDelTable();

  }

  // Event Listener on Button[#regGuideClear].onAction
  @FXML
  public void regGuideDoClear(ActionEvent event) {
    // TODO Autogenerated
    regGuideDoClear();
  }

  // Event Listener on Button[#regGuideRegister].onAction
  @FXML
  public void regGuideDoRegister(ActionEvent event) {
    // TODO Autogenerated
    String email = regGuideEmail.getText();
    String password = regGuidePassword.getText();
    String name = regGuideName.getText();
    String contact = regGuideContact.getText();

    // create the guide
    if (ViewUtils.successful(
        () -> ClimbSafeFeatureSet3Controller.registerGuide(email, password, name, contact))) {
      regGuideDoClear();
    }
  }

  // Event Listener on Button[#modGuideClear].onAction
  @FXML
  public void modGuideDoClear(ActionEvent event) {
    // TODO Autogenerated
    modGuideDoClear();
  }

  // Event Listener on Button[#modGuideAutofill].onAction
  @FXML
  public void modGuideDoAutofill(ActionEvent event) {
    // TODO Autogenerated
    modGuideDoAutofill();
  }

  // Event Listener on Button[#modGuideModify].onAction
  @FXML
  public void modGuideDoModify(ActionEvent event) {
    // TODO Autogenerated
    String email = modGuideEmail.getText();
    String password = modGuidePassword.getText();
    String name = modGuideName.getText();
    String contact = modGuideContact.getText();


    // modify the member
    if (ViewUtils.successful(
        () -> ClimbSafeFeatureSet3Controller.updateGuide(email, password, name, contact))) {
      modGuideDoClear();
    }
  }

  // Event Listener on Button[#delGuideClearSelection].onAction
  @FXML
  public void delGuideDoClearSelection(ActionEvent event) {
    // TODO Autogenerated
    delGuideDoClearSelection();
  }

  // Event Listener on Button[#delGuideDelete].onAction
  @FXML
  public void delGuideDoDelete(ActionEvent event) {
    // TODO Autogenerated
    delGuideDoDelete();
  }

  // Event Listener on Button[#delGuideModifySelected].onAction
  @FXML
  public void delGuideDoModifySelected(ActionEvent event) {
    // TODO Autogenerated
    delGuideDoModifySelected();
  }

  /**
   * Helper method to setup the table on Delete tab
   */
  private void initGuideDelTable() {
    /*
     * Create table mappings
     */
    delGuideTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
    delGuideTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    delGuideTableContact.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));
    /*
     * Update table on refresh event
     */
    delGuideTable.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      delGuideTable.setItems(ViewUtils.getGuides());
    });
    ClimbSafeView.getInstance().registerRefreshEvent(delGuideTable);
    /*
     * Enable multi rows selection
     */
    delGuideTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    /*
     * Add double click handler
     */
    delGuideTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
          if (mouseEvent.getClickCount() == 2) {
            if (delGuideTable.getSelectionModel().getSelectedItem() != null) {
              delGuideDoModifySelected();
            }
          }
        }
      }
    });
  }

  /**
   * Helper method to auto fill the Modify tab when given a valid member email
   */
  private void modGuideDoAutofill() {
    var email = modGuideEmail.getText();
    TOGuide selectedGuide = null;
    // search for the guide, return if not found
    for (var guide : ClimbSafeFeatureSet3Controller.getGuides()) {
      if (guide.getEmail().equals(email)) {
        selectedGuide = guide;
      }
    }
    if (selectedGuide == null) {
      return;
    }
    modGuidePassword.setText(selectedGuide.getPassword());
    modGuideName.setText(selectedGuide.getName());
    modGuideContact.setText(selectedGuide.getEmergencyContact());
  }

  /**
   * Helper method to delete guides on selected rows
   */
  private void delGuideDoDelete() {
    var selection = delGuideTable.getSelectionModel().getSelectedItems();
    var emailList = new ArrayList<String>();
    /*
     * Email need to be stored in another list since selection changes when a guide is deleted
     */
    if (selection != null) {
      for (var guide : selection) {
        emailList.add(guide.getEmail());
      }
    }
    for (var email : emailList) {
      ViewUtils.successful(() -> ClimbSafeFeatureSet1Controller.deleteGuide(email));
    }
  }

  /**
   * Helper method to clear Register tab
   */
  private void regGuideDoClear() {
    regGuideEmail.setText("");
    regGuidePassword.setText("");
    regGuideName.setText("");
    regGuideContact.setText("");
  }

  /**
   * Helper method to clear Modify tab
   */
  private void modGuideDoClear() {
    modGuideEmail.setText("");
    modGuidePassword.setText("");
    modGuideName.setText("");
    modGuideContact.setText("");
  }

  /**
   * Helper method to clear Table selection
   */
  private void delGuideDoClearSelection() {
    delGuideTable.getSelectionModel().clearSelection();
  }

  /**
   * Helper method to edit the selected member
   */
  private void delGuideDoModifySelected() {
    var selection = delGuideTable.getSelectionModel().getSelectedItems();
    if (selection.size() != 1) {
      ViewUtils.showError("Only one guide must be selected");
      return;
    }
    TOGuide guide = selection.get(0);
    // Fill the modify tab
    modGuideEmail.setText(guide.getEmail());
    modGuideDoAutofill();
    // Swap to the modify tab
    guidesPageTabPane.getSelectionModel().select(modGuideTab);
  }

}
