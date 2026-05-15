package com.az.nida.platform.intervention.repository;

import com.az.nida.platform.intervention.entity.Intervention;
import com.az.nida.platform.intervention.entity.InterventionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    List<Intervention> findByTeacherIdOrderByCreatedAtDesc(Long teacherId);

    List<Intervention> findByTeacherIdAndStatusOrderByCreatedAtDesc(
            Long teacherId, InterventionStatus status);

    List<Intervention> findByGroupIdOrderByCreatedAtDesc(Long groupId);
}