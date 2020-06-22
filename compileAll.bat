:: This file is for Windows only.

if not exist bin mkdir bin
javac src\ai\*.java src\gui\*.java src\client_server\*.java src\buttons\*.java -d bin

:: Then, copy the folder "res\img" to "bin".
if not exist bin\img mkdir bin\img
if not exist bin\img\O mkdir bin\img\O
if not exist bin\img\X mkdir bin\img\X
copy res\img\X bin\img\X 
copy res\img\O bin\img\O

pause
