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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import java.util.Iterator;

//create pos vs neg chart for each top posters

public class indPosvsNeg {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root =  "/mnt/docsig/storage/daily-strength/War-In-Iraq";
		File  funigram = new File(root+"/FilteredPosEmotionUnigramsFrequency.txt");
        FileInputStream fisunigram = new FileInputStream(funigram); 
        BufferedReader readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
        String line = "";
        
        HashSet<String> posUnigrams = new HashSet<String>();
        
        while((line=readerunigram.readLine())!=null)
        {
        	StringTokenizer token = new StringTokenizer(line," ");
        	String temp = token.nextToken();
        	posUnigrams.add(temp);
        }
        
        funigram = new File(root+"/FilteredNegEmotionUnigramsFrequency.txt");
        fisunigram = new FileInputStream(funigram); 
        readerunigram = new BufferedReader(new InputStreamReader(fisunigram));
        line = "";
        
        HashSet<String> negUnigrams = new HashSet<String>();
        
        while((line=readerunigram.readLine())!=null)
        {
        	StringTokenizer token = new StringTokenizer(line," ");
        	String temp = token.nextToken();
        	negUnigrams.add(temp);
        }
        
        //System.out.println(posUnigrams.size()+" "+negUnigrams.size());
        File top20Poster = new File(root+"/top20Poster.txt");
        FileInputStream fistop20Poster = new FileInputStream(top20Poster);
        BufferedReader readertop20Poster = new BufferedReader(new InputStreamReader(fistop20Poster));
        String currTopPoster = "";
        while((currTopPoster=readertop20Poster.readLine())!=null)
        {
        	StringTokenizer tokenizer = new StringTokenizer(currTopPoster,",");
        	String currTopPosterID = tokenizer.nextToken();
        	System.out.println("Now in: "+currTopPosterID);
        	ArrayList<String> fileName = new ArrayList<String>();
			String root1 = root+"/top20Poster/"+currTopPosterID+"/";
			File folder = new File(root1);
	        File[] listOfFolders = folder.listFiles();
	        for(int m=0; m<listOfFolders.length; m++)
	        {
	        	if(listOfFolders[m].getName().contains("posvsneg"))continue;
	        	fileName.add(listOfFolders[m].getName());
	        }
	        Collections.sort(fileName);
	        //System.out.println(folderName);
	        HashMap<String, ArrayList<Double>> posvsNegCountMap = new HashMap<String, ArrayList<Double>>();
	        
	        
	        
	        for(int m=0; m<fileName.size(); m++)
	        {
	        	File  fp = new File(root1+fileName.get(m));
	            FileInputStream fisp = new FileInputStream(fp); 
	            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
	            String currline = "";
	            double poscount =0, negcount =0, totalcount =0;
	            while((currline=readerp.readLine())!=null)
	            {
	            	StringTokenizer tokenize = new StringTokenizer(currline," ");
	            	while(tokenize.hasMoreTokens())
	            	{
	            		totalcount++;
	            		String currUnigram = tokenize.nextToken();
	            		currUnigram = currUnigram.replace("/", "_");
	            		if(posUnigrams.contains(currUnigram.toLowerCase()))poscount++;
	            		if(negUnigrams.contains(currUnigram.toLowerCase()))negcount++;
	            	}
	            }
	            ArrayList<Double> temp = new ArrayList<Double>();
	            temp.add(poscount);
	            temp.add(negcount);
	            temp.add(totalcount);
	            posvsNegCountMap.put(fileName.get(m), temp);
	            readerp.close();
	        }
	        
	        File writefile = new File(root1+"posvsneg_"+currTopPosterID+".csv");
         	if (!writefile.exists()) {
        		writefile.createNewFile();
        	}
        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
	        
	        for(int i=0; i<posvsNegCountMap.size(); i++)
	        {
	        	//System.out.println(currTopPosterID+" "+fileName.get(i)+": "+posvsNegCountMap.get(fileName.get(i)).get(0)+","+posvsNegCountMap.get(fileName.get(i)).get(1));
	        	if((posvsNegCountMap.get(fileName.get(i)).get(0))==0 && (posvsNegCountMap.get(fileName.get(i)).get(1))==0)continue;
	        	bw.write(fileName.get(i)+","+four.format(((posvsNegCountMap.get(fileName.get(i)).get(0))/(posvsNegCountMap.get(fileName.get(i)).get(2))))+","+ four.format(((posvsNegCountMap.get(fileName.get(i)).get(1))/(posvsNegCountMap.get(fileName.get(i)).get(2))))+"\n");
	        }
	        bw.close();
	        //break;
        }
        
	        
	}
}
