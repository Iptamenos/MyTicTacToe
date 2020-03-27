:: copy the "img" folder from "res\img" inside the "bin" directory
if not exist bin/img mkdir bin/img
cp res/img bin/img
java -cp bin gui.GUI
pause
