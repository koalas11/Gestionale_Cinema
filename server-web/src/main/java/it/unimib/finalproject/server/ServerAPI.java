package it.unimib.finalproject.server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;
import java.net.*;

/**
 * Rappresenta la risorsa esposta in "http://localhost:8080/movies".
 */
@Path("movies")
public class ServerAPI {
    private static AtomicLong idCounter = new AtomicLong();
    /**
     * Implementazione TEST di GET "/movies".
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
            String command = makeCommand("mget", response.get("movies"));
            json = sendRequest(command, in, out);
            response = json.get(1);

            closeConnection(in, out, socket);
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
    public Response getMovie(@PathParam("id") String id) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("mget" + " Desc" + id + " Reg" + id, in, out);
            response = json.get(1);

            if (response == null || !response.hasNonNull(id)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
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

            if (response == null || !response.hasNonNull(id)) {// Film not found.
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            String request = makeCommand("mget", response.get(id)).replace(id, "");

            response = sendRequest(request, in, out).get(1);

            if (response == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
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

            String totalId = id + dateID;
            // send command to the database and read responses
            json = sendRequest("getall " + totalId, in, out);
            response = json.get(1);

            if (response == null || !response.hasNonNull(totalId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String request = makeCommand("mget", response.get(totalId)).replace(totalId, "");

            response = sendRequest(request, in, out);

            if (response == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response.get(1)).build();
    }

    /**
     * Implementazione di GET "/movies/{id}/dates/{date}/times/{time}/halls".
     */
    @Path("/{id}/dates/{date}/times/{time}/halls")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHalls(@PathParam("id") String id, @PathParam("date") String dateID,
            @PathParam("time") String timeID) {
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

            System.out.println("ok");
            // send command to the database and read responses
            json = sendRequest("mgetall " + id + " " + dateID + " " + timeID, in, out);
            response = json.get(1);

            if (response == null || !(keyValueExists(response.get(id), dateID) && keyValueExists(response.get(dateID), timeID))) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            json = sendRequest("mgetall " + id + dateID + timeID, in, out);
            response = json.get(1);

            if (response == null || !response.hasNonNull(id + dateID + timeID)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /**
     * Implementazione di GET
     * "/movies/{id}/dates/{date}/times/{time}/halls/{hall}/seats".
     */
    @Path("/{id}/dates/{date}/times/{time}/halls/{hall}/seats")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeats(@PathParam("id") String id, @PathParam("date") String dateID,
            @PathParam("time") String timeID, @PathParam("hall") String hallID) {
        ObjectNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        timeID = "t" + timeID;
        hallID = "h" + hallID;

        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("mgetall " + id + " " + dateID + " " + timeID + " " + hallID, in, out).get(1);

            if (!(keyValueExists(json.get(id), dateID) && keyValueExists(json.get(dateID), timeID)
                    && keyValueExists(json.get(timeID), hallID))) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // send command to the database and read responses
            String fullId = id + dateID + timeID + hallID;
            json = sendRequest("mgetall " + fullId, in, out).get(1);

            if (json == null || !json.hasNonNull(fullId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectNode seats = (ObjectNode) json;
            response = changeSeats(seats, fullId);

            json = sendRequest("get " + hallID, in, out);
            response = (ObjectNode) json.get(1);
            response = response.setAll(seats);

            if (response == null || !response.hasNonNull(hallID)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (Exception e) {
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

            json = sendRequest("mgetall movies dates times halls seats", in, out);
            response = json.get(1);

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /* 
     * Implementazione di POST "/movies/{id}/dates/{date}/times/{time}/halls/{hall}/seats/{seats}".
    */
    @Path("/{id}/dates/{date}/times/{time}/halls/{hall}/seats/{seats}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setBooking(@PathParam("id") String id, @PathParam("date") String dateID,
            @PathParam("time") String timeID, @PathParam("hall") String hallID, @PathParam("seats") String seatID)   {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        timeID = "t" + timeID;
        hallID = "h" + hallID;
        
        // create a String array splitting seatID with commas as delimiters ex "1,2,3" -> ["1", "2", "3"]
        String[] seats = seatID.split(",");

        for (String seat : seats) {
            System.out.println("TEST SEATS -> " + seat);
        }
        
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String totalId = id + dateID + timeID + hallID;
            String idBooking = createID();
            String command = "msetifl";

            for (String s : seats) {
                command += " " + totalId + " " + s + " " + idBooking + " 0";
            }
            
            json = sendRequest(command, in, out);
            if (json == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readTree("{\"Id\" : \""+ idBooking + "\"}");

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }
    


    /*
     * close the connection with database and closes the socket
     */
    private void closeConnection(BufferedReader in, PrintWriter out, Socket socket) throws IOException {
        out.println("disconnect");
        String goodbye = in.readLine();
        System.out.println(goodbye);
        socket.close();
    }

    private ObjectNode changeSeats(ObjectNode seats, String fullId) {
        ArrayNode list = (ArrayNode) seats.get(fullId);
        
        for (int index = 0; index < list.size(); index++) {
            if (!"0".equals(list.get(index).asText()))
                list.set(index, "1");
        }

		return seats.set(fullId, list);
	}

    private String makeCommand(String string, JsonNode json) {
        if (string == null || json == null)
            return null;
        Iterator<JsonNode> it = json.elements();

        
        while (it.hasNext()) {
            string += " " + it.next().asText();
        }

        return string;
    }

    private boolean keyValueExists(JsonNode json, String field) {
        if (field == null || json == null) {
            return false;
        }

        Iterator<JsonNode> it = json.elements();

        while (it.hasNext()) {
            if (field.equals(it.next().asText())) {
                return true;
            }
        }

        return false;
    }

    private static String createID()
    {
        return "B" + String.valueOf(idCounter.getAndIncrement());
    }

    // send command to the database and read responses
    private JsonNode sendRequest(String input, BufferedReader in, PrintWriter out) throws IOException {

        // send command to the database and read responses
        System.out.println(input);
        out.println(input);
        String response = in.readLine();

        if (response == null) // Film non trovato.
            return null;
        // return Response.status(Response.Status.NOT_FOUND).build();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        // get(0) returns true if the request was successful or false otherwise
        // get(1) returns the actual data
        boolean check = jsonNode.get(0).asBoolean();
        if (!check) {
            System.out.println("Error: " + jsonNode.get(1));
            return null;
        }
        // Error :C

        System.out.println(jsonNode);
        return jsonNode;
    }


     
    
    /*
     * Implementazione di GET
     * "/movies/{id}/dates/{date}/times/{time}/halls/{hall}/seats/{seat}".
     */
    /*@Path("/{id}/dates/{date}/times/{time}/halls/{hall}/seats/{seat}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeats(@PathParam("id") String id, @PathParam("date") String dateID,
            @PathParam("time") String timeID, @PathParam("hall") String hallID, @PathParam("seat") String seatID) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        dateID = "d" + dateID;
        timeID = "t" + timeID;
        hallID = "h" + hallID;
        seatID = "s" + seatID;

        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 3030);

            // Create input and output streams for communication
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("mgetall " + id + " " + dateID + " " + timeID + " " + hallID, in, out).get(1);
            System.out.println("related halls" + json);

            if (!(keyValueExists(json.get(id), dateID) && keyValueExists(json.get(dateID), timeID) && keyValueExists(json.get(timeID), hallID) && keyValueExists(json.get(hallID), seatID))) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // send command to the database and read responses
            json = sendRequest("mget " + id + " " + dateID + " " + timeID + " " + hallID + " " + seatID, in, out);
            response = json.get(1);

            if (response == null) { // Hall not found.
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // close the connection
            out.println("disconnect");
            String goodbye = in.readLine();
            System.out.println(goodbye);

            // Close the connection
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }
    */
}
