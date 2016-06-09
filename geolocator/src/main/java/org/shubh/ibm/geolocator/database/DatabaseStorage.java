package org.shubh.ibm.geolocator.database;

import java.util.ArrayList;
import java.util.List;

import org.shubh.ibm.geolocator.model.Shop;


/**
 * @author sdeshmukh
 *
 */
public class DatabaseStorage {

	
	private static ArrayList<Shop> shops = new ArrayList<Shop>();
	
	
	
	/**
	 * @return List<Shop>
	 */
	public static List<Shop> getShops(){
		return shops;
	}
}
