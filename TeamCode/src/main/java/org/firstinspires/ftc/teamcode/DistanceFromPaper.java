package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import java.util.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.openftc.easyopencv.*;

/**
 * In this sample, we demonstrate how to use the advanced features provided by the {@link
 * OpenCvInternalCamera} interface
 */
@TeleOp(name = "OpenCV Paper Detection Demo Algorithm", group = "AI")
@Disabled
public class DistanceFromPaper extends LinearOpMode {

  @Override
  public void runOpMode() {
    DistanceFromPaperTracker distanceTracker = new DistanceFromPaperTracker(0.5);
    OpenCVPipelineRunner runner = new OpenCVPipelineRunner(hardwareMap, distanceTracker);

    runner.start();
    waitForStart();

    while (opModeIsActive()) {
      telemetry.addData("FPS", runner.phoneCam.getFps());
      telemetry.addData("Pipeline (ms)", runner.phoneCam.getPipelineTimeMs());
      telemetry.addData("Total Frame time (ms)", runner.phoneCam.getTotalFrameTimeMs());
      telemetry.addData(
          "W, H (px)",
          distanceTracker.bounds.size.width + ", " + distanceTracker.bounds.size.height);
      telemetry.addData("distance (in)", distanceTracker.computeDistance());
      telemetry.update();
      sleep(100);
    }
  }
}
