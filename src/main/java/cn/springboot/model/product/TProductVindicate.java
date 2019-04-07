package cn.springboot.model.product;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.springboot.model.BaseEntity;

/**
 * @Description 产品对象
 */
public class TProductVindicate implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String id;
	private String productNo;// '产品编号',
	private String userNo;// '开发员编号',
	private BigDecimal saleQty;// '销售数量',
	private BigDecimal cellQty;// '库存量',
	private int status;// '记录状态',
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private String updater;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String creater;
	private String remark;// '备注',

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

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

	public BigDecimal getCellQty() {
		return cellQty;
	}

	public void setCellQty(BigDecimal cellQty) {
		this.cellQty = cellQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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


}
