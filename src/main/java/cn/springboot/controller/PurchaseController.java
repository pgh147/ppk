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
import cn.springboot.model.product.TPurchase;
import cn.springboot.service.product.PurchaseService;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);
@Autowired
private PurchaseService purchaseService;
    
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TPurchase news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
        boolean flag = purchaseService.addProduct(news);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }
    /**
     * @Description ajax开发上传产品修改
     * @param news
     * @return
     */
    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@RequestBody  TPurchase news) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
        boolean flag = purchaseService.editProduct(news);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "修改成功");
        } else {
            result.put("status", "0");
            result.put("msg", "修改失败");
        }
        return result;
    }
    
    /**
     * @Description ajax开发上传产品修改
     * @param news
     * @return
     */
    @RequestMapping(value = "/updateStatus.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TPurchase news) {
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
    		throw new BusinessException("1001", "无权限");
    	}
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
        boolean flag = purchaseService.updateProduct(news);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "修改成功");
        } else {
            result.put("status", "0");
            result.put("msg", "修改失败");
        }
        return result;
    }
    /**
     * @Description ajax list上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(@RequestBody TPurchase product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
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
    	PageInfo<TPurchase> page = purchaseService.findProductByPage(pageNum, product);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("page", page);
        result.put("flag", isAdmin);
        return result;
    }
    
    /**
     * @Description ajax开发上传产品明细
     * @param news
     * @return
     */
    @RequestMapping(value = "/detail.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@RequestParam(value = "id") String id) {
    	TPurchase page = purchaseService.findProductById(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("page", page);
        return result;
    }
    
    /**
     * @Description ajax开发上传产品明细
     * @param news
     * @return
     */
    @RequestMapping(value = "/delete.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteById(@RequestParam(value = "id") String id) {
    	boolean flag = purchaseService.deleteProduct(id);
    	Map<String, Object> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "修改成功");
        } else {
            result.put("status", "0");
            result.put("msg", "修改失败");
        }
        return result;
    }
    
    
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
 	        ImportResolve<TPurchase> is = null;
 	        if(StringUtils.isEmpty(mainKeyStr)){
 	        	is = ExcelUtils.getData(req, colNames, mustArray, null, TPurchase.class, "","importFile");
 	        }else{
 	        	is = ExcelUtils.getData(req, colNames, mustArray, mainKeyStr.split(","), TPurchase.class, "","importFile");
 	        }
 	        Map<String, Object> resultMap = new HashMap<String,Object>();//getBaseSimplePageService().importData(dataList, user, reqData);
 	        if(StringUtils.isNotEmpty(is.getErrorMsg())){
 	        	resultMap.put("code", "9999");
 				resultMap.put("detail", is.getErrorMsg());
 				return resultMap;
 			}
 	       purchaseService.importPurchase(is.getDataList());
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
    
}
