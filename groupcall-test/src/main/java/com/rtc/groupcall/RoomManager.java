package com.rtc.groupcall;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.rtc.groupcall.api.dto.RoomDto;
import com.rtc.groupcall.api.dto.RoomUserDto;
import com.rtc.groupcall.db.entity.RoomEntity;
import com.rtc.groupcall.db.entity.UserEntity;
import com.rtc.groupcall.db.repository.RoomRepository;
import com.rtc.groupcall.db.repository.UserRepository;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

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

    @Autowired
    UserRepository userRepository;

    private final ConcurrentMap<Long, Room> rooms = new ConcurrentHashMap<>();

    public Map<Long, Collection> getOfficeRooms(Long officeId){
        Map<Long, Collection> roomParticipants = new TreeMap<>();
        ConcurrentMap<Long, UserSession> participants;
        for(Long key :rooms.keySet()){
            Room room = rooms.get(key);

            if(room.getOfficeId() != officeId)
                continue;

            participants = room.getParticipants();
            Map<Long, RoomUserDto> participantsMap = new TreeMap<>();

            for(Long userKey : participants.keySet()){
                UserSession user = participants.get(userKey);

                RoomUserDto userDto = RoomUserDto.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .roomId(user.getRoomId())
                        .roomName(user.getRoomName())
                        .officeId(user.getOfficeId())
                        .profileImage(user.getProfileImage())
                        .build();

                participantsMap.put(userKey, userDto);
            }

            roomParticipants.put(key, participantsMap.values());
        }
        return roomParticipants;
    }
    public RoomEntity getRoom(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public List<RoomEntity> getRooms(Long officeId){
        List<RoomEntity> roomEntitys = roomRepository.findAllByOfficeId(officeId);
        for(RoomEntity r: roomEntitys){
            if(rooms.get(r.getRoomId()) != null)
                continue;

            rooms.put(r.getRoomId(),  new Room(r.getRoomName(), kurento.createMediaPipeline(), r.getRoomId(), r.getOfficeId()));
        }
        List<RoomEntity> list = roomRepository.findAllByOfficeId(officeId);
        Map<Long, Collection> map = getOfficeRooms(officeId);

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getRoomUserList() != null && list.get(i).getRoomUserList().size() == map.get(list.get(i).getRoomId()).size())
                continue;
            list.get(i).setRoomUserList(map.get(list.get(i).getRoomId()));
        }
        return roomRepository.findAllByOfficeId(officeId);
    }

    public RoomEntity createRoom(RoomDto roomDto){
        RoomEntity roomEntity = RoomEntity.builder()
                .roomName(roomDto.getRoomName())
                .officeId(roomDto.getOfficeId())
                .userId(roomDto.getUserId())
                .build();
        rooms.put(roomDto.getRoomId(),  new Room(roomDto.getRoomName(), kurento.createMediaPipeline(), roomDto.getRoomId(), roomDto.getOfficeId()));
        return roomRepository.save(roomEntity);
    }

    public RoomEntity updateRoom(String roomName, RoomEntity roomEntity) {
        roomEntity.setRoomName(roomName);
        //map내용 수정
        Room room = rooms.get(roomEntity.getRoomId());
        room.setRoomName(roomName);
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

    public RoomEntity getLobby(Long officeId){
        return roomRepository.findByOfficeIdAndRoomName(officeId, "로비");
    }

    public void moveUser(Long userId, Long roomId){
        userRepository.moveUser(userId, roomId);
    }

    public Room getRoom(String roomName, Long roomId, Long officeId) {
//        log.info("{}찾기 시도, roomId = {}", roomName, roomId);
        Room room = rooms.get(roomId);

        if (room == null) {
//            log.info("{}이/가 없다, 새로 생성!", roomName);
            room = new Room(roomName, kurento.createMediaPipeline(), roomId, officeId);
            rooms.put(roomId, room);
        }else if(room.getPipeline() == null){
//            log.info("{}에 pipeline이 없다!. create pipeline!", roomName);
            room.setPipeline(kurento.createMediaPipeline());
        }
//        log.info("{}을 찾음!, roomId = {}", roomName, roomId);
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
