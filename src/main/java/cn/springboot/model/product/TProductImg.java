package cn.springboot.model.product;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.springboot.model.BaseEntity;

/**
 * @Description 产品对象
 */
public class TProductImg implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String id;
	private String imgNo;// '图片编号',
	private String imgData;// '图片数据',
	private int status;// '记录状态',
	private int type;// '图片类型',
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

	public String getImgNo() {
		return imgNo;
	}

	public void setImgNo(String imgNo) {
		this.imgNo = imgNo;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
