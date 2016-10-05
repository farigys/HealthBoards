import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import java.util.Iterator;

//creates a map of post-per-month for each poster for each condition, also the average duration of each poster in DailyStrength.org

public class avergaePostCalc {
	
	 public static void main(String[] args) throws FileNotFoundException, IOException {
		 
		 	String root = "/mnt/docsig/storage/daily-strength/";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	        File writefile2 = new File(root+"/overallAverageDurationOfPosters.csv");
         	if (!writefile2.exists()) {
        		writefile2.createNewFile();
        	}
        	FileWriter fw2 = new FileWriter(writefile2.getAbsoluteFile());
        	BufferedWriter bw2 = new BufferedWriter(fw2);
	        for (int m = 0; m < listOfFolders.length; m++) 
	        {
	        	if (listOfFolders[m].isDirectory())
	        	{
	        		String foldername = listOfFolders[m].getName();
	        		//String foldername = "Alcoholism";
	        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
	        		System.out.println("Now in: "+foldername);
	        		String rootDir = root+"/"+foldername;
			    	File  fp = new File(rootDir+"/userPostList.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fisp = new FileInputStream(fp); 
		            //System.out.println("Dhuksi");
		            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
		            File writefile = new File(rootDir+"/averagePostFrequency.csv");
		         	if (!writefile.exists()) {
		        		writefile.createNewFile();
		        	}
		         	
		         	File writefile1 = new File(rootDir+"/averageDurationOfPosters.csv");
		         	if (!writefile1.exists()) {
		        		writefile1.createNewFile();
		        	}
		         	
		         	
		         	
		         	//HashMap<String, ArrayList<monthwithPostCount>> monthwisefrequencyMap = new HashMap<String, ArrayList<monthwithPostCount>>();
		         	
		         	HashMap<String, TreeMap<String, Integer>> monthwiseFrequencyMap = new HashMap<String, TreeMap<String, Integer>>();
		         	
		         	String line1 = "";
	                while((line1 = readerp.readLine())!=null)
	                {
	                	StringTokenizer token = new StringTokenizer(line1,",");
	                	String posterID = token.nextToken();
	                	String postName = token.nextToken();
	                	String date = token.nextToken();
	                	StringTokenizer tokenDate = new StringTokenizer(date, "/ ");
                		String month = tokenDate.nextToken();
                		String day = tokenDate.nextToken();
                		String year = tokenDate.nextToken();
                		String formatdate = year+month;
	                	if(monthwiseFrequencyMap.containsKey(posterID))
	                	{
	                		TreeMap<String, Integer> tempTree = monthwiseFrequencyMap.get(posterID);
	                		if(tempTree.containsKey(formatdate))
	                		{
	                			int temp = tempTree.get(formatdate);
	                			temp++;
	                			tempTree.put(formatdate, temp);
	                		}
	                		else
	                		{
	                			tempTree.put(formatdate, 1);
	                		}
	                		monthwiseFrequencyMap.put(posterID, tempTree);
	                	}
	                	else
	                	{
	                		TreeMap<String, Integer> tempTree = new TreeMap<String, Integer>();
	                		tempTree.put(formatdate, 1);
	                		monthwiseFrequencyMap.put(posterID, tempTree);
	                	}
	                }
	                
		         	//System.out.println(monthwiseFrequencyMap);
//		        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
//		        	BufferedWriter bw = new BufferedWriter(fw);
//		            //System.out.println("Dhuksi");
//		            String line;
//		            HashMap<String, Integer> posterList = new HashMap<String, Integer>();
//		            HashMap<String, String> posterPostList = new HashMap<String, String>();
//		            while((line=readerp.readLine())!=null)
//		            {
//		            	//System.out.println("Dhuksi");
//		            	StringTokenizer st = new StringTokenizer(line, ",");
//		                String poster = st.nextToken();
//		                String post = st.nextToken();
//		                if(posterList.containsKey(poster))
//		                {int
//		                	int count = posterList.get(poster);
//		                	count++;
//		                	posterList.put(poster, count);
//		                }
//		                else posterList.put(poster, 1);
//		                if(posterPostList.containsKey(poster))
//		                {
//		                	String temp = posterPostList.get(poster);
//		                	temp = temp +","+ post;
//		                	posterPostList.put(poster, temp);
//		                }
//		                else
//		                {
//		                	posterPostList.put(poster, post);
//		                }
//		            }
//		            
//		            ValueComparator bvc =  new ValueComparator(posterList);
//		            TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
//		            sorted_map.putAll(posterList);
//		            //System.out.println(sorted_map);
//		            Iterator iter = sorted_map.entrySet().iterator();
//		            for(int i=0;i<20;i++)
//		            {
//		            	Map.Entry pairs = (Map.Entry)iter.next();
//		            	String currPoster = pairs.getKey().toString();
//		            	String temp = "";
//		            	Iterator iter1 = posterPostList.entrySet().iterator();
//		            	while(iter1.hasNext())
//		            	{
//		            		Map.Entry pairs1 = (Map.Entry)iter1.next();
//		            		String currKey = pairs1.getKey().toString();
//		            		if(currKey.equals(currPoster))temp = temp + "," + posterPostList.get(currKey);
//		            	}
//		            	bw.write(pairs.getKey()+","+pairs.getValue()+temp+"\n");
//		            }
//		            bw.close();
		         	
		         	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
		        	BufferedWriter bw = new BufferedWriter(fw);
		        	FileWriter fw1 = new FileWriter(writefile1.getAbsoluteFile());
		        	BufferedWriter bw1 = new BufferedWriter(fw1);
		        	String  line = "";
		         	Iterator iter = monthwiseFrequencyMap.entrySet().iterator();
		         	double avg = 0;
		            while(iter.hasNext())
		            {
		            	Map.Entry pairs = (Map.Entry)iter.next();
		            	String currPoster = pairs.getKey().toString();
		            	line = currPoster;
		            	TreeMap<String, Integer> tempTree = (TreeMap<String, Integer>) pairs.getValue();
		            	Iterator iter1 = tempTree.entrySet().iterator();
		            	int sum = 0;
		            	while(iter1.hasNext())
		            	{
		            		Map.Entry pairs1 = (Map.Entry)iter1.next();
		            		Integer currVal = Integer.parseInt(pairs1.getValue().toString());
		            		sum++;
		            		line = line + "," + currVal.toString();
		            	}
		            	//line = line + "," + sum.toString();
		            	bw.write(line+"\n");
		            	bw1.write(currPoster+","+sum+"\n");
		            	avg+=sum;
		            }
		            avg = avg/monthwiseFrequencyMap.size()*1.0;
		            bw1.write("Average:,"+avg+"\n");
		            bw2.write(foldername.toString()+","+avg+"\n");
		         	bw.close();
		         	bw1.close();
		         	
		            readerp.close();
		            
		            
	        	}
	        	
	     }
	     bw2.close();
	 }

}


