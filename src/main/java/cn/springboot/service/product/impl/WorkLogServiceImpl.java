package cn.springboot.service.product.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.common.constants.Constants;
import cn.springboot.config.db.pk.FactoryAboutKey;
import cn.springboot.config.db.pk.TableEnum;
import cn.springboot.mapper.product.TWorkLogMapper;
import cn.springboot.model.product.TWorkLog;
import cn.springboot.service.product.WorkLogService;

@Service("workLogService")
public class WorkLogServiceImpl implements WorkLogService {
    private static final Logger log = LoggerFactory.getLogger(WorkLogServiceImpl.class);

    @Autowired
    private TWorkLogMapper workLogMapper;
	@Override
	public boolean addProduct(TWorkLog news) {
        if (news != null) {
        	news.setId(FactoryAboutKey.getPK(TableEnum.T_QC_INCELL));
        	news.setCreateTime(Calendar.getInstance().getTime());
            int flag = workLogMapper.insertSelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
	}

	@Override
	public boolean editProduct(TWorkLog news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = workLogMapper.updateByPrimaryKeySelective(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
	}

	@Override
	public boolean updateProduct(TWorkLog news) {
		 if (news != null && StringUtils.isNotBlank(news.getId())) {
	           int flag = workLogMapper.updateByPrimaryKeySelective(news);
	           if (flag == 1)
	               return true;
	           else
	               return false;
	       } else
	           return false;
	}

	@Override
	public boolean deleteProduct(String id) {
		TWorkLog pro = workLogMapper.selectByPrimaryKey(id);
		if(null == pro){
			return false;
		}
		int num = workLogMapper.deleteByPrimaryKey(id);
		if(num <= 0){
			return false;
		}
		return false;
	}

	@Override
	public boolean batchDeleteProduct(List<String> ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TWorkLog findProductById(String newsId) {
		 if (StringUtils.isBlank(newsId))
	            return null;
	        else
	            return workLogMapper.selectByPrimaryKey(newsId);
	}

	@Override
	public List<TWorkLog> findProductByKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<TWorkLog> findProductByPage(Integer pageNum, TWorkLog keywords) {
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        // 紧跟着的第一个select方法会被分页
        List<TWorkLog> news = workLogMapper.findProductByPage(keywords);
        // 用PageInfo对结果进行包装
        PageInfo<TWorkLog> page = new PageInfo<TWorkLog>(news);
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;	
	}

}
