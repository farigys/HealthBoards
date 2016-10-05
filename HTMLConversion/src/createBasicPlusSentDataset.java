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


public class createBasicPlusSentDataset {
public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		
		String[] conditions = {"depression"};
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir1 = root+"/Caches/";
			
			int obsPeriod = 6;
			
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
		    
		    HashMap<String, Double> sentMap = new HashMap<String, Double>();
		    
		    f1 = new File(root + "/avgSentimentOfPosts.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	String[] parts = line.split(":");
		    	String filename = parts[0];
		    	sentMap.put(filename, Double.parseDouble(parts[1]));
		    }
		    
		    File file0 = new File(rootDir1 + "/basic+sent" + obsPeriod + "m");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file0.exists()) {
				file0.createNewFile();
			}

			FileWriter fw0 = new FileWriter(file0.getAbsoluteFile());
			BufferedWriter bw0 = new BufferedWriter(fw0);
			
			File file1 = new File(rootDir1 + "/basic+sent" + obsPeriod + "m.t");//if not filtered by frequency, use the file without .filtered part
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
//		    		double[] arr = new double[68];
//		    		for(int i=0; i<68; i++)
//		    		{
//		    			arr[i] = 0;
//		    		}
		    		Double totalSent = 0.0;
		    		Double lastSent = 0.0;
		    		
		    		for(int i=1; i<parts.length; i++)
		    		{
		    			String currPostName = parts[i];
		    			String[] nameParts = currPostName.split("/");
		    			String name = nameParts[1] + ".json";
		    			currPostName = nameParts[0] + "/" + name + "/" + nameParts[2];
		    			//System.out.println(currPostName);
		    			Double temp = sentMap.get(currPostName);
		    			//System.out.println(temp.size());
//		    			for(int j=0; j<temp.size()-1; j++)
//		    			{
//		    				arr[j] += temp.get(j);
//		    			}
		    			//if(temp<0)System.out.println("Hay Hay");
		    			totalSent+=temp;
		    			lastSent = temp;
		    		}
//		    		for(int i=0; i<arr.length; i++)
//		    		{
//		    			bw0.write(" "+ (i+1) + ":" + arr[i]);
//		    		}
		    		
		    		bw0.write(" 1" + ":" + Double.toString(totalSent/(parts.length - 1)));
		    		bw0.write(" 2" + ":" + Double.toString(lastSent));
		    		bw0.write(" 3" + ":" + Double.toString(lastSent - (totalSent/(parts.length - 1))));
		    		
		    		String basicInfo = basicMap.get(userId);
		    		String infoParts[] = basicInfo.split(";");
		    		
		    		for(int i=3, j=0; j<10; i++, j++)
		    		{
		    			bw0.write(" "+ (i+1) + ":" + infoParts[j]);
		    		}
		    		
		    		bw0.write("\n");
		    	}
		    	else
		    	{
		    		bw1.write(result);
//		    		double[] arr = new double[68];
//		    		for(int i=0; i<68; i++)
//		    		{
//		    			arr[i] = 0;
//		    		}
		    		Double totalSent = 0.0;
		    		Double lastSent = 0.0;
		    		
		    		for(int i=1; i<parts.length; i++)
		    		{
		    			String currPostName = parts[i];
		    			String[] nameParts = currPostName.split("/");
		    			String name = nameParts[1] + ".json";
		    			currPostName = nameParts[0] + "/" + name + "/" + nameParts[2];
		    			//System.out.println(currPostName);
		    			Double temp = sentMap.get(currPostName);
		    			//if(temp<0)System.out.println("Hay Hay");
		    			//System.out.println(temp.size());
//		    			for(int j=0; j<temp.size()-1; j++)
//		    			{
//		    				arr[j] += temp.get(j);
//		    			}
		    			totalSent+=temp;
		    			lastSent = temp;
		    		}
//		    		for(int i=0; i<arr.length; i++)
//		    		{
//		    			bw0.write(" "+ (i+1) + ":" + arr[i]);
//		    		}
		    		
		    		bw1.write(" 1" + ":" + Double.toString(totalSent/(parts.length - 1)));
		    		bw1.write(" 2" + ":" + Double.toString(lastSent));
		    		bw1.write(" 3" + ":" + Double.toString(lastSent - (totalSent/(parts.length - 1))));
		    		
		    		String basicInfo = basicMap.get(userId);
		    		String infoParts[] = basicInfo.split(";");
		    		
		    		for(int i=3, j=0; j<10; i++, j++)
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
