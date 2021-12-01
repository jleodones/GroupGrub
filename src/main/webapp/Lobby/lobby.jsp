<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Lobby</title>
		<%
		/* Getting values from request. */
		String code = request.getParameter("code");
		System.out.println(code);
		
		String username = request.getParameter("username");
		System.out.println(username);

		String m = request.getParameter("master");
		System.out.println(m);
		
		boolean master;
		if(m.equals("true")){
			master = true;
		}
		else{
			master = false;
		}
		%>
		
		<script>
			var socket;
			function connectToServer() {
/* 				var address = "ws://";
				var location = window.location.host + window.location.pathname
								+ window.location.search;
				console.log(location);
				address += location;
				address += "/lobby"; */
				var address = "ws://localhost:8080/baby/lobby/" + "<%=code%>/" + "<%=username%>";
				console.log(address);
				socket = new WebSocket(address);
				
				socket.onopen = function(event) {
					document.getElementById("mylobby").innerHTML += "Connected!" +" <br/>";
				}
				
				socket.onmessage = function(event) {
					document.getElementById("mylobby").innerHTML += event.data + "<br/>";
				}

				socket.onclose = function(event) {
					document.getElementById("mylobby").innerHTML += "Disconnected!";
				}
			}
		</script>
	</head>
	<body onload="connectToServer()">	
		<h1>Group code: <%= code %></h1>
		<br/>
		Currently in lobby:
		<br/>
		<div id="mylobby"></div>
	</body>
</html>