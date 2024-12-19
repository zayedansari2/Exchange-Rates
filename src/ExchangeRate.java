
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Scanner;


public class ExchangeRate {

	static double rate = 1.0;
	static JSONObject myObject;
	
	public static double getRate(String currancyCode) throws Exception {
		
		try {
			
			String firstPartURL = "https://v6.exchangerate-api.com/v6/";

			// Enter your API key here
			String key =""; 


			String thirdPartURL = "/latest/USD";
			String theURL = firstPartURL + key + thirdPartURL;
			
			URL url = new URL(theURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			JSONParser jsonParser = new JSONParser();
			myObject = (JSONObject)jsonParser.parse(br);
			
			if (currancyCode.equals("USD")) {
                return rate;
            }
			
			rate = (double)((JSONObject) myObject.get("conversion_rates")).get(currancyCode);
			
		    }
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return rate;
			
   	 }

    public static void main(String[] args) throws Exception {

		Scanner input = new Scanner(System.in);

		System.out.println("--Welcome to the Real Time Exchange Rate App--");
		System.out.println("Please Enter Currancy Code Below: ");
		String code = input.next();
		System.out.print("Exchange Rate: " + getRate(code));
        
    }
}
