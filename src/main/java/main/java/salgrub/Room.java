package main.java.salgrub;

import java.io.*;
import java.util.*;
import javax.websocket.*;

import main.java.salgrub.objects.User; 


public class Room {
	
	private String code;
	private HashMap<String, Session> sessions = new HashMap<String, Session>();
	private ArrayList<User> users = new ArrayList<User>();
	
//	For final parsing.
	private ArrayList<String> good = new ArrayList<String>();
	private ArrayList<String> bad = new ArrayList<String>();
	
	
	Room(String code){
		this.code = code;
	}
	
	public void addSession(String username, Session s) {
		sessions.put(username, s);
	}
	
	public void removeSession(String username) {
		sessions.remove(username);
	}
	
	public Integer getSize() {
		return sessions.size();
	}
	
	public HashMap<String, Session> getSessions(){
		return sessions;
	}

	public void addUser(User user) {
		users.add(user); 
	}
	
	public User getUser(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}
	
	public void setTags() {
		for(User u : users) {
			ArrayList <String> goodTags = u.getGoodTags();
			ArrayList <String> badTags = u.getDealbreakers();
			for (String g : goodTags) {
				good.add(g);
			}
			for(String b : badTags) {
				bad.add(b); 
			}
		}
	}
	
	public void displayUsers(Session s) {
		for(String username : sessions.keySet()) {
			try {
				s.getBasicRemote().sendText(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
