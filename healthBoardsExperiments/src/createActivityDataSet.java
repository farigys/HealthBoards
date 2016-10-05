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



public class createActivityDataSet {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		
		String[] conditions = {"depression"};
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir1 = root+"/Caches/";
			
			int obsPeriod = 12;
			
			HashSet<String> trainSet = new HashSet<String>();
			HashSet<String> testSet = new HashSet<String>();
			
			File  f1 = new File(rootDir1 + "trainUserList.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    String line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	trainSet.add(line);
		    }
		    
		    System.out.println(trainSet.size());
		    
		    f1 = new File(rootDir1 + "testUserList.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	testSet.add(line);
		    }
		    
		    System.out.println(testSet.size());
		    
		    //File file0 = new File(rootDir1 + "/mostbasic" + obsPeriod + "m");//if not filtered by frequency, use the file without .filtered part
		    File file0 = new File(rootDir1 + "/activity" + obsPeriod + "m");
		    // if file doesnt exists, then create it
			if (!file0.exists()) {
				file0.createNewFile();
			}

			FileWriter fw0 = new FileWriter(file0.getAbsoluteFile());
			BufferedWriter bw0 = new BufferedWriter(fw0);
			
			//File file1 = new File(rootDir1 + "/mostbasic" + obsPeriod + "m.t");//if not filtered by frequency, use the file without .filtered part
			File file1 = new File(rootDir1 + "/activity" + obsPeriod + "m.t");
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
		    	String result = parts[11];
		    	if(trainSet.contains(userId))
		    	{
		    		bw0.write(result);
		    		for(int i=1; i<11; i++)
		    		{
		    			if(i>7)bw0.write(" "+ i + ":" + parts[i]);
		    		}
		    		bw0.write("\n");
		    	}
		    	else
		    	{
		    		bw1.write(result);
		    		for(int i=1; i<11; i++)
		    		{
		    			if(i>7)bw1.write(" "+ i + ":" + parts[i]);
		    		}
		    		bw1.write("\n");
		    	}
		    	
		    }
		    
		    bw0.close();
		    bw1.close();
		}
	}
}
