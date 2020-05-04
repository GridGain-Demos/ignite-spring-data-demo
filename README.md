# Getting Started With Apache Ignite, Spring Boot and Spring Data Demo

Step-by-step plan:
* Initialize the project with Spring Initializer: https://start.spring.io
* Add Ignite dependencies (core, spring data, spring boot extension)
* Download Ignite 2.8, start 2 nodes and load the Cities database with SQLLine.
* Create Spring Data Repositories for Citites and Countries.
* Define some Spring methods to query data.
* Query with Ignite syntax (at least for joins).
* ID generator - use Ignite atomic sequence (https://apacheignite.readme.io/docs/id-generator). If it's not persisted, then upon a cluster restart, an application needs to init each sequence it with "SELECT max(ID) FROM tableX".
* Update and delete data with Spring Data repositories.

Note, the readers have to build the project from scratch while this github repo is a complete solution for reference and has a fewer instructions (like start the cluster and run this-that).

## Creating Project With Spring

Init the project with parameters shown on the picture below (https://start.spring.io):


