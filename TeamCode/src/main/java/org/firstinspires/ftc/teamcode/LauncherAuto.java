package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.*;

@Config
@TeleOp(name = "LauncherAlwaysOn", group = "Tests")
public class LauncherAuto extends LinearOpMode {

    public static double servoStart = 0.0;
    public static double servoEnd = 1.0;

    @Override
    public void runOpMode() {
        DcMotor launcher1 = null;
        DcMotor launcher2 = null;
        launcher1 = hardwareMap.get(DcMotor.class, "left");
        launcher2 = hardwareMap.get(DcMotor.class, "right");
        Servo servo = hardwareMap.get(Servo.class, "servo");

        boolean launch = false;
        waitForStart();
        launcher1.setPower(-1.0);
        launcher2.setPower(1.0);
        while (opModeIsActive()) {
            if (gamepad1.a && (servo.getPosition() == servoStart || servo.getPosition() == servoEnd)) {
                if (!launch) {
                    servo.setPosition(servoEnd);
                    launch = true;
                } else {
                    servo.setPosition(servoStart);
                    launch = false;
                }
            }
        }



    }
}
