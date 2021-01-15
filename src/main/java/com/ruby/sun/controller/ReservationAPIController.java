package com.ruby.sun.controller;

import com.ruby.sun.domain.Reservation;
import com.ruby.sun.domain.RoomReservation;
import com.ruby.sun.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public Reservation getReservationById(@PathVariable long id) {
        return this.reservationService.getReservation(id);
    }

    @PostMapping(value = "/reservations",
            produces = { MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE })
    public Reservation saveReservation(@RequestBody Reservation reservation){
        return this.reservationService.saveReservation(reservation);
    }
}
