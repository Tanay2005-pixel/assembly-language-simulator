@echo off
echo Compiling GUI files...
javac -d . SimulatorGUI.java CodeWindow.java MemoryWindow.java RegisterWindow.java ExecuteWindow.java SimulatorState.java MongoDBHelper.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Starting 8085 Simulator GUI...
java SimulatorGUI
