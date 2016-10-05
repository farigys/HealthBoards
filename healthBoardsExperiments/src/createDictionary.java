import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class createDictionary {
	public static void main(String[] args) throws IOException, ParseException {
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//		"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//		"brain-tumors", "cerebral-palsy", "dizziness-vertigo", "depression"};
		//String[] conditions = {"depression"};
		String[] conditions = {"relationship-health"};
		for(int c=0; c<conditions.length; c++)
		{
			HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
			HashMap<String, Integer> bigramMap = new HashMap<String, Integer>();
			HashMap<String, Integer> skipBigramMap = new HashMap<String, Integer>();
			
			HashMap<String, HashSet<String>> wordIdfMap = new HashMap<String, HashSet<String>>();
			HashMap<String, HashSet<String>> bigramIdfMap = new HashMap<String, HashSet<String>>();
			HashMap<String, HashSet<String>> skipBigramIdfMap = new HashMap<String, HashSet<String>>();
			
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			new File(root+"/Caches").mkdir();
			
			String rootDir1 = root+"/Caches";
			
			File file = new File(rootDir1 + "/Dictionary.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			File file1 = new File(rootDir1 + "/BigramDictionary.txt");
			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			File file2 = new File(rootDir1 + "/1-skip-2-gram-Dictionary.txt");
			// if file doesnt exists, then create it
			if (!file2.exists()) {
				file2.createNewFile();
			}

			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			
			
			for(int n=0; n<10000; n++)//only for relationship-health
			//for(int n=0; n<listOfFiles.length; n++)
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
					
					//for(int l=0; l<10000; l++)//only for relationship status
					for(int l=0; l<listitems.size(); l++)
					{
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
									if(!wordMap.containsKey(currWord)){
										wordMap.put(currWord, 1);
										}
									else{
										int tempcount = wordMap.get(currWord);
										tempcount++;
										wordMap.put(currWord, tempcount);
									}
									
									if(wordIdfMap.containsKey(currWord))
									{
										HashSet<String> tempSet = wordIdfMap.get(currWord);
										tempSet.add(postID);
										wordIdfMap.put(currWord, tempSet);
									}
									else
									{
										HashSet<String> tempSet = new HashSet<String>();
										tempSet.add(postID);
										wordIdfMap.put(currWord, tempSet);
									}
									
									if(countword == words.length - 1)continue;
									
									String currBigram = words[countword] + "+" + words[countword + 1];
									if(!bigramMap.containsKey(currBigram)){
										bigramMap.put(currBigram, 1);
									}
									else{
										int tempcount = bigramMap.get(currBigram);
										tempcount++;
										bigramMap.put(currBigram, tempcount);
									}
									
									if(bigramIdfMap.containsKey(currBigram))
									{
										HashSet<String> tempSet = bigramIdfMap.get(currBigram);
										tempSet.add(postID);
										bigramIdfMap.put(currBigram, tempSet);
									}
									else
									{
										HashSet<String> tempSet = new HashSet<String>();
										tempSet.add(postID);
										bigramIdfMap.put(currBigram, tempSet);
									}
									
									if(countword == words.length - 2)continue;
									
									String currSkipBigram = words[countword] + "+" + words[countword + 2];
									if(!skipBigramMap.containsKey(currSkipBigram)){
										skipBigramMap.put(currSkipBigram, 1);
									}
									else{
										int tempcount = skipBigramMap.get(currSkipBigram);
										tempcount++;
										skipBigramMap.put(currSkipBigram, tempcount);
									}
									
									if(skipBigramIdfMap.containsKey(currSkipBigram))
									{
										HashSet<String> tempSet = skipBigramIdfMap.get(currSkipBigram);
										tempSet.add(postID);
										skipBigramIdfMap.put(currSkipBigram, tempSet);
									}
									else
									{
										HashSet<String> tempSet = new HashSet<String>();
										tempSet.add(postID);
										skipBigramIdfMap.put(currSkipBigram, tempSet);
									}
								}
							}
							
						//}
						
					}
				//}
					x.close();
			}
			
			Iterator it = wordMap.entrySet().iterator();
			int index = 0;
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        bw.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + wordIdfMap.get(pair.getKey().toString()).size() + "\n");
		        index++;
		    }
			
			bw.close();
			
			index = 0;
			
			it = bigramMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        bw1.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + bigramIdfMap.get(pair.getKey().toString()).size() + "\n");
		        index++;
		    }
			
			bw1.close();
			
			index = 0;
			it = skipBigramMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        bw2.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + skipBigramIdfMap.get(pair.getKey().toString()).size() + "\n");
		        index++;
		    }
			
			bw2.close();
			
			file = new File(rootDir1 + "/Dictionary.filtered.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			file1 = new File(rootDir1 + "/BigramDictionary.filtered.txt");
			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			fw1 = new FileWriter(file1.getAbsoluteFile());
			bw1 = new BufferedWriter(fw1);
			
			file2 = new File(rootDir1 + "/1-skip-2-gram-Dictionary.filtered.txt");
			// if file doesnt exists, then create it
			if (!file2.exists()) {
				file2.createNewFile();
			}

			fw2 = new FileWriter(file2.getAbsoluteFile());
			bw2 = new BufferedWriter(fw2);
			
			it = wordMap.entrySet().iterator();
			index = 0;
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        int value = Integer.parseInt(pair.getValue().toString());
		        if(value>=100)
		        {
			        bw.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + wordIdfMap.get(pair.getKey().toString()).size() + "\n");
			        index++;
		        }
		    }
			
			bw.close();
			
			index = 0;
			
			it = bigramMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        int value = Integer.parseInt(pair.getValue().toString());
		        if(value>=10)
		        {
		        	bw1.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + bigramIdfMap.get(pair.getKey().toString()).size() + "\n");
			        index++;	
		        }
		        
		    }
			
			bw1.close();
			
			index = 0;
			it = skipBigramMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        int value = Integer.parseInt(pair.getValue().toString());
		        if(value>=10)
		        {
		        	bw2.write(pair.getKey().toString() + "%@%" + index + "%@%" + pair.getValue().toString() + "%@%" + skipBigramIdfMap.get(pair.getKey().toString()).size() + "\n");
			        index++;
		        }
		        
		    }
			
			bw2.close();
		}
	}
	
}
