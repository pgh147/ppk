package cn.springboot.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.common.constants.Constants;
import cn.springboot.mapper.product.ProductImportMapper;
import cn.springboot.mapper.product.ProductStkMapper;
import cn.springboot.mapper.product.ProductVindicateMapper;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductImport;
import cn.springboot.model.product.TProductStk;
import cn.springboot.model.product.TProductVindicate;
import cn.springboot.service.product.ProductVindicateService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("productVindicateService")
public class ProductVindicateServiceImpl implements ProductVindicateService {

    private static final Logger log = LoggerFactory.getLogger(ProductVindicateServiceImpl.class);
    
    @Autowired
    private ProductVindicateMapper productVindicateMapper;
    @Autowired
    private ProductStkMapper productStkMapper;
    @Autowired
    private ProductImportMapper productImportMapper;
    

    @Override
    public TProductVindicate findProductById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return productVindicateMapper.findById(id);
    }

    // 默认数据库
    @Override
    public List<TProduct> findProductByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return productVindicateMapper.findProductByKeywords(keywords);
    }

    

    @Override
    public PageInfo<TProduct> findProductByPage(Integer pageNum, TProduct product) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TProduct> news = productVindicateMapper.findProductByPage(product);
        // 用PageInfo对结果进行包装
        PageInfo<TProduct> page = new PageInfo<TProduct>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }


	@Override
	public void importSku(List<TProductImport> dataList) {
      StringBuffer strbuff = new StringBuffer("");
      for(TProductImport tp:dataList){
      	//订单ID
    	  strbuff.append("'"+tp.getAmazonOrderId()+"',");
      }
	  List<String> ids = productImportMapper.findProductListIn(strbuff.substring(0,strbuff.length()-1));
	  for(TProductImport tp:dataList){
		  try{
			  if(ids.contains(tp.getAmazonOrderId())){
				  productImportMapper.update(tp);
			  }else{
				  productImportMapper.insert(tp);
			  }
		  }catch(Exception e){
			  log.error("导入异常数据："+tp.getAmazonOrderId()+";"+e.getMessage());
		  }
	  }
		
	}

	@Override
	public void importStk(List<TProductStk> dataList) {
		StringBuffer strbuff = new StringBuffer("");
		StringBuffer strbuff2 = new StringBuffer("");
	      for(TProductStk tp:dataList){
	    	  if(tp.getWarehouseConditionCode().equals("SELLABLE")){
	    		  strbuff.append("'"+tp.getSellerSku()+"',");
	    	  }else if(tp.getWarehouseConditionCode().equals("UNSELLABLE")){
	    		  strbuff2.append("'"+tp.getSellerSku()+"',"); 
	    	  }
	    	  
	      }
		  List<String> ids = productStkMapper.findProductBySkuStatus(strbuff.substring(0,strbuff.length()-1),"SELLABLE");
		  List<String> ids2 = productStkMapper.findProductBySkuStatus(strbuff2.substring(0,strbuff2.length()-1),"UNSELLABLE");
		  for(TProductStk tp:dataList){
			  if(ids.contains(tp.getSellerSku())){
				  productStkMapper.update(tp);
			  }else if(ids2.contains(tp.getSellerSku())){
				  productStkMapper.update(tp);
			  }else{
				  productStkMapper.insert(tp);
			  }
		  }
	}

	@Override
	public List<TProduct> findUserSaleQty(Map<String, Object> param) {
		return productVindicateMapper.findUserSaleQty(param);
	}

	@Override
	public List<TProduct> findTop10SKU(Map<String, Object> param) {
		return productVindicateMapper.findTop10SKU(param);
	}


}
