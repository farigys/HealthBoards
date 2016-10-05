import java.io.File;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class fileFilter {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String[] conditions = {"depression"};
		  //String[] conditions = {"scoliosis"};
		  
		  
			for(int c=0; c<conditions.length; c++)
			{
String condition = conditions[c];
				
				System.out.println("Now processing: " + condition + "\n_____________________________________________________________");
				
				String root = "/home/farigys/Health_Board/" + condition;
				
				String rootDir = root + "/HTML/";
				
				File folder = new File(rootDir);
				
				File[] listOfFiles = folder.listFiles();
				
				int count = 0;
				
				for(int n=0; n<listOfFiles.length; n++)
				{
					String filename = listOfFiles[n].getName();
					String[] nameParts = filename.split("\\.");
					String name = nameParts[0];
					String[] parts = name.split("-");
					int length = parts.length;
					if(parts[length-1].matches("[0-9]+") && parts[length-2].matches("[0-9]+"))
					{
						//File file = new File(rootDir + filename);
						//file.delete();
						System.out.println(filename);
					}
				}
				System.out.println(count);
			}
	}
}
