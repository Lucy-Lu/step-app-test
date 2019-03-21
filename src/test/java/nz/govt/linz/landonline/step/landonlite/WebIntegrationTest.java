package nz.govt.linz.landonline.step.landonlite;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LandOnLiteApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders getHeaders = new HttpHeaders();
    HttpHeaders postHeaders = new HttpHeaders(new LinkedMultiValueMap<String, String>(Map.of(
            "Content-Type", List.of("application/json"))));
    HttpHeaders putHeaders = new HttpHeaders(new LinkedMultiValueMap<String, String>(Map.of(
            "Content-Type", List.of("application/json"))));
    HttpHeaders deleteHeaders = new HttpHeaders(new LinkedMultiValueMap<String, String>(Map.of(
            "Content-Type", List.of("application/json"))));
    HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders);
    HttpEntity<String> deleteEntity = new HttpEntity<String>(null, deleteHeaders);

    @Test
    public void testGetTitle() throws JSONException {
        ResponseEntity<String> response = get("/api/titles/1");
        JSONAssert.assertEquals("{id:1,ownerName:'Jane Doe'}", response.getBody(), false);
    }

    @Test
    public void testUpdateTitleOwner() throws JSONException {
        // Check that the update request returns the expected response
        ResponseEntity<String> response = post("/api/titles/1", "{\"id\":1,\"ownerName\":\"Brian Davies\"}");

        JSONAssert.assertEquals("{id:1,ownerName:'Brian Davies'}", response.getBody(), false);

        // Check that a fetch returns the updated owner name, and the original description
        response = get("/api/titles/1");
        JSONAssert.assertEquals("{id:1,ownerName:'Brian Davies',description:'Lot 1 on Block 1'}", response.getBody(), false);
    }

    @Test
    public void testAddTitle() throws JSONException {
        ResponseEntity<String> response = put("api/titles/3", "{\"id\":3,\"ownerName\":\"Tom Smith\",\"description\":\"Lot 3 on Block 3\"}");
        JSONAssert.assertEquals("{id:3,ownerName:'Tom Smith'}", response.getBody(), false);

//        response = get("/api/titles/3");
//        JSONAssert.assertEquals("{id:3,ownerName:'Tom Smith',description:'Lot 3 on Block 3'}", response.getBody(), false);

    }

    @Test
    public void testDeleteTitle() throws Exception {
        ResponseEntity<String> response = delete("/api/titles/1");

        Assert.assertEquals(response.getStatusCode().value(), 200);

        // Check that a fetch returns the updated owner name, and the original description
        response = get("/api/titles/1");

        Assert.assertEquals(response.getStatusCode().value(), 404);
    }

    private ResponseEntity<String> get(String url) {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url),
                HttpMethod.GET, entity, String.class);

        return response;
    }

    private ResponseEntity<String> post(String url, String body) {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url),
                HttpMethod.POST, new HttpEntity<>(body, postHeaders), String.class);
        System.out.println(response.getBody());
        return response;
    }

    private ResponseEntity<String> put(String url, String body) {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url),
                HttpMethod.PUT, new HttpEntity<>(body, putHeaders), String.class);
        System.out.println(response.getBody());
        return response;
    }

    private ResponseEntity<String> delete(String url) {
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(url),
                HttpMethod.DELETE, deleteEntity, String.class);
        return response;
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}