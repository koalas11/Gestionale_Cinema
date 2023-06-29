package it.unimib.finalproject.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SampleClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3030;

    public static void testConnect() {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);

            // Wait for the connection to be established
            while(!socket.isConnected());

            
            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send commands to the server and read responses

            String films = "Spiderman,Osu,Rem";
            for (String film : films.split(",")) {
                out.println("add movies " + film);
                in.readLine();
            }

            String dates = "2021-01-01,2021-01-02,2021-01-03";
            for (String date : dates.split(",")) {
                out.println("add dates " + date);
                in.readLine();
            }

            String times = "10:00,12:00,14:00,16:00,18:00,20:00,22:00";
            for (String time : times.split(",")) {
                out.println("add times " + time);
                in.readLine();
            }

 
            // TODO aggiungi nel for (dinamico)
            // adding films to the database and linking an ID to the films
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

            // Close the connection
            socket.close();
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
