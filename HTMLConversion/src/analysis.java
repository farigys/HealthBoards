import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class analysis {
	public static void main(String[] args) throws IOException, ParseException {
		NumberFormat formatter = new DecimalFormat("#0.00"); 
	  	//String[] conditions = {"abuse-support", "diet-nutrition", "exercise-fitness"};
		//String[] conditions = {"healthcare-professionals", "healthy-lifestyle", "nutritional-disorders", "grief-loss", "vitamins-supplements","obesity", 
		//						"abuse-support", "diet-nutrition", "exercise-fitness", "weight-loss", "scoliosis"};
		String[] conditions = {"depression"};
		//String[] conditions =	{ "vitamins-supplements","obesity", "weight-loss"};
		
		HashMap<String, String> userStartDate = new HashMap<String, String>();
		HashMap<String, String> userGender = new HashMap<String, String>();
		HashMap<String, Integer> userPostCount = new HashMap<String, Integer>();
		
		HashMap<String, HashMap<Integer, int[]>> initConditionMap = new HashMap<String, HashMap<Integer, int[]>>();  
		HashMap<String, HashMap<Integer, int[]>> replyConditionMap = new HashMap<String, HashMap<Integer, int[]>>();
		
		HashMap<String, HashMap<Integer, int[]>> ConditionMap = new HashMap<String, HashMap<Integer, int[]>>();
		
		HashMap<Integer, int[]> initTotalMap = new HashMap<Integer, int[]>();
		HashMap<Integer, int[]> replyTotalMap = new HashMap<Integer, int[]>();
		HashMap<Integer, int[]> TotalMap = new HashMap<Integer, int[]>();
		
		int[] postTime = new int[24];
		Arrays.fill(postTime, 0);
		
		Iterator it = userStartDate.entrySet().iterator();
		
		//File file = new File("/home/farigys/Health_Board/output.txt");// post analysis
		
		//File file = new File("/home/farigys/Health_Board/userOutput.txt");//user ananlysis

		File file = new File("/home/farigys/Health_Board/depression/postDistribution.txt");//post distribution
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		
		for(int i=1997; i<=2016; i++)
		{
			int[] totalMonthList1 = new int[13];
			
			Arrays.fill(totalMonthList1, 0);
			
			int[] totalMonthList2 = new int[13];
			
			Arrays.fill(totalMonthList2, 0);
			
			int[] totalMonthList3 = new int[13];
			
			Arrays.fill(totalMonthList3, 0);
			
			initTotalMap.put(i, totalMonthList1);
			replyTotalMap.put(i, totalMonthList2);
			TotalMap.put(i, totalMonthList3);
		}
		
//		it = TotalMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			Integer year = (Integer)pair.getKey();
//			
//			bw.write("Total \n______________________________\n");
//			
//			int[] monthList = (int[])pair.getValue();
//			
//			for(int m=1; m<monthList.length; m++)
//			{
//				bw.write(m+"/"+year+","+monthList[m]+"\n");
//			}
//			
//		}
		
		for(int c=0; c<conditions.length; c++)
		{
			String condition = conditions[c];
			
			System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
			
			String root = "/home/farigys/Health_Board/" + condition;
			
			String rootDir = root + "/JSON/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			HashMap<Integer, int[]> initYearMap = new HashMap<Integer, int[]>();
			HashMap<Integer, int[]> replyYearMap = new HashMap<Integer, int[]>();
			HashMap<Integer, int[]> totalYearMap = new HashMap<Integer, int[]>();
			
			
			
			for(int i=1997; i<=2016; i++)
			{
				int[] monthList1 = new int[13];
				
				Arrays.fill(monthList1, 0);
				int[] monthList2 = new int[13];
				
				Arrays.fill(monthList2, 0);
				int[] monthList3 = new int[13];
				
				Arrays.fill(monthList3, 0);
				
				initYearMap.put(i, monthList1);
				replyYearMap.put(i, monthList2);
				totalYearMap.put(i, monthList3);
			}
			
			//System.out.println(monthList[1]);
			//int x;
			//System.out.println(x = ((int[])initYearMap.get(2016))[0]);
			
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				String filename = listOfFiles[n].getName();
				
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(rootDir + filename));
				JSONObject jsonObject = (JSONObject) obj;
				
				JSONArray listitems = (JSONArray) jsonObject.get("items");
				
				for(int i=0; i<listitems.size(); i++)
				{
					JSONObject listitem = (JSONObject) listitems.get(i);
					
					String type = listitem.get("type").toString().trim();
					String date = listitem.get("published").toString().trim();
					
					StringTokenizer token = new StringTokenizer(date, "-,");
					
					int month = Integer.parseInt(token.nextToken());
					int day = Integer.parseInt(token.nextToken());
					int year = Integer.parseInt(token.nextToken());
					
					String time = token.nextToken().toString().trim();
					
					StringTokenizer timeToken = new StringTokenizer(time, ": ");
					
					int hour = Integer.parseInt(timeToken.nextToken());
					timeToken.nextToken();
					String am_pm = timeToken.nextToken();
					
					if(am_pm.equals("AM") && hour == 12)hour = 0;
					if(am_pm.equals("PM") && hour < 12)hour += 12;
					
					postTime[hour]++;
					
					//System.out.println(month + " " + day + " " + year);
					
					if(type.equals("init"))
					{
						int[] temp = initYearMap.get(year);
						temp[month] = temp[month] + 1;
						initYearMap.put(year, temp);
						
						temp = initTotalMap.get(year);
						temp[month] = temp[month] + 1;
						initTotalMap.put(year, temp);
					}
					else
					{
						int[] temp = replyYearMap.get(year);
						temp[month] = temp[month] + 1;
						replyYearMap.put(year, temp);
						
						temp = replyTotalMap.get(year);
						temp[month] = temp[month] + 1;
						replyTotalMap.put(year, temp);
					}
					
					int[] temp = totalYearMap.get(year);
					temp[month] = temp[month] + 1;
					totalYearMap.put(year, temp);
					
					temp = TotalMap.get(year);
					temp[month] = temp[month] + 1;
					TotalMap.put(year, temp);
					
					//////////////////////////////////////////////////////////////////////////////////////
					
					JSONObject actor = (JSONObject)listitem.get("actor");
					
					String name = actor.get("name").toString();
					String startDate = actor.get("startTime").toString();
					String gender = actor.get("gender").toString().trim();
					if(gender.equals(""))gender = "unspecified";
					
					userStartDate.put(name, startDate);
					userGender.put(name, gender);
					
					if(userPostCount.containsKey(name))
					{
						int tempCount = userPostCount.get(name);
						tempCount++;
						userPostCount.put(name, tempCount);
					}
					else 
					{
						userPostCount.put(name, 1);
					}
				}
			}
			
			initConditionMap.put(condition, initYearMap);
			replyConditionMap.put(condition, replyYearMap);
			ConditionMap.put(condition, totalYearMap);
			//bw.write(condition+"\n");
		}
		
		
