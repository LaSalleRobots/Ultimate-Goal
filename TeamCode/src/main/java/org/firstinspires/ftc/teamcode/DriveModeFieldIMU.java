package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp(name = "Driver Controlled (Field Centric [IMU])", group = "Driving")
public class DriveModeFieldIMU extends LinearOpMode {

  private MasqAdafruitIMU imu = null;

  private double getGamepadMoveMagnitude(Gamepad gamepad) {
    return -Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
  }

  private double getGamepadTurnMagnitude(Gamepad gamepad) {
    return gamepad.right_stick_x;
  }

  private double getGamepadMoveAngle(Gamepad gamepad) {
    return Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x);
  }

  private double getRobotHeading() {
    return imu.getPitch();
  }

  @Override
  public void runOpMode() throws InterruptedException {
    imu = new MasqAdafruitIMU("imu", hardwareMap);
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
    DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
    DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
    DcMotor bR = hardwareMap.get(DcMotor.class, "bR");
    imu.reset();
    waitForStart();

    while (opModeIsActive()) {
      fL.setPower(
          getGamepadMoveMagnitude(gamepad1)
                  * Math.sin((getGamepadMoveAngle(gamepad1) - getRobotHeading()) + (Math.PI / 4))
              + getGamepadTurnMagnitude(gamepad1));
      bL.setPower(
          getGamepadMoveMagnitude(gamepad1)
                  * Math.sin((getGamepadMoveAngle(gamepad1) - getRobotHeading()) - (Math.PI / 4))
              + getGamepadTurnMagnitude(gamepad1));
      fR.setPower(
          getGamepadMoveMagnitude(gamepad1)
                  * Math.sin((getGamepadMoveAngle(gamepad1) - getRobotHeading()) + (Math.PI / 4))
              - getGamepadTurnMagnitude(gamepad1));
      bR.setPower(
          getGamepadMoveMagnitude(gamepad1)
                  * Math.sin((getGamepadMoveAngle(gamepad1) - getRobotHeading()) - (Math.PI / 4))
              - getGamepadTurnMagnitude(gamepad1));
    }
  }
}
