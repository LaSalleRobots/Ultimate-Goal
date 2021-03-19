The Vuvision class simplifies the setup of vuforia setup and getting feild position data easily.

## Usage
How to use the class

### Create the VuVision Object
To begin create a new ```VuVision``` object inside your ```runOpMode()``` function.
**NOTE**: Please make sure to pass it the hardwaremap from the ```hardwareMap```

```java 
VuVision vision = new VuVision(hardwareMap);
```

### Methods
- `activateStart()` starts the camera up
- `tick()` updates the game position data from the current camera view

### Fields
- `visibleTarget` The name of the target that was last visible
- `robotPosition` The position of the robot with `x` `y` and `z`. It also has a `heading` parameter to show the actual heading. 
