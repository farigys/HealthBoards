import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PMIAnalysis {
	public static void main(String[] args) throws IOException, ParseException {
		//HashMap<String, HashSet<String>> lastPostContainer = new HashMap<String, HashSet<String>>();
		
		HashSet<String> lastPostSet = new HashSet<String>();
		
		HashMap<String, String> postDateMap = new HashMap<String, String>();
		
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		
		HashMap<String, Integer> bigramMap = new HashMap<String, Integer>();
		
		HashMap<String, Integer> lastPostWordMap = new HashMap<String, Integer>();
		
		HashMap<String, Integer> lastPostBigramMap = new HashMap<String, Integer>();
		
		String cutOffTime = "201502010000";
		
		int totalWordCount = 0, totalPostCount = 0, lastPostCount = 0;
		
		BufferedReader br = null;
		
		String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
				"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
				"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			br = new BufferedReader(new FileReader(root + "/userInfo.txt"));
			
			String line = "";
			
			line = br.readLine();
			
			while((line = br.readLine())!=null)
			{
				StringTokenizer token = new StringTokenizer(line, ";");
				int tokenLength = token.countTokens();
				String threadId = "", postId = "", postTime = "";
				for(int i=1; i<=tokenLength-3; i++)
				{
					threadId = token.nextToken().toString();
				}
				threadId = threadId.trim();
				postId = token.nextToken().toString().trim();
				postTime = token.nextToken().toString().trim();
				
				if(Long.parseLong(cutOffTime) < Long.parseLong(postTime))continue;
				
				//postDateMap.put(threadId+"/"+postId, postTime);
				
//				if(lastPostContainer.containsKey(threadId))
//				{
//					HashSet<String> postIdSet = lastPostContainer.get(threadId);
//					postIdSet.add(postId);
//					lastPostContainer.put(threadId,  postIdSet);
//				}
//				else
//				{
//					lastPostContainer.put(threadId, new HashSet<String>());
//				}
				lastPostSet.add(threadId+".json/"+postId);
				//System.out.println(threadId+"/"+postId);
			}
		}
		
		br = new BufferedReader(new FileReader("/home/farigys/Health_Board/userInfo.txt"));
		
		String line = "";
		
		line = br.readLine();
		
		while((line = br.readLine())!=null)
		{
			StringTokenizer token = new StringTokenizer(line, ";");
			int tokenLength = token.countTokens();
			String threadId = "", postId = "", postTime = "";
			for(int i=1; i<=tokenLength-3; i++)
			{
				threadId = token.nextToken().toString();
			}
			threadId = threadId.trim();
			postId = token.nextToken().toString().trim();
			postTime = token.nextToken().toString().trim();
			
			if(Long.parseLong(cutOffTime) < Long.parseLong(postTime))continue;
			
			//postDateMap.put(threadId+"/"+postId, postTime);
			
//			if(lastPostContainer.containsKey(threadId))
//			{
//				HashSet<String> postIdSet = lastPostContainer.get(threadId);
//				postIdSet.add(postId);
//				lastPostContainer.put(threadId,  postIdSet);
//			}
//			else
//			{
//				lastPostContainer.put(threadId, new HashSet<String>());
//			}
			lastPostSet.add(threadId+".json/"+postId);
			//System.out.println(threadId+"/"+postId);
		}
		
		System.out.println(lastPostSet.size());
		
		//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//		"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};

		
		
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
		
			String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
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
					
					for(int i=0; i<listitems.size(); i++)
					{
						JSONObject listitem = (JSONObject) listitems.get(i);
						String postID = listitem.get("id").toString().trim();
						
						postID = fileKey + "/" + postID;
						
						//System.out.println(postID);
						
						totalPostCount++;
						if(lastPostSet.contains(postID))lastPostCount++;
						
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
								while(token1.hasMoreTokens())
								{
									String word = token1.nextToken().toString().trim();
									//if(word.endsWith("."))word = word.substring(0, word.length()-1);
																
									if(wordMap.containsKey(word))
									{
										int count = wordMap.get(word);
										count++;
										wordMap.put(word, count);
									}
									else wordMap.put(word, 1);
									
									if(lastPostSet.contains(postID))
									{
										if(lastPostWordMap.containsKey(word))
										{
											int count = lastPostWordMap.get(word);
											count++;
											lastPostWordMap.put(word, count);
										}
										else lastPostWordMap.put(word, 1);
									}
									
									if(!previousWord.equals(""))
									{
										String currBigram = previousWord + "+" + word;
										
										if(bigramMap.containsKey(currBigram))
										{
											int count = bigramMap.get(currBigram);
											count++;
											bigramMap.put(currBigram, count);
										}
										else bigramMap.put(currBigram, 1);
										
										if(lastPostSet.contains(postID))
										{
											if(lastPostBigramMap.containsKey(currBigram))
											{
												int count = lastPostBigramMap.get(currBigram);
												count++;
												lastPostBigramMap.put(currBigram, count);
											}
											else lastPostBigramMap.put(currBigram, 1);
										}
										
									}
									previousWord = word;
									//totalWordCount++;
								}
							}
							
						}
					}
					
				}
				
				
			}
		}
		
		System.out.println(wordMap.size() + " " + lastPostWordMap.size());
		
		System.out.println(bigramMap.size() + " " + lastPostBigramMap.size());
		
		double Plp = ((double)lastPostCount)/totalPostCount;
		
		Iterator it = wordMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			 Map.Entry pair = (Map.Entry)it.next();
			 String word = pair.getKey().toString();
			 int count = Integer.parseInt(pair.getValue().toString());
			 
			 if(count<100)
			 {
				 //wordMap.remove(word);
				 lastPostWordMap.remove(word);
				 it.remove();
			 }
		}
		
		it = bigramMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			 Map.Entry pair = (Map.Entry)it.next();
			 String bigram = pair.getKey().toString();
			 int count = Integer.parseInt(pair.getValue().toString());
			 
			 if(count<10)
			 {
				 //wordMap.remove(word);
				 lastPostBigramMap.remove(bigram);
				 it.remove();
			 }
		}
		
		File file = new File("/home/farigys/Health_Board/LastPostWordPMI1BNS.csv");//user info cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		File file1 = new File("/home/farigys/Health_Board/LastPostWordPMIBigramBNS.csv");//user info cache
		// if file doesnt exists, then create it
		if (!file1.exists()) {
			file1.createNewFile();
		}

		FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		
		//TreeMap<Double, String> sortedPMImap = new TreeMap<Double, String>();
		
		it = lastPostWordMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			 Map.Entry pair = (Map.Entry)it.next();
			 String word = pair.getKey().toString();
			 int lastPCount = Integer.parseInt(pair.getValue().toString());
			 int totalCount = wordMap.get(word);
			 
			 double Plpw = lastPCount/((double)totalCount);
			 
			 double pmiw = Math.log10(Plpw/Plp);
			 
			 //sortedPMImap.put(pmiw, word);
			 
			 if(word.length()>1 && word.matches("\\D+"))bw.write(word + ": " + pmiw + "\n");
		}
		
		it = lastPostBigramMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			 Map.Entry pair = (Map.Entry)it.next();
			 String bigram = pair.getKey().toString();
			 int lastPCount = Integer.parseInt(pair.getValue().toString());
			 int totalCount = bigramMap.get(bigram);
			 
			 double Plpw = lastPCount/((double)totalCount);
			 
			 double pmiw = Math.log10(Plpw/Plp);
			 
			 //sortedPMImap.put(pmiw, word);
			 
			 if(bigram.length()>1 && bigram.matches("\\D+") && bigram.contains("+"))bw1.write(bigram + ": " + pmiw + "\n");
		}
		
//		it = sortedPMImap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			 Map.Entry pair = (Map.Entry)it.next();
//			 double pmiw = Double.parseDouble(pair.getKey().toString());
//			 String word = pair.getValue().toString();
//			 
//			 if(word.length()>1 && word.matches("\\D+"))bw.write(word + ": " + pmiw + "\n");
//		}
		bw.close();
		bw1.close();
	}
}
