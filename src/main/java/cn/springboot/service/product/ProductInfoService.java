package cn.springboot.service.product;
import cn.springboot.model.product.TProductInfo;
/** 
 * @Description 接口类
 * @author pgh
 * @date Mar 16, 2019 5:19:14 PM  
 */
public interface ProductInfoService {
    public boolean addProduct(TProductInfo news);
    public boolean editProduct(TProductInfo news);
    public boolean updateProduct(TProductInfo news);
    public boolean deleteProduct(String id);
    public TProductInfo findProductById(String newsId);
    public TProductInfo findProductByPInfo(TProductInfo newsId);
}