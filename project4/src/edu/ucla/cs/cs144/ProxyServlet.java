package edu.ucla.cs.cs144;

import java.io.*;
import java.net.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();

        String basicURL = "http://google.com/complete/search?output=toolbar&";
        String query = String.format("q=%s", URLEncoder.encode(request.getParameter("q"), "UTF-8"));
        
        URLConnection connection = new URL(basicURL + query).openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-8");
				
				
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer buf = new StringBuffer();
			 
		while ((inputLine = in.readLine()) != null) 
		{
			buf.append(inputLine);
		}

        out.println(buf.toString());
    }
}
