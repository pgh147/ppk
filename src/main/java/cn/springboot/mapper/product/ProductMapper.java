package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.product.TProduct;


/** 
 * @Description mapper接口
 */
@Mapper
public interface ProductMapper extends BaseMapper<String, TProduct> {
    
    List<TProduct> findProductByPage(TProduct prodect);
    List<TProduct> findProductByKeywords(@Param("keywords") String keywords);

}
