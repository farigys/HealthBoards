import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class createIndTestAndTrainSet {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String currCondition = "Rheumatoid-Arthritis";
		String root = "/home/farigys/Documents/daily-strength/"+currCondition;
		
		File f1 = new File(root+"/userList.txt");
        FileInputStream fis1 = new FileInputStream(f1); 
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		
       int count = Integer.parseInt(reader1.readLine());
       
       int trainSize = (int)(0.8*count);
       int testSize = count - trainSize;
       
       File writefile = new File(root+"/IndTrainList.txt");
       FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
   	   BufferedWriter bw = new BufferedWriter(fw);
       
       String line = null;
       
       for(int i=0; i<trainSize; i++)
       {
       	   line = reader1.readLine();
       	   bw.write(line+"\n");
       }
       bw.close();
       
       writefile = new File(root+"/IndTestList.txt");
       fw = new FileWriter(writefile.getAbsoluteFile());
   	   bw = new BufferedWriter(fw);
       
       while((line = reader1.readLine())!=null)
       {
    	   bw.write(line+"\n");
       }
       bw.close(); 
	}
	

}
