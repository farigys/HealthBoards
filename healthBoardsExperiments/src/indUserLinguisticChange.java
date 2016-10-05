import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class indUserLinguisticChange {
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		String root = "/home/farigys/Health_Board/";
		
		File fcat = new File(root+"LIWC/LIWC2001_Categories.txt");
        FileInputStream fiscat = new FileInputStream(fcat); 
        BufferedReader readercat = new BufferedReader(new InputStreamReader(fiscat));
        
        readercat.readLine();
        
        String linecat = "";
        
        ArrayList<String> catname = new ArrayList<String>();
        
        while((linecat=readercat.readLine())!=null)
        {
        	if(linecat.contains("%"))break;
        	StringTokenizer tokencat = new StringTokenizer(linecat, "\t");
        	tokencat.nextToken();
        	String cat = tokencat.nextToken();
        	if(cat.contains("@"))
        	{
        		StringTokenizer temptok = new StringTokenizer(cat, "@");
        		cat = temptok.nextToken();
        	}
        	catname.add(cat);
        }
        
        String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
				"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
        //String[] conditions = {"diet-nutrition"};
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			root = "/home/farigys/Health_Board/" + condition;
			
			File file = new File(root + "/LinguisticChange.csv");//user info cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
		    
			
			/////////////////////////////////////////////////////////////////////////////
			File f1 = new File(root+"/PsyLingPMI.csv");
			FileInputStream fis1 = new FileInputStream(f1); 
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
			String line = "";
			
			ArrayList<Integer> topLIWCCat = new ArrayList<Integer>();
			
			for(int m=0; m<10; m++)
			{
				line = reader1.readLine();
				String[] tokenList = line.split(":");
				String cat = tokenList[0];
				topLIWCCat.add(catname.indexOf(cat));
				
			}
			
			/////////////////////////////////////////////////////////////////////////////
			
			f1 = new File(root+"/PsyLingCount.csv");
			fis1 = new FileInputStream(f1); 
			reader1 = new BufferedReader(new InputStreamReader(fis1));
	        
	        line = "";
	        
	        HashMap<String, ArrayList<Double>> psyLingMap = new HashMap<String, ArrayList<Double>>();
	        
	        while((line = reader1.readLine())!=null)
	        {
	        	//int[] arr = new int[68];
	        	ArrayList<Double> arr = new ArrayList<Double>();
	        	String[] tokenList = line.split(",");
	        	String postid = tokenList[0];
	        	for(int m=0; m<topLIWCCat.size(); m++)
	        	{
	        		arr.add(Double.parseDouble(tokenList[topLIWCCat.get(m) + 1].trim()));
	        	}
	        	psyLingMap.put(postid, arr);
	        }
			
	        //////////////////////////////////////////////////////////////////////////////////
	        
	        
	        //////////////////////////////////////////////////////////////////////////////////
	        
			File f = new File(root+"/topPosters.txt");
	        FileInputStream fis = new FileInputStream(f); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	        
	        ArrayList<String> topUsers = new ArrayList<String>();
	        
	        for(int i=0; i<5; i++)
	        {
	        	line = reader.readLine();
	        	String[] tokens = {};
	        	try
	        	{
	        		tokens = line.split(":");
	        	}catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        	String userName = tokens[0].trim();
	        	topUsers.add(userName);
	        }
	        
	        for(int i=0; i<topUsers.size(); i++)
	        {
	        	String name = topUsers.get(i);
	        	f = new File(root+"/userPostInfo.txt");
		        fis = new FileInputStream(f); 
		        reader = new BufferedReader(new InputStreamReader(fis));
		        
		        line = "";
		        
		        TreeMap<Long, String> postMap = new TreeMap<Long, String>();
		        
		        while((line = reader.readLine())!=null)
		        {
		        	String tokens[] = line.split(",");
		        	String userName = tokens[0].trim();
		        	String threadID = tokens[1].trim() + ".json";
		        	String postID = tokens[2].trim();
		        	postID = threadID + "/" + postID;
		        	Long date = Long.parseLong(tokens[3].trim());
		        	
		        	if(name.equals(userName))
		        	{
		        		postMap.put(date, postID);
		        	}
		        }
		        
		        bw.write(name + "\n");
		        bw.write("posts,");
		        for(int m=0; m<topLIWCCat.size(); m++)
		        {
		        	bw.write(catname.get(topLIWCCat.get(m)) + ",");
		        }
				bw.write("\n");
		        
		        //ArrayList<Integer> slacktime = new ArrayList<Integer>();
		        
		        int count = 0;
		        
		        Iterator it = postMap.entrySet().iterator();
		        
		        Long prevDate = Long.MAX_VALUE;
		        String prevPostId = "";
		        
		        while(it.hasNext())
		        {
		        	count++;
		        	Map.Entry pair = (Map.Entry)it.next();
		        	Long date = Long.parseLong(pair.getKey().toString());
		        	String postId = pair.getValue().toString();
		        	
		        	ArrayList<Double> psyLingCounts = psyLingMap.get(postId);
		        	
		        	long slacktime;
		        	
		        	if(prevDate == Long.MAX_VALUE)slacktime = 0;
		        	else
		        	{
		        		String currDate = date.toString();
		        		String prvDate = prevDate.toString();
		        		
		        		String prevYear = prvDate.trim().substring(0, 4);
				    	String prevMonth = prvDate.trim().substring(4, 6);
				    	String prevDay = prvDate.trim().substring(6, 8);
				    	String prevHour = prvDate.trim().substring(8, 10);
				    	String prevMinute = prvDate.trim().substring(10, 12);
				    	

				    	String currYear = currDate.trim().substring(0, 4);
				    	String currMonth = currDate.trim().substring(4, 6);
				    	String currDay = currDate.trim().substring(6, 8);
				    	String currHour = currDate.trim().substring(8, 10);
				    	String currMinute = currDate.trim().substring(10, 12);
				    	
				    	String dateStop = currMonth + "/" + currDay + "/" + currYear + " " + currHour + ":" + currMinute;
						String dateStart = prevMonth + "/" + prevDay + "/" + prevYear + " " + prevHour + ":" + prevMinute;
						
						//System.out.println(dateStart);
						//System.out.println(dateStop);
						
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

						Date d1 = null;
						Date d2 = null;
						
						d1 = format.parse(dateStart);
						d2 = format.parse(dateStop);

						slacktime = (d2.getTime() - d1.getTime())/(60 * 1000);
						
						//System.out.println(slacktime);

				    	if(slacktime<0)
				    	{
				    		System.out.println(postId + ":" + currDate + "," + prevPostId + ":" + prevDate);
				    	}
		        	
		        	}
		        	prevDate = date;
		        	prevPostId = postId;
		        	
		        	bw.write(count+ ",");
		        	for(int l=0; l<psyLingCounts.size(); l++)
		        	{
		        		bw.write(psyLingCounts.get(l) + ",");
		        	}
		        	bw.write(slacktime+"\n");
		        }
		        
		        
	        }
	        bw.close();
	        
		}

	}
}
