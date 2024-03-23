# A Titanic Space Odyssey!
### _Project 1-2 of DACS 2022/23_

~ by ✨ 7 Wonders of Titan ✨

**_Introduction_**<br />
The central topic of project 1-2 is to bring a manned mission to land safely on Titan (one of Saturn’s moons) and return back to Earth. <br />
For the first phase, we planned an exploratory mission: We launched a space spaceshipOpenController using a ballistic missile from Earth in such a way that it approaches or collides with Titan. <br />
For the second phase, our goal is to launch a fully-fledged multiple-stage rocket from Earth and enter an orbit around Titan. This time we make use of a rocket engine to be able to make corrections to the trajectory if needed. Finally, we are supposed to return back to Earth. 
For the third phase, our goal was to implement two controllers (Open-loop controller and feedback controller) and a wind simulation algorithm. The controllers would calculate the trajectory of the landing procedure of the landing module. 

**_GUI functions_**
- You can zoom into the solar system by using the mouse scroll, so that you can take a good look at every celestial body
- You can also change the relative center of the display. You can have as the center the Sun, the Earth, Saturn or the spaceshipOpenController, you just need to click the respective button.
- You can stop the simulation or run it at three different speeds
- You can choose between the journey of the Spaceship to and from Titan or choose for the visualisation of the landing procedure. 


**_How to run the code with GUI for the flight_**
- To change the input of the starting position and starting velocity of the spaceshipOpenController you need to go into the java class called Data.java. In line 80 you can change the starting position and in line 110 you can change the starting velocity of the spaceshipOpenController.
- Now to launch the simulation you need to go into the java class Launcher.java and run the main method. 
- A window will pop up with a start menu, and you can click on either of the two buttons to see the simulation of the solar system with the spaceshipOpenController flying towards titan or the landing procedure. 

**_How to run the code with GUI for the landing_**
- Decide whether you want to use the open-controller or feedback controller. 

- FEEDBACK CONTROLLER
- Go to the java class called PaneLanding.java (src > main > GUI > Panes > PaneLanding) and change line 58/59. If you want to choose the feedback controller, comment line 58 and uncomment line 59. 
- Then to choose the starting location, velocity and angle go to FeedBackController.java (src > main > Calculations > LandingFeedbackController > FeedbackController). Go to initialiseTheController() in line 45. Here you can change all values needed. Ignore the third values in the arrays, these are z-coordinates which are not taken into account. 
- To change the wind go to line 29 in FeedBackController.java. <br />
  <br />
- OPEN-LOOP CONTROLLER
- Go to the java class called PaneLanding.java (src > main > GUI > Panes > PaneLanding) and change line 58/59. If you want to choose the open-loop controller, uncomment line 58 and comment line 59.
- Then to choose the starting location, velocity and angle go to OpenController.java (src > main > Calculations > LandingOpenController > OpenController). Then in line 17, 18, 19, 20, 21 and 22 you can change the starting location, velocity and angle.
- In line 25 of OpenController.java the wind can be set.<br />
  <br />
- When either of these controllers have been set,you can run the simulation by going to the java class called Launcher.java (src > main > GUI > Launcher) and run the main method.

**_How to run the code without GUI_**<br />
If you are having difficulties with JAVAFX or there is an error for you in the GUI, you can still display either the position coordinates of the desired object over desired amount of iterations of a solver. For that you need to:
- Go to src > Calculations > Simulation.java
- Chane the fields to see desired printed results
- If you wish to change the initial positions, velocity or the step size, you can do so is src > Calculations > Data.java. The lines were you can find the initial position and velocity are mentioned are above in **"How to run the code with GUI"**
- To launch the simulation, run the main method in Simulation.java

**_How to run the tests_**<br />
- On the left side of vscode press on the Icon called "Testing"
- You will see the name of the project and by clicking on it, you will see all the tests and run them separately or all together.
- the calculations are here are done using a time step of 60


**_Important!_**<br />
To run the GUI [Launcher.java]:
- Make sure you have JAVAFX downloaded and all the jar files added to the "Referenced Libraries" in the java project
- Edit the /.vscode/settings.json so that it contains the paths to the libraries
- Edit the /.vscode/launch.json so that next line after "Project name" you paste ""vmArgs":" with the path to your JAVAFX components

To run the tests: 
- Make sure you have the following two jar files from junit : "hamcrest-core-1.3.jar" and "junit-4.13.2.jar"
- Add these two jar files to the "Referenced Libraries" in the java project

**_Extra information:_**<br />
In the folder called "RocketManipulation" you can find three java classes that make use of the hill climbing algorithm. We use it to optimise the route and velocities when flying towards Titan, when orbiting around Titan and when flying back to Earth. You can run the three different methods by going to the respective java classes and running the main method. Information, like when a new solution is found with a better error are printed in the terminal.
