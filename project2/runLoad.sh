#!/bin/bash
TABLE="CS144";
#TABLE="TEST";

# Run the drop.sql batch file to drop existing tables
# Inside the drop.sql, you sould check whether the table exists. Drop them ONLY if they exists.
mysql $TABLE < drop.sql

# Run the create.sql batch file to create the database and tables
mysql $TABLE < create.sql

# Compile and run the parser to generate the appropriate load files
ant run-all

# If the Java code does not handle duplicate removal, do this now
sort  Item.del -u -o Item.del
sort  Bidder.del -u -o Bidder.del
sort  Seller.del -u -o Seller.del
sort  ItemCategory.del -u -o ItemCategory.del
sort  Bid.del -u -o Bid.del

# Run the load.sql batch file to load the data
mysql $TABLE < load.sql

# Remove all temporary files: files created by the parser
rm *.del
rm -r bin
#rm bin/edu/ucla/cs/cs144/*.class
