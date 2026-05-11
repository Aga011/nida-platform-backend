package com.az.user.service;

import com.az.common.exception.BusinessException;
import com.az.user.dto.ParentDto;
import com.az.user.dto.StudentDto;
import com.az.user.dto.TeacherDto;
import com.az.user.dto.UpdateProfileRequest;
import com.az.user.entity.Parent;
import com.az.user.entity.Student;
import com.az.user.entity.Teacher;
import com.az.user.mapper.ParentMapper;
import com.az.user.mapper.StudentMapper;
import com.az.user.mapper.TeacherMapper;
import com.az.user.repository.ParentRepository;
import com.az.user.repository.StudentRepository;
import com.az.user.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final ParentMapper parentMapper;

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentByUniqueId(String uniqueId) {
        Student student = studentRepository.findByStudentId(uniqueId)
                .orElseThrow(() -> BusinessException.notFound("Şagird tapılmadı: " + uniqueId));
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Şagird tapılmadı"));
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Müəllim tapılmadı"));
        return teacherMapper.toResponse(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto getTeacherByEmail(String email) {
        Teacher teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.notFound("Müəllim tapılmadı"));
        return teacherMapper.toResponse(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public ParentDto getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Valideyn tapılmadı"));
        return parentMapper.toResponse(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public ParentDto getParentByEmail(String email) {
        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.notFound("Valideyn tapılmadı"));
        return parentMapper.toResponse(parent);
    }

    @Override
    @Transactional
    public StudentDto updateStudentProfile(Long id, UpdateProfileRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Şagird tapılmadı"));
        if (request.fullName() != null) student.setFullName(request.fullName());
        if (request.avatarUrl() != null) student.setAvatarUrl(request.avatarUrl());
        if (request.city()     != null) student.setCity(request.city());
        if (request.school()   != null) student.setSchool(request.school());
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    @Transactional
    public TeacherDto updateTeacherProfile(Long id, UpdateProfileRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Müəllim tapılmadı"));
        if (request.fullName() != null) teacher.setFullName(request.fullName());
        if (request.avatarUrl() != null) teacher.setAvatarUrl(request.avatarUrl());
        if (request.city()     != null) teacher.setCity(request.city());
        if (request.school()   != null) teacher.setSchool(request.school());
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public ParentDto updateParentProfile(Long id, UpdateProfileRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Valideyn tapılmadı"));
        if (request.fullName() != null) parent.setFullName(request.fullName());
        if (request.avatarUrl() != null) parent.setAvatarUrl(request.avatarUrl());
        return parentMapper.toResponse(parentRepository.save(parent));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!studentRepository.existsById(id) &&
                !teacherRepository.existsById(id) &&
                !parentRepository.existsById(id)) {
            throw BusinessException.notFound("İstifadəçi tapılmadı");
        }
        studentRepository.deleteById(id);
    }
}