//		int[] temporary = TotalMap.get(2016);
//		for(int k=0; k<temporary.length; k++)
//		{
//			System.out.println(temporary[k]);
//		}
//		///////////////////////////////user analysis///////////run this part to get user timeline//////////////////////////
//		int[] userMonthCount = new int[13];
//		Arrays.fill(userMonthCount, 0);
//		
//		TreeMap<Integer, Integer> userStartMap = new TreeMap<Integer, Integer>();
//		
//		for(int x=1997; x<=2016; x++)
//		{
//			for(int l=1; l<=12; l++)
//			{
//				String date;
//				if(l<10)
//				{
//					date = Integer.toString(x) + "0" + Integer.toString(l);
//				}
//				else date = Integer.toString(x)+Integer.toString(l);
//				
//				//System.out.println(date);
//				userStartMap.put(Integer.parseInt(date), 0);
//			}
//		}
//		
//		it = userStartDate.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String startDate = pair.getValue().toString().trim();
//			StringTokenizer token = new StringTokenizer(startDate, " ");
//			//System.out.println(startDate);
//			
//			if(startDate.equals(""))continue;
//			
//			String month = token.nextToken();
//			String year = token.nextToken();
//			
//			Integer monthID = 0;
//			
//			if(month.equals("Jan"))monthID = 1;
//			else if(month.equals("Feb"))monthID = 2;
//			else if(month.equals("Mar"))monthID = 3;
//			else if(month.equals("Apr"))monthID = 4;
//			else if(month.equals("May"))monthID = 5;
//			else if(month.equals("Jun"))monthID = 6;
//			else if(month.equals("Jul"))monthID = 7;
//			else if(month.equals("Aug"))monthID = 8;
//			else if(month.equals("Sep"))monthID = 9;
//			else if(month.equals("Oct"))monthID = 10;
//			else if(month.equals("Nov"))monthID = 11;
//			else if(month.equals("Dec"))monthID = 12;
//			
//			//String date = monthID.toString() + "/" + year;
//			
//			String date;
//			
//			if(monthID<10)
//			{
//				date = year + "0" + Integer.toString(monthID);
//			}
//			else date = year + Integer.toString(monthID);
//			
//			if(!userStartMap.containsKey(Integer.parseInt(date)))continue;
//			int tempCount = userStartMap.get(Integer.parseInt(date));
//			tempCount++;
//			userStartMap.put(Integer.parseInt(date), tempCount);
//			
//			
//			userMonthCount[monthID]++;
//			
//		}
//		
//		for(int m=1; m<13; m++)
//		{
//			bw.write(m+","+userMonthCount[m]+"\n");
//		}
//		bw.write("\n");
//		
//		
//		it = userStartMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String date = pair.getKey().toString();
//			//System.out.println(date);
//			String year = date.substring(0, 4);
//			String month = date.substring(4, 6);
//			bw.write(month + "/" + year + "," + pair.getValue().toString() + "\n");
//		}
//		
//		int male = 0, female = 0, unspecified = 0, malePost = 0, femalePost = 0, unspecPost = 0;
//		
//		it = userGender.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String name = pair.getKey().toString().trim();
//			int postCount = userPostCount.get(name);
//			String gender = pair.getValue().toString().trim();
//			if(gender.equals("male"))
//			{
//				male++;
//				malePost+=postCount;
//			}
//			else if(gender.equals("female"))
//			{
//				female++;
//				femalePost+=postCount;
//			}
//			else 
//			{
//				unspecified++;
//				unspecPost+=postCount;
//			}
//			
//		}
//		
//		bw.write("male," + male + "\nfemale," + female + "\nunspecified," + unspecified + "\n");
//		bw.write("malePostAvg," + formatter.format((double)malePost/male) + "\nfemalePostAvg," + formatter.format((double)femalePost/female) + 
//				"\nunspecifiedPostAvg," + formatter.format((double)unspecPost/unspecified) + "\n");
		
