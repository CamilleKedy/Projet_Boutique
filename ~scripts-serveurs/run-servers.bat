@ECHO OFF
CHCP 1252 >NUL
CD /D "%~dp0"

SET JAVA_HOME=C:\dev23\jdk-18
SET ANT_HOME=C:\dev23\utils\ant

SET ANT_OPTS=-Djava.security.manager=allow

CALL "%ANT_HOME%\bin\ant.bat" -f build-servers-menu.xml  menu

ECHO. & PAUSE
