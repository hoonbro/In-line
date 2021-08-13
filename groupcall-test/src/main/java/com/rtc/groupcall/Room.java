package com.rtc.groupcall;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PreDestroy;

import lombok.Getter;
import lombok.Setter;
import org.kurento.client.Continuation;
import org.kurento.client.MediaPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@Getter
@Setter
public class Room implements Closeable {
    private final Logger log = LoggerFactory.getLogger(Room.class);

    private final ConcurrentMap<Long, UserSession> participants = new ConcurrentHashMap<>();
    private MediaPipeline pipeline;
    private String roomName;
    private final Long roomId;
    private final Long officeId;

    public Room(String roomName, MediaPipeline pipeline, Long roomId, Long officeId) {
        this.roomName = roomName;
        this.pipeline = pipeline;
        this.roomId = roomId;
        this.officeId = officeId;
//        log.info("ROOM {} 생성 성공", roomName);
    }

//    public Room(String roomName, Long roomId) {
//        this.roomName = roomName;
//        this.roomId = roomId;
////        log.info("ROOM {} 생성 성공", roomName);
//    }


    @PreDestroy
    private void shutdown() {
        this.close();
    }

    public UserSession join(Long officeId, Long userId, String userName, WebSocketSession session) throws IOException {
        log.info("ROOM {}: adding participant {}", this.roomName, userName);
        final UserSession participant = new UserSession(officeId, userId, userName, this.roomName, this.roomId, session, this.pipeline);
        joinRoom(participant);
        participants.put(participant.getUserId(), participant);
        sendParticipantNames(participant);
        return participant;
    }

    public void leave(UserSession user) throws IOException {
        log.info("{}님이 {}에서 나가셨습니다.", user.getUserName(), this.roomName);
        this.removeParticipant(user.getUserId(), user.getUserName());
        user.close();
    }

    private Collection<Long> joinRoom(UserSession newParticipant) throws IOException {
        final JsonObject newParticipantMsg = new JsonObject();
        newParticipantMsg.addProperty("id", "newParticipantArrived");
        newParticipantMsg.addProperty("userId", newParticipant.getUserId());
        newParticipantMsg.addProperty("userName", newParticipant.getUserName());

        final List<Long> participantsList = new ArrayList<>(participants.values().size());
//        log.info("ROOM {}: notifying other participants of new participant {}", roomName,
//                newParticipant.getName());

        for (final UserSession participant : participants.values()) {
            try {
                participant.sendMessage(newParticipantMsg);
            } catch (final IOException e) {
                log.debug("ROOM {}: participant {} could not be notified", roomName, participant.getUserName(), e);
            }
            participantsList.add(participant.getUserId());
        }

        return participantsList;
    }

    private void removeParticipant(Long userId, String userName) throws IOException {
        participants.remove(userId);

//        log.info("ROOM {}: notifying all users that {} is leaving the room", this.roomName, name);

        final List<Long> unnotifiedParticipants = new ArrayList<>();
        final JsonObject participantLeftJson = new JsonObject();
        participantLeftJson.addProperty("id", "participantLeft");
        participantLeftJson.addProperty("userId", userId);
        participantLeftJson.addProperty("userName", userName);
        for (final UserSession participant : participants.values()) {
            try {
                participant.cancelVideoFrom(userId);
                participant.sendMessage(participantLeftJson);
            } catch (final IOException e) {
                unnotifiedParticipants.add(participant.getUserId());
            }
        }

        if (!unnotifiedParticipants.isEmpty()) {
            log.debug("ROOM {}: The users {} could not be notified that {} left the room", this.roomName,
                    unnotifiedParticipants, userName);
        }

    }

    public void sendParticipantNames(UserSession user) throws IOException {

        final JsonArray participantsId = new JsonArray();
        final JsonArray participantsName = new JsonArray();
        for (final UserSession participant : this.getParticipantsvalues()) {
            if (participant.getUserId() != user.getUserId()) {
                final JsonElement userId = new JsonPrimitive(participant.getUserId());
                final JsonElement userName = new JsonPrimitive(participant.getUserName());
                participantsId.add(userId);
                participantsName.add(userName);
            }
        }

        final JsonObject existingParticipantsMsg = new JsonObject();
        existingParticipantsMsg.addProperty("id", "existingParticipants");
        existingParticipantsMsg.add("userId", participantsId);
        existingParticipantsMsg.add("userName", participantsName);
//        log.debug("PARTICIPANT {}: sending a list of {} participants", user.getName(),
//                participantsArray.size());
        user.sendMessage(existingParticipantsMsg);
    }

    public Collection<UserSession> getParticipantsvalues() {
        return participants.values();
    }

    public UserSession getParticipant(String name) {
        return participants.get(name);
    }

    @Override
    public void close() {
        for (final UserSession user : participants.values()) {
            try {
                user.close();
            } catch (IOException e) {
                log.debug("ROOM {}: Could not invoke close on participant {}", this.roomName, user.getUserName(),
                        e);
            }
        }

        participants.clear();

        pipeline.release(new Continuation<Void>() {

            @Override
            public void onSuccess(Void result) throws Exception {
                log.trace("ROOM {}: Released Pipeline", Room.this.roomName);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
                log.warn("PARTICIPANT {}: Could not release Pipeline", Room.this.roomName);
            }
        });

        log.debug("Room {} closed", this.roomName);
    }

}
