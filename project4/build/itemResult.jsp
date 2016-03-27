<!DOCTYPE html>
<%@ page import="edu.ucla.cs.cs144.ItemResult, edu.ucla.cs.cs144.BidResult, java.util.*, java.lang.*" %>
<html>
<head>
    <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <title>Item Search Results</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
	<style type="text/css"> 
	  html { height: 100% } 
	  body { height: 100%; margin: 0px; padding: 0px } 
	  #map_canvas { height: 40em; left: 20%; width: 60%} 
	</style> 
	<!-- add google map here -->
    <script type="text/javascript" async defer
      src="https://maps.googleapis.com/maps/api/js?callback=initialize"> 
	</script> 
	<script type="text/javascript">
	  	function initialize() { 
            var latitude = document.getElementById('Latitude').value;
            var longitude = document.getElementById('Longitude').value;
            var location = document.getElementById('Location').value;
            var itemName = document.getElementById('ItemName').value;
            if(latitude != 0 && longitude != 0)
            {
    		    var latLng = new google.maps.LatLng(latitude,longitude); 
    		    var myOptions = { 
    		      zoom: 14, // default is 8  
    		      center: latLng, 
    		      mapTypeId: google.maps.MapTypeId.ROADMAP }; 
    		    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions); 
    		    var coordInfoWindow = new google.maps.InfoWindow();
    	        coordInfoWindow.setContent(createInfoWindowContent(latLng, map.getZoom()));
    	        coordInfoWindow.setPosition(latLng);
    	        coordInfoWindow.open(map);

            	map.addListener('zoom_changed', function() {
              		coordInfoWindow.setContent(createInfoWindowContent(latLng, map.getZoom()));
              		coordInfoWindow.open(map);});
            }
            else
            {
                var myOptions = { 
                  zoom: 14, // default is 8  
                  mapTypeId: google.maps.MapTypeId.ROADMAP }; 
                var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
                var geocoder = new google.maps.Geocoder(); 
                var res = geocodeAddress(geocoder, map, location);
            }
	  	}

	  	var TILE_SIZE = 256;
	  	function createInfoWindowContent(latLng, zoom) {
            var scale = 1 << zoom;

            var worldCoordinate = project(latLng);

            var pixelCoordinate = new google.maps.Point(
                Math.floor(worldCoordinate.x * scale),
                Math.floor(worldCoordinate.y * scale));

            var tileCoordinate = new google.maps.Point(
                Math.floor(worldCoordinate.x * scale / TILE_SIZE),
                Math.floor(worldCoordinate.y * scale / TILE_SIZE));

            return [
                'ItemName: ' + document.getElementById('ItemName').value,
                'Location: ' + document.getElementById('Location').value,
                'LatLng: ' + latLng
            ].join('<br>');
        }

        function project(latLng) {
            var siny = Math.sin(latLng.lat() * Math.PI / 180);

            // Truncating to 0.9999 effectively limits latitude to 89.189. This is
            // about a third of a tile past the edge of the world tile.
            siny = Math.min(Math.max(siny, -0.9999), 0.9999);

            return new google.maps.Point(
                TILE_SIZE * (0.5 + latLng.lng() / 360),
                TILE_SIZE * (0.5 - Math.log((1 + siny) / (1 - siny)) / (4 * Math.PI)));
        }

        function geocodeAddress(geocoder, resultsMap, address) {
            geocoder.geocode({'address': address}, function(results, status) {
                if (status === google.maps.GeocoderStatus.OK) {
                  resultsMap.setCenter(results[0].geometry.location);
                  var marker = new google.maps.Marker({
                    map: resultsMap
                });

                var coordInfoWindow = new google.maps.InfoWindow();
                var latLng = results[0].geometry.location;
                coordInfoWindow.setContent(createInfoWindowContent(latLng, resultsMap.getZoom()));
                coordInfoWindow.setPosition(latLng);
                coordInfoWindow.open(resultsMap);

                resultsMap.addListener('zoom_changed', function() {
                    coordInfoWindow.setContent(createInfoWindowContent(latLng, resultsMap.getZoom()));
                    coordInfoWindow.open(resultsMap);});
                } 
                else
                {
                    var newmap = new google.maps.Map(document.getElementById('map_canvas'), {zoom: 1,center: {lat: 30, lng: -85}});
                    var marker = new google.maps.Marker({
                        map: newmap
                    });

                }
            });
        }
	</script> 
</head>

