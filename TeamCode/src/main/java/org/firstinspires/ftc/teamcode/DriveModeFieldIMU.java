package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Config
@TeleOp(name = "Driver Controlled (Field Centric [IMU])", group = "Tests")
public class DriveModeFieldIMU extends LinearOpMode {

  private BNO055IMU imu = null;
  private Orientation angles;
  private Acceleration gravity;

  private double getGamepadMoveMagnitude(Gamepad gamepad) {
    return Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
  }

  private double getGamepadTurnMagnitude(Gamepad gamepad) {
    return gamepad.right_stick_x;
  }

  private double getGamepadMoveAngle(Gamepad gamepad) {
    return Math.asin((gamepad.left_stick_x / gamepad.left_stick_y));
  }

  private double getRobotHeading() {
    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    return angles.firstAngle;
  }

  @Override
  public void runOpMode() throws InterruptedException {
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
    parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    imu = hardwareMap.get(BNO055IMU.class, "imu");
    imu.initialize(parameters);
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
    DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
    DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
    DcMotor bR = hardwareMap.get(DcMotor.class, "bR");
    waitForStart();

    while (opModeIsActive()) {
      fL.setPower(
        getGamepadMoveMagnitude(gamepad1) *
        Math.sin(getRobotHeading() + (Math.PI / 4)) +
        getGamepadTurnMagnitude(gamepad1)
      );
      bL.setPower(
        getGamepadMoveMagnitude(gamepad1) *
        Math.sin(getRobotHeading() - (Math.PI / 4)) +
        getGamepadTurnMagnitude(gamepad1)
      );
      fR.setPower(
        getGamepadMoveMagnitude(gamepad1) *
        Math.sin(getRobotHeading() + (Math.PI / 4)) -
        getGamepadTurnMagnitude(gamepad1)
      );
      bR.setPower(
        getGamepadMoveMagnitude(gamepad1) *
        Math.sin(getRobotHeading() - (Math.PI / 4)) -
        getGamepadTurnMagnitude(gamepad1)
      );
    }
  }
}
