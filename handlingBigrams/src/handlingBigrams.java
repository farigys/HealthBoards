import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class handlingBigrams {
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
			    	File  fp = new File(rootDir+"/PosEmotionBigramsFrequency.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fisp = new FileInputStream(fp); 
		            //System.out.println("Dhuksi");
		            BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
		            File  fn = new File(rootDir+"/NegEmotionBigramsFrequency.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fisn = new FileInputStream(fn); 
		            BufferedReader readern = new BufferedReader(new InputStreamReader(fisn));
		            File  fpuni = new File(rootDir+"/FilteredPosEmotionUnigramsFrequency.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fispuni = new FileInputStream(fpuni); 
		            //System.out.println("Dhuksi");
		            BufferedReader readerpuni = new BufferedReader(new InputStreamReader(fispuni));
		            File  fnuni = new File(rootDir+"/FilteredNegEmotionUnigramsFrequency.txt");
			    	//System.out.println("Dhuksi");
		            FileInputStream fisnuni = new FileInputStream(fnuni); 
		            //System.out.println("Dhuksi");
		            BufferedReader readernuni = new BufferedReader(new InputStreamReader(fisnuni));
//		            File writefile = new File(rootDir+"/averagePostFrequency.csv");
//		         	if (!writefile.exists()) {
//		        		writefile.createNewFile();
//		        	}
		         	
		         	//HashMap<String, ArrayList<monthwithPostCount>> monthwisefrequencyMap = new HashMap<String, ArrayList<monthwithPostCount>>();
		         	
		         	ArrayList<String> posUnigrams = new ArrayList<String>();
		         	ArrayList<String> negUnigrams = new ArrayList<String>();
		         	
		         	String line = "";
		         	
		         	while((line = readerpuni.readLine())!=null)
		         	{
		         		StringTokenizer tokenUnigram = new StringTokenizer(line, " ");
		         		String unigram = tokenUnigram.nextToken();
		         		posUnigrams.add(unigram);
		         	}
		         	
		         	line = "";
		         	
		         	while((line = readernuni.readLine())!=null)
		         	{
		         		StringTokenizer tokenUnigram = new StringTokenizer(line, " ");
		         		String unigram = tokenUnigram.nextToken();
		         		negUnigrams.add(unigram);
		         	}
		         	
		         	
		         	TreeMap<String, Integer> falsePosBigrams = new TreeMap<String, Integer>();
		         	TreeMap<String, Integer> falseNegBigrams = new TreeMap<String, Integer>();
		         	
		         	TreeMap<String, Integer> posBigrams = new TreeMap<String, Integer>();
		         	TreeMap<String, Integer> negBigrams = new TreeMap<String, Integer>();
		         	
		         	String line1 = "";
	                while((line1 = readerp.readLine())!=null)
	                {
	                	StringTokenizer token = new StringTokenizer(line1,"\t");
	                	String bigram = token.nextToken();
	                	bigram = bigram.replaceAll("/", "_");
	                	int bigramCount = Integer.parseInt(token.nextToken());
	                	StringTokenizer bigramToken = new StringTokenizer(bigram, " ");
	                	String bigram1 = bigramToken.nextToken();
	                	String bigram2 = bigramToken.nextToken();
	                	if(bigram1.contains("not") && !bigram1.equals("another") && posUnigrams.contains(bigram2))
	                	{
	                		falsePosBigrams.put(bigram, bigramCount);	                		
	                	}
	                	else if(bigram2.contains("not") && !bigram2.equals("another") && posUnigrams.contains(bigram1))
	                	{
	                		falsePosBigrams.put(bigram, bigramCount);
	                	}
	                	else
                		{
                			posBigrams.put(bigram, bigramCount);
                		}
	                	
	                }
	                
	                line1 = "";
	                while((line1 = readern.readLine())!=null)
	                {
	                	StringTokenizer token = new StringTokenizer(line1,"\t");
	                	String bigram = token.nextToken();
	                	bigram = bigram.replaceAll("/", "_");
	                	int bigramCount = Integer.parseInt(token.nextToken());
	                	StringTokenizer bigramToken = new StringTokenizer(bigram, " ");
	                	String bigram1 = bigramToken.nextToken();
	                	String bigram2 = bigramToken.nextToken();
	                	if(bigram1.contains("not") && !bigram1.equals("another") && negUnigrams.contains(bigram2))
	                	{
	                		falseNegBigrams.put(bigram, bigramCount);
	                	}
	                	else if(bigram2.contains("not") && !bigram2.equals("another") && negUnigrams.contains(bigram1))
	                	{
	                		falseNegBigrams.put(bigram, bigramCount);
	                	}
	                	else
                		{
                			negBigrams.put(bigram, bigramCount);
                		}
	                	
	                }
	                
	                TreeMap<String, Integer> finalPosBigrams = new TreeMap<String, Integer>();
		         	TreeMap<String, Integer> finalNegBigrams = new TreeMap<String, Integer>();
	                
		         	finalPosBigrams.putAll(posBigrams);
		         	finalPosBigrams.putAll(falseNegBigrams);
		         	
		         	finalNegBigrams.putAll(negBigrams);
		         	finalNegBigrams.putAll(falsePosBigrams);
		         	
		         	File writefilep = new File(rootDir+"/FilteredPosEmotionBigramsFrequency.txt");
		         	if (!writefilep.exists()) {
		        		writefilep.createNewFile();
		        	}
		         	FileWriter fwp = new FileWriter(writefilep.getAbsoluteFile());
		        	BufferedWriter bwp = new BufferedWriter(fwp);
		         	
		        	File writefilen = new File(rootDir+"/FilteredNegEmotionBigramsFrequency.txt");
		         	if (!writefilen.exists()) {
		        		writefilen.createNewFile();
		        	}
		         	FileWriter fwn = new FileWriter(writefilen.getAbsoluteFile());
		        	BufferedWriter bwn = new BufferedWriter(fwn);
		         	
	                Iterator iter = finalPosBigrams.entrySet().iterator();
	                while(iter.hasNext())
	                {
	                	Map.Entry pairs = (Map.Entry)iter.next();
	                	bwp.write(pairs.getKey()+"\t"+pairs.getValue()+"\n");
	                	//System.out.print(pairs.getKey()+" "+pairs.getValue()+" ");
	                }
	                bwp.close();
	                
	                iter = finalNegBigrams.entrySet().iterator();
	                while(iter.hasNext())
	                {
	                	Map.Entry pairs = (Map.Entry)iter.next();
	                	bwn.write(pairs.getKey()+"\t"+pairs.getValue()+"\n");
	                	//System.out.print(pairs.getKey()+" "+pairs.getValue()+" ");
	                }
	                bwn.close();
	                
//	                iter = falsePosBigrams.entrySet().iterator();
//	                while(iter.hasNext())
//	                {
//	                	Map.Entry pairs = (Map.Entry)iter.next();
//	                	System.out.print(pairs.getKey()+" "+pairs.getValue()+" ");
//	                }
//	                
//	                System.out.print("\n"+posBigrams.size()+"\n"+finalPosBigrams.size()+"\n");
//	                
//	                iter = falseNegBigrams.entrySet().iterator();
//	                while(iter.hasNext())
//	                {
//	                	Map.Entry pairs = (Map.Entry)iter.next();
//	                	System.out.print(pairs.getKey()+" "+pairs.getValue()+" ");
//	                }
//	                System.out.print("\n"+negBigrams.size()+"\n"+finalNegBigrams.size()+"\n");
	        	} 
	        }
	        
	 }

}
