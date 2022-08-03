package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TIssues;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface IssuesService {

    public boolean addProduct(TIssues news);

    public boolean editProduct(TIssues news);
    public boolean updateProduct(TIssues news);
    public boolean deleteProduct(String id);
    public boolean batchDeleteProduct(List<String> ids);
    public TIssues findProductById(String newsId);

    public List<TIssues> findProductByKeywords(String keywords);

    public PageInfo<TIssues> findProductByPage(Integer pageNum, TIssues keywords);

}