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
- `runFor(s)` runs any previous motor positions for given time in seconds
- `runDist(dist)` runs any previous motor positions for given distance, uses `fixionCoef` to calculate the time required

Movement Methods

**NOTE** all movement methods have the collection cell as the front
- `moveForwards()` moves the robot forwards
- `moveBackwards()` moves the robot backwards
- `moveLeft()` moves the robot left
- `moveRight()` moves the robot right
- `moveBackwardsLeft()` moves the robot diagonally backwards and left
- `moveBackwardsRight()` moves the robot diagonally backwards and right
- `moveForwardsLeft()` moves the robot diagonally Forwards and left
- `moveForwardsRight()` moves the robot diagonally Forwards and right
- `rotateLeft()` rotates the robot left around the center of the robot
- `rotateRight()` rotates the robot right around the center of the robot
- `slowScanRight()` rotates the robot at 25% power to the right around the center of the robot
- `slowScanLeft()` rotates the robot at 25% power to the left around the center of the robot

### Sample
```java
RoboHelper robot = new RoboHelper(hardwareMap, runtime);
robot.moveBackwards()
robot.runFor(2)
robot.togglePlateGrabber()
robot.moveForwards()
robot.runFor(2)
robot.togglePlateGrabber()
robot.moveRight()
robot.runFor(5)
```
1. Creates helper object
2. Moves robot backwards for 2 seconds
3. Moves the plate grabber down
4. Moves robot forwards for 2 seconds
5. Moves the plate grabber up
6. drives right for 5 seconds