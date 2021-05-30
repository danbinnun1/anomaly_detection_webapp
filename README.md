# Anomaly Detector Web Application
About the application
---------------------
This is an anomaly detection web application. It contains two parts:  
***The anomaly detection server***, which is a **Spring boot** RESTful Api server, that can train models, store them on a **MongoDB** database, and detect anomaly with these models.  
***The anomaly detection web application***, which is an interactive **ReactJS** web application that enables the client interact with the server and use its functionallity.  

About the project structure
---------------------------
The project implements the **MVC architectural pattern**.  
The responsility of the **Model** is to analyze and train the given models, store them on the database, and detect anomaly with them.  
The **View** is responsibe on showing the models and the anomaly detection to the user in an interactive way - data like the graphs and the anomaly. The View is also responsible on interacting between the user and the server.  
The **Controller** is responsible on connecting between the **Model** and the **View**, in such way that the model and the view don't know each other. The **View** sends data to the **Controller**, which makes the **Model** to work on that data and when the **Model** finishes, sending the result to the view.  

The server directories structure is as usual *Spring boot* project.  
About the directories of the server:  
***service directory*** which contains the class that are related to the **Model** of the **MVC**. The main service classes are *ConcreteModelService*, which is the service for interacting with the database, *ConcreteModelTrainingService*, which is the service for training the models, and *ConcreteAnomalyDetectionService*, which is the service for anomaly detection. Those classes implement interfaces accordingly. This directory also contains the implementation of the training and anomaly detection algorithms.  
***controller directory*** which contains the classes that are related to the **Controller** of the **MVC**. The controller classes are *ModelController* which handles requests that are related to the model training, and *AnomalyDetectionController* which handles requests that are related to anomaly detection. The controllers holds references to the services they need, and gets them using the *Spring*'s dependency injection.  
***repository directory*** which contains the interfaces which are used to interact with the database. The *Spring* framework supplies automatic concrete implementation to those interfaces, and injects them according to the dependency injection.  
***model directory*** which contains the classes which are saved in the database. Those classes are *Model*, which represents a trained model, and *Data*, which represents the data of the model.  
***dto directory*** which contains the *data transfer objects*, which are abstractions of the *model* objects (which are the objects that are saved in the database). The purpose of the *dto* is to seperate the controllers from the database, and give them only the information they need.  
***exceptions directory*** which contains the classes that represent the exceptions of the server, like *InvalidDataException*, *ModelNotFoundException*, and more.

Required installations
----------------------
The application is built using the **Java Spring Boot Framework** for the server, **MongoDB** for the database and **ReactJS** for the frontend.  

To run the application you should have the following:  
### Java
https://www.java.com/en/download/

### Maven (Java building tool)

Linux:
```
sudo apt-get install maven
```
Windows and Mac:
https://maven.apache.org/download.cgi

### MongoDB
https://www.mongodb.com/try/download/community

### npm

Linux:
```
sudo apt-get install npm
```
Windows and Mac:
https://nodejs.org/en/download/

### NodeJS packages
Inside the project directory, run the commands ```cd frontend``` ```npm install``` 

Installation and Running instructions
-------------------------------------
First, clone the repository.  
Next, build the **ReactJS** with the server using the commands ```cd frontend``` ```npm run build```  
Next, start the **MongoDB** database server using the ```mongod``` command, or ```service mongodb start```
Next, start the **Spring boot** server using the ```mvn spring-boot:run``` command inside the project main directory.  
The server will start locally on port 8080.  
Now you can send HTTP requests to the server and update the database.  

Demonstration Video
----------------
https://youtu.be/2U8jjl2nS5c

Uml Diagram
----------------
UML diagram of the server:  
![Screenshot](diag.png)  
  




