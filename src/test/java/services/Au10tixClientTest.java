package services;

import com.au10tix.services.Au10tixClientServiceImpl;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Au10tixClientTest {

    @InjectMocks
    private Au10tixClientServiceImpl au10tixClientService;

    @Mock
    private HttpClient httpClient;

    @Test
    @SneakyThrows
    public void testGetJsonBySerial() {

        HttpResponse<String> httpsResponse = mock(HttpResponse.class);

        when(httpsResponse.body()).thenReturn("{\"message\": \"success\"}");

        when(httpClient.send(any(HttpRequest.class),
                any((Class<HttpResponse.BodyHandler<String>>) (Class) HttpResponse.BodyHandler.class
                )))
                .thenReturn(httpsResponse);

        String jsonObject = au10tixClientService.getJsonBySerial(1);

        assertEquals(jsonObject, "{\"message\": \"success\"}");
    }

    @Test
    @SneakyThrows
    public void testJsonBySerialFail() {

        when(httpClient.send(any(HttpRequest.class),
                any((Class<HttpResponse.BodyHandler<String>>) (Class) HttpResponse.BodyHandler.class
                )))
                .thenThrow(new IllegalArgumentException("error message"));
        try {
            au10tixClientService.getJsonBySerial(1);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Error fetching json with serial id 1: error message");
        }
    }
}

