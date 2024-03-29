package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OpenCV Ring Detection Algorithm", group = "AI")
public class DetectRingPosition extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    RingDetector ringTracker = new RingDetector();
    OpenCVPipelineRunner runner = new OpenCVPipelineRunner(hardwareMap, ringTracker);

    runner.start();
    waitForStart();

    while (opModeIsActive()) {
      sleep(1000 * 2);
      RingDetector.Position position = ringTracker.getPosition();

      telemetry.addData("Predicted Position", position);
      telemetry.update();
    }
  }
}
