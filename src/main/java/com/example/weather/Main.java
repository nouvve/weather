package com.example.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Gorzów&appid=793742a1aa8cea81896e8e72211e8252");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // jak sie udalo polaczyc
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            StringBuffer res = new StringBuffer();

            while ((input = in.readLine()) != null) {
                res.append(input);
            }
            in.close();


            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(res.toString());

                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    iterateJson(jsonObject);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else { // nieudane polaczenie
            System.out.println("Nie udalo sie polaczyc.");
        }
    }
    private static void iterateJson(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            String keyStr = (String) key;
            Object value = jsonObject.get(keyStr);

            if (value instanceof JSONArray) {
                System.out.println(keyStr + " : ");
                JSONArray jsonArray = (JSONArray) value;
                for (Object arrayValue : jsonArray) {
                    iterateJson((JSONObject) arrayValue);
                }
            } else if (value instanceof JSONObject) {
                iterateJson((JSONObject) value);
            } else {
                System.out.println(keyStr + " : " + value);
            }
        }
    }
}
