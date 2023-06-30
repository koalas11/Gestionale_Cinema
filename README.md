# Progetto Sistemi Distribuiti 2022-2023

Lo scheletro propone degli esempi dei tre componenti da cui partire.

## Componenti del gruppo

* Marco Sanvito (886493) m.sanvito17@campus.unimib.it
* Emre Tugrul (886027) e.tugrul@campus.unimib.it
* Andrea Falbo (887525) a.falbo7@campus.unimib.it

## Compilazione ed esecuzione

Per facilitare l'esecuzione locale è presente il setupServer.bat che esegue i comandi necessari per avviare il server, il database e il file index.html. 
E' inoltre presente un timeout tra l'avvio del server e del database rispetto al file html per permettere l'avvio corretto delle componenti.
E' possibile modificare il timeout cambiando il valore della variabile timeout presente nel file setupServer.bat.

Il server Web e il database sono dei progetti Java che utilizano Maven per gestire le dipendenze, la compilazione e l'esecuzione. È necessario eseguire in seguente i seguenti obiettivi per compilare ed eseguire: `clean`, che rimuove la cartella `target`, `compile` per compilare e `exec:java` per avviare il
componente.

I tre obiettivi possono essere eseguiti insieme in una sola riga di comando da terminale tramite `./mvnw clean compile exec:java` per Linux/Mac e `mvnw.cmd clean compile exec:java` per Windows. L'unico requisito è un'istallazione di Java (almeno la versione 11), verificando che la variabile `JAVA_PATH` sia correttamente configurata.

Il client Web è invece un solo file HTML chiamato `index.html`, può essere aperto su un qualsiasi browser. È importante disabilitare CORS, come mostrato nel laboratorio 8 su JavaScript (AJAX).

## Porte e indirizzi

Il server Web si pone in ascolto all'indirizzo `localhost` alla porta `8080`. Il database si pone in ascolto allo stesso indirizzo del server Web ma alla porta `3030`.

## Classi Java

* `server-web`: 
    * `CORSFilter.java`: classe che implementa un filtro per disabilitare CORS.
    * `SampleClient.java`: classe che si occupa di inizializzare il database con dei dati precaricati. 
    * `ServerAPI.java`: classe che implementa le API del server Web.

* `database`:
    * `Main.java`: classe che gestisce i comandi in ingresso e restituisce i risultati in formato JSON.
    * `Database.java`: classe che implementa le API del database.
    * `WrongInputException.java`: classe che implementa un'eccezione personalizzata per la gestione degli errori.

* `client-web`:
    * `index.html`: file HTML base per la pagina web.
    * `style.css`: file CSS per la formattazione della pagina

