package cn.springboot.service.product;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductImport;
import cn.springboot.model.product.TProductStk;
import cn.springboot.model.product.TProductVindicate;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface ProductVindicateService {

    public TProductVindicate findProductById(String newsId);

    public List<TProduct> findProductByKeywords(String keywords);

    public PageInfo<TProduct> findProductByPage(Integer pageNum, TProduct keywords);
    void importSku(List<TProductImport> dataList);
    void importStk(List<TProductStk> dataList);
    public List<TProduct>  findUserSaleQty(Map<String,Object> param);
    public List<TProduct>  findTop10SKU(Map<String,Object> param);
}