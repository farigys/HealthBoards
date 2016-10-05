import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import java.util.Iterator;

//manage posts according to timeline

public class topPosterRenamer {

public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bone-Cancer";
        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+"/"+foldername;
		    	File  fp = new File(rootDir+"/top20Poster.txt");
		    	//System.out.println("Dhuksi");
	            FileInputStream fisp = new FileInputStream(fp); 
	            //System.out.println("Dhuksi");
	            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
	            String line = "";
	            while((line = readerp.readLine())!=null)
	            {
	            	StringTokenizer tokenizer = new StringTokenizer(line,",");
	            	String poster = tokenizer.nextToken();
	            	//new File(rootDir+"/top20Poster/"+poster).mkdir();
	            	String currDir = rootDir+"/top20Poster/"+poster+"/";
	            	
	            	File  fp1 = new File(rootDir+"/userPostList.txt");
	                FileInputStream fisp1 = new FileInputStream(fp1); 
	                BufferedReader readerp1 = new BufferedReader(new InputStreamReader(fisp1));
	                String line1 = "";
	                int flag = 0;
	                while((line1 = readerp1.readLine())!=null)
	                {
	                	StringTokenizer token = new StringTokenizer(line1,",");
	                	String posterID = token.nextToken();
	                	String postName = token.nextToken();
	                	String date = token.nextToken();
	                	if(posterID.equals(poster))
	                	{
	                		flag = 1;
	                		StringTokenizer tokenDate = new StringTokenizer(date, "/ ");
	                		String month = tokenDate.nextToken();
	                		String day = tokenDate.nextToken();
	                		String year = tokenDate.nextToken();
	                		String formatdate = year+month+day;
	                		
	                		StringTokenizer tokenPost = new StringTokenizer(postName, "-");
	                		String postID = tokenPost.nextToken();
	                		
	                		//System.out.println("Old name: Problem_"+postID+", new name: "+formatdate+"_Problem_"+postID);
	                		
	                		File oldfile =new File(currDir+"Problem_"+postID+".txt");
	                		File newfile =new File(currDir+formatdate+"_Problem_"+postID+".txt");
	                		oldfile.renameTo(newfile);
	                		//ArrayList.add();
	                		continue;
	                	}

	                	if(flag == 1)break;
	                	
	                }
	                readerp1.close();
	            	
	            }
	            readerp.close();
        	}
     }

   	
		}
}
