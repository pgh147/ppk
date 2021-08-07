package cn.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.common.util.FileNameLengthLimitExceededException;
import cn.springboot.common.util.FileUploadUtils;

import com.github.pagehelper.PageInfo;
import cn.springboot.common.constants.Constants;
import cn.springboot.common.exception.BusinessException;
import cn.springboot.config.shiro.vo.PermissionVo;
import cn.springboot.config.shiro.vo.Principal;
import cn.springboot.mapper.product.ProductMapper;
import cn.springboot.model.auth.Role;
import cn.springboot.model.product.TProduct;
import cn.springboot.service.product.ExportBigExcelService;
import cn.springboot.service.product.ProductService;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ExportBigExcelService excelService;
	
    @Autowired
    private ProductMapper productMapper;
	// 最大上传大小 字节为单位
	private long maxSize = FileUploadUtils.DEFAULT_MAX_SIZE;
	// 允许的文件内容类型
	private String[] allowedExtension = { "jpg", "png", "gif","JPEG","PNG" };
    @Value("${uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/upload/page", method = RequestMethod.GET)
    String toPage(HttpServletRequest request, ModelMap map) {
        log.info("# loding view/sysconfig/setconfig ");
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(null == perVos || perVos.size() <= 0){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/upload/page");
        
        if(!hasPagePermission){
        	return "common/error";
        }
        return "view/material/cpkfsc/cpkfsc";
    }
    
    /**
     * 判断是否有访问权限
     * @param perVos
     * @param url
     * @return
     */
    private boolean checkHasPermission (List<PermissionVo> perVos,String url){
    	boolean hasPer = false;
    	for(PermissionVo vo : perVos){
        	if(null != vo.getUrl() && vo.getUrl().equals(url)){
        		hasPer = true;
        		break;
        	}else if(null != vo.getChildren() && vo.getChildren().size() > 0){
        		hasPer = checkHasPermission(vo.getChildren(),url);
        	}
        }
    	return hasPer;
    }
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     */
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
//    public Map<String, String> add(@ModelAttribute("newsForm") TProduct news) {
    public Map<String, String> add(@RequestBody TProduct news) {	
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
        boolean flag = productService.addProduct(news);
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
    @RequestMapping(value = "/getImg/{id}.json")
    public void getImg(@PathVariable String id,HttpServletResponse response){
    	String url = productService.findProductImgByNo(id);
    	File file = null;
    	if(StringUtils.isNotBlank(url)){
    		file = new File(url);
    		FileInputStream fis =null;
    		if(!file.exists()){
    			response.setStatus(404);
    			return ;
    		}
    		try {
				fis = new FileInputStream(file);
				final byte[] bis = new byte[1024];
				while(fis.read(bis) > 0){
					response.getOutputStream().write(bis);
				}
			} catch (Exception e) {
//				response.setStatus(404);
			} finally{
				if(null != fis){
					try {
						fis.close();
					} catch (IOException e) {
						fis = null;
					}
				}
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			}
    	}else{
    		response.setStatus(404);
    	}
    }
    /**
     * @Description ajax开发上传产品
     * @param news
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/upload.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> upload(@RequestParam("file") MultipartFile file,TProduct news,HttpServletRequest request) throws Exception {	
    	
    	int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
					FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}
		
		File folder = new File(getAppFolder(request));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		FileUploadUtils.assertAllowed(file, allowedExtension, maxSize);
		String filenamePath = FileUploadUtils.extractFilename(file, getAppFolder(request), false);

		File desc = new File(filenamePath);
		if (!desc.exists()) {
			desc.createNewFile();
		}
		file.transferTo(desc);
		
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
    	news.setProductImgData(filenamePath);
    	boolean flag = false;
    	try {
    		flag = productService.addProduct(news);
		} catch (Exception e) {
			desc.delete();
		}
        
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
	 * 目录
	 * 
	 * @param request
	 * @return
	 */
	protected String getAppFolder(HttpServletRequest request) {
//		String flag = request.getParameter("flag");
//			if(StringUtils.isNotBlank(flag)){
//				return FileUploadUtils.extractUploadDir(request) + flag;
//			}else{
//				return FileUploadUtils.extractUploadDir(request) + "appfolder";
//			}
		return uploadPath;

	}
    /**
     * @Description ajax开发上传产品修改
     * @param news
     * @return
     */
    @RequestMapping(value = "/editUp.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> editUp(@RequestParam("file") MultipartFile file,TProduct news,HttpServletRequest request) throws Exception {	
    	Map<String, String> result = new HashMap<>();
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	news.setCreater(principal.getUser().getUsername());
    	if(news.getProductStatus() == 20){
    		result.put("status", "0");
            result.put("msg", "修改失败");
            return result;
    	}
    	
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false,isDevAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    		if(role.getName().equals("开发leader")){
    			isDevAdmin = true;
    			break;
    		}
    	}
    	if(!isAdmin &&!isDevAdmin && !news.getUserNo().equals(principal.getUser().getUsername())){
    		result.put("status", "0");
            result.put("msg", "无权限");
            return result;
    	}

    	if(null != file){
    		int fileNamelength = file.getOriginalFilename().length();
    		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
    			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
    					FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
    		}
    		
    		File folder = new File(getAppFolder(request));
    		if (!folder.exists()) {
    			folder.mkdirs();
    		}
    		FileUploadUtils.assertAllowed(file, allowedExtension, maxSize);
    		String filenamePath = FileUploadUtils.extractFilename(file, getAppFolder(request), false);

    		File desc = new File(filenamePath);
    		if (!desc.exists()) {
    			desc.createNewFile();
    		}
    		file.transferTo(desc);
    		news.setProductImgData(filenamePath);
    		news.setIsUpdateImg(true);
    	}else{
    		news.setIsUpdateImg(false);
    	}
    	
    	boolean flag = false;
    	flag = productService.editProduct(news);
        
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
    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@RequestBody  TProduct news) {
    	Map<String, String> result = new HashMap<>();
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//    	news.setUpdateTime(new Date());
    	news.setUpdater(principal.getUser().getUsername());
    	if(news.getProductStatus() == 20){
    		result.put("status", "0");
            result.put("msg", "修改失败");
            return result;
    	}
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false,isDevAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    		if(role.getName().equals("开发leader")){
    			isDevAdmin = true;
    			break;
    		}
    	}
    	if(!isAdmin &&!isDevAdmin && !news.getUserNo().equals(principal.getUser().getUsername())){
    		result.put("status", "0");
            result.put("msg", "无权限");
            return result;
    	}
    	boolean flag = productService.editProduct(news);
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
    public Map<String, String> updateStatus(@ModelAttribute("newsForm") TProduct news) {
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
        boolean flag = productService.updateProduct(news);
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
    public Map<String, Object> list(@RequestBody TProduct product, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false,isDevAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员")){
    			isAdmin = true;
    			break;
    		}
    		if(role.getName().equals("开发leader")){
    			isDevAdmin = true;
    			break;
    		}
    	}
    	// 2021-0807 修改a01能看所有
    	if(!isAdmin &&!isDevAdmin){
    		product.setpUserNo("'"+principal.getUser().getUsername()+"','a13'");
    	}
