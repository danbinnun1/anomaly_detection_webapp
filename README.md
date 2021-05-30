# Anomaly Detector Web Application
About the application
---------------------
This is an anomaly detection web application. It contains two parts:  
***The anomaly detection server***, which is responsible on training models and saving them on the database, and detect anomaly with these models.  
***The anomaly detection web application***, which is responsible on connecting between the client and the server.    

About the project structure
---------------------------
The project implements the **MVC architectural pattern**.
The responsility of the **Model** is to analyze and train the given models, store them on the database, and detect anomaly with them.  
The **View** is responsibe on showing the models and the anomaly detection to the user in an interactive way - data like the graphs and the anomaly. The View is also responsible on interacting between the user and the server.  
The **Controller** is responsible on connecting between the **Model** and the **View**, in such way that the model and the view don't know each other. The **View** sends data to the **Controller**, which makes the **Model** to work on that data and when the **Model** finishes, sending the result to the view.  
  
About the directories of the server:  
***Service directory*** which contains the class that are related to the **Model** of the **MVC**. The main service classes are *ConcreteModelService*, which is the service for interacting with the database,
*ConcreteModelTrainingService*, which is the service for training the models, and *ConcreteAnomalyDetectionService*, which is the service for anomaly detection.  
Those classes implement interfaces accordingly.  

Required installations
----------------------
The application is written in using the Java Spring Framework, MongoDB database and reactJS.

To run the application you should have the following:  
### Java
https://www.java.com/en/download/

### Mongodb
https://www.mongodb.com/try/download/community

### Maven

Linux:
```
sudo apt-get install maven
```
Windows and Mac:
https://maven.apache.org/download.cgi

### NodeJS and npm

Linux:
```
sudo apt-get install npm && sudo apt-get install nodejs
```
Windows and Mac:
https://nodejs.org/en/download/

### NodeJS packages
run the command ```npm install```


Installation and Running instructions
-------------------------------------
First start the mongodb database unsing the ```mongod``` command, or ```service mongodb start```

next, start the server with ```mvn spring-boot:run```. The server will run locally on port 8080.
Now you can send http requests to the server and update the database.

Finally, run ```npm start``` to start the react web page.

Demonstration Video
----------------
https://youtu.be/2U8jjl2nS5c

Uml Diagram
----------------
![Screenshot](diag.png)  
  




