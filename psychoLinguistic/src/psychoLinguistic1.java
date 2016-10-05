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


public class psychoLinguistic1 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength/";
    	
    	Trie t = new Trie();
    	
    	ArrayList<String> psywordlist = new ArrayList<String>();
    	
    	
    	
    	HashMap<String, ArrayList<Integer>> regCatMap = new HashMap<String, ArrayList<Integer>>();
    	
    	HashMap<String, ArrayList<String>> regexMap = new HashMap<String, ArrayList<String>>(); 
    	
    	HashSet<Pattern> regexset = new HashSet<Pattern>();
    	
    	HashSet<String> dict = new HashSet<String>();
    	
    	String rootDir = root+"Temporary Items/LIWC/";
    	
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
    	
    	
    	String[] folderList = {"Acne", "ADHD", "Alcoholism", "Asthma", "Back-Pain", "Bipolar-Disorder",
    			"Bone-Cancer", "COPD", "Diets-Weight-Maintenance", "Fibromyalgia", "Gastric-Bypass-Surgery", 
    			"Immigration-Law", "Infertility", "Loneliness", "Lung-Cancer", "Migraine", "Miscarriage",
    			"Pregnancy", "Rheumatoid-Arthritis", "War-In-Iraq"};
    	
    	for(int x = 0; x<folderList.length; x++)
    	{
    		//HashMap<String, int[]> psyWordMap = new HashMap<String, int[]>();
    		
    		File writefile = new File(root+folderList[x]+"/psylingcache1.txt");
         	if (!writefile.exists()) {
        		writefile.createNewFile();
        	}
         	
         	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
        	
        	
    		String rootD = root + folderList[x] + "/Texts.parsed/";
    		System.out.println(rootD);
        	File folder = new File(rootD);
        	File[] listOfFolders = folder.listFiles();
        	System.out.println(listOfFolders.length);
        	
        	int postcount = 0;
        	
        	for (int m = 0; m < listOfFolders.length; m++) 
            {
        		postcount++;
            	if(postcount%1000 == 0)System.out.println(postcount + " done");
        		if (listOfFolders[m].isFile())
        		{
        			String filename = listOfFolders[m].getName();
        			int[] arr = new int[69];
        	    	
        	    	//psyWordMap.put(filename, arr);
        	    	
        	    	File  f = new File(rootD+filename);
        		    FileInputStream fis = new FileInputStream(f); 
        		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        		    line = "";
        		    HashMap<String, Integer> wordmap = new HashMap<String, Integer>(); 
        		    
        		    while((line = reader.readLine())!=null)
        		    {
        	    		StringTokenizer tokenize = new StringTokenizer(line," ");
        	    		
        		    	while(tokenize.hasMoreTokens())
        	          	{
        		    		String currUnigram = tokenize.nextToken();
        	          		currUnigram = currUnigram.replace("/", "_");
        	          		StringTokenizer tokenize1 = new StringTokenizer(currUnigram, "_");
        	          		String match = tokenize1.nextToken();
        	          		//System.out.println(currUnigram);
        	          		String pclass = new String();
        	          		if(tokenize1.hasMoreTokens())pclass = tokenize1.nextToken();
        	          		if(!pclass.matches("\\w*"))continue;
        	          		if(wordmap.containsKey(currUnigram))
        	          		{
        	          			int count = wordmap.get(currUnigram);
        	          			count++;
        	          			wordmap.put(currUnigram, count);
        	          		}
        	          		else
        	          		{
        	          			int count = 0;
        	          			count++;
        	          			wordmap.put(currUnigram, count);
        	          		}
        	          	}
        	          		
        		    }
        		    
        		    Iterator it = wordmap.entrySet().iterator();
        		    while (it.hasNext()) {
        		        Map.Entry pair = (Map.Entry)it.next();
        		        String key = pair.getKey().toString();
        		        StringTokenizer tokenize2 = new StringTokenizer(key, "_");
        	      		String match = tokenize2.nextToken();
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
        	      				arr[cat]+=Integer.parseInt(pair.getValue().toString());
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
        	      		      				arr[cat]+=Integer.parseInt(pair.getValue().toString());
        	      		      			}
        	      						break;
        	      					}
        	      				}
        	      			}
        	      			
        	      		}
        	      		
        		    }
        		    
        		    //System.out.println(psyWordMap);
        		    //int[] temp = psyWordMap.get(filename);
        		    bw.write(filename);
        		    for(int i=1; i<arr.length; i++)
        		    {
        		    	bw.write(","+arr[i]);
        		    }
        		    
        		    bw.write("\n");
        		    
        		    reader.close();
        		}
            }
        	
        	System.out.println(folderList[x]+" done");
        	
        	bw.close();
    	}
    	
    	
    	
    	//String filename = "/test.txt";
    	
    	
	}
}

class wordCat
{
	String word;
	ArrayList<Integer> category;
	
	public wordCat(String word)
	{
		this.word = word;
		this.category = new ArrayList<Integer>();
	}
}
    

