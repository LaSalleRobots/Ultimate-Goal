The PipelineRunner makes running computer vison pipelines simple and straight forward.

## Usage
How to use the class

### Create the PipelineRunner Object
To begin create a new ```OpenCVPipelineRunner``` object inside your ```runOpMode()``` function.
**NOTE**: Please make sure to pass it the hardwaremap from the ```hardwareMap```
```java
OpenCVPipelineRunner runner = new OpenCVPipelineRunner(
      hardwareMap,
      tracker
    );
```

**FUN FACT** You can pass as many trackers as you want to need in the constructor. 

### Methods
- `start()` configures the camera to start
- `start(b)` configures the camera to start and b will set the status
- `addTracker(t)` adds the tracker to the runner
- `removeTracker(t)` removes the tracker from the runner

### Fields
- `phoneCam` is a `OpenCvInternalCamera` that can be access directly


### Sample
```java
DistanceFromPaperTracker distanceTracker = new DistanceFromPaperTracker(
      0.5
    );
OpenCVPipelineRunner runner = new OpenCVPipelineRunner(
      hardwareMap,
      distanceTracker
    );
runner.start();

telemetry.addData("FPS", runner.phoneCam.getFps());
```