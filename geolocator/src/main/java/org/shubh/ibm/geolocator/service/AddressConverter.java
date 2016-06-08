package org.shubh.ibm.geolocator.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import org.shubh.ibm.geolocator.model.GoogleResponse;
import org.shubh.ibm.geolocator.model.Result;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author ADMINIBM
 *
 */
public class AddressConverter {

	

	 private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

	 /*
	  * Here the fullAddress String is in format like
	  * "address". 
	  * .
	  */
	 public GoogleResponse convertToLatLong(String fullAddress) throws IOException {

	  /*
	   * Create an java.net.URL object by passing the request URL in
	   * constructor. Here you can see I am converting the fullAddress String
	   * in UTF-8 format. You will get Exception if you don't convert your
	   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
	   * parameter we also need to pass "sensor" parameter. sensor (required
	   * parameter) — Indicates whether or not the geocoding request comes
	   * from a device with a location sensor. This value must be either true
	   * or false.
	   */
	  java.net.URL url = new java.net.URL(URL + "?address="
	    + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
	  // Open the Connection
	  URLConnection conn = url.openConnection();

	  InputStream in = conn.getInputStream() ;
	  
	  ObjectMapper mapper = new ObjectMapper();
	  GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
	  in.close();
	  return response;
	  

	 }
	 
	
	 
	 /**
	 * @param address
	 * @return GoogleResponse
	 * @throws IOException
	 */
	public GoogleResponse getLongLat(String address) throws IOException {
	  
	  GoogleResponse res = new AddressConverter().convertToLatLong(address);
	  return res;
	  
	 }
	 

	}

