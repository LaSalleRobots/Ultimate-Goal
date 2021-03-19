package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.*;

@Config
@TeleOp(name = "LauncherAlwaysOn (Action)", group = "Tests")
public class LauncherAutoAction extends LinearOpMode {

    public static double servoStart = 0.0;
    public static double servoEnd = 255.0;

    Servo servo = null;
    ObservableGamepad gamepad = null;

    @Override
    public void runOpMode() {
        DcMotor launcher1 = null;
        DcMotor launcher2 = null;
        launcher1 = hardwareMap.get(DcMotor.class, "left");
        launcher2 = hardwareMap.get(DcMotor.class, "right");
        servo = hardwareMap.get(Servo.class, "servo");

        gamepad = new ObservableGamepad(gamepad1);

        waitForStart();

        launcher1.setPower(-1.0);
        launcher2.setPower(1.0);

        gamepad.addObserver(new ServoButtonObserver());

        while (opModeIsActive()) {
            gamepad.tick(gamepad1);
        }
    }

    private class ServoButtonObserver implements Observer {

        boolean launch = false;

        @Override
        public void update(Observable observable, Object o) {
            if (gamepad.getGamepad().a) {
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
