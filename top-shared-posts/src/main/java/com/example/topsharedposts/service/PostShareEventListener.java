package com.example.topsharedposts.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PostShareEventListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String HOURLY_KEY_PREFIX = "top_shared:hour:";
    private static final String DAILY_KEY_PREFIX = "top_shared:day:";

    @KafkaListener(
            topics = "${kafka.shared.post.topic.name}",
            groupId = "${kafka.consumer.group-id}"
    )
    public void handleShareEvent(String message) {
        JSONObject event = new JSONObject(message);
        String postId = event.getString("postId");
        LocalDateTime timestamp = LocalDateTime.parse(event.getString("timestamp"));

        // Update Redis counters
        String hourlyKey = HOURLY_KEY_PREFIX + timestamp.truncatedTo(ChronoUnit.HOURS);
        String dailyKey = DAILY_KEY_PREFIX + timestamp.toLocalDate();

        redisTemplate.opsForZSet().incrementScore(hourlyKey, postId, 1);
        redisTemplate.opsForZSet().incrementScore(dailyKey, postId, 1);
    }
}
