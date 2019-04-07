package cn.springboot.service.product.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.common.constants.Constants;
import cn.springboot.config.db.database.DataSourceEnum;
import cn.springboot.config.db.database.TargetDataSource;
import cn.springboot.config.db.pk.FactoryAboutKey;
import cn.springboot.config.db.pk.TableEnum;
import cn.springboot.mapper.product.ProductImgMapper;
import cn.springboot.mapper.product.ProductMapper;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductImg;
import cn.springboot.service.product.ProductService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Transactional
    @Override
    public boolean addProduct(TProduct tProduct) {
        if (tProduct != null) {
//        	String imgId = FactoryAboutKey.getPK(TableEnum.T_PRODUCT_IMG);
        	tProduct.setId(FactoryAboutKey.getPK(TableEnum.T_PRODUCT));
        	tProduct.setCreateTime(Calendar.getInstance().getTime());
        	tProduct.setProductImgNo(tProduct.getProductNo());
            int flag = productMapper.insert(tProduct);
            
            Map<String,Object> map = new HashMap<String,Object>();
        	map.put("imgNo", tProduct.getProductNo());
        	map.put("type", 1);
        	List<TProductImg> list= productImgMapper.findAllByFilter(map);
        	if(null != list && list.size() > 0){
        		list.get(0).setImgData(tProduct.getProductImgData());
        		productImgMapper.update(list.get(0));
        	}else{
        		String imgId = FactoryAboutKey.getPK(TableEnum.T_PRODUCT_IMG);
            	TProductImg img = new TProductImg();
                img.setId(imgId);
                img.setType(1);
                img.setImgNo(tProduct.getProductNo());
                img.setCreateTime(Calendar.getInstance().getTime());
                img.setImgData(tProduct.getProductImgData());
                productImgMapper.insert(img);
        	}
//            if (StringUtils.equals(tProduct.getTitle(), "a"))
//                throw new BusinessException("001", "测试事务回溯");
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public boolean editProduct(TProduct news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = productMapper.update(news);
            if(news.getIsUpdateImg()){
            	Map<String,Object> map = new HashMap<String,Object>();
            	map.put("imgNo", news.getProductNo());
            	map.put("type", 1);
            	List<TProductImg> list= productImgMapper.findAllByFilter(map);
            	if(null != list && list.size() > 0){
            		list.get(0).setImgData(news.getProductImgData());
            		productImgMapper.update(list.get(0));
            	}else{
            		String imgId = FactoryAboutKey.getPK(TableEnum.T_PRODUCT_IMG);
                	TProductImg img = new TProductImg();
                    img.setId(imgId);
                    img.setType(1);
                    img.setImgNo(news.getProductNo());
                    img.setCreateTime(Calendar.getInstance().getTime());
                    img.setImgData(news.getProductImgData());
                    productImgMapper.insert(img);
            	}
            }
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public boolean updateProduct(TProduct news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
//            TProduct t = productMapper.findById(news.getId());
            int flag = productMapper.update(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public TProduct findProductById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return productMapper.findById(id);
    }

    // 默认数据库
    @Override
    public List<TProduct> findProductByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return productMapper.findProductByKeywords(keywords);
    }

    // 数据库1
    @TargetDataSource(DataSourceEnum.DB1)
    @Override
    public PageInfo<TProduct> findProductByPage1(Integer pageNum, String keywords) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TProduct> news = productMapper.findProductByPage(null);
        // 用PageInfo对结果进行包装
        PageInfo<TProduct> page = new PageInfo<TProduct>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询数据库1 page.toString()={}", page.toString());
        return page;
    }

    // 数据库2
    @TargetDataSource(DataSourceEnum.DB2)
    @Override
    public PageInfo<TProduct> findProductByPage2(Integer pageNum, String keywords) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TProduct> news = productMapper.findProductByPage(null);
        // 用PageInfo对结果进行包装
        PageInfo<TProduct> page = new PageInfo<TProduct>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询数据库2 page.toString()={}", page.toString());
        return page;
    }

    @Override
    public PageInfo<TProduct> findProductByPage(Integer pageNum, TProduct product) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TProduct> news = productMapper.findProductByPage(product);
        // 用PageInfo对结果进行包装
        PageInfo<TProduct> page = new PageInfo<TProduct>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

	@Override
	public boolean deleteProduct(String id) {
		TProduct pro = productMapper.findById(id);
		if(null == pro){
			return false;
		}
		int num = productMapper.delete(id);
		if(num <= 0){
			return false;
		}
		productImgMapper.deleteByImgNo(pro.getProductImgNo());
		return false;
	}

}
