import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.StringTokenizer;


public class createEmoCacheFile {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String formattedDate = new String();
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        
        HashSet<String> posUnigrams = new HashSet<String>();
        HashSet<String> negUnigrams = new HashSet<String>();
        HashSet<String> posbigrams = new HashSet<String>();
        HashSet<String> negbigrams = new HashSet<String>();
        
        //////////////////////////////////////////////////////////////////////
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bipolar-Disorder";
        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+foldername;
        		
        		 File  funigram = new File(rootDir+"/FilteredPosEmotionUnigramsFrequency.txt");
        	     FileInputStream fisunigram = new FileInputStream(funigram); 
        	     BufferedReader readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
        	     String line1 = "";
        	           	        
        	      
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
        	        
        	        
        	        
        	     while((line2=readerbigram.readLine())!=null)
        	     {
        	       	StringTokenizer token1 = new StringTokenizer(line2,"\t");
        	       	String temp = token1.nextToken();
        	       	negbigrams.add(temp);
        	     }
        	        
        	     readerbigram.close();
        	}
        }
        
        System.out.println("emotion words collection done");
        ////////////////////////////////////////////////////////////////////////////////
        
        File writefile = new File(root+"/emoCacheFile1.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	folder = new File(root);
        listOfFolders = folder.listFiles();
    	
    	
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bipolar-Disorder";
        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		
        		String rootD = root + foldername + "/Texts.parsed/";
        		
        		File folder1 = new File(rootD);
                File[] listOfFolders1 = folder1.listFiles();
                
                int postcount = 0;
                
                for (int n = 0; n < listOfFolders1.length; n++) 
                {
                	postcount++;
                	if(postcount%1000 == 0)System.out.println(postcount + " done");
                	if (!listOfFolders1[n].isDirectory())
                	{
                		String postId = listOfFolders1[n].getName();
                		File fp = new File(rootD+postId);
                        FileInputStream fisp = new FileInputStream(fp); 
                        BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
                        
                        String currline = "";
          	            int uposcount =0, unegcount =0, utotalcount = 0;
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
                       
          	            readerp.close();
          	            
          	            fp = new File(rootD+postId);
    	      	        fisp = new FileInputStream(fp); 
    	      	        readerp = new BufferedReader(new InputStreamReader(fisp));
    	  	            currline = "";
    	  	            int bposcount =0, bnegcount =0, btotalcount = 0;
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
    	              
    		          readerp.close();
    		          bw.write(postId + ":"+(uposcount)+","+(unegcount)+","+(utotalcount)+","+(bposcount)+","+(bnegcount)+","+(btotalcount)+","+qcount+","+urlCount+"\n");
                    
                        
                	}
                }
        	}
        }
    	
    	
    	

        
        
	}

}
