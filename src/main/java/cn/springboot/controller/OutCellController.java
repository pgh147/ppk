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

import com.belle.bf.common.domain.search.Searchable;
import com.belle.bf.common.utils.CommonUtils;
import com.belle.bf.common.vo.ExportRequest;
import com.belle.bf.common.vo.SystemUser;
import com.belle.bf.common.web.bind.annotation.CurrentUser;
import com.belle.bf.common.web.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import com.sun.tools.internal.ws.processor.model.Parameter;
import com.sun.tools.internal.ws.processor.model.Request;

import cn.springboot.common.exception.BusinessException;
import cn.springboot.common.exception.MyselfMsgException;
import cn.springboot.common.util.ExcelUtils;
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.model.ImportRequest;
import cn.springboot.model.ImportResolve;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TOutcell;
import cn.springboot.model.product.TOutcellDtl;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.service.product.OutCellDtlService;
import cn.springboot.service.product.OutCellService;

@Controller
@RequestMapping(value = "/outCell")
public class OutCellController {

    private static final Logger log = LoggerFactory.getLogger(OutCellController.class);
@Autowired
private OutCellService outCellService;
@Autowired
private OutCellDtlService outCellDtlService;    
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TOutcellDtl news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
        boolean flag = outCellService.addProduct(news);
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
    public Map<String, String> edit(@RequestBody  TOutcellDtl news) {
    	Map<String, String> result = new HashMap<>();
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
    	if(!isAdmin && !news.getUserNo().equals(principal.getUser().getUsername())){
    		result.put("status", "0");
            result.put("msg", "无权限");
            return result;
    	}
        boolean flag = outCellService.editProduct(news);
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
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TOutcellDtl news) {
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
        boolean flag = outCellDtlService.updateProduct(news);
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
    public Map<String, Object> list(@RequestBody TOutcell product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
    	if(!isAdmin && !StringUtils.startsWith(principal.getUser().getUsername(), "p")){
    		product.setpUserNo("'"+principal.getUser().getUsername()+"','a13'");
    	}
//    	else if(StringUtils.isNotBlank(product.getUserNo())){
//    		product.setUserNo("'"+product.getUserNo()+"'");
//    	}
    	PageInfo<TOutcell> page = outCellService.findProductByPage(pageNum, product);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("page", page);
        result.put("flag", isAdmin);
        return result;
    }
    /**
     * @Description ajax list上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/selectByNo.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectByNo(@RequestParam(value = "billNo", required = true) String billNo) {
    	List<TOutcellDtl> list = outCellDtlService.findProductByKeywords(billNo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
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
    	TOutcell page = outCellService.findProductById(id);
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
    	Map<String, Object> result = new HashMap<>();
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
    		TOutcell pro = outCellService.findProductById(id);
    		if(null == pro || !pro.getUserNo().equals(principal.getUser().getUsername())){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    	}
    	boolean flag = outCellService.deleteProduct(id);
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
 	        ImportResolve<TOutcellDtl> is = null;
 	        if(StringUtils.isEmpty(mainKeyStr)){
 	        	is = ExcelUtils.getData(req, colNames, mustArray, null, TOutcellDtl.class, "","importFile");
 	        }else{
 	        	is = ExcelUtils.getData(req, colNames, mustArray, mainKeyStr.split(","), TOutcellDtl.class, "","importFile");
 	        }
 	        Map<String, Object> resultMap = new HashMap<String,Object>();//getBaseSimplePageService().importData(dataList, user, reqData);
 	        if(StringUtils.isNotEmpty(is.getErrorMsg())){
 	        	resultMap.put("code", "9999");
 				resultMap.put("detail", is.getErrorMsg());
 				return resultMap;
 			}
 	       outCellService.importOutCell(is.getDataList());
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
	 * 导出
	 * @param searchable 通用查询接口
	 * @param user 当前用户
	 * @param req http请求
	 * @return 结果集map: flag
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/export.json")
	@ResponseBody
	public Map<String, Object> exportData(HttpServletRequest req, @RequestBody TOutcell product) throws Exception {
		
		PageInfo<TOutcell> page = outCellService.findProductByPage(0, product);

    	// 调用导出服务
		ExcelUtils.exportData( page.getList(),List<Map> exportColumns);
//		return resultMap;
	}
	
}
