package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.JSONObject;

import main.java.salgrub.tagging.Restaurant;

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
			r.addSession(username, session);
		}
		//If doesn't exist, create.
		else {
			r = new Room(code);
			r.addSession(username, session);
			rooms.put(code, r);
			votes.put(code,  new HashMap<String, Integer>());
		}
	}
	
	@OnClose
	public void close(Session session, @PathParam("code") String code, @PathParam("username") String username) {
		System.out.println("Disconnecting from results server!");
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
	public void onMessage(String message, @PathParam("code") String code, @PathParam("username") String username) {
		
		HashMap<String, Integer> curr = votes.get(code);
		ArrayList<String> winners = new ArrayList<String>();
		
		System.out.println("Received Message");
		System.out.println(message);
		
		int maxVote = 0;
		
		if(message.equals("abcdefghijk")) {
			System.out.println("yo");
			
			//Retrieve the max vote count.
			for(Map.Entry<String, Integer> entry : curr.entrySet()) {
				if (maxVote < entry.getValue()) {
					maxVote = entry.getValue();
				}
			}
			System.out.println("max vote: " + maxVote);

			//Every item with that vote gets stored in the "winners" array list.
			for(Map.Entry<String, Integer> entry : curr.entrySet()) {
				if (maxVote == entry.getValue()) {
					System.out.println("yo");
					winners.add(entry.getKey());
				}
			}
			
			//Convert winner array to JSON.
			JSONObject json = new JSONObject();
			json.put("winners", winners);
			System.out.println("My winners pt 2: " + json.toString());
						
			Room r = rooms.get(code);
			r.someoneFinished();
			
			//if all the users aren't finished, keep on going
			if (!r.everyoneReady()) {
				System.out.println("true");
				r.broadcast("no", username);
			} else {	//else send the winner
				r.broadcastAll(json.toString());
			}
		}
		else if(curr.containsKey(message)) {
			System.out.println("Old vote count: " + curr.get(message));
			curr.replace(message, curr.get(message) + 1);
			System.out.println("New vote count: " + curr.get(message));
		}
		else {
			curr.put(message, Integer.valueOf(1));
		}
	}
}