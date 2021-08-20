package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.OnBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnBoardRepository  extends JpaRepository<OnBoardEntity, Long> {
    public OnBoardEntity findByEmail(String email);
    public List<OnBoardEntity> findAllByOfficeId(Long officeId);
}
