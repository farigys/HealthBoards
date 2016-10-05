import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class timelineAnalysisUserList {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		
		int ObsPeriod = 6;
		
		String[] conditions = {"depression", "relationship-health", "alzheimers-disease-dementia"};
		//String[] conditions = {"relationship-health"};
		//String[] conditions = {"alzheimers-disease-dementia"};
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir1 = root+"/Caches";
			
			File file0 = new File(rootDir1 + "/basicFeaturesWithClassForTimeLine.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file0.exists()) {
				file0.createNewFile();
			}

			FileWriter fw = new FileWriter(file0.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
//			File file1 = new File(rootDir1 + "/releventPosts.txt");//if not filtered by frequency, use the file without .filtered part
//			// if file doesnt exists, then create it
//			if (!file1.exists()) {
//				file1.createNewFile();
//			}
//
//			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
//			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			File  f1 = new File(root+"/userPostInfo.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    String line = "";
		    
		    
		    
		    HashMap<String, TreeMap<Long, ArrayList<String>>> userPostInfoMap = new HashMap<String, TreeMap<Long, ArrayList<String>>>();
		    HashMap<String, String> postType = new HashMap<String, String>();
		    
		    ArrayList<String> listOfUsers = new ArrayList<String>();
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String parts[] = line.split(";");
		    	String userName = parts[0].trim();
		    	String threadId = parts[1].trim();
		    	String itemId = parts[2].trim();
		    	String postId = threadId + "/" + itemId;
		    	Long postTime = Long.parseLong(parts[3].trim());
		    	String postCat = parts[4].trim();
		    	
		    	postType.put(postId, postCat);
		    	
		    	
		    	
		    	if(userPostInfoMap.containsKey(userName))
		    	{
		    		TreeMap<Long, ArrayList<String>> tempTree = userPostInfoMap.get(userName);
		    		if(tempTree.containsKey(postTime))
		    		{
		    			//System.out.println(userName + ":" + tempTree.get(postTime) + ":" + postTime);
		    			//System.out.println(userName + ":" + postId + ":" + postTime);
		    			ArrayList<String> tempPostList = tempTree.get(postTime);
		    			tempPostList.add(postId);
		    			tempTree.put(postTime, tempPostList);
		    		}
		    		else
		    		{
		    			ArrayList<String> tempPostList = new ArrayList<String>();
		    			tempPostList.add(postId);
		    			tempTree.put(postTime, tempPostList);
		    		}
		    		userPostInfoMap.put(userName, tempTree);
		    		
		    	}
		    	else
		    	{
		    		TreeMap<Long, ArrayList<String>> tempTree = new TreeMap<Long, ArrayList<String>>();
		    		if(tempTree.containsKey(postTime))
		    		{
		    			//System.out.println(userName + ":" + tempTree.get(postTime) + ":" + postTime);
		    			//System.out.println(userName + ":" + postId + ":" + postTime);
		    			ArrayList<String> tempPostList = tempTree.get(postTime);
		    			tempPostList.add(postId);
		    			tempTree.put(postTime, tempPostList);
		    		}
		    		else
		    		{
		    			ArrayList<String> tempPostList = new ArrayList<String>();
		    			tempPostList.add(postId);
		    			tempTree.put(postTime, tempPostList);
		    		}
		    		userPostInfoMap.put(userName, tempTree);
		    	}
		    	
		    }
			
			File  f = new File(root+"/userInfo.txt");
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    line = "";
		    reader.readLine();
		    
		    ArrayList<Integer> postCount = new ArrayList<Integer>(); 
		    
		    while((line = reader.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	postCount.add(Integer.parseInt(parts[6].trim()));
		    }
		    
		    Collections.sort(postCount);
		    
		    double median = 0;
		    
		    if(postCount.size()%2 != 0)median = postCount.get((postCount.size() - 1)/2);
		    else median = (postCount.get(postCount.size()/2) + postCount.get((postCount.size()/2) -1))/2;
		    
		    System.out.println(median);
		    
		    f = new File(root+"/userInfo.txt");
		    fis = new FileInputStream(f); 
		    reader = new BufferedReader(new InputStreamReader(fis));
		    
		    line = "";
		    //int userCount = 0;
		    reader.readLine();
		    
		    while((line = reader.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	int postc = Integer.parseInt(parts[6].trim());
		    	//if(postc >= (int)median)//use median
		    	if(postc>=10)	
		    	{
		    		String userId = parts[0].trim();
		    		listOfUsers.add(userId);
		    		String location = parts[1].trim();
		    		int hasLocation = 0;
		    		if(!location.equals(""))hasLocation = 1;
		    		String startTime = parts[2].trim();
		    		//System.out.println(startTime);
		    		if(startTime.equals(""))continue;
		    		String[] timeParts = startTime.split(" ");
		    		String month = timeParts[0].trim();
		    		String year = timeParts[1].trim();
		    		
		    		if(month.equals("Jan"))month = "01";
		    		else if(month.equals("Feb"))month = "02";
		    		else if(month.equals("Mar"))month = "03";
		    		else if(month.equals("Apr"))month = "04";
		    		else if(month.equals("May"))month = "05";
		    		else if(month.equals("Jun"))month = "06";
		    		else if(month.equals("Jul"))month = "07";
		    		else if(month.equals("Aug"))month = "08";
		    		else if(month.equals("Sep"))month = "09";
		    		else if(month.equals("Oct"))month = "10";
		    		else if(month.equals("Nov"))month = "11";
		    		else if(month.equals("Dec"))month = "12";
		    		
		    		//if(Integer.parseInt(year)>2014)continue;
		    		
		    		startTime = year + month + "010000";
		    		
		    		String image = parts[4].trim();
		    		int hasImage = 0;
		    		if(!image.equals(""))hasImage = 1;
		    		
		    		String genderS = parts[5].trim();
		    		int gender = 0;
		    		if(!genderS.equals(""))
		    		{
		    			if(genderS.equals("female"))gender = 1;
		    			else if(genderS.equals("male"))gender = 2;
		    		}
		    		
		    		//////////////////////calculating first slack time/////////////////////////////
		    		String earliestPostTime = parts[10].trim();
		    		
		    		String eYear = earliestPostTime.trim().substring(0, 4);
		        	String eMonth = earliestPostTime.trim().substring(4, 6);
		        	String eDay = earliestPostTime.trim().substring(6, 8);
		        	String eHour = earliestPostTime.trim().substring(8, 10);
		        	String eMinute = earliestPostTime.trim().substring(10, 12);
		        	
		    	
		    		//Long intiIdleTime = calculateTimeGap(startTime, earliestPostTime);
		    		
		    		
		    		
//		    		String cutOffMonth = Integer.toString(Integer.parseInt(eMonth) + ObsPeriod);
//		    		
//		    		
//		    		
//		    		String cutOffYear = eYear;
//		    		
//		    		if(Integer.parseInt(cutOffMonth)>12)
//		    		{
//		    			Integer tempMonth = Integer.parseInt(cutOffMonth) - 12;
//		    			cutOffMonth = tempMonth.toString();
//		    			//if(cutOffMonth.length() == 1) cutOffMonth = "0" + cutOffMonth;
//		    			cutOffYear = Integer.toString(Integer.parseInt(cutOffYear) + 1);
//		    		}
//		    		
//		    		if(cutOffMonth.length() == 1) cutOffMonth = "0" + cutOffMonth;
		    		
		    		String cutOffTime = "201505010000";
		    		
		    		if(cutOffTime.length()<12)System.out.println(cutOffTime+";"+earliestPostTime);
		    		
		    		TreeMap<Long, ArrayList<String>> indUserPostmap = userPostInfoMap.get(userId);
		    		
		    		ArrayList<Long> timeGapList = new ArrayList<Long>();
		    		
		    		ArrayList<String> releventPosts = new ArrayList<String>();
		    		
		    		int willReturn = 0;
		    		
		    		Iterator it = indUserPostmap.entrySet().iterator();
		    		
		    		Long prevTime = Long.parseLong(startTime);
		    		
		    		while(it.hasNext())
		    		{
		    			Map.Entry pair = (Map.Entry)it.next();
		    			Long currTime = Long.parseLong(pair.getKey().toString());
		    			if(currTime>Long.parseLong(cutOffTime))
		    			{
		    				Long finalIdleTimeTemp = calculateTimeGap(Long.toString(prevTime), cutOffTime);
		    				timeGapList.add(finalIdleTimeTemp);
		    				willReturn = 1;
		    				prevTime = currTime;
		    				break;
		    			}
		    			timeGapList.add(calculateTimeGap(Long.toString(prevTime), Long.toString(currTime)));
		    			ArrayList<String> temp = (ArrayList<String>)pair.getValue();
		    			
		    			releventPosts.addAll(temp);
		    			
		    			if(temp.size()>1)
		    			{
		    				for(int i=1; i<temp.size(); i++)
		    				{
		    					timeGapList.add((long) 0);
		    				}
		    			}
		    			prevTime = currTime;
		    		}
		    		
		    		if(timeGapList.size()<3)continue;
		    		
		    		Long initialIdleTime = timeGapList.get(0);
		    		
		    		if(willReturn == 0)
		    		{
		    			timeGapList.add(calculateTimeGap(Long.toString(prevTime), cutOffTime));
		    		}
		    		
		    		Long finalIdleTime = timeGapList.get(timeGapList.size() - 1);
		    		
		    		timeGapList.remove(0);
		    		timeGapList.remove(timeGapList.size()-1);
		    		
		    		ArrayList<Long> timeGapListSorted = new ArrayList<Long>(timeGapList);
		    		
		    		Collections.sort(timeGapListSorted);
		    		
		    		Long medianGap;
		    		
		    		if(timeGapListSorted.size()%2 != 0)medianGap = timeGapListSorted.get((timeGapListSorted.size() - 1)/2);
				    else medianGap = (timeGapListSorted.get(timeGapListSorted.size()/2) + timeGapListSorted.get((timeGapListSorted.size()/2) -1))/2;
				    
		    		Long maxGap = timeGapListSorted.get(timeGapListSorted.size() - 1);
		    		
		    		int indPostCount = 0, indReplyCount = 0, replyReceiverd = 0, selfReplyCount = 0, otherReplyCount = 0;
		    		
		    		//bw1.write(userId);
		    		
		    		for(int i=0; i<releventPosts.size(); i++)
		    		{
		    			String currPostId = releventPosts.get(i);
		    			//bw1.write(";" + currPostId);
		    			String currPostType = postType.get(currPostId).trim();
		    			if(currPostType.equals("init"))
		    			{
		    				indPostCount++;
		    				String rootDir = root + "/JSON/";
		    				String[] nameParts = currPostId.split("/");
		    				String postName = nameParts[1];
		    				Object obj = parser.parse(new FileReader(rootDir + postName + ".json"));
		    				JSONObject jsonObject = (JSONObject) obj;
		    				
		    				int totalItems = Integer.parseInt(jsonObject.get("totalItems").toString());
		    				replyReceiverd += (totalItems-1);
		    				
		    			}
		    			else
		    			{
		    				indReplyCount++;
		    			}
		    		}
		    		
		    		//bw1.write("\n");
		    		
		    		bw.write(userId+";" + gender+";" +hasLocation+";" +hasImage+";" +
		    				initialIdleTime+";" + finalIdleTime+";" + maxGap+";" +
		    				medianGap + ";" + indPostCount + ";" + indReplyCount + ";" + 
		    				replyReceiverd + ";" +willReturn + "\n");
		    		
		    	}
		    	
		    	
		    }
		    bw.close();
		    //bw1.close();
		    
//		    file0 = new File(rootDir1 + "/trainUserList.fiteredby10.txt");
//			// if file doesnt exists, then create it
//			if (!file0.exists()) {
//				file0.createNewFile();
//			}
//
//			fw = new FileWriter(file0.getAbsoluteFile());
//			bw = new BufferedWriter(fw);
//			
//			file1 = new File(rootDir1 + "/testUserList.fiteredby10.txt");
//			// if file doesnt exists, then create it
//			if (!file1.exists()) {
//				file1.createNewFile();
//			}
//
//			fw1 = new FileWriter(file1.getAbsoluteFile());
//			bw1 = new BufferedWriter(fw1);
//			
//		    
//		    Collections.shuffle(listOfUsers);
//		    int i = 0;
//		    for(; i<7*(listOfUsers.size()/10); i++)
//		    {
//		    	bw.write(listOfUsers.get(i) + "\n");
//		    }
//		    for(; i<listOfUsers.size(); i++)
//		    {
//		    	bw1.write(listOfUsers.get(i) + "\n");
//		    }
//		    
//		    bw.close();
//		    bw1.close();
		    
		    //System.out.println(userCount);
		}
	}
	
	public static Long calculateTimeGap(String prvDate, String currDate) throws ParseException
	{
		Long slacktime;
		
		//System.out.println(prvDate+ ";" + currDate);
		String prevYear = prvDate.trim().substring(0, 4);
    	String prevMonth = prvDate.trim().substring(4, 6);
    	String prevDay = prvDate.trim().substring(6, 8);
    	String prevHour = prvDate.trim().substring(8, 10);
    	String prevMinute = prvDate.trim().substring(10, 12);
    	

    	String currYear = currDate.trim().substring(0, 4);
    	String currMonth = currDate.trim().substring(4, 6);
    	String currDay = currDate.trim().substring(6, 8);
    	String currHour = currDate.trim().substring(8, 10);
    	String currMinute = currDate.trim().substring(10, 12);
    	
    	String dateStop = currMonth + "/" + currDay + "/" + currYear + " " + currHour + ":" + currMinute;
		String dateStart = prevMonth + "/" + prevDay + "/" + prevYear + " " + prevHour + ":" + prevMinute;
		
		//System.out.println(dateStart);
		//System.out.println(dateStop);
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		Date d1 = null;
		Date d2 = null;
		
		d1 = format.parse(dateStart);
		d2 = format.parse(dateStop);

		slacktime = (d2.getTime() - d1.getTime())/(24 * 60 * 60 * 1000);
		
		//if(slacktime<0)slacktime = Math.abs(slacktime);
		
		return slacktime;
	}
}
