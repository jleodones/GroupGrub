package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint(value = "/swiping/{code}/{username}")
public class ResultsSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, HashMap<String, Integer>> votes = new HashMap<String, HashMap<String, Integer> >();
	private static HashMap<String, Room> rooms = new HashMap<String, Room>();	//code, username, session

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session, @PathParam("code") String code, @PathParam("username") String username) { 
		//Connection is made.
		System.out.println("Connection made!");
		sessionVector.add(session);
		Room r;
		if(rooms.containsKey(code)) {
			r = rooms.get(code);
			//String m = code + "," + username;
			//broadcast(m);
			r.addSession(username, session);
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
	public void close(Session session, @PathParam("code") String code, @PathParam("username") String username) {
		System.out.println("Disconnecting!");
		System.out.println(code);
		System.out.println(username);

		
		//remove session from its room
		Room r = rooms.get(code);
		r.removeSession(username);
		
		//remove session from tracker
		sessionVector.remove(session);
		
		//no more sessions in the room, delete it and its results
		if(r.getSize() < 1) {
			System.out.println("removing rooms/votes");
			rooms.remove(code);
			votes.remove(code);
		}
	}
	
	@OnError
	public void error(Throwable error) {
		error.printStackTrace();
		System.out.println("Error!");
	}
	
	/*@OnMessage
	public void onMessage(String message, @PathParam("code") String code, @PathParam("username") String username ) {
		HashMap<String, Integer> curr = votes.get(code);
		
		if(message.equals("abcdefghijk")) {
			Integer max = Integer.valueOf(0);
			String winner = "";
			for(Map.Entry<String, Integer> entry : curr.entrySet()) {
				if (max < entry.getValue()) {
					max = entry.getValue();
					winner = entry.getKey();
				}
			}
					
			try {
				for(Session s : sessionVector) {
					s.getBasicRemote().sendText(winner);
				}
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
				}
		}
		
		else if(votes.containsKey(message))
			votes.replace(message, votes.get(message)+1);
		else
			votes.put(message, Integer.valueOf(1));
	}*/
}