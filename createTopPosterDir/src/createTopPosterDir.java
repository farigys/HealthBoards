import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import java.util.Iterator;

//creates the top20poster folder and stores all the posts of the top posters

public class createTopPosterDir {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		 
	 	String root = "/mnt/docsig/storage/daily-strength/";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        //for (int m = 0; m < listOfFolders.length; m++) 
        {
        	//if (listOfFolders[m].isDirectory())
        	{
        		//String foldername = listOfFolders[m].getName();
        		String foldername = "Infertility";
        		//if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
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
	            	new File(rootDir+"/top20Poster/"+poster).mkdir();
	            	tokenizer.nextToken();
	            	while(tokenizer.hasMoreTokens())
	            	{
	            		String tempPost = tokenizer.nextToken();
	            		StringTokenizer token = new StringTokenizer(tempPost,"-");
	            		String postID = token.nextToken();
	            		String fileName = "Problem_"+postID+".txt";
	    		    	File  fsource = new File(rootDir+"/Texts.parsed/"+fileName);
	    		    	//System.out.println("Dhuksi");
	    	            FileInputStream fisource = new FileInputStream(fsource); 
	    	            //System.out.println("Dhuksi");
	    	            BufferedReader readersource = new BufferedReader(new InputStreamReader(fisource));
			            File writefile = new File(rootDir+"/top20Poster/"+poster+"/"+fileName);
			         	if (!writefile.exists()) {
			        		writefile.createNewFile();
			        	}
			        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
			        	BufferedWriter bw = new BufferedWriter(fw);
			        	String writer;
			        	while((writer=readersource.readLine())!=null)
			        	{
			        		bw.write(writer+"\n");
			        	}
			        	bw.close();
			        	readersource.close();
	            		
	            	}
	            }
	            
        	}
     }
 }


}
