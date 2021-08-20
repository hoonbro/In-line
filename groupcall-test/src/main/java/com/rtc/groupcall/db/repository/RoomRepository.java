package com.rtc.groupcall.db.repository;

import com.rtc.groupcall.db.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    public RoomEntity findByRoomId(Long roomId);

    public List<RoomEntity> findAllByOfficeId(Long officeId);

    public RoomEntity findByOfficeIdAndRoomName(Long officeId, String roomName);
}
