import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;


public class createLastPostBigramDataset {
public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		
		//String[] conditions = {"depression"};
		//String[] conditions = {"relationship-health"};
		
	String[] conditions = {"alzheimers-disease-dementia"};
	
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir1 = root+"/Caches/";
			
			int obsPeriod = 12;
			
			HashSet<String> trainSet = new HashSet<String>();
			HashSet<String> testSet = new HashSet<String>();
			
			HashMap<String, String> resultMap = new HashMap<String, String>();
			
			HashMap<String, String> basicMap = new HashMap<String, String>();
			
			File  f1 = new File(rootDir1 + "trainUserList.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    String line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	trainSet.add(line);
		    }
		    
		    f1 = new File(rootDir1 + "testUserList.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	testSet.add(line);
		    }
		    
		    HashMap<String, String> unigramMap = new HashMap<String, String>();
		    
		    f1 = new File(root + "/Caches/BagOfBigrams.filtered.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    //int lineCount = 0;
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	//lineCount++;
		    	String[] parts = line.split(",");
		    	//System.out.println(lineCount);
		    	String filename = parts[0];
		    	String temp = "";
		    	try
		    	{
		    		temp = parts[1];
		    	}catch(Exception e)
		    	{
		    		e.printStackTrace();
		    		unigramMap.put(filename, temp);
		    		continue;
		    	}
		    	if(parts.length>2)
		    	{
		    		for(int i=2; i<parts.length; i++)
			    	{
			    		temp = temp + "," + parts[i];
			    	}
		    	}
		    	
		    	unigramMap.put(filename, temp);
		    	
		    }
		    
		    File file0 = new File(rootDir1 + "/bigramlastpost" + obsPeriod + "m");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file0.exists()) {
				file0.createNewFile();
			}

			FileWriter fw0 = new FileWriter(file0.getAbsoluteFile());
			BufferedWriter bw0 = new BufferedWriter(fw0);
			
			File file1 = new File(rootDir1 + "/bigramlastpost" + obsPeriod + "m.t");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			f1 = new File(rootDir1 + "basicFeaturesWithClass.txt");
			 fis1 = new FileInputStream(f1); 
			 reader1 = new BufferedReader(new InputStreamReader(fis1));
			    
			 line = "";
			    
			 while((line=reader1.readLine())!=null)
			 {
			  	String[] parts = line.split(";");
			   	String userId = parts[0];
			   	//System.out.println(userId);
			   	String temp = parts[1];
			   	for(int i=2; i<11; i++)
			   	{
			   		temp = temp + ";" + parts[i]; 
			   	}
			   	String result = parts[11];
			   	resultMap.put(userId, result);
			   	basicMap.put(userId, temp);
			 }
			 
			 f1 = new File(rootDir1 + "BigramDictionary.filtered.txt");
			 fis1 = new FileInputStream(f1); 
			 reader1 = new BufferedReader(new InputStreamReader(fis1));
			    
			 line = "";
			 
			 HashMap<String, Integer> idfMap = new HashMap<String, Integer>();
			 
			 while((line=reader1.readLine())!=null)
			 {
				 String[] parts = line.split("%@%");
				 idfMap.put(parts[1], Integer.parseInt(parts[3]));
			 }
			 
			 int dictSize = idfMap.size();
			
			f1 = new File(rootDir1 + "releventPosts.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	String userId = parts[0];
		    	String result = resultMap.get(userId);
		    	
		    	if(trainSet.contains(userId))
		    	{
		    		bw0.write(result);
		    		int[] arr = new int[dictSize];
		    		for(int i=0; i<dictSize; i++)
		    		{
		    			arr[i] = 0;
		    		}
		    		
		    		String lastPostName = "";
		    		
		    		for(int i=1; i<parts.length; i++)
		    		{
		    			String currPostName = parts[i];
		    			String[] nameParts = currPostName.split("/");
		    			String name = nameParts[1] + ".json";
		    			currPostName = nameParts[0] + "/" + name + "/" + nameParts[2];
		    			System.out.println(currPostName);
		    			lastPostName = currPostName;
		    			
		    			
		    		}
		    		
		    		String temp = unigramMap.get(lastPostName);
	    			//System.out.println(temp.size());
	    			
	    			String[] termParts = temp.split(",");
	    			
	    			for(int j=0; j<termParts.length; j++)
	    			{
	    				String[] dictParts = termParts[j].split(" ");
	    				try
	    				{
	    					int unigramId = Integer.parseInt(dictParts[0]);
	    					int unigramCount = Integer.parseInt(dictParts[1]);
		    				
		    				arr[unigramId]+= unigramCount;
	    				}catch(Exception e)
	    				{
	    					continue;
	    				}
	    				
	    			}
		    		
		    		for(int i=0; i<arr.length; i++)
		    		{
		    			if(arr[i]>0)bw0.write(" "+ (i+1) + ":" + arr[i]);
		    			
		    		}
		    		
		    		String basicInfo = basicMap.get(userId);
		    		String infoParts[] = basicInfo.split(";");
		    		
		    		System.out.println(arr.length);
		    		
		    		
		    		for(int i=arr.length, j=0; j<10; i++, j++)
		    		{
		    			bw0.write(" "+ (i+1) + ":" + infoParts[j]);
		    		}
		    		
		    		bw0.write("\n");
		    	}
		    	else
		    	{
		    		bw1.write(result);
		    		int[] arr = new int[dictSize];
		    		for(int i=0; i<dictSize; i++)
		    		{
		    			arr[i] = 0;
		    		}
		    		
		    		String lastPostName = "";
		    		
		    		for(int i=1; i<parts.length; i++)
		    		{
		    			String currPostName = parts[i];
		    			String[] nameParts = currPostName.split("/");
		    			String name = nameParts[1] + ".json";
		    			currPostName = nameParts[0] + "/" + name + "/" + nameParts[2];
		    			System.out.println(currPostName);
		    			lastPostName = currPostName;
		    			
		    			
		    		}
		    		
		    		String temp = unigramMap.get(lastPostName);
	    			//System.out.println(temp.size());
	    			
	    			String[] termParts = temp.split(",");
	    			
	    			for(int j=0; j<termParts.length; j++)
	    			{
	    				String[] dictParts = termParts[j].split(" ");
	    				try
	    				{
	    					int unigramId = Integer.parseInt(dictParts[0]);
	    					int unigramCount = Integer.parseInt(dictParts[1]);
		    				
		    				arr[unigramId]+= unigramCount;
	    				}catch(Exception e)
	    				{
	    					continue;
	    				}
	    			}
		    		
		    		for(int i=0; i<arr.length; i++)
		    		{
		    			if(arr[i]>0)bw1.write(" "+ (i+1) + ":" + arr[i]);
		    		}
		    		
		    		String basicInfo = basicMap.get(userId);
		    		String infoParts[] = basicInfo.split(";");
		    		
		    		System.out.println(arr.length);
		    		
		    		
		    		for(int i=arr.length, j=0; j<10; i++, j++)
		    		{
		    			bw1.write(" "+ (i+1) + ":" + infoParts[j]);
		    		}
		    		
		    		bw1.write("\n");
		    	}	
		    	
		    }
		    bw0.close();
			bw1.close();
		}
		
	}	
}
