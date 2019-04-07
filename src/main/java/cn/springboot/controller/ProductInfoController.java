package cn.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.springboot.common.exception.BusinessException;
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TProduct;
import cn.springboot.model.product.TProductInfo;
import cn.springboot.service.product.ProductInfoService;
import cn.springboot.service.product.ProductService;

@Controller
@RequestMapping(value = "/productInfo")
public class ProductInfoController {

    private static final Logger log = LoggerFactory.getLogger(ProductInfoController.class);
@Autowired
private ProductInfoService productInfoService;
@Autowired
private ProductService productService;

/**
 * 产品销售量页面
 * @return
 */
@RequestMapping(value = "/page", method = RequestMethod.GET)
String showSalesVolume() {
    return "view/material/productDetail/productDetail";
}
    
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TProductInfo news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
        boolean flag = productInfoService.addProduct(news);
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
    public Map<String, String> edit(@RequestBody  TProductInfo news) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
        boolean flag = productInfoService.editProduct(news);
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
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TProductInfo news) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();

    	news.setUpdater(principal.getUser().getUsername());
        boolean flag = productInfoService.updateProduct(news);
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
     * @Description ajax开发上传产品明细
     * @param news
     * @return
     */
    @RequestMapping(value = "/detail.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@RequestParam(value = "id") String id) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
    	List<TProduct> products= productService.findProductByKeywords(id);
    	if(!isAdmin && null != products && products.size() > 0 && ! products.get(0).getUserNo().equals(principal.getUser().getUsername())){
    		throw new BusinessException("1001", "无权限");
    	}
    	TProductInfo newsId = new TProductInfo();
    	newsId.setUserNo(principal.getUser().getUsername());
    	newsId.setProductNo(id);
    	TProductInfo page = productInfoService.findProductByPInfo(newsId);//.findProductById(id);
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
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    	}
    	List<TProduct> products= productService.findProductByKeywords(id);
    	if(!isAdmin && null != products && products.size() > 0 && ! products.get(0).getUserNo().equals(principal.getUser().getUsername())){
    		throw new BusinessException("1001", "无权限");
    	}
    	boolean flag = productInfoService.deleteProduct(id);
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
    
}
