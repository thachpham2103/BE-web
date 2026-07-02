package com.example.be.web.repository;

import com.example.be.web.doman.entity.ActivityLog;
import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    Page<ActivityLog> findByActor_Id(Long actorId, Pageable pageable);

    Page<ActivityLog> findByAction(ActivityAction action, Pageable pageable);

    Page<ActivityLog> findByTargetTypeAndTargetId(TargetType targetType, Long targetId, Pageable pageable);
}
