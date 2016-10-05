import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;

import org.json.simple.parser.JSONParser;


public class insomniacIndex {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
			
		String[] conditions = {"depression"};
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/";
			
			HashMap<String, String> timeMap = new HashMap<String, String>(); 
			
			File  f1 = new File(root+"depression/userPostInfo.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    String line = "";
		    
		    while((line = reader1.readLine())!=null)
			{
				String[] parts = line.split(";");
				String fileId = parts[1].trim() + "/" + parts[2].trim();
				timeMap.put(fileId, parts[3].trim());
			}
			
			File f = new File(root + "/CacheFiles/topPostersDEP.txt");
			FileInputStream fis = new FileInputStream(f); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			int[] totalPost = new int[12];
			int[] nightPost = new int[12];
			
			line = "";
			
			while((line = reader.readLine())!=null)
			{
				String[] parts = line.split(";");
	        	//System.out.println(parts.length);
	        	
	        	//int[] arr = new int[12];
	        	
	        	for(int k=1; k<parts.length; k++)
	        	{
	        		String postList = parts[k];
	        		if(postList.equals("@@@@@"))
	        		{
	        			//nightPost[k-1]
	        			continue;
	        		}
	        		String[] postnames = postList.split(",");
	        		//int[] arr = new int[69];
	        		for(int z=0; z<postnames.length; z++)
	        		{
	        			String postname = postnames[z];
	        			String time = timeMap.get(postname);
	        			//System.out.println(time);
	        			int hour = Integer.parseInt(time.substring(8, 10));
	        			//System.out.println(hour);
	        			if(hour>=21 || hour<6)
	        			{
	        				nightPost[k-1]++;
	        			}
	        			totalPost[k-1]++;
	        		}
	        		
	        		
	        	}
			}
			
			for(int k=0; k<12; k++)
			{
				System.out.println((double)nightPost[k]/totalPost[k]);
			}
		}
	}
}
