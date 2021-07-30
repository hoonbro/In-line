package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.OnBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnBoardRepository  extends JpaRepository<OnBoardEntity, String> {
    public OnBoardEntity findByEmail(String email);
}
