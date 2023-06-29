package it.unimib.finalproject.server;

public class Reservation {
    // rappresenta una prenotazione

    private int id;
    private String movieTitle;
    private String movieDate;
    private String movieTime;
    private int hallNumber;
    private int seatId;

    // get e set per gli attributi

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    private String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    private String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    private int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    private int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /**
     * Implementazione di POST "/movies".
     */
    // @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response addMovie(String body) {
    //     // Notare come per ogni caso restituisco al client codici HTTP diversi.
    //     try {
    //         // ObjectMapper è di Jackson, serve a leggere JSON e convertirlo in
    //         // classe Java.
    //         var mapper = new ObjectMapper();
    //         var movie = mapper.readValue(body, Movie.class);
    //         movie.setId(movies.size());
    //         movie.setDates(new ArrayList<Date>());
    //         movies.add(movie);

    //         // Poiché è una POST, nella risposta ci deve essere l'header
    //         // "Location" con l'URL alla risorsa appena creata. Ci pensa il
    //         // metodo "created" ad aggiungerlo, ma noi dobbiamo specificare
    //         // l'URL.
    //         var uri = new URI("/movies/" + movie.getId());

    //         return Response.created(uri).build();
    //     } catch (JsonParseException | JsonMappingException e) {
    //         System.out.println(e);

    //         // È un errore del client, ecco perché restituisce 400 Bad request.
    //         return Response.status(Response.Status.BAD_REQUEST).build();
    //     } catch (IOException | URISyntaxException e) {
    //         System.out.println(e);

    //         // È un errore del server che non dovrebbe accadere, restituisce 500
    //         // Internal server error.
    //         return Response.serverError().build();
    //     }
    // }


        
    // /**
    //  * Implementazione di GET "/movies/{id}/dates/{date}".
    //  */

    // @Path("/{id}/dates/{date}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getDate(@PathParam("id") int id, @PathParam("date") String parDate) {
    //     var movie = movies.get(id);
    //     if (movie == null) // Film non trovato.
    //         return Response.status(Response.Status.NOT_FOUND).build();
        
    //     for (var date : movie.getDates())
    //         if (date.getDate().equals(parDate)) // Data trovata.
    //             return Response.ok(date).build();
        
    //     // Data non trovata.
    //     return Response.status(Response.Status.NOT_FOUND).build();
    // }
    
    
    // /**
    //  * Implementazione di POST "/movies/{id}/dates/".
    //  */

    // @Path("/{id}/dates")
    // @POST
    // @Consumes(MediaType.TEXT_PLAIN)
    // @Produces(MediaType.APPLICATION_JSON)

    // public Response addDate(@PathParam("id") int id, String parDate) {
    //     var movie = movies.get(id);
    //     if (movie == null) // Film non trovato.
    //         return Response.status(Response.Status.NOT_FOUND).build();

    //     System.out.println(parDate);

    //     var date = new Date();
    //     date.setDate(parDate);
    //     movie.getDates().add(date);

    //     try {
    //         var uri = new URI("/movies/" + movie.getId() + "/dates/" + parDate);
    //         return Response.created(uri).build();
    //     } catch (URISyntaxException e) {
    //         System.out.println(e);
    //         return Response.serverError().build();
    //     }

    // }

    // /**
    //  * Implementazione di GET "/movies/{id}/dates/{date}/times".
    //  */

    // @Path("/{id}/dates/{date}/times")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)

    // public Response getTimes(@PathParam("id") int id, @PathParam("date") String parDate) {
    //     var movie = movies.get(id);
    //     if (movie == null) // Film non trovato.
    //         return Response.status(Response.Status.NOT_FOUND).build();

    //     for (var date : movie.getDates())
    //         if (date.getDate().equals(parDate)) // Data trovata.
    //             return Response.ok(date.getTimes()).build();

    //     // Data non trovata.
    //     return Response.status(Response.Status.NOT_FOUND).build();
    // }

    // /**
    //  * Implementazione di GET "/movies/{id}/dates/{date}/times/{time}".
    //  */

    // @Path("/{id}/dates/{date}/times/{time}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)

    // public Response getTime(@PathParam("id") int id, @PathParam("date") String parDate, @PathParam("time") String parTime) {
    //     var movie = movies.get(id);
    //     if (movie == null) // Film non trovato.
    //         return Response.status(Response.Status.NOT_FOUND).build();

    //     for (var date : movie.getDates())
    //         if (date.getDate().equals(parDate)) // Data trovata.
    //             for (var time : date.getTimes())
    //                 if (time.getTime().equals(parTime)) // Ora trovata.
    //                     return Response.ok(time).build();

    //     // Ora non trovata.
    //     return Response.status(Response.Status.NOT_FOUND).build();
    // }

    
}
