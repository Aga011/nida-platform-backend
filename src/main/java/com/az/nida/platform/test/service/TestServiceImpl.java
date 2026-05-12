package com.az.nida.platform.test.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.kafka.producer.ProgressEventProducer;
import com.az.nida.platform.test.dto.*;
import com.az.nida.platform.test.entity.Option;
import com.az.nida.platform.test.entity.Question;
import com.az.nida.platform.test.entity.TestAnswer;
import com.az.nida.platform.test.entity.TestSession;
import com.az.nida.platform.test.mapper.QuestionMapper;
import com.az.nida.platform.test.mapper.TestSessionMapper;
import com.az.nida.platform.test.repository.QuestionRepository;
import com.az.nida.platform.test.repository.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestSessionRepository testSessionRepository;
    private final QuestionRepository questionRepository;
    private final TestSessionMapper testSessionMapper;
    private final ProgressEventProducer progressEventProducer;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public TestSessionDto startTest(Long studentId, TestStartRequest request) {

        testSessionRepository.findByStudentIdAndSubjectAndTopicIdAndCompletedFalse(
                        studentId, request.subject(), request.topicId())
                .ifPresent(session -> {
                    throw BusinessException.conflict("Bu mövzuda yarımçıq test var. Davam edin.");
                });

        List<Question> questions = questionRepository
                .findRandomBySubjectAndTopicId(request.subject(), request.topicId());

        if (questions.isEmpty()) {
            throw BusinessException.notFound("Bu mövzu üçün sual tapılmadı");
        }


        Collections.shuffle(questions);

        TestSession session = TestSession.builder()
                .studentId(studentId)
                .subject(request.subject())
                .topicId(request.topicId())
                .currentIndex(0)
                .locked(true)
                .completed(false)
                .answers(new ArrayList<>())
                .build();

        TestSession saved = testSessionRepository.save(session);
        log.info("Test başladıldı: studentId={}, subject={}, topicId={}",
                studentId, request.subject(), request.topicId());

        return testSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TestSessionDto submitAnswer(Long sessionId, Long studentId, AnswerRequest request) {
        TestSession session = getValidSession(sessionId, studentId);

        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> BusinessException.notFound("Sual tapılmadı"));

        boolean correct = false;
        if (!request.skipped() && request.selectedOptionId() != null) {
            correct = question.getOptions().stream()
                    .filter(o -> o.getId().equals(request.selectedOptionId()))
                    .findFirst()
                    .map(Option::isCorrect)
                    .orElse(false);
        }

        TestAnswer answer = TestAnswer.builder()
                .testSession(session)
                .questionId(request.questionId())
                .selectedOptionId(request.selectedOptionId())
                .correct(correct)
                .skipped(request.skipped())
                .timeSpent(request.timeSpent())
                .build();

        session.getAnswers().add(answer);
        session.setCurrentIndex(session.getCurrentIndex() + 1);

        TestSession saved = testSessionRepository.save(session);

        // Kafka-ya real-time progress göndər
        progressEventProducer.sendTestProgress(
                String.valueOf(studentId),
                Map.of(
                        "sessionId", sessionId,
                        "questionId", request.questionId(),
                        "correct", correct,
                        "timeSpent", request.timeSpent()
                )
        );

        return testSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TestResultDto finishTest(Long sessionId, Long studentId) {
        TestSession session = getValidSession(sessionId, studentId);

        session.setCompleted(true);
        session.setLocked(false);
        session.setCompletedAt(LocalDateTime.now());
        testSessionRepository.save(session);

        TestResultDto result = buildResult(session);

        // Kafka-ya tamamlanma eventi göndər
        progressEventProducer.sendTestCompleted(
                String.valueOf(studentId),
                Map.of(
                        "sessionId", sessionId,
                        "subject", session.getSubject(),
                        "topicId", session.getTopicId(),
                        "correct", result.correct(),
                        "total", result.total(),
                        "successRate", result.successRate()
                )
        );

        log.info("Test tamamlandı: sessionId={}, successRate={}", sessionId, result.successRate());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public TestSessionDto getSession(Long sessionId, Long studentId) {
        return testSessionMapper.toResponse(getValidSession(sessionId, studentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSessionDto> getStudentSessions(Long studentId) {
        return testSessionRepository.findByStudentId(studentId)
                .stream()
                .map(testSessionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestResultDto getTestResult(Long sessionId) {
        TestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> BusinessException.notFound("Session tapılmadı"));
        return buildResult(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestResultDto> getStudentResults(Long studentId) {
        return testSessionRepository.findByStudentIdAndCompletedTrue(studentId)
                .stream()
                .map(this::buildResult)
                .toList();
    }


    private TestSession getValidSession(Long sessionId, Long studentId) {
        TestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> BusinessException.notFound("Test session tapılmadı"));

        if (!session.getStudentId().equals(studentId)) {
            throw BusinessException.forbidden("Bu session-a giriş icazəniz yoxdur");
        }

        return session;
    }

    private TestResultDto buildResult(TestSession session) {
        List<TestAnswer> answers = session.getAnswers();

        int total   = answers.size();
        int correct = (int) answers.stream().filter(TestAnswer::isCorrect).count();
        int skipped = (int) answers.stream().filter(TestAnswer::isSkipped).count();
        int wrong   = total - correct - skipped;
        long timeSpent = answers.stream().mapToLong(TestAnswer::getTimeSpent).sum();
        double successRate = total > 0 ? (double) correct / total * 100 : 0;

        return new TestResultDto(
                String.valueOf(session.getId()),
                session.getSubject(),
                session.getTopicId(),
                total,
                correct,
                wrong,
                skipped,
                successRate,
                timeSpent,
                session.getCompletedAt()
        );
    }
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getAssessmentQuestions() {
        return questionRepository.findAll()
                .stream()
                .limit(100)
                .map(questionMapper::toResponse)
                .toList();
    }
}