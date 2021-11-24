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
import javafx.util.StringConverter;

/**
 * Controller Class for the <code>SetupNMCPage.fxml</code>
 * 
 * @author Harrison Wang
 */
public class SetupNMCPageController {

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
    var priceSpinnerFactory =
        /*
         * The ClimbSafe application sets the constraint that the value for weekly guide price must
         * be >= 0. There is no specified upper limit for this value.
         * 
         * The default value shown in the Spinner object will 0. This does not affect 
         */
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1);
    priceSpinnerFactory.setConverter(new StringConverter<Integer>() {
      @Override
      public String toString(Integer object) {
        return object != null ? object.toString() : "null";
      }

      @Override
      public Integer fromString(String string) {
        final int curValue = priceSpinner.getValue();
        try {
          return Integer.valueOf(string);
        } catch (NumberFormatException n) {
          priceSpinner.getEditor().setText(Integer.toString(curValue));
          ViewUtils.showError("Guide Price per Week must be set to an Integer Value");
          return curValue;
        }
      }
    });
    priceSpinner.setValueFactory(priceSpinnerFactory);
  }

  private void initWeekSpinner() {
    var weekSpinnerFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1);
    weekSpinnerFactory.setConverter(new StringConverter<Integer>() {
      @Override
      public String toString(Integer object) {
        return object != null ? object.toString() : "null";
      }

      @Override
      public Integer fromString(String string) {
        final int curValue = weekSpinner.getValue();
        try {
          return Integer.valueOf(string);
        } catch (NumberFormatException n) {
          weekSpinner.getEditor().setText(Integer.toString(curValue));
          ViewUtils.showError("Number of Weeks must be set to an Integer Value");
          return curValue;
        }
      }
    });
    weekSpinner.setValueFactory(weekSpinnerFactory);
  }

  private void initStartDate() {
    startDatePicker.setValue(LocalDate.now());
    var dateFormatConverter = new StringConverter<LocalDate>() {
      final String pattern = "yyyy-MM-dd";
      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

      @Override
      public String toString(LocalDate object) {
        return object != null ? formatter.format(object) : "";
      }

      @Override
      public LocalDate fromString(String string) {
        final String curDate = startDatePicker.getEditor().getText();
        if (string == null || string.isEmpty())
          return null;
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

  private void initCurrentLabels() {
    String startDate = ClimbSafeFeatureSet1Controller.getStartDate();
    int nrWeeks = ClimbSafeFeatureSet1Controller.getNrWeeks();
    int guidePrice = ClimbSafeFeatureSet1Controller.getPriceOfGuidePerWeek();

    curStartDate.setText(startDate != null ? startDate : "Not Set");
    curNrWeeksLabel.setText(Integer.toString(nrWeeks));
    curPriceLabel.setText(Integer.toString(guidePrice));

    curStartDate.addEventHandler(ClimbSafeView.REFRESH_EVENT, e -> {
      final String newStartDate = ClimbSafeFeatureSet1Controller.getStartDate();
      curStartDate.setText(newStartDate != null ? newStartDate : "Not Set");
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

  @FXML
  public void initialize() {
    initPriceSpinner();
    initWeekSpinner();
    initStartDate();
    initCurrentLabels();
  }

}
