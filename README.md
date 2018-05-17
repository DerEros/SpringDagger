# SpringDagger
Compare how a simple service in a pizza online shop would be created and tested using Dagger 2 vs. Spring 5

## The idea
Write a typical (dummy)-micro service consisting of

- a REST frontend (that typically a web ui or a mobile app would access) 
- a pizza catalog service that provides access to available ingredients, predefined pizzas and a way to place an order
- an ingredients DAO that reads the available ingredients
- a pizza DAO that reads predefined pizzas
- a facade class that provide an interface to an external shopping cart service
- and obviously some tests to make sure, we get happy pizza customers

Implementation will only consist of dummy code, but wiring should look like in a real app.

## Programming language

Any language that compiles to JVM byte-code and is accessible to Dagger/Spring. Could be Java, Kotlin, Scala, Clojure, 
Groovy or even Frege.
