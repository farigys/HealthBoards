import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class sentimentAnalysis {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//findTopPostersLat10Posts();
		//String[] conditions = {"alzheimers-disease-dementia"};
		String[] conditions = {"depression"};
		
		String root = "/home/farigys/Health_Board/";
		
		String cacheDir = "/home/farigys/Health_Board/CacheFiles/";
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String rootDir = root + condition;
			
			File f = new File(rootDir+"/avgSentimentOfPosts.txt");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
	        HashMap<String, Double> sentmap = new HashMap<String, Double>();
	        
			String line = "";
			
			while((line = reader.readLine())!=null)
			{				
				String[] parts = line.split(":");
				
				sentmap.put(parts[0], Double.parseDouble(parts[1]));
			}
			
			double[][] sentArray = new double[100][100];
			
			f = new File(rootDir+"/topPosterLast100Posts.txt");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
			
	        line = "";
	        
	        int postercount = 0;
	        
	        while((line = reader.readLine())!=null)
			{	
				String[] parts = line.split(";");
				for(int i=1; i<=100; i++)
				{
					String postName = parts[i];
					//System.out.println(postName);
					String[] nameParts = postName.split("/");
					postName = nameParts[0] + "/" + nameParts[1] + ".json/" + nameParts[2];
					
					double sentVal = sentmap.get(postName);
					
					sentArray[postercount][i-1] = sentVal;
				}
				
				postercount++;
			}
			
			double[] sentValsforLat100posts = new double[100];
			
			File file = new File(rootDir + "/sentAnalysisTop100Users.txt");//user info cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i=0; i<100; i++)
			{
				bw.write(Integer.toString(i));
				for(int j=0; j<100; j++)
				{
					bw.write(","+Double.toString(sentArray[i][j]));
				}
				bw.write("\n");
			}
			
			bw.close();
			
			for(int j=0; j<100; j++)
			{
				double sum = 0;
				for(int i=0; i<100; i++)
				{
					sum+=sentArray[i][j];
				}
				
				sentValsforLat100posts[j] = sum/100;
			}
			
			//for(int i=0; i<100; i++)System.out.println(i + "," + sentValsforLat100posts[i]);
		}
		
		
		
	}

	private static void findTopPostersLat10Posts() throws IOException {
		String root = "/home/farigys/Health_Board/";
		
		String cacheDir = "/home/farigys/Health_Board/CacheFiles/";
		
		//String[] conditions = {"alzheimers-disease-dementia"};
	    String[] conditions = {"depression"};
	    //String[] conditions = {"relationship-health"};
	    
		
	    for(int c=0; c<conditions.length; c++)
		{
	    	String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String rootDir = root + condition;
			
			File file = new File(rootDir + "/topPosterLast100Posts.txt");//user info cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
	    	File f = new File(cacheDir+"topPostersDEP.txt");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	        
	        String line = "";
	        
	        HashSet<String> topUsers = new HashSet<String>();
	        
	        while ((line = reader.readLine())!=null)
	        {
	        	String[] parts = line.split(";");
	        	
	        	topUsers.add(parts[0]);
	        }
	        
	        reader.close();
	        
	       
	        f = new File(rootDir+"/userPostInfo.txt");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
	        
	        line = "";
	        
	        HashMap<String, ArrayList<post>> postMap = new HashMap<String, ArrayList<post>>();
	        
	        while ((line = reader.readLine())!=null)
	        {
	        	String[] parts = line.split(";");
	        	
	        	String userName = parts[0].trim();
	        	
	        	if(!topUsers.contains(userName))continue;
	        	
	        	if(postMap.containsKey(userName))
	        	{
	        		ArrayList<post> tempPostList = postMap.get(userName);
	        		post tempPost = new post();
	        		tempPost.postID = parts[1].trim() + "/" + parts[2].trim();
	        		tempPost.date = parts[3].trim();
	        		tempPostList.add(tempPost);
	        		postMap.put(userName, tempPostList);
	        	}
	        	else
	        	{
	        		ArrayList<post> tempPostList = new ArrayList<post>();
	        		post tempPost = new post();
	        		tempPost.postID = parts[1].trim() + "/" + parts[2].trim();
	        		tempPost.date = parts[3].trim();
	        		tempPostList.add(tempPost);
	        		postMap.put(userName, tempPostList);
	        	}
	        }
	        
	        Iterator it = postMap.entrySet().iterator();
	        
	        while(it.hasNext())
	        {
	        	Map.Entry pair = (Map.Entry) it.next();
	        	String userName = pair.getKey().toString();
	        	ArrayList<post> postList = (ArrayList<post>) pair.getValue();
	        	
	        	bw.write(userName);
	        	
	        	TreeMap<Long, String> sortedPostMap = new TreeMap<Long, String>();
	        	
	        	for(int i=0; i<postList.size(); i++)
	        	{
	        		sortedPostMap.put(Long.parseLong(postList.get(i).date), postList.get(i).postID);
	        	}
	        	
	        	ArrayList<Long> latestDates = new ArrayList<Long>(sortedPostMap.descendingKeySet());
	        	
	        	for(int i=0; i<100; i++)
	        	{
	        		bw.write(";" + sortedPostMap.get(latestDates.get(i)));
	        	}
	        	bw.write("\n");
	        }
	        
	        bw.close();
	        
		}
		
	}
	
}

class post
{
	String postID;
	String date;
	
	post()
	{
		postID = "";
		date = "";
	}
}
