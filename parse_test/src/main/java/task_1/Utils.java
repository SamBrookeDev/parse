package Task_1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Utils {

    //Добавим метод-обработчик URL для получения json

    public static JsonObject getRequest(String inputUrl) throws IOException {
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        URL url = new URL(inputUrl);
        String responce;

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        while ((responce = bufferedReader.readLine()) != null) {
            jsonObject = gson.fromJson(responce, JsonObject.class);
        }

        bufferedReader.close();
        return jsonObject;
    }

    public static JsonObject getRequestAnalog(String inputUrl) {

        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();

        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(inputUrl))
                .header("accept", "application/json")
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(response.body(), JsonObject.class);

    }

    public static void createJsonFile(Map<?, ?> input, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(input);
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(json);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
