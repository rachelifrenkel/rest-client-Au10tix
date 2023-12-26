package com.au10tix.services;

import lombok.SneakyThrows;

import java.net.http.HttpResponse;

public interface Au10tixClientService {
    @SneakyThrows
    String getJsonBySerial(int serial);

    @SneakyThrows
    HttpResponse<String> sendJson(String jsonObject);

}
