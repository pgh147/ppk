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
import cn.springboot.model.product.TOutcell;
import cn.springboot.service.product.OutCellService;

/** 
 * @Description 新接口实现类
 * @author pgh
 * @date Mar 16, 2019 5:19:24 PM  
 */
@Service("outCellService")
public class OutCellServiceImpl implements OutCellService {

    private static final Logger log = LoggerFactory.getLogger(OutCellServiceImpl.class);

    @Autowired
    private TOutcellMapper tOutcellMapper;
    
    @Transactional
    @Override
    public boolean addProduct(TOutcell tProduct) {
        if (tProduct != null) {
        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	tProduct.setBillNo("OUT"+billNo);
        	tProduct.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
        	tProduct.setCreateTime(Calendar.getInstance().getTime());
            int flag = tOutcellMapper.insertSelective(tProduct);
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
    public boolean editProduct(TOutcell news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = tOutcellMapper.updateByPrimaryKeySelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public boolean updateProduct(TOutcell news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
//            TProduct t = productMapper.findById(news.getId());
            int flag = tOutcellMapper.updateByPrimaryKeySelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public TOutcell findProductById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return tOutcellMapper.selectByPrimaryKey(id);//.findById(id);
    }

    // 默认数据库
    @Override
    public List<TOutcell> findProductByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return null;//tOutcellMapper.selectByPrimaryKey(keywords);
    }

    

    @Override
    public PageInfo<TOutcell> findProductByPage(Integer pageNum, TOutcell product) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TOutcell> news = tOutcellMapper.findProductByPage(product);
        // 用PageInfo对结果进行包装
        PageInfo<TOutcell> page = new PageInfo<TOutcell>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

	@Override
	public boolean deleteProduct(String id) {
		TOutcell pro = tOutcellMapper.selectByPrimaryKey(id);
		if(null == pro){
			return false;
		}
		int num = tOutcellMapper.deleteByPrimaryKey(id);
		if(num <= 0){
			return false;
		}
		return true;
	}

	@Override
	public int importOutCell(List<TOutcell> list) {
		int i = 0;
		for(TOutcell tProduct:list){
			String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	tProduct.setBillNo("OUT"+billNo+i);
        	tProduct.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
        	tProduct.setCreateTime(Calendar.getInstance().getTime());
            int flag = tOutcellMapper.insertSelective(tProduct);
            i++;
		}
		return i;
	}

}
