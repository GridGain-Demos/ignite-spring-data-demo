# Getting Started With Apache Ignite, Spring Boot and Spring Data Demo

This demo shows how to build a simple RESTful Web Service that uses Apache Ignite as a high-performance in-memory database.
The service is powered by Spring Boot that embeds an Apache Tomcat instance and interacts with an Apache Ignite cluster 
via Spring Data repositories' abstraction.

## Developing the Project From Scratch 

Follow this tutorial if you want to build the application from the from the ground-up learning all the nuances:
https://www.gridgain.com/docs/tutorials/spring/spring_ignite_tutorial

Alternatively, follow instructions from the next section to run the application and play with it.

## Run the Application

Checkout the project and start the application executing the following Maven command from the project root folder:

```
mvn spring-boot:run
```

Use the `curl` command-line tool to send several HTTP requests that will be intercepted and processed by your Spring 
controller. First, get all the cities with a population equal to or bigger than 8 million:

```
curl http://localhost:9000/api/cities?population=8000000
```

The response will be as follows:

```
[{"id":3793,"countryCode":"USA","name":"New York","district":"New York","population":8008278},
{"id":3580,"countryCode":"RUS","name":"Moscow","district":"Moscow (City)","population":8389200},
{"id":2515,"countryCode":"MEX","name":"Ciudad de México","district":"Distrito Federal","population":8591309},
{"id":3357,"countryCode":"TUR","name":"Istanbul","district":"Istanbul","population":8787958},
{"id":2822,"countryCode":"PAK","name":"Karachi","district":"Sindh","population":9269265},
{"id":939,"countryCode":"IDN","name":"Jakarta","district":"Jakarta Raya","population":9604900},
{"id":1890,"countryCode":"CHN","name":"Shanghai","district":"Shanghai","population":9696300},
{"id":206,"countryCode":"BRA","name":"São Paulo","district":"São Paulo","population":9968485},
{"id":2331,"countryCode":"KOR","name":"Seoul","district":"Seoul","population":9981619},
{"id":1024,"countryCode":"IND","name":"Mumbai (Bombay)","district":"Maharashtra","population":10500000}]
```

Second, get the top 10 most populated cities:

```
curl http://localhost:9000/api/cities/mostPopulated?limit=10
```

In response, the application will join `City` and `Country` tables to produce the following result:

```
[["Mumbai (Bombay)",10500000,"India","Federal Republic"],
["Seoul",9981619,"South Korea","Republic"],
["São Paulo",9968485,"Brazil","Federal Republic"],
["Shanghai",9696300,"China","PeoplesRepublic"],
["Jakarta",9604900,"Indonesia","Republic"],
["Karachi",9269265,"Pakistan","Republic"],
["Istanbul",8787958,"Turkey","Republic"],
["Ciudad de México",8591309,"Mexico","Federal Republic"],
["Moscow",8389200,"Russian Federation","Federal Republic"],
["New York",8008278,"United States","Federal Republic"]]
```

Finally, update a population of the city with id `3507`:

```
curl -X PUT -H 'Content-Type: application/json' -d '{"population":5000}' http://localhost:9000/api/cities/3507
```

The data will be updated and the controller will send back the modified version of that city’s record:

```
{"id":3507,"countryCode":"UZB","name":"Buhoro","district":"Buhoro","population":5000}
```
