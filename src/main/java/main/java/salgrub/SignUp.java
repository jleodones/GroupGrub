package main.java.salgrub;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

public class SignUp extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	out.println("yo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        
        
        String db = "jdbc:mysql://salgrub.cj3xrat8scfk.us-west-1.rds.amazonaws.com:3306/salgrub";
		String user = "jleodones";
		String pwd = "salgrub!";
		String sql = "INSERT INTO Users (username, password) VALUES (?,?)";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setString(2, pass);
			int i = ps.executeUpdate(); //the number of rows affected
			if(i!=0) {
				System.out.println("success"); 
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}

		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
    }
}