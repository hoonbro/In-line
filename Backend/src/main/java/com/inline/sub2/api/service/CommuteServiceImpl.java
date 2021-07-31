package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.CommuteDto;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.repository.CommuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommuteServiceImpl implements CommuteService {

    @Autowired
    CommuteRepository commuteRepository;


    @Override
    public CommuteEntity commuteIn(CommuteDto commuteDto) {
        Date now = new Date();
        CommuteEntity commuteEntity = new CommuteEntity();
        commuteEntity.setUserId(commuteDto.getUserId());
        commuteEntity.setOfficeId(commuteDto.getOfficeId());
        commuteEntity.setYmd(now); // 출근 할 때 년,월,일
        commuteEntity.setComeIn(now); // 출근 시분초
//        commuteEntity.setComeOut(now); // 퇴근 시분초
        return commuteRepository.save(commuteEntity);
    }

    @Override
    public CommuteEntity commuteOut(Long commuteId) {
        System.out.println("여기1");
        Date now = new Date();
        CommuteEntity commuteEntity = commuteRepository.findByCommuteId(commuteId);
        System.out.println("여기2");
        System.out.println(commuteEntity.getComeIn());
        commuteEntity.setComeOut(now);
        return commuteRepository.save(commuteEntity);
    }
}
