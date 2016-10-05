import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class sentimentAnalysisOnMonths {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//findTopPostersLat10Posts();
		//String[] conditions = {"alzheimers-disease-dementia"};
		//String[] conditions = {"depression"};
		String[] conditions = {"relationship-health"};
		
		String root = "/home/farigys/Health_Board/";
		
		String cacheDir = "/home/farigys/Health_Board/CacheFiles/";
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String rootDir = root + condition;
			
			File f = new File(rootDir+"/avgSentimentOfPosts.txt");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
	        HashMap<String, Double> sentmap = new HashMap<String, Double>();
	        
			String line = "";
			
			while((line = reader.readLine())!=null)
			{				
				String[] parts = line.split(":");
				
				sentmap.put(parts[0], Double.parseDouble(parts[1]));
			}
			
			double[][] posterSentArray = new double[100][12];
			int[] posterCount = new int[12];
			
			f = new File(root + "/CacheFiles/topPostersRH-NL.txt");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
			
	        line = "";
	        
	        int posterNo = 0;
	        
	        while((line=reader.readLine())!=null)
	        {
	        	String[] parts = line.split(";");
	        	//System.out.println(parts.length);
	        	
	        	//int[] arr = new int[12];
	        	
	        	for(int k=1; k<parts.length; k++)
	        	{
	        		String postList = parts[k];
	        		if(postList.equals("@@@@@"))continue;
	        		
	        		Double totalSentVal = 0.0;
	        		
	        		String[] postnames = postList.split(",");
	        		int z=0;
	        		for(; z<postnames.length; z++)
	        		{
	        			String postname = postnames[z];
	        			//System.out.println(postname);
	        			String[] nameparts = postname.split("/");
	        			String name = nameparts[1];
	        			name = name + ".json";
	        			postname = nameparts[0] + "/" + name + "/" + nameparts[2];
	        			//System.out.println(postname);
	        			Double sentVal = sentmap.get(postname);
	        			totalSentVal += sentVal;
	        		}
	        		
	        		posterCount[k-1]++;
	        		posterSentArray[posterNo][k-1]+=totalSentVal/z;	
	        	}
	        	posterNo++;
	        }
	        
	        for(int i=0; i<100; i++)
	        {
	        	for(int j=0; j<12; j++)
	        	{
	        		//System.out.println(posterSentArray[i][j]);
	        	}
	        }
	        
	        for(int i=0; i<12; i++)
	        {
	        	Double monthSentiment = 0.0;
	        	for(int j=0; j<100; j++)
	        	{
	        		monthSentiment+=posterSentArray[j][i];
	        	}
	        	System.out.print(monthSentiment/posterCount[i] + ",");
	        }
	        
		}
		
	}
	
}


