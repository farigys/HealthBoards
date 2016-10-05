import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class sentimentAnalyzer {
    //static StanfordCoreNLP pipeline;

    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
    	String tweet = "hi about a yr and a few month ago " +
    			"my daughter went in for physical and her " +
    			"dr said oh she has scoliosis we has to travel " +
    			"2 hrs one way to see the dr for her she has been " +
    			"sick for about 2 months before that and had lost 20 " +
    			"LBS which i had told them that well as the weeks went " +
    			"on she had put all 20 LBS back on and the brace take was " +
    			"way to small they insisted that it was still fine and " +
    			"put larger straps on the brace it wasnt ok it was to " +
    			"small it would bruise her hips on both side and they still" +
    			" wouldnt replace with larger so she didnt wear the brace " +
    			"i kept taking her to the chiropractor 1 to 2 times a week " +
    			"she was always in pain so went back to her dr who found the" +
    			" scoliosis and told her what happen probably 5 months without " +
    			"the brace she found me another dr much closer they ordered the " +
    			"brace that took from nov to end of feb this yr they changed the" +
    			" type of brace and how many hour to be worn now its a bending " +
    			"brace and she got to choose zebra strips she started during " +
    			"the week and cant sleep at all felt bad she went to school " +
    			"pasty white with black ring so today is friday so tonight " +
    			"we will try again i told her she would wear it all weekend and " +
    			"we will see if she can get some sleep in it at least she will " +
    			"be able to take a nap during the day she started with one curve " +
    			"in the lower back it was at a 22 now the lower is a 27 and she " +
    			"now has a upper of 12 her spine is all the way to the left side " +
    			"of her ribs we still go to the chiropractor every week and she i" +
    			"s still is in pain the dr say there is no pain with scoliosis but " +
    			"she says there is and her back is as hard as a rock is there some way " +
    			"to help her get use to the bending brace she cant sit up in it as it " +
    			"bends so far over to the side and why was that type picked for her and " +
    			"is there anyway to help with the pain ";
    	Properties props = new Properties();
    	props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
    	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    	
    	int mainSentiment = 0;
        if (tweet != null && tweet.length() > 0) {
        	System.out.println(tweet.length());
            int longest = 0;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            	System.out.print(sentence);
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                System.out.println(":" + (sentiment -2));

            }
        }
        
        tweet = "Hello from the other side";
        
        Annotation annotation = pipeline.process(tweet);
        
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
        	Tree tree = sentence.get(SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.println(sentence + ":" + (sentiment -2));
        }
    	
    	System.out.println();
    	
    }
    
}

