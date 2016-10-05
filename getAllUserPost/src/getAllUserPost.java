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

//creates list of top 20 posters
//also creates the list of all posters

public class getAllUserPost {
	
	 public static void main(String[] args) throws FileNotFoundException, IOException {
		 
		 	String root = "/mnt/docsig/storage/daily-strength/";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	        for (int m = 0; m < listOfFolders.length; m++) 
	        {
	        	if (listOfFolders[m].isDirectory())
	        	{
	        		String foldername = listOfFolders[m].getName();
	        		//String foldername = "Alcohalism";
	        		if(foldername.equals("Network Trash Folder")||foldername.equals("PeopleHTML")||foldername.equals("stanford-postagger-2013-06-20")||foldername.equals("Temporary Items")||foldername.contains("."))continue;
	        		System.out.println("Now in: "+foldername);
	        		String rootDir = root+"/"+foldername;
			    	File  fp = new File(rootDir+"/userPostList.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fisp = new FileInputStream(fp); 
		            //System.out.println("Dhuksi");
		            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
		            File writefile = new File(rootDir+"/top20Poster.txt");
		         	if (!writefile.exists()) {
		        		writefile.createNewFile();
		        	}
		        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
		        	BufferedWriter bw = new BufferedWriter(fw);
		            //System.out.println("Dhuksi");
		            String line;
		            HashMap<String, Integer> posterList = new HashMap<String, Integer>();
		            HashMap<String, String> posterPostList = new HashMap<String, String>();
		            while((line=readerp.readLine())!=null)
		            {
		            	//System.out.println("Dhuksi");
		            	StringTokenizer st = new StringTokenizer(line, ",");
		                String poster = st.nextToken();
		                String post = st.nextToken();
		                if(posterList.containsKey(poster))
		                {
		                	int count = posterList.get(poster);
		                	count++;
		                	posterList.put(poster, count);
		                }
		                else posterList.put(poster, 1);
		                if(posterPostList.containsKey(poster))
		                {
		                	String temp = posterPostList.get(poster);
		                	temp = temp +","+ post;
		                	posterPostList.put(poster, temp);
		                }
		                else
		                {
		                	posterPostList.put(poster, post);
		                }
		            }
		            
		            ValueComparator bvc =  new ValueComparator(posterList);
		            TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
		            sorted_map.putAll(posterList);
		            //System.out.println(sorted_map);
		            Iterator iter = sorted_map.entrySet().iterator();
		            for(int i=0;i<20;i++)
		            {
		            	Map.Entry pairs = (Map.Entry)iter.next();
		            	String currPoster = pairs.getKey().toString();
		            	String temp = "";
		            	Iterator iter1 = posterPostList.entrySet().iterator();
		            	while(iter1.hasNext())
		            	{
		            		Map.Entry pairs1 = (Map.Entry)iter1.next();
		            		String currKey = pairs1.getKey().toString();
		            		if(currKey.equals(currPoster))temp = temp + "," + posterPostList.get(currKey);
		            	}
		            	bw.write(pairs.getKey()+","+pairs.getValue()+temp+"\n");
		            }
		            bw.close();
		            readerp.close();
	        	}
	     }
	 }

}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }
    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } 
    }
}
