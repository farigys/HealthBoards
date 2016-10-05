import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class DataSetBreakDown {
	public static void main(String[] args) throws IOException {
		NumberFormat formatter = new DecimalFormat("#0.00"); 
	  	//String[] conditions = {"abuse-support", "diet-nutrition", "exercise-fitness"};
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//				"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//				"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		//String[] conditions = {"depression"};
		String[] conditions = {"relationship-health"};
		
		HashMap<String, Integer> userPostMap = new HashMap<String, Integer>();
		HashMap<String, Integer> userReplyMap = new HashMap<String, Integer>();
		
		//ArrayList<Integer> replyPerPost = new ArrayList<Integer>();
		
		HashMap<String, Integer> replyPerPost = new HashMap<String, Integer>();
		
		ArrayList<Integer> postPerUser = new ArrayList<Integer>();
		ArrayList<Integer> replyPerUser = new ArrayList<Integer>();
		
		int posts = 0, reply = 0, male = 0, female = 0, unspecified = 0;
		
		HashSet<String> users = new HashSet<String>();
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			File  f1 = new File(root+"/userInfo.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    String line = reader1.readLine();
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	//System.out.println(line);
		    	String[] parts = line.split(";");
		    	String userName = parts[0].trim();
		    	String gender = parts[5].trim();
		    	
		    	users.add(userName);
		    	
		    	if(gender.equals("male"))male++;
		    	else if(gender.equals("female"))female++;
		    	else unspecified++;
		    }
		    
		    Iterator it = users.iterator();
		    
		    while(it.hasNext())
		    {
		    	String userName = it.next().toString();
		    	if(!userPostMap.containsKey(userName))userPostMap.put(userName, 0);
		    	if(!userReplyMap.containsKey(userName))userReplyMap.put(userName, 0);
		    }
		    
		    f1 = new File(root+"/userPostInfo.txt");
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    line = "";
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	String userName = parts[0].trim();
		    	String postName = parts[1].trim();
		    	String postType = parts[4].trim();
		    	
		    	if(postType.equals("init"))
		    	{
		    		posts++;
		    		if(userPostMap.containsKey(userName))
		    		{
		    			int count = userPostMap.get(userName);
		    			count++;
		    			userPostMap.put(userName, count);
		    		}
		    		else System.out.println("ding!!!!!!");
		    		
		    		if(replyPerPost.containsKey(postName))
		    		{
		    			
		    		}
		    		else replyPerPost.put(postName, 0);
		    	}
		    	else if(postType.equals("reply"))
		    	{
		    		reply++;
		    		if(userReplyMap.containsKey(userName))
		    		{
		    			int count = userReplyMap.get(userName);
		    			count++;
		    			userReplyMap.put(userName, count);
		    		}
		    		else System.out.println("ding!!!!!!");
		    		
		    		if(replyPerPost.containsKey(postName))
		    		{
		    			int count = replyPerPost.get(postName);
		    			count++;
		    			replyPerPost.put(postName, count);
		    		}
		    		else replyPerPost.put(postName, 1);
		    	}
		    }
		    
		}
		System.out.println("posts: " + posts);
		//System.out.println("posts: " + replyPerPost.size());
		System.out.println("replies: " + reply);
		System.out.println("users: " + users.size());
		
		ArrayList<Integer> repPerPost = new ArrayList<Integer>(replyPerPost.values());
		
		stats postStat = calculateStats(repPerPost, posts);
		
		System.out.println("reply/post: avg: " + postStat.mean + ", stdErr: " + postStat.stdDev);
		
		postPerUser = new ArrayList<Integer>(userPostMap.values());
		
		postStat = calculateStats(postPerUser, posts);
		
		System.out.println("post/user: avg: " + postStat.mean + ", stdErr: " + postStat.stdDev);
		
		replyPerUser = new ArrayList<Integer>(userReplyMap.values());
		
		postStat = calculateStats(replyPerUser, reply);
		
		
		
		System.out.println("reply/user: avg: " + postStat.mean + ", stdErr: " + postStat.stdDev);
		
		double total = male + female + unspecified;
		
		System.out.println("male: " + male/total + ", female: " + female/total + ", unspecified: " + unspecified/total);
		
		
	}
	
	static stats calculateStats(ArrayList<Integer> arr, int size)
	{
		stats currStat = new stats();
		
		int sum = 0;
		
		for(int i=0; i<arr.size(); i++)
		{
			sum += arr.get(i);
		}
		
		currStat.sum = sum;
		
		double mean = 0, variance = 0;
		
		mean = (double)sum/arr.size();
		
		currStat.mean = mean;
		
		double sqrdDiff = 0;
		
		for(int i=0; i<arr.size(); i++)
		{
			sqrdDiff+= (((double)arr.get(i) - mean) * ((double)arr.get(i) - mean));
		}
		
		variance = sqrdDiff/arr.size();
		
		double stdDev = Math.sqrt(variance);
		
		currStat.variance = variance;
		
		currStat.stdDev = stdDev/Math.sqrt(arr.size());
		
		return currStat;
	}
}



class stats
{
	int sum;
	double mean;
	double variance;
	double stdDev;
	
	public stats()
	{
		sum = 0; 
		mean = 0;
		variance = 0;
		stdDev = 0;
	}
}
