<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="main.java.salgrub.objects.*"%>
<%@page import="org.json.*" %>
<%@page import="java.util.HashSet" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Group Landing</title>
		
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/util.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/glp.css">
		
		<script type="text/javascript" src="js/mouse.js"></script>
		
		
		<%
		String code = request.getParameter("code");
		String username = request.getParameter("username");
		String master = request.getParameter("master");
		User user; 
		if(username.equals("Guest")){
			user = new GuestUser(username);
		}
		else {
			user = new LoggedInUser(username);
		}
		
		HashSet<String> dbs = user.getDealbreakers(); 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dbs", dbs);
		
		String jsonString = jsonObject.toString(); 
		%>
		
	</head>
	<body>
		
		<div class = "groupTags left">
			<div class = "tagCols" style= "float: left;" id="goodCol"> 
				<h2> I want... </h2>
				<div class = "navBar">
					<input id= "goodTag" type="text" placeholder="Search.."/>
					<button id="addGItem" class = "btn-1">
						<a><span>Add</span></a>
					</button>
				</div>

				<div class = "tagsWrapper"></div>

				<div class="scrollbar goodScroll" >
					<ol id="goodList">
						
					</ol>
				</div>
				
			</div><!-- each section has a title, a sticky search bar, and a page slider to slide down the list of tags -->
		</div>

		<div class="groupTags right">
			<div class="logout"> 
				<button type="button" class = "btn-2" id="logout" style="Display: initial;">
						<a href="/baby" ><span >Logout</span></a>
				</button>
			</div>
			<div class = "tagCols bad" style = "float: right;" id="badCol">
				<h2> I do not want... </h2>
				<div class = "navBar">
					<input id= "badTag" type="text" placeholder="Search.." />
					<button id="addBItem" class = "btn-1">
						<a><span>Add</span></a>
					</button>
				</div>
				
				<div class = "tagsWrapper">
					
				</div>
				
				<div class="scrollbar badScroll">
					<ol id="badList">
						
					</ol>
				</div>
			</div>
			<div class = "tagCols bCol">
			
				<div id="waiting"></div>
				
				<button type="button" class="btn-2" id="finishedButton">
					<a><span>finished?</span></a>
				</button>
			</div>
		</div>
			
			
		</div>
		
		<script defer>
			window.onload = (() => {
				
				const dealTag = document.querySelector("#goodList"); //creates a list for deals
				const nodealTag = document.querySelector("#badList"); //creates a list const for noDeals
				
				var data;
				var socket;
				var address = "ws://" + window.location.host + "/baby/glp/" + "<%=code%>/<%=username%>/<%=master%>";
				console.log(address);
				socket = new WebSocket(address);
				
				var lat;
				var longitude;
				
				//Turn the JSON into array to display
				var x = JSON.parse('<%=jsonString%>'); 
				var db = x.dbs; 
				console.log("hihihihihi");
				console.log(db); 
				//display
				for (i=0;i<db.length;i++)
				{
					let listItem = document.createElement("li"); //create a list item 
					listItem.textContent = db[i]; //list item set to given text
					nodealTag.appendChild(listItem);//adds to the list passed into the method(html)
				}
				
				//Pulling location data from the master.
				if(<%=master%> === true){
					if(navigator.geolocation){
						navigator.geolocation.getCurrentPosition(showPosition, showError);
					}
					else{
						lat = "34.0195613";
						longitude = "-118.2896171";
					}
				}
				
				function showPosition(position) {
						lat = position.coords.latitude
						longitude = position.coords.longitude;
				}
				
				function showError(error) {
					  switch(error.code) {
					    case error.PERMISSION_DENIED:
							lat = "34.0195613";
							longitude = "-118.2896171";
					    case error.POSITION_UNAVAILABLE:
							lat = "34.0195613";
							longitude = "-118.2896171";
					    case error.TIMEOUT:
							lat = "34.0195613";
							longitude = "-118.2896171";
					    case error.UNKNOWN_ERROR:
							lat = "34.0195613";
							longitude = "-118.2896171";
					  }
				}
				
				//Check if guest user. If yes, change visibility.
				if(<%=username.equals("Guest")%>){
					document.getElementById("logout").style.visibility = "hidden";
					document.getElementById("badCol").innerHTML = "<h2>Log in to add Dealbreakers!</h2>" + "<br/>";
				}
				
				//OnMessage callback whenever the server sends information.
				socket.onmessage = function(event) {
					msg = event.data;
                    msg = msg.replace(/(\r\n|\n|\r)/gm,"");
                    
					console.log(msg);
					let wait = 0;
					if(msg == "finished"){
						if(<%=master%> === true){
							var mymsg = "datapls,";
							mymsg += lat + "," + longitude;
							socket.send(mymsg);
						}
 					}
					else if(msg == "wait" && wait ==0){
						document.getElementById("finishedButton").innerHTML += "Waiting!" + "<br/>";
						document.getElementById("finishedButton").disabled = "disabled";
						wait = 1;
					}
					else{ //Requested data has been received.
						console.log("Received data.");
						var x = JSON.parse(event.data);
						var array = JSON.stringify(x);
						data = encodeURIComponent(array);
						moveOn();
					}
				}
				
				const addGoodTag = (tag) => {
					var message = "1,";
					message += tag;
					socket.send(message);
				}
				
				const addDealbreaker = (tag) => {
					var message = "3,";
					message += tag;
					socket.send(message);
				}
				
				const sendFinish = () => {
					console.log("hang tight!");
					socket.send("done");
				}
				
				const moveOn = () => {
					var url = "../Restaurant_Swiping/RestaurantDisplay.jsp?code=<%=code%>&username=<%=username%>&master=<%=master%>&data=";
					url += data;
					window.location.href = url;
				}
				
				/* Angie's Script */
				
				const appendListItem = (theList, itemTxt) => { //append list item 
					let listItem = document.createElement("li"); //create a list item 
					listItem.textContent = itemTxt; //list item set to given text
					theList.appendChild(listItem);//adds to the list passed into the method(html)
				};
				
				const addGoodItem = () => { //adds items to list object
					const inputGood = document.querySelector("#goodTag");
					
					const val = inputGood.value.trim();

					if (!val) { return alert("Please enter a good Yelp tag"); }

					if(goodTags.indexOf(val) === -1){ 
						goodTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
						appendListItem(dealTag, val);
						inputGood.value = "";
						addGoodTag(val);
					}else{
						return alert("Oops! No duplicates!");
					}
				};
				

				const addBadItem = () => { //adds items to list object
					const inputBad = document.querySelector("#badTag");
					
					const val = inputBad.value.trim();

					if (!val) { return alert("Please enter a bad Yelp tag"); }

					if(badTags.indexOf(val) === -1){ 
						badTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
						appendListItem(nodealTag, val);
						inputBad.value = "";
						addDealbreaker(val);
					}else{
						return alert("Oops! No duplicates!");//if there is a duplicate tag it will not be added
					}
				};
				
				// create good tags list
				let goodTags = [];
				goodTags.forEach(item => appendListItem(dealTag, item));

				let badTags = [];
				badTags.forEach(item => appendListItem(nodealTag, item));
					
				// add button handling
				document.querySelector("#addGItem").addEventListener("click", addGoodItem);
				if(<%=!username.equals("Guest")%>){
					document.querySelector("#addBItem").addEventListener("click", addBadItem);
				}
				document.querySelector("#finishedButton").addEventListener("click", sendFinish);
				
			})();
		</script>
		
	</body>
</html>