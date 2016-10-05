
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


public class generateInfoFileRestricted3Post {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};

    	File f1 = new File(root+"/TrainList3Post.txt");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        String line;
        
        HashSet<String> trainUserSet = new HashSet<String>();
        HashSet<String> testUserSet = new HashSet<String>();
        
        while((line=reader1.readLine())!=null)
        {
        	String id = line;
        	trainUserSet.add(id);
        }
        
        reader1.close();
        
        f1 = new File(root+"/TestList3Post.txt");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        while((line=reader1.readLine())!=null)
        {
        	String id = line;
        	testUserSet.add(id);
        }
        
        HashMap<String, userInfo> trainMap = new HashMap<String, userInfo>();
        HashMap<String, userInfo> testMap = new HashMap<String, userInfo>();
        
        HashMap<String, userInfoRes> trainMapOriginal = new HashMap<String, userInfoRes>();
        HashMap<String, userInfoRes> testMapOriginal = new HashMap<String, userInfoRes>();
        
        
        
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

class FeatureMatrix3Post
{
	String id;
	String age;
	String gender;
	String duration;
	String timeGap;
	String condition1;
	String condition2;
	String isCondDiff;
	String repliesCount1;
	String repliesCount2;
	String totalRepliesCount;
	String RCPattern;
	String isProblem1;
	String isProblem2;
	String isProblemPattern;
	String hasLocation;
	String hasImage;
	String completeness;
	String posUniCount1;
	String negUniCount1;
	String totalUniCount1;
	String posBiCount1;
	String negBiCount1;
	String TotalBiCount1;
	String posUniCount2;
	String negUniCount2;
	String totalUniCount2;
	String posBiCount2;
	String negBiCount2;
	String TotalBiCount2;
	String uniCountPattern;
	String posUniPattern;
	String BiPattern;
}




