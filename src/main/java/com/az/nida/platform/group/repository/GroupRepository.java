package com.az.nida.platform.group.repository;

import com.az.nida.platform.group.entity.Group;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByTeacherId(Long teacherId);

    List<Group> findByTeacherIdAndSubject(Long teacherId, Subject subject);

    @Query("SELECT g FROM Group g JOIN g.studentIds s WHERE s = :studentId")
    List<Group> findByStudentId(Long studentId);

    @Query("SELECT g FROM Group g JOIN g.studentIds s WHERE s = :studentId AND g.subject = :subject")
    List<Group> findByStudentIdAndSubject(Long studentId, Subject subject);
}