package cn.springboot.model.product;

import java.util.Date;

public class TProductInfo {
    private String productNo;

    private String productSelling1;

    private String productSelling2;

    private String productSelling3;

    private String productSelling4;

    private String productSelling5;
    private String userNo;

    private String desc;

    private String message;

    private String searchTerm;

    private String classify;

    private Date updateTime;

    private String updater;

    private Date createTime;

    private String creater;

    private String remark;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductSelling1() {
        return productSelling1;
    }

    public void setProductSelling1(String productSelling1) {
        this.productSelling1 = productSelling1 == null ? null : productSelling1.trim();
    }

    public String getProductSelling2() {
        return productSelling2;
    }

    public void setProductSelling2(String productSelling2) {
        this.productSelling2 = productSelling2 == null ? null : productSelling2.trim();
    }

    public String getProductSelling3() {
        return productSelling3;
    }

    public void setProductSelling3(String productSelling3) {
        this.productSelling3 = productSelling3 == null ? null : productSelling3.trim();
    }

    public String getProductSelling4() {
        return productSelling4;
    }

    public void setProductSelling4(String productSelling4) {
        this.productSelling4 = productSelling4 == null ? null : productSelling4.trim();
    }

    public String getProductSelling5() {
        return productSelling5;
    }

    public void setProductSelling5(String productSelling5) {
        this.productSelling5 = productSelling5 == null ? null : productSelling5.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm == null ? null : searchTerm.trim();
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify == null ? null : classify.trim();
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

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
}