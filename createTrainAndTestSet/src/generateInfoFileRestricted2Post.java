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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class generateInfoFileRestricted2Post {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		HashMap<String, Integer> conditionMap = new HashMap<String, Integer>();
        String[] cond = {"Acne", "ADHD", "Alcoholism", "Asthma", "Back-Pain", "Bipolar-Disorder", "Bone-Cancer", "COPD", "Diets-Weight-Maintenance", "Fibromyalgia", "Gastric-Bypass-Surgery", "Immigration-Law", "Infertility", "Loneliness", "Lung-Cancer", "Migraine", "Miscarriage", "Pregnancy", "Rheumatoid-Arthritis", "War-In-Iraq"};                                                                                            
		for(int i=0; i<20; i++)
		{
			conditionMap.put(cond[i], i);
		}
        DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
       
        HashSet<String> posUnigrams = new HashSet<String>();
        HashSet<String> negUnigrams = new HashSet<String>();
        HashSet<String> posbigrams = new HashSet<String>();
        HashSet<String> negbigrams = new HashSet<String>();
        //////////////////////////////////////////////////////////////////////
        
        File f3 = new File(root+"/cacheFile.csv");
        FileInputStream fis3 = new FileInputStream(f3); 
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(fis3));
        HashMap<String, Integer> cacheMap = new HashMap<String, Integer>();
        
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
        System.out.println("repliescount done");
        //////////////////////////////////////////////////////////////////////
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bipolar-Disorder";
        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+"/"+foldername;
        		
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
        
    	File f1 = new File(root+"/TrainList2Post.txt");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        String line;
        
        ArrayList<String> trainUser = new ArrayList<String>();
        ArrayList<String> testUser = new ArrayList<String>();
        
        while((line=reader1.readLine())!=null)
        {
        	String id = line;
        	trainUser.add(id);
        }
        
        reader1.close();
        
        f1 = new File(root+"/TestList2Post.txt");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        while((line=reader1.readLine())!=null)
        {
        	String id = line;
        	testUser.add(id);
        }
        reader1.close();
        
        f1 = new File(root+"/id_age_gender.csv");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        HashMap<String, String> ageMap = new HashMap<String, String>();
        HashMap<String, String> genderMap = new HashMap<String, String>();
                
        while((line=reader1.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(line, ";");
        	String id = token1.nextToken();
        	String tempage = token1.nextToken();
        	//int age = Integer.parseInt(token1.nextToken());
        	String gender = token1.nextToken();
        	//System.out.println(id+" "+tempage+" "+gender);
        	ageMap.put(id, tempage);
        	genderMap.put(id, gender);
        }

       HashMap<String, FeatureMatrix2Post> trainMap = new HashMap<String, FeatureMatrix2Post>();
       HashMap<String, FeatureMatrix2Post> testMap = new HashMap<String, FeatureMatrix2Post>();
        
       FeatureMatrix2Post tempMat;
       
       File writefile = new File(root+"/train_data_on_2_post.txt");
       FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
   	   BufferedWriter bw = new BufferedWriter(fw);
       
       ////////////////train////////////////////////////////
       System.out.println("Training data creating.....");
       for(int i=0; i<trainUser.size(); i++)
       {
    	   int hasPostedNext = 0;
    	   tempMat = new FeatureMatrix2Post();
    	   String id = trainUser.get(i);
    	   System.out.println(id);
    	   tempMat.id = id;
    	   //System.out.println(ageMap.size());
    	   String age = ageMap.get(id);
    	   String gender = genderMap.get(id);
    	   String vals = htmlParse(id);
    	   int hasAge = Character.getNumericValue(vals.charAt(0));
       	   int hasGender = Character.getNumericValue(vals.charAt(1));
       	   int hasLoc = Character.getNumericValue(vals.charAt(2));
       	   int hasImage = Character.getNumericValue(vals.charAt(3));
       	   int completeness = hasAge + hasGender + hasLoc + hasImage;
    	   //System.out.println("done");
    	   ///////////////////////////////////////////////////
    	   
    	   String isProblem1 = "0", isProblem2 = "0";
    	   
    	   /////////////////post1//////////////////////////////////
    	   File f2 = new File(root+"/userPostIndex2Posts/"+id+".txt");
           LineNumberReader  lnr = new LineNumberReader(new FileReader(f2));
           lnr.skip(Long.MAX_VALUE);
           if(lnr.getLineNumber()>2)hasPostedNext = 1;
           FileInputStream fis2 = new FileInputStream(f2); 
           BufferedReader reader2 = new BufferedReader(new InputStreamReader(fis2));
           String line1 = reader2.readLine();
           StringTokenizer token = new StringTokenizer(line1, ":");
           String time1 = token.nextToken();
           String condition1 = token.nextToken();
           String postId1 = token.nextToken();
           if(postId1.contains("Problem"))isProblem1 = "1";
           reader2.close();
           
           String year1 = Character.toString(time1.charAt(0)) + Character.toString(time1.charAt(1));
           String month1 = Character.toString(time1.charAt(2)) + Character.toString(time1.charAt(3));
           String day1 = Character.toString(time1.charAt(4)) + Character.toString(time1.charAt(5));
           
           
           f2 = new File(root+"/"+condition1+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           String currline = "";
           
	       double uposcount =0, unegcount =0, utotalcount =1;
	       int qcount=0, urlCount = 0;
	       while((currline=reader2.readLine())!=null)
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
	       
	       f2 = new File(root+"/"+condition1+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
           double bposcount =0, bnegcount =0, btotalcount =1;
	       while((currline=reader2.readLine())!=null)
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
        
            reader2.close();  
            
            double posUniCount1 = uposcount; //done
        	double negUniCount1 = unegcount; //done 
        	double totalUniCount1 = utotalcount; //done
        	double posBiCount1 = bposcount; //done
        	double negBiCount1 = bnegcount; //done
        	double totalBiCount1 = btotalcount; //done
        	
        	StringTokenizer token1 = new StringTokenizer(postId1, "_");
        	token1.nextToken();
        	String postNo = token1.nextToken();
        	
        	//System.out.println(cacheMap.size()+" "+postNo);
        	
        	int repliesCount1 = cacheMap.get(postNo);
           
           //////////////////post2//////////////////////////////////////////////
           
	       f2 = new File(root+"/userPostIndex2Posts/"+id+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           reader2.readLine();
           line1 = reader2.readLine();
           token = new StringTokenizer(line1, ":");
           String time2 = token.nextToken();
           String condition2 = token.nextToken();
           String postId2 = token.nextToken();
           if(postId2.contains("Problem"))isProblem2 = "1";
           reader2.close();
           
           String year2 = Character.toString(time1.charAt(0)) + Character.toString(time1.charAt(1));
           String month2 = Character.toString(time1.charAt(2)) + Character.toString(time1.charAt(3));
           String day2 = Character.toString(time1.charAt(4)) + Character.toString(time1.charAt(5));
           
           
           f2 = new File(root+"/"+condition2+"/Texts.parsed/"+postId2+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
	       uposcount =0; unegcount =0; utotalcount =1;
	       qcount=0; urlCount = 0;
	       while((currline=reader2.readLine())!=null)
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
	       
	       f2 = new File(root+"/"+condition2+"/Texts.parsed/"+postId2+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
           bposcount =0; bnegcount =0; btotalcount =1;
	       while((currline=reader2.readLine())!=null)
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
        
            reader2.close();  
            
            double posUniCount2 = uposcount; //done
        	double negUniCount2 = unegcount; //done 
        	double totalUniCount2 = utotalcount; //done
        	double posBiCount2 = bposcount; //done
        	double negBiCount2 = bnegcount; //done
        	double totalBiCount2 = btotalcount; //done
        	
        	token1 = new StringTokenizer(postId2, "_");
        	token1.nextToken();
        	postNo = token1.nextToken();
        	
        	int repliesCount2 = cacheMap.get(postNo);
           
           /////////////////////////////////////////////////////////////////
           String isCondDiff = "1";
           if(condition1.equals(condition2))isCondDiff = "0";
           
           String isProblemPattern = "";
           if(isProblem1.equals("0") && isProblem2.equals("0"))isProblemPattern = "0";
           if(isProblem1.equals("0") && isProblem2.equals("1"))isProblemPattern = "1";
           if(isProblem1.equals("1") && isProblem2.equals("0"))isProblemPattern = "2";
           if(isProblem1.equals("1") && isProblem2.equals("1"))isProblemPattern = "3";
           ////////////////////////////////////////////////////////////////
           
           String uniCountPattern = "0";
       	   String posUniPattern = "0";
       	   String negUniPattern = "0";
       	   String biCountPattern = "0";
       	   String posBiPattern = "0";
       	   String negBiPattern = "0";
           
           if(posUniCount1 < posUniCount2)posUniPattern = "1";
           if(negUniCount1 < negUniCount2)negUniPattern = "1";
           if(posBiCount1 < posBiCount2)posBiPattern = "1";
           if(negBiCount1 < negBiCount2)negBiPattern = "1";
           if(totalUniCount1 < totalUniCount2)uniCountPattern = "1";
           if(totalBiCount1 < totalBiCount2)biCountPattern = "1";
           ///////////////////////////////////////////////////////////////
           int totalRepliesCount = repliesCount1 + repliesCount2;
           String RCPattern = "0";
           if(repliesCount1 > repliesCount2)RCPattern = "1";
           if(repliesCount1 < repliesCount2)RCPattern = "2";
           ///////////////////////////////////////////////////////////////
           int timeGap = (Integer.parseInt(year2)-Integer.parseInt(year1))*365 + (Integer.parseInt(month2)-Integer.parseInt(month1))*30 + (Integer.parseInt(day2)-Integer.parseInt(day1));
           
           tempMat.age = age;
           tempMat.gender = gender;
           tempMat.timeGap = timeGap;
           tempMat.condition1 = conditionMap.get(condition1);
           tempMat.condition2 = conditionMap.get(condition2);
           tempMat.isCondDiff = isCondDiff;
           tempMat.isProblem1 = isProblem1;
           tempMat.isProblem2 = isProblem2;
           tempMat.isProblemPattern = isProblemPattern;
           tempMat.repliesCount1 = repliesCount1;
           tempMat.repliesCount2 = repliesCount2;
           tempMat.totalRepliesCount = repliesCount1 + repliesCount2;
           tempMat.RCPattern = RCPattern;
           tempMat.hasLoc = hasLoc;
           tempMat.hasImage = hasImage;
           tempMat.completeness = completeness;
           tempMat.posUniCount1 = posUniCount1;
           tempMat.posUniCount2 = posUniCount2;
           tempMat.posBiCount1 = posBiCount1;
           tempMat.posBiCount2 = posBiCount2;
           tempMat.negUniCount1 = negUniCount1;
           tempMat.negUniCount2 = negUniCount2;
           tempMat.negBiCount1 = negBiCount1;
           tempMat.negBiCount2 = negBiCount2;
           tempMat.totalUniCount1 = totalUniCount1;
           tempMat.totalUniCount2 = totalUniCount2;
           tempMat.totalBiCount1 = totalBiCount1;
           tempMat.totalBiCount2 = totalBiCount2;
           tempMat.uniCountPattern = uniCountPattern;
           tempMat.biCountPattern = biCountPattern;
           tempMat.posUniPattern = posUniPattern;
           tempMat.negUniPattern = negUniPattern;
           tempMat.posBiPattern = posBiPattern;
           tempMat.negBiPattern = negBiPattern;
           tempMat.hasPostedNext = hasPostedNext;
           
           bw.write(tempMat.id+","+tempMat.age+","+tempMat.gender+","+tempMat.timeGap+","+tempMat.condition1+","+tempMat.condition2+","+tempMat.isCondDiff+","+tempMat.isProblem1+","+tempMat.isProblem2+","+tempMat.isProblemPattern+","+
        		   tempMat.repliesCount1+","+tempMat.repliesCount2+","+tempMat.totalRepliesCount+","+tempMat.RCPattern+","+tempMat.hasLoc+","+tempMat.hasImage+","+tempMat.completeness+","+tempMat.posUniCount1+","+tempMat.negUniCount1+","+tempMat.totalUniCount1+","+
        		   tempMat.posBiCount1+","+tempMat.negBiCount1+","+tempMat.totalBiCount1+","+tempMat.posUniCount2+","+tempMat.negUniCount2+","+tempMat.totalUniCount2+","+tempMat.posBiCount2+","+tempMat.negBiCount2+","+tempMat.totalBiCount2+","+
        		   tempMat.uniCountPattern+","+tempMat.biCountPattern+","+tempMat.posUniPattern+","+tempMat.negUniPattern+","+tempMat.posBiPattern+","+tempMat.negBiPattern+","+tempMat.hasPostedNext+"\n");
       }
       bw.close();
       
       writefile = new File(root+"/test_data_on_2_post.txt");
       fw = new FileWriter(writefile.getAbsoluteFile());
   	   bw = new BufferedWriter(fw);
   	   
       ////////////////test////////////////////////////////
   	   
   	   System.out.println("Testing data creating.....");
       for(int i=0; i<testUser.size(); i++)
       {
    	   int hasPostedNext = 0;
    	   tempMat = new FeatureMatrix2Post();
    	   String id = testUser.get(i);
    	   System.out.println(id);
    	   tempMat.id = id;
    	   String age = ageMap.get(id);
    	   String gender = genderMap.get(id);
    	   String vals = htmlParse(id);
    	   int hasAge = Character.getNumericValue(vals.charAt(0));
       	   int hasGender = Character.getNumericValue(vals.charAt(1));
       	   int hasLoc = Character.getNumericValue(vals.charAt(2));
       	   int hasImage = Character.getNumericValue(vals.charAt(3));
       	   int completeness = hasAge + hasGender + hasLoc + hasImage;
    	   
    	   ///////////////////////////////////////////////////
    	   
    	   String isProblem1 = "0", isProblem2 = "0";
    	   
    	   /////////////////post1//////////////////////////////////
    	   File f2 = new File(root+"/userPostIndex2Posts/"+id+".txt"); 
    	   LineNumberReader  lnr = new LineNumberReader(new FileReader(f2));
           lnr.skip(Long.MAX_VALUE);
           if(lnr.getLineNumber()>2)hasPostedNext = 1;
           FileInputStream fis2 = new FileInputStream(f2); 
           BufferedReader reader2 = new BufferedReader(new InputStreamReader(fis2));
           String line1 = reader2.readLine();
           StringTokenizer token = new StringTokenizer(line1, ":");
           String time1 = token.nextToken();
           String condition1 = token.nextToken();
           String postId1 = token.nextToken();
           if(postId1.contains("Problem"))isProblem1 = "1";
           reader2.close();
           
           String year1 = Character.toString(time1.charAt(0)) + Character.toString(time1.charAt(1));
           String month1 = Character.toString(time1.charAt(2)) + Character.toString(time1.charAt(3));
           String day1 = Character.toString(time1.charAt(4)) + Character.toString(time1.charAt(5));
           
           
           f2 = new File(root+"/"+condition1+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           String currline = "";
           
	       double uposcount =0, unegcount =0, utotalcount =1;
	       int qcount=0, urlCount = 0;
	       while((currline=reader2.readLine())!=null)
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
	       
	       f2 = new File(root+"/"+condition1+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
           double bposcount =0, bnegcount =0, btotalcount =1;
	       while((currline=reader2.readLine())!=null)
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
        
            reader2.close();  
            
            double posUniCount1 = uposcount; //done
        	double negUniCount1 = unegcount; //done 
        	double totalUniCount1 = utotalcount; //done
        	double posBiCount1 = bposcount; //done
        	double negBiCount1 = bnegcount; //done
        	double totalBiCount1 = btotalcount; //done
        	
        	StringTokenizer token1 = new StringTokenizer(postId1, "_");
        	token1.nextToken();
        	String postNo = token1.nextToken();
        	
        	int repliesCount1 = cacheMap.get(postNo);
           
           //////////////////post2//////////////////////////////////////////////
           
	       f2 = new File(root+"/userPostIndex2Posts/"+id+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           line1 = reader2.readLine();
           token = new StringTokenizer(line1, ":");
           String time2 = token.nextToken();
           String condition2 = token.nextToken();
           String postId2 = token.nextToken();
           if(postId2.contains("Problem"))isProblem2 = "1";
           reader2.close();
           
           String year2 = Character.toString(time1.charAt(0)) + Character.toString(time1.charAt(1));
           String month2 = Character.toString(time1.charAt(2)) + Character.toString(time1.charAt(3));
           String day2 = Character.toString(time1.charAt(4)) + Character.toString(time1.charAt(5));
           
           
           f2 = new File(root+"/"+condition2+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
	       uposcount =0; unegcount =0; utotalcount =1;
	       qcount=0; urlCount = 0;
	       while((currline=reader2.readLine())!=null)
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
	       
	       f2 = new File(root+"/"+condition2+"/Texts.parsed/"+postId1+".txt");
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));
           
           currline = "";
           
           bposcount =0; bnegcount =0; btotalcount =1;
	       while((currline=reader2.readLine())!=null)
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
        
            reader2.close();  
            
            double posUniCount2 = uposcount; //done
        	double negUniCount2 = unegcount; //done 
        	double totalUniCount2 = utotalcount; //done
        	double posBiCount2 = bposcount; //done
        	double negBiCount2 = bnegcount; //done
        	double totalBiCount2 = btotalcount; //done
        	
        	token1 = new StringTokenizer(postId2, "_");
        	token1.nextToken();
        	postNo = token1.nextToken();
        	
        	int repliesCount2 = cacheMap.get(postNo);
           
           /////////////////////////////////////////////////////////////////
           String isCondDiff = "1";
           if(condition1.equals(condition2))isCondDiff = "0";
           
           String isProblemPattern = "";
           if(isProblem1.equals("0") && isProblem2.equals("0"))isProblemPattern = "0";
           if(isProblem1.equals("0") && isProblem2.equals("1"))isProblemPattern = "1";
           if(isProblem1.equals("1") && isProblem2.equals("0"))isProblemPattern = "2";
           if(isProblem1.equals("1") && isProblem2.equals("1"))isProblemPattern = "3";
           ////////////////////////////////////////////////////////////////
           
           String uniCountPattern = "0";
       	   String posUniPattern = "0";
       	   String negUniPattern = "0";
       	   String biCountPattern = "0";
       	   String posBiPattern = "0";
       	   String negBiPattern = "0";
           
           if(posUniCount1 < posUniCount2)posUniPattern = "1";
           if(negUniCount1 < negUniCount2)negUniPattern = "1";
           if(posBiCount1 < posBiCount2)posBiPattern = "1";
           if(negBiCount1 < negBiCount2)negBiPattern = "1";
           if(totalUniCount1 < totalUniCount2)uniCountPattern = "1";
           if(totalBiCount1 < totalBiCount2)biCountPattern = "1";
           ///////////////////////////////////////////////////////////////
           int totalRepliesCount = repliesCount1 + repliesCount2;
           String RCPattern = "0";
           if(repliesCount1 > repliesCount2)RCPattern = "1";
           if(repliesCount1 < repliesCount2)RCPattern = "2";
           ///////////////////////////////////////////////////////////////
           int timeGap = (Integer.parseInt(year2)-Integer.parseInt(year1))*365 + (Integer.parseInt(month2)-Integer.parseInt(month1))*30 + (Integer.parseInt(day2)-Integer.parseInt(day1));
           
           tempMat.age = age;
           tempMat.gender = gender;
           tempMat.timeGap = timeGap;
           tempMat.condition1 = conditionMap.get(condition1);
           tempMat.condition2 = conditionMap.get(condition2);
           tempMat.isCondDiff = isCondDiff;
           tempMat.isProblem1 = isProblem1;
           tempMat.isProblem2 = isProblem2;
           tempMat.isProblemPattern = isProblemPattern;
           tempMat.repliesCount1 = repliesCount1;
           tempMat.repliesCount2 = repliesCount2;
           tempMat.totalRepliesCount = repliesCount1 + repliesCount2;
           tempMat.RCPattern = RCPattern;
           tempMat.hasLoc = hasLoc;
           tempMat.hasImage = hasImage;
           tempMat.completeness = completeness;
           tempMat.posUniCount1 = posUniCount1;
           tempMat.posUniCount2 = posUniCount2;
           tempMat.posBiCount1 = posBiCount1;
           tempMat.posBiCount2 = posBiCount2;
           tempMat.negUniCount1 = negUniCount1;
           tempMat.negUniCount2 = negUniCount2;
           tempMat.negBiCount1 = negBiCount1;
           tempMat.negBiCount2 = negBiCount2;
           tempMat.totalUniCount1 = totalUniCount1;
           tempMat.totalUniCount2 = totalUniCount2;
           tempMat.totalBiCount1 = totalBiCount1;
           tempMat.totalBiCount2 = totalBiCount2;
           tempMat.uniCountPattern = uniCountPattern;
           tempMat.biCountPattern = biCountPattern;
           tempMat.posUniPattern = posUniPattern;
           tempMat.negUniPattern = negUniPattern;
           tempMat.posBiPattern = posBiPattern;
           tempMat.negBiPattern = negBiPattern;
           tempMat.hasPostedNext = hasPostedNext;
           
           bw.write(tempMat.id+","+tempMat.age+","+tempMat.gender+","+tempMat.timeGap+","+tempMat.condition1+","+tempMat.condition2+","+tempMat.isCondDiff+","+tempMat.isProblem1+","+tempMat.isProblem2+","+tempMat.isProblemPattern+","+
        		   tempMat.repliesCount1+","+tempMat.repliesCount2+","+tempMat.totalRepliesCount+","+tempMat.RCPattern+","+tempMat.hasLoc+","+tempMat.hasImage+","+tempMat.completeness+","+tempMat.posUniCount1+","+tempMat.negUniCount1+","+tempMat.totalUniCount1+","+
        		   tempMat.posBiCount1+","+tempMat.negBiCount1+","+tempMat.totalBiCount1+","+tempMat.posUniCount2+","+tempMat.negUniCount2+","+tempMat.totalUniCount2+","+tempMat.posBiCount2+","+tempMat.negBiCount2+","+tempMat.totalBiCount2+","+
        		   tempMat.uniCountPattern+","+tempMat.biCountPattern+","+tempMat.posUniPattern+","+tempMat.negUniPattern+","+tempMat.posBiPattern+","+tempMat.negBiPattern+","+tempMat.hasPostedNext+"\n");
           
       }
       bw.close();
	}
	
	static String htmlParse(String id) throws FileNotFoundException, IOException
	{
		//System.out.println(id);
		String temp = "";
		String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	File f1 = new File(root+"/PeopleHTML/"+id+".html");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        String line;
        Integer hasGender = 0, hasAge = 0, hasLoc = 0, image_val = 0;
        int check = 0;
        while((line = reader1.readLine())!=null)
        {
        	if(line.contains("<div class=\"more-details\">") && check == 0)
        	{
        		StringTokenizer token1 = new StringTokenizer(line, "<>");
        		token1.nextToken();
        		token1.nextToken();
        		token1.nextToken();
        		token1.nextToken();
        		String info = token1.nextToken();
        		StringTokenizer token2 = new StringTokenizer(info, ",");
        		int countTokens = token2.countTokens();
        		for(int i=0; i<countTokens; i++)
        		{
        			String tok = token2.nextToken();
        			if(tok.contains("Male")||tok.contains("Female"))hasGender = 1;
        			else if(tok.matches(".*\\d.*"))hasAge = 1;       			
        		}
        		
        		if((hasGender==1 && hasAge==1 && countTokens>2) || (hasGender==0 && hasAge==1 && countTokens>1) || (hasGender==1 && hasAge==0 && countTokens>1))
        			hasLoc = 1;
        		check = 1;
        		continue;
        	}
        	if(line.contains("<div class=\"clear\"><div class=\"user_image_bg\">"))
        	{
        		reader1.readLine();
        		line = reader1.readLine();
        		if(line.contains("default"))image_val = 0;
        		else if(line.contains("templates"))image_val = 1;
        		else if(line.contains("userfiles"))image_val = 2;
        		check = 0;
        		break;
        	}
        }
        reader1.close();
        temp = hasAge.toString()+hasGender.toString()+hasLoc.toString()+image_val.toString();
		return temp;
	}
	

}

class FeatureMatrix2Post
{
	String id; //done
	String age; //done
	String gender; //done
	int timeGap; //done
	int condition1; //done
	int condition2; //done
	String isCondDiff; //done
	String isProblem1; //done
	String isProblem2; //done
	String isProblemPattern; //done
	int repliesCount1; //done
	int repliesCount2; //done
	int totalRepliesCount; //done
	String RCPattern; //done
	int hasLoc; //done
	int hasImage; //done
	int completeness; //done
	double posUniCount1; //done
	double negUniCount1; //done 
	double totalUniCount1; //done
	double posBiCount1; //done
	double negBiCount1; //done
	double totalBiCount1; //done
	double posUniCount2; //done
	double negUniCount2; //done
	double totalUniCount2; //done
	double posBiCount2; //done
	double negBiCount2; //done
	double totalBiCount2; //done
	String uniCountPattern; //done
	String posUniPattern; //done
	String negUniPattern; //done
	String biCountPattern; //done
	String posBiPattern; //done
	String negBiPattern; //done
	int hasPostedNext;
}




