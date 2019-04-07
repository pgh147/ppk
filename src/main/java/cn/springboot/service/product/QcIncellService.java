package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.model.simple.News;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface QcIncellService {

    public boolean addProduct(TQcIncell news);

    public boolean editProduct(TQcIncell news);
    public boolean updateProduct(TQcIncell news);
    public boolean deleteProduct(String id);
    public TQcIncell findProductById(String newsId);

    public List<TQcIncell> findProductByKeywords(String keywords);

    public PageInfo<TQcIncell> findProductByPage(Integer pageNum, TQcIncell keywords);

}