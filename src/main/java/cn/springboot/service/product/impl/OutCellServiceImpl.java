package cn.springboot.service.product.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.mapper.product.TOutcellDtlMapper;
import cn.springboot.mapper.product.TOutcellMapper;
import cn.springboot.model.product.TOutcell;
import cn.springboot.model.product.TOutcellDtl;
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
    
    @Autowired
    private TOutcellDtlMapper tOutcellDtlMapper;
    
    @Transactional
    @Override
    public boolean addProduct(TOutcellDtl dtl) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	TOutcell tcell = tOutcellMapper.selectByProductNo(dtl.getProductNo());
        if (null == tcell) {
        	TOutcell cell = new TOutcell();
        	cell.setUserNo(principal.getUser().getUsername());
        	String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        	cell.setBillNo("OUT"+billNo);
        	cell.setSurplusQty(0);
        	cell.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
        	cell.setCreateTime(Calendar.getInstance().getTime());
            int flag = tOutcellMapper.insertSelective(cell);
            
            dtl.setBillNo(billNo);
            dtl.setUserNo(principal.getUser().getUsername());
            dtl.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
            dtl.setCreateTime(Calendar.getInstance().getTime());
            tOutcellDtlMapper.insertSelective(dtl);
//            if (StringUtils.equals(tProduct.getTitle(), "a"))
//                throw new BusinessException("001", "测试事务回溯");
                return true;
        } else{
        	dtl.setBillNo(tcell.getBillNo());
            dtl.setUserNo(principal.getUser().getUsername());
            dtl.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
            dtl.setCreateTime(Calendar.getInstance().getTime());
            tOutcellDtlMapper.insertSelective(dtl);
            return true;
        }
    }

    @Override
    public boolean editProduct(TOutcellDtl news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
        	news.setUserNo("admin");
            int flag = tOutcellDtlMapper.updateByPrimaryKeySelective(news);
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
        	news.setUserNo("admin");
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
//    @Override
//    public List<TOutcell> findProductByKeywords(String keywords) {
//        log.info("# 查询默认数据库");
//        return tOutcellMapper.selectByProductNo(keywords);//tOutcellMapper.selectByPrimaryKey(keywords);
//    }

    

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
	public int importOutCell(List<TOutcellDtl> list) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		Map<String,List<TOutcellDtl> > cells = new HashMap<String,List<TOutcellDtl>>();
		for(TOutcellDtl dtl:list){
			if(cells.containsKey(dtl.getProductNo())){
				cells.get(dtl.getProductNo()).add(dtl);
			}else{
				List<TOutcellDtl> dtls = new ArrayList<TOutcellDtl>();
				dtls.add(dtl);
				cells.put(dtl.getProductNo(), dtls);
			}
		}
		int i = 0;
		for(String productNo:cells.keySet()){
			TOutcell outcell = tOutcellMapper.selectByProductNo(productNo);
			if(null == outcell){
				outcell = new TOutcell();
				String billNo = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
				outcell.setBillNo("OUT"+billNo+i);
				outcell.setProductNo(productNo);
				if(!StringUtils.isNotBlank(cells.get(productNo).get(0).getUserNo())){
					outcell.setUserNo(cells.get(productNo).get(0).getUserNo());
				}else{
					outcell.setUserNo(principal.getUser().getUsername());
				}
				outcell.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
				outcell.setCreateTime(Calendar.getInstance().getTime());
				outcell.setSurplusQty(0);
				outcell.setUsQty(0);
				outcell.setUkQty(0);
				outcell.setCaQty(0);
				tOutcellMapper.insertSelective(outcell);
			}
			
			for(TOutcellDtl cdtl:cells.get(productNo)){
				cdtl.setBillNo(outcell.getBillNo());
				if(!StringUtils.isNotBlank(cdtl.getUserNo())){
					cdtl.setUserNo(principal.getUser().getUsername());
				}
				cdtl.setId(FactoryAboutKey.getPK(TableEnum.T_OUTCELL));
				cdtl.setCreateTime(Calendar.getInstance().getTime());
				tOutcellDtlMapper.insertSelective(cdtl);
				i++;
			}
		}
		return i;
	}

}
