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
		
		%>
		
		<script>
			var socket;
			
			/* Displays the ready button depending on if the person is the master or not. */
			function buttonHandle(){
				if(<%=m.equals("false")%>){
					console.log("false master");
					document.getElementById("readyButton").style.display = "none";
				}
				else{
					console.log("true master");
				}
			}

			function connectToServer() {
				var address = "ws://" + window.location.host + "/baby/lobby/" + "<%=code%>/" + "<%=username%>";
				console.log(address);
				socket = new WebSocket(address);
				
				socket.onopen = function(event) {
					document.getElementById("mylobby").innerHTML += "Connected!" +" <br/>";
				}
				
				socket.onmessage = function(event) {
					msg = event.data;
                    msg = msg.replace(/(\r\n|\n|\r)/gm,"");
                    
					console.log(msg);
					if(msg == "ready"){
						window.location.href = "../GLP/Loggedin.jsp?code=<%=code%>&username=<%=username%>&master=<%=m%>";

 					}
					else{
						document.getElementById("mylobby").innerHTML += msg + "<br/>";
					}
				}
				
				socket.onclose = function(event) {
					document.getElementById("mylobby").innerHTML += "Disconnected!";
				}
			}
			
			function move(){
				console.log("yo");
				socket.send("<%=code%>, ready");				
			}
			
		</script>
	</head>
	<body onload="connectToServer(); buttonHandle();">	
		<h1>Group code: <%= code %></h1>
		<br/>
		<div id="mylobby">
			Currently in lobby:
		</div>
		<button id="readyButton" type="button" onclick="move();">
			Ready?
		</button>
	</body>
</html>