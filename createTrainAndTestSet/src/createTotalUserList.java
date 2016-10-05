import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;


//creates total user list and divides it into train and test set

public class createTotalUserList {
	
	 public static void main(String[] args) throws FileNotFoundException, IOException {
	    	String root = "/mnt/docsig/storage/daily-strength/";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
	        TreeSet<Integer> userset = new TreeSet<Integer>();
	        for (int m = 0; m < listOfFolders.length; m++) 
	        {
	        	if (listOfFolders[m].isDirectory())
	        	{
	        		String foldername = listOfFolders[m].getName();
	        		//String foldername = "Bone-Cancer";
	        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
	        		System.out.println("Now in: "+foldername);
	        		String rootDir = root+foldername;
	        		File f = new File(rootDir+"/userPostList.txt");
                    FileInputStream fis = new FileInputStream(f); 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    while((line = reader.readLine())!=null)
                    {
                    	StringTokenizer token = new StringTokenizer(line, ",");
                    	//token.nextToken();
                    	String userID = token.nextToken();
                    	//System.out.println(userID);
                    	userset.add(Integer.parseInt(userID));
                    }
                    
	        		
	        	}
	        }
	        ArrayList<Integer> userSet = new ArrayList<Integer>(userset); 
	       
	        
	        File writefile = new File(root+"totalPosterList.txt");
	        FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
        	for(int i=0; i<userSet.size();i++)
        	{
        		bw.write(userSet.get(i)+"\n");
        	}
        	bw.close();
        	Collections.shuffle(userSet);
        	writefile = new File(root+"trainList.txt");
	        fw = new FileWriter(writefile.getAbsoluteFile());
        	bw = new BufferedWriter(fw);
        	for(int i=0;i<30000;i++)
        	{
            	bw.write(userSet.get(i)+"\n");
           	}
        	bw.close();
        	
        	writefile = new File(root+"testList.txt");
	        fw = new FileWriter(writefile.getAbsoluteFile());
        	bw = new BufferedWriter(fw);
        	for(int i=30000;i<userSet.size();i++)
        	{
            	bw.write(userSet.get(i)+"\n");
           	}
        	bw.close();
//	        
	 }

}
