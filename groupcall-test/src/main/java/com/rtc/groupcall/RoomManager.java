package com.rtc.groupcall;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//import com.rtc.groupcall.db.entity.RoomEntity;
//import com.rtc.groupcall.db.repository.RoomRepository;
import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
public class RoomManager {

    private final Logger log = LoggerFactory.getLogger(RoomManager.class);

    @Autowired
    private KurentoClient kurento;

//    @Autowired
//    RoomRepository roomRepository;

    private final ConcurrentMap<String, Room> rooms = new ConcurrentHashMap<>();

    /**
     * Looks for a room in the active room list.
     *
     * @param roomName
     *          the name of the room
     * @return the room if it was already created, or a new one if it is the first time this room is
     *         accessed
     */
//    public List<RoomEntity> getAllRooms(Long OfficeId){
//        List<RoomEntity> roomEntitys = roomRepository.findAllByOfficeId(OfficeId);
//
//        for(RoomEntity r: roomEntitys){
//            rooms.put(r.getRoomName(),  new Room(r.getRoomName(), 1l));
//        }
//
//        return roomRepository.findAllByOfficeId(OfficeId);
//    }

    public Room getRoom(String roomName) {
        log.debug("Searching for room {}", roomName);
        Room room = rooms.get(roomName);

        if (room == null) {
            log.debug("Room {} not existent. Will create now!", roomName);
            room = new Room(roomName, kurento.createMediaPipeline(), 1l);
            rooms.put(roomName, room);
        }else if(room.getPipeline() == null){
            room.setPipeline(kurento.createMediaPipeline());
        }
        log.debug("Room {} found!", roomName);
        return room;
    }

    /**
     * Removes a room from the list of available rooms.
     *
     * @param room
     *          the room to be removed
     */
    public void removeRoom(Room room) {
        this.rooms.remove(room.getRoomName());
        room.close();
        log.info("Room {} removed and closed", room.getRoomName());
    }

}
