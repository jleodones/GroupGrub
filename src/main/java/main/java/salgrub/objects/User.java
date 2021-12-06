package main.java.salgrub.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*; 

public abstract class User{

	private boolean isMaster; 
	public String username; 
	private HashMap<String, Long> location; //https://www.w3schools.com/html/html5_geolocation.asp
	public HashSet<String> goodTags = new HashSet<String>();
	public HashSet<String> dealBreakers;
	private boolean finished;
	
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
	
	//FINISHED WITH TAGS?
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean getFinished() {
		return finished;
	}
	
	//GOOD TAGS.
	public void addGoodTag(String tag) {
		System.out.println("adding to good tags: " + tag);
		tag = tag.toLowerCase();
		this.goodTags.add(tag);
	}
	
	public void removeGoodTag(String tag) {
		tag = tag.toLowerCase();
		goodTags.remove(tag);
	}
	
	public HashSet<String> getGoodTags() {
		return goodTags;
	}
	
	
	//DEALBREAKERS.
	public void addDealbreaker(String tag) {
		System.out.println("adding to bad tags: " + tag);
		tag = tag.toLowerCase();
		this.dealBreakers.add(tag);
	}
	
	public void removeDealbreaker(String tag) {
		tag = tag.toLowerCase();
		dealBreakers.remove(tag);
	}
	public HashSet<String> getDealbreakers(){
		return dealBreakers; 
	}
	
	
}