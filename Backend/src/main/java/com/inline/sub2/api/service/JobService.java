package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.JobEntity;
import org.springframework.transaction.annotation.Transactional;

public interface JobService {
    @Transactional(rollbackFor = Exception.class)
    JobEntity getJobId(String jobName,Long officeId);

    JobEntity getJobName(Long jobId);
}
