<!DOCTYPE html>
<%@ page import="edu.ucla.cs.cs144.ItemResult, edu.ucla.cs.cs144.BidResult, java.util.*, java.lang.*" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Payment Confirmation</title>
</head>

<body>
    <% String httpURL = "http://" + request.getServerName() + ":1448" + request.getContextPath() + "/index.html"; %>
    <a id = "home" role = "button" class = "btn btn-success" href = "<%=httpURL %>">Home</a>
    <h1 class="text-center">Payment Confirmation</h1>
    <br>
    <br>
    <br>
    <br>
    <br>
    <h2 class="text-center">Thank you for your purchase!<img src="img/smile.png" height="45" width="45"></h2>
    <br>
  <!--   <div class="text-center"><strong> Item Name: </strong><%= request.getAttribute("itemName") %></div>
    <div class="text-center"><strong> Item ID:   </strong><%= request.getAttribute("ID") %></div>
    <div class="text-center"><strong> Buy Price: </strong><%= request.getAttribute("buyPrice") %></div>
    <div class="text-center"><strong> Credit Card Number: </strong><%= request.getAttribute("creditCardNum") %></div>
    <div class="text-center"><strong> Time: </strong><%= request.getAttribute("time") %></div>
 -->
  
    <table   width = "60%" ealign="center" >
    <tr>

        <th width = "35%"></th>
        <th width = "65%"></th>

    </tr>
    <tr>

        <td ><strong> Item Name </strong></td>
        <td><%=request.getAttribute("itemName") %></td>  

    </tr>
    <tr>

        <td ><strong>Item ID</strong></td>
        <td><%=request.getAttribute("ID")%></td>  

    </tr>
    <tr>

        <td><strong>Buy Price</strong></td>
        <td><%=request.getAttribute("buyPrice") %></td>  

    </tr>
    <tr>

        <td><strong>Credit Card Number</strong></td>
        <td><%=request.getAttribute("creditCardNum") %></td>  

    </tr>
    <tr>

        <td><strong>Time</strong></td>
        <td><%=request.getAttribute("time") %></td>  
     
    </tr>

</table>
<div id = "footer">&copy; Copyright by Zhanwei Ye and Hanjing Fang, UCLA</div>

</body>
</html>