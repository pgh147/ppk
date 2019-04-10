package cn.springboot.model.product;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.springboot.model.BaseEntity;

/**
 * @Description 产品对象
 */
public class TProduct implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String id;
	private String productNo;// '产品编号',
	private String productName;// '产品名字',
	private String userNo;// '开发员编号',
	private String uploadAccount;// '上传账号',
	private String productImgNo;// '产品图片主表',
	private String rivalLink;// '竞争对手链接',
	private String supplierLink;// '供应商链接',
	private String productAnys;// '产品开发员分析',
	private String productSafe;// '产品安全性',
	private String productInfo;// '产品英文',
	private String productImgData;// '产品图片流',
	private BigDecimal productPrice;// '产品成本价',
	private String productSize;// '产品大小',
	private BigDecimal productVol;// '产品重量',
	private BigDecimal firstSendQty;// '首批发货数量',
	private BigDecimal productOrderQty;// '产品起订量',
	private BigDecimal predictSalesQty;// '预计月销量',
	
	private BigDecimal SaleQty;// '销量',
	private BigDecimal cellQty;// '库存',
    private String productSelling1;

    private String productSelling2;

    private String productSelling3;

    private String productSelling4;

    private String productSelling5;
    private String descript;

    private String message;

    private String searchTerm;

    private String classify;
	private int status;// '记录状态',
	private int productStatus;// '产品状态',
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private String updater;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String creater;
	private String remark;// '备注',
	private boolean isUpdateImg;
	private String pSize;
	private String pVol;
	private String monthQty;// '月数量',
	private String weekQty;// '星期数量',
	private String threeQty;// '三条数量',
	private String stkQty;
	private String enTitle;
	private String qcNotice;
	private Integer inQty;//本地仓库存数量
	private Integer ingQty;//在途库存数量
	private String orderBy;
	private BigDecimal salePrice;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUploadAccount() {
		return uploadAccount;
	}

	public void setUploadAccount(String uploadAccount) {
		this.uploadAccount = uploadAccount;
	}

	public String getProductImgNo() {
		return productImgNo;
	}

	public void setProductImgNo(String productImgNo) {
		this.productImgNo = productImgNo;
	}

	public String getRivalLink() {
		return rivalLink;
	}

	public void setRivalLink(String rivalLink) {
		this.rivalLink = rivalLink;
	}

	public String getSupplierLink() {
		return supplierLink;
	}

	public void setSupplierLink(String supplierLink) {
		this.supplierLink = supplierLink;
	}

	public String getProductAnys() {
		return productAnys;
	}

	public void setProductAnys(String productAnys) {
		this.productAnys = productAnys;
	}

	public String getProductSafe() {
		return productSafe;
	}

	public void setProductSafe(String productSafe) {
		this.productSafe = productSafe;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public BigDecimal getProductVol() {
		return productVol;
	}

	public void setProductVol(BigDecimal productVol) {
		this.productVol = productVol;
	}

	public BigDecimal getFirstSendQty() {
		return firstSendQty;
	}

	public void setFirstSendQty(BigDecimal firstSendQty) {
		this.firstSendQty = firstSendQty;
	}

	public BigDecimal getProductOrderQty() {
		return productOrderQty;
	}

	public void setProductOrderQty(BigDecimal productOrderQty) {
		this.productOrderQty = productOrderQty;
	}

	public BigDecimal getPredictSalesQty() {
		return predictSalesQty;
	}

	public void setPredictSalesQty(BigDecimal predictSalesQty) {
		this.predictSalesQty = predictSalesQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductImgData() {
		return productImgData;
	}

	public void setProductImgData(String productImgData) {
		this.productImgData = productImgData;
	}

	public boolean getIsUpdateImg() {
		return isUpdateImg;
	}

	public void setIsUpdateImg(boolean isUpdateImg) {
		this.isUpdateImg = isUpdateImg;
	}

	public BigDecimal getSaleQty() {
		return SaleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		SaleQty = saleQty;
	}

	public BigDecimal getCellQty() {
		return cellQty;
	}

	public void setCellQty(BigDecimal cellQty) {
		this.cellQty = cellQty;
	}

	public String getMonthQty() {
		return monthQty;
	}

	public void setMonthQty(String monthQty) {
		this.monthQty = monthQty;
	}

	public String getWeekQty() {
		return weekQty;
	}

	public void setWeekQty(String weekQty) {
		this.weekQty = weekQty;
	}

	public String getThreeQty() {
		return threeQty;
	}

	public void setThreeQty(String threeQty) {
		this.threeQty = threeQty;
	}

	public String getStkQty() {
		return stkQty;
	}

	public void setStkQty(String stkQty) {
		this.stkQty = stkQty;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getProductSelling1() {
		return productSelling1;
	}

	public void setProductSelling1(String productSelling1) {
		this.productSelling1 = productSelling1;
	}

	public String getProductSelling2() {
		return productSelling2;
	}

	public void setProductSelling2(String productSelling2) {
		this.productSelling2 = productSelling2;
	}

	public String getProductSelling3() {
		return productSelling3;
	}

	public void setProductSelling3(String productSelling3) {
		this.productSelling3 = productSelling3;
	}

	public String getProductSelling4() {
		return productSelling4;
	}

	public void setProductSelling4(String productSelling4) {
		this.productSelling4 = productSelling4;
	}

	public String getProductSelling5() {
		return productSelling5;
	}

	public void setProductSelling5(String productSelling5) {
		this.productSelling5 = productSelling5;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getpSize() {
		return pSize;
	}

	public void setpSize(String pSize) {
		this.pSize = pSize;
	}

	public String getpVol() {
		return pVol;
	}

	public void setpVol(String pVol) {
		this.pVol = pVol;
	}

	public Integer getInQty() {
		return inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public Integer getIngQty() {
		return ingQty;
	}

	public void setIngQty(Integer ingQty) {
		this.ingQty = ingQty;
	}

	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getQcNotice() {
		return qcNotice;
	}

	public void setQcNotice(String qcNotice) {
		this.qcNotice = qcNotice;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

}
