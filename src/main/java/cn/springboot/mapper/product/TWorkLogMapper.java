package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TWorkLog;
@Mapper
public interface TWorkLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(TWorkLog record);

    int insertSelective(TWorkLog record);

    TWorkLog selectByPrimaryKey(String id);
    TWorkLog selectByProductNo(String productNo);
    int updateByPrimaryKeySelective(TWorkLog record);

    int updateByPrimaryKey(TWorkLog record);
    
    List<TWorkLog> findProductByPage(TWorkLog param);
}