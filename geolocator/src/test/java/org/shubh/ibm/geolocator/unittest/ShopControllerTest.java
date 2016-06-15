package org.shubh.ibm.geolocator.unittest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shubh.ibm.geolocator.Application;
import org.shubh.ibm.geolocator.model.GoogleResponse;
import org.shubh.ibm.geolocator.model.Result;
import org.shubh.ibm.geolocator.model.Shop;
import org.shubh.ibm.geolocator.service.AddressConverter;
import org.shubh.ibm.geolocator.test.utility.TestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ShopControllerTest {
	public static final Logger logger =LoggerFactory.getLogger(ShopControllerTest.class);

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	
	 @Autowired
	    private WebApplicationContext webApplicationContext;
	 
	 private  List<Shop> shops = new ArrayList<Shop>();
	 
	 private Shop shop;
	 
	 private GoogleResponse res;
	 
	@Before
    public void setup() throws Exception {
        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
       
        this.shop = new Shop("Dominos Pizza", 12, 411033,0, 0);
        res = new AddressConverter().convertToLatLong("Dominos Pizza, 12 , 411033");
        updateLngLat(res);
        shops.add(shop);
        
        this.shop = new Shop("Sonigara heights", 11, 411026,0, 0);
        res = new AddressConverter().convertToLatLong("Sonigara heights, 11 , 411026");
        updateLngLat(res);
        shops.add(shop);
        
        this.shop = new Shop("Mezza 9", 11, 411057,0, 0);
        res = new AddressConverter().convertToLatLong("Mezza 9, 13, 410057");
        updateLngLat(res);
        shops.add(shop);
        
        
	}

	@Test
	public void getShops() throws Exception{
                this.mockMvc.perform(get("/shops").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].shopName", is(shops.get(0).getShopName())))
                        .andExpect(jsonPath("$[0].shopAddressNumber", is(shops.get(0).getShopAddressNumber())))
                          .andExpect(jsonPath("$[0].shopAddressPostCode", is(shops.get(0).getShopAddressPostCode())))
                            .andExpect(jsonPath("$[0].shopLatitude", is(shops.get(0).getShopLongitude())))
                              .andExpect(jsonPath("$[0].shopLongitude", is(shops.get(0).getShopLatitude())))
                              .andExpect(jsonPath("$[1].shopName", is(shops.get(1).getShopName())))
                        .andExpect(jsonPath("$[1].shopAddressNumber", is(shops.get(1).getShopAddressNumber())))
                          .andExpect(jsonPath("$[1].shopAddressPostCode", is(shops.get(1).getShopAddressPostCode())))
                            .andExpect(jsonPath("$[1].shopLatitude", is(shops.get(1).getShopLongitude())))
                              .andExpect(jsonPath("$[1].shopLongitude", is(shops.get(1).getShopLatitude())))
                        ;
                logger.info("Shops size:"+shops.size());
	}
	
	
	@Test
	public void addShop()throws Exception{
		
		for (int i = 0; i<shops.size();i++){
        this.mockMvc.perform(post("/shops")
                .contentType(contentType)
                .content(TestUtil.convertObjectToJsonBytes(shops.get(i))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopName", is(shops.get(i).getShopName())))
           .andExpect(jsonPath("$.shopAddressNumber", is(shops.get(i).getShopAddressNumber())))
           .andExpect(jsonPath("$.shopAddressPostCode", is(shops.get(i).getShopAddressPostCode())))
          .andExpect(jsonPath("$.shopLatitude", is(shops.get(i).getShopLongitude())))
          .andExpect(jsonPath("$.shopLongitude", is(shops.get(i).getShopLatitude())));
		}
	}
	
	@Test
	public void getNearestShop() throws Exception{
		this.mockMvc.perform(get("/shops/query")
				.contentType(contentType)
				.param("lng", "73.7857032")
				.param("lat", "18.6209663")
				).andExpect(status().isOk())
				.andExpect(jsonPath("$.shopName", is(shops.get(0).getShopName())))
				.andExpect(jsonPath("$.shopAddressNumber", is(shops.get(0).getShopAddressNumber())))
				.andExpect(jsonPath("$.shopAddressPostCode", is(shops.get(0).getShopAddressPostCode())))
				.andExpect(jsonPath("$.shopLatitude", is(shops.get(0).getShopLongitude())))
				.andExpect(jsonPath("$.shopLongitude", is(shops.get(0).getShopLatitude())));
	}

	
	
	public void updateLngLat(GoogleResponse res){
		if(res.getStatus().equals("OK"))
		  {
			Result[] result = res.getResults();
			if(null != result && result.length > 0){
				this.shop.setShopLongitude(Double.valueOf(result[0].getGeometry().getLocation().getLng()));
				this.shop.setShopLatitude(Double.valueOf(result[0].getGeometry().getLocation().getLat()));
				System.out.println("longitude is"+shop.getShopLongitude());
			}
		  }
	}
}
