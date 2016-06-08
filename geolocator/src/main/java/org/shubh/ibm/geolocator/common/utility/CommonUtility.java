package org.shubh.ibm.geolocator.common.utility;


/**
 * @author sdeshmukh
 *
 */
public class CommonUtility {

	/**
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @param shopName
	 * @return double
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2,String shopName) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		//Distance in meteres
		dist = dist * 1.609344;
		System.out.println(shopName +" shop ditance is:"+dist);
		return dist;
	}
	
	
	/**
	 * @param deg
	 * @return double
	 */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	
	/**
	 * @param rad
	 * @return double
	 */
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
}
