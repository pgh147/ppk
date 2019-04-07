package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TOutcell;
@Mapper
public interface TOutcellMapper {
    int deleteByPrimaryKey(String id);

    int insert(TOutcell record);

    int insertSelective(TOutcell record);

    TOutcell selectByPrimaryKey(String id);
    TOutcell selectByProductNo(String productNo);
    int updateByPrimaryKeySelective(TOutcell record);

    int updateByPrimaryKey(TOutcell record);
    
    List<TOutcell> findProductByPage(TOutcell param);
}