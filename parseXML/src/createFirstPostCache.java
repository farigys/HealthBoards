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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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


public class createFirstPostCache {

    public static void main(String[] args) throws FileNotFoundException, IOException {
    	HashSet<String> posUnigrams = new HashSet<String>();
    	HashSet<String> negUnigrams = new HashSet<String>();
    	HashSet<String> posbigrams = new HashSet<String>();
    	HashSet<String> negbigrams = new HashSet<String>();
    	int count = 0;
    	String formattedDate = new String();
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bipolar-Disorder";
        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.equals("userPostIndex")||foldername.contains("."))continue;
        		//System.out.println("Now in: "+foldername);
        		String rootDir = root+foldername;
        		
        		
                File  funigram = new File(rootDir+"/FilteredPosEmotionUnigramsFrequency.txt");
                FileInputStream fisunigram = new FileInputStream(funigram); 
                BufferedReader readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
                String line1 = "";
                
                posUnigrams = new HashSet<String>();
                
                while((line1=readerunigram.readLine())!=null)
                {
                	StringTokenizer token1 = new StringTokenizer(line1," ");
                	String temp = token1.nextToken();
                	posUnigrams.add(temp);
                }
                
                funigram = new File(rootDir+"/FilteredNegEmotionUnigramsFrequency.txt");
                fisunigram = new FileInputStream(funigram); 
                readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
                line1 = "";
                
                negUnigrams = new HashSet<String>();
                
                while((line1=readerunigram.readLine())!=null)
                {
                	StringTokenizer token1 = new StringTokenizer(line1," ");
                	String temp = token1.nextToken();
                	negUnigrams.add(temp);
                }
                
                readerunigram.close();
                
                File  fbigram = new File(rootDir+"/FilteredPosEmotionBigramsFrequency.txt");
                FileInputStream fisbigram = new FileInputStream(fbigram); 
                BufferedReader readerbigram = new BufferedReader(new InputStreamReader(fisbigram));
                String line2 = "";
                
                posbigrams = new HashSet<String>();
                
                while((line2=readerbigram.readLine())!=null)
                {
                	StringTokenizer token1 = new StringTokenizer(line2,"\t");
                	String temp = token1.nextToken();
                	posbigrams.add(temp);
                }
                
                fbigram = new File(rootDir+"/FilteredNegEmotionBigramsFrequency.txt");
                fisbigram = new FileInputStream(fbigram); 
                readerbigram = new BufferedReader(new InputStreamReader(fisbigram));
                line2 = "";
                
                negbigrams = new HashSet<String>();
                
                while((line2=readerbigram.readLine())!=null)
                {
                	StringTokenizer token1 = new StringTokenizer(line2,"\t");
                	String temp = token1.nextToken();
                	negbigrams.add(temp);
                }
                
