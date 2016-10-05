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
import java.util.StringTokenizer;


public class logisticClassification {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root = "/home/farigys/Documents/timebasedtestdays/";
		HashMap<String, ArrayList<info>> dataMap = new HashMap<String, ArrayList<info>>();
		ArrayList<String> users = new ArrayList<String>();
		ArrayList<featureVals> featureValues = new ArrayList<featureVals>();
		
		int time[] = {7, 14, 21, 28};
		
		for(int i=0; i<4; i++)
		{
			int gap = time[i];
			File f = new File(root+"/timeBasedTestData"+gap+"d.csv");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	        
	        String line = "";
	        
	        while((line=reader.readLine())!=null)
	        {
	        	StringTokenizer token = new StringTokenizer(line, ":,");
	        	info temp = new info();
	        	String userId = token.nextToken();
	        	if(!users.contains(userId))users.add(userId);
	        	temp.postcount = Double.parseDouble(token.nextToken());
	        	temp.treplycount = Double.parseDouble(token.nextToken());
	        	temp.greply = Double.parseDouble(token.nextToken());
	        	temp.sreply = Double.parseDouble(token.nextToken());
	        	temp.timegap1 = Double.parseDouble(token.nextToken());
	        	temp.timegap2 = Double.parseDouble(token.nextToken());
	        	temp.avgdays = Double.parseDouble(token.nextToken());
	        	temp.age = Double.parseDouble(token.nextToken());
	        	temp.gender = Double.parseDouble(token.nextToken());
	        	temp.hasloc = Double.parseDouble(token.nextToken());
	        	temp.hasimage = Double.parseDouble(token.nextToken());
	        	temp.posuni = Double.parseDouble(token.nextToken());
	        	temp.neguni = Double.parseDouble(token.nextToken());
	        	temp.totaluni = Double.parseDouble(token.nextToken());
	        	temp.qcount = Double.parseDouble(token.nextToken());
	        	temp.url = Double.parseDouble(token.nextToken());
	        	temp.output = Integer.parseInt(token.nextToken());
	        	
	        	if(dataMap.containsKey(userId))
	        	{
	        		ArrayList<info> tempArray = dataMap.get(userId);
	        		tempArray.add(temp);
	        		dataMap.put(userId, tempArray);
	        	}
	        	else
	        	{
	        		ArrayList<info> tempArray = new ArrayList<info>();
	        		tempArray.add(temp);
	        		dataMap.put(userId, tempArray);
	        	}
	        	
	        }
	        
	        reader.close();
	        
	        File f1 = new File(root+"/featureValue"+gap+"d.txt");
	        FileInputStream fis1 = new FileInputStream(f1); 
	        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        featureVals temp = new featureVals();
	        temp.postcount = Double.parseDouble(reader1.readLine());
	        System.out.println(temp.postcount);
        	temp.treplycount = Double.parseDouble(reader1.readLine());
        	System.out.println(temp.treplycount);
        	temp.greply = Double.parseDouble(reader1.readLine());
        	System.out.println(temp.greply);
        	temp.sreply = Double.parseDouble(reader1.readLine());
        	temp.timegap1 = Double.parseDouble(reader1.readLine());
        	temp.timegap2 = Double.parseDouble(reader1.readLine());
        	temp.avgdays = Double.parseDouble(reader1.readLine());
        	temp.age = Double.parseDouble(reader1.readLine());
        	temp.gender = Double.parseDouble(reader1.readLine());
        	temp.hasloc = Double.parseDouble(reader1.readLine());
        	temp.hasimage = Double.parseDouble(reader1.readLine());
        	temp.posuni = Double.parseDouble(reader1.readLine());
        	temp.neguni = Double.parseDouble(reader1.readLine());
        	temp.totaluni = Double.parseDouble(reader1.readLine());
        	temp.qcount = Double.parseDouble(reader1.readLine());
        	temp.url = Double.parseDouble(reader1.readLine());
        	temp.intercept = Double.parseDouble(reader1.readLine());
        	
        	
        	reader1.close();
        	
        	featureValues.add(temp);
		}
		
		File writefile = new File("/home/farigys/Documents/timebasedtestdays/peroformance.txt");
	    if (!writefile.exists()) {
	    	writefile.createNewFile();
	    }
	    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i=0; i<users.size(); i++)
		{
			String userId = users.get(i);
			ArrayList<info> tempvals = dataMap.get(userId);
			ArrayList<Double> performances = new ArrayList<Double>();
			
			bw.write(userId);
			
			for(int j=0; j<4; j++)
			{
				info tempi = tempvals.get(j);
				featureVals tempf = featureValues.get(j);
				double performance = (tempf.intercept + tempf.postcount*tempi.postcount + tempf.treplycount*tempi.treplycount +
						tempf.greply*tempi.greply + tempf.sreply*tempi.sreply + tempf.timegap1*tempi.timegap1 +
						tempf.timegap2*tempi.timegap2 + tempf.avgdays*tempi.avgdays + tempf.age*tempi.age +
						tempf.gender*tempi.gender + tempf.hasloc*tempi.hasloc + tempf.hasimage*tempi.hasimage +
						tempf.posuni*tempi.posuni + tempf.neguni*tempi.neguni + tempf.totaluni*tempi.totaluni +
						tempf.qcount*tempi.qcount + tempf.url*tempi.url);
				
				performance = 1/(1+Math.exp(performance*(-1)));
				
				performances.add(performance);
				//System.out.print(performance+" ");
				bw.write(","+tempi.output+":"+four.format(performance));
				
			}
			System.out.print("\n");
			bw.write("\n");
			
		}
        bw.close();
		
	}
}

class info
{
	//String userid;
	double postcount;
	double treplycount;
	double greply;
	double sreply;
	double timegap1;
	double timegap2;
	double avgdays;
	double age;
	double gender;
	double hasloc;
	double hasimage;
	double posuni;
	double neguni;
	double totaluni;
	double qcount;
	double url;
	int output;
}

class featureVals
{
	double postcount;
	double treplycount;
	double greply;
	double sreply;
	double timegap1;
	double timegap2;
	double avgdays;
	double age;
	double gender;
	double hasloc;
	double hasimage;
	double posuni;
	double neguni;
	double totaluni;
	double qcount;
	double url;
	double intercept;
}


