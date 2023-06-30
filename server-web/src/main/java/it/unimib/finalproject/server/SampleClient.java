package it.unimib.finalproject.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SampleClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3030;
    private static final String PATH = "\\src\\main\\java\\it\\unimib\\finalproject\\server\\preloadedData.json";
    public static void populateDB() {
        
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            
            // Create mapper for JSON serialization/deserialization
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode preloadedJson = null;

            // Wait for the connection to be established
            while(!socket.isConnected());

            HashMap<String, Integer> seats = new HashMap<String, Integer>();
            seats.put("h1", 91);
            seats.put("h2", 70);
            seats.put("h3", 52);
            seats.put("h4", 88);
            seats.put("h5", 81);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // convert a JSON string to an ObjectNode
            preloadedJson = mapper.readValue(Paths.get(System.getProperty("user.dir") + PATH).toFile(), ObjectNode.class);

            // get data from the JSON file
            Iterator<Map.Entry<String,JsonNode>> itSingle = preloadedJson.get("single").fields();
            Iterator<Map.Entry<String,JsonNode>> itMovies = preloadedJson.get("movies").fields();

            String key = null;
            Map.Entry<String,JsonNode> entry;
            JsonNode value;
            
            while (itSingle.hasNext()) {
                entry = itSingle.next();
                key = entry.getKey();
                value = entry.getValue();

                out.println("set " + key + " " + value.asText());
                in.readLine();
            }

            Iterator<JsonNode> dates;
            Iterator<JsonNode> times;
            Iterator<JsonNode> halls;

            while (itMovies.hasNext()) {
                entry = itMovies.next();
                key = entry.getKey();
                value = entry.getValue();

                out.println("set " + key + " \"" + value.get("name").asText() + "\"");
                in.readLine();

                dates = value.get("dates").elements();
                times = value.get("times").elements();
                halls = value.get("halls").elements();
                String date, time, hall, hallFull, string;

                while (dates.hasNext()) {
                    date = key + dates.next().asText();
                    out.println("add " + key + " " + date);
                    in.readLine();

                    times = value.get("times").elements();
                    while (times.hasNext()) {
                        time = date + times.next().asText();
                        out.println("add " + date + " " + time);
                        in.readLine();

                        halls = value.get("halls").elements();
                        while (halls.hasNext()) {
                            hall = halls.next().asText();
                            hallFull = time + hall;
                            out.println("add " + time + " " + hallFull);
                            in.readLine();

                            out.println("set " + hallFull + " " + hall);
                            in.readLine();

                            string = "";
                            for (int i = 1; i <= seats.get(hall); i++) {
                                string += " 0";
                            }
                            out.println("madd " + hallFull +  " " + string);
                            in.readLine();
                        }
                    }
                }

                out.println("add movies " + key);
                in.readLine();
            }

            out.println("disconnect");
            in.readLine();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
