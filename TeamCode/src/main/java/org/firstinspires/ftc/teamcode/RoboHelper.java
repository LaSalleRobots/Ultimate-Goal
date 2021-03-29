package org.firstinspires.ftc.teamcode;

/* 2019-2020 FTC Robotics Ultimate-Goal
 * (c) 2019-2020 La Salle Robotics
 * Developed for the Ultimate Goal competition
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RoboHelper {

    private ElapsedTime runtime;

    // setup motors
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;

    private double fixionCoef = 1.75; // the distance the robot goes in 1 second (in feet)

    private double flP = 0;
    private double blP = 0;
    private double frP = 0;
    private double brP = 0;

    // setup class initializer
    public RoboHelper(HardwareMap hardwareMap, ElapsedTime runtime) {
        this.runtime = runtime;

        // setup motors
        this.leftFront = hardwareMap.get(DcMotor.class, "fL");
        this.rightFront = hardwareMap.get(DcMotor.class, "fR");
        this.leftBack = hardwareMap.get(DcMotor.class, "bL");
        this.rightBack = hardwareMap.get(DcMotor.class, "bR");

        // Set Directions
        this.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        this.leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public double magnitude(double x, double y) {
        return -Math.hypot(x, y);
    }

    public double angle(double x, double y) {
        return Math.atan2(y, x);
    }

    public void calculateDirections(double x, double y, double turn) {
        flP = magnitude(x, y) * Math.sin(angle(x, y) + (Math.PI / 4)) + turn;
        blP = magnitude(x, y) * Math.sin(angle(x, y) - (Math.PI / 4)) + turn;
        frP = magnitude(x, y) * Math.sin(angle(x, y) + (Math.PI / 4)) - turn;
        brP = magnitude(x, y) * Math.sin(angle(x, y) - (Math.PI / 4)) - turn;
    }

    public void sleep(double sleepTime) {
        double time = runtime.time();
        double initTime = time;

        while (time <= initTime + sleepTime) {
            time = runtime.time();
        }
    }

    public void powerOff() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    public void applyPower() {
        leftFront.setPower(flP);
        rightFront.setPower(frP);
        leftBack.setPower(blP);
        rightBack.setPower(brP);
    }

    public RoboHelper runFor(double runTime) {
        applyPower();
        sleep(runTime);
        powerOff();
        return this;
    }

    public RoboHelper runDist(double runningDistance) {
        applyPower();
        sleep(runningDistance * fixionCoef);
        powerOff();
        return this;
    }

    public RoboHelper moveForwards() {
        calculateDirections(0, -1, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveBackwards() {
        calculateDirections(0, -1, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveLeft() {
        calculateDirections(-1, 0, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveRight() {
        calculateDirections(1, 0, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveBackwardsLeft() {
        calculateDirections(-1, -1, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveBackwardsRight() {
        calculateDirections(1, -1, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveForwardsLeft() {
        calculateDirections(-1, 1, 0);
        applyPower();
        return this;
    }

    public RoboHelper moveForwardsRight() {
        calculateDirections(1, 1, 0);
        applyPower();
        return this;
    }

    public RoboHelper rotateLeft() {
        calculateDirections(0, 0, -1);
        applyPower();
        return this;
    }

    public RoboHelper rotateRight() {
        calculateDirections(0, 0, 1);
        applyPower();
        return this;
    }
}
