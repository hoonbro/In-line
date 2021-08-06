package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.CommuteDto;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.repository.CommuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.TimeZone;

@Service
public class CommuteServiceImpl implements CommuteService {

    @Autowired
    CommuteRepository commuteRepository;


    @Override
    public CommuteEntity commuteLogin(CommuteDto commuteDto) {
        CommuteEntity commuteEntity = new CommuteEntity();
        commuteEntity.setUserId(commuteDto.getUserId());
        commuteEntity.setOfficeId(commuteDto.getOfficeId());
        commuteEntity.setYmd(new Date());
        return commuteRepository.save(commuteEntity);
    }

    @Override
    public CommuteEntity commuteIn(Long commuteId) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        CommuteEntity commuteEntity = commuteRepository.findByCommuteId(commuteId);
        commuteEntity.setComeIn(new Date()); // 출근 시분초
        return commuteRepository.save(commuteEntity);
    }

    @Override
    public CommuteEntity commuteOut(Long commuteId) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        Date now = new Date();
        CommuteEntity commuteEntity = commuteRepository.findByCommuteId(commuteId);
        System.out.println(commuteEntity.getComeIn());
        commuteEntity.setComeOut(now);
        return commuteRepository.save(commuteEntity);
    }
}
