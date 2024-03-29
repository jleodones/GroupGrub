package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint(value = "/lobby/{code}/{username}")
public class LobbyWaitingSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session, @PathParam("code") String code, @PathParam("username") String username) {
		//Connection is made.
		System.out.println("Websocket connection made!");
		sessionVector.add(session); //Add this session to overall list of sessions.
		
		//ADDING THE USERS TO A ROOM:
		//If exists, add to existing room.
		Room r ;
		if(username.equals("codeCheck")) {
			if(rooms.containsKey(code)) {
				try {
					session.getBasicRemote().sendText("ye");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				
				try {
					session.getBasicRemote().sendText("ne");
				} catch (IOException e) {
					e.printStackTrace();
				} 		
			}
			return; 
		}
		if(rooms.containsKey(code) ) {
			r = rooms.get(code);
			String m = code + "," + username;
			broadcast(m);
			r.addSession(username, session);
		}
		//If doesn't exist, create.
		else {
			r = new Room(code);
			r.addSession(username, session);
			rooms.put(code, r);
		}
		r.displayUsers(session);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		sessionVector.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		error.printStackTrace();
		System.out.println("Error!");
	}
	
	@OnMessage
	public void broadcast(String message){
		String[] m = message.split(",");
		Room r = rooms.get(m[0]);
		
		HashMap<String, Session> users = r.getSessions();
			
		for(String x : users.keySet()) {
			try {
				Session s = users.get(x);
				s.getBasicRemote().sendText(m[1].trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Successful broadcast.");
	}
}