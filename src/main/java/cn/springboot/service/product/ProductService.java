package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TProduct;
import cn.springboot.model.simple.News;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface ProductService {

    public boolean addProduct(TProduct news);

    public boolean editProduct(TProduct news);
    public boolean updateProduct(TProduct news);
    public boolean deleteProduct(String id);
    public TProduct findProductById(String newsId);

    public List<TProduct> findProductByKeywords(String keywords);

    public PageInfo<TProduct> findProductByPage(Integer pageNum, TProduct keywords);

    public PageInfo<TProduct> findProductByPage1(Integer pageNum, String keywords);

    public PageInfo<TProduct> findProductByPage2(Integer pageNum, String keywords);

}