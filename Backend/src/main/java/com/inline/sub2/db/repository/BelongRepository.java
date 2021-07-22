package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.BelongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelongRepository extends JpaRepository<BelongEntity, String> {
}
