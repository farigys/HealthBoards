import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class HealthBoardDownloader {
	public static void main(String[] args) throws FileNotFoundException, IOException, Exception
	{	
		//String[] boards = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
		//					"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
		//					"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
		//String[] boards = {"healthy-lifestyle", "healthcare-professionals", "abuse-support", "grief-loss"};
		String[] boards = {"relationship-health"};
		for(int i=0; i<boards.length; i++)
		{
			String board = boards[i];
			System.out.println("Now downloading: " + board);
			//createDownloadList(board);
			//filterList(board);
			//createRemFileList(board);
			downloadFiles(board);
			System.out.println(board + " done");
		}
			    
	}
	
	private static void createDownloadList(String board) throws IOException, InterruptedException {
		
		//System.out.println("creating download list");
		String rootDir = "/home/farigys/workspace/HealthBoardDownloader/"+board;
		
		File temp = new File(rootDir + "/FilesLists.txt");
		
		if(!temp.exists())
		{
			String command = "python /home/farigys/ListAllPages.py " + board;
			
			Process proc = Runtime.getRuntime().exec(command);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		    String line = "";
		    while((line = reader.readLine()) != null) {
		        System.out.print(line + "\n");
		    }
		    
	        proc.waitFor();   
	        
	        System.out.println("Download List Created");
		}
		
		

		
	}

	

	private static void filterList(String board) throws IOException {
		String rootDir = "/home/farigys/workspace/HealthBoardDownloader/"+board;
		File  f = new File(rootDir+"/FilesLists.txt");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    File tempFile = new File(rootDir + "/myTempFile.txt");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	    
	    String line = reader.readLine();
	    writer.write(line+"\n");
	    String prevLine = line;
	    
	    while((line = reader.readLine())!=null)
	    {	    	
	    	String prevName = null;
	    	StringTokenizer token1 = new StringTokenizer(prevLine,"/");
	    	String prevCond = null;
	    	int c1 = 0;
	    	
	    	while(token1.hasMoreTokens())
	    	{
	    		c1++;
	    		prevName = token1.nextToken().toString();
	    		if (c1 == 4)prevCond = prevName;
	    	}
	    	
	    	String currName = null;
	    	StringTokenizer token2 = new StringTokenizer(line,"/");
	    	String currCond = null;
	    	int c2 = 0;
	    	
	    	while(token2.hasMoreTokens())
	    	{
	    		c2++;
	    		currName = token2.nextToken().toString();
	    		if (c2 == 4)currCond = currName;
	    	}
	    	
	    	if(currName.equals(prevName) || !currCond.equals(board))
	    	{
	    		//prevLine = line;
	    		continue;
	    	}
	    	
	    	StringTokenizer token3 = new StringTokenizer(prevName, "-");
	    	
	    	StringTokenizer token4 = new StringTokenizer(currName, "-");
	    	int count = token4.countTokens();
	    	
	    	int i = 0;
	    	
	    	for(i=0; i<count; i++)
	    	{
	    		if(!token3.nextToken().toString().equals(token4.nextToken().toString()))
	    		{
	       			break;
	    		}
	    	}
	    	
	    	if(i == count-2 && i>0)continue;
	    	
	    	writer.write(line+"\n");
	    	
	    	prevLine = line;
	    }
	    
	    writer.close();
	    
	    tempFile.renameTo(f);
		
	}
	
	private static void createRemFileList(String board) throws IOException
	{
		String rootDir = "/home/farigys/workspace/HealthBoardDownloader/"+board;
		File  f = new File(rootDir+"/FilesLists.txt");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    HashSet<String> downloadedList = new HashSet<String>();
	    
	    String line = "";
	    
	    while((line = reader.readLine())!=null)
	    {
	    	downloadedList.add(line);
	    }
	    
	    	
		File file2 = new File(rootDir + "/RemFilesLists.txt");//user info cache
		// if file doesnt exists, then create it
		if (!file2.exists()) {
			file2.createNewFile();
		}

		FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw2 = new BufferedWriter(fw2);
	    
	    File folder = new File(rootDir + "/HTML/");
	    
	    File[] listOfFiles = folder.listFiles();
	    
	    for(int k=0; k<listOfFiles.length; k++)
	    {
	    	String filename = "http://www.healthboards.com/boards/" + board + "/" + listOfFiles[k].getName();
	    	if(!downloadedList.contains(filename))
	    	{
	    		bw2.write(filename+"\n");
	    	}
	    }
	    bw2.close();
	    
	    
	}
	
	private static void downloadFiles(String board) throws IOException, InterruptedException {
		String command = "python /home/farigys/downloadWebPages.py " + board;
		
		Process proc = Runtime.getRuntime().exec(command);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

	    String line = "";
	    while((line = reader.readLine()) != null) {
	        System.out.print(line + "\n");
	    }
	    
        proc.waitFor();   

		
	}
	

}
