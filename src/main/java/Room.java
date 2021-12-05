//package main.java.salgrub;

import java.io.*;
import java.util.*;
import javax.websocket.*;

import org.apache.catalina.User; 


public class Room {
	
	private String code;
	private HashSet<Session> sessions = new HashSet<Session>(); 
	private HashSet<String> good = new HashSet<String>();
	private HashSet<String> bad = new HashSet<String>(); 
	private ArrayList<User> users = new ArrayList<Users>(); 
	
	Room(String code){
		this.code = code;
	}
	
	public void addSession (Session s) {
		sessions.add(s);
	}
	
	public HashSet<Session> getSessions(){
		return sessions;
	}
	
	public void addUser(User user) {
		users.add(user); 
	}
	
	public void setTags() {
		for(User u : users) {
			ArrayList <String> goodTags = u.getGoodTags();
			ArrayList <String> badTags = u.getBadTags();
			for (String g : goodTags) {
				good.add(g);
			}
			for(String b : badTags) {
				bad.add(b); 
			}
		}
	}
	
	/*public void displayUsers(Session s) {
		for(String username : sessions.keySet()) {
			try {
				s.getBasicRemote().sendText(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	// in Loggednin.jsp, add the tags to the current user
	//in each room have a list of users
	//after retrieving tags add it to the set in the room
	//send to restaurant.java 
	
}
