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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class generateInfoFileonFirstPostCorrected {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
        TreeSet<Integer> userset = new TreeSet<Integer>();
    	File f1 = new File(root+"/id_age_gender.csv");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        HashMap<String, String> ageMap = new HashMap<String, String>();
        HashMap<String, String> genderMap = new HashMap<String, String>();
        
        String line;
        
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
        //System.out.println(ageMap.size()+" "+genderMap.size());
        
        reader1.close();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        File f2 = new File(root+"/cacheFileFirstPost.csv");
        FileInputStream fis2 = new FileInputStream(f2); 
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(fis2));
        HashMap<String, posterInfo> cacheMap = new HashMap<String, posterInfo>();
        
        String linec = "";
        while((linec = reader2.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(linec, ",");
        	String userId = token1.nextToken();
        	//System.out.println(userId);
        	String postId = token1.nextToken();
        	//System.out.println(postId);
        	posterInfo tempcache = new posterInfo();
        	tempcache.posterId = userId;
        	tempcache.post = postId;
        	String tempdate = token1.nextToken();
        	if(tempdate.length()<10)tempdate="0"+tempdate;
        	tempcache.date = tempdate;
        	String condition = token1.nextToken();
        	tempcache.condition = condition;
        	tempcache.replycount = Integer.parseInt(token1.nextToken());
        	tempcache.uposcount = Double.parseDouble(token1.nextToken());
        	tempcache.unegcount = Double.parseDouble(token1.nextToken());
        	tempcache.utotalcount = Double.parseDouble(token1.nextToken());
        	tempcache.bposcount = Double.parseDouble(token1.nextToken());
        	tempcache.bnegcount = Double.parseDouble(token1.nextToken());
        	tempcache.btotalcount = Double.parseDouble(token1.nextToken());
        	tempcache.qcount = Integer.parseInt(token1.nextToken());
        	tempcache.urlcount = Integer.parseInt(token1.nextToken());
        	cacheMap.put(userId, tempcache);
        }
        System.out.println(cacheMap.size());
        File ftrain = new File(root+"/testList.csv");
        FileInputStream fistrain = new FileInputStream(ftrain); 
        BufferedReader readertrain = new BufferedReader(new InputStreamReader(fistrain));
        //int count = 1;
        HashMap<Integer, userInfo> userProfileMap = new HashMap<Integer, userInfo>();
        while((line=readertrain.readLine())!=null)
        {
        	Integer userID = Integer.parseInt(line);
        	System.out.println(userID);
        	String latestDate = "0000";
        	String earliestDate = "9999";
        	String latestPost = "";
        	int duration;
        	int totalPostCount = 0;
        	double postPerMonth;
        	int timeDiff;
        	int noOfReplies = 0;
        	int lastNoOfReplies =0;
        	double avgNoOfReplies;
        	int hasPostednext = 0;
        	int tempAge = 0;
        	String tempGender = "0";
        	String tempCondition = "";
//        	File writefile1 = new File(root+"test.txt");
//            FileWriter fw1 = new FileWriter(writefile1.getAbsoluteFile());
//        	BufferedWriter bw1 = new BufferedWriter(fw1);
        	int postCount = 0;
        	
        	posterInfo tempPosterInfo = cacheMap.get(userID.toString());
        	//System.out.println(tempPosterInfo);
        	
        	String postDate = tempPosterInfo.date;
        	
        	String condition = tempPosterInfo.condition;
        	
        	if(postDate.length()<10)postDate="0"+postDate;
        	
        	String postyear = Character.toString(postDate.charAt(0))+Character.toString(postDate.charAt(1));
        	String postmonth = Character.toString(postDate.charAt(2))+Character.toString(postDate.charAt(3));
        	
        	timeDiff =  (14-Integer.parseInt(postyear))*12 + (9-Integer.parseInt(postmonth));
        	if(timeDiff>=0 && timeDiff<6)timeDiff = 0;
        	else if(timeDiff>=6 && timeDiff<12)timeDiff = 1;
        	else if(timeDiff>=12 && timeDiff<18)timeDiff = 2;
        	else if(timeDiff>=18 && timeDiff<24)timeDiff = 3;
        	else if(timeDiff>=24 && timeDiff<30)timeDiff = 4;
        	else if(timeDiff>=30 && timeDiff<36)timeDiff = 5;
        	else if(timeDiff>=36 && timeDiff<42)timeDiff = 6;
        	else if(timeDiff>=42 && timeDiff<48)timeDiff = 7;
        	else if(timeDiff>=48 && timeDiff<54)timeDiff = 8;
        	else if(timeDiff>=54 && timeDiff<60)timeDiff = 9;
        	else if(timeDiff>=60)timeDiff = 10;
        	
        	int cond = 0;
        	
        	if(condition.equals("Acne"))cond=1;
        	else if(condition.equals("ADHD"))cond=2;
        	else if(condition.equals("Alcoholism"))cond=3;
        	else if(condition.equals("Asthma"))cond=4;
        	else if(condition.equals("Back-Pain"))cond=5;
        	else if(condition.equals("Bone-Cancer"))cond=6;
        	else if(condition.equals("Bipolar-Disorder"))cond=7;
        	else if(condition.equals("COPD"))cond=8;
        	else if(condition.equals("Diets-Weight-Maintenance"))cond=9;
        	else if(condition.equals("Fibromyalgia"))cond=10;
        	else if(condition.equals("Gastric-Bypass-Surgery"))cond=11;
        	else if(condition.equals("Immigration-Law"))cond=12;
        	else if(condition.equals("Infertility"))cond=13;
        	else if(condition.equals("Loneliness"))cond=14;
        	else if(condition.equals("Lung-Cancer"))cond=15;
        	else if(condition.equals("Migraine"))cond=16;
        	else if(condition.equals("Miscarriage"))cond=17;
        	else if(condition.equals("Pregnancy"))cond=18;
        	else if(condition.equals("Rheumatoid-Arthritis"))cond=19;
        	else if(condition.equals("War-In-Iraq"))cond=20;
        	
            	 
            	//System.out.println(userID.toString());
            if(ageMap.get(userID.toString())!=null)
            {
            	int age = Integer.parseInt(ageMap.get(userID.toString()));
            	if(age>10 && age<=20)age = 1;
            	else if(age>20 && age<=30)age = 2;
            	else if(age>30 && age<=40)age = 3;
            	else if(age>40 && age<=50)age = 4;
            	else if(age>50 && age<=60)age = 5;
            	else if(age>60 && age<=70)age = 6;
            	else if(age>70)age = 7;
            	else age = 0;
            	tempAge = age;
            	//if(genderMap.get(userID.toString()).equals("XX"))tempGender = "Unknown";
            	tempGender = genderMap.get(userID.toString());
            }
            
            noOfReplies = tempPosterInfo.replycount;
            
            int isProblem;
            String postName = tempPosterInfo.post;
            if(postName.contains("Problem"))isProblem=1;
            else isProblem = 0;
            double posUnigram = tempPosterInfo.uposcount;
            double negUnigram = tempPosterInfo.unegcount;
            double posBigram = tempPosterInfo.bposcount;
            double negBigram = tempPosterInfo.bnegcount;
            double totalUnigram = tempPosterInfo.utotalcount;
            double totalBigram = tempPosterInfo.btotalcount;
            
            LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(root+"/userPostIndex/"+userID+".txt")));
            lnr.skip(Long.MAX_VALUE);
            if(lnr.getLineNumber()>1)hasPostednext = 1;
            //System.out.println(lnr.getLineNumber());
            lnr.close();


        	 
        	 
        	 
        	 userInfo tempUser = new userInfo();
        	 
        	 tempUser.userID = userID;
        	 tempUser.timeDiff = timeDiff;
        	 tempUser.age = tempAge;
        	 tempUser.gender = tempGender;
        	 tempUser.condition = cond;
        	 tempUser.repliesCount = noOfReplies;
        	 tempUser.isProblem = isProblem;
        	 tempUser.posunicount = posUnigram;
        	 tempUser.negunicount = negUnigram;
        	 tempUser.posbicount = posBigram;
        	 tempUser.negbicount = negBigram;
        	 tempUser.totalunicount = totalUnigram;
        	 tempUser.totalbicount = totalBigram;
        	 tempUser.qCount = tempPosterInfo.qcount;
        	 tempUser.urlCount = tempPosterInfo.urlcount;
        	 tempUser.hasPostedNext = hasPostednext;
        	 
        	 
        	 userProfileMap.put(userID, tempUser);
        	 //bw1.close();
        }
        readertrain.close();
        
        File writefile = new File(root+"/userProfile1Test.csv");
        FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	 Iterator iter = userProfileMap.entrySet().iterator();
         while(iter.hasNext())
         {
         	Map.Entry pairs = (Map.Entry)iter.next();
         	String currPoster = pairs.getKey().toString();
         	userInfo temp = (userInfo)pairs.getValue();
         	
         	bw.write(pairs.getKey()+","+temp.timeDiff+","+temp.age+","+temp.gender+","+temp.condition+","+temp.repliesCount+","+temp.isProblem+","+four.format(temp.posunicount)+","+four.format(temp.negunicount)+","+four.format(temp.totalunicount)+","+four.format(temp.posbicount)+","+four.format(temp.negbicount)+","+temp.qCount+","+temp.urlCount+","+temp.hasPostedNext+"\n");
         }
    	
    	bw.close();
    	
        
	}

}

class userInfo
{
	int userID;
	int timeDiff;
	int age;
	String gender;
	int condition;
	int repliesCount;
	int isProblem;
	double posunicount;
	double negunicount;
	double posbicount;
	double negbicount;
	double totalunicount;
	double totalbicount;
	int qCount;
	int urlCount;
	int hasPostedNext;
}


class posterInfo
{
	String posterId;
	String post;
	String date;
	String condition;
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


