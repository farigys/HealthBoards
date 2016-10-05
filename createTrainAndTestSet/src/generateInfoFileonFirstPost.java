import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class generateInfoFileonFirstPost {
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
        File f2 = new File(root+"/cacheFile.csv");
        FileInputStream fis2 = new FileInputStream(f2); 
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(fis2));
        HashMap<String, cache> cacheMap = new HashMap<String, cache>();
        
        String linec = "";
        while((linec = reader2.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(linec, ",");
        	String postId = token1.nextToken();
        	System.out.println(postId);
        	cache tempcache = new cache();
        	tempcache.person = token1.nextToken();
        	tempcache.date = token1.nextToken();
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
        
        File ftrain = new File(root+"/trainList.txt");
        FileInputStream fistrain = new FileInputStream(ftrain); 
        BufferedReader readertrain = new BufferedReader(new InputStreamReader(fistrain));
        //int count = 1;
        HashMap<Integer, poster> userProfileMap = new HashMap<Integer, poster>();
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
        	int tempAge = -1;
        	String tempGender = "Unknown";
        	String tempCondition = "";
//        	File writefile1 = new File(root+"test.txt");
//            FileWriter fw1 = new FileWriter(writefile1.getAbsoluteFile());
//        	BufferedWriter bw1 = new BufferedWriter(fw1);
        	int postCount = 0;
        	 for (int m = 0; m < listOfFolders.length; m++) 
             {
             	if (listOfFolders[m].isDirectory())
             	{
             		String foldername = listOfFolders[m].getName();
             		//String foldername = "Bone-Cancer";
             		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
             		
             		String rootDir = root+foldername;
             		//System.out.println("Now in: "+root+foldername);
             		File fuser = new File(rootDir+"/userPostList.txt");
                    FileInputStream fisuser = new FileInputStream(fuser); 
                    BufferedReader readeruser = new BufferedReader(new InputStreamReader(fisuser));
                    String line1;
                    int flag = 0;
                    while((line1 = readeruser.readLine())!=null)
                    {
                    	StringTokenizer token = new StringTokenizer(line1, ", ");
                    	int tempUserID = Integer.parseInt(token.nextToken());
                    	//bw1.write(userID+" "+tempUserID+"\n");
                    	if(userID.equals(tempUserID))
                    	{
                    		//System.out.println("Dhuksi");
                    		//System.out.println("Now in: "+foldername);
                    		flag = 1;
                    		String tempPost = token.nextToken();
                    		//System.out.println(tempPost+","+tempCondition);
                    		//latestPost = tempPost;
                    		String currdate = token.nextToken();
                    		StringTokenizer tokenize = new StringTokenizer(currdate, "/");
                    		String month = tokenize.nextToken();
                    		String day = tokenize.nextToken();
                    		String year = tokenize.nextToken();
//                    		if(year.equals("13"))
//                    		{
//                    			//hasPostedIn13 = 1;
//                    			continue;
//                    		}
                    		String date = year+month;
                    		//System.out.println(date+" "+earliestDate);
                    		if(date.compareTo(earliestDate)<=0)
                    		{
                    			earliestDate = date;
                    			latestPost = tempPost;
                    			tempCondition = foldername;
                    		}

                    		postCount++;
                    		continue;
                    	}
                    	if(flag == 1)break;
                    	//System.out.println(userID);
                    }
                    
                    
                    readeruser.close();
                    
             		
             	}
             }
        	 
        	 //System.out.println(latestPost+","+tempCondition);
        	 if(tempCondition.equals(""))
        	 {
        		 //System.out.println("Dhuksi");
            	 String startyear = Character.toString(earliestDate.charAt(0))+Character.toString(earliestDate.charAt(1));
            	 String startmonth = Character.toString(earliestDate.charAt(2))+Character.toString(earliestDate.charAt(3));
            	 //System.out.println(earliestDate+" "+latestDate);
            	 String endyear = Character.toString(latestDate.charAt(0))+Character.toString(latestDate.charAt(1));
            	 String endmonth = Character.toString(latestDate.charAt(2))+Character.toString(latestDate.charAt(3));
            	 
            	 timeDiff = (14-Integer.parseInt(endyear))*12 + (8-Integer.parseInt(endmonth));
            	 
            	//System.out.println(userID.toString());
            	if(ageMap.get(userID.toString())!=null)
            	{
            		if(ageMap.get(userID.toString()).equals("0"))tempAge = 33;
            		else tempAge = Integer.parseInt(ageMap.get(userID.toString()));
            		//if(genderMap.get(userID.toString()).equals("XX"))tempGender = "Unknown";
            		tempGender = genderMap.get(userID.toString());
            	}
            	
                //tempGender = genderMap.get(userID);
            	 
            	 
            	 
            	 poster tempUser = new poster();
            	 
            	 tempUser.userID = userID;
            	 tempUser.earliestDate = earliestDate;
            	 tempUser.latestPostID = latestPost;
            	 tempUser.timediff = timeDiff;
            	 tempUser.noOfReplies = noOfReplies;
            	 tempUser.hasPostedIn13 = hasPostednext;
            	 tempUser.age = tempAge;
            	 tempUser.gender = tempGender;
            	 
            	 userProfileMap.put(userID, tempUser);
            	 continue;
        	 }
        	 
        	 
        	 if(postCount>1)hasPostednext = 1;
        	 else hasPostednext = 0;
        	 latestDate = earliestDate;
        	 //System.out.println(latestDate);
             File f = new File(root+tempCondition+"/XML/"+latestPost+".html.xml");
             FileInputStream fis = new FileInputStream(f); 
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
             String templine;
             while((templine=reader.readLine())!=null)
             {
             	if(templine.contains("<replies repliesCount="))break;
             }
             //System.out.println(templine);
             StringTokenizer tok = new StringTokenizer(templine, "\"");
             tok.nextToken();
             int replycount = Integer.parseInt(tok.nextToken());
             noOfReplies = replycount;
             reader.close();
        	 
             
             
        	 String startyear = Character.toString(earliestDate.charAt(0))+Character.toString(earliestDate.charAt(1));
        	 String startmonth = Character.toString(earliestDate.charAt(2))+Character.toString(earliestDate.charAt(3));
        	 //System.out.println(earliestDate+" "+latestDate);
        	 String endyear = Character.toString(latestDate.charAt(0))+Character.toString(latestDate.charAt(1));
        	 String endmonth = Character.toString(latestDate.charAt(2))+Character.toString(latestDate.charAt(3));
        	 
        	 timeDiff = (12-Integer.parseInt(endyear))*12 + (12-Integer.parseInt(endmonth));
        	 
        	//System.out.println(userID.toString());
        	if(ageMap.get(userID.toString())!=null)
        	{
        		if(ageMap.get(userID.toString()).equals("0"))tempAge = 33;
        		else tempAge = Integer.parseInt(ageMap.get(userID.toString()));
        		//if(genderMap.get(userID.toString()).equals("XX"))tempGender = "Unknown";
        		tempGender = genderMap.get(userID.toString());
        	}
        	
            //tempGender = genderMap.get(userID);
        	 
        	 
        	 
        	 poster tempUser = new poster();
        	 
        	 tempUser.userID = userID;
        	 tempUser.earliestDate = earliestDate;
        	 tempUser.latestPostID = latestPost;
        	 tempUser.timediff = timeDiff;
        	 tempUser.noOfReplies = noOfReplies;
        	 tempUser.hasPostedIn13 = hasPostednext;
        	 tempUser.age = tempAge;
        	 tempUser.gender = tempGender;
        	 
        	 userProfileMap.put(userID, tempUser);
        	 //bw1.close();
        }
        readertrain.close();
        
        File writefile = new File(root+"userProfile1.csv");
        FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	 Iterator iter = userProfileMap.entrySet().iterator();
         while(iter.hasNext())
         {
         	Map.Entry pairs = (Map.Entry)iter.next();
         	String currPoster = pairs.getKey().toString();
         	poster temp = (poster)pairs.getValue();
         	
         	bw.write(pairs.getKey()+","+temp.timediff+","+temp.age+","+temp.gender+","+temp.noOfReplies+","+temp.hasPostedIn13+"\n");
         }
    	
    	bw.close();
    	
        
	}

}

class poster
{
	int userID;
	String earliestDate;
	String latestPostID;
	int timediff;
	int noOfReplies;
	int hasPostedIn13;
	int age;
	String gender;
	String earliestPost;
}

class cache
{
	String person;
	String date;
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


