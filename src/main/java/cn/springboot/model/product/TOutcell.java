package cn.springboot.model.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TOutcell {
    private String id;

    private String billNo;

    private String productNo;

    private String userNo;

    private Integer outQty;
    private Integer surplusQty;
    private Integer cellSurplusQty;
    private String outReason;

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
    private String productImgNo;
    private String imgData;
    private String orderSql;
    private String pUserNo;
    private Integer caQty;
    private Integer usQty;
    private Integer ukQty;
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

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason == null ? null : outReason.trim();
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

	

	public Integer getCellSurplusQty() {
		return cellSurplusQty;
	}

	public void setCellSurplusQty(Integer cellSurplusQty) {
		this.cellSurplusQty = cellSurplusQty;
	}

	public String getpUserNo() {
		return pUserNo;
	}

	public void setpUserNo(String pUserNo) {
		this.pUserNo = pUserNo;
	}

	public Integer getCaQty() {
		return caQty;
	}

	public void setCaQty(Integer caQty) {
		this.caQty = caQty;
	}

	public Integer getUsQty() {
		return usQty;
	}

	public void setUsQty(Integer usQty) {
		this.usQty = usQty;
	}

	public Integer getUkQty() {
		return ukQty;
	}

	public void setUkQty(Integer ukQty) {
		this.ukQty = ukQty;
	}

	public Integer getSurplusQty() {
		return surplusQty;
	}

	public void setSurplusQty(Integer surplusQty) {
		this.surplusQty = surplusQty;
	}

	public String getProductImgNo() {
		return productImgNo;
	}

	public void setProductImgNo(String productImgNo) {
		this.productImgNo = productImgNo;
	}
}