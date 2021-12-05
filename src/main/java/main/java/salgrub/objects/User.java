package main.java.salgrub.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap; 

public abstract class User{

	private boolean isMaster; 
	public String username; 
	private HashMap<String, Long> location; //https://www.w3schools.com/html/html5_geolocation.asp
	public ArrayList<String> goodTags;
	public ArrayList<String> dealBreakers;
	
	
	User(String uname){
		username = uname; 
	}
	
	public HashMap<String, Long> getLocation(){
		return location; 
	}
	
	//servlet will call this function to set the location 
	public void setLocation(String latCoord, String lonCoord) {
		
		try {
			Long lon = Long.parseLong(lonCoord); 
			Long lat = Long.parseLong(latCoord); 
			location.put("longitude", lon);
			location.put("latitude", lat); 
			
		}catch(NumberFormatException e){
			System.out.println("oof"); 
		}		
	}
	
	//sey yes, swipe right 
	public void pickRestaurant() {
		 
	} 
	
	//say no, swipe left 
	public void rejectRestauant() {
		
	}


	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setGoodTags(ArrayList<String> tags) {
		this.goodTags = tags;
	}
	
	public ArrayList<String> getGoodTags() {
		return goodTags;
	}
	
	public ArrayList<String> getDealbreakers(){
		return dealBreakers; 
	}
	
	public void setDealbreakers (ArrayList<String> tags) {
		this.dealBreakers.addAll(tags);
	}
	
}