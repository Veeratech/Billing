package com.veeratech.billgen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BillGen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	RequestDispatcher rd=null;
	PrintWriter pw=null;
	int no_of_units_consumed=0;
	
	String query="select unitsconsumed from chandru.bill where cname=? and rr=?";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "rudra");
			pstmt=con.prepareStatement(query);
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		   String user=req.getParameter("cname");
		   String rr=req.getParameter("rr");
		
		    int rrnum=Integer.parseInt(rr);
		    pw=resp.getWriter();
		
		try {
			pstmt.setString(1, user);
			pstmt.setInt(2,rrnum);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				no_of_units_consumed=rs.getInt(1);
				pw.println("<html><center>Welcome to Billing Section</center></body></html>");
				
				req.setAttribute("unitsconsumed", no_of_units_consumed);
				rd=req.getRequestDispatcher("/S2");
				rd.include(req, resp);
				
				pw.println("<html><body><center>Please pay the bill before 5th of every month</center></body></html>");
				pw.flush();
				pw.close();
			}else{
				rd=req.getRequestDispatcher("error.html");
				rd.forward(req, resp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void destroy() {
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}}}
	