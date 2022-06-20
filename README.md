Task: Create an API that allows you to receive and transmit information about the transport that works in the quarries, basic information about the quarries, about the drivers of transport, about the results of their medical examination before the shift. Term Paper

Draw a diagram of the classes that will be used, indicate the links between them.
Write classes, use the lombok library to reduce the amount of code.
Implement REST services for all entities using Spring Boot. Implement GET / POST / PUT / DELETE operations. GET / 2 - returns an entity equal to 2. / GET - returns all entities present in the system.
Divide the code into controllers, services and data access level.
Linking controllers, services, and data access levels should be done using dependency inversion. 6.Implement data storage and subtraction from the csv file. each entity is stored in a separate file.
If the file does not exist for the entity, it must be created. example file name: fish-2022-05-27.csv.
Each file should contain headers only in the first line.
When you run the application, all entities are read from the file and saved to the hash map. When reading the data, you should search for all files for the entity that were created in the current month.
The coursework code should be available as a PR on github.
The project must contain README.md with a description of the task and step-by-step instructions for starting the program.
The project must use maven to assemble the project.
The code should be checked using Spotbugs and checkstyle.
Create a set of REST queries that test the performance of developed services.

#How to run
download the project from GitHub by cloning it, or just downloading as .zip file
open CLI and proceed to the project's path
run "mvn spring-boot:run" command and wait till the application is fully loaded and started
to shutdown the application, open the CLI that runs the application, press "Ctrl + C", enter "Y" and press "Enter"
the application should be shutdown saving all the changes to appropriate files
