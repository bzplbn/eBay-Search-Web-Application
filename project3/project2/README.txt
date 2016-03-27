

1. Relations according to our relational schema:
   
   (1)Item(ItemID, Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Latitude, Longitude, Location, Country, Started, Ends,Seller, Description) Key: ItemID

   (2)ItemCategory(ItemId, Category) Key:(itemID, Category)
   
   (3)Bid(UserID, ItemID, Time, Amount) Key: (UserID, Time)
 
   (4)Bidder(UserID, Rating, Country, Location) Key: UserID

   (5)Seller(UserID, Rating) Key:UserID

2. The only functional dependencies in each relation above are for keys.

3. This schema is in BCNF because all the functional dependencies for each table contains a key in the left hand side.

4. This schema is also in 4NF.
 

