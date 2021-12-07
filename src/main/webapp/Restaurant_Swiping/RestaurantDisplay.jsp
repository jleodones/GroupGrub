<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="main.java.salgrub.*"%>
<%@page import="main.java.salgrub.tagging.*"%>
<%@page import="java.util.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="com.google.gson.Gson"%>
<%@page import="org.json.*" %>

<!DOCTYPE html>
<html>

	<%
	String code = request.getParameter("code");
	String username = request.getParameter("username");
	String master = request.getParameter("master");
	String encoded = request.getParameter("data");
	%>
		
	<script language="javascript">
        var restaurantCount = 0;
        
        var restName;
        var restID;
        var restImg;
        var restClosed;
        var restRating;
        var restPrice;
        var restDistance;
        
        var socket;
        
		function connectToServer() {
			var address = "ws://" + window.location.host + "/baby/swiping/" + "<%=code%>/" + "<%=username%>";
			
			socket = new WebSocket(address);
			console.log("Socket Initializing");
 			getRestaurants();
 			
 			console.log("here");
 			
 			socket.onmessage = function(event) {
 				msg = event.data;
 	            msg = msg.replace(/(\r\n|\n|\r)/gm,"");
 	            console.log(event.data);
 	            
 				if (msg == "no") {
 					document.getElementById("yesButton").style.visibility = "hidden";
 					document.getElementById("noButton").style.visibility = "hidden";
 					document.getElementById("header").style.visibility = "visible";
 					document.getElementById("restaurant_view").innerHTML = "";
 				} else {
 					//Turn the JSON into a workable array.
					var x = JSON.parse(event.data);
					var json = {};
 					for(var i = 0; i < x.winners.length; i++){
						//Grab the index of the winner.
						var pos = restID.indexOf(x.winners[i]);
						
						var name = restName[pos];
						var id = x.winners[i];
						var img = restImg[pos];
						var rating = restRating[pos];
						var price = restPrice[pos];
						var distance = restDistance[pos];
						
						//Store this data in the JSON array.
 						json[name] = [name, id, img, rating, price, distance];
					}
					
					//Convert this into a JSON to send to the next page.
					var dataToSend = JSON.stringify(json);
					console.log(dataToSend);
					winner = encodeURIComponent(dataToSend);
					//Send to the next page.
 					window.location.href = "WinningRestaurant.jsp?winners=" + winner;
 				}
 			}
		}
		
        <%!
	        public String listAsStr(ArrayList<String> restaurantStrings) {
	        	StringBuffer sb = new StringBuffer();
	            sb.append("[");
	            for(int i=0; i<restaurantStrings.size(); i++){
	                sb.append("\"").append(restaurantStrings.get(i)).append("\"");
	                if(i+1 < restaurantStrings.size()){
	                     sb.append(",");
	                 }
	             }
	             sb.append("]");
	             
	             return sb.toString();
        	}
        %>
        
        function getRestaurants() {
            <% 
                /*
                Gets the list of restaurants according to the tags 
                */
                
                ArrayList<String> want = new ArrayList<String>();
           		ArrayList<String> noWant = new ArrayList<String>();
           		String latString;
           		String longString;
                
                System.out.println("Encoded: " + encoded);
                
                JSONObject jsnobject = new JSONObject(encoded);  
                JSONArray goodArray = jsnobject.getJSONArray("good");
                JSONArray badArray = jsnobject.getJSONArray("bad");
                String myLat = jsnobject.getString("latitude");
                String myLong = jsnobject.getString("longitude");
                
                Double latitude = Double.parseDouble(myLat);
                Double longitude = Double.parseDouble(myLong);
                
                for(int i = 0; i < goodArray.length(); i++){
                	want.add(goodArray.getString(i));
                }
                
                for(int i = 0; i < badArray.length(); i++){
                	noWant.add(badArray.getString(i));
                }
                
                System.out.println("Want: " + want.toString());
                System.out.println("No want: " + noWant.toString());

                
                Tagger tag = new Tagger(want, noWant, latitude, longitude);
                
                ArrayList<Restaurant> restaurants = tag.finalRestaurants();
                
                //converts the restaurant objects into strings
                ArrayList<String> restaurantName = new ArrayList<String>();
                ArrayList<String> restaurantID = new ArrayList<String>();
                ArrayList<String> restaurantImg = new ArrayList<String>();
                ArrayList<String> restaurantClosed = new ArrayList<String>();
                ArrayList<String> restaurantRating = new ArrayList<String>();
                ArrayList<String> restaurantPrice = new ArrayList<String>();
                ArrayList<String> restaurantDistance = new ArrayList<String>();

                for (Restaurant res : restaurants) {
                    restaurantName.add(res.getName());
                    restaurantID.add(res.getID());
                    restaurantImg.add(res.getImage_url());
                    restaurantClosed.add(Boolean.toString(res.getIs_closed()));
                    restaurantRating.add(Double.toString(res.getRating()));
                    restaurantPrice.add(res.getPrice());
                    restaurantDistance.add(Double.toString(res.getDistance()));
                }
                
                //string builder converts arraylist into array for javascript. Stores
                //array in 'listAsString'
                String jsonName = new Gson().toJson(listAsStr(restaurantName));
                String jsonID = new Gson().toJson(listAsStr(restaurantID));
                String jsonImg = new Gson().toJson(listAsStr(restaurantImg));
                String jsonClosed = new Gson().toJson(listAsStr(restaurantClosed));
                String jsonRating = new Gson().toJson(listAsStr(restaurantRating));
                String jsonPrice = new Gson().toJson(listAsStr(restaurantPrice));
                String jsonDistance = new Gson().toJson(listAsStr(restaurantDistance));
            %>
            
            restName = JSON.parse(<%=jsonName%>);
            restID = JSON.parse(<%=jsonID%>);
            restImg = JSON.parse(<%=jsonImg%>);
            restClosed = JSON.parse(<%=jsonClosed%>);
            restRating = JSON.parse(<%=jsonRating%>);
            restPrice = JSON.parse(<%=jsonPrice%>);
            restDistance = JSON.parse(<%=jsonDistance%>);

            
            loadNewRestaurant();
        }
        
        //loads new restaurant in the restaurant_view paragraph tag 
        function loadNewRestaurant() {
        	//10 is a placeholder for testing. Change this later.
        	if(restaurantCount < 3) {
            	document.getElementById("restaurant_view").innerHTML = restName[restaurantCount] + "<br>"
            	+ restID[restaurantCount] + "<br>" + restImg[restaurantCount] + "<br>"
            	+ restClosed[restaurantCount] + "<br>" + restRating[restaurantCount] + "<br>"
            	+ restPrice[restaurantCount] + "<br>" + restDistance[restaurantCount];
            }
        	else {
        		displayResults();
        	}
        }
        
        //loads new restaurant
        function yesRestaurant() {
        	console.log(restaurantCount);
        	restaurantCount += 1;
        	var messageString = restID[restaurantCount-1];
        	socket.send(messageString);

            loadNewRestaurant();
        }
        
        function noRestaurant() {
        	restaurantCount += 1;
        	loadNewRestaurant();
        }
        
        function displayResults() {
        	console.log("Sending to server socket abcdefg");
        	socket.send("abcdefghijk");
        }
        
    </script>
    <head>
        <meta charset="UTF-8">
        <title>Restaurant Display</title>
    </head> 
    <body onload="connectToServer()">
        <div>
        	<h1 id="header" style="visibility: hidden;"> Waiting For Everyone To Finish </h1>
            <p id="restaurant_view" onload="loadNewRestaurants()">
                
            </p>
            <button id="yesButton" onclick="yesRestaurant()" class="button">Yes</button>
            <button id="noButton" onclick="noRestaurant()" class="button">No</button>
        </div>
    </body>
    
    
</html>