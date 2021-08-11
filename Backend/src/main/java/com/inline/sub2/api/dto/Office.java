package com.inline.sub2.api.dto;

import com.inline.sub2.api.service.OfficeManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
public class Office {

    @Autowired
    OfficeManager officeManager;


    private final ConcurrentMap<Long,ParticipantDto> participants = new ConcurrentHashMap<>(); //userId,참여자정보
    private final Long officeId;



    public Office(Long officeId) {
        this.officeId = officeId;
    }

    public ConcurrentMap<Long,ParticipantDto> join(ParticipantDto participantDto) throws IOException {
        Long userId = participantDto.getUserId();
        participants.put(userId,participantDto);
        return participants;
    }

    public ConcurrentMap<Long,ParticipantDto> removeParticipant(Long userId) {
        participants.remove(userId);
        return participants;
    }

    public ParticipantDto moveParticipant(ChatDto chatDto) { // 방이동시

        ParticipantDto participantDto = participants.get(chatDto.getUserId());
        participantDto.setRoomId(chatDto.getRoomId());
        participants.put(chatDto.getUserId(),participantDto);

        return participantDto;

    }




//    public List<ParticipantDto> addParticipant(ParticipantDto participantDto) {
//        Long userId = participantDto.getUserId();
//        Long officeId = participantDto.getOfficeId();
//
//        participants.put(userId,participantDto);
//
////        officeManager.addOffices(officeId, participants);
//
//    }

//    public List<ParticipantDto> minusParticipant(ParticipantDto participantDto) {
//
//    }

//    public ConcurrentMap<Long,ParticipantDto> getParticipants(Long officeId) {
//
//    }





//    public ConcurrentMap<Long,ParticipantDto> getParticipants() {
//        return participants;
//    }



//    public ParticipantDto join(ParticipantDto participantDto) throws IOException {
//        final ParticipantDto participant = participantDto;
//        Office office = officeManager.getOffices(participantDto.getOfficeId());
//
//
////        joinOffice(participant);
//        participants.put(participant.getUserId(),participant);
//    }

//    private Collection<Long> joinOffice(ParticipantDto newParticipant) {
//        final List<Long> participantsList = new ArrayList<>(participants.values().size());
//
//        for(final ParticipantDto participant : participants.values()) {
//            participantsList.add(participant.getUserId());
//        }
//        return participantsList;
//    }



}
