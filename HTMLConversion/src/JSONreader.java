import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class JSONreader {
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		JSONParser parser = new JSONParser();
		
		 MaxentTagger tagger = new MaxentTagger(
	                "/home/farigys/Health_Board/stanford-postagger-2011-04-20/models/bidirectional-distsim-wsj-0-18.tagger");
		 
		 String condition = "arachnoiditis";
		 
		 String root = "/home/farigys/Health_Board/" + condition + "/JSON/";
		 
		 File folder = new File(root);
			
		 File[] listOfFiles = folder.listFiles();
		 
		 for(int n=0; n<listOfFiles.length; n++)
		 {
			 
			 String filename = listOfFiles[n].getName();
			 System.out.println(filename);
			 Object obj = parser.parse(new FileReader(root + filename));
				JSONObject jsonObject = (JSONObject) obj;

				// loop array
				JSONArray listitems = (JSONArray) jsonObject.get("items");
				
				JSONArray newlistitems = new JSONArray();
				
				int i =0;
				
				for(i=0; i<listitems.size(); i++)
				{
					JSONObject listitem = (JSONObject) listitems.get(i);
							
					JSONArray contents = (JSONArray) listitem.get("content");
					
					//System.out.println(contents.size());
					
					JSONArray posTaggedContents = new JSONArray();
					
					for(int contentCount=0; contentCount<contents.size(); contentCount++)
					{
						String tempContent = contents.get(contentCount).toString();
						if(tempContent.startsWith("text: "))
						{
							tempContent = tempContent.substring(6, tempContent.length());
							String tagged = tagger.tagString(tempContent);
							posTaggedContents.add("text: " + tagged.trim());
						}
						else
						{
							tempContent = tempContent.substring(10, tempContent.length());
							String tagged = tagger.tagString(tempContent);
							posTaggedContents.add("Quotation: " + tagged.trim());
						}
					}
					listitem.put("postaggedcontent", posTaggedContents);
					newlistitems.add(listitem);
				}
				jsonObject.put("items", newlistitems);
				
				FileWriter file = new FileWriter(root + filename);
				file.write(jsonObject.toJSONString());
				file.flush();
				file.close();
		 }
		 
		//try {
			//Object obj = parser.parse(new FileReader(args[0]));
			

		//} catch (FileNotFoundException e) {
			//e.printStackTrace();
		//} catch (IOException e) {
			//e.printStackTrace();
		//} catch (ParseException e) {
			//e.printStackTrace();
		//}
	}

}
