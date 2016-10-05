import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class createIndex {
	 public static void main(String[] args) throws FileNotFoundException, IOException {
		 	String root = "/mnt/docsig/storage/daily-strength/";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	        
	        HashMap<String, TreeSet> index = new HashMap();
	        
	        for (int m = 0; m < listOfFolders.length; m++) 
	        {
	        	if (listOfFolders[m].isDirectory())
	        	{
	        		String foldername = listOfFolders[m].getName();
	        		//String foldername = "Bone-Cancer";
	        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.contains("userPostIndex")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.equals("userPostIndex")||foldername.contains("."))continue;
	        		System.out.println("Now in: "+foldername);
	        		String rootDir = root+foldername;
	        		new File("/home/farigys/Documents/userPostIndex").mkdir();
	        		File file = new File(rootDir+"/XML");
	                File[] listOfFiles = file.listFiles();
	                String line = new String();
	                
	                for (int i = 0; i < listOfFiles.length; i++) {
	                	if (listOfFiles[i].isFile()) {
	                		String filename = listOfFiles[i].getName();
	                		String person = null;
	                		String date = null;
	                		File f = new File(rootDir+"/XML/"+filename);
		                    FileInputStream fis = new FileInputStream(f); 
		                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	                		StringTokenizer token2 = new StringTokenizer(filename, "-.");
	                		filename = token2.nextToken();
		                    // System.out.println("opening file "+filename);
		                     while((line=reader.readLine())!=null)
		                     {
		                    	 //System.out.println(line);
		                    	 if(line.contains("<problem>"))
		                    	 {
		                    		 //System.out.println("dhuksi");
		                    		 line = reader.readLine();
		                    		 //System.out.println(line);
		                    		 StringTokenizer tok = new StringTokenizer(line, "/\"><");
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     //System.out.println(person);
				                     while(!line.contains("<date>"))
				                     {
				                    	 //System.out.println(line);
				                    	 line=reader.readLine();
				                     }
				                     //System.out.println("dhuksi");
				                     //System.out.println(line);
				                     StringTokenizer token = new StringTokenizer(line, "<>");
			                         token.nextToken();
			                         token.nextToken();
			                         date = token.nextToken();
			                      
				                     StringTokenizer tok1 = new StringTokenizer(date, " ");
				                     String subformattedDate = tok1.nextToken();
				                     StringTokenizer tok2 = new StringTokenizer(subformattedDate, "/");
				                     String month = tok2.nextToken();
				                     String day = tok2.nextToken();
				                     String year = tok2.nextToken();
				                     String formattedDate = year+month+day;
				                     String time = tok1.nextToken();
				                     String hr = Character.toString(time.charAt(0))+Character.toString(time.charAt(1));
				                     String min = Character.toString(time.charAt(3))+Character.toString(time.charAt(4));
				                     if(time.contains("pm") && !hr.equals("12"))
				                     {
				                    	 Integer hour = (Integer.parseInt(hr)+12);
				                    	 hr = hour.toString();
				                     }
				                     if(time.contains("am") && hr.equals("12"))
				                     {
				                    	 hr = "00";
				                     }
				                     time = hr+min;
				                     
				                     formattedDate = formattedDate+time;
				                     
				                     String indexInput = formattedDate+":"+foldername+":Problem"+"_"+filename;
				                     
				                     if(index.containsKey(person))
				                     {
				                    	 TreeSet<String> temp = index.get(person);
				                    	 temp.add(indexInput);
				                    	 index.put(person, temp);
				                     }
				                     else
				                     {
				                    	 TreeSet<String> temp = new TreeSet<String>();
				                    	 temp.add(indexInput);
				                    	 index.put(person, temp);
				                     }
		                    	 }
		                    	 
		                    	 if(line.contains("<reply id="))
		                    	 {
		                    		 StringTokenizer token1 = new StringTokenizer(line, "\"");
		                    		 token1.nextToken();
		                    		 String replyId = token1.nextToken();
		                    		 line = reader.readLine();
		                    		 StringTokenizer tok = new StringTokenizer(line, "/\"><");
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     person = tok.nextToken();
				                     
				                     line = reader.readLine();
				                     StringTokenizer token = new StringTokenizer(line, "<>");
			                         token.nextToken();
			                         token.nextToken();
			                         date = token.nextToken();
			                      
				                     StringTokenizer tok1 = new StringTokenizer(date, " ");
				                     String subformattedDate = tok1.nextToken();
				                     StringTokenizer tok2 = new StringTokenizer(subformattedDate, "/");
				                     String month = tok2.nextToken();
				                     String day = tok2.nextToken();
				                     String year = tok2.nextToken();
				                     String formattedDate = year+month+day;
				                     String time = tok1.nextToken();
				                     StringTokenizer token3 = new StringTokenizer(time, ":");
				                     String hr = token3.nextToken();
				                     if(hr.length()==1)hr = "0"+hr;
				                     String min = token3.nextToken();
				                     min = Character.toString(min.charAt(0))+Character.toString(min.charAt(1));
				                     if(time.contains("pm") && !hr.equals("12"))
				                     {
				                    	 Integer hour = (Integer.parseInt(hr)+12);
				                    	 hr = hour.toString();
				                     }
				                     if(time.contains("am") && hr.equals("12"))
				                     {
				                    	 hr = "00";
				                     }
				                     time = hr+min;
				                     
				                     formattedDate = formattedDate+time;
				                     
				                     String indexInput = formattedDate+":"+foldername+":Reply"+"_"+filename+"_"+replyId;
				                     
				                     if(index.containsKey(person))
				                     {
				                    	 TreeSet<String> temp = index.get(person);
				                    	 temp.add(indexInput);
				                    	 index.put(person, temp);
				                     }
				                     else
				                     {
				                    	 TreeSet<String> temp = new TreeSet<String>();
				                    	 temp.add(indexInput);
				                    	 index.put(person, temp);
				                     }
		                    		 
		                    	 }
		                    	 
		                    	 
		                     }
		                     reader.close();
	                	}
	                	
	                	
	                }
	                //System.out.println(index.size());
	                	                
	                //System.out.println(index);
	        	}
	        }
	        
	        Iterator iter = index.entrySet().iterator();
            while(iter.hasNext())
            {
            	Map.Entry pairs = (Map.Entry)iter.next();
            	String poster = pairs.getKey().toString();
            	
            	File writefile = new File("/home/farigys/Documents/userPostIndex/"+poster+".txt");
	         	if (!writefile.exists()) {
	        		writefile.createNewFile();
	        	}
	        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	        	BufferedWriter bw = new BufferedWriter(fw);
	        	
	        	TreeSet<String> temp = (TreeSet)pairs.getValue();
	        	
	        	Iterator iter1 = temp.iterator();
	        	
	        	while(iter1.hasNext())
	        	{
	        		bw.write(iter1.next().toString()+"\n");
	        	}
	        	bw.close();
             	
            }
	 }

}
