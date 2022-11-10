package com.hf.op;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan({"com.hf.*", "com.hf.op.domain.model.*", "com.hf.common.domain.*"})
@EnableMethodCache(basePackages = {"com.auth.common",
    "com.service.base", "com.*.hf", "com.hf.*"})
@PropertySource({"classpath:${spring.profiles.active}/database.properties"})
@EnableCreateCacheAnnotation
@ComponentScan(basePackages = {"com.auth", "com.hf.*"})
@EnableFeignClients(value = "com")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
public class OpApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpApplication.class, args);
  }
}
