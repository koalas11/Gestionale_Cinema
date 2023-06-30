package it.unimib.finalproject.server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;
import java.net.*;

/**
 * Rappresentation of the resources at "http://localhost:8080/movies".
 */
@Path("movies")
public class ServerAPI {
    private static AtomicLong idCounter = new AtomicLong();
    /**
     * Implementation of GET "/movies".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        JsonNode response = null;
        JsonNode json = null;
        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("getall movies", in, out);
            if (json == null || !json.get(1).hasNonNull("movies")) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String command = makeCommand("mget", json.get(1).get("movies"));
            json = sendRequest(command, in, out);
            if (json == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            response = json.get(1);

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }

    /**
     * Implementation of GET "/movies/{id}/dates".
     */

    @Path("/{id}/dates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDates(@PathParam("id") String id) {
        JsonNode response = null;
        JsonNode json = null;
        id = "m" + id;
        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("getall " + id, in, out);
            
            if (json == null || !json.get(1).hasNonNull(id)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            String request = makeCommand("mget", json.get(1).get(id)).replace(id, "");

            response = sendRequest(request, in, out).get(1);

            if (response == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(response).build();
    }

    /**
     * Implementation of GET "/movies/{id}/dates/{date}/times".
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
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String totalId = id + dateID;
            json = sendRequest("getall " + totalId, in, out);

            if (json == null || !json.get(1).hasNonNull(totalId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String request = makeCommand("mget", json.get(1).get(totalId)).replace(totalId, "");

            response = sendRequest(request, in, out);

            if (response == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response.get(1)).build();
    }

    /**
     * Implementation of GET "/movies/{id}/dates/{date}/times/{time}/halls".
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
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String totalID = id + dateID + timeID;
            json = sendRequest("getall " + totalID, in, out);

            if (json.get(1) == null || !json.get(1).hasNonNull(totalID)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String request = makeCommand("mget", json.get(1).get(totalID)).replace(totalID, "");
            response = sendRequest(request, in, out);

            if (response == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response.get(1)).build();
    }

    /**
     * Implementation of GET
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

            // send command to the database and read responses
            String fullId = id + dateID + timeID + hallID;
            json = sendRequest("mgetall " + fullId, in, out);

            if (json == null || !json.get(1).hasNonNull(fullId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectNode seats = (ObjectNode) json.get(1);
            response = changeSeats(seats, fullId, null);

            json = sendRequest("get " + hallID, in, out);

            if (json == null || !json.get(1).hasNonNull(hallID)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            response = (ObjectNode) json.get(1);
            response = response.setAll(seats);

            closeConnection(in, out, socket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /**
     * Implementation of GET "/booking/{bookingId}/seats".
     */
    @Path("booking/{bookingId}/seats")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingId(@PathParam("bookingId") String bookingId) {
        ObjectNode response = null;
        JsonNode json = null;

        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send command to the database and read responses
            json = sendRequest("get " + bookingId, in, out);

            if (json == null || !json.get(1).hasNonNull(bookingId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String totalId = json.get(1).get(bookingId).asText();

            json = sendRequest("getAll " + totalId, in, out);

            if (json == null || !json.get(1).hasNonNull(totalId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectNode seats = (ObjectNode) json.get(1);
            response = changeSeats(seats, totalId, bookingId);

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /* 
     * Implementation of POST "/movies/{id}/dates/{date}/times/{time}/halls/{hall}/seats/{seats}".
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
        
        try {
            Socket socket = new Socket("localhost", 3030);

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
                return Response.status(Response.Status.CONFLICT).build();
            }

            json = sendRequest("set " + idBooking +  " " + totalId, in, out);
            if (json == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readTree("{\"Id\" : \""+ idBooking + "\"}");

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    /* 
     * Implementation of PUT "/booking/{bookingId}/seats/{seats}".
    */
    @Path("/booking/{bookingId}/seats/{seats}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("bookingId") String bookingId, @PathParam("seats") String seatID)   {
        JsonNode response = null;
        JsonNode json = null;
        // create a String array splitting seatID with commas as delimiters ex "1,2,3" -> ["1", "2", "3"]
        String[] seats = seatID.split(",");
        
        try {
            Socket socket = new Socket("localhost", 3030);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            json = sendRequest("get " + bookingId, in, out);
            if (json == null || !json.get(1).hasNonNull(bookingId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String totalId = json.get(1).get(bookingId).asText();
            String command = "msetifl";

            for (String s : seats) {
                command += " " + totalId + " " + s + " 0 " + bookingId;
            }
            
            json = sendRequest(command, in, out);
            if (json == null) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            json = sendRequest("getAll " + totalId, in, out);
            if (json == null || !json.get(1).hasNonNull(totalId)) {
                closeConnection(in, out, socket);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            if (checkRemovedAll(json.get(1).get(totalId), bookingId)) {
                json = sendRequest("delete " + bookingId, in, out);
                if (json == null) {
                    closeConnection(in, out, socket);
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readTree("\"Success\"");

            closeConnection(in, out, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(response).build();
    }

    private boolean checkRemovedAll(JsonNode jsonNode, String bookingId) {
        if (jsonNode == null || bookingId == null)
            return false;
        Iterator<JsonNode> it = jsonNode.elements();

        while (it.hasNext()) {
            if (bookingId.equals(it.next().asText()))
                return false;
        }
        return true;
    }

    /*
     * close the connection with database and closes the socket
     */
    private void closeConnection(BufferedReader in, PrintWriter out, Socket socket) throws IOException {
        try {
            out.println("disconnect");
            in.readLine();
            socket.close();
        } catch (IOException e) {
            socket.close();
        }
    }

    /*
     * obscure the id of the seats except the one of the booker
     */
    private ObjectNode changeSeats(ObjectNode seats, String fullId, String except) {
        ArrayNode list = (ArrayNode) seats.get(fullId);
        
        if (except == null)
            for (int index = 0; index < list.size(); index++) {
                if (!"0".equals(list.get(index).asText()))
                    list.set(index, "1");
            }
        else 
            for (int index = 0; index < list.size(); index++) {
                if (!except.equals(list.get(index).asText()))
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

    private static String createID()
    {
        return "B" + String.valueOf(idCounter.getAndIncrement());
    }

    // send command to the database and read responses
    private JsonNode sendRequest(String input, BufferedReader in, PrintWriter out) throws IOException {
        out.println(input);
        String response = in.readLine();

        if (response == null)
            return null;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        boolean check = jsonNode.get(0).asBoolean();
        if (!check) {
            return null;
        }
        return jsonNode;
    }
}
