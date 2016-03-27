<!DOCTYPE html>
<%@ page import="edu.ucla.cs.cs144.SearchResult, edu.ucla.cs.cs144.ItemResult, java.util.*, java.lang.*" %>
<html>
<head>
    <script type="text/javascript" src="AutoSuggestClient.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript">
        window.onload = function () {
            var oTextbox = new AutoSuggestControl(document.getElementById("keyword")); 
            }
    </script>
        <title>Keyword Search Results</title>
</head>

<body>
    <a id = "home" role = "button" class = "btn btn-success" href = "index.html">Home</a>
    <h1 class="text-center">Keyword Search</h1>

    <form class = "text-center" action = "/eBay/search" method = "GET">
        <span>Please enter keywords to search for:</span> <br/>
        <input type = "text" name = "q" id = "keyword" placeholder = "Enter a keyword..." autocomplete="off">
        <input name="numResultsToSkip" type="hidden" value="0"/>
        <input name="numResultsToReturn" type="hidden" value="10"/>
        <input class="btn btn-success" role = "button" type = "submit" value = "GO">
    </form>
    <%
    if((Boolean)request.getAttribute("hasResult")){
    %>
    <h3 class="text-center">Keyword Search Results for "<%= request.getParameter("q") %>" </h3>
    
    <table border = "1" width = "100%" class="table table-striped">
        <tr>
            <th width= "20%"> ItemID </th>
            <th width = "80%"> Name </th>
        </tr>

        <%
            SearchResult[] searchResults = (SearchResult[])request.getAttribute("results");
            SearchResult[] searchResultsAll= (SearchResult[])request.getAttribute("resultsTotal");
            int numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
            int numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
            int numResultsTotal = searchResults.length;
            String query = request.getParameter("q");

            int loopLength = numResultsTotal > numResultsToReturn ? numResultsToReturn : numResultsTotal;
            for(int i = 0; i < loopLength; i++) {
            String itemId = searchResults[i].getItemID();
            String itemName = searchResults[i].getName();
        %>
                <tr>
                    <td><a href= <%= "item?id=" + itemId %>> <%= itemId %></a></td>
                    <td><%= itemName %></td>
                </tr>

        <% } %>
    </table>

    <table width = "100%">
        <tr>
            <%
                if (numResultsToSkip >= numResultsToReturn) {
                    int preSkip = numResultsToSkip - numResultsToReturn;
            %>
                <td width = "48%"><a class="btn btn-primary btn-lg" role="button" href = <%="search?q=" + query + "&numResultsToSkip=" + preSkip + "&numResultsToReturn=" + numResultsToReturn %>> Previous </a></td>
            <% } else { %>
                <td width = "48%"> </td>
            <%}%>
            <%

                int pageCount =  searchResultsAll.length / 10 + 1;
                int pageCurrent = numResultsToSkip / 10 + 1;
            %>
               <td width = "46%"><%=pageCurrent%>/<%=pageCount%></td>
            <%
                if (numResultsTotal > numResultsToReturn) {
            %>
                <td width = "6%"><a class="btn btn-primary btn-lg" role="button" href = <%="search?q=" + query + "&numResultsToSkip=" + (numResultsToSkip + numResultsToReturn) + "&numResultsToReturn=" + numResultsToReturn %>>  Next  </a></td>
            <% } %>   
        </tr>
    </table>
    <%} else { %>
     <h3 class = "text-center"> Oops... No record for this keyword. Please enter a vaild keyword.</h3>
    <% } %>
    
    <br>
    <div id = "footer">&copy; Copyright by Zhanwei Ye and Hanjing Fang, UCLA</div>

</body>
</html>