package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        try {
            
            int numResultsToSkipDefault = 0;
            int numResultsToReturnDefault = 10;
            int numResultsToSkip = numResultsToSkipDefault;
            int numResultsToReturn = numResultsToReturnDefault;
            String query = "";
            
            //obtain query, numResultsToSkip and numResultsToReturn
            if(!request.getParameter("q").equals(null)){
                query = request.getParameter("q");
                query = URLEncoder.encode(query);
                request.setAttribute("query",query);
            }
            
            if(!request.getParameter("numResultsToSkip").equals(null)){
                numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
            } else {
                numResultsToSkip = numResultsToSkipDefault;
            }
            
            if(!request.getParameter("numResultsToReturn").equals(null)){
                numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
            } else {
                numResultsToReturn = numResultsToReturnDefault;
            }
            
            //Do basicSearch()
            AuctionSearchClient searchClient = new AuctionSearchClient();
            
            SearchResult[] results = searchClient.basicSearch(query, numResultsToSkip, numResultsToReturn + 1);
            SearchResult[] resultsTotal = searchClient.basicSearch(query, 0, 20000);
            Boolean hasResult = (results.length > 0);
            //request.setAttribute("query", query);
            //request.setAttribute("numResultsToSkip", numResultsToSkip);
            //request.setAttribute("numResultsToReturn", numResultsToReturn);
            request.setAttribute("results", results);
            request.setAttribute("resultsTotal", resultsTotal);
            request.setAttribute("hasResult", hasResult);
            request.getRequestDispatcher("/searchResults.jsp").forward(request, response);
            
            
        } catch (Exception e){
            
        }
    }
}
