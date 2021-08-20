package com.inline.sub2.db.repository;

import com.inline.sub2.api.dto.DeptUserDto;
import com.inline.sub2.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByEmailAndRetireDateIsNull(String email);

    public UserEntity findByUserIdAndRetireDateIsNull(Long userId);

    public List<UserEntity> findAllByOfficeIdAndRetireDateIsNull(Long officeId);

    @Query("select ((select count(user.userId) from UserEntity user where user.retireDate is not null and user.officeId=?1) / count(u.userId)) * 100 from UserEntity u where u.officeId = ?1")
    public Double findRetireRate(Long officeId);

    public int countByOfficeIdAndRetireDateIsNull(long OfficeId);

    @Query("select new com.inline.sub2.api.dto.DeptUserDto(d.deptName, count(u.userId)) from UserEntity u join DeptEntity d on u.deptId = d.deptId where u.officeId = :officeId and u.retireDate is null group by u.deptId")
    public List<DeptUserDto> countGroupByDept(long officeId);

    //select avg(DATE_FORMAT(now(), "%Y-%m-%d") - DATE_FORMAT(join_date,"%Y-%m-%d")) from user where office_id=1;
    @Query("select avg(year(now())-year(user.joinDate)) from UserEntity user where user.officeId=?1")
    public Long getJobYear(Long officeId);
}
