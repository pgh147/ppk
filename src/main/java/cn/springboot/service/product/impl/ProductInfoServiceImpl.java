package cn.springboot.service.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.springboot.mapper.product.TProductInfoMapper;
import cn.springboot.model.product.TProductInfo;
import cn.springboot.service.product.ProductInfoService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {

    private static final Logger log = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    @Autowired
    private TProductInfoMapper tProductInfoMapper;
    
    @Transactional
	@Override
	public boolean addProduct(TProductInfo news) {
		tProductInfoMapper.insertSelective(news);
		return true;
	}
    @Transactional
	@Override
	public boolean editProduct(TProductInfo news) {
		tProductInfoMapper.updateByPrimaryKeySelective(news);
		return true;
	}
    @Transactional
	@Override
	public boolean updateProduct(TProductInfo news) {
		tProductInfoMapper.updateByPrimaryKeySelective(news);
		return false;
	}
    @Transactional
	@Override
	public boolean deleteProduct(String id) {
		tProductInfoMapper.deleteByPrimaryKey(id);
		return false;
	}
	@Override
	public TProductInfo findProductById(String newsId) {
		return tProductInfoMapper.selectByPrimaryKey(newsId);
	}
	@Override
	public TProductInfo findProductByPInfo(TProductInfo info) {
		return tProductInfoMapper.selectByProductInfo(info);
	}
   
}
