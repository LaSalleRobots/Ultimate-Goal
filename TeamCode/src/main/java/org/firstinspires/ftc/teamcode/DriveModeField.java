package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp(name = "Driver Controlled (Field Centric)", group = "Tests")
public class DriveModeField  extends LinearOpMode {

    private VuVision vision = null;

    private double getGamepadMoveMagnitude(Gamepad gamepad) {
        return Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
    }
    private double getGamepadTurnMagnitude(Gamepad gamepad) {
        return gamepad.right_stick_x;
    }

    private double getGamepadMoveAngle(Gamepad gamepad) {
        return Math.asin((gamepad.left_stick_x/gamepad.left_stick_y));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        vision = new VuVision(hardwareMap);
        DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
        DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
        DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
        DcMotor bR = hardwareMap.get(DcMotor.class, "bR");;

        double robotHeading = 0;

        vision.activateStart();

        waitForStart();

        while (opModeIsActive()) {
            vision.tick();
            fL.setPower(getGamepadMoveMagnitude(gamepad1) * Math.sin(vision.robotPosition.heading + (Math.PI/4)) + getGamepadTurnMagnitude(gamepad1));
            bL.setPower(getGamepadMoveMagnitude(gamepad1) * Math.sin(vision.robotPosition.heading - (Math.PI/4)) + getGamepadTurnMagnitude(gamepad1));
            fR.setPower(getGamepadMoveMagnitude(gamepad1) * Math.sin(vision.robotPosition.heading + (Math.PI/4)) - getGamepadTurnMagnitude(gamepad1));
            bR.setPower(getGamepadMoveMagnitude(gamepad1) * Math.sin(vision.robotPosition.heading - (Math.PI/4)) - getGamepadTurnMagnitude(gamepad1));

        }
    }

}
