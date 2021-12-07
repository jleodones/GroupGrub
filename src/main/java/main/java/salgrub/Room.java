package main.java.salgrub;

import java.io.*;
import java.util.*;
import javax.websocket.*;
import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public ArrayList<User> getUserList(){
		return users;
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
	
	public boolean isFinished() {
		for(User u : users) {
			if(!u.getFinished()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void broadcast(String m) {
		for(String s : sessions.keySet()) {
			Session x = sessions.get(s);
			try {
				x.getBasicRemote().sendText(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public JSONObject giveMeTags(String latitude, String longitude) {
		HashSet<String> good = new HashSet();
		HashSet<String> bad = new HashSet();
		
		for(User u : users) {
			good.addAll(u.getGoodTags());
			bad.addAll(u.getDealbreakers());
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("good", good);
		jsonObject.put("bad", bad);
		jsonObject.put("latitude", latitude);
		jsonObject.put("longitude", longitude);
		
		System.out.println(jsonObject.toString());
		
		return jsonObject;
	}

}
