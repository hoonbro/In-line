package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.belongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface belongRepository extends JpaRepository<belongEntity, String> {
}
