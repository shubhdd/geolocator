package org.shubh.ibm.geolocator.service;


import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.shubh.ibm.geolocator.common.utility.CommonUtility;
import org.shubh.ibm.geolocator.database.DatabaseStorage;
import org.shubh.ibm.geolocator.model.GoogleResponse;
import org.shubh.ibm.geolocator.model.Result;
import org.shubh.ibm.geolocator.model.Shop;


/**
 * @author sdeshmukh
 *
 */
public class LocatorService {

	private static List<Shop> shops = DatabaseStorage.getShops();
	
	public  List<Shop> getShops(){
		return shops;
	}
	

	
	/**
	 * @param shop
	 * @return Shop
	 */
	public Shop addShop(Shop shop){
		System.out.println("addShop Method called"+shop.getShopName());
		System.out.println("shops"+shops);
		shops.add(shop);
		 String [] shopArray = {shop.getShopName(),String.valueOf(shop.getShopAddressNumber()),String.valueOf(shop.getShopAddressPostCode())};
		String address = StringUtils.join(shopArray,",");
		try{
			GoogleResponse res = new AddressConverter().getLongLat(address);
			if(res.getStatus().equals("OK"))
			  {
			  // for(Result result : res.getResults())
			  // {
				Result[] result = res.getResults();
				if(null != result && result.length > 0){
			    shop.setShopLongitude(Double.valueOf(result[0].getGeometry().getLocation().getLat()));
			    shop.setShopLatitude(Double.valueOf(result[0].getGeometry().getLocation().getLng()));
			    System.out.println("Lattitude of address is :"  +result[0].getGeometry().getLocation().getLat());
			    System.out.println("Longitude of address is :" + result[0].getGeometry().getLocation().getLng());
			    System.out.println("Location is " + result[0].getGeometry().getLocation_type());
				}
			 //  }
			  }
			  else
			  {
			   System.out.println(res.getStatus());
			  }
		}catch(IOException ie){
			System.out.println("Exception occurred in LocatorService addShop");
			ie.printStackTrace();
		}
		return shop;
	}
	
	/*public Shop updateShop(Shop shop){
		if(shop.getId()<= 0){
			return null;
		}
		shops.add(shop);
		
		return shop;
	}
	*/
	
	
	/**
	 * @param lng
	 * @param lat
	 * @return Shop
	 */
	public  Shop getShopsByLngLat(double lng, double lat){
		int n = 0;
		for(int i=1; i < shops.size(); i++){
			System.out.println("ShopName to calculate distance::"+shops.get(n));
			if(CommonUtility.distance(lat, lng, shops.get(i).getShopLatitude(), shops.get(i).getShopLongitude(),shops.get(i).getShopName())< 
					CommonUtility.distance(lat, lng ,shops.get(n).getShopLatitude(),shops.get(n).getShopLongitude(),shops.get(n).getShopName())){
				n=i;
			}
		}
		
		return shops.get(n);
	}
	
	

}
