package el173.auth.bff.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 29/11/2024 - 23:37
 */
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(30));
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
