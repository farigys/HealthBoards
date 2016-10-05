
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashSet;
 
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class posTagger {
	public static void main(String[] args) throws IOException,
    ClassNotFoundException 
    {

		MaxentTagger tagger = new MaxentTagger(
	        "/home/farigys/new_DS_Data_from_UH/dailyStrengthNEW3_FINAL_DONE/stanford-postagger-2013-06-20/models/english-left3words-distsim.tagger");
	
		
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/new_DS_Data_from_UH/dailyStrengthNEW3_FINAL_DONE";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
        HashSet<String> listOfIds = new HashSet<String>();
        
       
        
        for (int m = 0; m < listOfFolders.length; m++) 
        {
        	if (listOfFolders[m].isDirectory())
        	{
        		//File parsedFolder = new File(listOfFolders[m]+"/Texts.parsed1");
        		if(!listOfFolders[m].getName().equals("Pseudotumor_Cerebri") && !listOfFolders[m].getName().equals("Depression") )
        		{
        			System.out.println(listOfFolders[m].getName()+": done");
        			continue;
        			
        		}
        		String foldername = listOfFolders[m].getName();
        		//String foldername = "Bone_Cancer";
        		if(foldername.equals("Network Trash Folder")||foldername.contains("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("userPostIndex")||foldername.contains("."))continue;
        		System.out.println("Now in: "+foldername);
        		String rootDir = root + "/" + foldername + "/Texts/";
        		
        		File files = new File(rootDir);
        		File[] listoffiles = files.listFiles();
        		
        		new File(root + "/" + foldername + "/Texts.parsed1/").mkdir();
        		
        		for(int n=0; n<listoffiles.length; n++)
        		{
        			if(listoffiles[n].isFile())
        			{
        				if(n%100 == 0)System.out.println(n+" done");
        				String filename = listoffiles[n].getName();
        				
        				File f3 = new File(rootDir+filename);
        		        FileInputStream fis3 = new FileInputStream(f3); 
        		        BufferedReader reader3 = new BufferedReader(new InputStreamReader(fis3));
        		        
        		        
        		        
        		        File writefile = new File(root + "/" + foldername + "/Texts.parsed1/"+filename);
        			    if (!writefile.exists()) {
        			          writefile.createNewFile();
        			    }
        			    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        			    BufferedWriter bw = new BufferedWriter(fw);
        		        
        		        String line = "";
        		        
        		        while((line = reader3.readLine())!=null)
        		        {
        		        	String tagged = tagger.tagString(line);
        		        	bw.write(tagged+"\n");
        		        }
        		        
        		        bw.close();
        		        reader3.close();
        			}
        			
        		}
        		
        	}
        }
        
	
	}
}
