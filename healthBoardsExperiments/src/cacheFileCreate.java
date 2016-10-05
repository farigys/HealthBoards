import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class cacheFileCreate {
	public static void main(String[] args) throws IOException, ParseException {
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//		"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//		"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		String[] conditions = {"relationship-health"};
		//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//						"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
		
		File file = new File("/home/farigys/Health_Board/userInfo.txt");//user info cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		TreeMap <String, userInfo> globalUserInfoMap = new TreeMap<String, userInfo>();
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			File file1 = new File(root + "/userInfo.txt");//user info cache
			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			File file2 = new File(root + "/userPostInfo.txt");//user info cache
			// if file doesnt exists, then create it
			if (!file2.exists()) {
				file2.createNewFile();
			}
			
			TreeMap <String, userInfo> userInfoMap = new TreeMap<String, userInfo>();

			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				String filename = listOfFiles[n].getName();
				
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(rootDir + filename));
				JSONObject jsonObject = (JSONObject) obj;
				
				String threadURL = jsonObject.get("threadURL").toString();
				
				StringTokenizer token1 = new StringTokenizer(threadURL, "/");
				
				String threadID = "";
				
				while(token1.hasMoreTokens())
				{
					threadID = token1.nextToken();
				}
				
				threadID = condition + "/" + threadID;
				
				JSONArray listitems = (JSONArray) jsonObject.get("items");
				
				for(int i=0; i<listitems.size(); i++)
				{
					JSONObject listitem = (JSONObject) listitems.get(i);
					
					String type = listitem.get("type").toString().trim();
					
					String date = listitem.get("published").toString().trim();
					
					String postID = listitem.get("id").toString().trim();
					
					StringTokenizer token = new StringTokenizer(date, "-,");
					
					String month = token.nextToken();
					String day = token.nextToken();
					String year = token.nextToken();
					
					String time = token.nextToken().toString().trim();
					
					StringTokenizer timeToken = new StringTokenizer(time, ": ");
					
					int hour = Integer.parseInt(timeToken.nextToken());
					String minute = timeToken.nextToken();
					String am_pm = timeToken.nextToken();
					
					if(am_pm.equals("AM") && hour == 12)hour = 0;
					if(am_pm.equals("PM") && hour < 12)hour += 12;
					
					String Hour = Integer.toString(hour);
					if(Hour.length() == 1)Hour = "0" + Hour;
					
					time = Hour + minute;
					
					String currentDateAndTime = year + month + day + time;
					
					//System.out.println(currentDateAndTime);
					
					JSONObject actor = (JSONObject)listitem.get("actor");
					
					String name = actor.get("name").toString();
					String startTime = actor.get("startTime").toString();
					String gender = actor.get("gender").toString().trim();
					if(gender.equals(""))gender = "unspecified";
					
					String status = actor.get("status").toString();
					String location = actor.get("location").toString();
					location = location.replace(";", ",");
					String image = actor.get("image").toString();
					String posts = actor.get("posts").toString();
					String blogs = actor.get("blogs").toString();
					
					
					
					post currentPost = new post();
					
					currentPost.threadID = threadID;
					currentPost.postID = postID;
					currentPost.type = type;
					currentPost.time = currentDateAndTime;
					
					bw2.write(name + "; " + threadID + "; " + postID + "; " + currentDateAndTime + "; " + type + "\n");
					//conditionwise userInfo
					if(userInfoMap.containsKey(name))
					{
						userInfo tempInfo = userInfoMap.get(name);
						post currEarliestPost = tempInfo.earliestPost;
						post currLatestPost = tempInfo.latestPost;
						
						long currEarliestTime = Long.parseLong(currEarliestPost.time);
						long currLatestTime = Long.parseLong(currLatestPost.time);
						
						if(currEarliestTime > Long.parseLong(currentDateAndTime))
						{
							tempInfo.earliestPost = currentPost;
						}
						if(currLatestTime < Long.parseLong(currentDateAndTime))
						{
							tempInfo.latestPost = currentPost;
						}
						int totalPostCount = Integer.parseInt(tempInfo.posts);
						totalPostCount++;
						tempInfo.posts = Integer.toString(totalPostCount);
						userInfoMap.put(name, tempInfo);
					}
					else
					{
						userInfo tempInfo = new userInfo();
						tempInfo.location = location;
						if(location == " ")tempInfo.location = "unspecified";
						tempInfo.startTime = startTime;
						tempInfo.status = status;
						tempInfo.image = image;
						if(image == " ")tempInfo.image = "unspecified";
						tempInfo.gender = gender;
						if(gender == " ")tempInfo.gender = "unspecified";
						tempInfo.posts = "1";
						tempInfo.blogs = blogs;
						
						post earliestPost = new post();
						
						earliestPost.threadID = threadID;
						earliestPost.postID = postID;
						earliestPost.time = currentDateAndTime;
						earliestPost.type = type;
						
						post latestPost = new post();
						
						latestPost.threadID = threadID;
						latestPost.postID = postID;
						latestPost.time = currentDateAndTime;
						latestPost.type = type;
						
						tempInfo.earliestPost = earliestPost;
						tempInfo.latestPost = latestPost;
						
						userInfoMap.put(name, tempInfo);
					}
					////////////////////////////////////////////////
					
					//global UserInfo
					if(globalUserInfoMap.containsKey(name))
					{
						userInfo tempInfo = globalUserInfoMap.get(name);
						post currEarliestPost = tempInfo.earliestPost;
						post currLatestPost = tempInfo.latestPost;
						
						long currEarliestTime = Long.parseLong(currEarliestPost.time);
						long currLatestTime = Long.parseLong(currLatestPost.time);
						
						if(currEarliestTime > Long.parseLong(currentDateAndTime))
						{
							tempInfo.earliestPost = currentPost;
						}
						if(currLatestTime < Long.parseLong(currentDateAndTime))
						{
							tempInfo.latestPost = currentPost;
						}
						globalUserInfoMap.put(name, tempInfo);
					}
					else
					{
						userInfo tempInfo = new userInfo();
						tempInfo.location = location;
						if(location == " ")tempInfo.location = "unspecified";
						tempInfo.startTime = startTime;
						tempInfo.status = status;
						tempInfo.image = image;
						if(image == " ")tempInfo.image = "unspecified";
						tempInfo.gender = gender;
						if(gender == " ")tempInfo.gender = "unspecified";
						tempInfo.posts = posts;
						tempInfo.blogs = blogs;
						
						post earliestPost = new post();
						
						earliestPost.threadID = threadID;
						earliestPost.postID = postID;
						earliestPost.time = currentDateAndTime;
						earliestPost.type = type;
						
						post latestPost = new post();
						
						latestPost.threadID = threadID;
						latestPost.postID = postID;
						latestPost.time = currentDateAndTime;
						latestPost.type = type;
						
						tempInfo.earliestPost = earliestPost;
						tempInfo.latestPost = latestPost;
						
						globalUserInfoMap.put(name, tempInfo);
					}
					////////////////////////////////////////////////
				}
			}
			
			bw1.write("UserName, location, startTime, status, image, gender, posts, blogs, " +
					"earliestPostThread, earliestPostID, earliestPostTime, " +
					"earliestPostType, latestPostThread, latestPostID, latestPostTime, latestPostType\n");
			
			System.out.println(userInfoMap.size());
			
			Iterator it = userInfoMap.entrySet().iterator();
		
			while(it.hasNext())
			{
				 Map.Entry pair = (Map.Entry)it.next();
				 String name = pair.getKey().toString();
				 userInfo tempUserInfo = (userInfo)pair.getValue();
				 
				 bw1.write(name + "; " + tempUserInfo.location + "; " + tempUserInfo.startTime + "; " + 
						 tempUserInfo.status + "; " + tempUserInfo.image + "; " + tempUserInfo.gender + "; " + 
						 tempUserInfo.posts + "; " + tempUserInfo.blogs + "; " + 
						 tempUserInfo.earliestPost.threadID + "; " + tempUserInfo.earliestPost.postID + "; " +
						 tempUserInfo.earliestPost.time + "; " + tempUserInfo.earliestPost.type + "; " +
						 tempUserInfo.latestPost.threadID + "; " + tempUserInfo.latestPost.postID + "; " +
						 tempUserInfo.latestPost.time + "; " + tempUserInfo.latestPost.type + "\n"
						 );
			}
			
			bw1.close();
			bw2.close();
		}
