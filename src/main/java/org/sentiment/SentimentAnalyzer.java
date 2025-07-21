package org.sentiment;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.Properties;

public class SentimentAnalyzer {
    private static StanfordCoreNLP pipeline;

    public static void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        System.out.println("Initializing CoreNLP Pipeline... (This may take a moment)");
        pipeline = new StanfordCoreNLP(props);
        System.out.println("Pipeline initialized successfully.");
    }

    public static String findSentiment(String text) {
        if (text == null || text.isEmpty()) return "Neutral";
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            // Simplify CoreNLP's 5 labels to 3 for our UI
            switch (sentiment) {
                case "Very positive":
                case "Positive":
                    return "Positive";
                case "Very negative":
                case "Negative":
                    return "Negative";
                default:
                    return "Neutral";
            }
        }
        return "Neutral";
    }
}