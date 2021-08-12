package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.*;
import com.inline.sub2.api.service.ChatService;
import com.inline.sub2.api.service.CommuteService;
import com.inline.sub2.api.service.NotificationService;
import com.inline.sub2.api.service.OfficeManager;
import com.inline.sub2.db.entity.ChatEntity;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Controller
public class SocketController {

    @Autowired
    ChatService chatService;

    @Autowired
    CommuteService commuteService;

    @Autowired
    OfficeManager officeManager;

    @Autowired
    NotificationService notificationService;

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/pub/{officeId}")
    // /send로 메시지를 반환합니다.
    @SendTo("/sub/{officeId}")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public Map<String,Object> SocketHandler(ChatDto chatDto, @DestinationVariable("officeId") Long officeId) throws IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Map<String,Object> map = new HashMap<>();
        chatDto.setOfficeId(officeId);
        List<CommuteEntity> list = null;
        ChatEntity chatEntity = new ChatEntity();
        if(chatDto.getType().equals("CHAT")) {
             chatEntity = chatService.insertMessage(chatDto);
            chatDto.setSendDate(chatEntity.getSendDate());
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chatDto.setSendTime(transFormat.format(chatEntity.getSendTime()).split(" ")[1]);
            map.put("type",chatDto.getType());
            map.put("chatDto",chatDto);
        }
        else if(chatDto.getType().equals("ENTER")) {
            System.out.println("ENTER 들어오는지 여부");
            Office office = officeManager.getOffice(chatDto.getOfficeId());
            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setUserId(chatDto.getUserId());
            participantDto.setOfficeId(chatDto.getOfficeId());
            participantDto.setUserName(chatDto.getUserName());
            participantDto.setRoomId(chatDto.getRoomId());
            ConcurrentMap<Long,ParticipantDto> participants = office.join(participantDto);
            map.put("type","userUpdate");
            map.put("members",participants);
        }
        else if(chatDto.getType().equals("EXIT")) {
            System.out.println("EXIT 들어오는지 여부");
            Office office = officeManager.getOffice(chatDto.getOfficeId());
            ConcurrentMap<Long,ParticipantDto> participants = office.removeParticipant(chatDto.getUserId());
            map.put("type","userUpdate");
            map.put("members",participants);
        }
        else if(chatDto.getType().equals("MOVE")) {
            System.out.println("MOVE 들어오는지 여부");
            Office office = officeManager.getOffice(chatDto.getOfficeId());
            ConcurrentMap<Long,ParticipantDto> participants = office.moveParticipant(chatDto);
            map.put("type","userUpdate");
            map.put("members" , participants);
        }
        return map;
    }


    @MessageMapping("/notification/{userId}")
    @SendTo("/queue/{userId}")
    public NotificationEntity invite(NotificationEntity notificationEntity, @DestinationVariable("userId") Long userId) throws IOException {
//        System.out.println("받는 userId ? :  " + userId);
//        System.out.println("보내는 userId ? :  " + inviteDto.getUserId());
//        System.out.println("보내는 userNmae ? :  " + inviteDto.getUserName());
//        System.out.println("roomId ? :  " + inviteDto.getRoomId());
        notificationEntity.setReceiveId(userId);
        notificationEntity = notificationService.registNotification(notificationEntity);
        return notificationEntity;
    }
}