package cn.springboot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TPurchase;
import cn.springboot.model.product.TQcIncell;
import cn.springboot.service.product.QcIncellService;

@Controller
@RequestMapping(value = "/qcIncell")
public class QcIncellController {

    private static final Logger log = LoggerFactory.getLogger(QcIncellController.class);
@Autowired
private QcIncellService qcIncellService;
    
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TQcIncell news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
        boolean flag = qcIncellService.addProduct(news);
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
    public Map<String, String> edit(@RequestBody  TQcIncell news) {
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
        boolean flag = qcIncellService.editProduct(news);
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
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TQcIncell news) {
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
        boolean flag = qcIncellService.updateProduct(news);
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
    public Map<String, Object> list(@RequestBody TQcIncell product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
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
    		product.setpUserNo("'"+principal.getUser().getUsername()+"','a13'");
    	}
//    	else if(StringUtils.isNotBlank(product.getUserNo())){
//    		product.setUserNo("'"+product.getUserNo()+"'");
//    	}
    	PageInfo<TQcIncell> page = qcIncellService.findProductByPage(pageNum, product);
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
    	TQcIncell page = qcIncellService.findProductById(id);
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
    		TQcIncell pro = qcIncellService.findProductById(id);
    		if(null == pro || !pro.getUserNo().equals(principal.getUser().getUsername())){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    	}
    	boolean flag = qcIncellService.deleteProduct(id);
        if (flag) {
            result.put("status", "1");
            result.put("msg", "修改成功");
        } else {
            result.put("status", "0");
            result.put("msg", "修改失败");
        }
        return result;
    }
    
}
