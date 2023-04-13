# Food-Loop
Food Loop is an application aimed at providing you with the best places to eat nearby the University of Michigan - Dearborn campus (Will be updated later to provide this service to any user in the world based on their location).

This README file will provide instructions on how to build, test, and run this Spring Boot app written in Java 17 that uses Maven as the build tool.

## Prerequisites
Before building and running the app, ensure that you have the following installed on your machine:

- Java (Preferrably Java 17)
- Maven
- Git
- A Java IDE (IntelliJ, Eclipse, ...)

## Clone the repository
To get started, clone the repository using the following command:
 > git clone https://github.com/ashrf34q/food-loop.git
 
 ## Build the app
 Once you have cloned the repository, navigate to the project root directory and run the following command to build the app:
 > mvn clean install

 This command will build the app and create an executable JAR file in the target directory.
 
 ## Test the app
 To run the tests, use the following command:
 > mvn test

 This command will run all the tests in the project and provide a report of the test results.
 
 ## Run the app
 
 To run the app, use the following command:
 > java -jar target/food-search-app-0.0.1-SNAPSHOT.jar
 
 Note: The jar file might have a different name, just navigate to the target folder to make sure what the generated jar file is called.
 
 You can also run the app in a simpler, no hassle way directly from your IDE by clicking the run button.
 
 The app should now be up and running. You can access it by navigating to http://localhost:8080 in your web browser.
