:: This file is for Windows only.

if not exist bin mkdir bin
javac src\ai\*.java src\gui\*.java src\client_server\*.java -d bin
:: Then, copy the folder "res\img" to "bin".
pause
