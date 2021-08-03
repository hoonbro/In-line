package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, String> {
    public JobEntity findByJobNameAndOfficeId(String jobName,Long officeId);

    public JobEntity findByJobId(Long jobId);
}
