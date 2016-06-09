package org.shubh.ibm.geolocator.model;

/**
 * @author sdeshmukh
 *
 */
public class Shop {
	
	private String shopName;
	private int shopAddressNumber;
	private int shopAddressPostCode;
	private double shopLongitude;
	private double shopLatitude;
	
	public Shop(){}

	public Shop( String shopName, int shopAddressNumber,
			int shopAddressPostCode, double shopLongitude, double shopLatitude) {
		super();
		this.shopName = shopName;
		this.shopAddressNumber = shopAddressNumber;
		this.shopAddressPostCode = shopAddressPostCode;
		this.shopLongitude = shopLongitude;
		this.shopLatitude = shopLatitude;
	}

	/*public long getId() {
		return id;
	}*/

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getShopAddressNumber() {
		return shopAddressNumber;
	}

	public void setShopAddressNumber(int shopAddressNumber) {
		this.shopAddressNumber = shopAddressNumber;
	}

	public int getShopAddressPostCode() {
		return shopAddressPostCode;
	}

	public void setShopAddressPostCode(int shopAddressPostCode) {
		this.shopAddressPostCode = shopAddressPostCode;
	}

	public double getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	public double getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	

}
