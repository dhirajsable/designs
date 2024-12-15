package com.example.apiusage.rest;

import com.example.apiusage.entity.User;
import com.example.apiusage.repo.UserRepository;
import com.example.apiusage.service.QuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private QuotaService quotaService;

    @Autowired private UserRepository userRepository;

    @PostMapping("/call")
    public String apiCall(@RequestParam Long userId, @RequestParam String apiName)
            throws Exception {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        quotaService.recordApiCall(user, apiName);
        return "API call recorded successfully";
    }
}
