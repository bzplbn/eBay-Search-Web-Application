LOAD DATA 
     LOCAL INFILE 'Bidder.del'
     INTO TABLE Bidder
     FIELDS TERMINATED BY '|**|';

LOAD DATA 
     LOCAL INFILE 'Seller.del'
     INTO TABLE Seller
     FIELDS TERMINATED BY '|**|';

LOAD DATA 
     LOCAL INFILE 'Item.del'
     INTO TABLE Item
     FIELDS TERMINATED BY '|**|';

LOAD DATA 
     LOCAL INFILE 'ItemCategory.del'
     INTO TABLE ItemCategory
     FIELDS TERMINATED BY '|**|';

LOAD DATA 
     LOCAL INFILE 'Bid.del'
     INTO TABLE Bid
     FIELDS TERMINATED BY '|**|';