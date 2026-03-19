@echo off
REM Set JAVA_HOME to the local JDK 11 installation
set JAVA_HOME=C:\Users\Senthamil Anandhi D\.jdks\ms-11.0.27

REM Run the application using the downloaded Maven binary
echo Starting Sentiment Analysis Java Application...
.\apache-maven-3.9.6\bin\mvn.cmd clean compile exec:java -Dexec.mainClass="org.sentiment.Application"
