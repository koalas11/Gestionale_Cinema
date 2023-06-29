# Progetto Sistemi Distribuiti 2022-2023 - API REST

Documentazione delle API REST di esempio. Si assume che i dati vengano scambiati in formato JSON.

## `/contacts`

Ogni risorsa ha la sua sezione. In questo caso la risorsa da documentare è quella dei contatti.

### GET

**Descrizione**: una breve descrizione di cosa fa il metodo applicato alla risorsa. In questo caso restituisce l'elenco dei contatti salvati.

**Parametri**: un elenco dei parametri se previsti, sia nel percorso (esempio `/contacts/{id}`) che nella richiesta (esempio `/contacts?id={id}`) o anche negli header. In questo caso non sono previsti.

**Body richiesta**: cosa ci deve essere nel body della richiesta. In questo caso nulla perché è una GET.

**Risposta**: cosa viene restituito in caso di successo. In questo caso viene restituito la rappresentazione in JSON del contatto, un oggetto JSON con i campi `id` (un intero), `name` e `number` (due stringhe).

**Codici di stato restituiti**: elenco dei codici di stato, se necessario dettagliare e non elencare quelli già previsti da Jackarta in automatico. In questo caso c'è solo lo stato `200 OK` da segnalare:

* 200 OK

### POST

**Descrizione**: aggiunge un contatto alla rubrica telefonica.

**Parametri**: ci deve essere l'header `Content-Type: application/json`.

**Body richiesta**: rappresentazione in formato JSON del contatto con i campi `name` e `number` che sono due stringhe.

**Risposta**: in caso di successo il body è vuoto e la risorsa creata è indicata nell'header `Location`.

**Codici di stato restituiti**:

* 201 Created
* 400 Bad Request: c'è un errore del client (JSON, campo mancante o altro).

## `/contacts/{id}`

### GET

**Descrizione**: restituisce il contatto con l'id fornito.

**Parametri**: un parametro nel percorso `id` che rappresenta l'identificativo del contatto da restituire.

**Body richiesta**: vuoto.

**Risposta**: In caso di successo la rappresentazione in JSON del contatto, un oggetto JSON con i campi `id` (un intero), `name` e `number` (due stringhe).

**Codici di stato restituiti**:

* 200 OK
* 400 Bad Request: c'è un errore del client (ID non valido).
* 404 Not Found: contatto non trovato.
