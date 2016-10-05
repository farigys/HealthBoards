import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

//creating user post index for each condition, will have all the posts and replies a user did 
public class createIndCaseUserPostIndex {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	File folder = new File(root);
        File[] listOfFolders = folder.listFiles();
    	//String[] listOfFolders = {"Miscarriage", "Pregnancy", "COPD", "Rheumatoid-Arthritis", "Infertility", "Alcohalism", "Gastric-Bypass-Surgery", "Fibromyalgia", "Bipolar-Disorder"};
        
        String rootDir = root + "/userPostIndex/";
        
        HashMap<String, ArrayList<postIndex>> userPostIndexMap = new HashMap<String, ArrayList<postIndex>>();
        
        File f1 = new File(root+"/id_age_gender.csv");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        HashSet<String> userSet = new HashSet<String>();
        
        String line;
        
        while((line=reader1.readLine())!=null)
        {
        	StringTokenizer token1 = new StringTokenizer(line, ";");
        	String id = token1.nextToken();
        	userSet.add(id);
        }
        
        reader1.close();
        
        File files = new File(rootDir);
        File[] listOfFiles = files.listFiles();
        
        for (int m = 0; m < listOfFiles.length; m++) 
        {
        	if (!listOfFiles[m].isDirectory())
        	{
        		String filename = listOfFiles[m].getName().toString();
        		StringTokenizer token1 = new StringTokenizer(filename, ".");
        		String userId = token1.nextToken();
        		File f = new File(rootDir+filename);
                FileInputStream fis = new FileInputStream(f); 
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//                String post = null;
//                while((post = reader.readLine())!=null)
//                {
//                	StringTokenizer token= new StringTokenizer(post);
//                	
//                }
                String post = reader.readLine();
                StringTokenizer token = new StringTokenizer(post, ":");
                
                String date = token.nextToken();
                String condition = token.nextToken();
                String postId = token.nextToken();
                
                postIndex temp = new postIndex();
                
                temp.userId = userId;
                temp.date = date;
                temp.postId = postId;
                
                if(userPostIndexMap.containsKey(condition))
                {
                	ArrayList<postIndex> temp1 = userPostIndexMap.get(condition);
                	temp1.add(temp);
                	userPostIndexMap.put(condition, temp1);
                }
                else
                {
                	ArrayList<postIndex> temp1 = new ArrayList<postIndex>();
                	temp1.add(temp);
                	userPostIndexMap.put(condition, temp1);
                }
                
        		reader.close();
        	}
        }
        
        Iterator iter = userPostIndexMap.entrySet().iterator();
        while(iter.hasNext())
        {
        	Map.Entry pairs = (Map.Entry)iter.next();
         	String currCondition = pairs.getKey().toString();
         	
         	File writefile = new File(root+"/"+currCondition+"/userPostIndex.txt");
            FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
        	
        	ArrayList<postIndex> temp1 = (ArrayList)pairs.getValue();
        	ArrayList<String> userList = new ArrayList<String>();
        	
        	int count = 0;
        	
        	for(int i=0; i<temp1.size(); i++)
        	{
        		postIndex temp = temp1.get(i);
        		bw.write(temp.userId+":"+temp.date+":"+currCondition+":"+temp.postId+"\n");
        		
        		if(userSet.contains(temp.userId))
        		{
        			count++;
        			userList.add(temp.userId);
        		}
        	}
        	bw.close();
        	
        	writefile = new File(root+"/"+currCondition+"/userList.txt");
            fw = new FileWriter(writefile.getAbsoluteFile());
        	bw = new BufferedWriter(fw);
        	
        	bw.write(count+"\n");
        	for(int n=0; n<userList.size(); n++)
        	{
        		bw.write(userList.get(n)+"\n");
        	}
        	bw.close();
        	
        }
        
	}
	

}

class postIndex
{
	String userId;
	String date; 
	String postId;
}
