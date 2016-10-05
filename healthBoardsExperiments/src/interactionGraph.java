import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class interactionGraph {
	public static void main(String[] args) throws IOException, ParseException {
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
				//"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
				//"brain-tumors", "cerebral-palsy", "dizziness-vertigo", "depression"};
		//String[] conditions = {"relationship-health"};
		//String[] conditions = {"alzheimers-disease-dementia"};
		
		String[] conditions = {"depression"};
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			String root = "/home/farigys/Health_Board/" + condition;
			
			File  f1 = new File(root + "/userList.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    String line = "";
		    
		    ArrayList<String> nameList = new ArrayList<String>();
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String userName = line.trim();
		    	nameList.add(userName.trim());
		    }
		    
			
			
			
			File file = new File(root + "/interactionGraph.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
		    
		    int size = nameList.size();
		    
		    int[][] graph = new int[size][size];
		    
		    for(int i=0; i<size; i++)
		    {
		    	for(int j=0; j<size; j++)
		    	{
		    		graph[i][j] = 0;
		    	}
		    }
		    
		    String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				if(n%10 == 0)System.out.println("Done: " + n);
				String filename = listOfFiles[n].getName();
				
				String fileKey = condition + "/" + filename;
				
				//if(lastPostContainer.containsKey(fileKey))
				//{
					//HashSet<String> postIdList = lastPostContainer.get(fileKey);
					
					JSONParser parser = new JSONParser();
					FileReader x = new FileReader(rootDir + filename);
					Object obj = parser.parse(x);
					JSONObject jsonObject = (JSONObject) obj;
					
					JSONArray listitems = (JSONArray) jsonObject.get("items");
					
					ArrayList<String> actorList = new ArrayList<String>();
					
					for(int l=0; l<listitems.size(); l++)
					{
						JSONObject listitem = (JSONObject) listitems.get(l);
						JSONObject actor =  (JSONObject)listitem.get("actor");
						String actorName = actor.get("name").toString().trim();
						actorList.add(actorName);
					}
					
					for(int i=0; i<actorList.size(); i++)
					{
						String primaryActor = actorList.get(i);
						for(int j=0; j<actorList.size(); j++)
						{
							if(i==j)continue;
							String secondaryActor = actorList.get(j);
							
							int primaryActorIndex = nameList.indexOf(primaryActor);
							int secondaryActorIndex = nameList.indexOf(secondaryActor);
							
							if(primaryActorIndex == -1 || secondaryActorIndex == -1)System.out.println("bug");
							
							graph[primaryActorIndex][secondaryActorIndex]++;
						}
					}
			}
			
			for(int i=0; i<size; i++)
		    {
				bw.write(Integer.toString(i));
				//System.out.print(i);
		    	for(int j=0; j<size; j++)
		    	{
		    		 if(graph[i][j]!=0)
		    		 {
		    			 bw.write(";" + Integer.toString(j) + " " + Integer.toString(graph[i][j]));
		    			 //System.out.print(";" + j + " " + graph[i][j]);
		    		 }
		    	}
		    	bw.write("\n");
		    	//System.out.print("\n");
		    }
			
			bw.close();
		}
	}
}
