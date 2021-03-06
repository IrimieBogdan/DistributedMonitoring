# DistributedMonitoring

We propose a fault tolerant distributed monitoring system based on Nmap, and possible other tools, for scanning target hosts and networks. The system will also store the result for later retrieval, audit and statistics purposes.

## Set up

### Requirements

Install RabbitMQ server - V3.5.3 or [newer](http://www.rabbitmq.com/download.html)

Install MongoDB - V2.6.3 or [newer](http://www.mongodb.org/downloads)

Install Nmap - V6.40 or [newer](https://nmap.org/download.html) on every machine that runs a `Scanner` component.

Install JRE [1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) on all the machines that run one of the four components (FrontEnd, Scanner, Converter, Presenter).

### Building the project manually

Install [Maven](https://maven.apache.org/download.cgi)

Install [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Navigate to the root directory and run the command `mvn package`, this will run all the tests and generate jar artefacts for all the components.

### Configuration

Each component has a configuration file `conf.properties`. The file contains details regarding database and queue connection.

Configuration file example for the FrontEnd component:
```
#RabbitMQ connection details
rabbitHost = 192.168.56.102
rabbitSendQueue = commands
rabbitUser = rabbitUser
rabbitPassword = rabbitPassword

#MongoDB connection details
mongoHost = 192.168.56.101
mongoPort = 27017
```

### Deployment

The system can be deployed manualy, or using the [Chef recipes](https://github.com/IrimieBogdan/DmonDeployment).

More info can be found on the [deployment page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Deployment).

##Test/Stress the system

Fallow the guide on the [test page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Performance-Testing).

##Interacting with the monitoring system

Jobs can be submitted by making a HTTP POST request `http://<ip>:8080/request` with a json body. More details about the request body can be found on [request model page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Requests).
