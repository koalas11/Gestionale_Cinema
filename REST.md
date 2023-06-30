# Progetto Sistemi Distribuiti 2022-2023 - API REST

Documentazione delle API REST di esempio. Si assume che i dati vengano scambiati in formato JSON.

### GET 

## `/movies`

**Description**: Get the list of all movies.

**Parameters**: no parameters are required.

**Requested Body**: no body is required.

**Response**:  in case of success the JSON representation of the list of movies is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of movies is returned.

## `/movies/{id}`

**Description**: Get the movie with the given id.

**Parameters**: a parameter in the path `id` that represents the id of the movie to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the movie is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the movie is returned.
* 404 Not Found: No movie with the given id has been found.

## `/movies/{id}/dates`

**Description**: Get the list of dates in which the movie with the given id is available.

**Parameters**: a parameter in the path `id` that represents the id of the movie of which the dates are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of dates is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of dates is returned.
* 404 Not Found: No movie with the given id has been found or no date has been found.

## `/movies/{id}/dates/{date}/times`

**Description**: Get the list of times in which the movie with the given id is available on the given date.

**Parameters**: two parameters in the path 
* `id` that represents the id of the movie of which the times are to be returned
* `date` that represents the date of which the times are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of times is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of times is returned.
* 404 Not Found: No movie with the given id has been found or no date has been found or no time has been found.

## `/movies/{id}/dates/{date}/times/{time}/seats`

**Description**: Get the list of available seats for the movie with the given id on the given date at the given time.

**Parameters**: three parameters in the path 
* `id` that represents the id of the movie of which the seats are to be returned
* `date` that represents the date of which the seats are to be returned 
* `time` that represents the time of which the seats are to be returned.

**Requested Body**: no body is required. 

**Response**: in case of success the JSON representation of the list of seats is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of seats is returned.
* 404 Not Found: No movie with the given id has been found or no date has been found or no time has been found or no seat has been found.

### POST

## `/movies/{id}/dates/{date}/times/{time}/seats/{seat}`

**Description**: Book the seat with the given Booking id for the selected movie, date, time and seat.

**Parameters**: four parameters in the path 
* `id` that represents the id of the movie of which the seat is to be booked
* `date` that represents the date of which the seat is to be booked 
* `time` that represents the time of which the seat is to be booked 
* `seat` that represents the seat to be booked.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the booking is returned.

**Example response body** :
{
  "Id": "booking_id"
}

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the booking is returned.










