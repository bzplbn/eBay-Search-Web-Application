import java.security.*;
import java.io.*;
public class ComputeSHA {
  public static String getHash(String filename) throws Exception
  {   
	  //get data from file
	  InputStream is = new FileInputStream (filename);
	  byte[] buffer = new byte[1024];
	  
	  //Create a MessageDigest to do hashing
	  MessageDigest hasher = MessageDigest.getInstance("SHA-1");
	  
	  //read bytes from the file and pass them to the MessageDigest
	  int read;
	  while((read = is.read(buffer)) > 0)
	  {
		  hasher.update(buffer, 0, read);
	  }
	  
	  //Close the file and tell the hasher to compute
	  is.close();
	  byte[] b = hasher.digest();
	  
	  //Convert the hashed bytes to hex values
	  StringBuffer result = new StringBuffer();
	  for(int i = 0; i < b.length; i++)
	  {
		  result.append(Integer.toString(( b[i] & 0xff ) + 0x100, 16).substring( 1 ));
	  }
 	  
	  //return the final hex string
	  return result.toString();
	  
	 
	  
	  
  }
  public static void main(String[] args) throws Exception
  {
	  try
	  {
		  //Get the filename
		  String inFile = "";
		  if(args.length == 1)
		  {
			  inFile = args[0];
		  }
		  else
		  {
			  System.err.println("Invalid Argument Number!");
		  }
		  
		  //Get SHA-1 hash
		  System.out.println(getHash(inFile));
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }
  }
}
