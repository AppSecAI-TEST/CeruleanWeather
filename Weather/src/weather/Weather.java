package weather;

import java.io.*;
import java.net.*;
import java.util.*;
import java.math.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author 99tts
 */
public class Weather {

    public static void main(String[] args) throws Exception {
        System.out.print("City? ");

        Scanner r = new Scanner(System.in);

        String targetCity = r.nextLine();

        System.out.println("");

        String link = "http://api.openweathermap.org/data/2.5/weather?q="
                + targetCity + "&APPID=aef8e20c95b5cbc7cda011336296051e";

        URL url = new URL(link);

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String weather = "";
        String line = "";

        while ((line = br.readLine()) != null) {
            weather += line;
        }

        JSONParser parser = new JSONParser();

        Map w = (Map) parser.parse(weather);

        String desc = ((Map) ((List) w.get("weather")).get(0))
                .get("description").toString();

        Map main = (Map) w.get("main");

        if (w.get("name").toString().contains(targetCity)) {
            
            String temp = convertToCelsius(main.get("temp").toString());
            String min = convertToCelsius(main.get("temp_min").toString());
            String max = convertToCelsius(main.get("temp_max").toString());
            String city = w.get("name").toString() + ", " + ((Map) w.get("sys")).get("country").toString();
            Date date = new Date(1000 * Long.parseLong(w.get("dt").toString()));

            System.out.println("Current conditions in " + city);
            System.out.println(temp + ", " + desc);
            System.out.println("High: " + max + ", Low: " + min);
            System.out.println(date);
            System.out.println("");

        } else {
            System.out.println("Invalid city\n");
        }
        System.out.println("Enter to close");
        r.nextLine();

    }

    public static String convertToCelsius(String kelvin) {
        return (round((Double.parseDouble(kelvin) - 273.15), 1)) + " °C";
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
