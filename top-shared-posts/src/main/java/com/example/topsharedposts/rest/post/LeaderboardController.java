package com.example.topsharedposts.rest.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/{interval}/{time}")
    public List<Map<String, Object>> getTopNPosts(
            @PathVariable String interval,
            @PathVariable String time,
            @RequestParam int topN) {

        String redisKey = "top_shared:" + interval + ":" + time;
        Set<ZSetOperations.TypedTuple<Object>> topPosts =
                redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, 0, topN - 1);

        List<Map<String, Object>> result = new ArrayList<>();
        if (topPosts != null) {
            for (ZSetOperations.TypedTuple<Object> post : topPosts) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("postId", post.getValue());
                entry.put("shares", post.getScore());
                result.add(entry);
            }
        }
        return result;
    }
}
