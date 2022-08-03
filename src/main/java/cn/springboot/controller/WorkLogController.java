package cn.springboot.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.mapper.product.TWorkLogMapper;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.model.product.TWorkLog;
import cn.springboot.service.product.ExportBigExcelService;
import cn.springboot.service.product.WorkLogService;

@Controller
@RequestMapping(value = "/workLog")
public class WorkLogController {

    private static final Logger log = LoggerFactory.getLogger(WorkLogController.class);
@Autowired
private WorkLogService workLogService;
@Autowired
private TWorkLogMapper workLogMpper;
@Autowired
private ExportBigExcelService excelService; 
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TWorkLog news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
    	news.setUserNo(principal.getUser().getUsername());
        boolean flag = workLogService.addProduct(news);
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
    public Map<String, String> edit(@RequestBody  TWorkLog news) {
    	Map<String, String> result = new HashMap<>();
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
//    	List<Role> roles = principal.getRoles();
//    	boolean isAdmin = false;
//    	for(Role role:roles){
//    		if(role.getName().equals("超级管理员")){
//    			isAdmin = true;
//    			break;
//    		}
//    	}
//    	if(!isAdmin && !news.getUserNo().equals(principal.getUser().getUsername())){
//    		result.put("status", "0");
//            result.put("msg", "无权限");
//            return result;
//    	}
//    	if(!isAdmin){
//    		news.setUsQty(null);
//    		news.setUkQty(null);
//    		news.setCaQty(null);
//    	}
        boolean flag = workLogService.editProduct(news);
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
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TWorkLog news) {
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
        boolean flag = workLogService.updateProduct(news);
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
    public Map<String, Object> list(@RequestBody TWorkLog product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
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
    		product.setpUserNo(principal.getUser().getUsername());
    		product.setStartPUserNo(principal.getUser().getUsername().substring(0,1));
    	}
//    	else if(StringUtils.isNotBlank(product.getUserNo())){
//    		product.setUserNo("'"+product.getUserNo()+"'");
//    	}
    	PageInfo<TWorkLog> page = workLogService.findProductByPage(pageNum, product);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("page", page);
        result.put("flag", isAdmin);
        result.put("userNo", principal.getUser().getUsername());
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
    	TWorkLog page = workLogService.findProductById(id);
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
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
    		TWorkLog pro = workLogService.findProductById(id);
    		if(null == pro || !pro.getUserNo().equals(principal.getUser().getUsername())){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    	}
    	boolean flag = workLogService.deleteProduct(id);
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
     * @Description ajax开发上传产品明细
     * @param news
     * @return
     */
    @RequestMapping(value = "/batchDelete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> batchDeleteById(@RequestBody List<String> ids) {
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
    		TWorkLog pro = workLogService.findProductById(ids.get(0));
    		if(null == pro || !pro.getUserNo().equals(principal.getUser().getUsername())){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    	}
    	boolean flag = workLogService.batchDeleteProduct(ids);
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
     * 大数据量导出
     * @param exportRequest 通用查询接口参数
     * @param req http请求
     * @return 结果集map: flag
     * @throws Exception 异常
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/big_simpleExport.json",method = RequestMethod.GET)
    @ResponseBody
    public String exportData(HttpServletRequest req,@RequestParam("userNo") String userNo,
    		@RequestParam("isRead") String isRead,HttpServletResponse response) throws Exception {
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
    		return "common/error";
    	}
    	TWorkLog product = new TWorkLog();
    	product.setUserNo(userNo);
    	if(StringUtils.isNoneBlank(isRead)){
    		
    		product.setIsRead(Integer.parseInt(isRead));
    	}
      List<TWorkLog> news = workLogMpper.findProductByPage( product);
      for(TWorkLog log:news){
    	  log.setProductName(log.getIsRead()==1?"未读":"已读");
      }
      List<Map> exportColumns = new ArrayList<>();;
      Map<String,String> map1 = new HashMap<String,String>(); 
      Map<String,String> map2 = new HashMap<String,String>(); 
      Map<String,String> map3 = new HashMap<String,String>(); 
      Map<String,String> map4 = new HashMap<String,String>(); 
      
      map1.put("title", "员工编号");
      map1.put("field", "userNo");
      map2.put("title", "总结");
      map2.put("field", "workLog");
      map3.put("title", "上传日期");
      map3.put("field", "createTime");
      map4.put("title", "是否阅读");
      map4.put("field", "productName");
      
      exportColumns.add(map1);exportColumns.add(map2);exportColumns.add(map3);
      exportColumns.add(map4);
      	SXSSFWorkbook wb = null;
    	//响应到客户端
		try {
			wb = excelService.export(principal.getUser(), exportColumns, news);
			this.setResponseHeader(response, principal.getUser().getUsername()+DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")+".xlsx");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			log.error("导出错误"+e.getMessage());
		}finally{
			if(null != wb){
				wb.dispose();
			}
		}
		return "";
    }
    
    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
            	log.error("setResponseHeader"+e.getMessage());
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
        	log.error("setResponseHeader"+ex.getMessage());
        }
    }
}
