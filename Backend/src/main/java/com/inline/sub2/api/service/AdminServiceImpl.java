package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.repository.CommuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    CommuteRepository commuteRepository;

    @Override
    public Map<String, List<CommuteEntity>> getCommutesByDate(Long officeId) {
        Map<String, List<CommuteEntity>> map = new TreeMap<>();
        List<String> dateList = commuteRepository.findByYmd(officeId);
        List<CommuteEntity> list = commuteRepository.findAllByOfficeId(officeId);

        for(String date : dateList){
            map.put(date, new LinkedList<>());
        }

        for(CommuteEntity commuteEntity : list){
            Date from = commuteEntity.getYmd();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM");
            String date = transFormat.format(from);

            List<CommuteEntity> temp = map.get(date);
            temp.add(commuteEntity);
            map.put(date, temp);
        }

        return map;
    }

}
