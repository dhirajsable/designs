package com.example.apiusage.service;

import com.example.apiusage.entity.User;
import com.example.apiusage.repo.ApiQuotaRepository;
import com.example.apiusage.repo.ApiUsageRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuotaService {

    @Autowired private ApiUsageRepository apiUsageRepository;

    @Autowired private ApiQuotaRepository apiQuotaRepository;

    @Autowired private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired private RedisTemplate<String, Long> redisTemplate;

    public void recordApiCall(User user, String apiName) throws Exception {

        Long companyId = user.getCompany().getId();
        int quotaLimit = user.getCompany().getDailyQuota();
        String redisKey = String.format("api_quota:%d:%s:%s", companyId, apiName, LocalDate.now());
        Long currentCount = redisTemplate.opsForValue().increment(redisKey);

        if (currentCount == 1) {
            // Set expiry for the Redis key to 24 hours
            redisTemplate.expire(redisKey, Duration.ofDays(1));
        }

        if (currentCount > quotaLimit) {
            throw new Exception("API quota exceeded for the day");
        }

        // Publish the API usage event to Kafka
        String message =
                String.format(
                        "{\"companyId\":%d,\"userId\":%d,\"apiName\":\"%s\",\"timestamp\":\"%s\"}",
                        companyId,
                        user.getId(),
                        apiName,
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        kafkaTemplate.send("api-usage-topic", message);

        //        LocalDate today = LocalDate.now();
        //        // Check API quota
        //        ApiQuota apiQuota =
        // apiQuotaRepository.findByCompanyAndApiNameAndCallDate(user.getCompany(), apiName, today)
        //                .orElseGet(() -> {
        //                    ApiQuota quota = new ApiQuota();
        //                    quota.setCompany(user.getCompany());
        //                    quota.setApiName(apiName);
        //                    quota.setDailyQuota(user.getCompany().getDailyQuota());
        //                    quota.setUsedCount(0);
        //                    quota.setCallDate(today);
        //                    quota.setUser(user);
        //                    return apiQuotaRepository.save(quota);
        //                });
        //
        //        if (apiQuota.getUsedCount() >= apiQuota.getDailyQuota()) {
        //            throw new RuntimeException("API quota exceeded for the day");
        //        }
        //
        //        apiQuota.setUsedCount(apiQuota.getUsedCount() + 1);
        //        apiQuotaRepository.save(apiQuota);
        //
        //        // Record API usage
        //        ApiUsage apiUsage = apiUsageRepository.findByUserAndApiNameAndCallDate(user,
        // apiName, today)
        //                .orElse(new ApiUsage());
        //        apiUsage.setUser(user);
        //        apiUsage.setCompany(user.getCompany());
        //        apiUsage.setApiName(apiName);
        //        apiUsage.setUsageCount(apiUsage.getUsageCount() + 1);
        //        apiUsage.setCallDate(today);
        //        apiUsageRepository.save(apiUsage);

        // Publish Kafka event
        //        kafkaTemplate.send("api-usage-topic", String.format("API %s called by user %s",
        // apiName, user.getUsername()));
    }
}
