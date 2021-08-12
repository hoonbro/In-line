package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    public RoomEntity findByOfficeIdAndRoomName(Long officeId, String roomName);
}
