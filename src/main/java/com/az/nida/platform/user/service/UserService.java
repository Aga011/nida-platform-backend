package com.az.nida.platform.user.service;


import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.dto.TeacherDto;
import com.az.nida.platform.user.dto.UpdateProfileRequest;

import java.util.List;

public interface UserService {

    StudentDto getStudentByUniqueId(String uniqueId);

    StudentDto getStudentById(Long id);

    TeacherDto getTeacherById(Long id);

    TeacherDto getTeacherByEmail(String email);

    ParentDto getParentById(Long id);

    ParentDto getParentByEmail(String email);

    StudentDto updateStudentProfile(Long id, UpdateProfileRequest request);

    TeacherDto updateTeacherProfile(Long id, UpdateProfileRequest request);

    ParentDto updateParentProfile(Long id, UpdateProfileRequest request);

    void deleteUser(Long id);

    StudentDto searchByUniqueId(String uniqueId);

    List<StudentDto> searchStudents(String query);
}