/////////////////////////////////////////////user analysis ends here/////////////////////////////////////////////////////////////////		
		
//////////////////////Postwise analysis/////////////run this part to get timelines of posts//////////////////////////////////////////		
		
//		it = initConditionMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String cond = pair.getKey().toString();
//			HashMap<Integer, int[]> yearMap = (HashMap<Integer, int[]>) pair.getValue();
//			
//			bw.write("Thread Initiator for "+cond+"\n______________________________\n");
//			
//			Iterator it1 = yearMap.entrySet().iterator();
//			
//			while(it1.hasNext())
//			{
//				Map.Entry pair1 = (Map.Entry)it1.next();
//				Integer year = (Integer)pair1.getKey();
//				
//				int[] monthList = (int[])pair1.getValue();
//				
//				for(int m=1; m<monthList.length; m++)
//				{
//					bw.write(m+"/"+year+","+monthList[m]+"\n");
//				}
//			}
//			
//		}
//		//////////////////////////////////////////////////////////////////////////////////
//		
//		it = replyConditionMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String cond = pair.getKey().toString();
//			HashMap<Integer, int[]> yearMap = (HashMap<Integer, int[]>) pair.getValue();
//			
//			bw.write("Thread Reply for "+cond+"\n______________________________\n");
//			
//			Iterator it1 = yearMap.entrySet().iterator();
//			
//			while(it1.hasNext())
//			{
//				Map.Entry pair1 = (Map.Entry)it1.next();
//				Integer year = (Integer)pair1.getKey();
//				
//				int[] monthList = (int[])pair1.getValue();
//				
//				for(int m=1; m<monthList.length; m++)
//				{
//					bw.write(m+"/"+year+","+monthList[m]+"\n");
//				}
//			}
//			
//		}
//		//////////////////////////////////////////////////////////////////////////////////
//		
//		it = ConditionMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			String cond = pair.getKey().toString();
//			HashMap<Integer, int[]> yearMap = (HashMap<Integer, int[]>) pair.getValue();
//			
//			bw.write("Total post for "+cond+"\n______________________________\n");
//			
//			Iterator it1 = yearMap.entrySet().iterator();
//			
//			while(it1.hasNext())
//			{
//				Map.Entry pair1 = (Map.Entry)it1.next();
//				Integer year = (Integer)pair1.getKey();
//				
//				int[] monthList = (int[])pair1.getValue();
//				
//				for(int m=1; m<monthList.length; m++)
//				{
//					bw.write(m+"/"+year+","+monthList[m]+"\n");
//				}
//			}
//			
//		}
//		//////////////////////////////////////////////////////////////////////////////////
//		
//		it = initTotalMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			Integer year = (Integer)pair.getKey();
//			
//			bw.write("Thread Initiator \n______________________________\n");
//			
//			int[] monthList = (int[])pair.getValue();
//			
//			for(int m=1; m<monthList.length; m++)
//			{
//				bw.write(m+"/"+year+","+monthList[m]+"\n");
//			}
//			
//		}
//		//////////////////////////////////////////////////////////////////////////////////
//		
//		it = replyTotalMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			Integer year = (Integer)pair.getKey();
//			
//			bw.write("Reply \n______________________________\n");
//			
//			int[] monthList = (int[])pair.getValue();
//			
//			for(int m=1; m<monthList.length; m++)
//			{
//				bw.write(m+"/"+year+","+monthList[m]+"\n");
//			}
//			
//		}
//		//////////////////////////////////////////////////////////////////////////////////
//		
//		it = TotalMap.entrySet().iterator();
//		
//		while(it.hasNext())
//		{
//			Map.Entry pair = (Map.Entry)it.next();
//			Integer year = (Integer)pair.getKey();
//			
//			bw.write("Total \n______________________________\n");
//			
//			int[] monthList = (int[])pair.getValue();
//			
//			for(int m=1; m<monthList.length; m++)
//			{
//				bw.write(m+"/"+year+","+monthList[m]+"\n");
//			}
//			
//		}
		//////////////////////////////post analysis ends here////////////////////////////////////////////////////
		
		for(int i=0; i<24; i++)
			bw.write(i + "," + postTime[i] + "\n");
		
		bw.close();

		
	}

}
