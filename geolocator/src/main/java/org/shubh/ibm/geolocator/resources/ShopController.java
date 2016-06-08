package org.shubh.ibm.geolocator.resources;

import java.util.List;

import org.shubh.ibm.geolocator.model.Shop;
import org.shubh.ibm.geolocator.service.LocatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {

    LocatorService locatorService = new LocatorService();
	
    @RequestMapping(method=RequestMethod.GET)
    public List<Shop> getShops() {
    	return locatorService.getShops();
    }
    
    @RequestMapping("/query")
    public Shop getShopsByLngLat(@RequestParam("lng") double lng,@RequestParam("lat") double lat) {
    	System.out.println("Users Longitude and Latitude:"+lng + " "+lat);
    	return locatorService.getShopsByLngLat(lng,lat);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public Shop addShop(@RequestBody Shop shop){
    	System.out.println("Shopname in controller:"+shop.getShopName());
    	return locatorService.addShop(shop);
    	
    }
}
