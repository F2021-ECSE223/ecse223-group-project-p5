package ca.mcgill.ecse.climbsafe.javafx.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * Controller Class for the <code>SetupNMCPage.fxml</code>
 * 
 * @author Harrison Wang
 */
public class SetupNMCPageController {

  @FXML
  private AnchorPane rootPane;
  @FXML
  private Spinner<Integer> priceSpinner;
  @FXML
  private Button setupButton;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private Spinner<Integer> weekSpinner;
  @FXML
  private Label curPriceLabel;
  @FXML
  private Label curNrWeeksLabel;
  @FXML
  private Label curStartDate;

  /**
   * Event listener on {@link #setupButton} for {@link ActionEvent}
   * 
   * @param event the <code>ActionEvent</code> instance received
   * @author Harrison Wang
   */
  @FXML
  void setupNMC(ActionEvent event) {
    ViewUtils.callController(() -> {
      final LocalDate startDate = startDatePicker.getValue();
      final int nrWeeks = weekSpinner.getValue();
      final int priceOfGuidePerWeek = priceSpinner.getValue();

      ClimbSafeFeatureSet1Controller.setup(Date.valueOf(startDate), nrWeeks, priceOfGuidePerWeek);
    });
  }

  /**
   * Helper method to initialize {@link #priceSpinner}. Assigns a {@link SpinnerValueFactory} to
   * manage its behavior
   * 
   * @author Harrison Wang
   */
  private void initPriceSpinner() {
    // Set initial value to the current value in the model. If this value is not within the bounds
    // [0, infinity], set initial value to 0.
    int initialGuidePrice = ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();
    if (initialGuidePrice < 0)
      initialGuidePrice = 0;

    var priceSpinnerFactory =
        /*
         * The ClimbSafe application sets the constraint that the value for weekly guide price must
         * be >= 0. There is no specified upper limit for this value.
         * 
         * The default value shown in the Spinner object will be the value currently set in the
         * model. If this value is not within the bounds [0, infinity], the value will be set to 0.
         * This does not affect the value of guide price in the model.
         */
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, initialGuidePrice,
            1);

