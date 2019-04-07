package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TOutcell;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface OutCellService {

    public boolean addProduct(TOutcell news);

    public boolean editProduct(TOutcell news);
    public boolean updateProduct(TOutcell news);
    public boolean deleteProduct(String id);
    public TOutcell findProductById(String newsId);

    public List<TOutcell> findProductByKeywords(String keywords);

    public PageInfo<TOutcell> findProductByPage(Integer pageNum, TOutcell keywords);
    int importOutCell(List<TOutcell> list);
}