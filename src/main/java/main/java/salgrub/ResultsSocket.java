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
	private static int finishedSwiping = 0;
	private static int maxVote = 0;
	private static String winner = "";

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

			votes.put(code,  new HashMap<String, Integer>());


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
	
	@OnMessage
	public void onMessage(String message) {
		String[] list = message.split(",");
		message = list[0];
		String code = list[1];
		String username = list[2];
		
		HashMap<String, Integer> curr = votes.get(code);
		
		System.out.println("Recieved Message");
		System.out.println(message);
		
		if(message.equals("abcdefghijk")) {
			for(Map.Entry<String, Integer> entry : curr.entrySet()) {
				if (maxVote < entry.getValue()) {
					maxVote = entry.getValue();
					winner = entry.getKey();
				}
			}
			
			finishedSwiping += 1;
			
			//if all the rooms aren't finished, keep on going
			if (finishedSwiping < sessionVector.size()) {
				try {
					for (Session s : sessionVector) {
						s.getBasicRemote().sendText("Not Finished");
					}
				} catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
				}
			} else {	//else send the winner
				try {
					for(Session s : sessionVector) {
						s.getBasicRemote().sendText(winner);
					}
					
				} catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
				}
			}
		}
		
		else if(curr.containsKey(message))
			curr.replace(message, curr.get(message) + 1);
		else
			curr.put(message, Integer.valueOf(1));
	}
}