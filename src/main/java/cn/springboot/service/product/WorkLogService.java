package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TWorkLog;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface WorkLogService {

    public boolean addProduct(TWorkLog news);

    public boolean editProduct(TWorkLog news);
    public boolean updateProduct(TWorkLog news);
    public boolean deleteProduct(String id);
    public boolean batchDeleteProduct(List<String> ids);
    public TWorkLog findProductById(String newsId);

    public List<TWorkLog> findProductByKeywords(String keywords);

    public PageInfo<TWorkLog> findProductByPage(Integer pageNum, TWorkLog keywords);

}