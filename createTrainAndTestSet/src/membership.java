import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class membership {
	 public static void main(String[] args) throws FileNotFoundException, IOException {
		 	String root = "/home/farigys/Documents/daily-strength/PeopleHTMLTest/";
			File file = new File(root);
	        File[] listOfFiles = file.listFiles();
	        

			
			File writefile = new File("/home/farigys/Documents/daily-strength/memberSince.txt");
		    if (!writefile.exists()) {
		          writefile.createNewFile();
		    }
		    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String filename = listOfFiles[i].getName();
					File f = new File(root+filename);
					FileInputStream fis = new FileInputStream(f); 
		            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	                StringTokenizer token = new StringTokenizer(filename, ".");
	                filename = token.nextToken();
					String line = new String();
					System.out.print(filename+" ");
					while((line=reader.readLine())!=null)
					{
						if(line.contains("Member since"))break;
					}
					if(line == null)
					{
						continue;
					}
					String month = null, date = null, year = null;
					if(line.contains("<span id="))
					{
						StringTokenizer token1 = new StringTokenizer(line, " ,");
						token1.nextToken();
						token1.nextToken();
						token1.nextToken();
						token1.nextToken();
						month = token1.nextToken();
						date = token1.nextToken();
						year = token1.nextToken();
						StringTokenizer token2 = new StringTokenizer(year, "<");
						year = token2.nextToken();
					}
					else
					{
						StringTokenizer token1 = new StringTokenizer(line, " ,");
						token1.nextToken();
						token1.nextToken();
						token1.nextToken();
						month = token1.nextToken();
						date = token1.nextToken();
						year = token1.nextToken();
					}
					
					if(date.length() == 1)date = "0"+date;
					
					year = Character.toString(year.charAt(2)) + Character.toString(year.charAt(3)); 
					
					if(year.length() == 1)year = "0" + year;
					
					//System.out.println(month+" "+date+" "+year);
					HashMap<String, String> monthMap = new HashMap<String, String>();
					monthMap.put("January", "01");
					monthMap.put("February", "02");
					monthMap.put("March", "03");
					monthMap.put("April", "04");
					monthMap.put("May", "05");
					monthMap.put("June", "06");
					monthMap.put("July", "07");
					monthMap.put("August", "08");
					monthMap.put("September", "09");
					monthMap.put("October", "10");
					monthMap.put("November", "11");
					monthMap.put("December", "12");
					
			        
			        
			        String vals = htmlParse(filename);
			        
			        int hasAge = Character.getNumericValue(vals.charAt(0));
			       	int hasGender = Character.getNumericValue(vals.charAt(1));
			       	int hasLoc = Character.getNumericValue(vals.charAt(2));
			       	int hasImage = Character.getNumericValue(vals.charAt(3));
			       	int completeness = hasAge + hasGender + hasLoc + hasImage;
			        
			       	String fdate = year+monthMap.get(month)+date;
			       	//System.out.println(fdate);
			       	String output = filename+":"+year+monthMap.get(month)+date+","+hasAge+","+hasGender+","+hasLoc+","+hasImage+","+completeness;
			       	System.out.println(output);
			        bw.write(output+"\n");
			        
			        reader.close();
				}
			}
			bw.close();
	}
	 
	 
	 static String htmlParse(String id) throws FileNotFoundException, IOException
		{
			//System.out.println(id);
			String temp = "";
			String root = "/home/farigys/Documents/daily-strength";
	    	File folder = new File(root);
	        File[] listOfFolders = folder.listFiles();
	    	File f1 = new File(root+"/PeopleHTMLTest/"+id+".html");
	        FileInputStream fis1 = new FileInputStream(f1); 
	        BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        String line;
	        Integer hasGender = 0, hasAge = 0, hasLoc = 0, image_val = 0;
	        int check = 0;
	        while((line = reader1.readLine())!=null)
	        {
	        	if(line.contains("<div class=\"more-details\">") && check == 0)
	        	{
        			//System.out.println(line);
	        		if(line.contains("<div class=\"more-details\"></div>"))
	        		{
	        			return "0000";
	        		}
	        		StringTokenizer token1 = new StringTokenizer(line, "<>");
	        		token1.nextToken();
	        		token1.nextToken();
	        		token1.nextToken();
	        		token1.nextToken();
	        		String info = token1.nextToken();
	        		StringTokenizer token2 = new StringTokenizer(info, ",");
	        		int countTokens = token2.countTokens();
	        		for(int i=0; i<countTokens; i++)
	        		{
	        			String tok = token2.nextToken();
	        			if(tok.contains("Male")||tok.contains("Female"))hasGender = 1;
	        			else if(tok.matches(".*\\d.*"))hasAge = 1;       			
	        		}
	        		
	        		if((hasGender==1 && hasAge==1 && countTokens>2) || (hasGender==0 && hasAge==1 && countTokens>1) || (hasGender==1 && hasAge==0 && countTokens>1))
	        			hasLoc = 1;
	        		check = 1;
	        		continue;
	        	}
	        	if(line.contains("<div class=\"clear\"><div class=\"user_image_bg\">"))
	        	{
	        		reader1.readLine();
	        		line = reader1.readLine();
	        		if(line.contains("default"))image_val = 0;
	        		else if(line.contains("templates"))image_val = 1;
	        		else if(line.contains("userfiles"))image_val = 2;
	        		check = 0;
	        		break;
	        	}
	        }
	        reader1.close();
	        temp = hasAge.toString()+hasGender.toString()+hasLoc.toString()+image_val.toString();
			return temp;
		}
}