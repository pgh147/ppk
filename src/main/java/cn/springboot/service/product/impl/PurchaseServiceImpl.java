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
import cn.springboot.mapper.product.TPurchaseMapper;
import cn.springboot.mapper.product.TQcIncellMapper;
import cn.springboot.model.product.TPurchase;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.service.product.PurchaseService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private TPurchaseMapper tPurchaseMapper;
    @Autowired
    private TQcIncellMapper tQcIncellMapper;
    
    @Transactional
    @Override
    public boolean addProduct(TPurchase tPurchase) {
        if (tPurchase != null) {
        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	tPurchase.setBillNo("PE"+billNo);;
        	tPurchase.setId(FactoryAboutKey.getPK(TableEnum.T_PURCHASE));
//        	tPurchase.setCreateTime(Calendar.getInstance().getTime());
            int flag = tPurchaseMapper.insertSelective(tPurchase);//insert(tPurchase);//.insert(tPurchase);
            
            
            TQcIncell qcInCell = new TQcIncell();
            qcInCell.setProductNo(tPurchase.getProductNo());
            qcInCell.setPurchaseNo("PE"+billNo);
            qcInCell.setBillNo("QCI"+billNo);
            qcInCell.setUserNo(null != tPurchase.getQcIncellNo()?tPurchase.getQcIncellNo():tPurchase.getUserNo());
            qcInCell.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
//            qcInCell.setCreateTime(Calendar.getInstance().getTime());
            tQcIncellMapper.insertSelective(qcInCell);
            
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public boolean editProduct(TPurchase news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = tPurchaseMapper.updateByPrimaryKeySelective(news);//.updateByPrimaryKey(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public boolean updateProduct(TPurchase news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = tPurchaseMapper.updateByPrimaryKeySelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public TPurchase findProductById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return tPurchaseMapper.selectByPrimaryKey(id);
    }

    // 默认数据库
    @Override
    public List<TPurchase> findProductByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return null;//tPurchaseMapper.sel
    }

    

    @Override
    public PageInfo<TPurchase> findProductByPage(Integer pageNum, TPurchase product) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TPurchase> news = tPurchaseMapper.findProductByPage(product);
        // 用PageInfo对结果进行包装
        PageInfo<TPurchase> page = new PageInfo<TPurchase>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

	@Override
	public boolean deleteProduct(String id) {
		TPurchase pro = tPurchaseMapper.selectByPrimaryKey(id);
		if(null == pro){
			return false;
		}
		int num = tPurchaseMapper.deleteByPrimaryKey(id);
		if(num <= 0){
			return false;
		}
		return false;
	}

	@Transactional
	@Override
	public int importPurchase(List<TPurchase> list) {
		int i = 0;
		for(TPurchase tPurchase:list){
        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	tPurchase.setBillNo("PE"+billNo+i);;
        	tPurchase.setId(FactoryAboutKey.getPK(TableEnum.T_PURCHASE));
            tPurchaseMapper.insertSelective(tPurchase);//insert(tPurchase);//.insert(tPurchase);
            TQcIncell qcInCell = new TQcIncell();
            qcInCell.setProductNo(tPurchase.getProductNo());
            qcInCell.setPurchaseNo("PE"+billNo+i);
            qcInCell.setBillNo("QCI"+billNo+i);
            qcInCell.setUserNo(null != tPurchase.getQcIncellNo()?tPurchase.getQcIncellNo():tPurchase.getUserNo());
            qcInCell.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
            tQcIncellMapper.insertSelective(qcInCell);
            i++;
		            
		}
		return i;
	}

}
