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


public class createSparseArffFile {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root = "/home/farigys/Documents/daily-strength/";
		
		File writefile = new File("/home/farigys/Documents/userProfileSparse.arff");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
     	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	bw.write("@RELATION userProfileSparse\n\n");
		
		File  ff = new File(root+"listOfWords.txt");
        FileInputStream fisf = new FileInputStream(ff); 
        BufferedReader readerf = new BufferedReader(new InputStreamReader(fisf));
        
        ArrayList<String> listOfWords = new ArrayList<String>();
        
        String currline = "";
        int ccount = 0;
//        while((currline=readerf.readLine())!=null)
        for(ccount=0;ccount<134958;ccount++)
        {
        	listOfWords.add(currline);
        	bw.write("@ATTRIBUTE "+ccount+"\tstring\n");
        	//ccount++;
        }
        
        bw.write("@ATTRIBUTE duration {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}\n");
        bw.write("@ATTRIBUTE age {0, 1, 2, 3, 4, 5, 6, 7}\n");
        bw.write("@ATTRIBUTE gender {0, 1, 2}\n");
        bw.write("@ATTRIBUTE repliesCount NUMERIC\n");
        bw.write("@ATTRIBUTE isProblem {0, 1}\n");
        bw.write("@ATTRIBUTE posUniCount NUMERIC\n");
        bw.write("@ATTRIBUTE negUniCount NUMERIC\n");
        bw.write("@ATTRIBUTE posBiCount NUMERIC\n");
        bw.write("@ATTRIBUTE negBiCount NUMERIC\n");
        bw.write("@ATTRIBUTE qCount NUMERIC\n");
        bw.write("@ATTRIBUTE urlCount NUMERIC\n");
        bw.write("@ATTRIBUTE class {0, 1}\n");
        
        bw.write("\n@DATA\n");
        
        int lastIndex = listOfWords.size();
        
        System.out.println(lastIndex);
        
        listOfWords.clear();
        
        readerf.close();
        
        HashMap<String, String> userProfile = new HashMap<String, String>();
        
        
        ff = new File(root+"userProfile1.csv");
        fisf = new FileInputStream(ff); 
        readerf = new BufferedReader(new InputStreamReader(fisf));
        
        currline = "";
        
        while((currline=readerf.readLine())!=null)
        {
        	StringTokenizer token = new StringTokenizer(currline,",");
        	String userId = token.nextToken();
        	String values = "134958 "+token.nextToken()+","+"134959 "+token.nextToken()+","+"134960 "+token.nextToken()+","+"134961 "+token.nextToken()+","+"134962 "+token.nextToken()+","+"134963 "+token.nextToken()+","+"134964 "+token.nextToken()+","+"134965 "+token.nextToken()+","+"134966 "+token.nextToken()+","+"134967 "+token.nextToken()+","+"134968 "+token.nextToken()+","+"134969 "+token.nextToken();
        	userProfile.put(userId, values);
        }
        
        readerf.close();
        
        
        ff = new File(root+"bagOfWords.txt");
        fisf = new FileInputStream(ff); 
        readerf = new BufferedReader(new InputStreamReader(fisf));
        
        currline = "";
        
        while((currline=readerf.readLine())!=null)
        {
        	//System.out.println(currline);
        	StringTokenizer token = new StringTokenizer(currline,"{");
        	String userId = token.nextToken();
        	StringTokenizer token1 = new StringTokenizer(userId, ",");
        	userId = token1.nextToken();
        	String value = token.nextToken();
        	value = value.substring(0, value.length()-1);
        	if(userProfile.containsKey(userId))
        	{
        		if(value.length()>0)
        		{
        			String tempVal = "{"+value+","+userProfile.get(userId)+"}";
            		bw.write(tempVal+"\n");
        		}
        		else
        		{
        			String tempVal = "{"+userProfile.get(userId)+"}";
            		bw.write(tempVal+"\n");
        		}
        		
        	}
        	else continue;
        }
        
        bw.close();
        
        
	}
}
