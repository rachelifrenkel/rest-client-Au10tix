package services;

import com.au10tix.services.Au10tixClientService;
import com.au10tix.services.JsonOperationsImp;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonOperationTest {

    @InjectMocks
    private JsonOperationsImp jsonOperationsImp;
    @Mock
    private Au10tixClientService au10tixClientService;


    @Test
    @SneakyThrows
    public void shouldPerformHandle() {
        String jsonObject1 = "{\"serial\":1,\"message\": {\"subset\": {\"general\": {\"quantities\": {\"first\":1,\"second\":2,\"third\":3},\"information\": {\"date\":\"1-2-2020\",\"version\":\"1.00\"}}}}}";
        String jsonObject2 = "{\"serial\":2,\"message\": {\"subset\": {\"general\": {\"quantities\": {\"first\":4,\"second\":1,\"third\":6},\"information\": {\"date\":\"1-2-2020\",\"version\":\"2.00\"}}}}}";
        String jsonObjectResult = "{\"serial\":3,\"message\":{\"subset\":{\"general\":{\"information\":{\"date\":\"1-2-2021\",\"version\":\"3.00\"},\"quantities\":{\"first\":4,\"second\":2,\"third\":6}}}}}";

        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("correct");

        when(au10tixClientService.getJsonBySerial(1))
                .thenReturn(jsonObject1);

        when(au10tixClientService.getJsonBySerial(2))
                .thenReturn(jsonObject2);

        when(au10tixClientService.sendJson(jsonObjectResult)).thenReturn(httpResponse);

        jsonOperationsImp.handle();
    }


    @Test
    @SneakyThrows
    public void shouldPerformHandleFail() {
        String jsonObject1 = "{\"serial\":1,\"message\": {\"subset\": {\"general\": {\"quantities\": {\"first\":1,\"second\":2,\"third\":3},\"information\": {\"date\":\"1-2-2020\",\"version\":\"1.00\"}}}}}";
        String jsonObject2 = "{\"serial\":2,\"message\": {\"subset\": {\"general\": {\"quantities\": {\"first\":4,\"second\":1,\"third\":6},\"information\": {\"date\":\"1-2-2020\",\"version\":\"2.00\"}}}}}";
        String jsonObjectResult = "{\"serial\":3,\"message\":{\"subset\":{\"general\":{\"information\":{\"date\":\"1-2-2021\",\"version\":\"3.00\"},\"quantities\":{\"first\":4,\"second\":2,\"third\":6}}}}}";

        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("incorrect");

        when(au10tixClientService.getJsonBySerial(1))
                .thenReturn(jsonObject1);

        when(au10tixClientService.getJsonBySerial(2))
                .thenReturn(jsonObject2);

        when(au10tixClientService.sendJson(jsonObjectResult)).thenReturn(httpResponse);

        try {
            jsonOperationsImp.handle();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Request to process json is not successful");
        }

    }
}