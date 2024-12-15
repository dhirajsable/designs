package com.example.topsharedposts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
public class AggregationJob {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 * * * ?") // Run every hour
    public void aggregateHourlyToDaily() {
        LocalDateTime now = LocalDateTime.now().minusHours(1);
        String hourlyKey = "top_shared:hour:" + now.truncatedTo(ChronoUnit.HOURS);
        String dailyKey = "top_shared:day:" + now.toLocalDate();

        Set<ZSetOperations.TypedTuple<Object>> hourlyData =
                redisTemplate.opsForZSet().rangeWithScores(hourlyKey, 0, -1);

        if (hourlyData != null) {
            for (ZSetOperations.TypedTuple<Object> entry : hourlyData) {
                redisTemplate.opsForZSet().incrementScore(dailyKey, entry.getValue(), entry.getScore());
            }
        }

        // Optionally: Delete old hourly key
        redisTemplate.delete(hourlyKey);
    }
}
