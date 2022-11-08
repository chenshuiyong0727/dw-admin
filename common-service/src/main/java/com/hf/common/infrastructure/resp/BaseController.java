package com.hf.common.infrastructure.resp;

import com.hf.common.infrastructure.global.config.ConfigConst;
import com.hf.common.infrastructure.util.EncryptUtils;
import org.springframework.util.Assert;

/**
 * @author wpq
 * @function 全局控制器
 * @Date 2021/12/08
 */
public class BaseController {

  protected String decryptParam(String enc) {
    Assert.hasText(enc, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    return EncryptUtils.decryptAES(ConfigConst.ServerSecretAesKey, enc);
  }

  protected String encryptParam(String in) {
    Assert.hasText(in, "encryption body must not be null!");
    return EncryptUtils.encryptAES(ConfigConst.ServerSecretAesKey, in);
  }

}
