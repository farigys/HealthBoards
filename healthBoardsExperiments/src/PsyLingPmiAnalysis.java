import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.*;


public class PsyLingPmiAnalysis {

	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Health_Board/";
    	
    	//Trie t = new Trie();
    	
    	ArrayList<String> psywordlist = new ArrayList<String>();
    	
    	
    	
    	HashMap<String, ArrayList<Integer>> regCatMap = new HashMap<String, ArrayList<Integer>>();
    	
    	HashMap<String, ArrayList<String>> regexMap = new HashMap<String, ArrayList<String>>(); 
    	
    	HashSet<Pattern> regexset = new HashSet<Pattern>();
    	
    	HashSet<String> dict = new HashSet<String>();
    	
    	String rootDir = root+"LIWC/";
    	
    	File  fregex = new File(rootDir+"/LIWC2001_English.dic");
	    FileInputStream fisregex = new FileInputStream(fregex); 
	    BufferedReader readerregex = new BufferedReader(new InputStreamReader(fisregex));
	    String line = "";
	    readerregex.readLine();
	    
	    while((line = readerregex.readLine())!=null)
	    {
	    	StringTokenizer token = new StringTokenizer(line, "\t");
	    	String regex1 = token.nextToken();
	    	//if(regex1.length()<=3 && regex1.contains("*"))System.out.println(regex1);
	    	if(!regex1.contains("*"))dict.add(regex1);
	    	else
	    	{
	    		String regex2 = regex1.substring(0, 2);
	    		//System.out.println(regex2);
	    		if(regexMap.containsKey(regex2))
	    		{
	    			ArrayList<String> temp = regexMap.get(regex2);
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		else
	    		{
	    			ArrayList<String> temp = new ArrayList<String>();
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		
	    	}
	    	
	    	//wordCat regCat = new wordCat(regex1);
	    	
	    	ArrayList<Integer> cat = new ArrayList<Integer>();
	    	
	    	while(token.hasMoreTokens())
	    	{
	    		//int catno = Integer.parseInt(token.nextToken().toString());
	    		cat.add(Integer.parseInt(token.nextToken().toString()));
	    	}
	    	regCatMap.put(regex1, cat);
	    	
	    }
    	//System.out.println(regCatMap);
    	
    	
    	
    	readerregex.close();
    	////////////////////////////////////////////////////////////////////////////
    	
    	double[] arr = new double[69];
    	
    	File  f = new File("/home/farigys/Health_Board/LastPostWordPMI1.csv");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    line = "";
    	
	    while((line = reader.readLine())!=null)
	    {
	    	StringTokenizer token = new StringTokenizer(line, ":");
	    	String match = token.nextToken();
	    	double tempPmi = Double.parseDouble(token.nextToken());
	    	double pmi = Math.pow(10, tempPmi);
	    	
	  		if(dict.contains(match.toLowerCase()))
	  		{
	  			//System.out.println(key+" "+pair.getValue()+" from dict");
	  			ArrayList<Integer> tempc = regCatMap.get(match.toLowerCase());
	  			for(int i=0; i<tempc.size(); i++)
	  			{
	  				int cat = tempc.get(i);
	  				//int[] tempx = psyWordMap.get(filename);
	  				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
	  				//psyWordMap.put(filename, tempx);
	  				arr[cat]+=pmi;
	  			}
	  		}
	  		else
	  		{
	  			if(match.length()<2)continue;
	  			String key1 = match.toLowerCase().substring(0, 2);
	  			if(!regexMap.containsKey(key1))continue;
	  			else
	  			{
	  				ArrayList<String> tempreg = regexMap.get(key1);
	  				for(int i=0; i<tempreg.size(); i++)
	  				{
	  					Pattern pattern = Pattern.compile(tempreg.get(i));
	  					//System.out.println(pattern);
	  					//System.out.println("Matching "+match+" with "+tempreg.get(i));
	  					Matcher matcher = pattern.matcher(match.toLowerCase());
	  					if(matcher.find())
	  					{
	  						//System.out.println("match found for "+match+" with "+tempreg.get(i)+", "+key+" "+pair.getValue());
	  						//System.out.println(match.toLowerCase());
	  						ArrayList<Integer> tempc = regCatMap.get(tempreg.get(i));
	  						for(int j=0; j<tempc.size(); j++)
	  		      			{
	  		      				int cat = tempc.get(j);
	  		      				//int[] tempx = psyWordMap.get(filename);
	  		      				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
	  		      				//psyWordMap.put(filename, tempx);
	  		      				arr[cat]+=pmi;
	  		      			}
	  						break;
	  					}
	  				}
	  			}
	  			
	  		}
	    }
	    
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
        
        
        File file = new File(root + "/PsyLingPMI.csv");//user info cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
        
    	for(int k=0; k<68; k++)
    	{
    		bw.write(catname.get(k)+": "+Math.log10(arr[k+1]) + "\n");
    	}
    	
    	bw.close();
    	//String filename = "/test.txt";
    	
    	
	}
}

//class wordCat
//{
//	String word;
//	ArrayList<Integer> category;
//	
//	public wordCat(String word)
//	{
//		this.word = word;
//		this.category = new ArrayList<Integer>();
//	}
//}
//    


