package main.java.salgrub.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class LoggedInUser extends User {
	private PreparedStatement st1;
    private PreparedStatement st2; 
 
	private String pastRestID; 
	
	public LoggedInUser(String id) {
		super(id);
		dealBreakers = new HashSet<String>(); 
			
		//dealbreakers: read in from databse using jdbc 
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to driver.");
                    
            //Establish connection.
            Connection connection = DriverManager.getConnection("jdbc:mysql://salgrub.cj3xrat8scfk.us-west-1.rds.amazonaws.com:3306/salgrub"
                                                                , "jleodones", "salgrub!");
            System.out.println("Connection established.");
            
            //Prepare statement.
            st1 = connection.prepareStatement("Select * from Users where username = ?");
            
            st1.setString(1, username);            
            System.out.println("Statement set to: " + st1);
            
            ResultSet rs1 =  st1.executeQuery();
            rs1.next();
        	String dbID = rs1.getString(3); 
        	
            	
        	String dbTableName = "dealbreaker" + dbID ; 
        	
            st2 = connection.prepareStatement("Select * from " + dbTableName);

            System.out.println("Statement set to: " + st2);
            ResultSet rs2 = st2.executeQuery(); 
            while(rs2.next()) {	
            	String dealbreaker = rs2.getString(2); 
            	this.dealBreakers.add(dealbreaker); 
            }

        }catch(ClassNotFoundException cnfe){
            //System.out.println("hi");
            cnfe.printStackTrace();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }catch(Error e){
            System.out.println("Error?");
            e.printStackTrace();
        }
		
		System.out.println(dealBreakers); 
	}
	
	
}