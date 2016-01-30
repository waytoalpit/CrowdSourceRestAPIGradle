What is REST?
RESTful Web Services are REST architecture based web services. In REST Architecture everything is a resource. 
RESTful web services are light weight, highly scalable and maintainable and are very commonly used to create APIs for web based applications. 
REST stands for REpresentational State Transfer. REST is web standards based architecture and uses HTTP Protocol for data communication.

What is the scope of this project?
Scope of this project is to expose APIs for different set of Spectrum sensing data which can be leveraged by any cross platform,
cross technology system or applications.

Technology used:
Java + Jersey + Mongo + Gradle + Tomcat7 + Junit4

1. List of all devices with their measurements:
http://130.245.144.191:8080/CrowdSourceRestAPIGradle/rest/DeviceService/deviceinfo
2. List of all online registered device info
http://130.245.144.191:8080/CrowdSourceRestAPIGradle/rest/DeviceService/onlinedeviceinfo
3. List of all offline registered device info
http://130.245.144.191:8080/CrowdSourceRestAPIGradle/rest/DeviceService/offlinedeviceinfo
4. List of all registered device info, given center(1,1) and radius 100
http://130.245.144.191:8080/CrowdSourceRestAPIGradle/rest/DeviceService/1/1/100
5. Check for the last seen device info
http://130.245.144.191:8080/CrowdSourceRestAPIGradle/rest/DeviceService/lastseen

How to start a Tomcat instance and deploys the WAR to it with gradle?
 start : gradle TomcatRunWar
 stop : gradle TomcatStop