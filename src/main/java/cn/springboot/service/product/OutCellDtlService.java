package cn.springboot.service.product;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.springboot.model.product.TOutcell;
import cn.springboot.model.product.TOutcellDtl;

/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface OutCellDtlService {

    public boolean addProduct(TOutcellDtl news);

    public boolean editProduct(TOutcellDtl news);
    public boolean updateProduct(TOutcellDtl news);
    public boolean deleteProduct(String id);
    public TOutcellDtl findProductById(String newsId);

    public List<TOutcellDtl> findProductByKeywords(String keywords);

    public PageInfo<TOutcellDtl> findProductByPage(Integer pageNum, TOutcellDtl keywords);
    int importOutcellDtl(List<TOutcellDtl> list);
}