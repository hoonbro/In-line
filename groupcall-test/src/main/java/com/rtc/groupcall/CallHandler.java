package com.rtc.groupcall;

import java.io.IOException;

import com.rtc.groupcall.db.entity.RoomEntity;
import org.kurento.client.IceCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 *
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
public class CallHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(CallHandler.class);

    private static final Gson gson = new GsonBuilder().create();

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private UserRegistry registry;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);

        final UserSession user = registry.getBySession(session);

//        if (user != null) {
//            log.debug("Incoming message from user '{}': {}", user.getName(), jsonMessage);
//        } else {
//            log.debug("Incoming message from new user: {}", jsonMessage);
//        }

        switch (jsonMessage.get("id").getAsString()) {
            case "joinRoom":
                joinRoom(jsonMessage, session);
                break;
            case "receiveVideoFrom":
                final Long senderId = jsonMessage.get("sender").getAsLong();
                final UserSession sender = registry.getById(senderId);
                final String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
                user.receiveVideoFrom(sender, sdpOffer);
                break;
            case "leaveRoom":
                leaveRoom(user);
                break;
            case "onIceCandidate":
                JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();

                if (user != null) {
                    IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
                            candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
                    user.addCandidate(cand, jsonMessage.get("userId").getAsLong());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UserSession user = registry.removeBySession(session);
        final Room room = roomManager.getRoom(user.getRoomName(), user.getRoomId(), user.getOfficeId());
        RoomEntity lobby = roomManager.getLobby(room.getOfficeId());
        roomManager.moveUser(user.getUserId(), lobby.getRoomId());
        room.leave(user);
    }

    private void joinRoom(JsonObject params, WebSocketSession session) throws IOException {
        final String roomName = params.get("roomName").getAsString();
        final String userName = params.get("userName").getAsString();
        final Long roomId = params.get("roomId").getAsLong();
        final Long userId = params.get("userId").getAsLong();
        final Long officeId = params.get("officeId").getAsLong();
//        log.info(" {}님의 {} 접근 요청", name, roomName);

        Room room = roomManager.getRoom(roomName, roomId, officeId);
        roomManager.moveUser(userId, roomId);
        final UserSession user = room.join(officeId, userId, userName, session);
        registry.register(user);
    }

    private void leaveRoom(UserSession user) throws IOException {
        final Room room = roomManager.getRoom(user.getRoomName(), user.getRoomId(), user.getOfficeId());
        room.leave(user);
        RoomEntity lobby = roomManager.getLobby(room.getOfficeId());
        roomManager.moveUser(user.getUserId(), lobby.getRoomId());
//        if (room.getParticipants().isEmpty()) {
//            log.info("{}이 비었습니다.", room.getRoomName());
////            roomManager.removeRoom(room);
//        }
    }
}
