cd "%~dp0database"
start mvnw.cmd clean compile exec:java

cd "%~dp0server-web"
start mvnw.cmd clean compile exec:java

timeout 7

cd "%~dp0client-web"
start index.html