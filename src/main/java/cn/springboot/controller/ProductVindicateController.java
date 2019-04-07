package cn.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.springboot.common.exception.BusinessException;
import cn.springboot.common.exception.MyselfMsgException;
import cn.springboot.common.util.ExcelUtils;
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.model.ImportRequest;
import cn.springboot.model.ImportResolve;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductImport;
import cn.springboot.model.product.TProductStk;
import cn.springboot.service.product.ProductService;
import cn.springboot.service.product.ProductVindicateService;

@Controller
@RequestMapping(value = "/product_vindicate")
public class ProductVindicateController {

    private static final Logger log = LoggerFactory.getLogger(ProductVindicateController.class);
@Autowired
private ProductService productService;

@Autowired
private ProductVindicateService productVindicateService;

    
    
    /**
	 * 导入
	 * @param req http请求
	 * @param user 当前用户
	 * @param reqData 保存请求的数据
	 * @return 结果集map: flag
	 * @throws Exception 异常
	 */
	@RequestMapping("/import.json")
	@ResponseBody
	public Map<String, Object> importData(HttpServletRequest req, 
			ImportRequest reqData) throws Exception {
		try{
	    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
	    	List<Role> roles = principal.getRoles();
	    	boolean isAdmin = false;
	    	for(Role role:roles){
	    		if(role.getName().equals("超级管理员")){
	    			isAdmin = true;
	    			break;
	    		}
	    	}
	    	if(!isAdmin){
	    		throw new BusinessException("1001", "无权限导入");
	    	}
			// 获取参数
			String colNamesStr = reqData.getColNames();
			String mustArrayStr =  reqData.getMustArray();
			if(StringUtils.isEmpty(colNamesStr) || StringUtils.isEmpty(mustArrayStr)){
				throw new MyselfMsgException("参数colNames与mustArray不能为空");
			}
			String mainKeyStr = reqData.getMainKey();
			
			// 解析excel
			String[] colNames = colNamesStr.split(",");
	        String[] mustArray = mustArrayStr.split(",");
	        if(colNames.length != mustArray.length){
	        	throw new MyselfMsgException("导入列colNames与是否必须mustArray的参数长度不一致");
	        }
	        ImportResolve<TProductImport> is = null;
	        if(StringUtils.isEmpty(mainKeyStr)){
	        	is = ExcelUtils.getData(req, colNames, mustArray, null, TProductImport.class, "","importFile");
	        }else{
	        	is = ExcelUtils.getData(req, colNames, mustArray, mainKeyStr.split(","), TProductImport.class, "","importFile");
	        }
	        Map<String, Object> resultMap = new HashMap<String,Object>();//getBaseSimplePageService().importData(dataList, user, reqData);
	        if(StringUtils.isNotEmpty(is.getErrorMsg())){
	        	resultMap.put("code", "9999");
				resultMap.put("detail", is.getErrorMsg());
				return resultMap;
			}
	        productVindicateService.importSku(is.getDataList());
	        resultMap.put("code", "0");
	        resultMap.put("status", "导入成功");
			return resultMap;
		}catch(Exception e){
			Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("code", "9999");
			resultMap.put("detail", e.getMessage());
			return resultMap;
		}
		
	}
    
	
	/**
	 * 导入
	 * @param req http请求
	 * @param user 当前用户
	 * @param reqData 保存请求的数据
	 * @return 结果集map: flag
	 * @throws Exception 异常
	 */
	@RequestMapping("/importStk.json")
	@ResponseBody
	public Map<String, Object> importStkData(HttpServletRequest req, 
			ImportRequest reqData) throws Exception {
		try{
	    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
	    	List<Role> roles = principal.getRoles();
	    	boolean isAdmin = false;
	    	for(Role role:roles){
	    		if(role.getName().equals("超级管理员")){
	    			isAdmin = true;
	    			break;
	    		}
	    	}
	    	if(!isAdmin){
	    		throw new BusinessException("1001", "无权限导入");
	    	}
			String mainKeyStr = reqData.getMainKey();
	        Map<String, Object> resultMap = new HashMap<String,Object>();//getBaseSimplePageService().importData(dataList, user, reqData);
        
     		String colNamesStr2 = reqData.getColNames();
     		String mustArrayStr2 =  reqData.getMustArray();
     		
     		// 解析excel
     		String[] colNames2 = colNamesStr2.split(",");
             String[] mustArray2 = mustArrayStr2.split(",");
             if(colNames2.length != mustArray2.length){
             	throw new MyselfMsgException("导入列colNames与是否必须mustArray的参数长度不一致");
             }
             ImportResolve<TProductStk> is2 = null;
             if(StringUtils.isEmpty(mainKeyStr)){
             	is2 = ExcelUtils.getData(req, colNames2, mustArray2, null, TProductStk.class, "","importFile");
             }else{
             	is2 = ExcelUtils.getData(req, colNames2, mustArray2, mainKeyStr.split(","), TProductStk.class, "","importFile");
             }
             if(StringUtils.isNotEmpty(is2.getErrorMsg())){
             	resultMap.put("code", "9999");
    			resultMap.put("detail", is2.getErrorMsg());
    			return resultMap;
     		}
             productVindicateService.importStk(is2.getDataList());
             resultMap.put("code", "0");
             resultMap.put("status", "导入成功");
             return resultMap;
		}catch(Exception e){
			Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("code", "9999");
			resultMap.put("detail", e.getMessage());
			return resultMap;
		}
	}
    
    
    /**
     * @Description ajax list上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(@RequestBody TProduct product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
    	if(!isAdmin){
    		product.setUserNo(principal.getUser().getUsername());
    	}
    	PageInfo<TProduct> page = productVindicateService.findProductByPage(pageNum, product);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("page", page);
        return result;
    }
    
    /**
     * @Description ajax list上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/findUserSaleQty.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findUserSaleQty( @RequestParam(value = "endDate", required = true) String endDate,@RequestParam(value = "startDate", required = true) String startDate) {
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("endDate", endDate);
    	param.put("startDate", startDate);
    	List<TProduct> list = productVindicateService.findUserSaleQty(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
        return result;
    }
    
    
    /**
     * @Description ajax list上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/findTop10SKU.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findTop10SKU( @RequestParam(value = "endDate", required = true) String endDate,@RequestParam(value = "startDate", required = true) String startDate) {
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("endDate", endDate);
    	param.put("startDate", startDate);
    	List<TProduct> list = productVindicateService.findTop10SKU(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
        return result;
    }
    

}
