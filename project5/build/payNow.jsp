<!DOCTYPE html>
<%@ page import="edu.ucla.cs.cs144.ItemResult, edu.ucla.cs.cs144.BidResult, java.util.*, java.lang.*" %>
<html>
<head>
    <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Pay Now</title>
</head>

<body>
    <a id = "home" role = "button" class = "btn btn-success" href = "index.html">Home</a>
    <h1 class="text-center">Please enter your payment information</h1>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    
    <table align="center" >
    <tr>

        <th width = "35%"></th>
        <th width = "65%"></th>

    </tr>
    <tr>

        <td><strong> Item Name </strong></td>
        <td><%=request.getAttribute("itemName") %></td>  

    </tr>
    <tr>

        <td><strong>Item ID</strong></td>
        <td><%=request.getAttribute("ID")%></td>  

    </tr>
    <tr>

        <td><strong>Buy Price</strong></td>
        <td><%=request.getAttribute("buyPrice") %></td>  

    </tr>
    <tr>

        <td><strong>Credit Card Number</strong></td>
        <td>
            <% String httpsURL = "https://" + request.getServerName() + ":8443" + request.getContextPath() + "/confirm"; %>
            <form action = "<%= httpsURL %>"  method = "POST">
                <input type = "text" name = "creditCardNum" placeholder = "Enter your credit card # here..." autocomplete="off" style = "width:200px;"/>
                <input type = "hidden" name = "itemID" value = "<%= request.getAttribute("ID") %>">
                <input class = "btn btn-success" role = "button" type = "submit" value = "Pay">
            </form>
        </td>

    </tr>

</table>
<div id = "footer">&copy; Copyright by Zhanwei Ye and Hanjing Fang, UCLA</div>


    
</body>
</html>