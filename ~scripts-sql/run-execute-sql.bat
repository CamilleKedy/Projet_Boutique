@ECHO OFF
CD /D "%~dp0"

SET JAVA_HOME=C:\dev23\jdk-18
SET ANT_HOME=C:\dev23\utils\ant

CALL "%ANT_HOME%\bin\ant.bat" -f execute-sql.xml 

ECHO. & PAUSE
