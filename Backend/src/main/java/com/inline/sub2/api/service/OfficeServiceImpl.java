package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl implements OfficeService{
    @Autowired
    OfficeRepository officeRepository;
    @Override
    public OfficeEntity registOffice(String officeName) {
        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setOfficeName(officeName);
        return officeRepository.save(officeEntity);
    }
}
