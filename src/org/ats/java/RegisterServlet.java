package org.ats.java;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ats.dbconnection.DataSource;
import org.json.JSONObject;

public class RegisterServlet extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response){
			PrintWriter out = null;
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
			    BufferedReader reader = request.getReader();
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception e) { /*report an error*/ }
			  System.out.println(jb.toString());
			
		try{  
		out = response.getWriter();
		JSONObject userdetails = new JSONObject(jb.toString());
		String username = userdetails.getString("name");
		String email = userdetails.getString("email");
		String password = userdetails.getString("pwd");
		
		DataSource ds = null;
		Connection con = null;
		try {
			ds = DataSource.getInstance();
			con =  ds.getConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		String query = "Insert into user(username, email, password) values(?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, email);
		ps.setString(3, password);
		
		int a = ps.executeUpdate();
		
		if(a > 0){
			System.out.println("inserted successfully");
			out.print("{\"status\" : \"success\"}");
		}else{
			out.print("{\"status\" : \"error\"}");
		}
		
		}catch(Exception e){
			System.out.println(e);
			out.print("{\"status\" : \"error\"}");
		}
		
	}
	
}
