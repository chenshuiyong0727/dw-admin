package com.hf.common.domain.model.ds;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 曾仕斌
 * @function mysql插件类, 如支持乐观锁、分页
 * @date 2021/2/26
 */
@Configuration
@Slf4j
public class MybatisPlusConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driverName;

  private final String mysqlDriver = "com.mysql.cj.jdbc.Driver";

  private final String oracleDriver = "oracle.jdbc.OracleDriver";

  /**
   * 乐观锁、分页等插件 乐观锁插件,仅支持 updateById(id) 与 update(entity, wrapper) 方法
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    if (StringUtilLocal.isNotEmpty(driverName) && driverName.trim().equals(oracleDriver)) {
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE_12C));
    } else if (StringUtilLocal.isNotEmpty(driverName) && driverName.trim().equals(mysqlDriver)) {
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    } else {
      //DEFAULT DB TYPE IS MYSQL
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    }
    return interceptor;
  }
}
