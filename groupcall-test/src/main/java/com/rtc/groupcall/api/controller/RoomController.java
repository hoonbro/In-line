package com.rtc.groupcall.api.controller;

import com.rtc.groupcall.RoomManager;
import com.rtc.groupcall.api.dto.RoomDto;
import com.rtc.groupcall.db.entity.RoomEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @GetMapping
    public ResponseEntity<List<RoomEntity>> getRooms(@RequestParam("officeId") Long officeId) {
        List<RoomEntity> list = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;

        try {
            list = roomManager.getRooms(officeId);
            log.info("화상 회의방 리스트 조회 성공");
        }catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("화상 회의방 리스트 조회 실패 {}", e);
        }
        return new ResponseEntity<List<RoomEntity>>(list, status);
    }

    @PostMapping
    public ResponseEntity<RoomEntity> createRoom(@RequestBody RoomDto roomDto){
        RoomEntity roomEntity = null;
        HttpStatus status = HttpStatus.CREATED;
        try {
            roomEntity = roomManager.createRoom(roomDto);
            log.info("방 생성 성공");
        }catch (Exception e){
            log.error("방 생성 실패 :{}", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<RoomEntity>(roomEntity, status);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomEntity> updateRoom(@PathVariable("roomId") Long roomId, @RequestBody RoomDto roomDto){
        roomDto.setRoomId(roomId);
        HttpStatus status = HttpStatus.CREATED;
        RoomEntity roomEntity = null;
        try{
            roomEntity = roomManager.updateRoom(roomDto);
            log.info("방 정보 수정 성공");
        }catch (Exception e){
            log.error("방 정보 수정 실패 : {}", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<RoomEntity>(roomEntity, status);
    }
}
