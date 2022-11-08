package com.hf.common.infrastructure.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * @author
 * @function
 * @date 2021/8/13
 */
public class BasicMapper<T> {

  private BaseMapper<T> baseMapper;

  public int insert(T entity) {
    return baseMapper.insert(entity);
  }

  public int deleteById(Serializable id) {
    return baseMapper.deleteById(id);
  }

  public int deleteByMap(@Param("cm") Map<String, Object> columnMap) {
    return baseMapper.deleteByMap(columnMap);
  }

  public int delete(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.delete(queryWrapper);
  }

  public int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList) {
    return baseMapper.deleteBatchIds(idList);
  }

  public int updateById(@Param("et") T entity) {
    return baseMapper.updateById(entity);
  }

  public int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper) {
    return baseMapper.update(entity, updateWrapper);
  }

  public T selectById(Serializable id) {
    return baseMapper.selectById(id);
  }

  public List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList) {
    return baseMapper.selectBatchIds(idList);
  }

  public List<T> selectByMap(@Param("cm") Map<String, Object> columnMap) {
    return baseMapper.selectByMap(columnMap);
  }

  public T selectOne(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectOne(queryWrapper);
  }

  public Integer selectCount(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectCount(queryWrapper);
  }

  public List<T> selectList(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectList(queryWrapper);
  }

  public List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectMaps(queryWrapper);
  }

  public List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectObjs(queryWrapper);
  }

  public <E extends IPage<T>> E selectPage(E page, @Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectPage(page, queryWrapper);
  }

  public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page,
      @Param("ew") Wrapper<T> queryWrapper) {
    return baseMapper.selectMapsPage(page, queryWrapper);
  }

}
