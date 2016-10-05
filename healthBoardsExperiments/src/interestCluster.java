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


public class interestCluster {
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
			
			File  f1 = new File(root + "/userInfo.txt");
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    String line = "";
		    
		    HashSet<String> names = new HashSet<String>();
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String[] parts = line.split(";");
		    	String userName = parts[0];
		    	names.add(userName.trim());
		    }
		    
		    ArrayList<String> nameList = new ArrayList<String>(names);
		    
		    File file = new File(root + "/userList.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i=0; i<nameList.size(); i++)
			{
				bw.write(nameList.get(i)+"\n");
			}
			
			bw.close();
			
			file = new File(root + "/interestCluster.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		    
		    int size = nameList.size();
		    
		    int[][] graph = new int[size][size];
		    int[][] graph1 = new int[size][size];
		    
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
					HashSet<String> uniqueActorSet = new HashSet<String>();
					
					for(int l=0; l<listitems.size(); l++)
					{
						JSONObject listitem = (JSONObject) listitems.get(l);
						JSONObject actor =  (JSONObject)listitem.get("actor");
						String actorName = actor.get("name").toString().trim();
						actorList.add(actorName);
						uniqueActorSet.add(actorName);
					}
					
					//if(filename.contains("702190-"))System.out.println(actorList);
					
					ArrayList<String> uniqueActorList = new ArrayList<String>(uniqueActorSet); 
					
					for(int i=0; i<uniqueActorList.size(); i++)
					{
						String primaryActor = uniqueActorList.get(i);
						for(int j=0; j<uniqueActorList.size(); j++)
						{
							if(i==j)continue;
							String secondaryActor = uniqueActorList.get(j);
							
							int primaryActorIndex = nameList.indexOf(primaryActor);
							int secondaryActorIndex = nameList.indexOf(secondaryActor);
							
							if(primaryActorIndex == -1 || secondaryActorIndex == -1)System.out.println("bug");
							
							graph[primaryActorIndex][secondaryActorIndex]++;
							graph1[primaryActorIndex][secondaryActorIndex]+=Collections.frequency(actorList, secondaryActor);
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
		    			 bw.write(";" + Integer.toString(j) + " " + Integer.toString(graph[i][j]) + " " + Integer.toString(graph1[i][j]));
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
