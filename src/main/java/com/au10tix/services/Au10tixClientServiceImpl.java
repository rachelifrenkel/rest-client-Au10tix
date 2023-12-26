package com.au10tix.services;

import com.au10tix.properties.PropertiesService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class Au10tixClientServiceImpl implements Au10tixClientService {

    public final static PropertiesService propertiesService = new PropertiesService();
    public final static Properties propertiesData = propertiesService.getProperty("src/main/resources/app.properties");
    public final static String URL = propertiesData.getProperty("BASE_URL");

    private final HttpClient httpClient;

    @Override
    @SneakyThrows
    public String getJsonBySerial(int serial) {

        var targetURI = new URI(URL.concat("?serial=").concat(String.valueOf(serial)));

        var httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new Exception(format("Error fetching json with serial id %d: %s", serial, e.getMessage()));
        }
    }

    @Override
    @SneakyThrows
    public HttpResponse<String> sendJson(String data) {

        var targetURI = new URI(URL.concat("/api/process"));
        var httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


}
