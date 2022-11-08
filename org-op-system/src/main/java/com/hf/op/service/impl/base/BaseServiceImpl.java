package com.hf.op.service.impl.base;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.global.config.ConfigConst;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;
import com.hf.op.service.inf.base.BaseService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author wpq
 * @function 基础服务
 * @Date 2021/12/10
 */
@Service
@RefreshScope
public class BaseServiceImpl extends CrudService implements BaseService {

  private final Integer baseRandom = 9000;

  private final Integer minCode = 1000;

  @CreateCache(expire = 300, name = CommCacheConst.BASE_KEY_ORG
      + "codeCache", cacheType = CacheType.REMOTE)
  private Cache<String, String> codeCache;
  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_base_dict_", cacheType = CacheType.BOTH)
  private Cache<String, List<ListSysDictVo>> opBaseDictKeyCache;
  private OpDepartmentRepository opDepartmentRepository;

  public BaseServiceImpl(OpDepartmentRepository opDepartmentRepository) {
    this.opDepartmentRepository = opDepartmentRepository;
  }

  @Override
  public ResponseMsg getLoginVerityCode(QueryVerityCodeLoginDto queryVerityCodeLoginDto) {
      /*  if (ResponseMsg.isNotSuccess(verityCodeResult)) {
            return verityCodeResult;
        }
        Map<String, Object> result = (Map<String, Object>) verityCodeResult.getData();*/
    //   QueryVerityCodeLoginDto queryVerityCodeLoginDto = new QueryVerityCodeLoginDto();
    queryVerityCodeLoginDto.setSecretKey(ConfigConst.ServerSecretAesKey);
    String code = ((new Random()).nextInt(baseRandom) + minCode) + "";
    codeCache.put(CommCacheConst.EXP_CODE + queryVerityCodeLoginDto.getLoginAccount(), code);
    queryVerityCodeLoginDto.setVerifyCode(code);
    String verifyCode = queryVerityCodeLoginDto.getVerifyCode();
    String secretKey = queryVerityCodeLoginDto.getSecretKey();
    Map<String, Object> resp = new HashMap<>();
    resp.put("verifyCode", verifyCode);
    resp.put("secretKey", secretKey);
    return new ResponseMsg(resp);
  }

  @Override
  public ResponseMsg listSysDict() {
        /*ResponseMsg responseMsg = baseComServiceImpl.remoteListSysDict();
        if (ResponseMsg.isNotSuccess(responseMsg)) {
            return responseMsg;
        }
        List<ListSysDictVo> result = (List<ListSysDictVo>) responseMsg.getData();
        responseMsg = this.setData(responseMsg, result);

        return responseMsg;*/

    List<ListSysDictVo> listSysDictVos = opBaseDictKeyCache
        .get(CommCacheConst.NOR_BASE_SYS_DICT_CACHE);
    if (CollectionUtils.isEmpty(listSysDictVos)) {
      listSysDictVos = opDepartmentRepository.listSysDict();
    }
    return new ResponseMsg(listSysDictVos);
  }
}
