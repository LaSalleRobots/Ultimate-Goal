package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import java.util.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.openftc.easyopencv.*;

/**
 * In this sample, we demonstrate how to use the advanced features provided
 * by the {@link OpenCvInternalCamera} interface
 */
@TeleOp(name = "OpenCV Paper Detection Demo Algorithm", group = "AI")
public class DistanceFromPaper extends LinearOpMode {

  /**
   * NB: we declare our camera as the {@link OpenCvInternalCamera} type,
   * as opposed to simply {@link OpenCvCamera}. This allows us to access
   * the advanced features supported only by the internal camera.
   */
  OpenCvInternalCamera phoneCam;

  @Override
  public void runOpMode() {
    /**
     * NOTE: Many comments have been omitted from this sample for the
     * sake of conciseness. If you're just starting out with EasyOpenCV,
     * you should take a look at {@link InternalCamera1Example} or its
     * webcam counterpart, {@link WebcamExample} first.
     */

    int cameraMonitorViewId = hardwareMap.appContext
      .getResources()
      .getIdentifier(
        "cameraMonitorViewId",
        "id",
        hardwareMap.appContext.getPackageName()
      ); // for camera preview

    phoneCam =
      OpenCvCameraFactory
        .getInstance()
        .createInternalCamera(
          OpenCvInternalCamera.CameraDirection.BACK,
          cameraMonitorViewId
        );
    phoneCam.openCameraDevice();
    DistanceFromPaperPipeline pipeline = new DistanceFromPaperPipeline(0.5);
    phoneCam.setPipeline(pipeline);

    // Set the viewport renderer to use the gpu so we have better handling
    phoneCam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
    phoneCam.showFpsMeterOnViewport(false);
    /*
     * We use the most verbose version of #startStreaming(), which allows us to specify whether we want to use double
     * (default) or single buffering. See the JavaDoc for this method for more details
     */
    phoneCam.startStreaming(
      640,
      480,
      OpenCvCameraRotation.SIDEWAYS_LEFT,
      OpenCvInternalCamera.BufferMethod.DOUBLE
    );

    waitForStart();

    while (opModeIsActive()) {
      telemetry.addData("FPS", phoneCam.getFps());
      telemetry.addData("Pipeline (ms)", phoneCam.getPipelineTimeMs());
      telemetry.addData(
        "Total Frame time (ms)",
        phoneCam.getTotalFrameTimeMs()
      );
      telemetry.addData("W, H (px)", pipeline.bounds.size.width+ ", " +pipeline.bounds.size.height);
      telemetry.addData("distance (in)", pipeline.computeDistance());
      //telemetry.addData("bounds", pipeline.bounds);
      telemetry.update();
      sleep(100);
    }
  }
}
