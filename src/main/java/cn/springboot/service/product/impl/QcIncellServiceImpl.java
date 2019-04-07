package cn.springboot.service.product.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import cn.springboot.mapper.product.TOutcellMapper;
import cn.springboot.mapper.product.TQcIncellMapper;
import cn.springboot.model.product.TOutcell;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.service.product.QcIncellService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("qcIncellService")
public class QcIncellServiceImpl implements QcIncellService {

    private static final Logger log = LoggerFactory.getLogger(QcIncellServiceImpl.class);

    @Autowired
    private TQcIncellMapper tQcIncellMapper;
    
    @Autowired
    private TOutcellMapper tOutcellMapper;

    @Transactional
    @Override
    public boolean addProduct(TQcIncell tProduct) {
        if (tProduct != null) {
        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	tProduct.setBillNo("QCI"+billNo);
        	tProduct.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
        	tProduct.setCreateTime(Calendar.getInstance().getTime());
            int flag = tQcIncellMapper.insertSelective(tProduct);
            
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
    public boolean editProduct(TQcIncell news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = tQcIncellMapper.updateByPrimaryKeySelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    
    @Transactional
    @Override
    public boolean updateProduct(TQcIncell news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
//            TProduct t = productMapper.findById(news.getId());
            int flag = tQcIncellMapper.updateByPrimaryKeySelective(news);
            //审核后同步出库
            if(news.getProductStatus() == 20 ){
            	TQcIncell inCell = tQcIncellMapper.selectByPrimaryKey(news.getId());
            	TOutcell tProduct = tOutcellMapper.selectByProductNo(inCell.getProductNo());
            	if(null == tProduct){
            		String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
            		TOutcell tOutcell = new TOutcell();
            		tOutcell.setBillNo("OUT"+billNo);
            		tOutcell.setProductNo(inCell.getProductNo());
            		tOutcell.setSurplusQty(String.valueOf(inCell.getIncellQty()));
            		tOutcell.setUserNo(inCell.getUserNo());
            		tOutcell.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
            		tOutcell.setCreateTime(Calendar.getInstance().getTime());
            		tOutcellMapper.insertSelective(tOutcell);
            	}else{
            		TOutcell tOut = new TOutcell();
            		tOut.setId(tProduct.getId());
            		tOut.setSurplusQty(String.valueOf(Integer.parseInt(tProduct.getSurplusQty())+inCell.getIncellQty()));
            		tOutcellMapper.updateByPrimaryKeySelective(tOut);
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
    public TQcIncell findProductById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return tQcIncellMapper.selectByPrimaryKey(id);//productMapper.findById(id);
    }

    // 默认数据库
    @Override
    public List<TQcIncell> findProductByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return null;//tQcIncellMapper.findProductByKeywords(keywords);
    }

    

    @Override
    public PageInfo<TQcIncell> findProductByPage(Integer pageNum, TQcIncell product) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TQcIncell> news = tQcIncellMapper.findProductByPage(product);
        // 用PageInfo对结果进行包装
        PageInfo<TQcIncell> page = new PageInfo<TQcIncell>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

	@Override
	public boolean deleteProduct(String id) {
		TQcIncell pro = tQcIncellMapper.selectByPrimaryKey(id);
		if(null == pro){
			return false;
		}
		int num = tQcIncellMapper.deleteByPrimaryKey(id);
		if(num <= 0){
			return false;
		}
		return false;
	}

}
