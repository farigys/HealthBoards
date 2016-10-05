import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class createSparseBoW {
	public static void main(String[] args) throws IOException, ParseException {
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
				//"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
				//"brain-tumors", "cerebral-palsy", "dizziness-vertigo", "depression"};
		//String[] conditions = {"relationship-health"};
		String[] conditions = {"alzheimers-disease-dementia"};
		for(int c=0; c<conditions.length; c++)
		{
			HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
			HashMap<String, Integer> bigramMap = new HashMap<String, Integer>();
			HashMap<String, Integer> skipBigramMap = new HashMap<String, Integer>();
			
			
			
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootCache = root + "/Caches/";
			
			File  f1 = new File(rootCache+"/Dictionary.filtered.txt");//if not filtered by frequency, use the file without .filtered part
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    String line = "";
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String[] parts = line.split("%@%");
		    	String word = parts[0];
		    	int index = Integer.parseInt(parts[1]); 
		    	
		    	wordMap.put(word, index);
		    	
		    }
		    
		    f1 = new File(rootCache+"/BigramDictionary.filtered.txt");//if not filtered by frequency, use the file without .filtered part
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String[] parts = line.split("%@%");
		    	String word = parts[0];
		    	int index = Integer.parseInt(parts[1]); 
		    	
		    	bigramMap.put(word, index);
		    	
		    }
		    
		    f1 = new File(rootCache+"/1-skip-2-gram-Dictionary.filtered.txt");//if not filtered by frequency, use the file without .filtered part
		    fis1 = new FileInputStream(f1); 
		    reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    line = "";
		    
		    while((line = reader1.readLine())!=null)
		    {
		    	String[] parts = line.split("%@%");
		    	String word = parts[0];
		    	int index = Integer.parseInt(parts[1]); 
		    	
		    	skipBigramMap.put(word, index);
		    	
		    }
		    
		    reader1.close();
		    
		    String rootDir1 = root+"/Caches";
			
			File file = new File(rootDir1 + "/BagOfWords.filtered.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			File file1 = new File(rootDir1 + "/BagOfBigrams.filtered.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			File file2 = new File(rootDir1 + "/BagOfSkipBigrams.filtered.txt");//if not filtered by frequency, use the file without .filtered part
			// if file doesnt exists, then create it
			if (!file2.exists()) {
				file2.createNewFile();
			}

			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
		    
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
					
					for(int l=0; l<listitems.size(); l++)
					{
						//if(l%100 == 0)System.out.println("Done: " + l);
						TreeMap<Integer, Integer> wordCountMap = new TreeMap<Integer, Integer>();
						TreeMap<Integer, Integer> bigramCountMap = new TreeMap<Integer, Integer>();
						TreeMap<Integer, Integer> skipBigramCountMap = new TreeMap<Integer, Integer>();
						
						JSONObject listitem = (JSONObject) listitems.get(l);
						String postID = listitem.get("id").toString().trim();
						
						postID = fileKey + "/" + postID;
						
						//System.out.println(postID);
						
						
						//if(!postIdList.contains(postID))continue;
						//else
						//{
							//String content = listitem.get("content").toString();
							//if(content==null)continue;
							//StringTokenizer token = new StringTokenizer(content, "#####");
							String contents = "";
							JSONArray contentList = new JSONArray();
							//try{
								contentList = (JSONArray) listitem.get("postaggedcontent");
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
									contents = contents + " " + text.trim();
								}
								
								String[] words = text.split(" ");
								if(words.length <= 0)continue;
								
								for(int countword = 0; countword<words.length; countword++)
								{
									String currWord = words[countword];
									int index = 0;
									try{
										index = wordMap.get(currWord);
									}catch(Exception e)
									{
										continue;
									}
									if(wordCountMap.containsKey(index))
									{
										int tempCount = wordCountMap.get(index);
										tempCount++;
										wordCountMap.put(index, tempCount);
									}
									else wordCountMap.put(index, 1);
									
									if(countword == words.length - 1)continue;
									
									String currBigram = words[countword] + "+" + words[countword + 1];
									try{
										index = bigramMap.get(currBigram);
									}catch(Exception e)
									{
										continue;
									}
									if(bigramCountMap.containsKey(index))
									{
										int tempCount = bigramCountMap.get(index);
										tempCount++;
										bigramCountMap.put(index, tempCount);
									}
									else bigramCountMap.put(index, 1);
									
									if(countword == words.length - 2)continue;
									
									String currSkipBigram = words[countword] + "+" + words[countword + 2];
									try{
										index = skipBigramMap.get(currSkipBigram);
									}catch(Exception e)
									{
										continue;
									}
									if(skipBigramCountMap.containsKey(index))
									{
										int tempCount = skipBigramCountMap.get(index);
										tempCount++;
										skipBigramCountMap.put(index, tempCount);
									}
									else skipBigramCountMap.put(index, 1);
								}
							}
							
							bw.write(postID);
							
							Iterator it = wordCountMap.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry pair = (Map.Entry)it.next();
						        bw.write(","+pair.getKey().toString() + " " + pair.getValue());
						        
						    }
							
							bw.write("\n");
							
							bw1.write(postID);
							
							it = bigramCountMap.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry pair = (Map.Entry)it.next();
						        bw1.write(","+pair.getKey().toString() + " " + pair.getValue());
						        
						    }
							
							bw1.write("\n");
							
							bw2.write(postID);
							
							it = skipBigramCountMap.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry pair = (Map.Entry)it.next();
						        bw2.write(","+pair.getKey().toString() + " " + pair.getValue());
						        
						    }
							
							bw2.write("\n");
							
					}
					x.close();
			}
			
			bw.close();
			bw1.close();
			bw2.close();
			
		}
	}
}
