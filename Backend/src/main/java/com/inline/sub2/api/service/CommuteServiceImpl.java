package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.CommuteDto;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.repository.CommuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class CommuteServiceImpl implements CommuteService {

    @Autowired
    CommuteRepository commuteRepository;


    @Override
    public CommuteEntity commuteLogin(CommuteDto commuteDto) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Date now = new Date();
        System.out.println(commuteDto.getUserId());
        System.out.println(commuteDto.getOfficeId());
        CommuteEntity commuteEntity = commuteRepository.findByUserIdAndYmd(commuteDto.getUserId(),now);
        System.out.println(commuteEntity);
        if(commuteEntity == null) {
            commuteEntity = new CommuteEntity();
            commuteEntity.setUserId(commuteDto.getUserId());
            commuteEntity.setOfficeId(commuteDto.getOfficeId());
            commuteEntity.setYmd(now);
            return commuteRepository.save(commuteEntity);
        }
        else {
            return commuteEntity;
        }
    }

    @Override
    public CommuteEntity commuteIn(Long commuteId) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        CommuteEntity commuteEntity = commuteRepository.findByCommuteId(commuteId);
        commuteEntity.setComeIn(new Date()); // 출근 시분초
        System.out.println(commuteEntity.getComeIn());
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

//    @Override
//    public List<CommuteEntity> commuteList(Long officeId) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//        Date now = new Date();
//        return commuteRepository.findByOfficeIdAndYmdAndComeInNotNullAndComeOutNull(officeId,now);
//    }
}
