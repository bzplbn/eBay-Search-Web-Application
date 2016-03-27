package edu.ucla.cs.cs144;

public class BidResult {
    private String UserID;
    private String Rating;
    private String Location;
    private String Country;
    private String Time;
    private String Amount;
    
    public BidResult() {}
    
    public BidResult(String UserID, String Rating, String Location, String Country, String Time, String Amount) {
        this.UserID = UserID;
        this.Rating = Rating;
        this.Time = Time;
        this.Amount = Amount;
        
        if(!Location.equals(null)){
            this.Location = Location;
        } else {
            this.Location = "";
        }
        
        if(!Country.equals(null)){
            this.Country = Country;
        } else {
            this.Country = "";
        }
        
    }
    
    public String getUserID() {
        return UserID;
    }
    
    public String getRating() {
        return Rating;
    }
    
    public String getLocation() {
        return Location;
    }
    
    public String getCountry() {
        return Country;
    }
    public String getTime() {
        return Time;
    }
    
    public String getAmount() {
        return Amount;
    }

    
}


