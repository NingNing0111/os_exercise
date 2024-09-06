package me.pgthinker.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import me.pgthinker.constant.CacheConstant;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Project: me.pgthinker.config
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/6 09:51
 * @Description:
 */
@Configuration
@EnableCaching
public class CacheManagerConfig {
    @Bean(name = CacheConstant.LOCAL_CACHE)
    public CacheManager onlyLocalCacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    // 自定义缓存key生成器
    @Bean(CacheConstant.KEY_GENERATOR)
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> target.getClass().getSimpleName() + "_" + method.getName() + "_" + StringUtils.arrayToDelimitedString(params,"_");
    }
}
