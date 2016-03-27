-- 1. Find the number of users in the database.
SELECT COUNT(*)
FROM (
	(SELECT UserID FROM Seller)  
	 UNION 
	(SELECT UserID FROM Bidder)
	) 
    AS s ;

-- 2. Find the number of items in "New York", (i.e., items whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the items in "New York" but not in "new york".
SELECT COUNT(DISTINCT ItemID)
FROM Item
WHERE Binary location = "New York"; -- convert to binary: to make it case sensitive


-- 3. Find the number of auctions belonging to exactly four categories.
SELECT COUNT(*)
FROM (
	SELECT ItemID
	FROM ItemCategory
    GROUP BY ItemID 
    Having COUNT(*) = 4
    ) 
    AS b;

-- 4. Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid.
-- YYYYMMDDHHMMSS
SELECT ItemID
FROM Item
WHERE Currently = (SELECT MAX(Currently)
                   FROM Item
                   WHERE Ends > "2001-12-20 00:00:01"
                   AND Started < "2001-12-20 00:00:01"
                   AND Number_of_Bids > 0)
AND Ends > "2001-12-20 00:00:01"
AND Started < "2001-12-20 00:00:00"
AND Number_of_Bids > 0;

-- 5. Find the number of sellers whose rating is higher than 1000.
SELECT COUNT(*)
FROM Seller
WHERE Rating > 1000;

-- 6. Find the number of users who are both sellers and bidders.
SELECT COUNT(UserID)
FROM Seller
WHERE UserID IN (
      SELECT UserID
      FROM Bidder);

-- 7. Find the number of categories that include at least one item with a bid of more than $100.
SELECT COUNT(DISTINCT Category)
FROM ItemCategory
WHERE ItemID IN (
	SELECT ItemID
	FROM Bid
	WHERE Amount > 100.00
);

