package org.firstinspires.ftc.teamcode;

/* 2019-2020 FTC Robotics Skystone
 * (c) 2019-2020 La Salle Robotics
 * Developed for the Skystone competition
 */
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RoboHelper {

  private HardwareMap hardwareMap;
  private ElapsedTime runtime;

  //setup motors
  private DcMotor leftFront = null;
  private DcMotor rightFront = null;
  private DcMotor leftBack = null;
  private DcMotor rightBack = null;

  //Setup claw servos variables
  public Servo plateGrabber = null;
  public Servo plateGrabber2 = null;

  //
  private double fixionCoef = 1.75; //the distance the robot goes in 1 second (in feet)

  private double power = 0.5;
  public double leftFrontPower = 0.5;
  public double rightFrontPower = 0.5;
  public double leftBackPower = 0.5;
  public double rightBackPower = 0.5;
  public boolean closedMover = false;

  //setup class initializer
  public RoboHelper(HardwareMap hardwareMap, ElapsedTime runtime) {
    this.hardwareMap = hardwareMap;
    this.runtime = runtime;

    //setup motors
    this.leftFront = hardwareMap.get(DcMotor.class, "leftFront");
    this.rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    this.leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    this.rightBack = hardwareMap.get(DcMotor.class, "rightBack");
    this.plateGrabber = hardwareMap.get(Servo.class, "plateGrabber");
    this.plateGrabber2 = hardwareMap.get(Servo.class, "plateGrabber2");

    //Set Directions
    this.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
    this.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
    this.leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
    this.rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
  }

  public void sleep(double sleepTime) {
    double time = runtime.time();
    double initTime = time;

    while (time <= initTime + sleepTime) {
      time = runtime.time();
    }
  }

  public void togglePlateGrabber() {
    if (closedMover) {
      plateGrabber.setPosition(0.8);
      plateGrabber2.setPosition(0.2);
      closedMover = false;
    } else {
      plateGrabber.setPosition(0.2);
      plateGrabber2.setPosition(0.8);
      closedMover = true;
    }
  }

  public void powerOff() {
    leftFront.setPower(0);
    rightFront.setPower(0);
    leftBack.setPower(0);
    rightBack.setPower(0);
  }

  public void applyPower() {
    leftFront.setPower(leftFrontPower);
    rightFront.setPower(rightFrontPower);
    leftBack.setPower(leftBackPower);
    rightBack.setPower(rightBackPower);
  }

  public void runFor(double runTime) {
    applyPower();
    sleep(runTime);
    powerOff();
  }

  public void runDist(double runningDistance) {
    applyPower();
    sleep(runningDistance * fixionCoef);
    powerOff();
  }

  public void moveForwards() {
    rightFrontPower = -1;
    rightBackPower = 1;

    leftFrontPower = 1;
    leftBackPower = -1;
  }

  public void moveBackwards() {
    rightFrontPower = 1;
    rightBackPower = -1;

    leftFrontPower = -1;
    leftBackPower = 1;
  }

  public void moveLeft() {
    rightFrontPower = -1;
    rightBackPower = -1;

    leftFrontPower = -1;
    leftBackPower = -1;
  }

  public void moveRight() {
    rightFrontPower = 1;
    rightBackPower = 1;

    leftFrontPower = 1;
    leftBackPower = 1;
  }

  public void moveBackwardsLeft() {
    rightFrontPower = -1;
    rightBackPower = 0;

    leftFrontPower = 0;
    leftBackPower = -1;
  }

  public void moveBackwardsRight() {
    rightFrontPower = 0;
    rightBackPower = 1;

    leftFrontPower = 1;
    leftBackPower = 0;
  }

  public void moveForwardsLeft() {
    rightFrontPower = 0;
    rightBackPower = -1;

    leftFrontPower = -1;
    leftBackPower = 0;
  }

  public void moveForwardsRight() {
    rightFrontPower = 1;
    rightBackPower = 0;

    leftFrontPower = 0;
    leftBackPower = 1;
  }

  public void rotateLeft() {
    leftFrontPower = -1;
    rightFrontPower = -1;
    leftBackPower = 1;
    rightBackPower = 1;
  }

  public void rotateRight() {
    leftFrontPower = 1;
    rightFrontPower = 1;
    leftBackPower = -1;
    rightBackPower = -1;
  }

  public void slowScanRight() {
    leftFrontPower = 0.25;
    rightFrontPower = 0.25;
    leftBackPower = -0.25;
    rightBackPower = -0.25;
  }

  public void slowScanLeft() {
    leftFrontPower = -0.25;
    rightFrontPower = -0.25;
    leftBackPower = 0.25;
    rightBackPower = 0.25;
  }
}
