package com.hf.op.domain.model.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 曾仕斌
 * @function 登录(base_login)表Dao
 * @date 2021/2/5
 */
@Repository
public interface AuthLoginRepository extends BaseMapper<AuthUnifiedUserEntity> {

}
