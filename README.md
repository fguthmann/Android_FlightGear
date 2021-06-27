# Flight Gear Android App

Flight Controller Android App, ex3, Advanced Programming 2, biu

# Introduction
An android application for controlling flight as a remote control using the Flight Gear.
The project is built in MVVM architecture in which: the model is the presentation of the app in screen, the VM is the main activity and the model is the client that connect to the FG.

# Folder structure links
(Android_FlightGear shortcut is AFG)
 * AFG/      Root folder
 * [AFG/app/src/main/java/ex3_2/com/](app/src/main/java/ex3_2/com/)  Source files of the application.
* [AFG/app/src/main/java/ex3_2/com/View/](app/src/main/java/ex3_2/com/View/)  Source files of the view which contain the joystick.
* [AFG/app/src/main/java/ex3_2/com/Model/](app/src/main/java/ex3_2/com/Model/)  Source files of all logical management- the connection to the FG.
* [AFG/app/src/main/java/ex3_2/com/MainActivity.java](app/src/main/java/ex3_2/com/MainActivity.java)  Contain the main activity- which s the bridge between receiving the commands from the user and sending them to the FG.
* [AFG/.idea/](.idea/)  Contains setting for the environment and the app.
* [AFG/gradle/wrapper/](gradle/wrapper/)  Contains setting for the environment and the app.


# Pre requirements
* Flight Gear 2020.3.6 installed
* In FG settings-> AdditionalSettings, write "--telnet=socket,in,10,127.0.0.1,6400,tcp".
* The telnet client can be running on the same machine as FlightGear itself, or on other machines that are in some way connected to the same network (LAN, WAN/internet).
* The IP given to the app shouldn't be 127.0.0.1 even if the app is running from the same machine as the FG.
* The port is 6400, if the port isnt available use different port (change it in the FG settings as well).
* (Developing is recommended in android studio using the emulator)
