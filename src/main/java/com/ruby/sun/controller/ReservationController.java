package com.ruby.sun.controller;

import com.ruby.sun.domain.RoomReservation;
import com.ruby.sun.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="/reservations")
public class ReservationController {
    private ReservationService reservationService;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations(@RequestParam(value="date", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Model model) throws ParseException {
        if(date == null){
            date = new Date(DATE_FORMAT.parse("2017-05-01").getTime());
        }
        List<RoomReservation> roomReservationList = this.reservationService.getRoomReservationsAfterDate(date);
        model.addAttribute("roomReservations", roomReservationList);
        return "reservations";
    }
}