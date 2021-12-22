import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.*;
import java.io.IOException;

public class RequestSender {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_KEY = "fc3565d883ab9ead40a1cdb278ff7d76";

    public static String sendTempRequest() throws IOException {
        String city, date, weather, temp, description;
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
                .addQueryParameter("q", "Moscow")
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "ru")
                .addQueryParameter("appid", API_KEY)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseJson = response.body().string();

        JsonNode cityNode = objectMapper
                .readTree(responseJson)
                .at("/name");
        city = cityNode.asText();

        JsonNode cityDate = objectMapper
                .readTree(responseJson)
                .at("/dt");
        date = cityDate.asText();

        JsonNode cityWeather = objectMapper
                .readTree(responseJson)
                .at("/weather/0/main");
        weather = cityWeather.asText();

        JsonNode cityTemp = objectMapper
                .readTree(responseJson)
                .at("/main/temp");
        temp = cityTemp.asText();

        JsonNode cityDesc = objectMapper
                .readTree(responseJson)
                .at("/weather/0/description");


        System.out.println("В городе " + city + " на дату " + date + " ожидается " + weather + ", температура: " + temp);

 return cityDesc.asText();
    }
}
