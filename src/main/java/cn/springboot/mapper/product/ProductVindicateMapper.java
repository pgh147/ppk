package cn.springboot.mapper.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductVindicate;


/** 
 * @Description mapper接口
 */
@Mapper
public interface ProductVindicateMapper extends BaseMapper<String, TProductVindicate> {
    
    List<TProduct> findProductByPage(TProduct prodect);
    List<TProduct> findProductByKeywords(@Param("keywords") String keywords);
    public List<TProduct> findProductListNoIn(String ids);
    List<TProduct> findUserSaleQty(@Param("param") Map<String,Object> param);
    List<TProduct> findTop10SKU(@Param("param") Map<String,Object> param);
}
