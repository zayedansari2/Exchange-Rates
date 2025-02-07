import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Scanner;


public class ExchangeRate {

	static double rate = 1.0;
	static JSONObject myObject;

	// Method to read API key from a file
    private static String getApiKey() throws Exception {
        return new String(Files.readAllBytes(Paths.get("config/apiKey.txt"))).trim();
    }
	
	public static double getRate(String currencyCode) throws Exception {

		
		try {
			String firstPartURL = "https://v6.exchangerate-api.com/v6/";
			String apiKey = getApiKey();
			String thirdPartURL = "/latest/USD";
			String theURL = firstPartURL + apiKey + thirdPartURL;

			URL url = new URL(theURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			JSONParser jsonParser = new JSONParser();
			myObject = (JSONObject) jsonParser.parse(br);

			if (currencyCode.equals("USD")) {
				return 1.0;
			}

			rate = ((Number) ((JSONObject) myObject.get("conversion_rates")).get(currencyCode)).doubleValue();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return rate;
	}

	public static void convert() throws Exception {

		Scanner input = new Scanner(System.in);

		while (true) {
			System.out.println("--Welcome to the Real Time Exchange Rate App--");
			System.out.println();
			System.out.println("Select 1 to view exchange rates");
			System.out.println("Select 2 to convert currency");
			System.out.println("Select 3 to exit");
			String choice = input.next();
			System.out.println();

			if (choice.equals("1")) {
				System.out.println("Enter currency code:");
				String currencyCode = input.next().toUpperCase();
				getRate(currencyCode);
				System.out.println("Exchange rate for " + currencyCode + " is " + rate);
			} else if (choice.equals("2")) {
				System.out.println("Proceeding to currency conversion...");

				System.out.println("Please Enter Current Currency: ");
				String currentCode = input.next().toUpperCase();
				double currentRate = getRate(currentCode);

				System.out.println("Please Enter Desired Currency: ");
				String desiredCode = input.next().toUpperCase();
				double desiredRate = getRate(desiredCode);

				System.out.println("Please Enter Amount in " + currentCode + ": ");
				double amount = input.nextDouble();

				double convertedAmount = (amount / currentRate) * desiredRate;

				System.out.printf("Converted Amount: %.2f %s%n", convertedAmount, desiredCode);

			} else if (choice.equals("3")) {

				System.out.println("Exiting the application. Goodbye!");
				break;

			} else {

				System.out.println("Invalid choice. Please try again.");

			}
			
			System.out.println();
		}

		input.close();
	}

	public static void main(String[] args) throws Exception {
    	convert();
	}

}
