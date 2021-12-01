<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="tagging.*"%>
<%@page import="java.util.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
	<script language="javascript">
        var restaurantCount = 0;
        
        var restName;
        var restID;
        var restImg;
        var restClosed;
        var restRating;
        var restPrice;
        var restDistance;
        
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
                want.add("hamburgers");
                
                ArrayList<String> noWant = new ArrayList<String>();
                noWant.add("fish");
                
                Tagger tag = new Tagger(want, noWant);
                
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
        	if(restaurantCount < restName.length) {
            	document.getElementById("restaurant_view").innerHTML = restName[restaurantCount] + "<br>"
            	+ restID[restaurantCount] + "<br>" + restImg[restaurantCount] + "<br>"
            	+ restClosed[restaurantCount] + "<br>" + restRating[restaurantCount] + "<br>"
            	+ restPrice[restaurantCount] + "<br>" + restDistance[restaurantCount];
            	
            	restaurantCount += 1;
            }
        }
        
        //loads new restaurant
        function nextRestaurant() {
            loadNewRestaurant();
        }
    </script>
    <head>
        <meta charset="UTF-8">
        <title>Restaurant Display</title>
    </head> 
    <body onload="getRestaurants()">
        <div>
            <p id="restaurant_view" onload="loadNewRestaurant()">
                
            </p>
            <button onclick="nextRestaurant()">ENTER</button>
        </div>
    </body>
    
    
</html>