package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import com.ruby.sun.domain.RoomReservation;
import com.ruby.sun.repository.ReservationRepository;
import com.ruby.sun.repository.RoomRepository;
import com.ruby.sun.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationAPIController.class)
class ControllerWebMVCTest {
    @MockBean
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ApplicationContext applicationContext;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void testMockBean(){
        //mock bean being registered in the application context
        assertNotNull(applicationContext.getBean(ReservationService.class));
        //assertNotNull(applicationContext.getBean(ReservationRepository.class)); doesn't exist in the application context
    }

    @Test
    void testGetReservations() throws Exception {
        Date date = DATE_FORMAT.parse("2017-01-01");
        List<RoomReservation> mockReservationList = new ArrayList<>();
        RoomReservation mockRoomReservation = new RoomReservation();
        mockRoomReservation.setLastName("Test");
        mockRoomReservation.setFirstName("JUnit");
        mockRoomReservation.setDate(date);
        mockRoomReservation.setGuestId(1);
        mockRoomReservation.setRoomNumber("J1");
        mockRoomReservation.setRoomId(100);
        mockRoomReservation.setRoomName("JUnit Room");
        mockReservationList.add(mockRoomReservation);

        Reservation reservation = new Reservation();
        reservation.setCreatedDate(new Date());
        reservation.setGuestId(8);

        when(reservationService.getRoomReservationsAfterDate(date)).thenReturn(mockReservationList);

        this.mockMvc.perform(get("/api/reservations/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("8")));
    }
}
