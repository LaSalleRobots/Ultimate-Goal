package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.*;

import java.util.Observable;

public class ObservableGamepad extends Observable {

  private Gamepad last_state;
  private Gamepad current_state;

  public ObservableGamepad(Gamepad gp) {
    try {
      this.current_state.copy(gp);
      this.last_state.copy(gp);
    } catch (RobotCoreException e) {
    }
  }

  public void tick(Gamepad gp) {
    try {
      if (!last_state.equals(current_state)) {
        setChanged();
      }
      this.current_state.copy(gp);
      this.last_state.copy(current_state);
      notifyObservers(current_state);
    } catch (RobotCoreException e) {

    }
  }
  public Gamepad getGamepad() {
    return current_state;
  }

}
