package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByEmailAndRetireDateIsNull(String email);

    public UserEntity findByUserIdAndRetireDateIsNull(Long userId);

    public List<UserEntity> findAllByOfficeIdAndRetireDateIsNull(Long officeId);

    @Query("select ((select count(user.userId) from UserEntity user where user.retireDate is not null and user.officeId=?1) / 2) * 100 from UserEntity u where u.officeId = ?1")
    public Double findRetireRate(Long officeId);
}
