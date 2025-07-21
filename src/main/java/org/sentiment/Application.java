package org.sentiment;
import com.google.gson.Gson;
import static spark.Spark.*;

public class Application {
    // Simple data classes to structure our JSON
    static class SentimentRequest { String text; }
    static class SentimentResponse {
        String sentiment;
        SentimentResponse(String sentiment) { this.sentiment = sentiment; }
    }

    public static void main(String[] args) {
        // Initialize the NLP model once when the server starts
        SentimentAnalyzer.init();

        // Configure the Spark server to run on port 4567
        port(4567);
        // Serve frontend files from the 'src/main/resources/public' folder
        staticFiles.location("/public");

        // Define the API endpoint for sentiment analysis
        post("/analyze", (request, response) -> {
            response.type("application/json");
            Gson gson = new Gson();

            // Parse the incoming JSON request
            SentimentRequest req = gson.fromJson(request.body(), SentimentRequest.class);
            // Perform the analysis
            String sentiment = SentimentAnalyzer.findSentiment(req.text);
            // Create our response object
            SentimentResponse res = new SentimentResponse(sentiment);

            // Convert our response object to a JSON string and return it
            return gson.toJson(res);
        });

        System.out.println("Server started on http://localhost:4567");
    }
}
