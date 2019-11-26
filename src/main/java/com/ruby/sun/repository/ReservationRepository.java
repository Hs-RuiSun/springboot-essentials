package com.ruby.sun.repository;

import com.ruby.sun.domain.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByCreatedDateGreaterThanEqual(Date date);
    List<Reservation> findByCreatedDate(Date date);
}