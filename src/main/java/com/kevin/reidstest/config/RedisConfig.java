package com.kevin.reidstest.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @创建人 zhaowenjian
 * @创建时间 2020/12/9
 * @描述
 */
@EnableCaching
@Configuration
public class RedisConfig {

    //    @Bean
//    public RedisCacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))//key的序列化方式
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))//value 的序列化方式
//                .disableCachingNullValues()//不缓存null值
//                .entryTtl(Duration.ofSeconds(60));//默认缓存过期时间
//        //设置一个初始化的缓存名称set集合
//        Set<String> cacheNames = new HashSet<>();
//        cacheNames.add("user");
//        //对每个缓存名称应用不同的配置，自定义过期时间
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put("user", redisCacheConfiguration.entryTtl(Duration.ofSeconds(120)));
//
//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
//                .cacheDefaults(redisCacheConfiguration)
//                .transactionAware()
//                .initialCacheNames(cacheNames)  /*一定要先初始化缓存名再初始化相关配置*/
//                .withInitialCacheConfigurations(configMap)
//                .build();
//        return redisCacheManager;
//    }
//    @Bean
//    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        //key序列化方式;但是如果方法上有Long等非String类型的话，会报类型转换错误；
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//        //默认使用JdkSerializationRedisSerializer序列化方式;会出现乱码，改成StringRedisSerializer
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);
//        redisTemplate.afterPropertiesSet();//参数设置完成后对redisTemplate进行初始化，必须要有
//        return redisTemplate;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> template) {
        return template.opsForHash();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> template) {
        return template.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> template) {
        return template.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> template) {
        return template.opsForZSet();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
