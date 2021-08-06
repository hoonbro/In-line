package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.CommuteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommuteRepository extends JpaRepository<CommuteEntity,String> {

    @Query("SELECT u FROM CommuteEntity u WHERE u.commuteId = ?1")
    public CommuteEntity findByCommuteId(Long commuteId);
    public CommuteEntity findByUserId(Long userId);
    public CommuteEntity findByUserIdAndYmd(Long userId, Date ymd);

}
