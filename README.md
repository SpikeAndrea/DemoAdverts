# DemoAdverts
Simple Demo back-end and front-end car adverts

La parte back-end è in java Spring, mentre quella front-end in vue.js

## Database

Il database utilizzato è MySql. Contiene una singola tabella che si chiama cars.
Le specifiche per la connessione al database sono indicate nel file application.propierties:

>datasource.url=jdbc:mysql://localhost:3306/adverts?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC
spring.datasource.username=user_db
spring.datasource.password=passw_db
spring.jpa.properties.hibernate.id.new_generator_mappings=false
server.port:9000

Essendo che l'applicazione girerà in localhost, con server.port cambiamo la porta di default, che è l'8080, sulla quale
invece girerà la parte front-end.

## BACK-END REQUEST

Le richieste possibili sono tutte definite nel controller CarController.java e sono:

GET - http://localhost:9000/cars
>formato json elenco di tutti gli annunci

GET - http://localhost:9000/cars/id
>formato json di un annuncio specifico corrispondente all'id

GET - http://localhost:9000/carsOrderBy/columnOrder?dir=ASC
>formato jsnon di tutti gli annunci ordinati in base a {columnOrder}, specificando la direzione (?dir=ASC)

GET - http://localhost:9000/carsByTitle/which_title
>formato jsnon di tutti gli annunci che contengono nel campo title il valore di which_title


POST - http://localhost:9000/cars
>finserisce un nuovo annuncio, i dati devono essere passati in formato json nella richiesta

POST - http://localhost:9000/cars/rem/{id}
>rimuove l'annuncio corrispondente all'id richiesto

POST - http://localhost:9000/cars/id
>modifica un annuncio, i dati da modificare devono essere passati in formato json nella richiesta
