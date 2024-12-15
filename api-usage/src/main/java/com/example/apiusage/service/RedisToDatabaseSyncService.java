package com.example.apiusage.service;

import com.example.apiusage.entity.ApiQuota;
import com.example.apiusage.entity.Company;
import com.example.apiusage.repo.ApiQuotaRepository;
import com.example.apiusage.repo.CompanyRepository;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RedisToDatabaseSyncService {

    @Autowired private RedisTemplate<String, Long> redisTemplate;

    @Autowired private ApiQuotaRepository apiQuotaRepository;

    @Autowired private CompanyRepository companyRepository;

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void syncRedisToDatabase() {
        Set<String> keys = redisTemplate.keys("api_quota:*");
        if (keys == null) return;

        for (String key : keys) {
            String[] parts = key.split(":");
            Long companyId = Long.valueOf(parts[1]);
            String apiName = parts[2];
            LocalDate date = LocalDate.parse(parts[3]);

            Company company = companyRepository.findById(companyId).orElse(null);

            Long usedCount = redisTemplate.opsForValue().get(key);
            if (usedCount == null) continue;

            // Update or insert quota in the database
            apiQuotaRepository
                    .findByCompanyAndApiNameAndCallDate(company, apiName, date)
                    .ifPresentOrElse(
                            apiQuota -> {
                                apiQuota.setUsedCount(usedCount.intValue());
                                apiQuotaRepository.save(apiQuota);
                            },
                            () -> {
                                ApiQuota newQuota = new ApiQuota();
                                newQuota.setCompany(
                                        companyRepository.findById(companyId).orElseThrow());
                                newQuota.setApiName(apiName);
                                newQuota.setDailyQuota(
                                        0); // Update with the correct limit if needed
                                newQuota.setUsedCount(usedCount.intValue());
                                newQuota.setCallDate(date);
                                apiQuotaRepository.save(newQuota);
                            });
        }
    }
}
