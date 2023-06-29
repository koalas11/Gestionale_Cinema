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

            System.out.println("GOT ");
            // send command to the database and read responses
            json = sendRequest("getall movies", in, out);
            response = json.get(1);
            
            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);
            
            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        return Response.ok(response).build();
    }


    
    /**
     * Implementazione di GET "/movies/{id}".
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("id") String id) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // send command to the database and read responses
            json = sendRequest("get " + id, in, out);
            response = json.get(1);

            if (response == null || !response.hasNonNull(id)) // Movie not found.
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

    

    /** 
     * Implementazione di GET "/movies/{id}/dates".
     */

    @Path("/{id}/dates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDates(@PathParam("id") String id) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
            // send command to the database and read responses
            json = sendRequest("getall " + id, in, out);
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
    public Response getDate(@PathParam("id") String id, @PathParam("date") String dateID) {
        JsonNode response = null;
        JsonNode json = null;
        Boolean exists = false;
        id = "m" + id;
        dateID = "d" + dateID;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);
            

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // getting json to check associations
            json = sendRequest("getall " + id, in, out).get(1).get(id);
            System.out.println("test" + json);
            

            if (!keyValueExists(json, dateID)) // Association not found.
                return Response.status(Response.Status.NOT_FOUND).build();
            
            json = sendRequest("mget " + id + " " + dateID, in, out);
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
    
    /** 
     * Implementazione di GET "/movies/{id}/dates/{date}/times".
     */

    @Path("/{id}/dates/{date}/times")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimes(@PathParam("id") String id, @PathParam("date") String dateID) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mgetall " + id + " " + dateID, in, out);
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
    public Response getTime(@PathParam("id") String id, @PathParam("date") String dateID, @PathParam("time") String timeID) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        timeID = "t" + timeID;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            json = sendRequest("mgetall " + id + " " + dateID, in, out).get(1);
            System.out.println("related times" + json);
            
            if (!(keyValueExists(json.get(id), dateID) && keyValueExists(json.get(dateID), timeID))) // Association not found.
                return Response.status(Response.Status.NOT_FOUND).build();

            // send command to the database and read responses
            json = sendRequest("mget " + id + " " + dateID + " " + timeID, in, out);
            response = json.get(1);

            if (response == null) // Time not found.
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

    /**
     * Implementazione di GET "/movies/{id}/dates/{date}/times/{time}/halls".
     */
    @Path("/{id}/dates/{date}/times/{time}/halls")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHalls(@PathParam("id") String id, @PathParam("date") String dateID, @PathParam("time") String timeID) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        timeID = "t" + timeID;
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mgetall " + id + " " + dateID + " " + timeID, in, out);
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



    private boolean keyValueExists(JsonNode json, String field) {
        if (field == null || json == null)
            return false;

        Iterator<JsonNode> it = json.elements();

        while (it.hasNext()) {
            if (field.equals(it.next().asText())) {
                return true;   
            }
        }

        return false;
    }

    // send command to the database and read responses
    private JsonNode sendRequest(String input, BufferedReader in, PrintWriter out) throws IOException {

        // send command to the database and read responses
        out.println(input);
        String response = in.readLine();
        
        if (response == null) // Film non trovato.
            return null;
            //return Response.status(Response.Status.NOT_FOUND).build();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        // get(0) returns true if the request was successful or false otherwise
        // get(1) returns the actual data 
        boolean check = jsonNode.get(0).asBoolean();
        if (!check)
            return null;
            // Error :C

        return jsonNode;
    }


        
    // /**
    // TODO take "m,d,t,h,s", for each letter in current path, check if association exists m,d -> d,t... (need a PathParam list to make it a method and generic).
    //  */
    // @Path("/{id}/dates/{date}/times/{time}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getTest(@PathParam("id") int id, @PathParam("date") String dateID, @PathParam("time") String timeID) {
    //     JsonNode response = null;
    //     JsonNode json = null;
    //     try {
    //         // Connect to the server
    //         Socket socket = new Socket("localhost", 3030);

    //         // Create input and output streams for communication
    //         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    //         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    //         // getting json to check associations
    //         //jsonDates = sendRequest("getall m" + id, in, out).get(1).get(id);
    //         json = sendRequest("mgetall m" + id + " d" + dateID, in, out).get(1).get(dateID);
    //         System.out.println("test" + json);

    //         // test brutti in arrivo :'(
    //         String test = "m,d,t,h,s";
    //         // for each letter in test splitted by commas check if the association exists
    //         for (String s : test.split(",")) {
    //             if (!keyValueExists(json, s + timeID)) // Association not found.
    //                 return Response.status(Response.Status.NOT_FOUND).build();
    //         }

    //         if (!keyValueExists(json, timeID)) // Association not found.
    //             return Response.status(Response.Status.NOT_FOUND).build();


    //         // send command to the database and read responses
    //         json = sendRequest("mget m" + id + " d" + dateID + " t" + timeID, in, out);
    //         response = json.get(1);
            

    //         if (response == null) // Data non trovata.
    //             return Response.status(Response.Status.NOT_FOUND).build();
            

    //         // close the connection
    //         out.println("disconnect");
    //         String goodbye = in.readLine();
    //         System.out.println(goodbye);
            
    //         // Close the connection
    //         socket.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return Response.ok(response).build();
    // }

    // private boolean keyValueExists(JsonNode json, String field) {
    //     if (field == null || json == null)
    //         return false;

    //     Iterator<JsonNode> it = json.elements();

    //     while (it.hasNext()) {
    //         if (field.equals(it.next().asText())) {
    //             return true;   
    //         }
    //     }
    //     return false;
    // }


}
