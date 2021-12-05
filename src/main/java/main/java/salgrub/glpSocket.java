package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

import main.java.salgrub.objects.*;

@ServerEndpoint(value = "/glp/{code}/{username}")
public class glpSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session, @PathParam("code") String code, @PathParam("username") String username) {
		//Connection is made.
		System.out.println("GLP connection made!");
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
			r.addSession(username, session);
			r.addUser(user);
		}
		//If doesn't exist, create.
		else {
			r = new Room(code);
			r.addSession(username, session);
			r.addUser(user);
			rooms.put(code, r);
		}
		
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
	public void onMessage(String message, @PathParam("code") String code, @PathParam("username") String username) {
		String[] myMsg = message.split(",");
		
		Integer command = Integer.parseInt(myMsg[0]);
		
		/* COMMANDS:
		 * 1: ADD GOOD TAG
		 * 2: REMOVE GOOD TAG
		 * 3: ADD DEALBREAKER
		 * 4: REMOVE DEALBREAKER
		 */
		
		if(command == 1) {
			addTag(myMsg[1], code, username, true);
		}
		else if(command == 2) {
			removeTag(myMsg[1], code, username, true);
		}
		else if(command == 3) {
			addTag(myMsg[1], code, username, false);
		}
		else if(command == 4) {
			removeTag(myMsg[1], code, username, false);

		}
		else {
			System.out.println("Bad command.");
		}
	}
	
	public void addTag(String tag, String code, String username, boolean good) {
		
		//Get the room and appropriate user.
		Room r = rooms.get(code);
		User user = r.getUser(username);
		
		//Add the tag to the user.
		//Good specifies which tag list you are adding to.
		if(good) {
			user.addGoodTag(tag);
		}
		else {
			user.addDealbreaker(tag);
		}
	}
	
	public void removeTag(String tag, String code, String username, boolean good) {
		
		//Get the room and appropriate user.
		Room r = rooms.get(code);
		User user = r.getUser(username);
		
		//Remove the tag from the user.
		//Good specifies which tag list you are removing from.
		if(good) {
			user.removeGoodTag(tag);
		}
		else {
			user.removeDealbreaker(tag);
		}
	}
}