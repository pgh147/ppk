package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.model.product.TQcIncell;
@Mapper
public interface TQcIncellMapper {
    int deleteByPrimaryKey(String id);
    int deleteByIds(@Param("ids")String ids);
    int insert(TQcIncell record);

    int insertSelective(TQcIncell record);

    TQcIncell selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TQcIncell record);

    int updateByPrimaryKey(TQcIncell record);
    List<TQcIncell> findProductByPage(TQcIncell record);
    
}