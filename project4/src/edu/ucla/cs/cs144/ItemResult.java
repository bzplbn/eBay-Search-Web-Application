package edu.ucla.cs.cs144;

public class ItemResult {
    private String ItemID;
    private String Name;
    private String[] categoriesArray;
    private String Currently;
    private String First_Bid;
    private String Buy_Price;
    private String Number_of_Bids;
    private BidResult[] bidsArray;
    private String Location;
    private String Country;
    private String Latitude;
    private String Longitude;
    private String Started;
    private String Ends;
    private String SellerID;
    private String SellerRating;
    private String Description;

    
    public ItemResult() {}
    
    public ItemResult(String ItemID, String Name, String[] categoriesArray, String Currently, String First_Bid, String Buy_Price, String Number_of_Bids, BidResult[] bidsArray, String Location, String Country, String Latitude, String Longitude, String Started, String Ends, String SellerID, String SellerRating, String Description) {
        
        this.ItemID = ItemID;
        this.Name = Name;
        this.categoriesArray = categoriesArray;
        this.Currently = Currently;
        this.First_Bid = First_Bid;
        this.Number_of_Bids = Number_of_Bids;
        this.bidsArray = bidsArray;
        this.Location = Location;
        this.Country = Country;
        this.Started = Started;
        this.Ends = Ends;
        this.SellerID = SellerID;
        this.SellerRating = SellerRating;
        this.Description = Description;
        
        if(!Buy_Price.equals(null)){
            this.Buy_Price = Buy_Price;
        }
        else
        {
            this.Buy_Price = "";
        }
        
        if(!Latitude.equals(null)){
            this.Latitude = Latitude;
        }
        else
        {
            this.Latitude = "";
        }
        
        if(!Longitude.equals(null)){
            this.Longitude = Longitude;
        }
        else
        {
            this.Longitude = "";
        }
        
    }
    
    public String getItemID() {
        return ItemID;
    }
    
    public String getName() {
        return Name;
    }
    
    public String[] getCategoriesArray() {
        return categoriesArray;
    }
    public String getCurrently() {
        return Currently;
    }
    
    public String getFirstBid() {
        return First_Bid;
    }
    
    public String getNumberOfBids() {
        return Number_of_Bids;
    }

    public BidResult[] getBids() {
        return bidsArray;
    }
    
    public String getLocation() {
        return Location;
    }
    
    public String getCountry() {
        return Country;
    }
    
    public String getStarted() {
        return Started;
    }
    
    public String getEnds() {
        return Ends;
    }
    
    public String getSellerID() {
        return SellerID;
    }
    
    public String getSellerRating() {
        return SellerRating;
    }

    public String getBuyPrice(){
        return Buy_Price;
    }

    public String getLatitude(){
        return Latitude;
    }

    public String getLongitude(){
        return Longitude;
    }
    
    public String getDescription() {
        return Description;
    }

   
}


