package com.auth.common.infrastructure.util.jwt;

import com.auth.common.infrastructure.constant.CommonConstants;
import com.auth.common.infrastructure.util.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

/**
 * JWT助手类,服务于工具类
 *
 * @author 曾仕斌
 * @date 2021-01-12
 */
public class JWTHelper {

  private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

  /**
   * 密钥加密token
   */
  public static String generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
    String compactJws = Jwts.builder()
        .setSubject(jwtInfo.getUserId().toString())
        .claim(CommonConstants.JWT_KEY_USER_EMAIL, jwtInfo.getUserEmail())
        .claim(CommonConstants.JWT_KEY_USER_MOBILE, jwtInfo.getUserMobile())
        .claim(CommonConstants.JWT_KEY_ID_CARD_NO, jwtInfo.getIdCardNo())
        .claim(CommonConstants.JWT_KEY_USER_ACCOUNT, jwtInfo.getUserAccount())
        .claim(CommonConstants.JWT_KEY_USER_REAL_NAME, jwtInfo.getUserRealName())
        .claim(CommonConstants.JWT_ID, jwtInfo.getTokenId())
        .setExpiration(DateTime.now().plusSeconds(expire).toDate())
        .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
        .compact();
    return compactJws;
  }

  /**
   * 公钥解析token
   */
  public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
    Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath))
        .parseClaimsJws(token);
    return claimsJws;
  }

  /**
   * 公钥解析token
   */
  public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
    Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey))
        .parseClaimsJws(token);
    return claimsJws;
  }

  /**
   * 获取token中的用户信息
   */
  public static IJWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
    Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
    Claims body = claimsJws.getBody();
    return new JWTInfo(Long.parseLong(body.getSubject()),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_EMAIL)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_MOBILE)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_ID_CARD_NO)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ACCOUNT)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_REAL_NAME)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_EXP)));
  }

  /**
   * 获取token中的用户信息
   */
  public static IJWTInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
    if (token.startsWith("Bearer")) {
      token = token.replace("Bearer ", "");
    }
    Jws<Claims> claimsJws = parserToken(token, pubKey);
    Claims body = claimsJws.getBody();
    return new JWTInfo(Long.parseLong(body.getSubject()),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_EMAIL)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_MOBILE)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_ID_CARD_NO)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ACCOUNT)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_REAL_NAME)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_EXP)),
        StringHelper.getObjectValue(body.get(CommonConstants.JWT_ID)));
  }
}
