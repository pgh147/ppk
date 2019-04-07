package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.product.TProductStk;

/** 
 * @Description mapper接口
 */
@Mapper
public interface ProductStkMapper extends BaseMapper<String, TProductStk> {
    public List<String> findProductBySkuStatus(@Param("sku")String ids,@Param("status")String status);
}
