
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


public class generateInfoFileRestricted {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};

    	File f1 = new File(root+"/id_age_gender.csv");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        String line;
        
        HashSet<String> trainUserSet = new HashSet<String>();
        HashSet<String> testUserSet = new HashSet<String>();
        
        for(int i=0; i<15000; i++)
        {
        	line = reader1.readLine();
        	StringTokenizer token1 = new StringTokenizer(line, ";");
        	String id = token1.nextToken();
        	//if(id.equals("72616"))System.out.print(01);
        	trainUserSet.add(id);
        }
        
        while((line=reader1.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(line, ";");
        	String id = token1.nextToken();
        	//if(id.equals("72616"))System.out.print(11);
        	testUserSet.add(id);
        }
        
        reader1.close();
        
        HashMap<String, userInfo> trainMap = new HashMap<String, userInfo>();
        HashMap<String, userInfo> testMap = new HashMap<String, userInfo>();
        
        HashMap<String, userInfoRes> trainMapOriginal = new HashMap<String, userInfoRes>();
        HashMap<String, userInfoRes> testMapOriginal = new HashMap<String, userInfoRes>();
        
        f1 = new File(root+"/userProfile1.csv");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        while((line=reader1.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(line, ",");
        	String id = token1.nextToken();
        	userInfo tempUser = new userInfo();
        	tempUser.userID = Integer.parseInt(id);
        	tempUser.timeDiff = Integer.parseInt(token1.nextToken());
        	tempUser.age = Integer.parseInt(token1.nextToken());;
        	tempUser.gender = token1.nextToken();
        	tempUser.condition = Integer.parseInt(token1.nextToken());
        	tempUser.repliesCount = Integer.parseInt(token1.nextToken());
        	tempUser.isProblem = Integer.parseInt(token1.nextToken());
        	tempUser.posunicount = Double.parseDouble(token1.nextToken());
        	tempUser.negunicount = Double.parseDouble(token1.nextToken());
        	tempUser.totalunicount = Double.parseDouble(token1.nextToken());
        	tempUser.posbicount = Double.parseDouble(token1.nextToken());
        	tempUser.negbicount = Double.parseDouble(token1.nextToken());
        	tempUser.qCount = Integer.parseInt(token1.nextToken());
        	tempUser.urlCount = Integer.parseInt(token1.nextToken());
        	tempUser.hasPostedNext = Integer.parseInt(token1.nextToken());
        	trainMap.put(id, tempUser);
        	
        }
        
        reader1.close();

        f1 = new File(root+"/userProfile1Test.csv");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        while((line=reader1.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(line, ",");
        	String id = token1.nextToken();
        	userInfo tempUser = new userInfo();
        	tempUser.userID = Integer.parseInt(id);
        	tempUser.timeDiff = Integer.parseInt(token1.nextToken());
        	tempUser.age = Integer.parseInt(token1.nextToken());;
        	tempUser.gender = token1.nextToken();
        	tempUser.condition = Integer.parseInt(token1.nextToken());
        	tempUser.repliesCount = Integer.parseInt(token1.nextToken());
        	tempUser.isProblem = Integer.parseInt(token1.nextToken());
        	tempUser.posunicount = Double.parseDouble(token1.nextToken());
        	tempUser.negunicount = Double.parseDouble(token1.nextToken());
        	tempUser.totalunicount = Double.parseDouble(token1.nextToken());
        	tempUser.posbicount = Double.parseDouble(token1.nextToken());
        	tempUser.negbicount = Double.parseDouble(token1.nextToken());
        	tempUser.qCount = Integer.parseInt(token1.nextToken());
        	tempUser.urlCount = Integer.parseInt(token1.nextToken());
        	tempUser.hasPostedNext = Integer.parseInt(token1.nextToken());
        	testMap.put(id, tempUser);
        	
        }
        reader1.close();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        
        ArrayList<String> trainUser = new ArrayList(trainUserSet);
        ArrayList<String> testUser = new ArrayList(testUserSet);
        //if(testUser.contains("72616"))System.out.println("1");
        
        for(int i=0; i<trainUser.size(); i++)
        {
        	String id = trainUser.get(i);
        	userInfo tempUser = new userInfo();
        	if(trainMap.containsKey(id))tempUser = trainMap.get(id);
        	else if(testMap.containsKey(id))tempUser = testMap.get(id);
        	userInfoRes tempUserRes = new userInfoRes();
        	tempUserRes.user = tempUser;
        	String vals = htmlCrawl(id);
        	int hasAge = Character.getNumericValue(vals.charAt(0));
        	int hasGender = Character.getNumericValue(vals.charAt(1));
        	int hasLoc = Character.getNumericValue(vals.charAt(2));
        	int hasImage = Character.getNumericValue(vals.charAt(3));
        	
        	int completeness = hasAge + hasGender + hasLoc + hasImage;
        	
        	tempUserRes.hasLocation = hasLoc;
        	tempUserRes.hasImage = hasImage;
        	tempUserRes.completeness = completeness;
        	
        	trainMapOriginal.put(id, tempUserRes);
        }
        
        for(int i=0; i<testUser.size(); i++)
        {
        	String id = testUser.get(i);
        	userInfo tempUser = new userInfo();
        	if(trainMap.containsKey(id))tempUser = trainMap.get(id);
        	else if(testMap.containsKey(id))tempUser = testMap.get(id);
        	userInfoRes tempUserRes = new userInfoRes();
        	tempUserRes.user = tempUser;
        	String vals = htmlCrawl(id);
        	int hasAge = Character.getNumericValue(vals.charAt(0));
        	int hasGender = Character.getNumericValue(vals.charAt(1));
        	int hasLoc = Character.getNumericValue(vals.charAt(2));
        	int hasImage = Character.getNumericValue(vals.charAt(3));
        	
        	int completeness = hasAge + hasGender + hasLoc + hasImage;
        	
        	tempUserRes.hasLocation = hasLoc;
        	tempUserRes.hasImage = hasImage;
        	tempUserRes.completeness = completeness;
        	
        	testMapOriginal.put(id, tempUserRes);
        }
        
        File writefile = new File(root+"/userProfile1Restricted1.csv");
        FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	 Iterator iter = trainMapOriginal.entrySet().iterator();
         while(iter.hasNext())
         {
         	Map.Entry pairs = (Map.Entry)iter.next();
         	String currPoster = pairs.getKey().toString();
         	userInfoRes temp = (userInfoRes)pairs.getValue();
         	
         	bw.write(pairs.getKey()+","+temp.user.timeDiff+","+temp.user.age+","+temp.user.gender+","+temp.user.condition+","+temp.user.repliesCount+","+temp.user.isProblem+","+temp.hasLocation+","+temp.hasImage+","+temp.completeness+","+four.format(temp.user.posunicount)+","+four.format(temp.user.negunicount)+","+four.format(temp.user.totalunicount)+","+four.format(temp.user.posbicount)+","+four.format(temp.user.negbicount)+","+temp.user.qCount+","+temp.user.urlCount+","+temp.user.hasPostedNext+"\n");
         }
         bw.close();
        
         writefile = new File(root+"/userProfile1TestRestricted1.csv");
         fw = new FileWriter(writefile.getAbsoluteFile());
     	 bw = new BufferedWriter(fw);
     	
     	 iter = testMapOriginal.entrySet().iterator();
         while(iter.hasNext())
         {
         	Map.Entry pairs = (Map.Entry)iter.next();
         	String currPoster = pairs.getKey().toString();
         	userInfoRes temp = (userInfoRes)pairs.getValue();
         	
         	bw.write(pairs.getKey()+","+temp.user.timeDiff+","+temp.user.age+","+temp.user.gender+","+temp.user.condition+","+temp.user.repliesCount+","+temp.user.isProblem+","+temp.hasLocation+","+temp.hasImage+","+temp.completeness+","+four.format(temp.user.posunicount)+","+four.format(temp.user.negunicount)+","+four.format(temp.user.totalunicount)+","+four.format(temp.user.posbicount)+","+four.format(temp.user.negbicount)+","+temp.user.qCount+","+temp.user.urlCount+","+temp.user.hasPostedNext+"\n");
         }
         bw.close();
        
        
	}
	
	static String htmlCrawl(String id) throws FileNotFoundException, IOException
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

class userInfoRes
{
	userInfo user;
	int hasLocation;
	int hasImage;
	int completeness;
}




