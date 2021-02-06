package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class RestTemplateTest {
    private RestTemplate restTemplate;

    @Before
    public void setup(){
        restTemplate = new RestTemplate();
    }

    @Test
    public void testRestTemplate(){
        String url = "http://localhost:8000/api/reservations/1";
        Reservation reservation = restTemplate.getForObject(url, Reservation.class);
        assertEquals(200, reservation.getGuestId());

        ResponseEntity<Reservation> responseEntity = restTemplate.getForEntity(url, Reservation.class);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(200, responseEntity.getBody().getGuestId());

        url = "http://localhost:8000/api/reservations/{id}";
        reservation = restTemplate.getForObject(url, Reservation.class, 1);
        assertEquals(200, reservation.getGuestId());

        final URI requestUri = UriComponentsBuilder
                .fromUriString("http://localhost:8000/api/reservations/{id}")
                .build("123", "234");
        final RequestEntity<Void> requestEntity = RequestEntity
                .get(requestUri)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        responseEntity = restTemplate.exchange(requestEntity, Reservation.class);
        assertEquals("Request should be successful", HttpStatus.OK, responseEntity.getStatusCode());
    }
}
