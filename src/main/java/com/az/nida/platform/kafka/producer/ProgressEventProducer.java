package com.az.nida.platform.kafka.producer;

import com.az.nida.platform.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProgressEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTestProgress(String studentId, Map<String, Object> progressData) {
        kafkaTemplate.send(KafkaConfig.TOPIC_TEST_PROGRESS, studentId, progressData);
        log.info("Test progress eventi göndərildi: studentId={}", studentId);
    }

    public void sendTestCompleted(String studentId, Map<String, Object> resultData) {
        kafkaTemplate.send(KafkaConfig.TOPIC_TEST_COMPLETED, studentId, resultData);
        log.info("Test completed eventi göndərildi: studentId={}", studentId);
    }

    public void sendAnalyticsUpdate(String studentId, Map<String, Object> analyticsData) {
        kafkaTemplate.send(KafkaConfig.TOPIC_ANALYTICS_UPDATE, studentId, analyticsData);
        log.info("Analytics update eventi göndərildi: studentId={}", studentId);
    }

    public void sendStreakUpdate(String studentId, int streakDays) {
        Map<String, Object> data = Map.of("studentId", studentId, "streakDays", streakDays);
        kafkaTemplate.send(KafkaConfig.TOPIC_STREAK_UPDATE, studentId, data);
        log.info("Streak update eventi göndərildi: studentId={}, streak={}", studentId, streakDays);
    }
}