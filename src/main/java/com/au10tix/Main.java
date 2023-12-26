package com.au10tix;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.au10tix.services.Au10tixClientServiceImpl;
import com.au10tix.services.JsonOperationsImp;

import java.net.http.HttpClient;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {

        JsonOperationsImp jsonOperationsImp = new JsonOperationsImp(new Au10tixClientServiceImpl(HttpClient.newHttpClient()));
        jsonOperationsImp.handle();
    }
}