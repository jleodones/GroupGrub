<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Lobby</title>
		
		<script>
		var socket = new WebSocket("/")
		</script>
	</head>
	<body>
		<%
		/* Getting values from request. */
		String code = request.getParameter("code");
		String username = request.getParameter("username");
		String m = request.getParameter("master");
		boolean master;
		if(m.equals("true")){
			master = true;
		}
		else{
			master = false;
		}
		%>
		
		<h1>Group code: <%= code %></h1>
		
	</body>
</html>