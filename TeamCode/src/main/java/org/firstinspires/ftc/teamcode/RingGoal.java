package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Autonomus Ring Goal Detector", group = "AI")
public class RingGoal extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    RingDetector ringTracker = new RingDetector();
    OpenCVPipelineRunner runner = new OpenCVPipelineRunner(hardwareMap, ringTracker);
    RoboHelper roboHelper = new RoboHelper();

    double oneSquareTime = 2.0;

    runner.start();
    waitForStart();

    while (opModeIsActive()) {
      sleep(1000 * 2);
      RingDetector.Position position = ringTracker.getPosition();

      telemetry.addData("Predicted Position", position);
      telemetry.update();

      roboHelper.calculateDirections(0, -1, 0);
      if (RingDetector.Position.A == position) {
        roboHelper.runFor(oneSquareTime * 3);
      } else if (RingDetector.Position.B == position) {
        roboHelper.runFor(oneSquareTime * 4);
        roboHelper.calculateDirections(-1, 0, 0);
        roboHelper.runFor(oneSquareTime);
      } else if (RingDetector.Position.C == position) {
        roboHelper.runFor(oneSquareTime * 5);
      }
    }
  }
}
