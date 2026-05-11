package com.az.user.service;


import com.az.user.dto.ParentDto;
import com.az.user.dto.StudentDto;
import com.az.user.dto.TeacherDto;
import com.az.user.dto.UpdateProfileRequest;

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
}