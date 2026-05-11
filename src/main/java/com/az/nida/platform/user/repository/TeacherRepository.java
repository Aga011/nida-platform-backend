package com.az.nida.platform.user.repository;


import com.az.nida.platform.user.entity.Teacher;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Teacher> findByApprovedTrue();

    List<Teacher> findByApprovedFalse();

    @Query("SELECT t FROM Teacher t JOIN t.subjects s WHERE s = :subject")
    List<Teacher> findBySubject(Subject subject);
}