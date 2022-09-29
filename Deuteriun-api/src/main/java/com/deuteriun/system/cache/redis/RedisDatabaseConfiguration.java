package com.deuteriun.system.cache.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * @ClassName RedisDatabaseConfiguration
 * @Description TODO
 * @Author gavin
 * @Date 15/6/2022 16:23
 * @Version 1.0
 **/
@Configuration
public class RedisDatabaseConfiguration {

    @Value("${spring.redis.host:127.0.0.1}")
    public java.lang.String host;
    @Value("${spring.redis.port:6379}")
    public Integer port;
    @Value("${spring.redis.password}")
    private java.lang.String password;
    @Value("${spring.redis.timeout:2000}")
    public Integer timeout;
    @Value("${spring.redis.lettuce.pool.max-idle:16}")
    public Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle:5}")
    public Integer minIdle = 5;
    @Value("${spring.redis.lettuce.pool.max-total:16}")
    public Integer maxTotal = 30;

    @Bean
    public RedisTemplate<String, String> redisTemplate1() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(1);
        if (!ObjectUtils.isEmpty(password)) {
            configuration.setPassword(RedisPassword.of(password));
        }
        return createRedisTemplate(creatFactory(configuration));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate2() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(2);
        if (!ObjectUtils.isEmpty(password)) {
            configuration.setPassword(RedisPassword.of(password));
        }
        return createRedisTemplate(creatFactory(configuration));
    }

    private RedisTemplate<String, String> getSerializerRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    private GenericObjectPoolConfig<Object> getGenericObjectPoolConfig() {
        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWait(Duration.ofSeconds(timeout));
        return genericObjectPoolConfig;
    }

    private LettuceConnectionFactory creatFactory(RedisStandaloneConfiguration configuration) {
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(getGenericObjectPoolConfig());
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    private RedisTemplate<String, String> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = getSerializerRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
