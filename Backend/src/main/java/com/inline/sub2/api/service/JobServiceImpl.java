package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JobServiceImpl implements JobService{

    @Autowired
    JobRepository jobRepository;

    @Override
    public JobEntity getJobId(String jobName, Long officeId) {
        return jobRepository.findByJobNameAndOfficeId(jobName,officeId);
    }

    @Override
    public JobEntity getJobName(Long jobId) {
        return jobRepository.findByJobId(jobId);
    }


}
