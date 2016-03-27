/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import java.lang.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {
    
    static final String columnSeparator = "|**|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
    "none",
    "Element",
    "Attr",
    "Text",
    "CDATA",
    "EntityRef",
    "Entity",
    "ProcInstr",
    "Comment",
    "Document",
    "DocType",
    "DocFragment",
    "Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
         methods). */
        
        Element root = doc.getDocumentElement();
        Element item[] = getElementsByTagNameNR(root, "Item");
        
        try
        {
            OutputStream os_Seller = new FileOutputStream("Seller.del", true);
            OutputStream os_Bidder = new FileOutputStream("Bidder.del", true);
            OutputStream os_Item = new FileOutputStream("Item.del", true);
            OutputStream os_ItemCategory = new FileOutputStream("ItemCategory.del", true);
            OutputStream os_Bid = new FileOutputStream("Bid.del", true);
            String s = new String();
            
            for(int i = 0; i < item.length; i++)
            {
                //*****************************************
                // Extract date for Table Seller
                Element seller = getElementByTagNameNR(item[i], "Seller");
                s = (seller.getAttributeNode("UserID")).getNodeValue() + columnSeparator + (seller.getAttributeNode("Rating")).getNodeValue() +"\n";
                byte bWrite [] = s.getBytes();
                os_Seller.write( bWrite );
                
                //*****************************************
                // Extract date for Table Item
                // Reformat date/time
                String started = getElementTextByTagNameNR(item[i], "Started");
                String ends = getElementTextByTagNameNR(item[i], "Ends");
                SimpleDateFormat myFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
                try {
                    Date parsedStarted = myFormat.parse(started);
                    Date parsedEnds = myFormat.parse(ends);
                    SimpleDateFormat aimFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    started = (aimFormat.format(parsedStarted)).toString();
                    ends = (aimFormat.format(parsedEnds)).toString();
                    // System.out.println(i + ".started: " + started);
                    // System.out.println(i + ".ends: " + ends);
                }catch(ParseException pe) {
                    System.out.println("ERROR: Cannot parse the data!");
                }
                // Reformat money
                String currently = getElementTextByTagNameNR(item[i], "Currently");
                currently = currently.replace("$","");
                currently = currently.replace(",","");
                String first_Bid = getElementTextByTagNameNR(item[i], "First_Bid");
                first_Bid = first_Bid.replace("$","");
                first_Bid = first_Bid.replace(",","");
                String buy_Price = getElementTextByTagNameNR(item[i], "Buy_Price");
                if (buy_Price != "")
                {
                    buy_Price = buy_Price.replace("$","");
                    buy_Price = buy_Price.replace(",","");
                }
                
                // Get locaion's attributes -- latitude & longitude
                Element location = getElementByTagNameNR(item[i], "Location");
                Node nLatitude = location.getAttributeNode("Latitude");
                String latitude;
                if (nLatitude != null)
                {
                    latitude = nLatitude.getNodeValue();
                }
                else
                    latitude = "";
                Node nLongitude = location.getAttributeNode("Longitude");
                String longitude;
                if (nLongitude != null)
                {
                    longitude = nLongitude.getNodeValue();
                }
                else
                    longitude = "";
                String description = getElementTextByTagNameNR(item[i], "Description");
                if (description.length() > 4000) {
                    description = description.substring(0,4000);
                }
                //System.out.println(i + ": " + latitude + ", " + longitude);
                s = (item[i].getAttributeNode("ItemID")).getNodeValue() + columnSeparator + getElementTextByTagNameNR(item[i], "Name") + columnSeparator +
                description + columnSeparator + started + columnSeparator + ends + columnSeparator + currently +
                columnSeparator + first_Bid + columnSeparator + buy_Price + columnSeparator + getElementTextByTagNameNR(item[i], "Country") + columnSeparator +
                getElementTextByTagNameNR(item[i], "Location") + columnSeparator + latitude + columnSeparator + longitude + columnSeparator +
                getElementTextByTagNameNR(item[i], "Number_of_Bids") + columnSeparator + (seller.getAttributeNode("UserID")).getNodeValue() + "\n";
                bWrite = s.getBytes();
                os_Item.write( bWrite );
                
                
                //*****************************************
                // Extract date for Table ItemGategory
                Element itemCategory[] = getElementsByTagNameNR(item[i], "Category");
                for (int j = 0; j < itemCategory.length; j++)
                {
                    s = (item[i].getAttributeNode("ItemID")).getNodeValue() + columnSeparator + getElementText(itemCategory[j]) + "\n";
                    bWrite = s.getBytes();
                    os_ItemCategory.write( bWrite );
                }
                
                //*****************************************
                // Extract date for Table Bid and Bidder
                Element bids = getElementByTagNameNR(item[i], "Bids");
                Element bid[] = getElementsByTagNameNR(bids, "Bid");
                if (bid.length != 0)
                {
                    for (int j = 0; j < bid.length; j++)
                    {
                        Element bidder = getElementByTagNameNR(bid[j], "Bidder");
                        String userid = (bidder.getAttributeNode("UserID")).getNodeValue();
                        String rating = (bidder.getAttributeNode("Rating")).getNodeValue();
                        
                        //Extract date for Table Bidder
                        s = userid + columnSeparator + rating + columnSeparator + getElementTextByTagNameNR(bidder, "Country") + columnSeparator +
                        getElementTextByTagNameNR(bidder, "Location") + "\n";
                        bWrite = s.getBytes();
                        os_Bidder.write( bWrite );
                        
                        //Extract date for Table Bid
                        String time = getElementTextByTagNameNR(bid[j], "Time");
                        try {
                            Date parsedStarted = myFormat.parse(time);
                            SimpleDateFormat aimFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            time = (aimFormat.format(parsedStarted)).toString();
                        }catch(ParseException pe) {
                            System.out.println("ERROR: Cannot parse the data!");
                        }
                        String amount = getElementTextByTagNameNR(bid[j], "Amount");
                        amount = amount.replace("$","");
                        amount = amount.replace(",","");
                        s = userid + columnSeparator + (item[i].getAttributeNode("ItemID")).getNodeValue() + columnSeparator + time + columnSeparator +
                        amount + "\n";
                        bWrite = s.getBytes();
                        os_Bid.write( bWrite );
                    }
                }
            }
            os_Seller.close();
            os_Bidder.close();
            os_Item.close();
            os_ItemCategory.close();
            os_Bid.close();
        }catch(IOException e)
        {
            e.printStackTrace();
            System.exit(5);
        }
        
        /**************************************************************/
        
    }
    
    
    /*
     Function for parse Element content(for jsp to display)
     Return parsed Item Attributes
     */
    ItemResult parseElement(Element root){
        
        String ItemID = root.getAttribute("ItemID");
        String Name = getElementTextByTagNameNR(root, "Name");
        
        String Description = getElementTextByTagNameNR(root, "Description");
        if (Description.length() > 4000) {
            Description = Description.substring(0,4000);
        }
        
        String Started = getElementTextByTagNameNR(root, "Started");
        String Ends = getElementTextByTagNameNR(root, "Ends");
        String Currently = getElementTextByTagNameNR(root, "Currently");
        String First_Bid = getElementTextByTagNameNR(root, "First_Bid");
        String Buy_Price = getElementTextByTagNameNR(root, "Buy_Price");
        String Country = getElementTextByTagNameNR(root, "Country");
        String Number_of_Bids = getElementTextByTagNameNR(root, "Number_of_Bids");
        
        Element locationElement = getElementByTagNameNR(root, "Location");
        String Location = getElementText(locationElement);
        String Longitude = null;
        String Latitude = null;
        if(!locationElement.getAttribute("Latitude").equals(null)){
            Longitude = locationElement.getAttribute("Longitude");
            Latitude = locationElement.getAttribute("Latitude");
        }
        
        Element seller = getElementByTagNameNR(root, "Seller");
        String SellerRaing = seller.getAttribute("Rating");
        String SellerID = seller.getAttribute("UserID");
        
        //Categories
        Element[] categories = getElementsByTagNameNR(root, "Category");
        ArrayList<String> categoriesList = new ArrayList<String>();
        for(Element category : categories){
            categoriesList.add(getElementText(category));
        }
        String[] categoriesArray = new String[categoriesList.size()];
        categoriesArray = categoriesList.toArray(categoriesArray);
        
        //Bids
        Element allBids = getElementByTagNameNR(root, "Bids");
        Element[] bids = getElementsByTagNameNR(allBids, "Bid");
        ArrayList<BidResult> bidsList = new ArrayList<BidResult>();
        for(Element bid : bids){
            //bidder info
            Element bidder = getElementByTagNameNR(bid, "Bidder");
            String Rating = bidder.getAttribute("Rating");
            String UserID = bidder.getAttribute("UserID");
            String bidderLocation = getElementTextByTagNameNR(bidder, "Location");
            String bidderCountry = getElementTextByTagNameNR(bidder, "Country");
            
            //bid info
            String Time = getElementTextByTagNameNR(bid, "Time");
            String Amount = getElementTextByTagNameNR(bid, "Amount");
            
            BidResult newBid = new BidResult(UserID, Rating, bidderLocation, bidderCountry, Time, Amount);
            //newBid.setLocation(Location, Country);
            bidsList.add(newBid);
        }
        
        //Sort: Latest Come first
        Collections.sort(bidsList, new Comparator<BidResult>(){
            public int compare(BidResult o1, BidResult o2){

                Date time1 = null;
                Date time2 = null;
                try{
                    SimpleDateFormat myFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
                    time1 = myFormat.parse(o1.getTime());
                    time2 = myFormat.parse(o2.getTime());

                } catch (ParseException e){
                    e.printStackTrace();
                    System.exit(6);
                }
                return time1.compareTo(time2)>0 ? -1 :time1.compareTo(time2)<0 ? 1 : 0;
                
            }
        });

        BidResult[] bidsArray = new BidResult[bidsList.size()];
        bidsArray = bidsList.toArray(bidsArray);
        
        ItemResult itemResult = new ItemResult(ItemID, Name, categoriesArray, Currently, First_Bid,Buy_Price, Number_of_Bids, bidsArray, Location, Country, Latitude, Longitude, Started, Ends, SellerID, SellerRaing, Description );
        
        return itemResult;      
    }
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
