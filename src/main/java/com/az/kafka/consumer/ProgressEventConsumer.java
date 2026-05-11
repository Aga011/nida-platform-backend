package com.az.kafka.consumer;
import com.az.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ProgressEventConsumer {

    @KafkaListener(
            topics = KafkaConfig.TOPIC_TEST_PROGRESS,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTestProgress(Map<String, Object> progressData) {
        log.info("Test progress eventi alındı: {}", progressData);
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_TEST_COMPLETED,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTestCompleted(Map<String, Object> resultData) {
        log.info("Test completed eventi alındı: {}", resultData);
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_ANALYTICS_UPDATE,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeAnalyticsUpdate(Map<String, Object> analyticsData) {
        log.info("Analytics update eventi alındı: {}", analyticsData);
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_STREAK_UPDATE,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeStreakUpdate(Map<String, Object> streakData) {
        log.info("Streak update eventi alındı: {}", streakData);
    }
}