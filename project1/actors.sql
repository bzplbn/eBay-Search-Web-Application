-- Create table
CREATE TABLE Actors
(
	Name VARCHAR(40),
	Movie VARCHAR(80),
	Year INTEGER,
	Role VARCHAR(40)
  
)ENGINE = INNODB;

-- Load data
LOAD DATA 
     LOCAL INFILE '~/data/actors.csv'
     INTO TABLE Actors
     FIELDS TERMINATED BY ','
     OPTIONALLY ENCLOSED BY '"'; 

-- "Give me the names of all the actors in the movie 'Die Another Day'.
SELECT Name FROM Actors WHERE Movie = 'Die Another Day';

-- DROP table
DROP TABLE Actors;