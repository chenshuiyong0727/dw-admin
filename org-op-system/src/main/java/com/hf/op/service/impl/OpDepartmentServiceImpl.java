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
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.department.OpDepartmentEntity;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRepository;
import com.hf.op.infrastructure.dto.department.ListDeptDropDownListVo;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.department.QueryOpDeptTreeListDto;
import com.hf.op.infrastructure.dto.department.SaveOrUpdateOpDeptDto;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.hf.op.service.inf.OpDepartmentService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author wpq
 * @function 部门业务类
 * @Date 2021/11/29
 */
@Service
public class OpDepartmentServiceImpl extends
    BatchCrudService<OpDepartmentRepository, OpDepartmentEntity> implements OpDepartmentService {

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_department_", cacheType = CacheType.BOTH)
  private Cache<Long, OpDepartmentEntity> opDepartmentKeyCache;

  private OpDepartmentRepository opDepartmentRepository;
  private OpSysUserRepository opSysUserRepository;

  public OpDepartmentServiceImpl(OpDepartmentRepository opDepartmentRepository,
      OpSysUserRepository opSysUserRepository) {
    this.opDepartmentRepository = opDepartmentRepository;
    this.opSysUserRepository = opSysUserRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> saveDepartments(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    saveOrUpdateOpDeptDto.setName(saveOrUpdateOpDeptDto.getName().trim());
    if (isExist(saveOrUpdateOpDeptDto)) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_NAME_EXIST.getCode(),
          OpRespCodeEnum.DEPT_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    OpDepartmentEntity opDepartmentEntity = new OpDepartmentEntity();
    opDepartmentEntity.setId(createId());
    setUpdateUser(opDepartmentEntity);
    opDepartmentEntity.setName(saveOrUpdateOpDeptDto.getName());
    opDepartmentEntity.setDescription(saveOrUpdateOpDeptDto.getDescription());
    Integer level = 1;
    if (saveOrUpdateOpDeptDto.getLevelOne() == 1) {
      opDepartmentEntity.setLevel(level);
      opDepartmentEntity.setLevelOneDepartment(opDepartmentEntity.getId());
      opDepartmentEntity.setLevelOneDepartmentName(opDepartmentEntity.getName());
    } else {
      //查询数据库进行校验
      QueryWrapper<OpDepartmentEntity> parentOpDepartmentWrapper = new QueryWrapper();
      Long superiorDepartment = Long.valueOf(saveOrUpdateOpDeptDto.getSuperiorDepartment());
      parentOpDepartmentWrapper.eq("id", superiorDepartment);
      parentOpDepartmentWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
      OpDepartmentEntity parentOpDepartmentEntity = opDepartmentRepository
          .selectOne(parentOpDepartmentWrapper);
      if (null == parentOpDepartmentEntity) {
        return new ResponseMsg(OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getCode(),
            OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getMsg());
      }
      level = level + parentOpDepartmentEntity.getLevel();
      opDepartmentEntity.setLevel(level);
      opDepartmentEntity
          .setSuperiorDepartment(Long.valueOf(saveOrUpdateOpDeptDto.getSuperiorDepartment()));
      opDepartmentEntity.setLevelParams(opDepartmentEntity, parentOpDepartmentEntity);
    }
    setCreateUser(opDepartmentEntity);
    opDepartmentRepository.insert(opDepartmentEntity);
    opDepartmentKeyCache.put(opDepartmentEntity.getId(), opDepartmentEntity);
    result = setData(result, opDepartmentEntity.getId().toString());
    return result;
  }

/*
    @Override
    public ResponseMsg getDepartmentsById1(Long id) {
        ResponseMsg result = new ResponseMsg();
        OpDepartmentEntity opDepartmentEntity = opDepartmentKeyCache.get(id);
        if (null == opDepartmentEntity) {
            opDepartmentEntity = opDepartmentRepository.selectById(id);
            String superiorDepartmentName = this.getSuperiorDepartmentNameById(opDepartmentEntity.getSuperiorDepartment());
            result = setData(result, opDepartmentEntity);
        } else {
            result = setData(result, opDepartmentEntity);
        }
        return result;
    }
*/

  @Override
  public ResponseMsg getDepartmentsById(Long id) {
    ResponseMsg result = new ResponseMsg();
    OpDepartmentEntity opDepartmentEntity = getSuperiorDepartmentNameById(id);
    if (opDepartmentEntity != null && opDepartmentEntity.getSuperiorDepartment() != null) {
      OpDepartmentEntity superiorDepartmentEntity = getSuperiorDepartmentNameById(
          opDepartmentEntity.getSuperiorDepartment());
      if (superiorDepartmentEntity != null) {
        opDepartmentEntity.setSuperiorDepartmentName(superiorDepartmentEntity.getName());
      }
    }
    return setData(result, opDepartmentEntity);
  }

  private OpDepartmentEntity getSuperiorDepartmentNameById(Long id) {
    OpDepartmentEntity opDepartmentEntity = opDepartmentKeyCache.get(id);
    if (null == opDepartmentEntity) {
      opDepartmentEntity = opDepartmentRepository.selectById(id);
    }
    return opDepartmentEntity;
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateDepartments(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    OpDepartmentEntity opDepartmentEntity = opDepartmentRepository
        .selectById(saveOrUpdateOpDeptDto.getId());
    if (opDepartmentEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_NOT_EXIST.getCode(),
          OpRespCodeEnum.DEPT_NOT_EXIST.getMsg());
    }
    saveOrUpdateOpDeptDto.setName(saveOrUpdateOpDeptDto.getName().trim());
    if (isExist(saveOrUpdateOpDeptDto)) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_NAME_EXIST.getCode(),
          OpRespCodeEnum.DEPT_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    setUpdateUser(opDepartmentEntity);
    opDepartmentEntity.setName(saveOrUpdateOpDeptDto.getName());
    opDepartmentEntity.setDescription(saveOrUpdateOpDeptDto.getDescription());
    opDepartmentEntity.setDataStatus(saveOrUpdateOpDeptDto.getDataStatus());
    Integer level = 1;
    if (saveOrUpdateOpDeptDto.getLevelOne() == 0) {
      //查询数据库进行校验
      QueryWrapper<OpDepartmentEntity> parentOpDepartmentWrapper = new QueryWrapper();
      parentOpDepartmentWrapper
          .eq("id", Long.valueOf(saveOrUpdateOpDeptDto.getSuperiorDepartment()));
      parentOpDepartmentWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
      OpDepartmentEntity parentOpDepartmentEntity = opDepartmentRepository
          .selectOne(parentOpDepartmentWrapper);
          /*  if (null == parentOpDepartmentEntity || StringUtilLocal.isEmpty(parentOpDepartmentEntity.getOtherSuperiorDepartment())) {
                return new ResponseMsg(OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getCode(),
                        OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getMsg());
            }*/
      if (null == parentOpDepartmentEntity
          || (parentOpDepartmentEntity.getLevel() > 4 && StringUtilLocal
          .isEmpty(parentOpDepartmentEntity.getOtherSuperiorDepartment()))
          || (parentOpDepartmentEntity.getLevel() <= 4 && parentOpDepartmentEntity.getLevel() > 1
          && parentOpDepartmentEntity.getSuperiorDepartment() == null)) {
        return new ResponseMsg(OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getCode(),
            OpRespCodeEnum.PARENT_DEPT_NOT_EXIST.getMsg());
      }
      level = level + parentOpDepartmentEntity.getLevel();
      opDepartmentEntity.setLevel(level);
      opDepartmentEntity
          .setSuperiorDepartment(Long.valueOf(saveOrUpdateOpDeptDto.getSuperiorDepartment()));
      opDepartmentEntity.setLevelParams(opDepartmentEntity, parentOpDepartmentEntity);
    }
    opDepartmentRepository.updateById(opDepartmentEntity);
    this.updateDepartmentsOtherParam(saveOrUpdateOpDeptDto.getId(),
        saveOrUpdateOpDeptDto.getName());
    opDepartmentKeyCache.put(opDepartmentEntity.getId(), opDepartmentEntity);
    result = setData(result, opDepartmentEntity.getId().toString());
    return result;
  }

  /**
   * 修改其他字段
   */
  private void updateDepartmentsOtherParam(Long id, String name) {
    List<OpDepartmentEntity> list1 = this.getListByParam(1, id);
    List<OpDepartmentEntity> list2 = this.getListByParam(2, id);
    List<OpDepartmentEntity> list3 = this.getListByParam(3, id);
    List<OpDepartmentEntity> list4 = this.getListByParam(4, id);
    List<OpDepartmentEntity> list5 = this.getListByParam(5, id);
    List<OpDepartmentEntity> res = new ArrayList<>();
    if (!CollectionUtils.isEmpty(list1)) {
      for (OpDepartmentEntity entity : list1) {
        entity.setLevelOneDepartmentName(name);
        setUpdateUser(entity);
        res.add(entity);
      }
    }
    if (!CollectionUtils.isEmpty(list2)) {
      for (OpDepartmentEntity entity : list2) {
        entity.setLevelTwoDepartmentName(name);
        setUpdateUser(entity);
        res.add(entity);
      }
    }

    if (!CollectionUtils.isEmpty(list3)) {
      for (OpDepartmentEntity entity : list3) {
        entity.setLevelThreeDepartmentName(name);
        setUpdateUser(entity);
        res.add(entity);
      }
    }

    if (!CollectionUtils.isEmpty(list4)) {
      for (OpDepartmentEntity entity : list4) {
        entity.setLevelFourDepartmentName(name);
        setUpdateUser(entity);
        res.add(entity);
      }
    }
    if (!CollectionUtils.isEmpty(list5)) {
      for (OpDepartmentEntity entity : list5) {
        List<String> otherSuperiorDepartmentList = Arrays
            .asList(entity.getOtherSuperiorDepartment().split(","));
        String otherSuperiorDepartmentName = null;
        for (String otherSuperiorDepartment : otherSuperiorDepartmentList) {
          Long otherId = Long.parseLong(otherSuperiorDepartment);
          String departmentName = null;
          if (!otherId.equals(id)) {
            OpDepartmentEntity opDepartmentEntity = getSuperiorDepartmentNameById(
                Long.parseLong(otherSuperiorDepartment));
            if (opDepartmentEntity != null) {
              departmentName = opDepartmentEntity.getName();
            }
          } else {
            departmentName = name;
          }
          if (StringUtilLocal.isEmpty(otherSuperiorDepartmentName)) {
            otherSuperiorDepartmentName = departmentName;
          } else {
            otherSuperiorDepartmentName = otherSuperiorDepartmentName + "-" + departmentName;
          }
        }
        entity.setOtherSuperiorDepartmentName(otherSuperiorDepartmentName);
        setUpdateUser(entity);
        res.add(entity);
      }
    }
    if (!CollectionUtils.isEmpty(res)) {
      this.saveOrUpdateBatch(res, res.size());
    }
  }

  private List<OpDepartmentEntity> getListByParam(Integer level, Long id) {
    QueryWrapper<OpDepartmentEntity> wrapper = new QueryWrapper();
    if (level == 1) {
      wrapper.eq("level_one_department", id);
    } else if (level == 2) {
      wrapper.eq("level_two_department", id);
    } else if (level == 3) {
      wrapper.eq("level_three_department", id);
    } else if (level == 4) {
      wrapper.eq("level_four_department", id);
    } else {
      wrapper.like("other_superior_department", id);
    }
    List<OpDepartmentEntity> list1 = opDepartmentRepository.selectList(wrapper);
    return list1;
  }

  @Override
  public Boolean isExist(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    QueryWrapper<OpDepartmentEntity> opDepartmentByNameWrapper = new QueryWrapper<>();
    opDepartmentByNameWrapper.eq("name", saveOrUpdateOpDeptDto.getName());
    if (null != saveOrUpdateOpDeptDto.getId()) {
      opDepartmentByNameWrapper.ne("id", saveOrUpdateOpDeptDto.getId());
    }
    int count = opDepartmentRepository.selectCount(opDepartmentByNameWrapper);
    return count > 0 ? true : false;
  }

  @Override
  public ResponseMsg pageListDepartments(QueryOpDeptListDto queryOpDeptListDto) {
    Page page = new Page(queryOpDeptListDto.getPageNum(), queryOpDeptListDto.getPageSize());
    IPage<QueryOpDeptListDto> pageListDepartments = opDepartmentRepository
        .pageListDepartments(page, queryOpDeptListDto);
    ResponseMsg result = new ResponseMsg();
    result = setData(result, pageListDepartments);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateDeptStatus(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    OpDepartmentEntity opDepartmentEntity = opDepartmentRepository
        .selectById(saveOrUpdateOpDeptDto.getId());
    if (opDepartmentEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_NOT_EXIST.getCode(),
          OpRespCodeEnum.DEPT_NOT_EXIST.getMsg());
    }

    QueryWrapper<OpDepartmentEntity> sonDeptWrapper = new QueryWrapper<>();
    sonDeptWrapper.eq("superior_department", opDepartmentEntity.getId());
    List<OpDepartmentEntity> list1 = opDepartmentRepository.selectList(sonDeptWrapper);
    if (!CollectionUtils.isEmpty(list1)) {
      for (OpDepartmentEntity entity : list1) {
        if (entity.getDataStatus().equals(DataStatusEnum.ENABLE.getCode())) {
          return new ResponseMsg(OpRespCodeEnum.DEPT_EXIST_SON_DEPT.getCode(),
              OpRespCodeEnum.DEPT_EXIST_SON_DEPT.getMsg());
        }
      }
    }
    if (opDepartmentEntity.getSuperiorDepartment() != null) {
      QueryWrapper<OpDepartmentEntity> parentDeptWrapper = new QueryWrapper<>();
      parentDeptWrapper.eq("id", opDepartmentEntity.getSuperiorDepartment());
      OpDepartmentEntity parent = opDepartmentRepository.selectOne(parentDeptWrapper);
      if (parent != null && parent.getDataStatus().equals(DataStatusEnum.FORBIDDEN.getCode())) {
        return new ResponseMsg(OpRespCodeEnum.PARENT_DEPT_DISABLE.getCode(),
            OpRespCodeEnum.PARENT_DEPT_DISABLE.getMsg());
      }
    }
/*    QueryWrapper<OpDepartmentEntity> sonDeptWrapper = new QueryWrapper<>();
    sonDeptWrapper.eq("superior_department", opDepartmentEntity.getId());
    int sonDeptCount = opDepartmentRepository.selectCount(sonDeptWrapper);*/
    /*if (sonDeptCount > 0) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_EXIST_SON_DEPT.getCode(),
          OpRespCodeEnum.DEPT_EXIST_SON_DEPT.getMsg());
    }*/
    QueryWrapper<OpSysUserEntity> wrapper = new QueryWrapper();
    wrapper.eq("department_id", saveOrUpdateOpDeptDto.getId());
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    int userCount = opSysUserRepository.selectCount(wrapper);
    if (userCount > 0) {
      return new ResponseMsg(OpRespCodeEnum.DEPT_EXIST_USER.getCode(),
          OpRespCodeEnum.DEPT_EXIST_USER.getMsg());
    }
    setUpdateUser(opDepartmentEntity);
    opDepartmentEntity.setDataStatus(saveOrUpdateOpDeptDto.getDataStatus());
    opDepartmentRepository.updateById(opDepartmentEntity);
    if (null != opDepartmentKeyCache.get(saveOrUpdateOpDeptDto.getId())) {
      opDepartmentKeyCache.put(saveOrUpdateOpDeptDto.getId(), opDepartmentEntity);
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opDepartmentEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg listTreeDepartments() {
    List<QueryOpDeptTreeListDto> listTreeDepartments = opDepartmentRepository.listTreeDepartments();
    Map<Long, List<QueryOpDeptTreeListDto>> collect = listTreeDepartments.stream()
        .collect(Collectors.groupingBy(QueryOpDeptTreeListDto::getSuperiorDepartment));
    listTreeDepartments.forEach((item) -> {
      if (CollectionUtils.isEmpty(collect.get(item.getId()))) {
        item.setChild(new ArrayList<>());
      } else {
        item.setChild(collect.get(item.getId()));
      }
    });
    List<QueryOpDeptTreeListDto> list = listTreeDepartments
        .stream()
        .filter((item) -> item.getLevel() == 1)
        .collect(Collectors.toList());
    ResponseMsg result = new ResponseMsg();
    result = setData(result, list);
    return result;
  }

  @Override
  public ResponseMsg listLevelDepartments() {
    List<QueryOpDeptTreeListDto> queryOpDeptTreeListDtoList = opDepartmentRepository
        .listTreeDepartments();
    ResponseMsg result = new ResponseMsg();
    result = setData(result, queryOpDeptTreeListDtoList);
    return result;
  }

  @Override
  public ResponseMsg listDropDownDepartments() {
    List<ListDeptDropDownListVo> listDeptDropDownListVos = opDepartmentRepository
        .listDropDownDepartments();
    ResponseMsg result = new ResponseMsg();
    result = setData(result, listDeptDropDownListVos);
    return result;
  }


}
