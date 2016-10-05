import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.parser.JSONParser;


public class timelineAnalysis {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
		
		String[] conditions = {"alzheimers-disease-dementia", "depression", "relationship-health"};
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir1 = root+"/Caches";
			
			File file0 = new File(rootDir1 + "/basicFeaturesWithClassForTimeLine.txt");
			FileInputStream fis1 = new FileInputStream(file0); 
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
			
			String line = "";
			
			ArrayList<Integer> zeroInit = new ArrayList<Integer>();
			ArrayList<Integer> zeroFinal = new ArrayList<Integer>();
			ArrayList<Integer> zeroMax = new ArrayList<Integer>();
			ArrayList<Integer> zeroMed = new ArrayList<Integer>();
			ArrayList<Integer> oneInit = new ArrayList<Integer>();
			ArrayList<Integer> oneFinal = new ArrayList<Integer>();
			ArrayList<Integer> oneMax = new ArrayList<Integer>();
			ArrayList<Integer> oneMed = new ArrayList<Integer>();
			
			while((line=reader1.readLine())!=null)
			{
				String parts[] = line.split(";");
				int willReturn = Integer.parseInt(parts[11]);
				
				if(willReturn == 0)
				{
					zeroInit.add(Integer.parseInt(parts[4]));
					zeroFinal.add(Integer.parseInt(parts[5]));
					zeroMax.add(Integer.parseInt(parts[6]));
					zeroMed.add(Integer.parseInt(parts[7]));
				}
				else if(willReturn == 1)
				{
					oneInit.add(Integer.parseInt(parts[4]));
					oneFinal.add(Integer.parseInt(parts[5]));
					oneMax.add(Integer.parseInt(parts[6]));
					oneMed.add(Integer.parseInt(parts[7]));
				}
				
			}
			
			System.out.println(condition);
			
			int sum = 0;
			for(int i = 1; i < zeroInit.size(); i++)
			    sum += zeroInit.get(i);
			
			System.out.println("AvgZeroInit: " + ((double)sum)/((double)zeroInit.size()));
			
			sum = 0;
			for(int i = 1; i < zeroFinal.size(); i++)
			    sum += zeroFinal.get(i);
			
			System.out.println("AvgzeroFinal: " + ((double)sum)/((double)zeroFinal.size()));
			
			sum = 0;
			for(int i = 1; i < zeroMax.size(); i++)
			    sum += zeroMax.get(i);
			
			System.out.println("AvgZeroMax: " + ((double)sum)/((double)zeroMax.size()));
			
			sum = 0;
			for(int i = 1; i < zeroMed.size(); i++)
			    sum += zeroMed.get(i);
			
			System.out.println("AvgZeroMed: " + ((double)sum)/((double)zeroMed.size()));
			
			sum = 0;
			for(int i = 1; i < oneInit.size(); i++)
			    sum += oneInit.get(i);
			
			System.out.println("AvgoneInit: " + ((double)sum)/((double)oneInit.size()));
			
			sum = 0;
			for(int i = 1; i < oneFinal.size(); i++)
			    sum += oneFinal.get(i);
			
			System.out.println("AvgoneFinal: " + ((double)sum)/((double)oneFinal.size()));
			
			sum = 0;
			for(int i = 1; i < oneMax.size(); i++)
			    sum += oneMax.get(i);
			
			System.out.println("AvgoneMax: " + ((double)sum)/((double)oneMax.size()));
			
			sum = 0;
			for(int i = 1; i < oneMed.size(); i++)
			    sum += oneMed.get(i);
			
			System.out.println("AvgoneMed: " + ((double)sum)/((double)oneMed.size()));
		}
	}
}
