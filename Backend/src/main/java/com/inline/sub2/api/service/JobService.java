package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.JobEntity;

public interface JobService {
    JobEntity getJobId(String jobName);
}
