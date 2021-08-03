package com.rtc.groupcall.db.repository;

import com.rtc.groupcall.db.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    public List<RoomEntity> findAllByOfficeId(Long officeId);

    public RoomEntity findByRoomId(Long roomId);
}
