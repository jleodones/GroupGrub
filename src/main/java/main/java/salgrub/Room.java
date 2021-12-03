package main.java.salgrub;

import java.io.*;
import java.util.*;
import javax.websocket.*; 


public class Room {
	
	private String code;
	private HashMap<String, Session> sessions = new HashMap<String, Session>();
	
	Room(String code){
		this.code = code;
	}
	
	public void addSession(String username, Session s) {
		sessions.put(username, s);
	}
	
	public HashMap<String, Session> getSessions(){
		return sessions;
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
