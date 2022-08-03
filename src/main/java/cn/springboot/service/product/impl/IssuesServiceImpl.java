package cn.springboot.service.product.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.common.constants.Constants;
import cn.springboot.config.db.pk.FactoryAboutKey;
import cn.springboot.config.db.pk.TableEnum;
import cn.springboot.mapper.product.TIssuesMapper;
import cn.springboot.model.product.TIssues;
import cn.springboot.service.product.IssuesService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("issuesService")
public class IssuesServiceImpl implements IssuesService {

    private static final Logger log = LoggerFactory.getLogger(IssuesServiceImpl.class);

    @Autowired
    private TIssuesMapper issuesMapper;


//    @Transactional
//    @Override
//    public boolean addProduct(TQcIncell tProduct) {
//        if (tProduct != null) {
//        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
//        	tProduct.setBillNo("QCI"+billNo);
//        	tProduct.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
//        	tProduct.setCreateTime(Calendar.getInstance().getTime());
//            int flag = tQcIncellMapper.insertSelective(tProduct);
//            
////            if (StringUtils.equals(tProduct.getTitle(), "a"))
////                throw new BusinessException("001", "测试事务回溯");
//            if (flag == 1)
//                return true;
//            else
//                return false;
//        } else
//            return false;
//    }
//
//    @Override
//    public boolean editProduct(TQcIncell news) {
//        if (news != null && StringUtils.isNotBlank(news.getId())) {
//            int flag = tQcIncellMapper.updateByPrimaryKeySelective(news);
//            if (flag == 1)
//                return true;
//            else
//                return false;
//        } else
//            return false;
//    }
//    
//    @Transactional
//    @Override
//    public boolean updateProduct(TQcIncell news) {
//        if (news != null && StringUtils.isNotBlank(news.getId())) {
////            TProduct t = productMapper.findById(news.getId());
//            int flag = tQcIncellMapper.updateByPrimaryKeySelective(news);
////            //审核后同步出库
//            if(news.getProductStatus() == 20 ){
//            	TQcIncell inCell = tQcIncellMapper.selectByPrimaryKey(news.getId());
//            	TOutcell tProduct = tOutcellMapper.selectByProductNo(inCell.getProductNo());
//            	if(null == tProduct){
//            		String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
//            		TOutcell tOutcell = new TOutcell();
//            		tOutcell.setBillNo("OUT"+billNo);
//            		tOutcell.setProductNo(inCell.getProductNo());
//            		tOutcell.setSurplusQty(inCell.getIncellQty());
//            		tOutcell.setUsQty(inCell.getUsQty());
//            		tOutcell.setUkQty(inCell.getUkQty());
//            		tOutcell.setCaQty(inCell.getCaQty());
//            		tOutcell.setUserNo(inCell.getUserNo());
//            		tOutcell.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
//            		tOutcell.setCreateTime(Calendar.getInstance().getTime());
//            		tOutcellMapper.insertSelective(tOutcell);
//            	}else{
//            		TOutcell tOut = new TOutcell();
//            		tOut.setId(tProduct.getId());
//            		tOut.setSurplusQty(tProduct.getSurplusQty()+inCell.getIncellQty());
//            		tOut.setUsQty(tProduct.getUsQty()+inCell.getUsQty());
//            		tOut.setUkQty(tProduct.getUkQty()+inCell.getUkQty());
//            		tOut.setCaQty(tProduct.getCaQty()+inCell.getCaQty());
//            		tOutcellMapper.updateByPrimaryKeySelective(tOut);
//            	}
//            }
//
//            if (flag == 1)
//                return true;
//            else
//                return false;
//        } else
//            return false;
//    }
//    
//    
//    @Override
//    public TQcIncell findProductById(String id) {
//        if (StringUtils.isBlank(id))
//            return null;
//        else
//            return tQcIncellMapper.selectByPrimaryKey(id);//productMapper.findById(id);
//    }
//
//    // 默认数据库
//    @Override
//    public List<TQcIncell> findProductByKeywords(String keywords) {
//        log.info("# 查询默认数据库");
//        return null;//tQcIncellMapper.findProductByKeywords(keywords);
//    }
//
//    
//
//    @Override
//    public PageInfo<TQcIncell> findProductByPage(Integer pageNum, TQcIncell product) {
//        // request: url?pageNum=1&pageSize=10
//        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
//        if (pageNum == null)
//            pageNum = 1;
//        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
//        // 紧跟着的第一个select方法会被分页
//        List<TQcIncell> news = tQcIncellMapper.findProductByPage(product);
//        // 用PageInfo对结果进行包装
//        PageInfo<TQcIncell> page = new PageInfo<TQcIncell>(news);
//        // 测试PageInfo全部属性
//        // PageInfo包含了非常全面的分页属性
//        log.info("# 查询默认数据库 page.toString()={}", page.toString());
//        return page;
//    }
    @Transactional
	@Override
	public boolean deleteProduct(String id) {
		TIssues pro = issuesMapper.selectByPrimaryKey(id);
		if(null == pro){
			return false;
		}
		int num = issuesMapper.deleteByPrimaryKey(id);
		if(num <= 0){
			return false;
		}
		return false;
	}
    @Transactional
	@Override
	public boolean batchDeleteProduct(List<String> ids) {
		StrBuilder str = new StrBuilder();
		for(String s:ids){
			str.append("'"+s+"',");
		}
//		issuesMapper.deleteByIds(str.substring(0,str.length()-1));
		return false;
	}
    @Transactional
	@Override
	public boolean addProduct(TIssues news) {
        if (news != null) {
        	news.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
        	news.setCreateTime(Calendar.getInstance().getTime());
            int flag = issuesMapper.insertSelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
	}
	 @Transactional
	@Override
	public boolean editProduct(TIssues news) {
	        if (news != null && StringUtils.isNotBlank(news.getId())) {
	            int flag = issuesMapper.updateByPrimaryKeySelective(news);
	            if (flag == 1)
	                return true;
	            else
	                return false;
	        } else
	            return false;
	}
	 @Transactional
	@Override
	public boolean updateProduct(TIssues news) {
		 if (news != null && StringUtils.isNotBlank(news.getId())) {
           int flag = issuesMapper.updateByPrimaryKeySelective(news);
           if (flag == 1)
               return true;
           else
               return false;
       } else
           return false;
	}

	@Override
	public TIssues findProductById(String newsId) {

		 if (StringUtils.isBlank(newsId))
	            return null;
	        else
	            return issuesMapper.selectByPrimaryKey(newsId);//productMapper.findById(id);
	}

	@Override
	public PageInfo<TIssues> findProductByPage(Integer pageNum, TIssues keywords) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TIssues> news = issuesMapper.findProductByPage(keywords);
        // 用PageInfo对结果进行包装
        PageInfo<TIssues> page = new PageInfo<TIssues>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;			
		
	}
	@Override
	public List<TIssues> findProductByKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
