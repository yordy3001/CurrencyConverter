package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyLoader {

	public static List<Currency> loadCurrenciesFromFile(String filePath) {
		List<Currency> currencies = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("Reading line: " + line); // Print each line read from the file

				String[] parts = line.split(",");
				if (parts.length == 2) {
					String name = parts[0].trim();
					String code = parts[1].trim();
					currencies.add(new Currency(name, code));
				} else {
					System.err.println("Skipping line due to incorrect format: " + line); // Print if line format is
																							// incorrect
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Finished loading currencies. Total count: " + currencies.size()); // Print total count of
																								// loaded currencies
		return currencies;
	}
}