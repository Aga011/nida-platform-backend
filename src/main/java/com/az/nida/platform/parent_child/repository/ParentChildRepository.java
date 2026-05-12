package com.az.nida.platform.parent_child.repository;

import com.az.nida.platform.parent_child.entity.ParentChildRequest;
import com.az.nida.platform.parent_child.entity.ParentChildStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentChildRepository extends JpaRepository<ParentChildRequest, Long> {

    List<ParentChildRequest> findByParentId(Long parentId);

    List<ParentChildRequest> findByStudentId(Long studentId);

    List<ParentChildRequest> findByParentIdAndStatus(Long parentId, ParentChildStatus status);

    List<ParentChildRequest> findByStudentIdAndStatus(Long studentId, ParentChildStatus status);

    Optional<ParentChildRequest> findByParentIdAndStudentId(Long parentId, Long studentId);

    boolean existsByParentIdAndStudentIdAndStatus(Long parentId, Long studentId, ParentChildStatus status);

    long countByParentIdAndStatus(Long parentId, ParentChildStatus status);
}