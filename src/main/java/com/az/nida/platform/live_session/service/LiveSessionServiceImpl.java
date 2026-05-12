package com.az.nida.platform.live_session.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.live_session.dto.CreateLiveSessionRequest;
import com.az.nida.platform.live_session.dto.LiveSessionDto;
import com.az.nida.platform.live_session.entity.LiveSession;
import com.az.nida.platform.live_session.entity.LiveSessionStatus;
import com.az.nida.platform.live_session.mapper.LiveSessionMapper;
import com.az.nida.platform.live_session.repository.LiveSessionRepository;
import com.az.nida.platform.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveSessionServiceImpl implements LiveSessionService {

    private final LiveSessionRepository liveSessionRepository;
    private final LiveSessionMapper     liveSessionMapper;
    private final NotificationService   notificationService;

    @Override
    @Transactional
    public LiveSessionDto createSession(Long teacherId, CreateLiveSessionRequest request) {
        LiveSession session = LiveSession.builder()
                .teacherId(teacherId)
                .groupId(request.groupId())
                .title(request.title())
                .meetingUrl(request.meetingUrl())
                .status(LiveSessionStatus.SCHEDULED)
                .build();

        LiveSession saved = liveSessionRepository.save(session);
        log.info("Canlı dərs yaradıldı: teacherId={}, title={}", teacherId, request.title());
        return liveSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LiveSessionDto startSession(Long sessionId, Long teacherId) {
        LiveSession session = getTeacherSession(sessionId, teacherId);

        if (session.getStatus() != LiveSessionStatus.SCHEDULED) {
            throw BusinessException.badRequest("Yalnız planlaşdırılmış dərslər başladıla bilər");
        }

        session.setStatus(LiveSessionStatus.ACTIVE);
        session.setStartedAt(LocalDateTime.now());
        LiveSession saved = liveSessionRepository.save(session);

        log.info("Canlı dərs başladıldı: sessionId={}", sessionId);
        return liveSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LiveSessionDto endSession(Long sessionId, Long teacherId) {
        LiveSession session = getTeacherSession(sessionId, teacherId);

        if (session.getStatus() != LiveSessionStatus.ACTIVE) {
            throw BusinessException.badRequest("Yalnız aktiv dərslər bitirилə bilər");
        }

        session.setStatus(LiveSessionStatus.ENDED);
        session.setEndedAt(LocalDateTime.now());
        LiveSession saved = liveSessionRepository.save(session);

        log.info("Canlı dərs bitdi: sessionId={}", sessionId);
        return liveSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LiveSessionDto joinSession(Long sessionId, Long studentId) {
        LiveSession session = liveSessionRepository.findById(sessionId)
                .orElseThrow(() -> BusinessException.notFound("Canlı dərs tapılmadı"));

        if (session.getStatus() != LiveSessionStatus.ACTIVE) {
            throw BusinessException.badRequest("Dərs aktiv deyil");
        }

        if (!session.getParticipantIds().contains(studentId)) {
            session.getParticipantIds().add(studentId);
            liveSessionRepository.save(session);
            log.info("Şagird canlı dərsə qoşuldu: sessionId={}, studentId={}", sessionId, studentId);
        }

        return liveSessionMapper.toResponse(session);
    }

    @Override
    @Transactional
    public LiveSessionDto leaveSession(Long sessionId, Long studentId) {
        LiveSession session = liveSessionRepository.findById(sessionId)
                .orElseThrow(() -> BusinessException.notFound("Canlı dərs tapılmadı"));

        session.getParticipantIds().remove(studentId);
        LiveSession saved = liveSessionRepository.save(session);

        log.info("Şagird canlı dərsdən ayrıldı: sessionId={}, studentId={}", sessionId, studentId);
        return liveSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiveSessionDto> getTeacherSessions(Long teacherId) {
        return liveSessionRepository.findByTeacherId(teacherId)
                .stream()
                .map(liveSessionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiveSessionDto> getGroupSessions(Long groupId) {
        return liveSessionRepository.findByGroupId(groupId)
                .stream()
                .map(liveSessionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiveSessionDto> getStudentSessions(Long studentId) {
        return liveSessionRepository.findByStudentId(studentId)
                .stream()
                .map(liveSessionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LiveSessionDto getSessionById(Long sessionId) {
        return liveSessionMapper.toResponse(
                liveSessionRepository.findById(sessionId)
                        .orElseThrow(() -> BusinessException.notFound("Canlı dərs tapılmadı")));
    }


    private LiveSession getTeacherSession(Long sessionId, Long teacherId) {
        LiveSession session = liveSessionRepository.findById(sessionId)
                .orElseThrow(() -> BusinessException.notFound("Canlı dərs tapılmadı"));

        if (!session.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu dərsə giriş icazəniz yoxdur");
        }

        return session;
    }
}