package com.az.nida.platform.permission.repository;

import com.az.nida.platform.permission.entity.Permission;
import com.az.nida.platform.permission.entity.PermissionStatus;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByTeacherId(Long teacherId);

    List<Permission> findByStudentId(Long studentId);

    List<Permission> findByTeacherIdAndStatus(Long teacherId, PermissionStatus status);

    List<Permission> findByStudentIdAndStatus(Long studentId, PermissionStatus status);

    Optional<Permission> findByTeacherIdAndStudentIdAndSubject(
            Long teacherId, Long studentId, Subject subject);

    boolean existsByTeacherIdAndStudentIdAndSubjectAndStatus(
            Long teacherId, Long studentId, Subject subject, PermissionStatus status);

    List<Permission> findByStudentIdAndSubjectAndStatus(
            Long studentId, Subject subject, PermissionStatus status);
}