    // Add a StringConverter to handle user-input
    priceSpinnerFactory.setConverter(new StringConverter<Integer>() {
      @Override
      public String toString(Integer object) {
        return object != null ? object.toString() : "null";
      }

      @Override
      public Integer fromString(String string) {
        /*
         * When the value in the text-field is changed, SpinnerFactory will call this method to try
         * and convert the user-input to a value.
         * 
         * We get the current value in priceSpinner's text-field. We assume that this value is not
         * invalid. We can make this assumption because the SpinnerFactory will set a valid initial
         * value, and our implementation of this method prevents invalid values from being set.
         * 
         * We try to convert the string value to an Integer. If an error NumberFormatException is
         * caught, we assert that the string is invalid in some way. If the value is properly
         * converted, we check if the value is within the bounds [0, infinity] and assert that the
         * string is invalid. In this case, we revert the priceSpinner to its state before the
         * user's input. To do this we reset the string in the text-field to the current value (the
         * value before the user's input), and return the current value. We then display a pop-up to
         * inform the user of their error.
         */
        final int curValue = priceSpinner.getValue();
        try {
          int newValue = Integer.valueOf(string);
          if (newValue < 0)
            throw new NumberFormatException();
          return newValue;
        } catch (NumberFormatException n) {
          priceSpinner.getEditor().setText(Integer.toString(curValue));
          ViewUtils
              .showError("Guide Price per Week must be set to an Integer Value greater than 0");
          return curValue;
        }
      }
    });
    priceSpinner.setValueFactory(priceSpinnerFactory);
  }

  /**
   * Helper method to initialize {@link #weekSpinner}. Assigns a {@link SpinnerValueFactory} to
   * manage its behavior
   * 
   * @author Harrison Wang
   */
  private void initWeekSpinner() {
    // Set initial value to the current value in the model. If this value is not within the bounds
    // [0, 52], set initial value to 0.
    int initialWeeksValue = ClimbSafeFeatureSet1Controller.getNrWeeks();
    if (initialWeeksValue > 52 || initialWeeksValue < 0)
      initialWeeksValue = 0;

    var weekSpinnerFactory =
        /*
         * The ClimbSafe application sets the constraint that the value for number of weeks must be
         * >= 0. There is no specified upper limit for this value, however we assert that it must be
         * <= 52.
         * 
         * The default value shown in the Spinner object will 0. This does not affect the value of
         * number of weeks in the model.
         */
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 52, initialWeeksValue, 1);

    // Add a StringConverter to handle user-input
    weekSpinnerFactory.setConverter(new StringConverter<Integer>() {
      @Override
      public String toString(Integer object) {
        return object != null ? object.toString() : "null";
      }

      @Override
      public Integer fromString(String string) {
        /*
         * When the value in the text-field is changed, SpinnerFactory will call this method to try
         * and convert the user-input to a value.
         * 
         * We get the current value in weekSpinner's text-field. We assume that this value is not
         * invalid. We can make this assumption because the SpinnerFactory will set a valid initial
         * value, and our implementation of this method prevents invalid values from being set.
         * 
         * We try to convert the string value to an Integer. If an error NumberFormatException is
         * caught, we assert that the string is invalid in some way. If the value is properly
         * converted, we check if the value is within the bounds [0, 52] and assert that the string
         * is invalid. In this case, we revert the weekSpinner to its state before the user's input.
         * To do this we reset the string in the text-field to the current value (the value before
         * the user's input), and return the current value. We then display a pop-up to inform the
         * user of their error.
         */
        final int curValue = weekSpinner.getValue();
        try {
          int newValue = Integer.valueOf(string);
          if (newValue > 52 || newValue < 0)
            throw new NumberFormatException();
          return newValue;
        } catch (NumberFormatException n) {
          weekSpinner.getEditor().setText(Integer.toString(curValue));
          ViewUtils.showError("Number of Weeks must be set to an Integer Value between 0 and 52");
          return curValue;
        }
      }
    });
    weekSpinner.setValueFactory(weekSpinnerFactory);
  }

  /**
   * Helper method to initialize {@link #startDatePicker}. Assigns a {@link StringConverter} to
   * handle user-input, and sets an initial value.
   * 
   * @author Harrison Wang
   */
  private void initStartDate() {
    // Set the initial value to today's date. If ClimbSafe has a valid date already set, that value
    // will get used. Otherwise, set the initial value to today.
    Date startDate = ClimbSafeFeatureSet1Controller.getStartDate();
    startDatePicker
        .setValue(startDate != null ? LocalDate.parse(startDate.toString()) : LocalDate.now());

    /*
     * Add a StringConverter to handle user-input
     * 
     * By default, the DatePicker control object holds an instance of LocalDate, which can be
     * directly passed to the ClimbSafe controller to set up NMC information. This StringConverter
     * is used to convert user-input of type String into an instance of LocalDate.
     */
    var dateFormatConverter = new StringConverter<LocalDate>() {
      // define a DateTimeFormatter instance, which checks for the yyyy-mm-dd convention
      final String pattern = "yyyy-MM-dd";
      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

      @Override
      public String toString(LocalDate object) {
        return object != null ? formatter.format(object) : "";
      }

      @Override
      public LocalDate fromString(String string) {
        /*
         * When the value in the text-field is changed, DatePicker will call this method to try and
         * convert the user-input to a value.
         * 
         * We get the current value in startDatePicker's text-field. We assume that this value is
         * not invalid. We can make this assumption because we set the initial value to be valid (we
         * set it to Today), and our implementation of this method prevents invalid values from
         * being set.
         * 
         * We try to convert the string value to a LocalDate instance, using our formatter. If an
         * error DateTimeParseException is caught, we assert that the string is invalid in some way.
         * In this case, we revert the startDatePicker to its state before the user's input. To do
         * this we reset the string in the text-field to the current value (the value before the
         * user's input), and return the current value. We then display a pop-up to inform the user
         * of their error.
         */
        final String curDate = startDatePicker.getEditor().getText();
        try {
          return LocalDate.parse(string, formatter);
        } catch (DateTimeParseException d) {
          startDatePicker.getEditor().setText(curDate);
          ViewUtils.showError("Start Date must be of the format yyyy-mm-dd");
          return LocalDate.parse(curDate, formatter);
        }
      }
    };
    startDatePicker.setConverter(dateFormatConverter);
  }

  /**
   * Helper method to initialize the labels used to display the current NMC values for the user.
   * 
   * @author Harrison Wang
   */
  private void initCurrentLabels() {
    // Set the initial values for these labels
    Date startDate = ClimbSafeFeatureSet1Controller.getStartDate();
    int nrWeeks = ClimbSafeFeatureSet1Controller.getNrWeeks();
    int guidePrice = ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();

    curStartDate.setText(startDate != null ? startDate.toString() : "Not Set");
    curNrWeeksLabel.setText(Integer.toString(nrWeeks));
    curPriceLabel.setText(Integer.toString(guidePrice));

    // Add a listener for REFRESH_EVENT so that when the NMC information is changed, the labels are
    // also changed.
    curStartDate.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      final Date newStartDate = ClimbSafeFeatureSet1Controller.getStartDate();
      curStartDate.setText(newStartDate != null ? newStartDate.toString() : "Not Set");
    });
    curNrWeeksLabel.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      final int newNrWeeks = ClimbSafeFeatureSet1Controller.getNrWeeks();
      curNrWeeksLabel.setText(Integer.toString(newNrWeeks));
    });
    curPriceLabel.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      final int newGuidePrice = ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();
      curPriceLabel.setText(Integer.toString(newGuidePrice));
    });

    ClimbSafeView.getInstance().registerRefreshEvent(curStartDate, curNrWeeksLabel, curPriceLabel);
  }

  /**
   * Initialize method, called when this page is first created.
   * 
   * @author Harrison Wang
   */
  @FXML
  public void initialize() {
    // deselect any focused elements when the user clicks away
    rootPane.setOnMouseClicked(e -> {
      rootPane.requestFocus();
    });
    initPriceSpinner();
    initWeekSpinner();
    initStartDate();
    initCurrentLabels();
  }

}
