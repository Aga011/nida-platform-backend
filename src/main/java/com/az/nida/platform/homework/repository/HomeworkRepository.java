package com.az.nida.platform.homework.repository;

import com.az.nida.platform.homework.entity.Homework;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findByTeacherId(Long teacherId);

    List<Homework> findByGroupId(Long groupId);

    List<Homework> findByTeacherIdAndSubject(Long teacherId, Subject subject);

    List<Homework> findByGroupIdAndSubject(Long groupId, Subject subject);
}