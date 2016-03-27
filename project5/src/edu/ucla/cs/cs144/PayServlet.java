package edu.ucla.cs.cs144;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PayServlet extends HttpServlet implements Servlet {
       
    public PayServlet() {}
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        try 
        {
            String itemID = request.getParameter("itemID");
            HttpSession session = request.getSession(true);
            
            HashMap<String, ItemResult> itemSessionInfo = (HashMap<String, ItemResult>)session.getAttribute("itemSessionInfo");
            if (itemSessionInfo.containsKey(itemID))
            {
                ItemResult itemRes = itemSessionInfo.get(itemID);
                request.setAttribute("itemName", itemRes.getName());
                request.setAttribute("ID", itemRes.getItemID());
                request.setAttribute("buyPrice", itemRes.getBuyPrice());
                request.getRequestDispatcher("/payNow.jsp").forward(request, response);
            }
        } catch (Exception e){
            
        }
    }
}