                readerbigram.close();
        	}
        }
        
        System.out.println("pos-neg Done");
        
        File writefile = new File("/home/farigys/Documents/cacheFileFirstPost.csv");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	String root1 = "/home/farigys/Documents/userPostIndex/";
    	
    	File  fc = new File(root+"/cacheFile.csv");
        FileInputStream fisc = new FileInputStream(fc); 
        BufferedReader readerc = new BufferedReader(new InputStreamReader(fisc));
        
        HashMap<String, cache> cacheMap = new HashMap<String, cache>();
        
        String linec = "";
        while((linec = readerc.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(linec, ",");
        	String postId = token1.nextToken();
        	//System.out.println(postId);
        	cache tempcache = new cache();
        	tempcache.person = token1.nextToken();
        	token1.nextToken();
        	tempcache.replycount = Integer.parseInt(token1.nextToken());
        	tempcache.uposcount = Double.parseDouble(token1.nextToken());
        	tempcache.unegcount = Double.parseDouble(token1.nextToken());
        	tempcache.utotalcount = Double.parseDouble(token1.nextToken());
        	tempcache.bposcount = Double.parseDouble(token1.nextToken());
        	tempcache.bnegcount = Double.parseDouble(token1.nextToken());
        	tempcache.btotalcount = Double.parseDouble(token1.nextToken());
        	tempcache.qcount = Integer.parseInt(token1.nextToken());
        	tempcache.urlcount = Integer.parseInt(token1.nextToken());
        	if(cacheMap.containsKey(postId))System.out.println("ghapla for "+postId);
        	else cacheMap.put(postId, tempcache);
        }
        
        System.out.println("prevcache Done");
        
        readerc.close();
    	
    	File postIndex = new File(root1);
    	File[] listOfFiles = postIndex.listFiles();
    	
    	//System.out.println(listOfFiles.length);
    	
    	ArrayList<String> userList = new ArrayList<String>();
    	
    	int replyc = 0;
    	
    	for(int i=0; i<listOfFiles.length;i++)
    	{
    		String filename = listOfFiles[i].getName().toString();
    		StringTokenizer tokenizer1 = new StringTokenizer(filename, ".");
    		String userId = tokenizer1.nextToken();
    		
    		//System.out.println(userId);
    		
    		count++;
    		
    		if(count%100 == 0)
    		{
    			System.out.println("total: "+count);
    			System.out.println("having reply: "+replyc);
    			replyc = 0;
    		}
    			
    		
    		userList.add(userId);
    		
    		 File  ff = new File(root1+filename);
             FileInputStream fisf = new FileInputStream(ff); 
             BufferedReader readerf = new BufferedReader(new InputStreamReader(fisf));
             
             String firstPost = readerf.readLine();
             
             readerf.close();
             
             StringTokenizer tokenizer2 = new StringTokenizer(firstPost,":");
             
             String formatDate = tokenizer2.nextToken();
             //System.out.println(formatDate);
             String condition = tokenizer2.nextToken();
             //System.out.println(condition);
             String file2 = tokenizer2.nextToken();
             //System.out.println(file2);
             
             if (file2.contains("Problem"))
             {
            	 StringTokenizer tokenizer3 = new StringTokenizer(file2, "_");
            	 tokenizer3.nextToken();
            	 String postId = tokenizer3.nextToken();
            	 //System.out.println(postId);
            	 if(cacheMap.containsKey(postId))
            	 {
            		 //System.out.println("found");
            		 cache temp = cacheMap.get(postId);
            		 bw.write(userId+","+file2+","+formatDate+","+condition+","+temp.replycount+","+four.format(temp.uposcount)+","+four.format(temp.unegcount)+","+four.format(temp.utotalcount)+","+four.format(temp.bposcount)+","+four.format(temp.bnegcount)+","+four.format(temp.btotalcount)+","+temp.qcount+","+temp.urlcount+"\n");
            	 }
            	 else continue;
            	 
             }
             
             else if(file2.contains("Reply"))
             {
            	replyc++;
            	//System.out.println("Accessing "+file2);
     		    File  fp = new File("/home/farigys/Documents/daily-strength/"+condition+"/Texts.parsed/"+file2+".txt");
   	            FileInputStream fisp = new FileInputStream(fp); 
   	            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
   	            String currline = "";
   	            double uposcount =0, unegcount =0, utotalcount =1;
   	            int qcount=0, urlCount = 0;
   	            while((currline=readerp.readLine())!=null)
   	            {
   	            	StringTokenizer tokenize = new StringTokenizer(currline," ");
   	            	while(tokenize.hasMoreTokens())
   	            	{
   	            		utotalcount++;
   	            		String currUnigram = tokenize.nextToken();
   	            		currUnigram = currUnigram.replace("/", "_");
   	            		if(posUnigrams.contains(currUnigram.toLowerCase()))uposcount++;
   	            		if(negUnigrams.contains(currUnigram.toLowerCase()))unegcount++;
   	            		if(currUnigram.contains("?"))qcount++;
   	            		if(currUnigram.contains("www.")||currUnigram.contains("http://")||currUnigram.contains("https://"))urlCount++;
   	            	}
   	            }
   	            uposcount /= utotalcount;
   	            unegcount /= utotalcount;
                
   	            readerp.close();
   	            
	   	        fp = new File("/home/farigys/Documents/daily-strength/"+condition+"/Texts.parsed/"+file2+".txt");
	   	        fisp = new FileInputStream(fp); 
	   	        readerp = new BufferedReader(new InputStreamReader(fisp));
		            currline = "";
		            double bposcount =0, bnegcount =0, btotalcount =1;
		            while((currline=readerp.readLine())!=null)
		            {
		            	StringTokenizer tokenize = new StringTokenizer(currline," ");
		            	String currbigram = "";
		            	String tempCurrBigram = "";
		            	if(tokenize.hasMoreTokens()) tempCurrBigram = tokenize.nextToken();
		            	//System.out.println(tempCurrBigram);
		            	while(tokenize.hasMoreTokens())
		            	{
		            		btotalcount++;
		            		String currbigramtemp = tokenize.nextToken();
		            		currbigram = tempCurrBigram + " " + currbigramtemp;
		            		tempCurrBigram = currbigramtemp;
		            		//System.out.println(currbigram);
		            		currbigram = currbigram.replace("/", "_");
		            		if(posbigrams.contains(currbigram.toLowerCase()))bposcount++;
		            		if(negbigrams.contains(currbigram.toLowerCase()))bnegcount++;
		            	}
		            }
	   	            
		          bposcount /= btotalcount;
		          bnegcount /= btotalcount;
	       
		          readerp.close();
		          bw.write(userId+","+file2+","+formatDate+","+condition+","+"0"+","+four.format(uposcount)+","+four.format(unegcount)+","+four.format(utotalcount)+","+four.format(bposcount)+","+four.format(bnegcount)+","+four.format(btotalcount)+","+qcount+","+urlCount+"\n");
	     	 }
    		
    	}
    	
    	bw.close();
        
    	System.out.println("cache creation done");
    	
    	Collections.shuffle(userList);
    	
    	writefile = new File("/home/farigys/Documents/trainList.csv");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	fw = new FileWriter(writefile.getAbsoluteFile());
    	bw = new BufferedWriter(fw);
    	
    	for(int i=0;i<50000;i++)
    	{
    		bw.write(userList.get(i)+"\n");
    	}
    	 bw.close();
    	
    	writefile = new File("/home/farigys/Documents/testList.csv");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	fw = new FileWriter(writefile.getAbsoluteFile());
    	bw = new BufferedWriter(fw);
    	
    	for(int i=50000;i<userList.size();i++)
    	{
    		bw.write(userList.get(i)+"\n");
    	}
    	 bw.close();
	    
    }
}

class cache
{
	String person;
	int replycount;
	double uposcount;
	double unegcount;
	double utotalcount;
	double bposcount;
	double bnegcount;
	double btotalcount;
	int qcount;
	int urlcount;
}




