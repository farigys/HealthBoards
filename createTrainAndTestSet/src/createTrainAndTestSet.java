

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farig
 */

//used for parsing XML files and list the poster and the date for later use

public class createTrainAndTestSet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bone-Cancer";
        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+foldername;
        		new File(rootDir+"/train").mkdir();
        		new File(rootDir+"/test").mkdir();
//                File writefile = new File(rootDir+"/userPostList.txt");
//	         	if (!writefile.exists()) {
//	        		writefile.createNewFile();
//	        	}
	        	//FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	        	//BufferedWriter bw = new BufferedWriter(fw);
	                File file = new File(rootDir+"/XML");
	                File[] listOfFiles = file.listFiles();
	                String line = new String();
	                //ArrayList<String> peopleList = new ArrayList<String>();
	                //File  fp = new File(rootDir+"/peopleLists.txt");
	                //FileInputStream fisp = new FileInputStream(fp); 
	                //BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
	                    //try {
	                    //    while ((line = readerp.readLine()) != null) {
	                    //        StringTokenizer st = new StringTokenizer(line, "\t/");
	                    //        st.nextToken();
	                    //        String people = st.nextToken();
	                    //        //System.out.println(people);
	                    //        peopleList.add(people);
	                    //    }
	                    //    } catch (IOException ex) {
	                    //  }
	                    //readerp.close();
	                //Collections.sort(peopleList);
	                
	                ////////////////////////////////////////////////////////////////////////////////////////////////
	                //TreeMap<String, ArrayList<String>> posterMap = new TreeMap<String, ArrayList<String>>();
	                for (int i = 0; i < listOfFiles.length; i++) {
	                    if (listOfFiles[i].isFile()) {
	                    	//new File(rootDir+"/train").mkdir();   //System.out.println("File " + listOfFiles[i].getName());
	                      //String person = new String();
	                      String date = new String();
	                      File f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
	                      FileInputStream fis = new FileInputStream(f); 
	                      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	                      int linecount = 0;
	                      //while((person = reader.readLine())!=null)
	                      //{
	                          //linecount++;
	                          //person = reader.readLine();
	                          //if(person.contains("<person id="))break;
	                      //}
	                      
	                      
	                      //System.out.println(person);
	                      //System.out.println(linecount);
	                      //System.out.println(person+f.getName());
	                      //if(person==null)continue;
	                      //System.out.println(person+" "+personToFind);
	                      //if(person!=null)
	                      //{
	                    	  //StringTokenizer tok = new StringTokenizer(person, "/\"");
		                     //person = tok.nextToken();
		                      //person = tok.nextToken();
		                      //person = tok.nextToken();
	                    	  //System.out.println("Dhuksi");
	                          while((date = reader.readLine())!=null)
	                            {
	                                //linecount++;
	                                //date = reader.readLine();
	                                //System.out.println(date);
	                                if(date.contains("<date>"))break;
	                            }
	                          reader.close();
	                          f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
		                      fis = new FileInputStream(f); 
		                      reader = new BufferedReader(new InputStreamReader(fis));
	                          //String filename = listOfFiles[i].getName();
	                          StringTokenizer token;// = new StringTokenizer(filename, ".");
	                          //String postname = token.nextToken();
	                          if(date!=null)
	                          {
	                              token = new StringTokenizer(date, "<>");
	                              token.nextToken();
	                              token.nextToken();
	                              date = token.nextToken();
	                              StringTokenizer token1 = new StringTokenizer(date, "/ ");
	                              token1.nextToken();
	                              token1.nextToken();
	                              String year = token1.nextToken();
	                              File writefile;
	                              if(Integer.parseInt(year)>=13)
	                              {
	                            	  	writefile = new File(rootDir+"/test/"+listOfFiles[i].getName());
		          			         	
	                              }
	                              else
	                              {
	                            	  writefile = new File(rootDir+"/train/"+listOfFiles[i].getName());
	                              }
	                              if (!writefile.exists()) {
	          			        		writefile.createNewFile();
	          			        	}
	          			        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	          			        	BufferedWriter bw = new BufferedWriter(fw);
	          			        	String writer;
	          			        	while((writer=reader.readLine())!=null)
	          			        	{
	          			        		bw.write(writer+"\n");
	          			        	}
	          			        	bw.close();
	                          }
	                          reader.close();
	                          
	                          //bw.write(people+","+postname+","+date+"\n");
	                          //System.out.println(people+","+postname+","+date);
	                          //if(posterMap.containsKey(person))
		                      //{
		                    	//  ArrayList<String> temp = posterMap.get(person);
		                    	  //temp.add(postname+","+date);
		                    	 // posterMap.put(person, temp);
		                      //}
	                          //else
	                          //{
	                        	  //ArrayList<String> temp = new ArrayList<String>();
		                    	  //temp.add(postname+","+date);
	                        	 // posterMap.put(person, temp);
	                         // }
	                      //} 
	                      
	                      
	                      
	                    }
	                   
	
	                  }
	                
