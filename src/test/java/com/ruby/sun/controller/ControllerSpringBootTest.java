package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {})
@AutoConfigureMockMvc
public class ControllerSpringBootTest {
    @Autowired
    private ReservationAPIController reservationAPIController;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void setTestRestTemplate(){
        String url = "http://localhost:" + port + "/api/reservations/1";
        Reservation reservation = restTemplate.getForEntity(url, Reservation.class).getBody();
        assertNotNull(reservation);
        assertEquals(8, reservation.getRoomId());
        assertEquals(200, reservation.getGuestId());
    }

    @Test
    public void testTestRestTemplate(){
        String url = "http://localhost:" + port + "/api/reservations/1";
        Reservation reservation = testRestTemplate.getForEntity(url, Reservation.class).getBody();
        assertNotNull(reservation);
        assertEquals(8, reservation.getRoomId());
        assertEquals(200, reservation.getGuestId());
    }

    @Test
    public void testGetAPI(){
        Reservation reservation = reservationAPIController.getReservationById(1);
        assertNotNull(reservation);
        assertEquals(8, reservation.getRoomId());
        assertEquals(200, reservation.getGuestId());
    }

    @Test
    public void testMockMVCWithSpringBoot() throws Exception {
        this.mockMvc.perform(get("/api/reservations/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestId").value(200));
    }
}
