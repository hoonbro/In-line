package com.rtc.groupcall;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.rtc.groupcall.api.dto.RoomDto;
import com.rtc.groupcall.db.entity.RoomEntity;
import com.rtc.groupcall.db.repository.RoomRepository;
import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@Service
public class RoomManager {

    private final Logger log = LoggerFactory.getLogger(RoomManager.class);

    @Autowired
    private KurentoClient kurento;

    @Autowired
    RoomRepository roomRepository;

    private final ConcurrentMap<Long, Room> rooms = new ConcurrentHashMap<>();

    public RoomEntity getRoom(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public List<RoomEntity> getRooms(Long OfficeId){
        List<RoomEntity> roomEntitys = roomRepository.findAllByOfficeId(OfficeId);

        for(RoomEntity r: roomEntitys){
            if(rooms.get(r.getRoomId()) != null)
                continue;

            rooms.put(r.getRoomId(),  new Room(r.getRoomName(), kurento.createMediaPipeline(), r.getRoomId()));
        }

        return roomRepository.findAllByOfficeId(OfficeId);
    }

    public RoomEntity createRoom(RoomDto roomDto){
        RoomEntity roomEntity = RoomEntity.builder()
                .roomName(roomDto.getRoomName())
                .officeId(roomDto.getOfficeId())
                .userId(roomDto.getUserId())
                .build();
        return roomRepository.save(roomEntity);
    }

    public RoomEntity updateRoom(String roomName, RoomEntity roomEntity) {
        roomEntity.setRoomName(roomName);
        //map내용 수정
        log.info(roomEntity.toString());
        Room room = rooms.get(roomEntity.getRoomId());
        room.setRoomName(roomName);
        log.info(room.toString());
        rooms.put(roomEntity.getRoomId(), room);
        return roomRepository.save(roomEntity);
    }

    public void deleteRoom(Long roomId){
        roomRepository.deleteById(roomId);

        Room room = rooms.get(roomId);
        this.rooms.remove(roomId);
        room.close();
//        log.info("Room {} removed and closed, roomId = {}", room.getRoomName(), room.getRoomId());
    }

    public Room getRoom(String roomName, Long roomId) {
//        log.info("{}찾기 시도, roomId = {}", roomName, roomId);
        Room room = rooms.get(roomId);

        if (room == null) {
//            log.info("{}이/가 없다, 새로 생성!", roomName);
            room = new Room(roomName, kurento.createMediaPipeline(), roomId);
            rooms.put(roomId, room);
        }else if(room.getPipeline() == null){
//            log.info("{}에 pipeline이 없다!. create pipeline!", roomName);
            room.setPipeline(kurento.createMediaPipeline());
        }
//        log.info("{}을 찾음!, roomId = {}", roomName, roomId);
        log.info(room.toString());
        return room;
    }

    /**
     * Removes a room from the list of available rooms.
     *
     * @param room
     *          the room to be removed
     */
//    public void removeRoom(Room room) {
//        this.rooms.remove(room.getRoomId());
//        room.close();
//        log.info("Room {} removed and closed, roomId = {}", room.getRoomName(), room.getRoomId());
//    }

}
