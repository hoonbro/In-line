package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, String> {
    public OfficeEntity findByOfficeName(String officeName);
    public OfficeEntity findByOfficeId(Long officeId);
}
