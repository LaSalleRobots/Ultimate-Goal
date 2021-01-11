The RecordPlayer package allows you to play bundled sound effects with ease. 

## Usage
How to use the class

### Create RecordPlayer Object
To begin simply create a new ```RecordPlayer``` object inside your ```runOpMode()``` function.
**NOTE**: Please make sure to pass it the app context from the ```hardwareMap.appContext```
```java
RecordPlayer recordPlayer = new RecordPlayer(hardwareMap.appContext);
```
### Get sound effect names
Then use ```recordPlayer.getSounds();``` to get an array of strings that contain all of the names

### Sound Effect names
- "ss_alarm"
- "ss_bb8_down"
- "ss_bb8_up"
- "ss_darth_vader"
- "ss_fly_by"
- "ss_mf_fail"
- "ss_laser"
- "ss_laser_burst"
- "ss_light_saber"
- "ss_light_saber_long"
- "ss_light_saber_short"
- "ss_light_speed"
- "ss_mine"
- "ss_power_up"
- "ss_r2d2_up"
- "ss_roger_roger"
- "ss_siren"
- "ss_wookie"

### Play a sound effect
Finally to play the sound effect use 
```java
recordPlayer.playSound("ss_bb8_up");
``` 
which will play the "ss_bb8_up" sound effect. **NOTE**: Make sure that it is being passed a name that is inside the ```recordPlayer.getSounds()``` list