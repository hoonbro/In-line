package com.rtc.groupcall.controller;

import com.rtc.groupcall.RoomManager;
import com.rtc.groupcall.db.entity.RoomEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @GetMapping("/list")
    public ResponseEntity<List<RoomEntity>> roomList() {
        List<RoomEntity> list = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;

        try {
            list = roomManager.getAllRooms(1l);
            log.error("화상 회의방 리스트 조회 성공");
        }catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("화상 회의방 리스트 조회 실패 {}", e);
        }
        return new ResponseEntity<List<RoomEntity>>(list, status);
    }
}
