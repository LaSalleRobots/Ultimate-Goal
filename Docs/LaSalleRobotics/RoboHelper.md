The RoboHelper package allows for simpler control of the robot, not ideal but a start!

## Usage
How to use the class

### Create RobotHelper Object
TO begin simply create a new ```RobotHelper``` object inside your ```runOpMode()``` function.
**NOTE**: Please make sure to pass it the app context from the ```hardwareMap.appContext```
```java
RoboHelper robot = new RoboHelper(hardwareMap, runtime);
```

### Methods
Basic Methods
- `sleep(s)` waits for the given time in seconds
- `togglePlateGrabber()` toggles the position of the plate grabber
- `powerOff()` turns the motors off
- `applyPower()` useful when using custom power settings and you need to apply them to the motors
- `runFor(s) RobotHelper` runs any previous motor positions for given time in seconds and returns itself for more chaining
- `runDist(dist) RobotHelper` runs any previous motor positions for given distance, uses `fixionCoef` to calculate the time required and returns itself for more chaining

Movement Methods

**NOTE** all movement methods return the robot itself allowing for chaining of commands.
- `moveForwards() RobotHelper` moves the robot forwards
- `moveBackwards() RobotHelper` moves the robot backwards
- `moveLeft() RobotHelper` moves the robot left
- `moveRight() RobotHelper` moves the robot right
- `moveBackwardsLeft() RobotHelper` moves the robot diagonally backwards and left
- `moveBackwardsRight() RobotHelper` moves the robot diagonally backwards and right
- `moveForwardsLeft() RobotHelper` moves the robot diagonally Forwards and left
- `moveForwardsRight() RobotHelper` moves the robot diagonally Forwards and right
- `rotateLeft() RobotHelper` rotates the robot left around the center of the robot
- `rotateRight() RobotHelper` rotates the robot right around the center of the robot

Additonal Methods
- `magnitude(x,y) double` calculates the magnitude of the given x and y from the 0,0 (origin)
- `angle(x,y) double` calculates the angle of the vector coming from 0,0 (origin)  
- `calculateDirections(x,y,turn)` calculates the values for the motor 


### Sample
```java
RoboHelper robot = new RoboHelper(hardwareMap, runtime);
robot.moveBackwards();
robot.runFor(2);
robot.moveForwards().runFor(2);
robot.moveRight().runFor(5);
robot.moveBackwardsLeft().runFor(3);
```
1. Creates helper object
2. Moves robot backwards for 2 seconds
3. Moves robot forwards for 2 seconds
4. Moves robot right for 5 seconds
5. Moves robot left and backwards for 3 seconds

Notice how the robot allows for chaining of certain commands to make shorter & simpler code.