<body onload="initialize()">
    <a id = "home" role = "button" class = "btn btn-success" href = "index.html">Home</a>
    <h1 class="text-center">Item Search</h1>

    <form action = "/eBay/item" class = "text-center" method = "GET">
        <span>Please enter an item ID to search for:</span> <br/>
        <input type = "text" name = "id" placeholder = "Enter an itemID..."/>
        <input class="btn btn-success" role="button" type = "submit" value = "GO">
    </form>
    
    <%
    Boolean hasItem = (Boolean) request.getAttribute("hasItem");
    if(hasItem){
    %>
        <h3 class="text-center">Basic Information for Item "<%= request.getParameter("id") %>" </h1>
    
        <%
             ItemResult item = (ItemResult)request.getAttribute("itemInfo");
        %>
        <input type="hidden" value="<%=item.getLatitude()%>" id="Latitude"/>
        <input type="hidden" value="<%=item.getLongitude()%>" id="Longitude"/>
        <input type="hidden" value="<%=item.getLocation()%>" id="Location"/>
        <input type="hidden" value="<%=item.getName()%>" id="ItemName"/>
        <table border = "1" width = "80%"  class="table table-striped">
            <tr>

                <th width = "20%"></th>
                <th width = "80%"></th>

            </tr>
            <tr>

                <td><strong> ItemID </strong></td>
                <td><%=item.getItemID()%></td>  

            </tr>
            <tr>

                <td><strong>Name</strong></td>
                <td><%=item.getName()%></td>  

            </tr>
            <tr>

                <td><strong>Category</strong></td>
                <%
                int count = item.getCategoriesArray().length;
                String res = "";
                for(int i = 0; i < count; i++){
                res += item.getCategoriesArray()[i] + " | ";
                }
                %>
                <td><%=res%></td> 

            </tr>
            <tr>

                <td><strong>Currently</strong></td>
                <td><%=item.getCurrently()%></td>  

            </tr>
            <tr>

                <td><strong>Buy Price</strong></td>
                <% if(!item.getBuyPrice().equals("")){ %>
                <td><%=item.getBuyPrice()%></td>
                <% } else { %>
                <td> NaN </td>
                <% } %>

            </tr>
            <tr>

                <td><strong>First Bid</strong></td>
                <td><%= item.getFirstBid() %></td>

            </tr>
            <tr>

                <td><strong>Number of Bids</strong></td>
                <td><%=item.getNumberOfBids()%></td>

            </tr>
            <tr>

                <td><strong>Location</strong></td>
                <td>
                    <ul>
                            <li><%=item.getLocation()%></li>

                            <%if(!item.getLatitude().equals("")){%>
                            <li>Latitude:<%=item.getLatitude()%></li>
                            <% } else { %>
                            <li>Latitude: NaN </li>
                            <% } %>

                            <%if(!item.getLongitude().equals("")){%>
                            <li>Longitude:<%=item.getLongitude()%></li>
                            <% } else { %>
                            <li>Longitude: NaN </li>
                            <% } %>
                    </ul>
                </td>

            </tr>
            <tr>

                <td><strong>Country</strong></td>
                <td><%=item.getCountry()%></td>

            </tr>
            <tr>
            
                <td><strong>Started</strong></td>
                <td><%=item.getStarted()%></td>

            </tr>
            <tr>

                <td><strong>Ends</strong></td>
                <td><%=item.getEnds()%></td>

            </tr>
            <tr>

                <td><strong>Seller</strong></td>
                <td>

                    <ul>

                        <li>UserID: <%=item.getSellerID()%></li>
                        <li>Rating: <%=item.getSellerRating()%></li>
                  
                    </ul>

                </td>

            </tr>
            <tr>
            
                <td><strong>Description</strong></td>
                <td><%= item.getDescription() %></td>

            </tr>

        </table>

        <br>
        
        <%if(!item.getNumberOfBids().equals("0")){%>
        <h3 class = "text-center"> Bids Information for Item "<%= request.getParameter("id") %>" </h3>
        <table border = "1" width = "80%"  class="table table-striped">
            <tr>

                <th>Bidder ID</th>
                <th>Bidder Rating</th>
                <th>Bidder Loation</th>
                <th>Bidder Country</th>
                <th>Bid Time</th>
                <th>Bid Amount</th>

            </tr>
        
                <%
                BidResult[] BidResults = (BidResult[])item.getBids();
                for(int i = 0; i < BidResults.length; i++) { 
                %>
            <tr>

                <td><%= BidResults[i].getUserID()%></td>
                <td><%= BidResults[i].getRating()%></td>
                <td><%= BidResults[i].getLocation()%></td>
                <td><%= BidResults[i].getCountry()%></td>
                <td><%= BidResults[i].getTime()%></td>
                <td><%= BidResults[i].getAmount()%></td>

            </tr>
            <% } %>
        </table>
        <%}%>
       

        <br>
     
        <h3 class = "text-center"> Location Information for Item "<%= request.getParameter("id") %>" </h3>
        <div id="map_canvas" ></div>

        

        <% } else { %>

            <h3 class = "text-center"> Oops... No record for this item. Please enter a vaild Item ID.</h3>
        <% } %>
        
        <br>
        <div id = "footer">&copy; Copyright by Zhanwei Ye and Hanjing Fang, UCLA</div>

</body>
</html>