

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

	public class HTMLconverter {

	  public static void main(String[] args) throws IOException, ClassNotFoundException {
		  	//String[] conditions = {"diet-nutrition", "exercise-fitness"};
		  	//String[] conditions = {"abuse-support"};
			//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders"};
			//String[] conditions = {"grief-loss"};
			//String[] conditions =	{ "vitamins-supplements","obesity", "weight-loss"};
		  String[] conditions = {"relationship-health","alzheimers-disease-dementia"};
		  //String[] conditions = {"relationship-health","alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
			//		"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
				//"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		   //String[] conditions = {"depression"};
		  //String[] conditions = {"scoliosis"};
		  
		  MaxentTagger tagger = new MaxentTagger(
	                "/home/farigys/Health_Board/stanford-postagger-2011-04-20/models/bidirectional-distsim-wsj-0-18.tagger");
	 
		  
			for(int c=0; c<conditions.length; c++)
			{
				String condition = conditions[c];
				
				System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
				
				String root = "/home/farigys/Health_Board/" + condition;
				
				String rootDir = root + "/HTML/";
				
				File folder = new File(rootDir);
				
				File[] listOfFiles = folder.listFiles();
				
				HashMap<String, ArrayList<String>> fileMap = new HashMap<String, ArrayList<String>>();
				
				File outputFolder = new File(root + "/JSON/");
				
				if(!outputFolder.exists())outputFolder.mkdir();
				
				///////////////Creating file name map which stores all the name of the subpages in a arraylist mapped to the id////////////////////
				
				for(int n=0; n<listOfFiles.length; n++)
				{
					if(n%100 == 0)System.out.println(n + " files done");
					//String filename = "/home/farigys/Health_Board/diet-nutrition/JSON/139445-atkins-diet-safe.html";
					String filename = listOfFiles[n].getName();
					StringTokenizer nameToken = new StringTokenizer(filename, "-");
					String fileid = nameToken.nextToken();
					if(fileMap.containsKey(fileid))
					{
						ArrayList<String> tempList = fileMap.get(fileid);
						tempList.add(filename);
						fileMap.put(fileid, tempList);
					}
					else
					{
						ArrayList<String> tempList = new ArrayList<String>();
						tempList.add(filename);
						fileMap.put(fileid, tempList);
					}
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				int n = 0;
				
				for (ArrayList<String> fileList : fileMap.values()) 
				{
					JSONObject obj = new JSONObject(); //the whole JSON object
					
					JSONArray postList = new JSONArray(); //holds all the individual posts
					  
					Document doc;
					
					int totalCount = 0;
					

					int count = 0;
					
					Collections.sort(fileList);
					
					String outputfilename = fileList.get(fileList.size()-1);
					
					//if(!outputfilename.contains("139445-atkins-diet-safe"))continue; //for testing purpose
					
					String threadUrl = "http://www.healthboards.com/boards/" + condition + "/" + outputfilename;
					
					String initUrl = "";
					
					obj.put("@context", "HealthBoards"); /////////////////////////////
					obj.put("type", "Object");/////////////////////////////
					
					
					for(int l=fileList.size()-1; l>=0; l--)
					{
						String filename = fileList.get(l);
						//String rootDir = "/home/farigys/";
						//String filename = "934737-pain-relief-scoliosis.html";
						
						//System.out.println("Now Processing: " + filename);
						
						File input = new File(rootDir + filename);
						doc = Jsoup.parse(input, "UTF-8", ""); //parsing html
						
						
							
						// get posts
						Elements links = doc.select("div[id~=(post|edit).[0-9]+]");//looking for the divs that contains info
						
						totalCount += links.size();//total number of posts in the thread
						
						
									
						
						
						
						//Elements links = doc.select("a[href]");
						for (Element link : links) {

							// get the value from href attribute
							
							int editedFlag = 0, guestFlag = 0;
							
							JSONObject singlePost = new JSONObject();
											
							count++;
							
							if(count == 1)
							{
								singlePost.put("type", "init");
							}
							else
							{
								singlePost.put("type", "reply");
							}
												
							String id = link.attr("id").substring(4, link.attr("id").length());//id of the post
							
							singlePost.put("id", id);
							
							String url = "http://www.healthboards.com/boards/" + condition + "/" + filename + "#post" + id;//url of the post
							
							if(count == 1)
							{
								initUrl = url;
							}
							
							//looking for the contents of the div- time and actor info
							
							Elements listTd = link.select("td");
							Element td = listTd.get(0);
							String published = td.text();
							
							String name = "", actorUrl = "", status = "", location = "", image = "", actorTag = "", startTime = "", gender = "", posts = "", blogs = "";
							//System.out.println(td.text());
							
							td = listTd.get(1);///can use for sequence number- no need right now
							
							
							
							td = listTd.get(2);
							
							try{
								gender = td.select("font").get(0).text();
								gender = gender.substring(1, gender.length()-1);
							}
							catch(Exception e)
							{
								gender = "";
							}
							
							
							Elements divs = td.select("div");
							
							//System.out.println(divs.size());
							
							int flag = 0;
							
							for(int i=0; i<divs.size(); i++)
							{
								Element tempdiv = divs.get(i);
								Elements divComp = tempdiv.select("div");
								
								//System.out.println("size: "+divComp.size());
								
								//used to eliminate redundancy in user info
								
								if(divComp.size() == 1 && !tempdiv.text().equals(""))
								{
									//System.out.println(tempdiv.text());
									
									if(i==0)
									{
										name = tempdiv.text();
									}
									else
									{
										Elements imgdiv = tempdiv.select("img");
										if(imgdiv.size()>0)
										{
											Element img = imgdiv.get(0);
											image = img.attr("src");
											continue;
										}
										if(tempdiv.text().contains("Join Date"))startTime = tempdiv.text(); 
										else if(tempdiv.text().contains("Location"))location = tempdiv.text();
										else if(tempdiv.text().contains("Posts"))posts = tempdiv.text(); 
										else if(tempdiv.text().contains("Blog Entries"))blogs = tempdiv.text();
										else
										{
											if(i==1)status = tempdiv.text();
										}
										
										
									}
									
								}
								//System.out.println(i+": "+tempdiv.text());
							}
							
							Elements emoticon = link.select("img[src~=[A-Za-z0-9.-:/]+(icons/icon)[0-9]+[A-Za-z0-9.-]*]"); 
							
							String icon = "";
							
							if(emoticon.size()>0)
							{
								icon = emoticon.get(0).attr("alt");
							}
							
							singlePost.put("icon", icon);
							
							//
							//System.out.println("\nlink : " + link);
							//System.out.println("text : " + link.text());
							
							//printing actor attrs
							
							//name = name.replace(' ', '-');
							
							if(guestFlag == 0)actorUrl = "http://www.healthboards.com/boards/members/"+name+".html";
							
							//if(!location.equals(""))location = location.substring(10, location.length());
							
							if(!location.equals(""))
							{
								StringTokenizer locationToken = new StringTokenizer(location, ":");
								locationToken.nextToken();
								if(locationToken.hasMoreTokens())location = locationToken.nextToken().trim();
								else location = "";
							}
							
							if(!startTime.equals(""))startTime = startTime.substring(11, startTime.length());
							
							if(!posts.equals(""))posts = posts.substring(7, posts.length());
							
							if(!blogs.equals(""))blogs = blogs.substring(14, blogs.length());
							
							
							td = listTd.get(3);
							
							String title = "";
							
							try{
								title = td.select("strong").get(0).text();
							}catch(Exception e)
							{
								title = "";
							}
							
							
							
							
							singlePost.put("title", title);
							
							singlePost.put("published", published);
							
							String updated = "", updater = "";
							
							//JSONObject updated = new JSONObject();
												
							Elements updateElement = link.select("em");
							
							if(updateElement.size() > 0)
							{
								editedFlag = 1;
								
								String update = link.select("em").get(0).text();
								
								StringTokenizer token = new StringTokenizer(update, ";");
								
								String updaterTemp = token.nextToken().toString();
								
								//String updater = "";
								
								updated =  token.nextToken().toString();
								
								StringTokenizer token1 = new StringTokenizer(updaterTemp, " ");
								
								int tokenCount = 1;
								
								while(token1.hasMoreTokens())
								{
									String temp = token1.nextToken();
									
									if(temp.equals("Last") || temp.equals("edited") || temp.equals("by"))continue;
									
									updater+=temp;
									
									if(tokenCount<token1.countTokens())updater.concat("-");
									
									tokenCount++;
								}
								
								//String updaterUrl = "http://www.healthboards.com/boards/members/"+updater+".html";
								
								//updated.put("time", updatedTime);
								
								//JSONObject updaterActor = new JSONObject();
								
								//updaterActor.put("name", updater);
								
								//updaterActor.put("url", updaterUrl);
								
								//updated.put("actor", updaterActor);
							}
							
							singlePost.put("updated", updated);
							
							singlePost.put("updater", updater);
							
							singlePost.put("url", url);
							

							JSONObject actor = new JSONObject();
							
							actor.put("name", name);
							actor.put("url", actorUrl);
							actor.put("status", status);
							actor.put("location", location);
							actor.put("image", image);
							actor.put("startTime", startTime);
							actor.put("gender", gender);
							actor.put("posts", posts);
							actor.put("blogs", blogs);
							
							singlePost.put("actor", actor);			
							
							//to find contents and quotes
							Element contentElement = link.select("div[id~=post_message_"+id + "]").get(0);
							
							HashSet<String> InReplyTo = new HashSet<String>();
							
							String content = contentElement.text();
							
							//String content = ""; //testing purpose
							
							Elements anchorDiv = contentElement.select("td[style~=border:1px inset]");
							
							JSONArray contents = new JSONArray();
							
							if(anchorDiv.size()>0)
							{
								for (int p=0; p<anchorDiv.size(); p++)
								{
									try{
										Element anchor = anchorDiv.get(p).select("a").get(0);

										InReplyTo.add(anchor.attr("href"));

									}catch(Exception e)
									{
										//e.printStackTrace();
									}
									//try{								
									String quote = anchorDiv.get(p).text();
									//content += "%%%" + quote;
									String tempcontent = content.replace(quote, "%%%%%%%%");
									//StringTokenizer quoteToken = new StringTokenizer(tempcontent, "%%%%%%%%");
									
									String[] parts = tempcontent.split("%%%%%%%%");
									
									int tokenCount = parts.length;
									
									if(tokenCount >=2)
									{
										String text = parts[0];
										if(!text.equals("Quote: "))contents.add("text: " + text.trim());
										contents.add("Quotation: " + quote.trim());
										content = parts[1];
									}
									
									else if(tokenCount == 1)
									{
										String text = parts[0];
										contents.add("text: " + text.trim());
										contents.add("Quotation: " + quote.trim());
										content = "";
									}
									else if(tokenCount == 0)
									{
										contents.add("Quotation: " + quote.trim());
										content = "";
									}
									
									
									//if(quoteToken.countTokens()>=2)content = quoteToken.nextToken() + "#####" + quote + "#####" + quoteToken.nextToken();
									//else content = 	content + "#####"+quote+"#####";
								}
								//content = contentElement.text();   //for testing purposes
									
							}
							
							//else 
							{
								contents.add("text: " + content.trim());
							}
							
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
							
							singlePost.put("content", contents);
							singlePost.put("postaggedcontent", posTaggedContents);
							
							
							if(count>1)InReplyTo.add(initUrl);
							
							ArrayList<String> inReplyTo = new ArrayList<String>(InReplyTo);
							
							
							
							//to find hugs and thanks
							ArrayList<String> gratitude = new ArrayList<String>();
							
							Elements gratitudeElements = link.select("div[id~=post_(hugs|thanks)_box_"+id);
							
							for (Element gratitudeElement : gratitudeElements)
							{
								Elements gratTextList = gratitudeElement.select("div");
								if(gratTextList.size()>2)
								{
									//System.out.println("TESTING "+gratTextList.get(2).text());
									String grat = gratTextList.get(2).text();
									String gratStore = "";
									
									StringTokenizer gratToken = new StringTokenizer(grat, ":");
									String commonpart = "", userpart = "";
									
									if(gratToken.countTokens()>=3)
									{
										commonpart = gratToken.nextToken() + ":" + gratToken.nextToken();
										userpart = gratToken.nextToken();
									}
									else
									{
										commonpart = gratToken.nextToken();
										if(gratToken.hasMoreTokens())userpart = gratToken.nextToken();
										else userpart = "unknown (00-00-0000)";
									}
									
																
									StringTokenizer tokengrat = new StringTokenizer(userpart, ",");
									commonpart = commonpart.trim();
									
									ArrayList<String> gratUsers = new ArrayList<String>();
									
									while(tokengrat.hasMoreTokens())
									{
										gratUsers.add(tokengrat.nextToken());
									}
									
									if(commonpart.contains("hug"))
									{
										for(int x=0; x<gratUsers.size(); x++)
										{
											gratStore = gratUsers.get(x).replace(name, "");
											gratitude.add("hug:"+gratStore.trim());
										}
									}
									else if(commonpart.contains("Thank You"))
									{
										for(int x=0; x<gratUsers.size(); x++)
										{
											gratStore = gratUsers.get(x).replace(name, "");
											gratitude.add("Thank You:"+gratStore.trim());
										}
									}
									
								}
								
							}
							
							//System.out.println(gratitude);
							
							
							
							
							
							
							JSONArray gratitudeList = new JSONArray();
							
							//System.out.println(gratitude);
							
							for(int j=0; j<gratitude.size(); j++)
							{
								String tempGrat = gratitude.get(j), gratType;
								
								if(tempGrat.contains("Thank You"))gratType = "thank";
								else gratType = "hug";
								
								
								
								
								StringTokenizer token2 = new StringTokenizer(tempGrat, ":");
								token2.nextToken();
								String gratUserAndTime = token2.nextToken();
								StringTokenizer token3 = new StringTokenizer(gratUserAndTime, "(");
								String gratUserName = token3.nextToken().trim();
								//gratUserName = gratUserName.replace(' ', '-');
								String gratTime = token3.nextToken();
								gratTime = gratTime.substring(0, gratTime.length()-1);
								
								JSONObject gratItem = new JSONObject();
								gratItem.put("type", gratType);
								gratItem.put("id", id);
								gratItem.put("published", gratTime);
								
								
								gratItem.put("grat actor", gratUserName);
								
								gratitudeList.add(gratItem);
							}
							
							singlePost.put("gratitude", gratitudeList);
							
							JSONArray replyList = new JSONArray();
							
							for(int m=0; m<inReplyTo.size(); m++)
							{
								replyList.add(inReplyTo.get(m));
							}
							
							singlePost.put("inReplyTo", replyList);
							
							
							
							//System.out.println("\"name\": "+name);
							//System.out.println("\"url\": "+actorUrl);
							//System.out.println("\"status\": "+status);
							//.out.println("\"location\": "+location);
							//System.out.println("\"image\": "+image);
							//System.out.println("\"tag\": "+actorTag);
							//System.out.println("\"startTime\": "+startTime);
							//System.out.println("\"gender\": "+gender.substring(1, gender.length()-1));
							//System.out.println("\"posts\": "+posts);
							//System.out.println("\"content\": " + content);
							//for(int j=0; j<gratitude.size(); j++)
							//{
							//	System.out.println("\"gratitude\": " + gratitude.get(j));
							//}
							
							
							//System.out.println();
							Element tagSearcher = link.select("td[id~=td_(post|edit)_[0-9]+]").get(0);
							Elements divListForTags = tagSearcher.select("div");
							String tagDiv = "";
							if(editedFlag == 0)tagDiv = divListForTags.get(divListForTags.size()-1).text();
							else tagDiv = divListForTags.get(divListForTags.size()-2).text();
							//System.out.println("tag: "+tagDiv);
							if(tagDiv.contains("__________________"))
							{
								//System.out.println("tag: "+tagDiv);
								tagDiv = tagDiv.replace("__________________", "");
							}
							else tagDiv = "";
							
							singlePost.put("tag", tagDiv);
							
							postList.add(singlePost);

						}
						int totalItems = count;
						
						//System.out.println(count);
						
						
					}
					
					obj.put("totalItems", totalCount);
					obj.put("threadURL", threadUrl);
					obj.put("items", postList);
					
					FileWriter file = new FileWriter(root + "/JSON/" + outputfilename + ".json");
					file.write(obj.toJSONString());
					file.flush();
					file.close();
					
					//System.out.println(obj);
					

					
				}
				
				
				System.out.println(condition + " done\n________________________________________________________");	
				
				
				
			}
			
			

	  }
	  
	  
	
}

class filenamecomparator implements Comparator<String>
{
	public int compare(String s1, String s2) {
		int pageno1 = 0, pageno2 = 0;
		StringTokenizer token = new StringTokenizer(s1, "-.");
		for(int i=0; i<token.countTokens()-2; i++)
		{
			pageno1 = Integer.parseInt(token.nextToken());
		}
		token = new StringTokenizer(s2, "-.");
		for(int i=0; i<token.countTokens()-2; i++)
		{
			pageno2 = Integer.parseInt(token.nextToken());
		}
		
		if(pageno1 < pageno2){
            return 1;
        } else {
            return -1;
        }
	
	}
}
	