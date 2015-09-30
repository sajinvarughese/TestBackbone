package org.ats.java;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ats.dbconnection.DataSource;
import org.json.JSONObject;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
		String jsonString = request.getParameter("json");
		System.out.println(jsonString);
		PrintWriter out = null;
		try{
		out = response.getWriter();
		JSONObject userdetails = new JSONObject(jsonString);
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
		
		String query = "select * from user where email = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, email);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			out.print("Success");
		}else{
			out.print("error");
		}
		
		}catch(Exception e){
			out.print("error");
			System.out.println(e);
		}
		
	}
	
}
