/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package main.java.salgrub;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VerifyLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PreparedStatement st;
    
    public void init() throws ServletException{
    	System.out.println("Here.");
        initializeJdbc();
    }
     
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Post request successful.");
        
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
                
        try{
            st.setString(1, username);
            st.setString(2, password);
            
            System.out.println("Statement set to: " + st);
            
            ResultSet rs =  st.executeQuery();

            if(!rs.next()){
                System.out.println("Successful failure!");
                out.println("n");
                out.flush();
            }
            else{
                System.out.println("Successful login.");
                out.println("y");
                out.flush();
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }catch(Error e){
            System.out.println("Error?");
            e.printStackTrace();
        }
        System.out.println("Done finally!");
    }
    
    //Initializes the JDBC driver.
    private void initializeJdbc(){
    	System.out.println("Caught.");

        try{
            //Loading.
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to driver.");
                    
            //Establish connection.
            Connection connection = DriverManager.getConnection("jdbc:mysql://salgrub.cj3xrat8scfk.us-west-1.rds.amazonaws.com:3306/salgrub"
                                                                , "jleodones", "salgrub!");
            System.out.println("Connection established.");
            
            //Prepare statement.
            st = connection.prepareStatement("Select * from Users where username = ? and password = ?");
            System.out.println("Statement set to: " + st);
            
        }catch(ClassNotFoundException cnfe){
            //System.out.println("hi");
            cnfe.printStackTrace();
        } catch (SQLException ex) {
            //System.out.println("Something's up");
            ex.printStackTrace();
            //Logger.getLogger(VerifyLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception e){
            //System.out.println("hm?");
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
