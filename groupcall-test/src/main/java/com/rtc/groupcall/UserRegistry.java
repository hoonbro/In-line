package com.rtc.groupcall;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class UserRegistry {

    private final ConcurrentHashMap<Long, UserSession> usersById = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UserSession> usersBySessionId = new ConcurrentHashMap<>();

    public void register(UserSession user) {
//        log.info("user register user name : {}", user.getName());
        usersById.put(user.getUserId(), user);
        usersBySessionId.put(user.getSession().getId(), user);
//        log.info("user register : {}",user.toString());
    }

    public UserSession getById(Long userId) {
        return usersById.get(userId);
    }

    public UserSession getBySession(WebSocketSession session) {
        return usersBySessionId.get(session.getId());
    }

    public boolean exists(String name) {
        return usersById.keySet().contains(name);
    }

    public UserSession removeBySession(WebSocketSession session) {
        final UserSession user = getBySession(session);
        usersById.remove(user.getUserId());
        usersBySessionId.remove(session.getId());
        return user;
    }

}
