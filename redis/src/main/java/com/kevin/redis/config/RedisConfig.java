package com.kevin.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;



/**
 * @创建人 zhaowenjian
 * @创建时间 2020/12/9
 * @描述
 */
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
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式;但是如果方法上有Long等非String类型的话，会报类型转换错误；
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        //默认使用JdkSerializationRedisSerializer序列化方式;会出现乱码，改成StringRedisSerializer
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.afterPropertiesSet();//参数设置完成后对redisTemplate进行初始化，必须要有
        return redisTemplate;
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
}
