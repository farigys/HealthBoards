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
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;


public class generateSparseBagofWords {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root = "/home/farigys/Documents/daily-strength/";
		File folder = new File(root);
	    File[] listOfFolders = folder.listFiles();
	    String rootDir = root+"userPostIndex/";
	    File files = new File(rootDir);
	    File[] listOfFiles = files.listFiles();
	    int count = 0;
	    
	    File writefile = new File("/home/farigys/Documents/bagOfWords.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	String temp1;
	    
	    ArrayList<String> listOfWords = new ArrayList<String>();
	    
	    HashMap<String, ArrayList<Integer>> bagOfWords = new HashMap<String, ArrayList<Integer>>();
	    
	    for(int i=0; i<listOfFiles.length; i++)
	    {
	    	count++;
    		if(count%100 == 0)
    		{
    			System.out.println("total: "+count);
    		}
	    	String filename = listOfFiles[i].getName().toString();
    		StringTokenizer tokenizer1 = new StringTokenizer(filename, ".");
    		String userId = tokenizer1.nextToken();
    		
    		ArrayList<Integer> temp = new ArrayList<Integer>();
    		
    		for(int m=0; m<listOfWords.size(); m++)
    		{
    			temp.add(0);
    		}
    		
    		
    		File  ff = new File(rootDir+filename);
            FileInputStream fisf = new FileInputStream(ff); 
            BufferedReader readerf = new BufferedReader(new InputStreamReader(fisf));
            
            String firstPost = readerf.readLine();
            
            readerf.close();
            
            StringTokenizer tokenizer2 = new StringTokenizer(firstPost,":");
            
            //System.out.println(firstPost);
            
            tokenizer2.nextToken();
            String condition = tokenizer2.nextToken();
            firstPost = tokenizer2.nextToken();
            
            File  fp = new File("/home/farigys/Documents/daily-strength/"+condition+"/Texts.parsed/"+firstPost+".txt");
	        FileInputStream fisp = new FileInputStream(fp); 
	        BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
	        String currline = "";
	        
	        while((currline=readerp.readLine())!=null)
	            {
	            	StringTokenizer tokenize = new StringTokenizer(currline," ");
	            	while(tokenize.hasMoreTokens())
	            	{
	            		String currUnigram = tokenize.nextToken();
	            		currUnigram = currUnigram.replace("/", "_");
	            		int index;
	            		if(listOfWords.contains(currUnigram))
	            		{
	            			index = listOfWords.indexOf(currUnigram);
	            			int tempVal = temp.get(index)+1;
	            			temp.set(index, tempVal);
	            		}
	            		else
	            		{
	            			listOfWords.add(currUnigram);
	            			temp.add(1);
	            		}
	            		
	            	}
	            }
	        
	        //bagOfWords.put(userId, temp);
	        
	        temp1 = new String();
	        
	        //bw.write(userId+", {");
	        
	        for(Integer k=0; k<temp.size(); k++)
	        {
	        	Integer val;
	        	if((val = temp.get(k))!=0)
	        	{
	        		temp1=temp1+k.toString()+" "+val.toString()+",";
	        	}
	        }
	        //System.out.println(userId+" "+listOfWords.size()+" "+temp.size()+" "+temp1.length());
	        //System.out.println();
	        if(temp1.length()==0)temp1="}";
	        else temp1 = temp1.substring(0, temp1.length()-1)+"}";
	        
	        //bw.write(temp1+"\n");
	        
            
	    }
	    
	    //System.out.println("Bag of Words made, writing starts");
	    
	    
    	
//    	Iterator iter = bagOfWords.entrySet().iterator();
//        while(iter.hasNext())
//        {
//        	Map.Entry pairs = (Map.Entry)iter.next();
//        	String currUser = pairs.getKey().toString();
//        	ArrayList<Integer> temp = (ArrayList<Integer>)pairs.getValue();
//        	
//        	temp1 = new String();
//        	
//        	bw.write(currUser+", {");
//        	
//        	for(Integer k=0; k<temp.size(); k++)
//        	{
//        		Integer val;
//        		if((val = temp.get(k))!=0)
//        		{
//        			temp1=temp1+k.toString()+" "+val.toString()+",";
//        		}
//        	}
//        	
//        	temp1 = temp1.substring(0, temp1.length()-1)+"}";
//        	
//        	bw.write(temp1+"\n");
//        }
        bw.close();
        
        writefile = new File("/home/farigys/Documents/listOfWords.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	fw = new FileWriter(writefile.getAbsoluteFile());
    	bw = new BufferedWriter(fw);
    	
    	System.out.println(listOfWords.size());
    	
    	for(int l=0; l<listOfWords.size(); l++)
    	{
    		bw.write(listOfWords.get(l)+"\n");
    	}
	}

}
