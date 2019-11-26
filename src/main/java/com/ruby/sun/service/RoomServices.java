package com.ruby.sun.service;

import com.ruby.sun.domain.Room;
import com.ruby.sun.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServices {
    private RoomRepository roomRepository;
    public RoomServices(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add(room));
        return rooms;
    }
}
