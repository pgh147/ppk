package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductImg;


/** 
 * @Description mapper接口
 */
@Mapper
public interface ProductImgMapper extends BaseMapper<String, TProductImg> {
    
    List<TProductImg> findProductByPage(@Param("keywords") String keywords);
    List<TProductImg> findProductByKeywords(@Param("keywords") String keywords);
    
    int deleteByImgNo(@Param("imgNo") String imgNo);

}
