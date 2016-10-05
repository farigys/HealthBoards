import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;


public class linguisticAnalysis {
	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Health_Board/";
    	
    	int[][] fullList = new int[1200][6];
    	
    	File fcat = new File(root+"CacheFiles/ALZ100-NL.csv");
        FileInputStream fiscat = new FileInputStream(fcat); 
        BufferedReader readercat = new BufferedReader(new InputStreamReader(fiscat));
        
        String line = "";
        
        int linecount = 0;
        
        while((line=readercat.readLine())!=null)
        {
        	String[] parts = line.split(",");
        	for(int z=0; z<parts.length; z++)
        	{
        		fullList[linecount][z] = Integer.parseInt(parts[z]);
        	}
        	linecount++;
        }
        
        int[][] squeezedList = new int[12][6];
        
        
        for(int j=0; j<6; j++)
        {
        	for(int i=0; i<1200; i++)
        	{
        		squeezedList[i%12][j] += fullList[i][j];
        	}
        }
        
        double[][] percentList = new double[12][5];
        
       for(int j=0; j<5; j++)
       {
    	   for(int i=0; i<12; i++)
    	   {
    		   System.out.print(squeezedList[i][j]/(double)squeezedList[i][5] + ",");
    	   }
    	   System.out.print("\n");
       }
	}
}
