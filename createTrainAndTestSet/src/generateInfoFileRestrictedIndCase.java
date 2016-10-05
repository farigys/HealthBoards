import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.StringTokenizer;


public class generateInfoFileRestrictedIndCase {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength";
    	String currCondition = "/Diets-Weight-Maintenance";
    	String rootDir = root + currCondition;
    	
    	HashMap <String, String> totalInfoList = new HashMap<String, String>();
    	
    	File f1 = new File(root+"/userProfile1Restricted1.csv");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        String line = null;
        
        while((line = reader1.readLine())!=null)
        {
        	StringTokenizer token = new StringTokenizer(line, ",");
        	String id = token.nextToken();
        	String value = id;
        	while(token.hasMoreTokens())
        	{
        		value = value + "," + token.nextToken();
        	}
        	totalInfoList.put(id, value);
        }
        reader1.close();
        
        f1 = new File(root+"/userProfile1TestRestricted1.csv");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        line = null;
        
        while((line = reader1.readLine())!=null)
        {
        	StringTokenizer token = new StringTokenizer(line, ",");
        	String id = token.nextToken();
        	String value = id;
        	while(token.hasMoreTokens())
        	{
        		value = value + "," + token.nextToken();
        	}
        	totalInfoList.put(id, value);
        }
        reader1.close();
        
        f1 = new File(root+"/"+currCondition+"/IndTrainList.txt");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        File writefile = new File(root+"/"+currCondition+"/indTrainListInfo.txt");
        FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
        
        line = null;
        
        while((line = reader1.readLine())!=null)
        {
        	String id = line;
        	String value = totalInfoList.get(id);
        	bw.write(value+"\n");
        }
        bw.close();
        
        f1 = new File(root+"/"+currCondition+"/IndTestList.txt");
        fis1 = new FileInputStream(f1); 
        reader1 = new BufferedReader(new InputStreamReader(fis1));
        
        writefile = new File(root+"/"+currCondition+"/indTestListInfo.txt");
        fw = new FileWriter(writefile.getAbsoluteFile());
    	bw = new BufferedWriter(fw);
    	
    	line = null;
        
        while((line = reader1.readLine())!=null)
        {
        	String id = line;
        	String value = totalInfoList.get(id);
        	bw.write(value+"\n");
        }
        bw.close();
	}
}
