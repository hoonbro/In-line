package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.RoomEntity;

public interface roomService {
    RoomEntity createtRoom(String roomName, Long officeId);

    public RoomEntity getLobby(Long officeId);
}
