# RoboPompier
===============================

## 2A POO - TP 2024-2025

### Project Structure

- **src**: Contains the source code for the project.
  - `LecteurDonnees.java`: Reads all elements from a data description file (cases, fires, and robots) and displays them. You need to modify this class (or write a new one) to create objects corresponding to your own classes.
  - `Robot.java`: An abstract class used as a base for different types of robots. It includes methods for creating paths, moving, and interacting with the simulator.
    - `Drone.java`: A subclass of `Robot` that represents a drone.
    - `Pattes.java`: A subclass of `Robot` that represents a robot with legs.
    - `RobotCaterpillar.java`: A subclass of `Robot` that represents a caterpillar robot.
    - `RobotWheels.java`: A subclass of `Robot` that represents a robot with wheels.
  - `Simulateur.java`: Manages the simulation, including events and the state of the simulation.
  - `SimpleChefPompier.java`: Manages the strategy for controlling the robots, including sending them to fires and refilling water.
  - `AskInstructions.java`: An event class that allows a robot to ask for new instructions from the `SimpleChefPompier`.

- **cartes**: Contains some example data files.

- **bin/gui.jar**: Java archive containing the classes for the graphical interface. See an example of its usage in `TestInvader.java`.

- **doc**: Documentation (API) for the classes in the graphical interface contained in `gui.jar`. Entry point: `index.html`.

- **Makefile**: Provides explanations on command-line compilation, particularly the concept of classpath and the use of `gui.jar`.

### Main Classes

- **Robot**: An abstract class used as a base for different types of robots. It includes methods for creating paths, moving, and interacting with the simulator.
  - `Drone`: A subclass of `Robot` that represents a drone.
  - `Pattes`: A subclass of `Robot` that represents a robot with legs.
  - `RobotCaterpillar`: A subclass of `Robot` that represents a caterpillar robot.
  - `RobotWheels`: A subclass of `Robot` that represents a robot with wheels.

- **Simulateur**: Manages the simulation, including events and the state of the simulation.

- **SimpleChefPompier**: Manages the strategy for controlling the robots, including sending them to fires and refilling water.


### Compilation and Execution

To compile and run the tests, you can use the provided `Makefile`. Here are some useful commands:

- **Compilation**:
  ```sh
  make install

- **Execution**:
  ```sh
  make exeMap 
  make exeMushroom
  make exeSpiral
  make exeDesert

- **Tools**:
To generate the documentation, use the following command:

- **Additional Information**:
TestChefPompier: Located in bin/, this class is used for testing the ChefPompier functionality.
Images: Located in images/, this directory contains any images used in the project.
Libraries: Located in lib/, this directory contains additional libraries required by the project.
For more detailed information, refer to the documentation in the doc/ directory.
