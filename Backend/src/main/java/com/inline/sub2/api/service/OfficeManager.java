package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.Office;
import com.inline.sub2.api.dto.ParticipantDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class OfficeManager {
    private final ConcurrentMap<Long, Office> offices = new ConcurrentHashMap<>();



    public Office getOffice(Long officeId) {
        Office office = offices.get(officeId);

        if(office ==null) {
            office = new Office(officeId);
            offices.put(officeId,office);
        }
        return office;
    }



//    public void addOffices(Long officeId, ConcurrentMap<Long,ParticipantDto> participants) {
//
//    }



//    public void addOffices(ParticipantDto participantDto) {
//        Office office = offices.get(participantDto.getOfficeId());
//        ConcurrentMap<Long,ParticipantDto> participants = office.getParticipants();
//        participants.put(participantDto.getUserId(),participantDto);
//        offices.put(participantDto.getOfficeId(),office);
//    }



}

