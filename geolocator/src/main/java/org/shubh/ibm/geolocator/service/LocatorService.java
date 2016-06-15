package org.shubh.ibm.geolocator.service;


import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.shubh.ibm.geolocator.common.utility.CommonUtility;
import org.shubh.ibm.geolocator.database.DatabaseStorage;
import org.shubh.ibm.geolocator.model.GoogleResponse;
import org.shubh.ibm.geolocator.model.Result;
import org.shubh.ibm.geolocator.model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author sdeshmukh
 *
 */
public class LocatorService {
	
	
	public static final Logger logger =LoggerFactory.getLogger(LocatorService.class);
	private static List<Shop> shops = DatabaseStorage.getShops();
	
	public  List<Shop> getShops(){
		logger.info("getShops Called"+shops.size());
		return shops;
	}
	

	
	/**
	 * @param shop
	 * @return Shop
	 */
	public Shop addShop(Shop shop){
		logger.info("addShop Method called");
		logger.debug("shops in memory"+shops);
		shops.add(shop);
		 String [] shopArray = {shop.getShopName(),String.valueOf(shop.getShopAddressNumber()),String.valueOf(shop.getShopAddressPostCode())};
		String address = StringUtils.join(shopArray,",");
		try{
			GoogleResponse res = new AddressConverter().getLongLat(address);
			if(res.getStatus().equals("OK"))
			  {
				Result[] result = res.getResults();
				if(null != result && result.length > 0){
			    shop.setShopLongitude(Double.valueOf(result[0].getGeometry().getLocation().getLat()));
			    shop.setShopLatitude(Double.valueOf(result[0].getGeometry().getLocation().getLng()));
			    logger.info("Lattitude of address is :"  +result[0].getGeometry().getLocation().getLat());
			    logger.info("Longitude of address is :" + result[0].getGeometry().getLocation().getLng());
			    logger.info("Location is " + result[0].getGeometry().getLocation_type());
				}
			  }
			  else
			  {
			   logger.info(res.getStatus());
			  }
		}catch(IOException ie){
			logger.error("Exception occurred in LocatorService addShop");
			ie.printStackTrace();
		}
		return shop;
	}
	

	
	/**
	 * @param lng
	 * @param lat
	 * @return Shop
	 */
	public  Shop getShopsByLngLat(double lng, double lat){
		logger.info("getShopsByLngLat Method called");
		int n = 0;
		for(int i=1; i < shops.size(); i++){
			if(CommonUtility.distance(lat, lng, shops.get(i).getShopLatitude(), shops.get(i).getShopLongitude(),shops.get(i).getShopName())< 
					CommonUtility.distance(lat, lng ,shops.get(n).getShopLatitude(),shops.get(n).getShopLongitude(),shops.get(n).getShopName())){
				n=i;
			}
		}
		
		return shops.get(n);
	}
	
	

}
