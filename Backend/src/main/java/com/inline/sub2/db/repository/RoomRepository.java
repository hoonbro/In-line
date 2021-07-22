package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

}
