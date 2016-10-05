/*Whoever reading this code, I feel sorry for you. I sincerely apologize for writing code this way.*/

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class psylingClassifier {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String root = "/home/farigys/Documents/daily-strength";
		
		ArrayList<String> catname = new ArrayList<String>();
		
		File fcat = new File(root+"/Temporary Items/LIWC/LIWC2001_Categories.txt");
        FileInputStream fiscat = new FileInputStream(fcat); 
        BufferedReader readercat = new BufferedReader(new InputStreamReader(fiscat));
        
        readercat.readLine();
        
        String linecat = "";
        
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
        
		
		File f3 = new File(root+"/psylingCache.txt");
        FileInputStream fis3 = new FileInputStream(f3); 
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(fis3));
        
        HashMap<String, int[]> psylingMap = new HashMap<String, int[]>();
        
        String linec = "";
        while((linec = reader3.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(linec, ",");
        	String postName = token1.nextToken();
        	//System.out.println(postId);
        	int[] arr = new int[68];
        	int i=0;
        	while(token1.hasMoreTokens())
        	{
        		arr[i] = Integer.parseInt(token1.nextToken().toString());
        		i++;
        	}
        	psylingMap.put(postName, arr);
        }
        reader3.close();
		
		int gaps[] = {1, 3, 6, 6, 9, 12, 15, 18, 21, 24};
		//int gaps[] = {7, 14, 21, 28};
		for(int m1=0; m1<gaps.length; m1++)
		{
			Integer gap = gaps[m1];
			System.out.println("gap = "+gap);
			DecimalFormat four = new DecimalFormat("#0.0000");
	    	
	    	new File(root+"/psylingtrainandtest1").mkdir();
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	        
	        
	        
	        //////////////////////////////////////////////////////////////////////
	        
	        ////////////////////////////////////////////////////////////////////////
	        
	        File f1 = new File(root+"/memberSince.txt");
	        FileInputStream fis1 = new FileInputStream(f1); 
	        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        HashMap<String, String> mshp_comp = new HashMap<String, String>();
	        
	        String line = "";
	        
	        while((line=reader1.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(line, ":");
	        	String id = token1.nextToken();
	        	String details = token1.nextToken();
	        	mshp_comp.put(id, details);
	        }
	        System.out.println("membership and completeness done");
	        reader1.close();

			/////////////////////////////////////////////////////////////////////////////////////////
			
	        
	        
	        ////////////////////////////////////////////////////////////////////////////////////////
	        f1 = new File(root+"/timeBasedTrainList.txt");
	        fis1 = new FileInputStream(f1); 
	        reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        ArrayList<String> trainList = new ArrayList<String>();
	        
	        while((line=reader1.readLine())!=null)
	        {
	        	trainList.add(line);
	        }
	        System.out.println("trainList done");
	        reader1.close();
	        ////////////////////////////////////////////////////////////////////////////////////////
	        
			////////////////////////////////////////////////////////////////////////////////////////
			f1 = new File(root+"/timeBasedDevList.txt");
			fis1 = new FileInputStream(f1); 
			reader1 = new BufferedReader(new InputStreamReader(fis1));
			
			ArrayList<String> devList = new ArrayList<String>();
			
			while((line=reader1.readLine())!=null)
			{
				devList.add(line);
			}
			System.out.println("devList done");
			reader1.close();
			////////////////////////////////////////////////////////////////////////////////////////
			f1 = new File(root+"/timeBasedTestList.txt");
			fis1 = new FileInputStream(f1); 
			reader1 = new BufferedReader(new InputStreamReader(fis1));
			
			ArrayList<String> testList = new ArrayList<String>();
			
			while((line=reader1.readLine())!=null)
			{
				testList.add(line);
			}
			System.out.println("testList done");
			reader1.close();

			
			
			File writefile = new File("/home/farigys/Documents/daily-strength/psylingtrainandtest1/timeBasedTrainData_"+gap+"m.arff");
		    if (!writefile.exists()) {
		    	writefile.createNewFile();
		    }
		    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
			
		    bw.write("@relation trainontime"+gap+"m"+"\n\n");
		    
		    for(int l=0; l<catname.size(); l++)
		    {
		    	bw.write("@attribute "+catname.get(l)+" numeric"+"\n");
		    }
		    bw.write("@attribute class {0, 1}"+"\n\n");
		    
		    bw.write("@data"+"\n");
		    
		    
			////////////////////creating trainDataFile//////////////////////////////////////////////
				
			
		    for(int i=0; i<trainList.size(); i++)
			{
		    	int[] psycount = new int[68]; 
		    	
		    	if(i%100 == 0)System.out.println(i+ " done");
				String userId = trainList.get(i);
				
				String details = mshp_comp.get(userId);
				
				StringTokenizer token1 = new StringTokenizer(details, ",");
				
				String memTime = token1.nextToken();
				
				String year = memTime.substring(0, 2);
				String month = memTime.substring(2, 4);
				String day = memTime.substring(4);
				
				
				//using (months)/days as observation period
				Integer yr = 0;
				Integer mnth = 0;
				Integer dy = 0;
				
				try{
					yr = Integer.parseInt(year);
					mnth = Integer.parseInt(month);
					dy = Integer.parseInt(day);
				
				}catch(Exception ex)
				{
					System.out.println(userId);
				}
				
				//dy += gap;
				
//				if((mnth == 1||mnth == 3||mnth == 5||mnth == 7||mnth == 8||mnth == 10||mnth == 12) && dy>31)
//				{
//					dy = dy - 31;
//					mnth++;
//				}
				
//				else if((mnth == 4||mnth == 6||mnth == 9||mnth == 11) && dy>30)
//				{
//					dy = dy - 30;
//					mnth++;
//				}
//				else if(mnth == 2)
//				{
//					if(yr%4 == 0)
//					{
//						if(dy>29)
//						{
//							dy = dy - 29;
//							mnth++;
//						}
//					}
//					else
//					{
//
//						if(dy>28)
//						{
//							dy = dy - 28;
//							mnth++;
//						}
//					}
//				}
				
				mnth += gap;
				
				if(mnth>12)
				{
					mnth = mnth - 12;
					yr += 1;
				}
				
				String newYear = yr.toString();
				
				if(newYear.length()==1)newYear = "0"+newYear;
				
				String newMonth = mnth.toString();
				
				if(newMonth.length()==1)newMonth = "0"+newMonth;
				
				String newDay = dy.toString();
				
				if(newDay.length()==1)newDay = "0"+newDay;
				
				String cutOffTime = newYear + newMonth + newDay;
				
				int postcount=0, replycount=0, totalcount = 0, repliesreceived=0, posUnigram = 0, negUnigram = 0, totalUnigram = 0;
				
				int greply = 0, sreply = 0;
				
				int qCount = 0, url = 0;
				
				int timegap1=-1, timegap2=-1;
				
				f1 = new File(root+"/userPostIndex/"+userId+".txt");
				
				LineNumberReader  lnr = new LineNumberReader(new FileReader(f1));
				lnr.skip(Long.MAX_VALUE);
				int totalPostCount = lnr.getLineNumber();
				lnr.close();
				
				fis1 = new FileInputStream(f1); 
				reader1 = new BufferedReader(new InputStreamReader(fis1));
				
				String firstPostTime = null;
				String prevPostTime = null;
				
				int totalDays = 0;
				double avgDays = -1;
				
				while((line=reader1.readLine())!=null)
				{
					StringTokenizer token3 = new StringTokenizer(line, ":");
					String postTime = token3.nextToken();
					postTime = postTime.substring(0,6);
					if(totalcount == 0)firstPostTime = postTime;
					//if(userId.equals("471811"))System.out.println(cutOffTime);
					if(postTime.compareTo(cutOffTime)>0)
					{
						//if(totalcount==0)timegap1 = -1;
						break;
					}

					String condition = token3.nextToken();
					String postId = token3.nextToken();
					String postName = postId + ".txt";
					//////////////////////////////////////////////////////
					
					
					///////////////////////////////////////////////////////
					
					int[] emos = psylingMap.get(postName);
					
					for(int k=0; k<emos.length; k++)
					{
						psycount[k] += emos[k];
					}
					
					//////////////////////////////////////////////////////
										
					totalcount++;				
										
					//////////////////////////////////////////////////////
					
					
					
					
				}
				
				
				reader1.close();
				int hasPostedNext = 0;
				
				if(totalcount<totalPostCount)hasPostedNext = 1;
				
				for(int x=0; x<psycount.length; x++)
				{
					bw.write(psycount[x]+",");
				}
				bw.write(hasPostedNext+"\n");
				
				
			}
			bw.close();
			System.out.println("trainDataFile created for "+gap);
			////////////////////////////////////////////////////////////////////////////////////////
			
			
			writefile = new File("/home/farigys/Documents/daily-strength/psylingtrainandtest1/timeBasedTestData_"+gap+"m.arff");
		    if (!writefile.exists()) {
		    	writefile.createNewFile();
		    }
		    fw = new FileWriter(writefile.getAbsoluteFile());
		    bw = new BufferedWriter(fw);
		    
		    bw.write("@relation testontime"+gap+"m"+"\n\n");
		    
		    for(int l=0; l<catname.size(); l++)
		    {
		    	bw.write("@attribute "+catname.get(l)+" numeric"+"\n");
		    }
		    bw.write("@attribute class {0, 1}"+"\n\n");
		    
		    bw.write("@data"+"\n");
			
			////////////////////creating testDataFile//////////////////////////////////////////////
				
			
		    for(int i=0; i<testList.size(); i++)
			{
		    	int[] psycount = new int[68]; 
		    	
		    	if(i%100 == 0)System.out.println(i+ " done");
				String userId = testList.get(i);
				
				String details = mshp_comp.get(userId);
				
				StringTokenizer token1 = new StringTokenizer(details, ",");
				
				String memTime = token1.nextToken();
				
				String year = memTime.substring(0, 2);
				String month = memTime.substring(2, 4);
				String day = memTime.substring(4);
				
				
				//using (months)/days as observation period
				Integer yr = 0;
				Integer mnth = 0;
				Integer dy = 0;
				
				try{
					yr = Integer.parseInt(year);
					mnth = Integer.parseInt(month);
					dy = Integer.parseInt(day);
				
				}catch(Exception ex)
				{
					System.out.println(userId);
				}
				
				//dy += gap;
				
//				if((mnth == 1||mnth == 3||mnth == 5||mnth == 7||mnth == 8||mnth == 10||mnth == 12) && dy>31)
//				{
//					dy = dy - 31;
//					mnth++;
//				}
				
//				else if((mnth == 4||mnth == 6||mnth == 9||mnth == 11) && dy>30)
//				{
//					dy = dy - 30;
//					mnth++;
//				}
//				else if(mnth == 2)
//				{
//					if(yr%4 == 0)
//					{
//						if(dy>29)
//						{
//							dy = dy - 29;
//							mnth++;
//						}
//					}
//					else
//					{
//
//						if(dy>28)
//						{
//							dy = dy - 28;
//							mnth++;
//						}
//					}
//				}
				
				mnth += gap;
				
				if(mnth>12)
				{
					mnth = mnth - 12;
					yr += 1;
				}
				
				String newYear = yr.toString();
				
				if(newYear.length()==1)newYear = "0"+newYear;
				
				String newMonth = mnth.toString();
				
				if(newMonth.length()==1)newMonth = "0"+newMonth;
				
				String newDay = dy.toString();
				
				if(newDay.length()==1)newDay = "0"+newDay;
				
				String cutOffTime = newYear + newMonth + newDay;
				
				int postcount=0, replycount=0, totalcount = 0, repliesreceived=0, posUnigram = 0, negUnigram = 0, totalUnigram = 0;
				
				int greply = 0, sreply = 0;
				
				int qCount = 0, url = 0;
				
				int timegap1=-1, timegap2=-1;
				
				f1 = new File(root+"/userPostIndex/"+userId+".txt");
				
				LineNumberReader  lnr = new LineNumberReader(new FileReader(f1));
				lnr.skip(Long.MAX_VALUE);
				int totalPostCount = lnr.getLineNumber();
				lnr.close();
				
				fis1 = new FileInputStream(f1); 
				reader1 = new BufferedReader(new InputStreamReader(fis1));
				
				String firstPostTime = null;
				String prevPostTime = null;
				
				int totalDays = 0;
				double avgDays = -1;
				
				while((line=reader1.readLine())!=null)
				{
					StringTokenizer token3 = new StringTokenizer(line, ":");
					String postTime = token3.nextToken();
					postTime = postTime.substring(0,6);
					if(totalcount == 0)firstPostTime = postTime;
					//if(userId.equals("471811"))System.out.println(cutOffTime);
					if(postTime.compareTo(cutOffTime)>0)
					{
						//if(totalcount==0)timegap1 = -1;
						break;
					}

					String condition = token3.nextToken();
					String postId = token3.nextToken();
					String postName = postId + ".txt";
					//////////////////////////////////////////////////////
					
					
					///////////////////////////////////////////////////////
					
					int[] emos = psylingMap.get(postName);
					
					for(int k=0; k<emos.length; k++)
					{
						psycount[k] += emos[k];
					}
					
					//////////////////////////////////////////////////////
										
					totalcount++;				
										
					//////////////////////////////////////////////////////
					
					
					
					
				}
				
				
				reader1.close();
				int hasPostedNext = 0;
				
				if(totalcount<totalPostCount)hasPostedNext = 1;
				
				for(int x=0; x<psycount.length; x++)
				{
					bw.write(psycount[x]+",");
				}
				bw.write(hasPostedNext+"\n");
				
				
			}
			bw.close();
			System.out.println("testDataFile created for "+gap);
			
			/////////////////////////////////////////////////////////////////////////////////////
		}

		
		
	    
	    
		
	}

}


