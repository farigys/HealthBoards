import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class psyLingCacheCreate {
	public static void main(String[] args) throws IOException, ParseException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Health_Board/";
    	
    	//Trie t = new Trie();
    	
    	ArrayList<String> psywordlist = new ArrayList<String>();
    	
    	
    	
    	HashMap<String, ArrayList<Integer>> regCatMap = new HashMap<String, ArrayList<Integer>>();
    	
    	HashMap<String, ArrayList<String>> regexMap = new HashMap<String, ArrayList<String>>(); 
    	
    	HashSet<Pattern> regexset = new HashSet<Pattern>();
    	
    	HashSet<String> dict = new HashSet<String>();
    	
    	String rootDir = root+"LIWC/";
    	
    	File  fregex = new File(rootDir+"/LIWC2001_English.dic");
	    FileInputStream fisregex = new FileInputStream(fregex); 
	    BufferedReader readerregex = new BufferedReader(new InputStreamReader(fisregex));
	    String line = "";
	    readerregex.readLine();
	    
	    while((line = readerregex.readLine())!=null)
	    {
	    	StringTokenizer token = new StringTokenizer(line, "\t");
	    	String regex1 = token.nextToken();
	    	//if(regex1.length()<=3 && regex1.contains("*"))System.out.println(regex1);
	    	if(!regex1.contains("*"))dict.add(regex1);
	    	else
	    	{
	    		String regex2 = regex1.substring(0, 2);
	    		//System.out.println(regex2);
	    		if(regexMap.containsKey(regex2))
	    		{
	    			ArrayList<String> temp = regexMap.get(regex2);
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		else
	    		{
	    			ArrayList<String> temp = new ArrayList<String>();
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		
	    	}
	    	
	    	//wordCat regCat = new wordCat(regex1);
	    	
	    	ArrayList<Integer> cat = new ArrayList<Integer>();
	    	
	    	while(token.hasMoreTokens())
	    	{
	    		//int catno = Integer.parseInt(token.nextToken().toString());
	    		cat.add(Integer.parseInt(token.nextToken().toString()));
	    	}
	    	regCatMap.put(regex1, cat);
	    	
	    }
    	//System.out.println(regCatMap);
    	
    	
    	
    	readerregex.close();
    	////////////////////////////////////////////////////////////////////////////
    	
    	//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//		"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
    	String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
    					"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
    					"brain-tumors", "cerebral-palsy", "dizziness-vertigo", "depression"};
		//String[] conditions = {"relationship-health"};
    	//String[] conditions = {"depression"};
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			root = "/home/farigys/Health_Board/" + condition;
			
			rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			File file = new File(root + "/PsyLingCount.csv");//PsychoLinguistic category cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				String filename = listOfFiles[n].getName();
				
				String fileKey = condition + "/" + filename;
				
				//if(lastPostContainer.containsKey(fileKey))
				{
					//HashSet<String> postIdList = lastPostContainer.get(fileKey);
					
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(new FileReader(rootDir + filename));
					JSONObject jsonObject = (JSONObject) obj;
					
					JSONArray listitems = (JSONArray) jsonObject.get("items");
					
					for(int l=0; l<listitems.size(); l++)
					{
						JSONObject listitem = (JSONObject) listitems.get(l);
						String postID = listitem.get("id").toString().trim();
						
						postID = fileKey + "/" + postID;
						
						double[] arr = new double[69];
						
						//System.out.println(postID);
						
						int tokenCount;
						//if(!postIdList.contains(postID))continue;
						//else
						{
							//String content = listitem.get("content").toString();
							//if(content==null)continue;
							//StringTokenizer token = new StringTokenizer(content, "#####");
							String contents = "";
							JSONArray contentList = new JSONArray();
							//try{
								contentList = (JSONArray) listitem.get("content");
							//}catch(Exception e)
							//{
								//e.printStackTrace();
								//System.out.println(fileKey);
							//}
							
							
							for(int m=0; m<contentList.size(); m++)
							{
								String text = contentList.get(m).toString();
								if(text.startsWith("text: "))
								{
									text = text.replace("text: ", "");
									text = text.replaceAll("Quote:", "");
									contents = contents + ". " + text.trim();
								}
							}
							
							//while(token.hasMoreTokens())
							{
								//contents = token.nextToken();
								StringTokenizer token1 = new StringTokenizer(contents, " \n\t\r:,-!()[]{}?\"';.");
								String previousWord = "";
								tokenCount = token1.countTokens();
								while(token1.hasMoreTokens())
								{
									String match = token1.nextToken().toString().trim();
									//if(word.endsWith("."))word = word.substring(0, word.length()-1);
									
									if(dict.contains(match.toLowerCase()))
							  		{
							  			//System.out.println(key+" "+pair.getValue()+" from dict");
							  			ArrayList<Integer> tempc = regCatMap.get(match.toLowerCase());
							  			for(int i=0; i<tempc.size(); i++)
							  			{
							  				int cat = tempc.get(i);
							  				//int[] tempx = psyWordMap.get(filename);
							  				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
							  				//psyWordMap.put(filename, tempx);
							  				arr[cat]++;
							  			}
							  		}
							  		else
							  		{
							  			if(match.length()<2)continue;
							  			String key1 = match.toLowerCase().substring(0, 2);
							  			if(!regexMap.containsKey(key1))continue;
							  			else
							  			{
							  				ArrayList<String> tempreg = regexMap.get(key1);
							  				for(int i=0; i<tempreg.size(); i++)
							  				{
							  					Pattern pattern = Pattern.compile(tempreg.get(i));
							  					//System.out.println(pattern);
							  					//System.out.println("Matching "+match+" with "+tempreg.get(i));
							  					Matcher matcher = pattern.matcher(match.toLowerCase());
							  					if(matcher.find())
							  					{
							  						//System.out.println("match found for "+match+" with "+tempreg.get(i)+", "+key+" "+pair.getValue());
							  						//System.out.println(match.toLowerCase());
							  						ArrayList<Integer> tempc = regCatMap.get(tempreg.get(i));
							  						for(int j=0; j<tempc.size(); j++)
							  		      			{
							  		      				int cat = tempc.get(j);
							  		      				//int[] tempx = psyWordMap.get(filename);
							  		      				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
							  		      				//psyWordMap.put(filename, tempx);
							  		      				arr[cat]++;
							  		      			}
							  						break;
							  					}
							  				}
							  			}
							  			
							  		}
									
								}
								
//								for(int cnt =0; cnt<arr.length; cnt++)
//								{
//									arr[cnt] = arr[cnt]/(double)tokenCount;
//								}
							}
						}
						
						bw.write(postID + ",");
						for(int x=1; x<68; x++)bw.write(arr[x] + ",");
						bw.write(arr[68] + "," + tokenCount + "\n");
					}
				}
			}
			bw.close();
		}
	}
}
