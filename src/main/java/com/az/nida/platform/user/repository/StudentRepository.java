package com.az.nida.platform.user.repository;


import com.az.nida.platform.user.entity.Student;
import com.az.nida.platform.user.enums.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    boolean existsByStudentId(String studentId);

    List<Student> findByGrade(Grade grade);

    List<Student> findByCity(String city);

    List<Student> findBySchool(String school);

    List<Student> findByFullNameContainingIgnoreCaseOrStudentIdContainingIgnoreCase(
            String fullName, String studentId);
}