package main.java.salgrub;

import java.io.*;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint(value = "/swiping/{code}/{username}")
public class ResultsSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Integer> votes = new HashMap<String, Integer>();

	public int getCount() {
		return sessionVector.size(); 
	}
	
	@OnOpen
	public void open(Session session) {
		//Connection is made.
		System.out.println("Connection made!");
		sessionVector.add(session);
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
	public void onMessage(String message){
		if(message.equals("abcdefghijk")) {
			Integer max = Integer.valueOf(0);
			String winner = "";
			for(Map.Entry<String,Integer> entry : votes.entrySet()) {
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
	}
}