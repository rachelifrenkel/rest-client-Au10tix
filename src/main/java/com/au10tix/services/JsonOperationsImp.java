package com.au10tix.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import static com.au10tix.constants.JsonData.JSON_PROCESS_ERROR_MESSAGE;
import static com.au10tix.utils.Utils.processJson;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@AllArgsConstructor
public class JsonOperationsImp implements JsonOperations {
    private final Au10tixClientService au10tixClientService;

    @SneakyThrows
    public void handle() {
        //receives 2 JSON from Au10tix’s rest server
        var firstJson = au10tixClientService.getJsonBySerial(1);
        var secondJson = au10tixClientService.getJsonBySerial(2);

        //process JSON
        var processData = processJson(firstJson, secondJson);

        //sends the JSON to be processed
        var response = au10tixClientService.sendJson(processData);

        //check if the call to the server was successful
        notNull(response, JSON_PROCESS_ERROR_MESSAGE);
        isTrue(HttpStatus.valueOf(response.statusCode()).is2xxSuccessful(), JSON_PROCESS_ERROR_MESSAGE);
        isTrue("correct".equals(response.body()), JSON_PROCESS_ERROR_MESSAGE);

        System.out.println("The json process is successful");
    }
}