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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SampleClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3030;
    private static final String PATH = "\\src\\main\\java\\it\\unimib\\finalproject\\server\\preloadedData.json";
    public static void testConnect() {
        
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            
            // Create mapper for JSON serialization/deserialization
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode preloadedJson = null;

            // Wait for the connection to be established
            while(!socket.isConnected());

            System.out.println("1");
            HashMap<String, Integer> seats = new HashMap<String, Integer>();
            seats.put("h1", 91);
            seats.put("h2", 91);
            seats.put("h3", 91);
            seats.put("h4", 91);
            seats.put("h5", 91);

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
            
            System.out.println("2");
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

            System.out.println("3");
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

                    while (times.hasNext()) {
                        time = date + times.next().asText();
                        out.println("add " + date + " " + time);
                        in.readLine();

                        while (halls.hasNext()) {
                            hall = halls.next().asText();
                            hallFull = time + hall;
                            out.println("add " + time + " " + hallFull);
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

            // Send commands to the server and read responses

            //String movies = "Spiderman,Osu,Rem";
            // for (String movie : movies.split(",")) {
            //     System.out.println(movie);
            //     out.println("add movies " + movie);
            //     in.readLine();
            // }

            // //String dates = "2021-01-01,2021-01-02,2021-01-03";
            // for (String date : dates.split(",")) {
            //     out.println("add dates " + date);
            //     in.readLine();
            // }

            // //String times = "10:00,12:00,14:00,16:00,18:00,20:00,22:00";
            // for (String time : times.split(",")) {
            //     out.println("add times " + time);
            //     in.readLine();
            // }

            // //String halls = "h1,h2,h3";
            // for (String hall : halls.split(",")) {
            //     out.println("add halls " + hall);
            //     in.readLine();
            // }

            // //String seats = "s1,s2,s3,s4,s5,s6,s7,s8,s9,s10";
            // for (String seat : seats.split(",")) {
            //     out.println("add seats " + seat);
            //     in.readLine();
            // }

 
            // TODO aggiungi nel for (dinamico)
            // adding films to the database and linking an ID to the films
            /*
            out.println("set m1 Spiderman");
            in.readLine();
            out.println("set m2 Osu");
            in.readLine();
            out.println("set m3 Rem");
            in.readLine();


            // adding dates to the films
            out.println("add m1 d1");
            in.readLine();
            out.println("add m1 d2");
            in.readLine();
            out.println("add m2 d1");
            in.readLine();

            out.println("set d1 2021-01-01");
            in.readLine();
            out.println("set d2 2021-01-02");
            in.readLine();
            out.println("set d3 2021-01-03");
            in.readLine();

            // adding times to the dates
            out.println("add d1 t1");
            in.readLine();
            out.println("add d1 t2");
            in.readLine();
            out.println("add d2 t2");
            in.readLine();
            out.println("add d2 t4");
            in.readLine();
            out.println("add d3 t1");
            in.readLine();
            out.println("add d3 t5");
            in.readLine();
            out.println("add d3 t7");
            in.readLine();

            out.println("set t1 10:00");
            in.readLine();
            out.println("set t2 12:00");
            in.readLine();
            out.println("set t3 14:00");
            in.readLine();
            out.println("set t4 16:00");
            in.readLine();
            out.println("set t5 18:00");
            in.readLine();
            out.println("set t6 20:00");
            in.readLine();
            out.println("set t7 22:00");
            in.readLine();

            // adding halls to the combination of films, dates and times (checking the associations between previous films, dates and times)
            out.println("add m1d1t1 h1");
            in.readLine();
            out.println("add m1d1t1 h2");
            in.readLine();
            out.println("add m1d1t1 h3");
            in.readLine();
            out.println("add m1d1t1 h4");
            in.readLine();
            out.println("add m1d1t1 h5");
            in.readLine();
            out.println("add m2d1t1 h1");
            in.readLine();
            out.println("add m2d1t1 h2");
            in.readLine();
            out.println("add m2d1t2 h1");
            in.readLine();

            for (int i = 1; i <= 90; i++) {
                out.println("add m1d1t1h1 0");
                in.readLine();
            }
            out.println("add m1d1t1h1 1");
            in.readLine();

            out.println("add t1 h1");
            in.readLine();
            out.println("add t1 h2");
            in.readLine();
            out.println("add t2 h1");
            in.readLine();
            out.println("add t2 h3");
            in.readLine();
            out.println("add t3 h1");
            in.readLine();
            out.println("add t3 h2");
            in.readLine();
            out.println("add t4 h1");
            in.readLine();
            out.println("add t4 h2");
            in.readLine();
            out.println("add t5 h3");
            in.readLine();
            out.println("add t5 h2");
            in.readLine();
            out.println("add t6 h1");
            in.readLine();
            out.println("add t6 h3");
            in.readLine();
            out.println("add t7 h1");
            in.readLine();
            out.println("add t7 h2");
            in.readLine();

            out.println("set h1 hallA");
            in.readLine();
            out.println("set h2 hallB");
            in.readLine();
            out.println("set h3 hallC");
            in.readLine();
            out.println("set h4 hallD");
            in.readLine();
            out.println("set h5 hallE");
            in.readLine();


            

            // adding seats to the halls (10 seats for each hall)

            // for (int i = 1; i <= 10; i++) {
            //     out.println("add h1 s" + i);
            //     in.readLine();
            //     out.println("add h2 s" + i);
            //     in.readLine();
            //     out.println("add h3 s" + i);
            //     in.readLine();
            // }

            // for (int i = 1; i <= 10; i++) {
            //     out.println("set s" + i + " seat" + i);
            //     in.readLine();
            // }

            */

            // Close the connection
            socket.close();
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
