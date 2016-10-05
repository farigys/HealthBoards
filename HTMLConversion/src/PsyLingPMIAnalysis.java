import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.*;


public class PsyLingPMIAnalysis {
	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Health_Board/";
    	
    	File fcat = new File(root+"LIWC/LIWC2001_Categories.txt");
        FileInputStream fiscat = new FileInputStream(fcat); 
        BufferedReader readercat = new BufferedReader(new InputStreamReader(fiscat));
        
        readercat.readLine();
        
        String linecat = "";
        
        ArrayList<String> catname = new ArrayList<String>();
        
        while((linecat=readercat.readLine())!=null)
        {
        	if(linecat.contains("%"))break;
        	StringTokenizer tokencat = new StringTokenizer(linecat, "\t");
        	tokencat.nextToken();
        	String cat = tokencat.nextToken();
        	if(cat.contains("@"))
        	{
        		StringTokenizer temptok = new StringTokenizer(cat, "@");
        		cat = temptok.nextToken();
        	}
        	catname.add(cat);
        }
        
        //String[] conditions = {"alzheimers-disease-dementia"};
        //String[] conditions = {"depression"};
        String[] conditions = {"relationship-health"};
        
        for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String rootDir = "/home/farigys/Health_Board/" + condition;
			
			HashMap<String, int[]> psyLingMap = new HashMap<String, int[]>();
			
			File f = new File(rootDir + "/PsyLingCount.csv");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
	        String line = "";
	        
	        while((line = reader.readLine())!=null)
	        {
	        	String[] parts = line.split(",");
	        	String filename = parts[0];
	        	//String
	        	//System.out.println(parts.length);
	        	int[] arr =new int[69];
	        	for(int k=1; k<parts.length; k++)
	        	{
	        		arr[k-1] = (int) Double.parseDouble(parts[k].trim());
	        	}
	        	psyLingMap.put(filename, arr);
	        }
	        
			f = new File(rootDir + "/PsyLingPMI.csv");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
	        
	        int linecount = 0;
	        
	        line = "";
	        
	        int[] releventCat = new int[5];
	        
	        while((line = reader.readLine())!=null)
	        {
	        	
	        	if(linecount==5)break;
	        	
	        	String[] parts = line.split(":");
	        	
	        	String currcatname = parts[0].trim();
	        	
	        	if(currcatname.equals("Present"))
	        	{
	        		
	        		continue;
	        	}
	        	
	        	int currindex = catname.indexOf(currcatname);
	        	
	        	releventCat[linecount] = currindex;
	        	linecount++;
	        }
			
	        f = new File(root + "/CacheFiles/topPostersRH-NL.txt");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
	        
	        line = "";
	        
	        while((line=reader.readLine())!=null)
	        {
	        	String[] parts = line.split(";");
	        	//System.out.println(parts.length);
	        	
	        	//int[] arr = new int[12];
	        	
	        	for(int k=1; k<parts.length; k++)
	        	{
	        		String postList = parts[k];
	        		if(postList.equals("@@@@@"))
	        		{
	        			System.out.println("0,0,0,0,0,0");
	        			continue;
	        		}
	        		String[] postnames = postList.split(",");
	        		int[] arr = new int[69];
	        		for(int z=0; z<postnames.length; z++)
	        		{
	        			String postname = postnames[z];
	        			//System.out.println(postname);
	        			String[] nameparts = postname.split("/");
	        			String name = nameparts[1];
	        			name = name + ".json";
	        			postname = nameparts[0] + "/" + name + "/" + nameparts[2];
	        			//System.out.println(postname);
	        			int[] tempArr = psyLingMap.get(postname);
	        			//System.out.println(tempArr);
	        			for(int x=0; x<69; x++)
	        			{
	        				arr[x]+=tempArr[x];
	        			}
	        		}
	        		
	        		for(int x=0; x<5; x++)
	        		{
	        			int relCat = releventCat[x];
	        			System.out.print(arr[relCat]+",");
	        		}
	        		System.out.print(arr[arr.length-1] + "\n");
	        	}
	        	//System.out.print("_________________________\n");
	        }
		}
	}
}
