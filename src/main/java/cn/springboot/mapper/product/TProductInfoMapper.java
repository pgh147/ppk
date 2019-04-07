package cn.springboot.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TProductInfo;
@Mapper
public interface TProductInfoMapper {
    int deleteByPrimaryKey(String productNo);

    int insert(TProductInfo record);

    int insertSelective(TProductInfo record);

    TProductInfo selectByPrimaryKey(String productNo);
    TProductInfo selectByProductInfo(TProductInfo record);
    int updateByPrimaryKeySelective(TProductInfo record);

    int updateByPrimaryKey(TProductInfo record);
}