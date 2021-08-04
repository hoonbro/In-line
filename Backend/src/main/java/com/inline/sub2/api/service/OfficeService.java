package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OfficeService {
    @Transactional(rollbackFor = Exception.class)
    OfficeEntity registOffice(String officeName);

    OfficeEntity getOfficeName(Long officeId);

    Boolean duplicateOfficeName(String officeName);
}
