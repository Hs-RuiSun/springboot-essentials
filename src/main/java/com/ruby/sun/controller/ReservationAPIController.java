package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import com.ruby.sun.domain.RoomReservation;
import com.ruby.sun.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ReservationAPIController {
    private ReservationService reservationService;

    public ReservationAPIController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @GetMapping("/reservations?date[gte]={date}")
    public List<RoomReservation> getAllReservationsAfterDate(@PathVariable(value="date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return this.reservationService.getRoomReservationsAfterDate(date);
    }

    @GetMapping("/reservations/{id}")
    public List<RoomReservation> getReservations(@PathVariable(value="date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return this.reservationService.getRoomReservationsAfterDate(date);
    }
}