//////////////////////////////////creates cache for all conditions//////////////////////////////		
//		bw.write("UserName, location, startTime, status, iamge, gender, posts, blogs, " +
//				"earliestPostThread, earliestPostID, earliestPostTime, " +
//				"earliestPostType, latestPostThread, latestPostID, latestPostTime, latestPostType\n");
//		
//		System.out.println(globalUserInfoMap.size());
//		
//		Iterator it = globalUserInfoMap.entrySet().iterator();
//	
//		while(it.hasNext())
//		{
//			 Map.Entry pair = (Map.Entry)it.next();
//			 String name = pair.getKey().toString();
//			 userInfo tempUserInfo = (userInfo)pair.getValue();
//			 
//			 bw.write(name + "; " + tempUserInfo.location + "; " + tempUserInfo.startTime + "; " + 
//					 tempUserInfo.status + "; " + tempUserInfo.image + "; " + tempUserInfo.gender + "; " + 
//					 tempUserInfo.posts + "; " + tempUserInfo.blogs + "; " + 
//					 tempUserInfo.earliestPost.threadID + "; " + tempUserInfo.earliestPost.postID + "; " +
//					 tempUserInfo.earliestPost.time + "; " + tempUserInfo.earliestPost.type + "; " +
//					 tempUserInfo.latestPost.threadID + "; " + tempUserInfo.latestPost.postID + "; " +
//					 tempUserInfo.latestPost.time + "; " + tempUserInfo.latestPost.type + "\n"
//					 );
//		}
//		
//		bw.close();
		
	}
	
	public static class post
	{
		String threadID;
		String postID;
		String time;
		String type;
		
		public post()
		{
			this.threadID = "";
			this.postID = "";
			this.time = "";
			this.type = "";
		}
	}
	
	public static class userInfo
	{
		String location;
		String startTime;
		String status;
		String image;
		String gender;
		String posts;
		String blogs;
		post earliestPost;
		post latestPost;
	}
	
}


