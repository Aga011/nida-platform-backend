package com.az.nida.platform.auth.service;


import com.az.nida.platform.auth.dto.AuthResponse;
import com.az.nida.platform.auth.dto.LoginRequest;
import com.az.nida.platform.auth.dto.RegisterRequest;
import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.common.util.UniqueIdGenerator;
import com.az.nida.platform.notification.rabbit.EmailPublisher;
import com.az.nida.platform.user.entity.Parent;
import com.az.nida.platform.user.entity.Student;
import com.az.nida.platform.user.entity.Teacher;
import com.az.nida.platform.user.entity.User;
import com.az.nida.platform.user.enums.Role;
import com.az.nida.platform.user.repository.ParentRepository;
import com.az.nida.platform.user.repository.StudentRepository;
import com.az.nida.platform.user.repository.TeacherRepository;
import com.az.nida.platform.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final JwtService        jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UniqueIdGenerator uniqueIdGenerator;
    private final EmailPublisher emailPublisher;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw BusinessException.conflict("Bu email artıq qeydiyyatdan keçib");
        }

        User user = switch (request.role()) {
            case STUDENT -> buildStudent(request);
            case TEACHER -> buildTeacher(request);
            case PARENT  -> buildParent(request);
            default      -> throw BusinessException.badRequest("Yanlış rol");
        };

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUniqueId(uniqueIdGenerator.generate());
        String verificationCode = String.format("%06d", new java.util.Random().nextInt(999999));
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationExpiry(LocalDateTime.now().plusHours(24));
        user.setEmailVerificationCode(verificationCode);
        user.setEmailVerificationCodeExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        emailPublisher.sendVerificationEmail(
                user.getEmail(),
                user.getFullName(),
                verificationCode
        );

        log.info("Yeni istifadəçi qeydiyyatdan keçdi: {} - {}", user.getEmail(), user.getRole());

        String token        = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return buildAuthResponse(user, token, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Email və ya şifrə yanlışdır"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Email və ya şifrə yanlışdır");
        }

        if (!user.isEmailVerified()) {
            throw BusinessException.badRequest("Email təsdiqlənməyib. Zəhmət olmasa emailinizi yoxlayın");
        }

        if (user.getRole() == Role.TEACHER) {
            Teacher teacher = teacherRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> BusinessException.notFound("Müəllim tapılmadı"));
            if (!teacher.isApproved()) {
                throw BusinessException.forbidden("Hesabınız hələ təsdiqlənməyib");
            }
        }

        String token        = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        log.info("İstifadəçi daxil oldu: {}", user.getEmail());

        return buildAuthResponse(user, token, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        User user = userRepository.findAll().stream()
                .filter(u -> refreshToken.equals(u.getRefreshToken()))
                .findFirst()
                .orElseThrow(() -> BusinessException.unauthorized("Refresh token etibarsızdır"));

        if (jwtService.isTokenExpired(refreshToken)) {
            throw BusinessException.unauthorized("Refresh token müddəti bitib");
        }

        String newToken        = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return buildAuthResponse(user, newToken, newRefreshToken);
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findAll().stream()
                .filter(u -> token.equals(u.getEmailVerificationToken()))
                .findFirst()
                .orElseThrow(() -> BusinessException.badRequest("Token etibarsızdır"));

        if (user.getEmailVerificationExpiry().isBefore(LocalDateTime.now())) {
            throw BusinessException.badRequest("Token müddəti bitib. Yenidən göndərin");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiry(null);
        userRepository.save(user);

        log.info("Email təsdiqləndi: {}", user.getEmail());
    }

    @Override
    @Transactional
    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.notFound("İstifadəçi tapılmadı"));

        if (user.isEmailVerified()) {
            throw BusinessException.badRequest("Email artıq təsdiqlənib");
        }

        String verificationCode = String.format("%06d", new java.util.Random().nextInt(999999));
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationExpiry(LocalDateTime.now().plusHours(24));
        user.setEmailVerificationCode(verificationCode);
        user.setEmailVerificationCodeExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        emailPublisher.sendVerificationEmail(user.getEmail(), user.getFullName(), verificationCode);

        log.info("Verification email yenidən göndərildi: {}", email);
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.notFound("İstifadəçi tapılmadı"));

        user.setPasswordResetToken(UUID.randomUUID().toString());
        user.setPasswordResetExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        emailPublisher.sendPasswordResetEmail(user.getEmail(), user.getFullName(), user.getPasswordResetToken());

        log.info("Şifrə sıfırlama emaili göndərildi: {}", email);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findAll().stream()
                .filter(u -> token.equals(u.getPasswordResetToken()))
                .findFirst()
                .orElseThrow(() -> BusinessException.badRequest("Token etibarsızdır"));

        if (user.getPasswordResetExpiry().isBefore(LocalDateTime.now())) {
            throw BusinessException.badRequest("Token müddəti bitib");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiry(null);
        userRepository.save(user);

        log.info("Şifrə sıfırlandı: {}", user.getEmail());
    }

    @Override
    @Transactional
    public void verifyCode(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.notFound("İstifadəçi tapılmadı"));

        if (user.isEmailVerified()) {
            throw BusinessException.badRequest("Email artıq təsdiqlənib");
        }

        if (!code.equals(user.getEmailVerificationCode())) {
            throw BusinessException.badRequest("Kod yanlışdır");
        }

        if (user.getEmailVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw BusinessException.badRequest("Kodun müddəti bitib. Yenidən göndərin");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationCode(null);
        user.setEmailVerificationCodeExpiry(null);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiry(null);
        userRepository.save(user);

        log.info("Email kod ilə təsdiqləndi: {}", email);
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        userRepository.findAll().stream()
                .filter(u -> refreshToken.equals(u.getRefreshToken()))
                .findFirst()
                .ifPresent(u -> {
                    u.setRefreshToken(null);
                    userRepository.save(u);
                });
    }

    private Teacher buildTeacher(RegisterRequest request) {
        return Teacher.builder()
                .fullName(request.fullName())
                .email(request.email().toLowerCase().trim())
                .role(Role.TEACHER)
                .city(request.city())
                .school(request.school())
                .subjects(request.subjects())
                .approved(false)
                .build();
    }

    private Parent buildParent(RegisterRequest request) {
        return Parent.builder()
                .fullName(request.fullName())
                .email(request.email().toLowerCase().trim())
                .role(Role.PARENT)
                .build();
    }

    private Student buildStudent(RegisterRequest request) {
        return Student.builder()
                .fullName(request.fullName())
                .email(request.email().toLowerCase().trim())
                .role(Role.STUDENT)
                .grade(request.grade())
                .foreignLanguage(request.foreignLanguage())
                .city(request.city())
                .school(request.school())
                .birthDate(request.birthDate())
                .build();
    }

    private AuthResponse buildAuthResponse(User user, String token, String refreshToken) {
        String gradeLevel      = null;
        String specialtyGroup  = null;

        if (user instanceof Student student) {
            gradeLevel     = student.getGrade() != null ? student.getGrade().name() : null;
            specialtyGroup = student.getSpecialtyGroup();
        }

        return new AuthResponse(
                user.getId(),
                token,
                refreshToken,
                user.getUniqueId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.isEmailVerified(),
                gradeLevel,
                specialtyGroup
        );
    }
}