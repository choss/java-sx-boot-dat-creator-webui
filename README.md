# java-sx-boot-dat-creator-webui
Converts any payload for fusee to an sx os boot.dat

This is a reimplementation of CTCaer's python script to convert payloads to boot them with the SX-Gear or SX-Pro. 
The output is a "boot.dat" file which can be booted directly from those devices. 

This is a web based spring-boot app so in order to get it working a simple mvn clean package and mvn boot:run should do the trick.

For fun it should also be deployed to heroku. 
