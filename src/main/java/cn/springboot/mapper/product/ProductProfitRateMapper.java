package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.product.TProductProfitRate;

/** 
 * @Description mapper接口
 */
@Mapper
public interface ProductProfitRateMapper extends BaseMapper<String, TProductProfitRate> {
    public List<String> findProductListIn(@Param("orderIds")String ids);
}
