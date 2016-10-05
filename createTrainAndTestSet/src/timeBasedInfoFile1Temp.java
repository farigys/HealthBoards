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


public class timeBasedInfoFile1 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int gaps[] = {1, 3, 6, 6, 9, 12, 15, 18, 21, 24};
		//int gaps[] = {7, 14, 21, 28};
		for(int m1=0; m1<gaps.length; m1++)
		{
			Integer gap = gaps[m1];
			System.out.println("gap = "+gap);
			DecimalFormat four = new DecimalFormat("#0.0000");
	    	String root = "/home/farigys/Documents/daily-strength";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	        
	        File f3 = new File(root+"/cacheFile.csv");
	        FileInputStream fis3 = new FileInputStream(f3); 
	        BufferedReader reader3 = new BufferedReader(new InputStreamReader(fis3));
	        
	        HashMap<String, Integer> cacheMap = new HashMap<String, Integer>();
	        HashMap<String, String> emoMap = new HashMap<String, String>();
	        HashMap<String, String> replyMap = new HashMap<String, String>();
	        HashSet<String> posUnigrams = new HashSet<String>();
	        HashSet<String> negUnigrams = new HashSet<String>();
	        HashSet<String> posbigrams = new HashSet<String>();
	        HashSet<String> negbigrams = new HashSet<String>();
	        
	        String linec = "";
	        while((linec = reader3.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(linec, ",");
	        	String postId = token1.nextToken();
	        	//System.out.println(postId);
	        	token1.nextToken();
	        	token1.nextToken();
	        	int replycount = Integer.parseInt(token1.nextToken());
	        	cacheMap.put(postId, replycount);
	        }
	        reader3.close();
	        
	        f3 = new File(root+"/emoCacheFile.txt");
	        fis3 = new FileInputStream(f3); 
	        reader3 = new BufferedReader(new InputStreamReader(fis3));
	        
	        linec = "";
	        while((linec = reader3.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(linec, ":");
	        	String postId = token1.nextToken();
	        	StringTokenizer token2 = new StringTokenizer(postId, ".");
	        	postId = token2.nextToken();
	        	//
	        	String emos = null;
	        	try{
	        		emos = token1.nextToken();
	        	}
	        	catch(Exception ex)
	        	{
	        		//System.out.println(postId);
	        	}
	        	
	        	emoMap.put(postId, emos);
	        	//System.out.println(postId);
	        }
	        reader3.close();
	        
	        System.out.println("repliescount, posUnigram, negUnigram done");
	        //////////////////////////////////////////////////////////////////////
	        
	        //////////////////////////////////////////////////////////////////////
	        f3 = new File(root+"/replyCountCache.txt");
	        fis3 = new FileInputStream(f3); 
	        reader3 = new BufferedReader(new InputStreamReader(fis3));
	        
	        linec = "";
	        while((linec = reader3.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(linec, " ");
	        	String postId = token1.nextToken();
	        	//
	        	String greply = null;
	        	String sreply = null;
	        	token1.nextToken();
	        	try{
	        		greply = token1.nextToken();
	        		sreply = token1.nextToken();
	        	}
	        	catch(Exception ex)
	        	{
	        		//System.out.println(postId);
	        		
	        	}
	        	String replies = greply+","+sreply;
	        	replyMap.put(postId, replies);
	        	//System.out.println(postId);
	        }
	        reader3.close();
	        
	        System.out.println("replies done");
	        ////////////////////////////////////////////////////////////////////////
	        HashMap<String, HashMap<String, String>> xmlNameMap = new HashMap<String, HashMap<String, String>>();
	        HashMap<String, String> xmlName = new HashMap<String, String>();
	        for (int m = 0; m < listOfFolders.length; m++) 
	        {
	        	if (listOfFolders[m].isDirectory())
	        	{
	        		String foldername = listOfFolders[m].getName();
	        		//String foldername = "Bipolar-Disorder";
	        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
	        		System.out.println("Now in: "+foldername);
	        		
	        		String rootDir = root+"/"+foldername;
	        		
	        		String rootDir1 = rootDir+"/XML/";
	        		
	        		File files = new File(rootDir1);
	        		File[] listoffiles = files.listFiles();
	        		HashMap<String, String> temp1 = new HashMap<String, String>();
	        		for(int n = 0; n < listoffiles.length; n++)
	        		{
	        			String filename = listoffiles[n].getName();
	        			StringTokenizer tokenx = new StringTokenizer(filename,"-.");
	        			String pid = tokenx.nextToken();
	        			//temp1.put(pid,filename);
	        			//if(pid.equals("7752543"))System.out.println("paisi");
	        			xmlName.put(pid,filename);
	        		}
	        		//xmlNameMap.put(foldername, temp1);
	        		//System.out.println(temp1.size());
	        		//System.out.println(xmlNameMap.size());
	        		
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
	        
	        System.out.println("emotion words done");
	        ////////////////////////////////////////////////////////////////////////////////
	        //////////////////////////////////////////////////////////////////////
	        
	        File f1 = new File(root+"/id_age_gender_3.txt");
	        FileInputStream fis1 = new FileInputStream(f1); 
	        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        HashMap<String, String> ageMap = new HashMap<String, String>();
	        HashMap<String, String> genderMap = new HashMap<String, String>();
	        ArrayList<String> userList = new ArrayList<String>();
	        
	        String line;
	        
	        while((line=reader1.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(line, ",");
	        	String id = token1.nextToken();
	        	userList.add(id);
	        	String tempage = token1.nextToken();
	        	//int age = Integer.parseInt(token1.nextToken());
	        	String gender = token1.nextToken();
	        	//System.out.println(id+" "+tempage+" "+gender);
	        	ageMap.put(id, tempage);
	        	genderMap.put(id, gender);
	        }
	        System.out.println("Id, age, gender done");
	        reader1.close();
	        /////////////////////////////////////////////////////////////////////////
	        
	        f1 = new File(root+"/memberSince.txt");
	        fis1 = new FileInputStream(f1); 
	        reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        HashMap<String, String> mshp_comp = new HashMap<String, String>();
	        
	        while((line=reader1.readLine())!=null)
	        {
	        	StringTokenizer token1 = new StringTokenizer(line, ":");
	        	String id = token1.nextToken();
	        	String details = token1.nextToken();
	        	mshp_comp.put(id, details);
	        }
	        System.out.println("membership and completeness done");
	        reader1.close();
	        ////////////////////////////////////////////////////////////////////////////
	        
	        
	        ////////////////test, train, dev generation/////////////////////////////////
//	        Collections.shuffle(userList);
//	        
//	        File writefile = new File("/home/farigys/Documents/daily-strength/timeBasedTrainList.txt");
//		    if (!writefile.exists()) {
//		          writefile.createNewFile();
//		    }
//		    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
//		    BufferedWriter bw = new BufferedWriter(fw);
//	        
//		    int i = 0;
//		    
//	        for(i=0; i<0.6*userList.size(); i++)
//	        {
//	        	bw.write(userList.get(i)+"\n");
//	        }
//	        bw.close();
//	        
//	        writefile = new File("/home/farigys/Documents/daily-strength/timeBasedDevList.txt");
//		    if (!writefile.exists()) {
//		          writefile.createNewFile();
//		    }
//		    fw = new FileWriter(writefile.getAbsoluteFile());
//		    bw = new BufferedWriter(fw);
//	        
//	        for(; i<0.8*userList.size(); i++)
//	        {
//	        	bw.write(userList.get(i)+"\n");
//	        }
//	        bw.close();
//	        
//	        writefile = new File("/home/farigys/Documents/daily-strength/timeBasedTestList.txt");
//		    if (!writefile.exists()) {
//		          writefile.createNewFile();
//		    }
//		    fw = new FileWriter(writefile.getAbsoluteFile());
//		    bw = new BufferedWriter(fw);
//	        
//	        for(; i<userList.size(); i++)
//	        {
//	        	bw.write(userList.get(i)+"\n");
//	        }
//	        bw.close();
	        ////////////////////////////////////////////////////////////////////////////////////////
	        
	        
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
			/////////////////////////////////////////////////////////////////////////////////////////
			
			HashMap<String, featureVector> trainData = new HashMap<String, featureVector>();
			HashMap<String, featureVector> devData = new HashMap<String, featureVector>();
			HashMap<String, featureVector> testData = new HashMap<String, featureVector>();
			
			
			File writefile = new File("/home/farigys/Documents/daily-strength/timeBasedTrainData_"+gap+"m.txt");
		    if (!writefile.exists()) {
		    	writefile.createNewFile();
		    }
		    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
			
			////////////////////creating trainDataFile//////////////////////////////////////////////
				
			
		    for(int i=0; i<trainList.size(); i++)
			{
		    	if(i%100 == 0)System.out.println(i+ " done");
				String userId = trainList.get(i);
				String age = ageMap.get(userId);
				String gender = genderMap.get(userId);
				
				String details = mshp_comp.get(userId);
				
				StringTokenizer token1 = new StringTokenizer(details, ",");
				
				String memTime = token1.nextToken();
				String hasAge =  token1.nextToken();
				String hasGender = token1.nextToken();;
				String hasLoc = token1.nextToken();
				String hasImage = token1.nextToken();
				String completeness = token1.nextToken();
				
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
					//////////////////////////////////////////////////////
					StringTokenizer token4 = new StringTokenizer(postId, "_");
					token4.nextToken();
					String pId = token4.nextToken();
					
					if(postId.contains("Problem"))
					{
						//int replyCount = cacheMap.get(pId);
						//repliesreceived += replyCount;
						String rootDir = root+"/"+condition+"/XML/";
						
						//File dir = new File(rootDir);
						//File[] foundFiles =  
						
						//HashMap<String, String> temp = xmlNameMap.get(condition);
						
						String postname = xmlName.get(pId);
						//System.out.println(pId + " " + postname + " "+ userId+" "+condition);
						if(postname.equals(null))System.out.println(pId+" "+condition);
						File inputFile = new File(rootDir+postname);
	        	        FileInputStream fisi = new FileInputStream(inputFile);
	        	        BufferedReader readeri = new BufferedReader(new InputStreamReader(fisi));
	        	        int flag = 0;
	        	        int tgreply = 0, tsreply = 0;
	        	        
	        	        String posterId = "";
	        	        String replierId = "";
	        	        
	        	        String tline = "";
	        	        
	        	        while((tline = readeri.readLine())!=null)
	        	        {
	        	        	if(tline.equals("</document>"))break;
	        	        	if(tline.contains("<person id="))
	        	        	{
	        	        		//System.out.println(tline);
	        	        		StringTokenizer ttoken1 = new StringTokenizer(tline, "\"/");
	        	    			ttoken1.nextToken();
	        	    			ttoken1.nextToken();
	        	        		if(flag == 0)
	        	        		{
	        	        			//System.out.println(token1.nextToken());
	        	        			posterId = ttoken1.nextToken();
	        	        			flag = 1;
	        	        		}
	        	        		else
	        	        		{
	        	        			replierId = ttoken1.nextToken();
	        	        			if(replierId.equals(posterId))tsreply++;
	        	        			else tgreply++;
	        	        		}
	        	        	}
	        	        	if(tline.contains("<date>"))
	        	        	{
	        	        		StringTokenizer token5 = new StringTokenizer(tline, "> /");
	        	        		token5.nextToken();
	        	        		String tmonth = token5.nextToken().toString();
	        	        		String tday = token5.nextToken().toString();
	        	        		String tyear = token5.nextToken().toString();
	        	        		
	        	        		String tcurrtime = tyear+tmonth+tday;
	        	        		//System.out.println(tcurrtime);
	        	        		if(tcurrtime.compareTo(cutOffTime)>0)
	        	        		{
	        	        			if(replierId.equals(posterId))tsreply--;
	        	        			else tgreply--;
	        	        			break;
	        	        		}
	        	        		
	        	        	}
	        	        }
	        	        
	        	        greply += tgreply;
	        	        sreply += tsreply;
	        	        readeri.close();
	        	        postcount++;
					}
					else
					{
						replycount++;						
					}
					///////////////////////////////////////////////////////
					
					String emos = emoMap.get(postId);
					StringTokenizer tokenx = new StringTokenizer(emos, ",");
					
					posUnigram += Integer.parseInt(tokenx.nextToken());
					negUnigram += Integer.parseInt(tokenx.nextToken());
					totalUnigram += Integer.parseInt(tokenx.nextToken());
					tokenx.nextToken();
					tokenx.nextToken();
					tokenx.nextToken();
					qCount += Integer.parseInt(tokenx.nextToken());
					url += Integer.parseInt(tokenx.nextToken());
					///////////////////////////////////////////////////////
					totalcount++;
					
					//////////////////////////////////////////////////////
										
					if(prevPostTime!=null)
					{
						String prevyear = prevPostTime.substring(0, 2);
						String prevmonth = prevPostTime.substring(2, 4);
						String prevday = prevPostTime.substring(4, 6);
					
						String curryear = cutOffTime.substring(0, 2);
						String currmonth = cutOffTime.substring(2, 4);
						String currday = cutOffTime.substring(4, 6);
					            
					    totalDays += (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
					}					
										
					//////////////////////////////////////////////////////
					
					prevPostTime = postTime;
					
					
				}
				if(totalcount>0)
				{
					String prevyear = memTime.substring(0, 2);
		            String prevmonth = memTime.substring(2, 4);
		            String prevday = memTime.substring(4, 6);
		
		        	String curryear = firstPostTime.substring(0, 2);
		            String currmonth = firstPostTime.substring(2, 4);
		            String currday = firstPostTime.substring(4, 6);
		            
		            timegap1 = (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
		            
				}
				if(prevPostTime!=null)
				{
					String prevyear = prevPostTime.substring(0, 2);
					String prevmonth = prevPostTime.substring(2, 4);
					String prevday = prevPostTime.substring(4, 6);
				
					String curryear = cutOffTime.substring(0, 2);
					String currmonth = cutOffTime.substring(2, 4);
					String currday = cutOffTime.substring(4, 6);
				            
				    timegap2 = (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
				}
				
				if(totalcount>0)
				{
					avgDays = (double)(totalDays/totalcount);
				}
				
				reader1.close();
				int hasPostedNext = 0;
				
				if(totalcount<totalPostCount)hasPostedNext = 1;
				
				
				featureVector tempVec = new featureVector();
				tempVec.userId = userId;
				tempVec.postcount = postcount;
				tempVec.replycount = replycount;
				tempVec.repliesreceived = repliesreceived;
				tempVec.timegap1 = timegap1;
				tempVec.timegap2 = timegap2;
				tempVec.age = age;
				tempVec.gender = gender;
				tempVec.hasLoc = hasLoc;
				tempVec.hasImage = hasImage;
				tempVec.completeness = completeness;
				tempVec.posUnigram = posUnigram;
				tempVec.negUnigram = negUnigram;
				tempVec.totalUnigram = totalUnigram;
				tempVec.hasPostedNext = hasPostedNext;
				
				trainData.put(userId, tempVec);
				
				int treplycount = replycount - sreply;
				
				if(treplycount<0)
				{
					System.out.println("Problem with "+userId+":"+replycount+" "+sreply+" "+greply);
					
				}
				
				bw.write(postcount+","+treplycount+","+greply+","+sreply+","+timegap1+","+timegap2+","
				        +avgDays+","+age+","+gender+","+hasLoc+","+hasImage+","+posUnigram+","+negUnigram+","
						+totalUnigram+","+qCount+","+url+","+hasPostedNext+"\n");
			}
			bw.close();
			System.out.println("trainDataFile created for "+gap);
			////////////////////////////////////////////////////////////////////////////////////////
			writefile = new File("/home/farigys/Documents/daily-strength/timeBasedTestData_"+gap+"m.txt");
		    if (!writefile.exists()) {
		    	writefile.createNewFile();
		    }
		    fw = new FileWriter(writefile.getAbsoluteFile());
		    bw = new BufferedWriter(fw);
			
		    
			//////////////////creating DevDataFile//////////////////////////////////////////////
					
		    for(int i=0; i<testList.size(); i++)
			{
		    	if(i%100 == 0)System.out.println(i+ " done test");
				String userId = testList.get(i);
				String age = ageMap.get(userId);
				String gender = genderMap.get(userId);
				
				String details = mshp_comp.get(userId);
				
				StringTokenizer token1 = new StringTokenizer(details, ",");
				
				String memTime = token1.nextToken();
				String hasAge =  token1.nextToken();
				String hasGender = token1.nextToken();;
				String hasLoc = token1.nextToken();
				String hasImage = token1.nextToken();
				String completeness = token1.nextToken();
				
				String year = memTime.substring(0, 2);
				String month = memTime.substring(2, 4);
				String day = memTime.substring(4);
				
				
				//using months as observation period
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
				
//				dy += gap;
//				
//				if((mnth == 1||mnth == 3||mnth == 5||mnth == 7||mnth == 8||mnth == 10||mnth == 12) && dy>31)
//				{
//					dy = dy - 31;
//					mnth++;
//				}
//				
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
					
					if(postTime.compareTo(cutOffTime)>0)
					{
						//if(totalcount==0)timegap1 = -1;
						break;
					}

					String condition = token3.nextToken();
					String postId = token3.nextToken();
					//////////////////////////////////////////////////////
					StringTokenizer token4 = new StringTokenizer(postId, "_");
					token4.nextToken();
					String pId = token4.nextToken();
					
					if(postId.contains("Problem"))
					{
						//int replyCount = cacheMap.get(pId);
						//repliesreceived += replyCount;
						String rootDir = root+"/"+condition+"/XML/";
						
						//File dir = new File(rootDir);
						//File[] foundFiles =  
						
						//HashMap<String, String> temp = xmlNameMap.get(condition);
						
						String postname = xmlName.get(pId);
						
						//System.out.println(pId + " " + postname + " "+ userId+" "+condition);
						if(postname.equals(null))System.out.println(pId+" "+condition);
						
						File inputFile = new File(rootDir+postname);
	        	        FileInputStream fisi = new FileInputStream(inputFile);
	        	        BufferedReader readeri = new BufferedReader(new InputStreamReader(fisi));
	        	        int flag = 0;
	        	        int tgreply = 0, tsreply = 0;
	        	        
	        	        String posterId = "";
	        	        String replierId = "";
	        	        
	        	        String tline = "";
	        	        
	        	        while((tline = readeri.readLine())!=null)
	        	        {
	        	        	if(tline.equals("</document>"))break;
	        	        	if(tline.contains("<person id="))
	        	        	{
	        	        		//System.out.println(tline);
	        	        		StringTokenizer ttoken1 = new StringTokenizer(tline, "\"/");
	        	    			ttoken1.nextToken();
	        	    			ttoken1.nextToken();
	        	        		if(flag == 0)
	        	        		{
	        	        			//System.out.println(token1.nextToken());
	        	        			posterId = ttoken1.nextToken();
	        	        			flag = 1;
	        	        		}
	        	        		else
	        	        		{
	        	        			replierId = ttoken1.nextToken();
	        	        			if(replierId.equals(posterId))tsreply++;
	        	        			else tgreply++;
	        	        		}
	        	        	}
	        	        	if(tline.contains("<date>"))
	        	        	{
	        	        		StringTokenizer token5 = new StringTokenizer(tline, "> /");
	        	        		token5.nextToken();
	        	        		String tmonth = token5.nextToken().toString();
	        	        		String tday = token5.nextToken().toString();
	        	        		String tyear = token5.nextToken().toString();
	        	        		
	        	        		String tcurrtime = tyear+tmonth+tday;
	        	        		//System.out.println(tcurrtime);
	        	        		if(tcurrtime.compareTo(cutOffTime)>0)
	        	        		{
	        	        			if(replierId.equals(posterId))tsreply--;
	        	        			else tgreply--;
	        	        			break;
	        	        		}
	        	        		
	        	        	}
	        	        }
	        	        
	        	        greply += tgreply;
	        	        sreply += tsreply;
	        	        readeri.close();
	        	        postcount++;
					}
					else
					{
						replycount++;						
					}
					///////////////////////////////////////////////////////
					
					String emos = emoMap.get(postId);
					StringTokenizer tokenx = new StringTokenizer(emos, ",");
					
					posUnigram += Integer.parseInt(tokenx.nextToken());
					negUnigram += Integer.parseInt(tokenx.nextToken());
					totalUnigram += Integer.parseInt(tokenx.nextToken());
					tokenx.nextToken();
					tokenx.nextToken();
					tokenx.nextToken();
					qCount += Integer.parseInt(tokenx.nextToken());
					url += Integer.parseInt(tokenx.nextToken());
					///////////////////////////////////////////////////////
					totalcount++;
					
					//////////////////////////////////////////////////////
										
					if(prevPostTime!=null)
					{
						String prevyear = prevPostTime.substring(0, 2);
						String prevmonth = prevPostTime.substring(2, 4);
						String prevday = prevPostTime.substring(4, 6);
					
						String curryear = cutOffTime.substring(0, 2);
						String currmonth = cutOffTime.substring(2, 4);
						String currday = cutOffTime.substring(4, 6);
					            
					    totalDays += (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
					}					
										
					//////////////////////////////////////////////////////
					
					prevPostTime = postTime;
					
					
				}
				if(totalcount>0)
				{
					String prevyear = memTime.substring(0, 2);
		            String prevmonth = memTime.substring(2, 4);
		            String prevday = memTime.substring(4, 6);
		
		        	String curryear = firstPostTime.substring(0, 2);
		            String currmonth = firstPostTime.substring(2, 4);
		            String currday = firstPostTime.substring(4, 6);
		            
		            timegap1 = (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
		            
				}
				if(prevPostTime!=null)
				{
					String prevyear = prevPostTime.substring(0, 2);
					String prevmonth = prevPostTime.substring(2, 4);
					String prevday = prevPostTime.substring(4, 6);
				
					String curryear = cutOffTime.substring(0, 2);
					String currmonth = cutOffTime.substring(2, 4);
					String currday = cutOffTime.substring(4, 6);
				            
				    timegap2 = (Integer.parseInt(curryear)-Integer.parseInt(prevyear))*365 + (Integer.parseInt(currmonth)-Integer.parseInt(prevmonth))*30 + (Integer.parseInt(currday)-Integer.parseInt(prevday));
				}
				
				if(totalcount>0)
				{
					avgDays = (double)(totalDays/totalcount);
				}
				
				reader1.close();
				int hasPostedNext = 0;
				
				if(totalcount<totalPostCount)hasPostedNext = 1;
				
				
				featureVector tempVec = new featureVector();
				tempVec.userId = userId;
				tempVec.postcount = postcount;
				tempVec.replycount = replycount;
				tempVec.repliesreceived = repliesreceived;
				tempVec.timegap1 = timegap1;
				tempVec.timegap2 = timegap2;
				tempVec.age = age;
				tempVec.gender = gender;
				tempVec.hasLoc = hasLoc;
				tempVec.hasImage = hasImage;
				tempVec.completeness = completeness;
				tempVec.posUnigram = posUnigram;
				tempVec.negUnigram = negUnigram;
				tempVec.totalUnigram = totalUnigram;
				tempVec.hasPostedNext = hasPostedNext;
				
				//replycount -= sreply;
				
				testData.put(userId, tempVec);
				
				int treplycount = replycount - sreply;
				
				if(treplycount<0)
				{
					System.out.println("Problem with "+userId+":"+replycount+" "+sreply+" "+greply);
					
				}
				
				bw.write(userId+":"+postcount+","+treplycount+","+greply+","+sreply+","+timegap1+","+timegap2+","
				        +avgDays+","+age+","+gender+","+hasLoc+","+hasImage+","+posUnigram+","+negUnigram+","
						+totalUnigram+","+qCount+","+url+","+hasPostedNext+"\n");
			}
			bw.close();
			System.out.println("TestDataFile created for "+gap);
			/////////////////////////////////////////////////////////////////////////////////////
		}

		
		
	    
	    
		
	}

}

