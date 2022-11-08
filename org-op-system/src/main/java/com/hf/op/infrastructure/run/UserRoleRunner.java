package com.hf.op.infrastructure.run;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.domain.model.role.OpSysRoleRepository;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.service.inf.OpSysUserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UserRoleRunner implements CommandLineRunner {


  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_base_dict_", cacheType = CacheType.BOTH)
  private Cache<String, List<ListSysDictVo>> opBaseDictKeyCache;


  private OpDepartmentRepository opDepartmentRepository;
  private OpSysRoleRepository opSysRoleRepository;

  private OpSysUserService opSysUserServiceImpl;

  public UserRoleRunner(OpSysUserService opSysUserServiceImpl,
      OpSysRoleRepository opSysRoleRepository,
      OpDepartmentRepository opDepartmentRepository) {
    this.opSysUserServiceImpl = opSysUserServiceImpl;
    this.opSysRoleRepository = opSysRoleRepository;
    this.opDepartmentRepository = opDepartmentRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    // 字典
    List<ListSysDictVo> listSysDict = opDepartmentRepository.listSysDict();
    opBaseDictKeyCache.put(CommCacheConst.NOR_BASE_SYS_DICT_CACHE, listSysDict);

    log.info("初始化权限列表");
    opSysUserServiceImpl.setAllUserFunctions();
  }
}
