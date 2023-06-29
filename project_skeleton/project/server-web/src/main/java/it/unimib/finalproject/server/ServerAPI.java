package it.unimib.finalproject.server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Rappresenta la risorsa esposta in "http://localhost:8080/movies".
 */
@Path("movies")
public class ServerAPI {

    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Implementazione  TEST di GET "/movies".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
            // send command to the database and read responses
            json = sendRequest("getall movies", in, out);
            response = json.get(1);
            
            //System.out.println("TEST: " + response);
            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }


    
    /**
     * Implementazione di GET "/movies/{id}".
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("id") int id) {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
            // send command to the database and read responses
            json = sendRequest("get m" + id, in, out);
            response = json.get(1);
            

            if (response == null) // Film non trovato.
                return Response.status(Response.Status.NOT_FOUND).build();
            
            //System.out.println("TEST: " + response);
            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }

    

    /** 
     * Implementazione di GET "/movies/{id}/dates".
     */

    @Path("/{id}/dates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDates(@PathParam("id") int id) {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
            // send command to the database and read responses
            json = sendRequest("getall m" + id, in, out);
            response = json.get(1);
            
            if (response == null) // Film non trovato.
                return Response.status(Response.Status.NOT_FOUND).build();
            System.out.println("trovato film: " + response);

            
            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }

    /** 
     * Implementazione di GET "/movies/{id}/dates/{date}".
     */

    @Path("/{id}/dates/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDate(@PathParam("id") int id, @PathParam("date") String dateID) {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mget m" + id + " d" + dateID, in, out);
            response = json.get(1);
            

            if (response == null) // Data non trovata.
                return Response.status(Response.Status.NOT_FOUND).build();
            System.out.println("Data del film: " + response);
            

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }
    
    /** 
     * Implementazione di GET "/movies/{id}/dates/{date}/times".
     */

    @Path("/{id}/dates/{date}/times")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimes(@PathParam("id") int id, @PathParam("date") String dateID) {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mgetall m" + id + " d" + dateID, in, out);
            response = json.get(1);
            

            if (response == null) // Data non trovata.
                return Response.status(Response.Status.NOT_FOUND).build();
            System.out.println("Data del film: " + response);
            

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /** 
     * Implementazione di GET "/movies/{id}/dates/{date}/times/{time}".
     */

    @Path("/{id}/dates/{date}/times/{time}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTime(@PathParam("id") int id, @PathParam("date") String dateID, @PathParam("time") String timeID) {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mget m" + id + " d" + dateID + " t" + timeID, in, out);
            response = json.get(1);
            

            if (response == null) // Data non trovata.
                return Response.status(Response.Status.NOT_FOUND).build();
            System.out.println("Ora del film: " + response);
            

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }


    /**
     * Implementazione TEST di GET "/movies/all".
     */
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        JsonNode response = null;
        JsonNode json = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);
            

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("mgetall movies dates times", in, out);

            response = json.get(1);
            
            System.out.println(json);
            System.out.println(response);

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Response.ok(response).build();
    }

    /**
     * Implementazione TEST di GET "/movies/{id}/test/{date}".
     */
    @Path("/{id}/test/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest(@PathParam("id") int id, @PathParam("date") String dateID) {
        JsonNode response = null;
        JsonNode json = null;
        JsonNode check = null;
        Boolean exists = false;
        JsonNode relatedDates = null;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);
            

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            relatedDates = sendRequest("getall m" + id, in, out);
            relatedDates = relatedDates.get(1);


            for (JsonNode date : relatedDates.get("m" + id)) {
                if (date.textValue().equals("d" + dateID)) {
                    exists = true;
                    break;
                }
            }

            if (exists == false) // Film non trovato.
                return Response.status(Response.Status.NOT_FOUND).build();
            
            json = sendRequest("mget m" + id + " d" + dateID, in, out);
            response = json.get(1);
            
            if (response == null) // Data non trovata.
                return Response.status(Response.Status.NOT_FOUND).build();

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Response.ok(response).build();
    }


    // send command to the database and read responses
    private JsonNode sendRequest(String input, BufferedReader in, PrintWriter out) throws IOException {

        // send command to the database and read responses
        out.println(input);
        String response = in.readLine();
        
        if (response == null) // Film non trovato.
            return null;
            //return Response.status(Response.Status.NOT_FOUND).build();

        //System.out.println("TEST: " + response);

        JsonNode jsonNode = objectMapper.readTree(response);
        // get(0) returns true if the request was successful or false if it was not
        // get(1) returns the actual data 
        boolean check = jsonNode.get(0).asBoolean();
        if (!check)
            return null;
            // Error :C

        return jsonNode;
    }

}
