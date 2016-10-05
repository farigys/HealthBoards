/*Whoever reading this code, I feel sorry for you. I sincerely apologize for writing code this way.*/

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


public class createCacheFile {

    public static void main(String[] args) throws FileNotFoundException, IOException {
    	String formattedDate = new String();
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        File writefile = new File(root+"/cacheFile.csv");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	
    	
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bipolar-Disorder";
        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+foldername;
        		
        		
                File  funigram = new File(rootDir+"/FilteredPosEmotionUnigramsFrequency.txt");
                FileInputStream fisunigram = new FileInputStream(funigram); 
                BufferedReader readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
                String line1 = "";
                
                HashSet<String> posUnigrams = new HashSet<String>();
                
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
                
                HashSet<String> negUnigrams = new HashSet<String>();
                
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
                
                HashSet<String> posbigrams = new HashSet<String>();
                
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
                
                HashSet<String> negbigrams = new HashSet<String>();
                
                while((line2=readerbigram.readLine())!=null)
                {
                	StringTokenizer token1 = new StringTokenizer(line2,"\t");
                	String temp = token1.nextToken();
                	negbigrams.add(temp);
                }
                
                readerbigram.close();
	        	
                /////////////////////////////////////////////////////////////////////////////////////////////////////
                
	                File file = new File(rootDir+"/XML");
	                File[] listOfFiles = file.listFiles();
	                String line = new String();
	                String postID = new String();
	                
	                ////////////////////////////////////////////////////////////////////////////////////////////////
	                //TreeMap<String, ArrayList<String>> posterMap = new TreeMap<String, ArrayList<String>>();
	                for (int i = 0; i < listOfFiles.length; i++) {
	                    if (listOfFiles[i].isFile()) {
	                      //System.out.println("File " + listOfFiles[i].getName());
	                      String person = new String();
	                      String date = new String();
	                      String replyCount = new String();
	                      String filename = listOfFiles[i].getName();
                          StringTokenizer token = new StringTokenizer(filename, "-.");
                          postID = token.nextToken();
	                      File f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
	                      
	                      FileInputStream fis = new FileInputStream(f); 
	                      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	                      
	                      while((person = reader.readLine())!=null)
	                      {
	                          //linecount++;
	                          //person = reader.readLine();
	                          if(person.contains("<person id="))break;
	                      }
	                      
	                      //System.out.println("post:" + postID);
	                      //System.out.println("poster:"+person);
	                      //System.out.println(linecount);
	                      //System.out.println(person+f.getName());
	                      //if(person==null)continue;
	                      //System.out.println(person+" "+personToFind);
	                      if(person!=null)
	                      {	                    	  
	                    	  StringTokenizer tok = new StringTokenizer(person, "/\"><");
		                      person = tok.nextToken();
		                      person = tok.nextToken();
		                      person = tok.nextToken();
		                      person = tok.nextToken();
	                      }
	                      else continue;
	                      reader.close();
	                    	  //System.out.println("Dhuksi");
	                      f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
	                      fis = new FileInputStream(f); 
	                      reader = new BufferedReader(new InputStreamReader(fis));
	                      
	                      while((date = reader.readLine())!=null)
	                      {
	                          //linecount++;
	                          //date = reader.readLine();
	                          //System.out.println(date);
	                          if(date.contains("<date>"))break;
	                      }
	                      
	                          
	                      if(date!=null)
	                      {
	                          token = new StringTokenizer(date, "<>");
	                          token.nextToken();
	                          token.nextToken();
	                          date = token.nextToken();
	                      
		                      StringTokenizer tok1 = new StringTokenizer(date, " ");
		                      String subformattedDate = tok1.nextToken();
		                      StringTokenizer tok2 = new StringTokenizer(subformattedDate, "/");
		                      String month = tok2.nextToken();
		                      String day = tok2.nextToken();
		                      String year = tok2.nextToken();
		                      formattedDate = year+month+day; 
		                      //bw.write(people+","+postname+","+date+"\n");
		                      //System.out.println(people+","+postname+","+date);
	                      } 
	                      
	                      else continue;
	                      
	                      reader.close();
                    	  //System.out.println("Dhuksi");
	                      f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
	                      fis = new FileInputStream(f); 
	                      reader = new BufferedReader(new InputStreamReader(fis));
		                  
	                      while((replyCount = reader.readLine())!=null)
			               {
			                  	 if(replyCount.contains("<replies repliesCount="))break;
			               }
			               //System.out.println(replyCount);
			               //try{
	                      if(replyCount!=null)
	                      {
	                    	  StringTokenizer tok3 = new StringTokenizer(replyCount, "\"");
	                          tok3.nextToken();
		                      replyCount = tok3.nextToken();
	                      }
	                      
	                      else continue;
			                        	 
			                         //}catch(Exception ex)
			                         {
			                        	 //System.out.println(postID);
			                         }
	                          
	                          
	                       
	                         
	                         
	                         
	                      
	                      reader.close();
	                      
	                      ///////////////////////////////////////////////////////////////////////////////////////////
	                      
	                    File  fp = new File(rootDir+"/Texts.parsed/Problem_"+postID+".txt");
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
	      	            
	      	        fp = new File(rootDir+"/Texts.parsed/Problem_"+postID+".txt");
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
	                      
	              bw.write(postID+","+person+","+formattedDate+","+replyCount+","+four.format(uposcount)+","+four.format(unegcount)+","+four.format(utotalcount)+","+four.format(bposcount)+","+four.format(bnegcount)+","+four.format(btotalcount)+","+qcount+","+urlCount+"\n");
	                      
	                    }
	                   
	                    
	                    
	                  }
	                
	                
	                ////////////////////////////////////////////////////////////////////////////////////////////////
	                

	                
	              
        	}
        
    }
        bw.close();
        
	    
    }
}
