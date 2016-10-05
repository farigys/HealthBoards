import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.parser.ParseException;


public class findTopPostersNotLeaving {
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		//String[] conditions = {"depression"};
		//String[] conditions = {"relationship-health"};
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//		"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//		"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//						"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
		String[] conditions = {"alzheimers-disease-dementia"};
		String cutOffTime = "201505010000";
		
		HashMap<String, ArrayList<String>> userPostMap = new HashMap<String, ArrayList<String>>();
		
		
		
		HashMap<String, String> postTimeMap = new HashMap<String, String>();
		
		HashMap<String, String> endActivityMap = new HashMap<String, String>();
		
		TreeMap<Integer, ArrayList<String>> topPosterMap = new TreeMap<Integer, ArrayList<String>>();
		
		for(int c=0; c<conditions.length; c++)
		{
			HashSet<String> topPosters = new HashSet<String>();
			
			String condition = conditions[c];
			
			System.out.println(condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			File file = new File("/home/farigys/Health_Board/topPostersRH.txt");
			FileInputStream files = new FileInputStream(file); 
		    BufferedReader freader = new BufferedReader(new InputStreamReader(files));
		    
		    String line1 = "";
		    
		    while((line1 = freader.readLine())!=null)
		    {
		    	String[] parts = line1.split(";");
		    	topPosters.add(parts[0]);
		    }
		    
		    
			File  f = new File(root+"/userInfo.txt");
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    String line = reader.readLine();
		    while((line = reader.readLine())!=null)
		    {
		    	String[] tokens = line.split(";");
		    	//System.out.println(tokens[6]);
		    	String userName = tokens[0];
		    	Integer posts = Integer.parseInt(tokens[6].trim());
		    	//Long startActivity = Long.parseLong(tokens[10].trim());
		    	//Long endActivity = Long.parseLong(tokens[14].trim());
		    	
		    	long activityDuration;
		    	
//		    	int startYear = Integer.parseInt(tokens[10].trim().substring(0, 4));
//		    	int startMonth = Integer.parseInt(tokens[10].trim().substring(4, 6));
//		    	int startDay = Integer.parseInt(tokens[10].trim().substring(6, 8));
//		    	
//
//		    	int endYear = Integer.parseInt(tokens[14].trim().substring(0, 4));
//		    	int endMonth = Integer.parseInt(tokens[14].trim().substring(4, 6));
//		    	int endDay = Integer.parseInt(tokens[14].trim().substring(6, 8));
//		    	
//		    	
//		    	
//		    	activityDuration = (endYear - startYear)*365 + (endMonth-startMonth)*30.5 + (endDay-startDay);
//		    	
		    	
		    	String startYear = tokens[10].trim().substring(0, 4);
		    	String startMonth = tokens[10].trim().substring(4, 6);
		    	String startDay = tokens[10].trim().substring(6, 8);
		    	

		    	String endYear = tokens[14].trim().substring(0, 4);
		    	String endMonth = tokens[14].trim().substring(4, 6);
		    	String endDay = tokens[14].trim().substring(6, 8);
		    	String endHour = tokens[14].trim().substring(8, 10);
		    	String endMinute = tokens[14].trim().substring(10,12);
		    	
		    	endActivityMap.put(userName, tokens[14].trim());
//		    	
		    	String dateStart = startMonth + "/" + startDay + "/" + startYear;
				String dateStop = endMonth + "/" + endDay + "/" + endYear;
				
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

				Date d1 = null;
				Date d2 = null;
				
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);

				activityDuration = d2.getTime() - d1.getTime();
				
				//System.out.println(activityDuration);
		    	
		    	if(activityDuration<Long.parseLong("31536000000") || topPosters.contains(userName))continue;
		    	
		    	//if(Long.parseLong(cutOffTime) > Long.parseLong(tokens[14].trim()))continue;
		    	
		    	//System.out.println(userName + ":" + activityDuration);
		    	
		    	if(topPosterMap.containsKey(posts))
		    	{
		    		ArrayList<String> tempUserNameList = topPosterMap.get(posts);
		    		tempUserNameList.add(userName);
		    		topPosterMap.put(posts, tempUserNameList);
		    	}
		    	else
		    	{
		    		ArrayList<String> tempUserNameList = new ArrayList<String>();
		    		tempUserNameList.add(userName);
		    		topPosterMap.put(posts, tempUserNameList);
		    	}
		    	
		    }
		    
		    f = new File(root+"/userPostInfo.txt");
		    fis = new FileInputStream(f); 
		    reader = new BufferedReader(new InputStreamReader(fis));
		    line = "";
		    while((line = reader.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	String userName = parts[0].trim();
		    	String fileName = parts[1].trim();
		    	String fileId = parts[2].trim();
		    	String postTime = parts[3].trim();
		    	
		    	postTimeMap.put(fileName + "/" + fileId, postTime);
		    	
		    	if(userPostMap.containsKey(userName))
		    	{
		    		ArrayList<String> temp = userPostMap.get(userName);
		    		temp.add(fileName + "/" + fileId);
		    		userPostMap.put(userName, temp);
		    	}
		    	else
		    	{
		    		ArrayList<String> temp = new ArrayList<String>();
		    		temp.add(fileName + "/" + fileId);
		    		userPostMap.put(userName, temp);
		    	}
		    }
		   

			
		}
		
		ArrayList<String> topUsers = new ArrayList<String>();
		
		ArrayList<Integer> keys = new ArrayList<Integer>(topPosterMap.keySet());
	    int count = 0;
        for(int i=keys.size()-1; i>=0;i--){
        		ArrayList<String> tempList = topPosterMap.get(keys.get(i));
        		for(int j=0; j<tempList.size(); j++)
        		{
        			//bw.write(tempList.get(j) + ": " + keys.get(i)+"\n");
        			System.out.println(tempList.get(j) + ": " + keys.get(i)+"\n");
        			topUsers.add(tempList.get(j));
        			count++;
        			if(count==10)break; //
        		}
        		if(count==10)break; //
        }
        
        String rootDir = "/home/farigys/Health_Board/";
        
        File file = new File(rootDir + "/topPostersRH-NL.txt");//user info cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
        
        for(int i=0; i<count; i++)
        {
        	String userName = topUsers.get(i);
        	String endTime = endActivityMap.get(userName);
        	
        	String endYear = endTime.trim().substring(0, 4);
	    	String endMonth = endTime.trim().substring(4, 6);
	    	String endDay = endTime.trim().substring(6, 8);
	    	String endHour = endTime.trim().substring(8, 10);
	    	String endMinute = endTime.trim().substring(10,12);
        	
	    	String cutoffyear = Integer.toString(Integer.parseInt(endYear) - 1);
	    	
	    	String cutofftime = cutoffyear + endMonth + endDay + endHour + endMinute;
	    	
	    	String[] cutoffmonths = new String[12];
	    	
	    	cutoffmonths[0] = cutofftime;
	    	
	    	for(int j=1; j<12; j++)
	    	{
	    		String cutoffmonth = Integer.toString(Integer.parseInt(cutoffmonths[j-1].substring(4, 6)) + 1);
	    		if(Integer.parseInt(cutoffmonth)>12)
	    		{
	    			cutoffmonth = Integer.toString(Integer.parseInt(cutoffmonth) - 12);
	    			cutoffyear = Integer.toString(Integer.parseInt(cutoffyear) + 1);
	    		}
	    		if(cutoffmonth.length()==1)cutoffmonth = "0" + cutoffmonth;
	    		cutoffmonths[j] = cutoffyear + cutoffmonth + endDay + endHour + endMinute;
	    	}
	    	
	    	HashMap<Integer, ArrayList<String>> postsInTimeBuckets = new HashMap<Integer, ArrayList<String>>();
	    	
	    	ArrayList<String> postList = userPostMap.get(userName);
	    	
	    	for(int j=0; j<postList.size(); j++)
	    	{
	    		String postId = postList.get(j);
	    		String posttime = postTimeMap.get(postId);
	    		
	    		int k = 0;
	    		
	    		for(k=0; k<11; k++)
	    		{
	    			if(Long.parseLong(posttime) > Long.parseLong(cutoffmonths[k]) && 
	    					Long.parseLong(posttime) < Long.parseLong(cutoffmonths[k+1]))
	    			{
	    				if(postsInTimeBuckets.containsKey(k))
	    				{
	    					ArrayList<String> temp = postsInTimeBuckets.get(k);
		    				temp.add(postId);
		    				postsInTimeBuckets.put(k, temp);
	    				}
	    				else
	    				{
	    					ArrayList<String> temp = new ArrayList<String>();
		    				temp.add(postId);
		    				postsInTimeBuckets.put(k, temp);
	    				}
	    				break;
	    			}
	    		}
	    		if(k == 11 && Long.parseLong(posttime) > Long.parseLong(cutoffmonths[k]))
	    		{
	    			if(postsInTimeBuckets.containsKey(k))
    				{
    					ArrayList<String> temp = postsInTimeBuckets.get(k);
	    				temp.add(postId);
	    				postsInTimeBuckets.put(k, temp);
    				}
    				else
    				{
    					ArrayList<String> temp = new ArrayList<String>();
	    				temp.add(postId);
	    				postsInTimeBuckets.put(k, temp);
    				}
	    		}
	    		
	    	}
        	//TreeMap<Long, ArrayList<String>> lastTenPostList = new TreeMap<Long, ArrayList<String>>(); 
        
	    	
	    	bw.write(userName + ";");
	    	System.out.print(userName + ": ");
	    	int count1 = 0;
	    	for(int x =0; x<12; x++)
	    	{
	    		//bw.write(x + ":");
	    		ArrayList<String> temp = postsInTimeBuckets.get(x);
	    		try
	    		{
	    			bw.write(temp.get(0));
	    			for(int z=1; z<temp.size(); z++)
		    		{
		    			bw.write("," + temp.get(z));
		    			
		    		}
		    		if(x != 11)bw.write(";");
		    		count1+=temp.size();
	    		}catch(Exception e)
	    		{
	    			bw.write("@@@@@");
	    			if(x != 11)bw.write(";");
	    			continue;
	    		}
	    		
	    	}
	    	System.out.println(count1);
	    	bw.write("\n");
        
        }
        
        bw.close();
		
//		String root = "/home/farigys/Health_Board/";
//		
//		 File file = new File(root + "/topPosters.txt");//user info cache
//			// if file doesnt exists, then create it
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		BufferedWriter bw = new BufferedWriter(fw);
//	    
//	    keys = new ArrayList<Integer>(topPosterMap.keySet());
//	    count = 0;
//        for(int i=keys.size()-1; i>=0;i--){
//        		ArrayList<String> tempList = topPosterMap.get(keys.get(i));
//        		for(int j=0; j<tempList.size(); j++)
//        		{
//        			bw.write(tempList.get(j) + ": " + keys.get(i)+"\n");
//        			count++;
//        			if(count==10)break;
//        		}
//        		if(count==10)break;
//            }
//        bw.close();
        System.out.println("_____________________________________________________________");
	
	}
}
