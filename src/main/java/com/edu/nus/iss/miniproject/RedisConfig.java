package com.edu.nus.iss.miniproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.edu.nus.iss.miniproject.model.User;
import com.edu.nus.iss.miniproject.model.Word;

@Configuration
public class RedisConfig {

    // Railway: REDIS_HOST
    @Value("${redis.host}")
    private String redisHost;

    // Railway: REDIS_PORT
    @Value("${redis.port}")
    private Integer redisPort;

    // Railway: REDIS_USERNAME
    @Value("${redis.username}")
    private String redisUsername;

    // Railway: REDIS_PASSWORD
    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.databaseUser}")
    private Integer redisUserDatabase;

    @Value("${redis.databaseWord}")
    private Integer redisWordDatabase;
    
    @Primary
    private JedisConnectionFactory createJedisConnectionFactory(int database) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (redisUsername != null && !redisUsername.isEmpty()) {
            config.setUsername(redisUsername);
        }
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
        config.setDatabase(database);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build();
        return new JedisConnectionFactory(config, jedisClientConfiguration);
    }

    private RedisTemplate<String, String> createRedisStringTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        return template;
    }

    private RedisTemplate<String, String> createRedisObjectTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        // template.setHashKeySerializer(stringRedisSerializer);
        // template.setHashValueSerializer(stringRedisSerializer);
        return template;
    }

    @Bean
    public JedisConnectionFactory jedisUserConnFactory() {
        return createJedisConnectionFactory(redisUserDatabase);
    }

    @Bean
    public JedisConnectionFactory jedisWordConnFactory() {
        return createJedisConnectionFactory(redisWordDatabase);
    }

    // Default RedisTemplate (could be one of the existing connection factories)
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        return createRedisStringTemplate(jedisUserConnFactory());
    }

    @Bean("userRedisTemplate")
    public RedisTemplate<String, String> userRedisTemplate() {
        return createRedisStringTemplate(jedisUserConnFactory());
    }

    @Bean("wordRedisTemplate")
    public RedisTemplate<String, String> wordRedisTemplate() {
        return createRedisObjectTemplate(jedisWordConnFactory());
    }
}