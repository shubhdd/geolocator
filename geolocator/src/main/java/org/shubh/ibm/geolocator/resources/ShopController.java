package org.shubh.ibm.geolocator.resources;

import java.util.List;

import org.shubh.ibm.geolocator.model.Shop;
import org.shubh.ibm.geolocator.service.LocatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sdeshmukh
 * 
 */
@RestController
@RequestMapping("/shops")
public class ShopController {
	public static final Logger logger = LoggerFactory
			.getLogger(ShopController.class);
	LocatorService locatorService = new LocatorService();

	@RequestMapping(method = RequestMethod.GET)
	public List<Shop> getShops() {
		return locatorService.getShops();
	}

	@RequestMapping("/query")
	public Shop getShopsByLngLat(@RequestParam("lng") double lng,
			@RequestParam("lat") double lat) {
		logger.debug("Users Longitude and Latitude:" + lng + " " + lat);
		return locatorService.getShopsByLngLat(lng, lat);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Shop addShop(@RequestBody Shop shop) {
		logger.debug("Shopname for addition in controller:"
				+ shop.getShopName());
		return locatorService.addShop(shop);

	}
}
