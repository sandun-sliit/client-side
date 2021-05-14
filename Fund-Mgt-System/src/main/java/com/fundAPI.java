package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fundAPI
 */
@WebServlet("/fundAPI")
public class fundAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	fund fundObj = new fund();
	
    public fundAPI() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = fundObj.insertfund(request.getParameter("resID"), request.getParameter("resName"),
				       request.getParameter("fAmount"), request.getParameter("subject"), request.getParameter("description"));
		response.getWriter().write(output);
		
		//request.getParameter("fundID")
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = fundObj.updateFund( paras.get("hidFundIDSave").toString(), paras.get("resID").toString(),
				paras.get("resName").toString(), paras.get("fAmount").toString(), paras.get("subject").toString(), paras.get("description").toString());
		response.getWriter().write(output);
	}
	
	//paras.get("resID").toString(),

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = fundObj.deleteFund(paras.get("fundID").toString());
		response.getWriter().write(output);
	}
	
	private static Map getParasMap(HttpServletRequest request) 
	{
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}	

}
