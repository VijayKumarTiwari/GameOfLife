package org.vijay.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.vijay.GameoflifeApplication;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vijayt on 7/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameoflifeApplication.class)
@WebIntegrationTest
public class GameOfLifeServiceIntegrationTests {
    private RestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080";

    @Test
    public final void testConfigure() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/config")
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        ResponseEntity<List> response = restTemplate.exchange(requestEntity, List.class);
        List body = response.getBody();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) body.get(i);
                if ((i == 0 && j == 1) || (i == 2 && j == 2) || (i == 1 && j == 1)) {
                    assertEquals(true, row.get(j));
                } else {
                    assertEquals(false, row.get(j));
                }
            }
        }
    }

    @Test
    public final void testGetState() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/config")
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, List.class);
        requestEntity = RequestEntity.get(new URI(baseUrl + "/get-state")).build();
        ResponseEntity<List> response = restTemplate.exchange(requestEntity, List.class);
        List body = response.getBody();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) body.get(i);
                if ((i == 0 && j == 1) || (i == 2 && j == 2) || (i == 1 && j == 1)) {
                    assertEquals(true, row.get(j));
                } else {
                    assertEquals(false, row.get(j));
                }
            }
        }
    }

    @Test
    public final void testCalcNextGen() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/config")
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, List.class);
        requestEntity = RequestEntity.get(new URI(baseUrl + "/calc-next-gen")).build();
        ResponseEntity<List> response = restTemplate.exchange(requestEntity, List.class);
        List body = response.getBody();
        System.out.print(body);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) body.get(i);
                if ((i == 1 && j == 1) || (i == 1 && j == 2)) {
                    assertEquals(true, row.get(j));
                } else {
                    assertEquals(false, row.get(j));
                }
            }
        }
        response = restTemplate.exchange(requestEntity, List.class);
        body = response.getBody();
        System.out.print(body);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) body.get(i);
                assertEquals(false, row.get(j));
            }
        }
    }
}
