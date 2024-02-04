//package com.rupesh.blog.config;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class CacheEvictionOnStartup implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final CacheManager cacheManager;
//
//    public CacheEvictionOnStartup(CacheManager cacheManager) {
//        this.cacheManager = cacheManager;
//        this.cacheManager.getCacheNames().stream().forEach(name -> System.out.println("cache name : "+name));
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//    	log.warn("All previous cache destroyed");
//        evictCache();
//    }
//
//    @CacheEvict(value = "blogCache", allEntries = true)
//    public void evictCache() {
//    	log.warn("Data removed form cache....");
//    }
//    
//}
