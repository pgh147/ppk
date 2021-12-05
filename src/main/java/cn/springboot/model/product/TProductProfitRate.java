package cn.springboot.model.product;

import java.math.BigDecimal;

import cn.springboot.model.BaseEntity;

/**
 * @Description excel导入的对象
 */
public class TProductProfitRate implements BaseEntity<String> {

	private static final long serialVersionUID = 3624947930970250778L;

	private String fnsku;
	private BigDecimal yourPrice	;
	private BigDecimal salesPrice	;
	private BigDecimal longestSide	;
	private BigDecimal medianSide	;
	private BigDecimal shortestSide	;
	private BigDecimal estimatedFeeTotal	;
	private BigDecimal fba	;


	
	@Override
	public String getId() {
		return fnsku;
	}



	public String getFnsku() {
		return fnsku;
	}



	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}



	public BigDecimal getYourPrice() {
		return yourPrice;
	}



	public void setYourPrice(BigDecimal yourPrice) {
		this.yourPrice = yourPrice;
	}



	public BigDecimal getSalesPrice() {
		return salesPrice;
	}



	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}



	public BigDecimal getLongestSide() {
		return longestSide;
	}



	public void setLongestSide(BigDecimal longestSide) {
		this.longestSide = longestSide;
	}



	public BigDecimal getMedianSide() {
		return medianSide;
	}



	public void setMedianSide(BigDecimal medianSide) {
		this.medianSide = medianSide;
	}



	public BigDecimal getShortestSide() {
		return shortestSide;
	}



	public void setShortestSide(BigDecimal shortestSide) {
		this.shortestSide = shortestSide;
	}



	public BigDecimal getEstimatedFeeTotal() {
		return estimatedFeeTotal;
	}



	public void setEstimatedFeeTotal(BigDecimal estimatedFeeTotal) {
		this.estimatedFeeTotal = estimatedFeeTotal;
	}



	public BigDecimal getFba() {
		return fba;
	}



	public void setFba(BigDecimal fba) {
		this.fba = fba;
	}


	

}
