# Task for VALA Group: find prime factors for a number

## Preparation
Please install:

* **Java** (version 11 or higher) https://www.oracle.com/java/technologies/javase-downloads.html

* **Maven** (3.6.3 on my local machine) https://maven.apache.org/download.cgi

## Building a JAR file

To run the application you need to build a `jar` file:
* Navigate to the root project directory
* Run `mvn clean install`
* Run `mvn clean compile assembly:single` to build a jar file

The result `jar` file `prime_number-1.0-SNAPSHOT-jar-with-dependencies.jar`
can be found under `target` path in the project root directory.

## Execution
When the `jar` file is created you can run it using command
`java -jar prime_number-1.0-SNAPSHOT-jar-with-dependencies.jar`.

Once you see a message "Give me the number:" enter the number and press `Enter`

A database file with results will be created in the same folder where the `jar`
 file is,
 also the application will create a new file for each output in that folder.

## Testing
To run tests:
* Run `mvn clean test`
