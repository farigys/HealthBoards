import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONwriter {
	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		String root = "/home/farigys/Health_Board/depression/JSON/";
		String filename = "287752-so-annoyed-tom-cruise.html.json";
		 System.out.println(filename);
		 Object obj = parser.parse(new FileReader(root + filename));
			JSONObject jsonObject = (JSONObject) obj;

			// loop array
			JSONArray listitems = (JSONArray) jsonObject.get("items");
			
			System.out.println(listitems.size());
			
			int i =0;
			
			for(i=0; i<listitems.size(); i++)
			{
				JSONObject listitem = (JSONObject) listitems.get(i);
				
				JSONObject actor = (JSONObject) listitem.get("actor");
				
				String name = actor.get("name").toString();
				
				if(name.equals("Blue102"))
				{
					JSONArray contents = (JSONArray) listitem.get("content");
					System.out.println(contents);
				}
				
//				JSONArray contents = (JSONArray) listitem.get("content");
//				
//				//System.out.println(contents.size());
//				
//				JSONArray posTaggedContents = (JSONArray) listitem.get("postaggedcontent");
//				
//				for(int contentCount=0; contentCount<contents.size(); contentCount++)
//				{
//					String tempContent = contents.get(contentCount).toString();
//					
//					System.out.println(tempContent);
//					
//				}

//				for(int contentCount=0; contentCount<posTaggedContents.size(); contentCount++)
//				{
//					String tempContent = posTaggedContents.get(contentCount).toString();
//					
//					System.out.println(tempContent);
//					
//				}
				System.out.println("_________________________________________________________________________________");
			}
//		JSONObject obj = new JSONObject();
//		obj.put("Name", "crunchify.com");
//		obj.put("Author", "App Shah");
// 
//		JSONArray company = new JSONArray();
//		company.add("Compnay: eBay");
//		company.add("Compnay: Paypal");
//		company.add("Compnay: Google");
//		obj.put("Company List", company);
//		
//		obj.put("URL", "http://www.google.com");
// 
//		// try-with-resources statement based on post comment below :)
//		try {
//
//			FileWriter file = new FileWriter("/home/farigys/test.json");
//			file.write(obj.toJSONString());
//			file.flush();
//			file.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.print(obj);
	}
}
