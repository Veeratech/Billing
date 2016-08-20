package com.veeratech.billgen;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BillController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		int value = (int) req.getAttribute("unitsconsumed");

		ServletContext context = getServletContext();

		String units = context.getInitParameter("units");
		float perunit = Float.parseFloat(units);

		double bill_amount = value * perunit;

		pw.println("<html><body><center>< Total bill amount is " + bill_amount
				+ "</center></body></html>");

	}

}
