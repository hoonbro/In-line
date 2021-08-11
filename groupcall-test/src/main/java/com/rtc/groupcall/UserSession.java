package com.rtc.groupcall;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kurento.client.Continuation;
import org.kurento.client.EventListener;
import org.kurento.client.IceCandidate;
import org.kurento.client.IceCandidateFoundEvent;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;

/**
 *
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@Getter
@Setter
@ToString
public class UserSession implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(UserSession.class);

    private final Long userId;
    private final String userName;
    private final WebSocketSession session;

    private final MediaPipeline pipeline;
    private final Long roomId;
    private final String roomName;
    private final WebRtcEndpoint outgoingMedia;
    private final ConcurrentMap<Long, WebRtcEndpoint> incomingMedia = new ConcurrentHashMap<>();

    public UserSession(final Long userId, final String userName, String roomName, final Long roomId, final WebSocketSession session,
                       MediaPipeline pipeline) {

        this.userId = userId;
        this.pipeline = pipeline;
        this.userName = userName;
        this.roomId = roomId;
        this.session = session;
        this.roomName = roomName;
        this.outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();

        this.outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

            @Override
            public void onEvent(IceCandidateFoundEvent event) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "iceCandidate");
                response.addProperty("userId", userId);
                response.addProperty("userName", userName);
                response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(response.toString()));
                    }
                } catch (IOException e) {
                    log.debug(e.getMessage());
                }
            }
        });
    }

    public WebRtcEndpoint getOutgoingWebRtcPeer() {
        return outgoingMedia;
    }


    public WebSocketSession getSession() {
        return session;
    }

    /**
     * The room to which the user is currently attending.
     *
     * @return The room
     */
    public String getRoomName() {
        return this.roomName;
    }
    public Long getRoomId(){return this.roomId;}

    public void receiveVideoFrom(UserSession sender, String sdpOffer) throws IOException {
//        log.info("USER {} : {}님이 {}에 연결되었습니다.", this.userName, sender.getUserName(), this.roomName);

//        log.trace("USER {}: SdpOffer for {} is {}", this.name, sender.getName(), sdpOffer);

        final String ipSdpAnswer = this.getEndpointForUser(sender).processOffer(sdpOffer);
        final JsonObject scParams = new JsonObject();
        scParams.addProperty("id", "receiveVideoAnswer");
        scParams.addProperty("userId", sender.getUserId());
        scParams.addProperty("userName", sender.getUserName());
        scParams.addProperty("sdpAnswer", ipSdpAnswer);

//        log.trace("USER {}: SdpAnswer for {} is {}", this.name, sender.getName(), ipSdpAnswer);
        this.sendMessage(scParams);
        log.debug("gather candidates");
        this.getEndpointForUser(sender).gatherCandidates();
    }

    private WebRtcEndpoint getEndpointForUser(final UserSession sender) {
        if (sender.getUserId() == userId) {
//            log.debug("PARTICIPANT {}: configuring loopback", this.name);
            return outgoingMedia;
        }

//        log.debug("PARTICIPANT {}: receiving video from {}", this.name, sender.getName());

        WebRtcEndpoint incoming = incomingMedia.get(sender.getUserId());
        if (incoming == null) {
//            log.debug("PARTICIPANT {}: creating new endpoint for {}", this.name, sender.getName());
            incoming = new WebRtcEndpoint.Builder(pipeline).build();

            incoming.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("userId", sender.getUserId());
                    response.addProperty("userName", sender.getUserName());
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.debug(e.getMessage());
                    }
                }
            });

            incomingMedia.put(sender.getUserId(), incoming);
        }

//        log.debug("PARTICIPANT {}: obtained endpoint for {}", this.name, sender.getName());
        sender.getOutgoingWebRtcPeer().connect(incoming);

        return incoming;
    }

    public void cancelVideoFrom(final UserSession sender) {
        this.cancelVideoFrom(sender.getUserId());
    }

    public void cancelVideoFrom(final Long userId) {
//        log.debug("PARTICIPANT {}: canceling video reception from {}", this.name, senderName);
        final WebRtcEndpoint incoming = incomingMedia.remove(userId);
//        log.debug("PARTICIPANT {}: removing endpoint for {}", this.name, senderName);
        incoming.release(new Continuation<Void>() {
            @Override
            public void onSuccess(Void result) throws Exception {
//                log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
//                        UserSession.this.name, senderName);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
//                log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
//                        senderName);
            }
        });
    }

    @Override
    public void close() throws IOException {
//        log.debug("PARTICIPANT {}: Releasing resources", this.name);
        for (final Long remoteParticipantId : incomingMedia.keySet()) {

//            log.trace("PARTICIPANT {}: Released incoming EP for {}", this.name, remoteParticipantName);

            final WebRtcEndpoint ep = this.incomingMedia.get(remoteParticipantId);

            ep.release(new Continuation<Void>() {

                @Override
                public void onSuccess(Void result) throws Exception {
//                    log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
//                            UserSession.this.name, remoteParticipantName);
                }

                @Override
                public void onError(Throwable cause) throws Exception {
//                    log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
//                            remoteParticipantName);
                }
            });
        }

        outgoingMedia.release(new Continuation<Void>() {

            @Override
            public void onSuccess(Void result) throws Exception {
//                log.trace("PARTICIPANT {}: Released outgoing EP", UserSession.this.name);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
//                log.warn("USER {}: Could not release outgoing EP", UserSession.this.name);
            }
        });
    }

    public void sendMessage(JsonObject message) throws IOException {
//        log.debug("USER {}: Sending message {}", name, message);
        synchronized (session) {
            session.sendMessage(new TextMessage(message.toString()));
        }
    }

    public void addCandidate(IceCandidate candidate, Long userId) {
        if (this.userId != userId) {
            outgoingMedia.addIceCandidate(candidate);
        } else {
            WebRtcEndpoint webRtc = incomingMedia.get(userId);
            if (webRtc != null) {
                webRtc.addIceCandidate(candidate);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UserSession)) {
            return false;
        }
        UserSession other = (UserSession) obj;
        boolean eq = userName.equals(other.userName);
        eq &= roomName.equals(other.roomName);
        return eq;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + userName.hashCode();
        result = 31 * result + roomName.hashCode();
        return result;
    }
}
