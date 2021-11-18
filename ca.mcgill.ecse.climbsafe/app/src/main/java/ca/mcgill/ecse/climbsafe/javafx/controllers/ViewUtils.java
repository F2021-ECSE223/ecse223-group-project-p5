package ca.mcgill.ecse.climbsafe.javafx.controllers;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet3Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet5Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.controller.TOBookedItem;
import ca.mcgill.ecse.climbsafe.controller.TOBundleItem;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import ca.mcgill.ecse.climbsafe.controller.TOEquipmentBundle;
import ca.mcgill.ecse.climbsafe.controller.TOGuide;
import ca.mcgill.ecse.climbsafe.controller.TOMember;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {

  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(Executable executable) {
    try {
      executable.execute();
      ClimbSafeView.getInstance().refresh();
      return true;
    } catch (InvalidInputException e) {
      showError(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(Executable controllerCall) {
    return callController(controllerCall);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  /**
   * Get an observable list of assignments
   * 
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOAssignment> getAssignments() {
    return FXCollections.observableList(ClimbSafeFeatureSet6Controller.getAssignments());
  }

  /**
   * Get an observable list of members
   * 
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOMember> getMembers() {
    List<TOMember> members = ClimbSafeFeatureSet2Controller.getMembers();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(members);
  }

  /**
   * Get an observable list of guides
   * 
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOGuide> getGuides() {
    return FXCollections.observableList(ClimbSafeFeatureSet3Controller.getGuides());
  }

  /**
   * Get an observable list of all booked items
   * 
   * @return Observablelist
   * @author Jimmy Sheng
   */
  public static ObservableList<TOBookedItem> getBookedItems() {
    return FXCollections.observableList(ClimbSafeFeatureSet2Controller.getBookedItems());
  }

  /**
   * Get an observable list of the given member's booked items
   * 
   * @param memberEmail
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOBookedItem> getBookedItems(String memberEmail) {
    try {
      List<TOBookedItem> bookedItems = ClimbSafeFeatureSet2Controller.getBookedItems(memberEmail);
      return FXCollections.observableList(bookedItems);
    } catch (InvalidInputException e) {
      showError(e.getMessage());
      return FXCollections.observableList(new ArrayList<TOBookedItem>());
    }
  }

  /**
   * Get an observable list of equipment bundles
   * 
   * @return Observablelist
   * @author Jimmy Sheng
   */
  public static ObservableList<TOEquipmentBundle> getEquipmentBundles() {
    return FXCollections.observableList(ClimbSafeFeatureSet5Controller.getEquipmentBundles());
  }

  /**
   * Get an observable list of all bundle items
   * 
   * @return Observablelist
   * @author Jimmy Sheng
   */
  public static ObservableList<TOBundleItem> getBundleItems() {
    return FXCollections.observableList(ClimbSafeFeatureSet5Controller.getBundleItems());
  }

  /**
   * Get an observable list of bundle items in the given bundle
   * 
   * @param bundleName
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOBundleItem> getBundleItems(String bundleName) {
    try {
      List<TOBundleItem> bundleItems = ClimbSafeFeatureSet5Controller.getBundleItems(bundleName);
      return FXCollections.observableList(bundleItems);
    } catch (InvalidInputException e) {
      showError(e.getMessage());
      return FXCollections.observableList(new ArrayList<TOBundleItem>());
    }
  }

  /**
   * Get an observable list of equipments
   * 
   * @return ObservableList
   * @author Jimmy Sheng
   */
  public static ObservableList<TOEquipment> getEquipments() {
    return FXCollections.observableList(ClimbSafeFeatureSet4Controller.getEquipments());
  }

}


@FunctionalInterface
interface Executable {
  public void execute() throws Throwable;
}
