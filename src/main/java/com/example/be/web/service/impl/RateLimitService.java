package com.example.be.web.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimitService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key, int limit, Duration window) {
        String redisKey = "rate_limit:" + key;
        Long count = redisTemplate.opsForValue().increment(redisKey);

        if (count == 1) {
            redisTemplate.expire(redisKey, window);
        }

        return count <= limit;
    }
}

