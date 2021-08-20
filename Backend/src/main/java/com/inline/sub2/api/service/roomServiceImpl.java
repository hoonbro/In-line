package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.RoomEntity;
import com.inline.sub2.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class roomServiceImpl implements  roomService{

    @Autowired
    RoomRepository RoomRepository;

    public RoomEntity createtRoom(String roomName, Long officeId){
        RoomEntity roomEntity = new RoomEntity();

        roomEntity.setRoomName(roomName);
        roomEntity.setOfficeId(officeId);
        return RoomRepository.save(roomEntity);
    }

    public RoomEntity getLobby(Long officeId){
        return RoomRepository.findByOfficeIdAndRoomName(officeId, "로비");
    }
}
