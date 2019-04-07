package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TPurchase;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface PurchaseService {

    public boolean addProduct(TPurchase news);

    public boolean editProduct(TPurchase news);
    public boolean updateProduct(TPurchase tPurchase);
    public boolean deleteProduct(String id);
    public TPurchase findProductById(String newsId);

    public List<TPurchase> findProductByKeywords(String keywords);

    public PageInfo<TPurchase> findProductByPage(Integer pageNum, TPurchase keywords);
    
    int importPurchase(List<TPurchase> list);

}