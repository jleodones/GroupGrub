package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.JSONObject;

import main.java.salgrub.objects.*;

@ServerEndpoint(value = "/glp/{code}/{username}/{master}")
public class glpSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session, @PathParam("code") String code, @PathParam("username") String username, @PathParam("master") String mas) {
		//Connection is made.
		System.out.println("GLP connection made!");
		sessionVector.add(session); //Add this session to overall list of sessions.
		
		//ADDING THE USERS TO A ROOM:
		//If exists, add to existing room.
		Room r;
		User user;
		Boolean master;
		
		if(mas.equals("true")) {
			master = true;
		}
		else {
			master = false;
		}
		//Instantiating user.
		if(username.equals("Guest")){
			user = new GuestUser(username);
			user.setMaster(master);
		}
		else {
			user = new LoggedInUser(username);
			user.setMaster(master);
		}
		
		if(rooms.containsKey(code)) {
			r = rooms.get(code);
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
		System.out.println("Disconnecting from GLP server!");
		sessionVector.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		error.printStackTrace();
		System.out.println("Error!");
	}
	
	@OnMessage
	public void onMessage(String message, @PathParam("code") String code, @PathParam("username") String username) {
		
		//Grab the message.
		System.out.println(message);
		
		String[] myMsg = message.split(",");
		
		if(myMsg[0].equals("done")) {
			finish(message, code, username);
		}
		else if(myMsg[0].equals("datapls")) {
			Room r = rooms.get(code);
			JSONObject obj = r.giveMeTags(myMsg[1], myMsg[2]);
			
			//Send the object to the master user in the session.
			for(User u : r.getUserList()) {
				System.out.println("Sending my data to... " + u.getUsername());
				r.broadcastAll(obj.toString());
			}	
		}
		else {
			tagParse(message, code, username);
		}
	}
	
	public void finish(String message, String code, String username) {
		//Change user to finished.
		Room r = rooms.get(code);
		User user = r.getUser(username);
		
		user.setFinished(true);
		
		//Check if everyone is finished.
		if(r.isFinished()) {
			r.broadcastAll("finished");
		}
		else {
			r.broadcast("wait", username);
		}
	}
	
	public void tagParse(String tag, String code, String username) {
		String[] myMsg = tag.split(",");
		
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