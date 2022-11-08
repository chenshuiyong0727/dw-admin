package com.gate.api;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableMethodCache(basePackages = {"com.gate.api", "com.auth"})
@EnableCreateCacheAnnotation
@PropertySource({"classpath:gate.properties"})
@ComponentScan(basePackages = {"com.gate.api", "com.auth"})
public class GateApplication {

  public static void main(String[] args) {
    SpringApplication.run(GateApplication.class, args);
  }

}
