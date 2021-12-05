package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

import main.java.salgrub.objects.*;

@ServerEndpoint(value = "/lobby/{badTags}/{goodTags}/{code}")
public class glpSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session, @PathParam("badTags") String[] bad, @PathParam("goodTags") String[] good, @PathParam("code") String code, @PathParam("username") String username) {
		//Connection is made.
		System.out.println("Websocket connection made!");
		sessionVector.add(session); //Add this session to overall list of sessions.
		
		//ADDING THE USERS TO A ROOM:
		//If exists, add to existing room.
		Room r;
		User user;
		if(username.equals("Guest")){
			user = new GuestUser(username);
		}
		else {
			user = new LoggedInUser(username);
		}

		if(rooms.containsKey(code)) {
			r = rooms.get(code);
			String m = code + "," + username;
			//broadcast(m);
//			user.setGoodTag(good);
//			user.setBadTags(bad); 
			r.addSession(username, session);
			r.addUser(user);
		}
		//If doesn't exist, create.
		else {
			r = new Room(code);
			r.addSession(username, session);
			rooms.put(code, r);
		}
		//r.displayUsers(session);
		
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
	
}