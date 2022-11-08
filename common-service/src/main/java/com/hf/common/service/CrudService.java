package com.hf.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hf.common.domain.UserInfo;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.entity.BaseEntity;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.UserUtil;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author 曾仕斌
 * @function 业务类基础类, ID生成采用雪花算法, 机器id和数据中心Id不能大于31
 * @date 2021/8/4
 */
@Slf4j
public abstract class CrudService {

  protected UserUtil userUtil;

  /**
   * 基础时间截 (2014-01-01),用于计算当前服务器时间与该时间的时间差
   */
  private final long basicTime = 1388505600000L;

  /**
   * 机器id所占的位数
   */
  private final static long workerIdBits = 5L;

  /**
   * 数据标识id所占的位数
   */
  private final long datacenterIdBits = 5L;

  /**
   * 序列在id中占的位数
   */
  private final long sequenceBits = 12L;

  /**
   * 机器ID向左移12位
   */
  private final long workerIdShift = sequenceBits;

  /**
   * 数据标识id向左移17位(12+5)
   */
  private final long dataCenterIdShift = sequenceBits + workerIdBits;

  /**
   * 时间截向左移22位(5+5+12)
   */
  private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

  /**
   * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
   */
  private final long sequenceMask = -1L ^ (-1L << sequenceBits);

  /**
   * 工作机器ID(0~31)
   */
  @Value("${workerId}")
  private long workerId;

  /**
   * 数据中心ID(0~31)
   */
  @Value("${dataCenterId}")
  private long dataCenterId = 1;

  /**
   * 毫秒内序列(0~4095)
   */
  private long sequence = 0L;

  /**
   * 上次生成ID的时间截
   */
  private long lastTimestamp = -1L;

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  /**
   * 阻塞到下一个毫秒，直到获得新的时间戳
   *
   * @param lastTimestamp 上次生成ID的时间截
   * @return 当前时间戳
   */
  protected long tilNextMillis(long lastTimestamp) {
    long timestamp = System.currentTimeMillis();
    while (timestamp <= lastTimestamp) {
      timestamp = System.currentTimeMillis();
    }
    return timestamp;
  }

  /**
   * 获得下一个ID (该方法是线程安全的)
   *
   * @return SnowflakeId
   */
  protected synchronized long createId() {
    long timestamp = System.currentTimeMillis();

    //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(
          String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    //如果是同一时间生成的，则进行毫秒内序列
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;

      //毫秒内序列溢出
      if (sequence == 0) {

        //阻塞到下一个毫秒,获得新的时间戳
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;//时间戳改变，毫秒内序列重置
    }

    //上次生成ID的时间截
    lastTimestamp = timestamp;

    //移位并通过或运算拼到一起组成64位的ID
    return ((timestamp - basicTime) << timestampLeftShift)//
        | (dataCenterId << dataCenterIdShift) //
        | (workerId << workerIdShift) //
        | sequence;
  }

  protected ResponseMsg setData(ResponseMsg responseMsg, Object data) {
    if (null == responseMsg || null == data) {
      return responseMsg;
    }
    try {
      if (data instanceof String) {
        responseMsg.setData(data);
      } else {
        responseMsg.setData(jacksonObjectMapper.writeValueAsString(data));
      }
    } catch (JsonProcessingException e) {
      log.error("CrudService.setData.json.error:", e);
      responseMsg.setData(data);
    }
    return responseMsg;
  }

  /**
   * @description 设置创建者
   * @method setCreateUser
   * @date: 2022/3/10 14:10
   * @author:liangcanlong
   */
  protected void setCreateUser(BaseEntity baseEntity) {
    LocalDateTime now = LocalDateTime.now();
    baseEntity.setCreateTime(now);
    baseEntity.setUpdateTime(now);
    UserInfo userInfo = UserUtil.getUserInfo();
    if (userInfo == null) {
      return;
    }
    baseEntity.setCreateUserId(userInfo.getUserId());
    baseEntity.setCreateUserName(userInfo.getUserRealName());
    baseEntity.setUpdateUserName(userInfo.getUserRealName());
    baseEntity.setUpdateUserId(userInfo.getUserId());
  }

  /**
   * @description 设置更新者
   * @method setUpdateUser
   * @date: 2022/3/10 14:10
   * @author:liangcanlong
   */
  protected void setUpdateUser(BaseEntity baseEntity) {
    LocalDateTime now = LocalDateTime.now();
    baseEntity.setUpdateTime(now);
    UserInfo userInfo = UserUtil.getUserInfo();
    if (userInfo == null) {
      return;
    }
    baseEntity.setUpdateUserName(userInfo.getUserRealName());
    baseEntity.setUpdateUserId(userInfo.getUserId());
  }


  /**
   * @description 设置默认创建者
   * @date: 2022/4/25 14:48
   * @author:曾仕斌
   */
  protected void setCreateDefaultUser(BaseEntity baseEntity) {
    LocalDateTime now = LocalDateTime.now();
    baseEntity.setCreateTime(now);
    baseEntity.setUpdateTime(now);
    baseEntity.setCreateUserId(CommonConstant.DEFAULT_USER_ID);
    baseEntity.setCreateUserName(CommonConstant.DEFAULT_USER_NAME);
    baseEntity.setUpdateUserId(CommonConstant.DEFAULT_USER_ID);
    baseEntity.setUpdateUserName(CommonConstant.DEFAULT_USER_NAME);
  }


  /**
   * @description 设置默认更新者
   * @date: 2022/4/27
   * @author:曾仕斌
   */
  protected void setUpdateDefaultUser(BaseEntity baseEntity) {
    LocalDateTime now = LocalDateTime.now();
    baseEntity.setUpdateTime(now);
    UserInfo userInfo = UserUtil.getUserInfo();
    if (userInfo == null) {
      return;
    }
    baseEntity.setUpdateUserName(CommonConstant.DEFAULT_USER_NAME);
    baseEntity.setUpdateUserId(CommonConstant.DEFAULT_USER_ID);
  }


}
