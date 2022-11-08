package com.hf.common.infrastructure.global.config;

/**
 * @author wpq
 * @function 全局配置
 * @Date 2021/12/08
 */
public class ConfigConst {

  /**
   * 是否调试模式
   */
  public static final String Debug = "debug";

  /**
   * swagger-ui开关
   */
  public static final String SwaggerShow = "swagger.show";

  /**
   * 签名校验-开关
   */
  public static final String ServerSigSwitch = "server.sig.switch";

  /**
   * 签名校验-MD5盐值
   */
  public static final String ServerSigSaltKey = "server.sig.salt.key";

  /**
   * 加密-AES密钥
   */
  public static final String ServerSecretAesKey = "58d10555a17a4039";

  /**
   * 全局属性 - 短信后缀
   */
  public static final String GlobalPropSmsSuffixText = "global.prop.smsSuffixText";

  /**
   * 全局属性 - 中心平台标识
   */
  public static final String GlobalPropCentral = "global.prop.central";

  /**
   * 全局属性 - 全局环境唯一编号
   */
  public static final String GlobalPropEnvId = "global.prop.envId";

}
