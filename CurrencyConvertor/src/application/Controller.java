package application;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller implements Initializable {

	@FXML
	private Text answerLabel;

	@FXML
	private Button calculateBtn;

	@FXML
	private ChoiceBox<Currency> choiceFrom;

	@FXML
	private ChoiceBox<Currency> choiceTo;

	@FXML
	private Text conversionLabel;

	@FXML
	private Button clearBtn;

	@FXML
	private Label numericValue;

	@FXML
	private Label errorLabel;

	@FXML
	private Button exchangeBTN;

	@FXML
	private TextField valueLabel;

	@FXML
	void calculateBtnPressed(ActionEvent event) {
		double result = convert(choiceFrom, choiceTo);
		DecimalFormat df = new DecimalFormat("#,##0.00"); // Format with comma and 2 decimal places
		if (result >= 0) {
			numericValue.setText(df.format(result));

			errorLabel.setText(""); // Clear error label on successful conversion
		} else {
			numericValue.setText(""); // Clear numeric value if there's an error
		}
		numericValue.setVisible(true);
	}

	@FXML
	void clearBtnPressed(ActionEvent event) {
		valueLabel.clear();
		choiceFrom.getSelectionModel().clearSelection();
		choiceTo.getSelectionModel().clearSelection();
		numericValue.setText("");
		errorLabel.setText("");
		conversionLabel.setText("");
	}

	@FXML
	void exchangeBTNpressed(ActionEvent event) {
		Currency fromvalue = choiceFrom.getValue();
		Currency toValue = choiceTo.getValue();

		choiceFrom.setValue(toValue);
		choiceTo.setValue(fromvalue);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		List<Currency> currencies = CurrencyLoader.loadCurrenciesFromFile(
				"C:\\Users\\yordi\\eclipse-workspace\\CurrencyConvertor\\src\\application\\resources\\Currency_Names.txt");

		choiceFrom.getItems().addAll(currencies);
		choiceTo.getItems().addAll(currencies);
		
		choiceFrom.setValue(currencies.get(0));
		choiceTo.setValue(currencies.get(1));

		valueLabel.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*\\.?\\d*")) {
				errorLabel.setText("Invalid input. \nPlease enter a valid number.");
				valueLabel.setText(oldValue);
			} else {
				errorLabel.setText(""); // Clear error message when input is valid
			}
		});
	}

	private double amount() {
		try {
			return Double.parseDouble(valueLabel.getText());
		} catch (NumberFormatException e) {
			errorLabel.setText("Invalid input. \nPlease enter a valid number.");
			return -1;
		}
	}

	private double convert(ChoiceBox<Currency> choiceFrom, ChoiceBox<Currency> choiceTo) {
		try {
			CurrencyRates service = new CurrencyRates(
					"C:\\Users\\yordi\\eclipse-workspace\\CurrencyConvertor\\src\\application\\exchange_rates.txt");
			Currency fromCurrency = choiceFrom.getValue();
			Currency toCurrency = choiceTo.getValue();
			double amount = amount();

			if (amount >= 0) {
				if (fromCurrency != null && toCurrency != null) {
					String fromCurrencyCode = fromCurrency.getCode();
					String toCurrencyCode = toCurrency.getCode();
					conversionLabel.setText(service.getFormattedRates(fromCurrencyCode, toCurrencyCode));

					return amount * service.getRate(fromCurrencyCode, toCurrencyCode);
				} else {
					errorLabel.setText("Please select both currencies.");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

}