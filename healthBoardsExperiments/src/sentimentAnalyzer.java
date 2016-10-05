import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


public class sentimentAnalyzer {
	public static void main(String[] args) throws IOException, ParseException {
		//HashMap<String, HashSet<String>> lastPostContainer = new HashMap<String, HashSet<String>>();
		String conditions[] = {"relationship-health"};
		//String[] conditions = {"alzheimers-disease-dementia"};
		//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//		"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
		//String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//		"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//		"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		
		for(int c=0; c<conditions.length; c++)
		{
			Properties props = new Properties();
	    	props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	    	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			File file = new File(root + "/avgSentimentOfPosts.txt");//PsychoLinguistic category cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				//if(n%10 == 0)
				
				String filename = listOfFiles[n].getName();
				System.out.println(n + " done: " + filename);
				String fileKey = condition + "/" + filename;
				
				//if(lastPostContainer.containsKey(fileKey))
				//{
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
						
						int totalSentiment = 0;
						double count = 0;
						
						Annotation annotation = pipeline.process(contents);
			            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			            	//System.out.print(sentence);
			            	count++;
			                Tree tree = sentence.get(SentimentAnnotatedTree.class);
			                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
			                //System.out.println(":" + (sentiment));
			                totalSentiment += sentiment;
			            }
			            
			            double avgSentiment = totalSentiment/count;
			            
			            bw.write(postID + ":" + avgSentiment + "\n");
					}
				}
			//}
			bw.close();
		}
	}
}
