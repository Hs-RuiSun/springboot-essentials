package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient // has to add for WebTestClient
public class ControllerRestTemplateTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private WebTestClient webTestClient;
    @LocalServerPort
    private int port;

    @Test
    public void testExchange(){
        String url = "http://localhost:" + port + "/api/reservations/1";
        ResponseEntity<Reservation> reservationResponseEntity =
                testRestTemplate.exchange(url, HttpMethod.GET, null, Reservation.class);
        assertNotNull(reservationResponseEntity.getBody());
    }

    @Test
    public void testWebClient(){
        webTestClient.get()
                .uri("/api/reservations/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reservation.class);
    }
}
