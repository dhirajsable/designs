package com.example.topsharedposts.rest.post;


import com.example.topsharedposts.rest.requests.ShareRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class ShareController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.shared.post.topic.name}")
    private String sharedPostTopicName;

    @PostMapping("/share")
    public ResponseEntity<String> sharePost(@RequestBody ShareRequest shareRequest) {
        String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Validate input
        if (shareRequest.getPostId() == null) {
            return ResponseEntity.badRequest().body("Invalid input: Post ID and Timestamp are required.");
        }

        // Serialize the event as a JSON string
        String event = String.format("{\"postId\":\"%s\",\"timestamp\":\"%s\"}",
                shareRequest.getPostId(), timeStamp);

        // Publish the event to Kafka
        kafkaTemplate.send(sharedPostTopicName, event);

        // Return success response
        return ResponseEntity.ok("Post shared successfully.");
    }
}
