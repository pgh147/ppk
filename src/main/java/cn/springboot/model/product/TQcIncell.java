package cn.springboot.model.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TQcIncell {
    private String id;

    private String billNo;

    private String productNo;

    private String userNo;
    private String pUserNo;
    private String qcNotice;

    private Integer okQty;

    private Integer noOkQty;

    private String qcReason;

    private Integer incellQty;

    private Integer returnQty;

    private Integer status;

    private Integer productStatus;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updater;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String creater;

    private String remark;
    
    private String productName;
    private String imgData;
    private String orderSql;
    private String purchaserNo; //采购员
    private String purchaseQty; //采购数量
    private String purchaseNo;//采购单号
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Integer getOkQty() {
        return okQty;
    }

    public void setOkQty(Integer okQty) {
        this.okQty = okQty;
    }

    public Integer getNoOkQty() {
        return noOkQty;
    }

    public void setNoOkQty(Integer noOkQty) {
        this.noOkQty = noOkQty;
    }

    public String getQcReason() {
        return qcReason;
    }

    public void setQcReason(String qcReason) {
        this.qcReason = qcReason == null ? null : qcReason.trim();
    }

    public Integer getIncellQty() {
        return incellQty;
    }

    public void setIncellQty(Integer incellQty) {
        this.incellQty = incellQty;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
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
        this.updater = updater == null ? null : updater.trim();
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
        this.creater = creater == null ? null : creater.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getQcNotice() {
		return qcNotice;
	}

	public void setQcNotice(String qcNotice) {
		this.qcNotice = qcNotice;
	}

	public String getpUserNo() {
		return pUserNo;
	}

	public void setpUserNo(String pUserNo) {
		this.pUserNo = pUserNo;
	}

	public String getPurchaserNo() {
		return purchaserNo;
	}

	public void setPurchaserNo(String purchaserNo) {
		this.purchaserNo = purchaserNo;
	}

	public String getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(String purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
}