package com.ruby.sun.repository;

import com.ruby.sun.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TestEntityManager entityManager;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testFindByDate() throws ParseException {
        Reservation reservation = reservationRepository.findById(4L).get();
        reservation.setCreatedDate(DATE_FORMAT.parse("2016-07-01"));
        reservationRepository.save(reservation);

        Date today = new Date(new java.util.Date().getTime());
        Reservation expectedReservation = new Reservation();
        expectedReservation.setGuestId(1);
        expectedReservation.setRoomId(2);
        expectedReservation.setCreatedDate(today);
        entityManager.persist(expectedReservation);
        entityManager.flush();

        List<Reservation> reservations = reservationRepository.findByCreatedDateGreaterThanEqual(today);
        assertEquals(1, reservations.size());
        Reservation actualReservation = reservations.get(0);
        assertEquals(1, actualReservation.getGuestId());
        assertEquals(2, actualReservation.getRoomId());
    }
}
