package Task_1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CountryPhoneNumbers {
    public static void main(String[] args) throws IOException {

        Map<String, ArrayList<String>> numbersMap = new HashMap<>();
        JsonObject obj = Utils.getRequestAnalog("https://onlinesim.ru/api/getFreeCountryList");
        JsonArray codesArray = obj.getAsJsonArray("countries");

        for (int i = 0; i < codesArray.size(); i++) {
            if (!codesArray.get(i).getAsJsonObject().get("country").isJsonNull()) {
                int code = codesArray.get(i).getAsJsonObject().get("country").getAsInt();
                String country = codesArray.get(i).getAsJsonObject().get("country_text").getAsString();
                JsonObject object = Utils.getRequest("https://onlinesim.ru/api/getFreePhoneList?country=" + code);
                JsonArray numArray = object.getAsJsonArray("numbers");
                ArrayList<String> numberList = new ArrayList<String>();

                for (int j = 0; j < numArray.size(); j++) {
                    if (!numArray.get(j).getAsJsonObject().get("full_number").isJsonNull()) {
                        String number = numArray.get(j).getAsJsonObject().get("full_number").getAsString();
                        numberList.add(number);
                    }
                }
                numbersMap.put(country, numberList);
            }
        }

        numbersMap.entrySet().forEach(System.out::println);

        Utils.createJsonFile(numbersMap, "result.json");

    }

}




