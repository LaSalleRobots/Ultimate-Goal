package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp(name = "Driver Controlled (Robot Centric)", group = "Tests")
public class DriveModeRobot extends LinearOpMode {

  private double getGamepadMoveMagnitude(Gamepad gamepad) {
    return -Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
  }

  private double getGamepadTurnMagnitude(Gamepad gamepad) {
    return gamepad.right_stick_x;
  }

  private double getGamepadMoveAngle(Gamepad gamepad) {
    return Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x);
  }

  @Override
  public void runOpMode() throws InterruptedException {
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
    DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
    DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
    DcMotor bR = hardwareMap.get(DcMotor.class, "bR");

    fL.setDirection(DcMotorSimple.Direction.REVERSE);
    bL.setDirection(DcMotorSimple.Direction.REVERSE);
    fR.setDirection(DcMotorSimple.Direction.FORWARD);
    bR.setDirection(DcMotorSimple.Direction.FORWARD);


    waitForStart();

    double flP = 0;
    double blP = 0;
    double frP = 0;
    double brP = 0;

    while (opModeIsActive()) {

      flP = getGamepadMoveMagnitude(gamepad1) *
              Math.sin(getGamepadMoveAngle(gamepad1) + (Math.PI / 4)) +
              getGamepadTurnMagnitude(gamepad1);
      fL.setPower(flP);
      blP = getGamepadMoveMagnitude(gamepad1) *
              Math.sin(getGamepadMoveAngle(gamepad1) - (Math.PI / 4)) +
              getGamepadTurnMagnitude(gamepad1);
      bL.setPower(blP);
      frP = getGamepadMoveMagnitude(gamepad1) *
              Math.sin(getGamepadMoveAngle(gamepad1) + (Math.PI / 4)) -
              getGamepadTurnMagnitude(gamepad1);
      fR.setPower(frP);
      brP = getGamepadMoveMagnitude(gamepad1) *
              Math.sin(getGamepadMoveAngle(gamepad1) - (Math.PI / 4)) -
              getGamepadTurnMagnitude(gamepad1);
      bR.setPower(brP);
      telemetry.addData("Front Left", flP);
      telemetry.addData("Front Right", frP);
      telemetry.addData("Back Left", blP);
      telemetry.addData("Back Right", brP);
      telemetry.update();
    }
  }
}
