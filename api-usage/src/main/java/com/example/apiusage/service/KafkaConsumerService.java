package com.example.apiusage.service;

import com.example.apiusage.repo.ApiUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired private ApiUsageRepository apiUsageRepository;

    //    @KafkaListener(topics = "api-usage-topic", groupId = "usage-group")
    //    public void consume(String message) {
    //        // Parse the JSON message
    //        ApiUsageEvent event = parseMessage(message);
    //
    //        // Save API usage details to the database
    //        ApiUsage apiUsage = new ApiUsage();
    //        apiUsage.setUserId(event.getUserId());
    //        apiUsage.setCompanyId(event.getCompanyId());
    //        apiUsage.setApiName(event.getApiName());
    //        apiUsage.setUsageCount(1); // Increment usage count
    //        apiUsage.setCallDate(event.getTimestamp());
    //
    //        apiUsageRepository.save(apiUsage);
    //    }
    //
    //    private ApiUsageEvent parseMessage(String message) {
    //        // Implement a JSON parser (e.g., Jackson or Gson) to map message to ApiUsageEvent
    //    }
}
