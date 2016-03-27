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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        // *** DEBUG **//
        // response.setContentType("text/html");
        // PrintWriter out = response.getWriter();

        String query = "";
        if(request.getParameter("id") != null){
            query = request.getParameter("id");
        }
        request.setAttribute("id", query);
        String xml = AuctionSearchClient.getXMLDataForItemId(query);

        //If no such item
        boolean hasItem = false;
        if(xml == null || xml.length() == 0){
            hasItem = false;
            request.setAttribute("hasItem", hasItem);
            request.getRequestDispatcher("/itemResult.jsp").forward(request, response);
            // *** DEBUG **//
            //out.println("<BODY><H1>" + "no such item" + "</H1><PRE>");
            return;
        }
        
        ItemResult itemRes = new ItemResult();
        
        //string to XML DOM doc, add a "parseElement" function to myParser class from Project 2 to parse the doc for a user-friendly output
        
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            Document doc = db.parse(is);
            
            Element root = doc.getDocumentElement();
            MyParser parser = new MyParser();
            //out.println("<BODY><H1>" + root.getAttribute("ItemID") + "</H1><PRE>");
            itemRes = parser.parseElement(root);
            hasItem = true;
            request.setAttribute("hasItem", hasItem);
            request.setAttribute("itemInfo", itemRes);

            request.getRequestDispatcher("/itemResult.jsp").forward(request, response);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