//    	if(StringUtils.isNotBlank(product.getUserNo())){
//    		product.setUserNo("'"+product.getUserNo()+"'");
//    	}
    	PageInfo<TProduct> page = productService.findProductByPage(pageNum, product);
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
    	TProduct page = productService.findProductById(id);
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
    		TProduct pro = productService.findProductById(id);
    		if(null == pro ){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    		
//    		if(principal.getUser().getUsername().equals("a01") ){
//    			if(pro.getUserNo().indexOf("a0") < 0){    				
//    				result.put("status", "0");
//    				result.put("msg", "删除失败");
//    				return result;
//    			}
//    		}else 
    			if(!pro.getUserNo().equals(principal.getUser().getUsername())){
    			result.put("status", "0");
                result.put("msg", "删除失败");
    			return result;
    		}
    	}
    	boolean flag = productService.deleteProduct(id);
    	
        if (flag) {
            result.put("status", "1");
            result.put("msg", "删除成功");
        } else {
            result.put("status", "0");
            result.put("msg", "删除失败");
        }
        return result;
    }
    /**
     * 产品维护页面
     * @return
     */
    @RequestMapping(value = "/vindicate/page", method = RequestMethod.GET)
    String showVindicate(HttpServletRequest request, ModelMap map) {
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(null == perVos || perVos.size() <= 0){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/vindicate/page");
        
        if(!hasPagePermission){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        return "view/material/vindicate/vindicate";
    }
    
    
    /**
     * 产品销售量页面
     * @return
     */
    @RequestMapping(value = "/salesvolume/page", method = RequestMethod.GET)
    String showSalesVolume(HttpServletRequest request, ModelMap map) {
        log.info("# loding view/sysconfig/setconfig ");
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(null == perVos || perVos.size() <= 0){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/salesvolume/page");
        
        if(!hasPagePermission){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        return "view/material/salesvolume/salesvolume";
    }

    /**
     * 产品采购页面
     * @return
     */
    @RequestMapping(value = "/purchase/page", method = RequestMethod.GET)
    String showPurchase(HttpServletRequest request, ModelMap map) {
        log.info("# loding view/sysconfig/setconfig ");
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(null == perVos || perVos.size() <= 0){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/purchase/page");
        
        if(!hasPagePermission){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        return "view/material/purchase/purchase";
    }
    
    /**
     * 产品质检入库页面
     * @return
     */
    @RequestMapping(value = "/qcInCell/page", method = RequestMethod.GET)
    String showQcInCell(HttpServletRequest request, ModelMap map) {
        log.info("# loding view/sysconfig/setconfig ");
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean isAdmin = false;
    	for(Role role:roles){
    		if(role.getName().equals("超级管理员") ){
    			isAdmin = true;
    			break;
    		}
    		
    	}
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(!isAdmin && (null == perVos || perVos.size() <= 0)){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/qcInCell/page");
        
        if(!isAdmin && !hasPagePermission){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }

        return "view/material/qcInCell/qcInCell";
    }
    
    /**
     * 产品出库页面
     * @return
     */
    @RequestMapping(value = "/outCell/page", method = RequestMethod.GET)
    String showOutCell(HttpServletRequest request, ModelMap map) {
        log.info("# loding view/sysconfig/setconfig ");
        List<PermissionVo> perVos = (List<PermissionVo>)SecurityUtils.getSubject().getSession().getAttribute(Constants.PERMISSION_SESSION);
        if(null == perVos || perVos.size() <= 0){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        boolean hasPagePermission = checkHasPermission(perVos,"product/outCell/page");
        
        if(!hasPagePermission){
        	map.put("err", "没有访问权限");
        	return "common/error";
        }
        return "view/material/outCell/outCell";
    }
    
    /**
     * 产品明细页面
     * @return
     */
    @RequestMapping(value = "/detail/page", method = RequestMethod.GET)
    String showDetail(HttpServletRequest request, ModelMap map) {
    	Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
    	List<Role> roles = principal.getRoles();
    	boolean hasPer = false;
    	for(Role role:roles){
    		if(role.getName().indexOf("开发") >= 0){
    			hasPer = true;
    			break;
    		}else if(role.getName().equals("超级管理员") ){
    			hasPer = true;
    			break;
    		}
    		
    	}
    	if(!hasPer){
    		map.put("err", "没有访问权限");
    		return "common/error";
    		
    	}
        return "view/material/productDetail/productDetail";
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
    public String exportData(HttpServletRequest req,@RequestParam("productNo") String productNo,@RequestParam("userNo") String userNo,
    		@RequestParam("productName") String productName,@RequestParam("productStatus") String productStatus,@RequestParam("uploadAccount") String uploadAccount,HttpServletResponse response) throws Exception {
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
    	TProduct product = new TProduct();
    	product.setProductName(productName);
    	product.setProductNo(productNo);
    	product.setUserNo(userNo);
    	if(StringUtils.isNotEmpty(productStatus)){
    		product.setProductStatus(Integer.parseInt(productStatus));
    	}
    	product.setUploadAccount(uploadAccount);
      List<TProduct> news = productMapper.findProductByPage(product);
      List<Map> exportColumns = new ArrayList<>();;
      Map<String,String> map1 = new HashMap<String,String>(); 
      Map<String,String> map2 = new HashMap<String,String>(); 
      Map<String,String> map3 = new HashMap<String,String>(); 
      Map<String,String> map4 = new HashMap<String,String>(); 
      Map<String,String> map5 = new HashMap<String,String>(); 
      Map<String,String> map6 = new HashMap<String,String>(); 
      Map<String,String> map7 = new HashMap<String,String>(); 
      Map<String,String> map8 = new HashMap<String,String>(); 
      Map<String,String> map9 = new HashMap<String,String>(); 
      Map<String,String> map10 = new HashMap<String,String>();
      Map<String,String> map11 = new HashMap<String,String>();
      Map<String,String> map12 = new HashMap<String,String>();
      
      Map<String,String> map13 = new HashMap<String,String>();
      Map<String,String> map14 = new HashMap<String,String>();
      Map<String,String> map15 = new HashMap<String,String>();
      Map<String,String> map16 = new HashMap<String,String>();
      map1.put("title", "产品编号");
      map1.put("field", "productNo");
      map2.put("title", "英文标题");
      map2.put("field", "enTitle");
      map3.put("title", "卖点1");
      map3.put("field", "productSelling1");
      map4.put("title", "卖点2");
      map4.put("field", "productSelling2");
      map5.put("title", "卖点3");
      map5.put("field", "productSelling3");
      map6.put("title", "卖点4");
      map6.put("field", "productSelling4");
      map7.put("title", "卖点5");
      map7.put("field", "productSelling5");
      map8.put("title", "描述");
      map8.put("field", "descript");
      map9.put("title", "广告词");
      map9.put("field", "message");
      map10.put("title", "searchTerm");
      map10.put("field", "searchTerm");
      map11.put("title", "分类");
      map11.put("field", "classify");   
      map12.put("title", "售价");
      map12.put("field", "salePrice");  
      
      map13.put("title", "产品尺寸");
      map13.put("field", "productSize");
      map14.put("title", "外包装尺寸");
      map14.put("field", "pSize");
      map15.put("title", "净重");
      map15.put("field", "productVol");
      map16.put("title", "毛重");
      map16.put("field", "pVol");
      exportColumns.add(map1);exportColumns.add(map2);exportColumns.add(map3);
      exportColumns.add(map4);exportColumns.add(map5);exportColumns.add(map6);
      exportColumns.add(map7);exportColumns.add(map8);exportColumns.add(map9);
      exportColumns.add(map10);exportColumns.add(map11);exportColumns.add(map12);
      exportColumns.add(map13);exportColumns.add(map14);exportColumns.add(map15);
      exportColumns.add(map16);
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
