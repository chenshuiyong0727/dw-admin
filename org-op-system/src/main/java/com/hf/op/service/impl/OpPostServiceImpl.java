package com.hf.op.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.department.OpDepartmentEntity;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.domain.model.oppost.OpPostEntity;
import com.hf.op.domain.model.oppost.OpPostRepository;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRepository;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.post.ListPostDropDownListVo;
import com.hf.op.infrastructure.dto.post.QueryOpPostListDto;
import com.hf.op.infrastructure.dto.post.SaveOrUpdateOpPostDto;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.hf.op.service.inf.OpPostService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wpq
 * @function 岗位业务类
 * @Date 2021/12/13
 */
@Service
public class OpPostServiceImpl extends CrudService implements OpPostService {

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_post_", cacheType = CacheType.BOTH)
  private Cache<Long, OpPostEntity> opPostKeyCache;

  private OpPostRepository opPostRepository;
  private OpDepartmentRepository opDepartmentRepository;

  private OpSysUserRepository opSysUserRepository;

  public OpPostServiceImpl(OpPostRepository opPostRepository,
      OpDepartmentRepository opDepartmentRepository,
      OpSysUserRepository opSysUserRepository) {
    this.opPostRepository = opPostRepository;
    this.opSysUserRepository = opSysUserRepository;
    this.opDepartmentRepository = opDepartmentRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> saveOpPost(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    saveOrUpdateOpPostDto.setName(saveOrUpdateOpPostDto.getName().trim());
    if (isExist(saveOrUpdateOpPostDto)) {
      return new ResponseMsg(OpRespCodeEnum.POST_NAME_EXIST.getCode(),
          OpRespCodeEnum.POST_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    OpPostEntity opPostEntity = new OpPostEntity();
    BeanUtils.copyProperties(saveOrUpdateOpPostDto, opPostEntity);
    opPostEntity.setId(createId());
    setCreateUser(opPostEntity);
    opPostRepository.insert(opPostEntity);
    opPostKeyCache.put(opPostEntity.getId(), opPostEntity);
    result = setData(result, opPostEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg getOpPostById(Long id) {
    ResponseMsg result = new ResponseMsg();
    OpPostEntity opPostEntity = opPostKeyCache.get(id);
    if (null == opPostEntity) {
      opPostEntity = opPostRepository.selectById(id);
    }
    result = setData(result, opPostEntity);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateOpPost(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    OpPostEntity opPostEntity = opPostRepository.selectById(saveOrUpdateOpPostDto.getId());
    if (opPostEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.POST_NOT_EXIST.getCode(),
          OpRespCodeEnum.POST_NOT_EXIST.getMsg());
    }
    saveOrUpdateOpPostDto.setName(saveOrUpdateOpPostDto.getName().trim());
    if (isExist(saveOrUpdateOpPostDto)) {
      return new ResponseMsg(OpRespCodeEnum.POST_NAME_EXIST.getCode(),
          OpRespCodeEnum.POST_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    BeanUtils.copyProperties(saveOrUpdateOpPostDto, opPostEntity);
    setUpdateUser(opPostEntity);
    opPostRepository.updateById(opPostEntity);
    opPostKeyCache.put(opPostEntity.getId(), opPostEntity);
    result = setData(result, opPostEntity.getId().toString());
    return result;
  }

  @Override
  public Boolean isExist(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    QueryWrapper<OpPostEntity> opPostEntityQueryWrapper = new QueryWrapper<>();
    opPostEntityQueryWrapper.eq("name", saveOrUpdateOpPostDto.getName());
    if (null != saveOrUpdateOpPostDto.getId()) {
      opPostEntityQueryWrapper.ne("id", saveOrUpdateOpPostDto.getId());
    }
    int count = opPostRepository.selectCount(opPostEntityQueryWrapper);
    return count > 0 ? true : false;
  }

  @Override
  public ResponseMsg pageListOpPost(QueryOpPostListDto queryOpPostListDto) {
    Page page = new Page(queryOpPostListDto.getPageNum(), queryOpPostListDto.getPageSize());
    IPage<QueryOpPostListDto> pageListOpPost = opPostRepository
        .pageListOpPost(page, queryOpPostListDto);
    if (pageListOpPost.getTotal() > 0L) {
      this.converPage(pageListOpPost);
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, pageListOpPost);
    return result;
  }

  private void converPage(IPage<QueryOpPostListDto> pageListOpPost) {
    List<QueryOpPostListDto> postListDtos = pageListOpPost.getRecords();
    QueryWrapper<OpDepartmentEntity> wrapper = new QueryWrapper();
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    Integer count = opDepartmentRepository.selectCount(wrapper);
    if (count == 0) {
      return;
    }
    Page page = new Page(0, count);
    IPage<QueryOpDeptListDto> pageListDepartments = opDepartmentRepository
        .pageListDepartments(page, new QueryOpDeptListDto());
    if (pageListDepartments.getTotal() == 0L) {
      return;
    }
    List<QueryOpDeptListDto> deptListDtos = pageListDepartments.getRecords();
    Map<Long, QueryOpDeptListDto> downListVoMap = ListBeanUtil.toMap(deptListDtos, "id");
    if (downListVoMap == null) {
      return;
    }
    for (QueryOpPostListDto dto : postListDtos) {
      if (StringUtilLocal.isEmpty(dto.getDepartmentId())) {
        continue;
      }
      if (!dto.getDepartmentId().contains(";")) {
        String name = QueryOpDeptListDto.getDeptName(downListVoMap, dto.getDepartmentId());
        dto.setDepartmentName(name);
      } else {
        String[] idArray = dto.getDepartmentId().split(";");
        String name = "";
        for (int i = 0; i < idArray.length; i++) {
          if (i == 0) {
            name = QueryOpDeptListDto.getDeptName(downListVoMap, idArray[i]);
          } else {
            name = name + "/" + QueryOpDeptListDto.getDeptName(downListVoMap, idArray[i]);
          }
        }
        dto.setDepartmentName(name);
      }
    }

  }

/*
  private String getDeptName(Map<Long, QueryOpDeptListDto> downListVoMap, String departmentId) {
    QueryOpDeptListDto deptListDto = downListVoMap.get(Long.parseLong(departmentId));
    if (deptListDto == null) {
      return null;
    }
    String name = deptListDto.getLevelOneDepartmentName();
    if (StringUtilLocal.isNotEmpty(deptListDto.getLevelTwoDepartmentName())) {
      name = name + "-" + deptListDto.getLevelTwoDepartmentName();
      if (StringUtilLocal.isNotEmpty(deptListDto.getLevelThreeDepartmentName())) {
        name = name + "-" + deptListDto.getLevelThreeDepartmentName();
        if (StringUtilLocal.isNotEmpty(deptListDto.getLevelFourDepartmentName())) {
          name = name + "-" + deptListDto.getLevelFourDepartmentName();
          if (StringUtilLocal.isNotEmpty(deptListDto.getOtherSuperiorDepartmentName())) {
            name = name + "-" + deptListDto.getOtherSuperiorDepartmentName();
          }
        }
      }
    }
    return name;
  }
*/

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateOpPostStatus(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    OpPostEntity opPostEntity = opPostRepository.selectById(saveOrUpdateOpPostDto.getId());
    if (opPostEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.POST_NOT_EXIST.getCode(),
          OpRespCodeEnum.POST_NOT_EXIST.getMsg());
    }
    QueryWrapper<OpSysUserEntity> wrapper = new QueryWrapper();
    wrapper.eq("post_id", saveOrUpdateOpPostDto.getId());
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    int userCount = opSysUserRepository.selectCount(wrapper);
    if (userCount > 0) {
      return new ResponseMsg(OpRespCodeEnum.POST_EXIST_USER.getCode(),
          OpRespCodeEnum.POST_EXIST_USER.getMsg());
    }
    BeanUtils.copyProperties(saveOrUpdateOpPostDto, opPostEntity);
    setUpdateUser(opPostEntity);
    opPostRepository.updateById(opPostEntity);
    if (null != opPostKeyCache.get(saveOrUpdateOpPostDto.getId())) {
      opPostKeyCache.put(saveOrUpdateOpPostDto.getId(), opPostEntity);
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opPostEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg listDropDownPosts(
      SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    List<ListPostDropDownListVo> listPostDropDownListVos = opPostRepository
        .listDropDownPosts(saveOrUpdateOpPostDto);
    ResponseMsg result = new ResponseMsg();
    result = setData(result, listPostDropDownListVos);
    return result;
  }
}
