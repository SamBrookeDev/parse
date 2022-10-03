package Task_2;

import Task_1.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class PriceListParcer {


    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();
        JsonObject priceListData = Utils.getRequest("https://onlinesim.ru/price-list-data?type=receive");
        JsonObject inputCountries = priceListData.getAsJsonObject("countries");
        JsonObject inputText = priceListData.getAsJsonObject("text");
        JsonObject inputList = priceListData.getAsJsonObject("list");
        Map<String, Map<String, String>> resultMap = new HashMap<>();

        for (String key : inputCountries.keySet()) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> myMap = gson.fromJson(inputList.get(key), type);
            String countryName = inputText.getAsJsonObject().get("country_" + key).getAsString();
            resultMap.put(countryName, myMap);
        }
        resultMap.entrySet().forEach(System.out::println);
        Utils.createJsonFile(resultMap, "resultMap.json");
    }

}



