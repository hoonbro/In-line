package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.OfficeEntity;
import org.springframework.transaction.annotation.Transactional;

public interface OfficeService {
    @Transactional(rollbackFor = Exception.class)
    OfficeEntity registOffice(String officeName);

    Boolean duplicateOfficeName(String officeName);
}
