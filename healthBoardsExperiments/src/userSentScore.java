import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class userSentScore {
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
			
			f = new File(rootDir+"/userPostInfo.txt");
	        fis = new FileInputStream(f); 
	        reader = new BufferedReader(new InputStreamReader(fis));
	        
	        
		}
	}
}
