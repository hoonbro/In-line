package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<DeptEntity, Long> {
    public DeptEntity findByDeptNameAndOfficeId(String deptName,Long officeId);

    public DeptEntity findByDeptId(Long deptId);

    public List<DeptEntity> findByOfficeId(Long officeId);
}