//	                for(int pcount=0;pcount<peopleList.size();pcount++)
//	                {
//	                	String personToFind = peopleList.get(pcount);
//	                	if(posterMap.containsKey(personToFind))
//	                	{
//	                		ArrayList<String> temp = posterMap.get(personToFind);
//	                		for(int count = 0; count<temp.size(); count++)
//	                		{
//	                			bw.write(personToFind+","+temp.get(count)+"\n");
//	                		}
//	                	}
//	                }
	                ////////////////////////////////////////////////////////////////////////////////////////////////
	                
//	                for(int pcount=0;pcount<peopleList.size();pcount++)
//	                {
//	                    String personToFind = "/people/"+peopleList.get(pcount).toString();
//	                    //String personToFind = "/people/422111";
//	                    StringTokenizer st = new StringTokenizer(personToFind, "/");
//	                    st.nextToken();
//	                    String people = st.nextToken();
//	                    //String people = "/people/422111";
//	                    for (int i = 0; i < listOfFiles.length; i++) {
//	                    if (listOfFiles[i].isFile()) {
//	                      //System.out.println("File " + listOfFiles[i].getName());
//	                      String person = new String();
//	                      String date = new String();
//	                      File f = new File(rootDir+"/XML/"+listOfFiles[i].getName());
//	                      FileInputStream fis = new FileInputStream(f); 
//	                      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//	                      int linecount = 0;
//	                      while((person = reader.readLine())!=null)
//	                      {
//	                          //linecount++;
//	                          //person = reader.readLine();
//	                          if(person.contains("<person id="))break;
//	                      }
//	                      
//	                      StringTokenizer tok = new StringTokenizer(person, "/\"");
//	                      person = tok.nextToken();
//	                      person = tok.nextToken();
//	                      person = "/people/"+tok.nextToken();
//	                      //System.out.println(person);
//	                      //System.out.println(linecount);
//	                      //System.out.println(person+f.getName());
//	                      //if(person==null)continue;
//	                      //System.out.println(person+" "+personToFind);
//	                      if(person!=null && person.equals(personToFind))
//	                      {
//	                    	  //System.out.println("Dhuksi");
//	                          while((date = reader.readLine())!=null)
//	                            {
//	                                //linecount++;
//	                                //date = reader.readLine();
//	                                //System.out.println(date);
//	                                if(date.contains("<date>"))break;
//	                            }
//	                          String filename = listOfFiles[i].getName();
//	                          StringTokenizer token = new StringTokenizer(filename, "-");
//	                          String postname = token.nextToken();
//	                          if(date!=null)
//	                          {
//	                              token = new StringTokenizer(date, "<>");
//	                              token.nextToken();
//	                              token.nextToken();
//	                              date = token.nextToken();
//	                          }
//	                          
//	                          bw.write(people+","+postname+","+date+"\n");
//	                          //System.out.println(people+","+postname+","+date);
//	                      }                      
//	                      reader.close();
//	                    }
//	                   
//	
//	                  }
//	                }
	                
	               //bw.close();
        	}
        
    }
    }
    
}