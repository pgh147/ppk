package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TPurchase;
@Mapper
public interface TPurchaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(TPurchase record);

    int insertSelective(TPurchase record);

    TPurchase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TPurchase record);

    int updateByPrimaryKey(TPurchase record);
    
    List<TPurchase> findProductByPage(TPurchase record);
}