package com.hf.op.domain.model.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @function 鉴权登录日志(auth_login_log)表Dao
 * @Date 2021/12/09
 */
@Repository
public interface AuthLoginLogRepository extends BaseMapper<AuthLoginLogEntity> {

}
