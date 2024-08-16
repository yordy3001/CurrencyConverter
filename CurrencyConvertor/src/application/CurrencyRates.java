package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CurrencyRates {
    private Map<String, Map<String, Double>> exchangeRates = new HashMap<>();

    public CurrencyRates(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String fromCurrency = parts[0];
            String toCurrency = parts[1];
            double rate = Double.parseDouble(parts[2]);

            exchangeRates.computeIfAbsent(fromCurrency, k -> new HashMap<>()).put(toCurrency, rate);
        }
        reader.close();
    }

    public double getRate(String fromCurrency, String toCurrency) {
        return exchangeRates.getOrDefault(fromCurrency, new HashMap<>()).getOrDefault(toCurrency, 0.0);
    }
    
    public String getFormattedRates(String fromCurrency, String toCurrency) {
        double forwardRate = getRate(fromCurrency, toCurrency);
        double reverseRate = getRate(toCurrency, fromCurrency);
        
        if (forwardRate == 0.0) {
            return "Exchange rate not available.";
        }
        
        if (reverseRate == 0.0) {
            
            return String.format("1 %s = %.6f %s\n1 %s = Exchange rate not available", fromCurrency, forwardRate, toCurrency, toCurrency);
        }
        
        return String.format("1 %s = %.6f %s\n1 %s = %.6f %s", fromCurrency, forwardRate, toCurrency, toCurrency, reverseRate, fromCurrency);
    }
		
    	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}