# Progetto Sistemi Distribuiti 2022-2023 - TCP

Documentazione del protoccolo socket su TCP. Si suggerisce di seguire il protocollo di Redis (https://redis.io/docs/reference/protocol-spec/), perché è molto semplice sia da comprendere sia da implementare. Non è necessario prendere tutti i punti, basta quello necessario per l'invio della richiesta e della risposta.

La documentazione può variare molto in base al tipo di protocollo che si vuole costruire. Se come Redis, è un protocollo di richieste e risposte, per cui è necessario indicare come inviare la richiesta (comando e dati) e la risposta. Si può anche utilizzare JSON al posto delle semplici stringhe, in tal caso andranno specificati bene la struttura degli oggetti scambiati tra client e server.