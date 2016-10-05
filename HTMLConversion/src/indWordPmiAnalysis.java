import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


public class indWordPmiAnalysis {
	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	//String root = "/home/farigys/Health_Board/";
    	
    	String[] conditions = {"alzheimers-disease-dementia", "amyotrophic-lateral-sclerosis-als", "aneurysm", "arachnoiditis",
				"bells-palsy", "brain-head-injury", "brain-nervous-system-disorders",
				"brain-tumors", "cerebral-palsy", "dizziness-vertigo"};
    	
    	//String[] conditions = {"depression"};
    	//String[] conditions = {"relationship-health"};
    	
    	HashMap<String, Double> pmiMap = new HashMap<String, Double>();
    	
    	TreeMap<Double, String> sortedpmimap = new TreeMap<Double, String>();
    	
    	for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
		
			BufferedReader br = null;
			
			br = new BufferedReader(new FileReader(root + "/LastPostWordPMIBigram.csv"));
			
			String line = "";
			
			int linecount = 0;
			
			while((line = br.readLine())!=null)
			{
				//if(linecount == 20)break;
				String[] parts = line.split(":");
				String unigram = parts[0];
				//try{
					if(parts[1].startsWith("/") || parts[1].equals(""))continue;
					Double pmi = Double.parseDouble(parts[1]);
					//System.out.println(line);
					if(pmiMap.containsKey(unigram))
					{
						Double tempPmi = pmiMap.get(unigram);
						tempPmi+=pmi;
						pmiMap.put(unigram, tempPmi);
					}
					else pmiMap.put(unigram,  pmi);
				//}catch(Exception e)
				{
					//e.printStackTrace();
					//continue;
				}
				//pmiMap.put(Double.parseDouble(parts[1]), parts[0]);
				//linecount++;
			}
		}
    	
    	Iterator it = pmiMap.entrySet().iterator();
    	
    	while(it.hasNext())
    	{
    		Map.Entry pair = (Map.Entry) it.next();
    		//System.out.println(pair.getKey() + " " + pair.getValue());
    		sortedpmimap.put(Double.parseDouble(pair.getValue().toString()), pair.getKey().toString());
    	}
    	
    	NavigableSet<Double> pmiSet = sortedpmimap.descendingKeySet();
    	
    	it = pmiSet.iterator();
    	
    	int contentCount = 0;
    	
    	while(it.hasNext() && contentCount < 20)
    	{
    		Double currKey = (Double)it.next();
    		System.out.println(sortedpmimap.get(currKey) + ":" + currKey);
    		contentCount++;
    	}
		
	}
}
