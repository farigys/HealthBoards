import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;


public class calculatePerformanceMeasure {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		
		 
		
		String condition = "depression";
		
		String root = "/home/farigys/Health_Board/";
		
		String rootDir1 = root+ "/liblinear-2.1/";
		
		int[] obsPeriods = {1, 6, 12};
		
		for(int x=0; x<3; x++)
		{
			int obsPeriod = obsPeriods[x];
			
			//int obsPeriod = 12;
			
			//String[] type = {"mostbasic", "basic", "basic+psyling", "unigram", "bigram", "skipbigram", "psylingunigram"
			//		, "psylingbigram", "psylingskipbigram"};
			
			//String[] type = {"basic+sent","demographic", "activity", "timeline","basic","lastpostbasic+psyling","unigramlastpost", "bigramlastpost", "skipbigramlastpost"};
				
			String[] type = {"basic+sent"};
			//String[] type = {"basicDT","demographic", "activity", "timeline"};
			
			for(int i=0; i<type.length; i++)
			{
				int TP = 0, TN = 0, FP = 0, FN = 0;
				
				String filename = type[i] + obsPeriod + "m.t";
				
				File  f1 = new File(rootDir1 + filename);
			    FileInputStream fis1 = new FileInputStream(f1); 
			    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
			    
			    String line = "";
			    
			    ArrayList<Integer> actResult = new ArrayList<Integer>();
			    
			    while((line=reader1.readLine())!=null)
			    {
			    	String[] parts = line.split(" ");
			    	actResult.add(Integer.parseInt(parts[0]));
			    }
			    
			    filename = type[i] + obsPeriod + "moutput";
				
				f1 = new File(rootDir1 + filename);
			    fis1 = new FileInputStream(f1); 
			    reader1 = new BufferedReader(new InputStreamReader(fis1));
			    
			    line = "";
			    
			    //ArrayList<Integer> predResult = new ArrayList<Integer>();
			    int count = 0;
			    while((line=reader1.readLine())!=null)
			    {
			    	int predResult = Integer.parseInt(line.trim());
			    	if(predResult == 0 && actResult.get(count) == 0)TP++;
			    	else if(predResult == 1 && actResult.get(count) == 1)TN++;
			    	else if(predResult == 0 && actResult.get(count) == 1)FP++;
			    	else if(predResult == 1 && actResult.get(count) == 0)FN++;
			    	
			    	count++;
			    }
			    
			    double accuracy = ((double)(TP+TN))/(TP+TN+FP+FN);
			    double precision = ((double)(TP))/(TP+FP);
			    double recall = ((double)(TP))/(TP+FN);
			    double fmeasure = 2* (precision*recall)/(precision+recall);
			    
			    System.out.println("\n"+type[i] + obsPeriod);
			    System.out.println("------------------------");
			    System.out.print("Acc: " + accuracy + " Prec: " + precision + " Recall: " + recall + " F: " + fmeasure + "\n");
			}
		}
		
		
		
		
	}
}
