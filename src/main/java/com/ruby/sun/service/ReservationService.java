package com.ruby.sun.service;

import com.ruby.sun.domain.Guest;
import com.ruby.sun.domain.Reservation;
import com.ruby.sun.domain.Room;
import com.ruby.sun.domain.RoomReservation;
import com.ruby.sun.repository.GuestRepository;
import com.ruby.sun.repository.ReservationRepository;
import com.ruby.sun.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findAll().forEach(list::add);
        return list;
    }

    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Reservation getReservation(long reservationId){
        return reservationRepository.findById(reservationId).orElse(null);
    }
    public List<RoomReservation> getRoomReservationsAfterDate(Date date){
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        Iterable<Reservation> reservations = this.reservationRepository.findByCreatedDate(date);

        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, Room> roomMap = new HashMap<>();
        rooms.forEach(room -> roomMap.put(room.getId(), room));

        reservations.forEach(reservation -> {
            Optional<Guest> guestOptional = this.guestRepository.findById(reservation.getGuestId());
            guestOptional.ifPresent(guest -> {
                RoomReservation roomReservation = new RoomReservation();

                roomReservation.setFirstName(guest.getFirstName());
                roomReservation.setLastName(guest.getLastName());
                roomReservation.setGuestId(guest.getId());

                Room room = roomMap.get(reservation.getRoomId());
                roomReservation.setRoomId(room.getId());
                roomReservation.setRoomName(room.getName());
                roomReservation.setRoomNumber(room.getNumber());
                roomReservation.setDate(reservation.getCreatedDate());

                roomReservationMap.put(reservation.getId(), roomReservation);
            });
        });
        return new ArrayList<>(roomReservationMap.values());
    }
}
