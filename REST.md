# Progetto Sistemi Distribuiti 2022-2023 - API REST

Documentation for the REST API between the Server Web and the Client Web.

### GET 

## `/movies`

**Description**: Get the list of all movies.

**Parameters**: no parameters are required.

**Requested Body**: no body is required.

**Response**:  in case of success the JSON representation of the list of movies is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of movies is returned.

## `/movies/{id}/dates`

**Description**: Get the list of dates in which the movie with the given id is available.

**Parameters**: a parameter in the path `id` that represents the id of the movie of which the dates are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of dates is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of dates is returned.
* 404 Not Found: At least one of the parameters has not been found.

## `/movies/{id}/dates/{date}/times`

**Description**: Get the list of times in which the movie with the given id is available on the given date.

**Parameters**: two parameters in the path 
* `id` that represents the id of the movie of which the times are to be returned
* `date` that represents the date of which the times are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of times is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of times is returned.
* 404 Not Found: At least one of the parameters has not been found.

## `/movies/{id}/dates/{date}/times/{time}/halls`

**Description**: Get the list of available halls for the movie with the given id on the given date at the given time.

**Parameters**: three parameters in the path 
* `id` that represents the id of the movie of which the halls are to be returned
* `date` that represents the date of which the halls are to be returned 
* `time` that represents the time of which the halls are to be returned.

**Requested Body**: no body is required. 

**Response**: in case of success the JSON representation of the list of halls is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of halls is returned.
* 404 Not Found: At least one of the parameters has not been found.

## `/movies/{id}/dates/{date}/times/{time}/halls/{hall}/seats`

**Description**: Get the list of available seats for the movie with the given id on the given date at the given time in the given hall.

**Parameters**: four parameters in the path
* `id` that represents the id of the movie of which the seats are to be returned
* `date` that represents the date of which the seats are to be returned
* `time` that represents the time of which the seats are to be returned
* `hall` that represents the hall of which the seats are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of seats is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of seats is returned.

**Security** : the list of seats is obfuscated from the user (so that the user cannot see which seats are already booked)

## `/movies/booking/{bookingId}/seats`

**Description**: Get the list of seats for the booking with the given id.

**Parameters**: a parameter in the path `bookingId` that represents the id of the booking of which the seats are to be returned.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the list of seats is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the list of seats is returned.

* 404 Not Found: At least one of the parameters has not been found.

**Security** : the list of seats is obfuscated from the user (so that the user cannot see which seats are already booked)

### POST

## `/movies/{id}/dates/{date}/times/{time}/seats/{seats}`

**Description**: Book the seat with the given Booking id for the selected movie, date, time, hall and seats.

**Parameters**: four parameters in the path 
* `id` that represents the id of the movie of which the seat is to be booked
* `date` that represents the date of which the seat is to be booked 
* `time` that represents the time of which the seat is to be booked 
* `seat` that represents the seats to be booked.

**Requested Body**: no body is required.

**Response**: in case of success the JSON representation of the booking is returned.

**Example response body** :
```{```
  ```"Id": "booking_id"```
```}```

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the booking is returned.
* 404 Not Found: At least one of the parameters has not been found.
* 409 Conflict: The seat is already booked.

### PUT 

## `/movies/booking/{bookingId}/seats/{seats}`

**Description**: Update the booking with the given Booking id for the selected seats.

**Parameters**: two parameters in the path
* `bookingId` that represents the id of the booking to be updated
* `seats` that represents the seats to be updated.

**Requested Body**: no body is required.

**Response**: in case of success a JSON with "Success" is returned.

**Returned Status Codes**:

* 200 OK: The request has been successfully processed and the booking is returned.
* 404 Not Found: At least one of the parameters has not been found.



