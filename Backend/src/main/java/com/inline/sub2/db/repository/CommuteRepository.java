package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.CommuteEntity;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import java.util.List;

@Repository
public interface CommuteRepository extends JpaRepository<CommuteEntity,String> {

    @Query("SELECT u FROM CommuteEntity u WHERE u.commuteId = ?1")
    public CommuteEntity findByCommuteId(Long commuteId);
    public CommuteEntity findByUserId(Long userId);
    public CommuteEntity findByUserIdAndYmd(Long userId, Date ymd);

    @Query("SELECT c FROM CommuteEntity c WHERE c.officeId = ?1 ORDER BY c.ymd")
    public List<CommuteEntity> findAllByOfficeId(Long officeId);

    @Query("SELECT DISTINCT FUNCTION('DATE_FORMAT', c.ymd, '%Y-%m') FROM CommuteEntity c WHERE c.officeId = ?1 ORDER BY c.ymd")
    public List<String> findByYmd(Long officeId);
}
