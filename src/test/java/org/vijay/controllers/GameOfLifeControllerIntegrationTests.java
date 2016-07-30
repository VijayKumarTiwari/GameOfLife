package org.vijay.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.vijay.GameoflifeApplication;
import org.vijay.commons.InMemoryGameBoardStore;
import org.vijay.domains.GameBoard;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vijayt on 7/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameoflifeApplication.class)
@WebIntegrationTest
public class GameOfLifeControllerIntegrationTests {
    private RestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080/board";

    @Autowired
    private InMemoryGameBoardStore inMemoryGameBoardStore;

    @Before
    public void clearData() {
        inMemoryGameBoardStore.getStore().clear();
        inMemoryGameBoardStore.resetCounter();
    }

    @Test
    public final void testCreate() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        ResponseEntity<GameBoard> response = restTemplate.exchange(requestEntity, GameBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameBoard body = response.getBody();
        List board = body.getBoard();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) board.get(i);
                if ((i == 0 && j == 1) || (i == 2 && j == 2) || (i == 1 && j == 1)) {
                    assertEquals(true, row.get(j));
                } else {
                    assertEquals(false, row.get(j));
                }
            }
        }
    }

    @Test
    public final void testGet() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, GameBoard.class);
        requestEntity = RequestEntity.get(new URI(baseUrl + "/1")).build();
        ResponseEntity<GameBoard> response = restTemplate.exchange(requestEntity, GameBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameBoard body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getId().intValue());

    }

    @Test
    public final void testUpdateToNextGen() throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, GameBoard.class);
        requestEntity = RequestEntity.put(new URI(baseUrl + "/1")).build();
        ResponseEntity<GameBoard> response = restTemplate.exchange(requestEntity, GameBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameBoard body = response.getBody();
        List board = body.getBoard();
        System.out.print(body);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) board.get(i);
                if ((i == 1 && j == 1) || (i == 1 && j == 2)) {
                    assertEquals(true, row.get(j));
                } else {
                    assertEquals(false, row.get(j));
                }
            }
        }
        response = restTemplate.exchange(requestEntity, GameBoard.class);
        body = response.getBody();
        board = body.getBoard();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                List row = (List) board.get(i);
                assertEquals(false, row.get(j));
            }
        }
    }

    @Test
    public final void testList() throws URISyntaxException {
        RequestEntity requestEntity = RequestEntity.get(new URI(baseUrl)).build();
        ResponseEntity<Collection> response = restTemplate.exchange(requestEntity, Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, GameBoard.class);
        requestEntity = RequestEntity.get(new URI(baseUrl)).build();
        response = restTemplate.exchange(requestEntity, Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2|2,1");
        requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, GameBoard.class);
        builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2|3,1");
        requestEntity = RequestEntity.post(builder.build().encode().toUri()).build();
        restTemplate.exchange(requestEntity, GameBoard.class);
        requestEntity = RequestEntity.get(new URI(baseUrl)).build();
        response = restTemplate.exchange(requestEntity, Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public final void testDelete() throws URISyntaxException {
        RequestEntity requestEntity = RequestEntity.delete(new URI(baseUrl + "/1")).build();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("size", "4")
                .queryParam("indexes", "1,2|3,3|2,2");
        RequestEntity requestEntity1 = RequestEntity.post(builder.build().encode().toUri()).build();
        ResponseEntity<GameBoard> response = restTemplate.exchange(requestEntity1, GameBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        responseEntity = restTemplate.exchange(requestEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
