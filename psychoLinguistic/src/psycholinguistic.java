import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.*;


public class psycholinguistic {

	public static void main(String[] args) throws FileNotFoundException, IOException {
    	DecimalFormat four = new DecimalFormat("#0.0000");
    	String root = "/home/farigys/Documents/daily-strength/";
    	
    	Trie t = new Trie();
    	
    	ArrayList<String> psywordlist = new ArrayList<String>();
    	
    	HashSet<Pattern> regexset = new HashSet<Pattern>();
    	
    	HashSet<String> dict = new HashSet<String>();
    	
    	String rootDir = root+"Temporary Items/LIWC/";
    	
    	File  fregex = new File(rootDir+"/LIWC2001_English.dic");
	    FileInputStream fisregex = new FileInputStream(fregex); 
	    BufferedReader readerregex = new BufferedReader(new InputStreamReader(fisregex));
	    String line = "";
	    readerregex.readLine();
	    
	    while((line = readerregex.readLine())!=null)
	    {
	    	StringTokenizer token = new StringTokenizer(line, "\t");
	    	String regex1 = token.nextToken();
	    	if(regex1.contains("*"))dict.add(regex1);
	    	else
	    	{
	    		Pattern pattern1 = Pattern.compile(regex1);
		    	//System.out.println(regex1);
		    	regexset.add(pattern1);
	    	}
	    	t.insert(regex1);
	    	
	    }
    	System.out.println("dictionary/trie created");
    	
    	
    	
    	readerregex.close();
    	
    	File  f = new File(rootDir+"/test.txt");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    line = "";
	    HashMap<String, Integer> wordmap = new HashMap<String, Integer>(); 
	    
	    while((line = reader.readLine())!=null)
	    {
    		StringTokenizer tokenize = new StringTokenizer(line," ");
    		
	    	while(tokenize.hasMoreTokens())
          	{
	    		String currUnigram = tokenize.nextToken();
          		currUnigram = currUnigram.replace("/", "_");
          		StringTokenizer tokenize1 = new StringTokenizer(currUnigram, "_");
          		String match = tokenize1.nextToken();
          		String pclass = tokenize1.nextToken();
          		if(!pclass.matches("\\w*"))continue;
          		if(wordmap.containsKey(currUnigram))
          		{
          			int count = wordmap.get(currUnigram);
          			count++;
          			wordmap.put(currUnigram, count);
          		}
          		else
          		{
          			int count = 0;
          			count++;
          			wordmap.put(currUnigram, count);
          		}
          	}
          		
          	//}
	    }
	    
	    Iterator it = wordmap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	        String key = pair.getKey().toString();
	        StringTokenizer tokenize2 = new StringTokenizer(key, "_");
      		String match = tokenize2.nextToken();
	        if(t.search(match))
	        {
	        	System.out.println(key+" "+pair.getValue());
	        }
	    }
	    
//	    while((line = reader.readLine())!=null)
//	    {
//    		StringTokenizer tokenize = new StringTokenizer(line," ");
//	    	while(tokenize.hasMoreTokens())
//          	{
//          		String currUnigram = tokenize.nextToken();
//          		currUnigram = currUnigram.replace("/", "_");
//          		StringTokenizer tokenize1 = new StringTokenizer(currUnigram, "_");
//          		String match = tokenize1.nextToken();
//          		String pclass = tokenize1.nextToken();
//          		if(pclass.matches("\\w*"))continue;
//          		if(dict.contains(match.toLowerCase()))
//          		{
//          			psywordlist.add(currUnigram);
//          			continue;
//          		}
//          		
//          		//Matcher matcher = pattern.matcher(console.readLine("Enter input string to search: "));
//          	}
//	    	
//	    }
	}
}

class TrieNode 
{
    char content; 
    boolean isEnd; 
    int count;  
    LinkedList<TrieNode> childList; 
 
    /* Constructor */
    public TrieNode(char c)
    {
        childList = new LinkedList<TrieNode>();
        isEnd = false;
        content = c;
        count = 0;
    }  
    public TrieNode subNode(char c)
    {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.content == c)
                    return eachChild;
        return null;
    }
}
 
class Trie
{
    private TrieNode root;
 
     /* Constructor */
    public Trie()
    {
        root = new TrieNode(' '); 
    }
     /* Function to insert word */
    public void insert(String word)
    {
        if (search(word) == true) 
            return;        
        TrieNode current = root; 
        for (char ch : word.toCharArray() )
        {
            TrieNode child = current.subNode(ch);
            if (child != null)
                current = child;
            else 
            {
                 current.childList.add(new TrieNode(ch));
                 current = current.subNode(ch);
            }
            current.count++;
        }
        current.isEnd = true;
    }
    /* Function to search for word */
    public boolean search(String word)
    {
    	System.out.println("current word: "+word);
        TrieNode current = root;  
        for (char ch : word.toCharArray() )
        {
        	System.out.println("current char: "+current.content);
        		
            if (current.subNode(ch) == null)
            {
            	//System.out.println("dhuksi null");
            	LinkedList<TrieNode> childList1 = current.childList;
            	Iterator it = childList1.iterator();
        	    while (it.hasNext()) {
        	    	TrieNode temp = (TrieNode) it.next();
        	    	//System.out.print(temp.content);
        	    	if(temp.content == '*')return true;
        	    }
            	return false;
            }
                          
            else
            {
            	current = current.subNode(ch);
            }
                
        }      
        if (current.isEnd == true) 
            return true;
        return false;
    }
    /* Function to remove a word */
    public void remove(String word)
    {
        if (search(word) == false)
        {
            System.out.println(word +" does not exist in trie\n");
            return;
        }             
        TrieNode current = root;
        for (char ch : word.toCharArray()) 
        { 
            TrieNode child = current.subNode(ch);
            if (child.count == 1) 
            {
                current.childList.remove(child);
                return;
            } 
            else 
            {
                child.count--;
                current = child;
            }
        }
        current.isEnd = false;
    }
}    

