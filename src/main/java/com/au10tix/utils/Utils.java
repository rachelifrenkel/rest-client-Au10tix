package com.au10tix.utils;

import com.au10tix.dtos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.au10tix.constants.JsonData.DATE;
import static com.au10tix.constants.JsonData.VERSION;
import static java.util.Optional.ofNullable;

public final class Utils {
    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    @SneakyThrows
    public static <T> T fromJson(String jsonString, Class<T> toValueType) {
        try {
            return StringUtils.hasText(jsonString) ? MAPPER.readValue(jsonString, toValueType) : null;
        } catch (IOException e) {
            throw e;
        }
    }

    @SneakyThrows
    public static String toJson(Object data) {
        try {
            return data == null ? null : MAPPER.writeValueAsString(data);
        } catch (IOException e) {
            throw e;
        }
    }

    @SneakyThrows
    public static String processJson(String firstJson, String secondJson) {
        JsonResponse jsonResponse1 = fromJson(firstJson, JsonResponse.class);
        JsonResponse jsonResponse2 = fromJson(secondJson, JsonResponse.class);

        JsonResponse jsonToSend = JsonResponse.builder()
                .serial(3)
                .message(Message.builder()
                        .subset(Subset.builder()
                                .general(General.builder()
                                        .information(Information.builder()
                                                .date(DATE)
                                                .version(VERSION)
                                                .build()
                                        )
                                        .quantities(Quantities.builder()
                                                .first(Math.max(
                                                        getQuantityValue(jsonResponse1).getFirst(),
                                                        getQuantityValue(jsonResponse2).getFirst()
                                                ))
                                                .second(Math.max(
                                                        getQuantityValue(jsonResponse1).getSecond(),
                                                        getQuantityValue(jsonResponse2).getSecond()
                                                ))
                                                .third(Math.max(
                                                        getQuantityValue(jsonResponse1).getThird(),
                                                        getQuantityValue(jsonResponse2).getThird()
                                                ))
                                                .build()
                                        )
                                        .build()).build()).build()).build();

        return toJson(jsonToSend);
    }

    private static Quantities getQuantityValue(JsonResponse jsonResponse) {
        return ofNullable(jsonResponse)
                .map(JsonResponse::getMessage)
                .map(Message::getSubset)
                .map(Subset::getGeneral)
                .map(General::getQuantities)
                .orElseGet(() -> Quantities.builder().first(0).second(0).third(0).build());
    }
}
