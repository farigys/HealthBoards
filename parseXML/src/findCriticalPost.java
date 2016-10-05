import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farig
 */


public class findCriticalPost {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        HashMap<String, TreeMap<String, String>> posterMap = new HashMap();
//        File writefile = new File("/home/farigys/Desktop/data4.txt");
//     	if (!writefile.exists()) {
//    		writefile.createNewFile();
//    	}
//    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
//    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	
//        for (int m = 0; m < listOfFolders.length; m++) 
//        {
//        	if (listOfFolders[m].isDirectory())
//        	{
//        		String foldername = listOfFolders[m].getName();
//	       		//String foldername = "Alcohalism";
//	       		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
//	       		//System.out.println("Now in: "+foldername);
//	       		String rootDir = root+foldername;
//	       		File  f = new File(rootDir+"/userPostList.txt");
//	            FileInputStream fis = new FileInputStream(f); 
//	            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//	            String line1 = "";
//	            while((line1=reader.readLine())!=null)
//	            {
//	            	StringTokenizer token = new StringTokenizer(line1, ",");
//	            	String posterId = token.nextToken();
//	            	//System.out.println("Now in: "+foldername);
//	            	String post = token.nextToken();
//	            	StringTokenizer token1 = new StringTokenizer(post, "-.");
//	            	post = token1.nextToken();
//	            	String date = token.nextToken();
//	            	StringTokenizer token2 = new StringTokenizer(date, "/ ");
//	            	String month = token2.nextToken();
//	            	String day = token2.nextToken();
//	            	String year = token2.nextToken();
//	            	String time = token2.nextToken();
//	            	String hour = Character.toString(time.charAt(0))+Character.toString(time.charAt(1));
//	            	String minute = Character.toString(time.charAt(3))+Character.toString(time.charAt(4));
//	            	if(time.contains("pm"))
//	            	{
//	            		Integer hr = Integer.parseInt(hour)+12;
//	            		hour = hr.toString();
//	            	}
//	            	time = hour+minute;
//	            	date = year+month+day+time;
//	            	
//	            	if(posterMap.containsKey(posterId))
//	            	{
//	            		TreeMap<String, String> tempMap = posterMap.get(posterId);
//	            		tempMap.put(date, post);
//	            		posterMap.put(posterId, tempMap);
//	            	}
//	            	else
//	            	{
//
//	            		TreeMap<String, String> tempMap = new TreeMap();
//	            		tempMap.put(date, post);
//	            		posterMap.put(posterId, tempMap);
//	            	}
//	            	
//	            }
//       		
//        	}
//        }
//        
//        
//        
//        //bw.close();
        
		 File  f = new File("/home/farigys/Desktop/data2.txt");
         FileInputStream fis = new FileInputStream(f); 
         BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
         
         File  f1 = new File(root+"userPostList.txt");
         FileInputStream fis1 = new FileInputStream(f1); 
         BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
         
         String line = "";
         while((line=reader1.readLine())!=null)
         {
        	 StringTokenizer token = new StringTokenizer(line, " ");
        	 String posterId = token.nextToken();
        	 //if(posterId.equals("2264988"))System.out.println(posterId);
        	 String postId = token.nextToken();
        	 String date = token.nextToken();
        	 
        	 if(posterMap.containsKey(posterId))
        	 {
        		 //if(posterId.equals("2264988"))System.out.println("dhuksi");
        		 TreeMap<String, String> tempMap = posterMap.get(posterId);
        		 tempMap.put(date, postId);
        		 //if(posterId.equals("2264988"))System.out.println(tempMap);
        		 posterMap.put(posterId, tempMap);
        		 //if(posterId.equals("2264988"))System.out.println(posterMap.get(posterId));
        	 }
        	 else
        	 {
        		 TreeMap<String, String> tempMap = new TreeMap<String, String>();
        		 tempMap.put(date, postId);
        		 posterMap.put(posterId, tempMap);
        	 }
         }
         //System.out.println(posterMap.get("147693"));
         while((line=reader.readLine())!=null)
         {
        	 String posterId = line;
        	 TreeMap<String, String> tempMap = posterMap.get(posterId);
        	
        	 
        	 Iterator iter = tempMap.entrySet().iterator();
             	Map.Entry pairs = (Map.Entry)iter.next();
             	System.out.println(pairs.getValue());
             
        	 
        	 
        	 
        	 
         }
		
		
		
	
	}

}
