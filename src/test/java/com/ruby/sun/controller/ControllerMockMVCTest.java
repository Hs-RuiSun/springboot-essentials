package com.ruby.sun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruby.sun.config.AppConfig;
import com.ruby.sun.domain.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@ComponentScan("com.ruby.sun") //those two could move to Configuration class for common spring project
@EnableWebMvc //
public class ControllerMockMVCTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testApplicationContext(){
        assertNotNull(webApplicationContext.getServletContext());
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("reservationController"));
        assertNotNull(webApplicationContext.getBean("reservationService")); //the full Spring application context is started but without the server
    }

    @Test
    public void testLogin() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRESTAPIGETWithPathVariable() throws Exception {
        this.mockMvc.perform(get("/api/reservations/{id}", 1)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestId").value(200));
    }

    @Test
    public void testRESTAPIGETWithParameters() throws Exception {
        this.mockMvc.perform(get("/api/reservations")
                                .param("date[gte]", "2021-01-01")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].guestId").value(200));
    }

    @Test
    public void testReservationAPI() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setRoomId(8);
        reservation.setGuestId(200);
        reservation.setCreatedDate(new Date());
        String reservationJSON = objectMapper.writeValueAsString(reservation);
        this.mockMvc.perform(post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(reservationJSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(8))
                .andExpect(jsonPath("$.guestId").value(200));
    }
}