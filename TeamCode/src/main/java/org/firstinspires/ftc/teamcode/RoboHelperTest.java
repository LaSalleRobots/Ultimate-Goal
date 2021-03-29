package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "RoboHelper Api Test", group = "API Test Group")
public class RoboHelperTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        runtime.reset();

        RoboHelper robot = new RoboHelper(hardwareMap, runtime);

        robot.moveForwards().runFor(2);
        robot.moveBackwardsRight().runFor(4);
        robot.rotateLeft().runFor(4);

    }
}
