import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.StringTokenizer;


public class createReplyCountCache {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        
        File writefile = new File(root+"/replyCountCache.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
        
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bone-Cancer";
        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root+"/"+foldername+"/XML/";
        		File files = new File(rootDir);
        		File[] listoffiles = files.listFiles();
        		
        		for(int n = 0; n < listoffiles.length; n++)
        		{
        			if(listoffiles[n].isDirectory())continue;
        			if(n%100 == 0)System.out.println(n + " posts done");
        			String filename = listoffiles[n].getName();
        			StringTokenizer token = new StringTokenizer(filename, "-");
        			String postId = token.nextToken();
        			File inputFile = new File(rootDir+filename);
        	        FileInputStream fisi = new FileInputStream(inputFile);
        	        BufferedReader readeri = new BufferedReader(new InputStreamReader(fisi));
        	        int flag = 0;
        	        int greply = 0, sreply = 0;
        	        
        	        String posterId = "";
        	        
        	        String line = "";
        	        
        	        while((line = readeri.readLine())!=null)
        	        {
        	        	if(line.equals("</document>"))break;
        	        	if(line.contains("<person id="))
        	        	{
        	        		StringTokenizer token1 = new StringTokenizer(line, "\"/");
        	    			token1.nextToken();
        	    			token1.nextToken();
        	        		if(flag == 0)
        	        		{
        	        			//System.out.println(token1.nextToken());
        	        			posterId = token1.nextToken();
        	        			flag = 1;
        	        		}
        	        		else
        	        		{
        	        			String replierId = token1.nextToken();
        	        			if(replierId.equals(posterId))sreply++;
        	        			else greply++;
        	        		}
        	        	}
        	        }
        	        readeri.close();
        	        bw.write(postId+" "+posterId+" "+greply+" "+sreply+"\n");
        		}
        		//System.out.println(listoffiles.length);
        	}
        }
        bw.close();
        
        
        
	}
}
