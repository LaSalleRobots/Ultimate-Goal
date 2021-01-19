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
    PaperColorPipeline pipeline = new PaperColorPipeline(0.5);
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
      //telemetry.addData("bounds", pipeline.bounds);
      telemetry.update();
      sleep(100);
    }
  }

  class PaperColorPipeline extends OpenCvPipeline {

    private final Mat output = new Mat();
    private final Mat gray = new Mat();
    private final Mat threshold = new Mat();
    private final Mat edges = new Mat();
    private int thresholdThresh = 200;
    private int thresholdMaxVal = 255;
    private int cannyMinThreshold = 120;
    private int cannyMaxThreshold = 150;
    private final double pctBoundsThreshold;
    private final ArrayList<MatOfPoint> contours = new ArrayList<>();
    private final MatOfPoint2f largestContour2f = new MatOfPoint2f();

    public RotatedRect bounds = new RotatedRect();

    PaperColorPipeline(double pctThreshold) {
      pctBoundsThreshold = pctThreshold;
    }

    public PaperColorPipeline(
      double pctThreshold,
      int thresholdThresh,
      int thresholdMaxVal
    ) {
      this.pctBoundsThreshold = pctThreshold;
      this.thresholdThresh = thresholdThresh;
      this.thresholdMaxVal = thresholdMaxVal;
    }

    public PaperColorPipeline(
      double pctThreshold,
      int thresholdThresh,
      int thresholdMaxVal,
      int cannyMinThreshold,
      int cannyMaxThreshold
    ) {
      this.pctBoundsThreshold = pctThreshold;
      this.thresholdThresh = thresholdThresh;
      this.thresholdMaxVal = thresholdMaxVal;
      this.cannyMinThreshold = cannyMinThreshold;
      this.cannyMaxThreshold = cannyMaxThreshold;
    }

    @Override
    public Mat processFrame(Mat input) {
      contours.clear();
      Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
      Imgproc.blur(gray, gray, new Size(3, 3));
      Imgproc.threshold(
        gray,
        threshold,
        thresholdThresh,
        thresholdMaxVal,
        Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU
      );
      Imgproc.Canny(threshold, edges, cannyMinThreshold, cannyMaxThreshold);
      Imgproc.findContours(
        edges,
        contours,
        new Mat(),
        Imgproc.RETR_LIST,
        Imgproc.CHAIN_APPROX_SIMPLE
      );
      input.copyTo(output);
      //Imgproc.drawContours(output, contours, -1, new Scalar(225, 0, 0));
      MatOfPoint largestContour = max();

      if (largestContour != null) {
        largestContour.convertTo(largestContour2f, CvType.CV_32F);
        RotatedRect newBounds = Imgproc.minAreaRect(largestContour2f);
        double sizeDifference = newBounds.size.area() / bounds.size.area();
        if (
          sizeDifference > pctBoundsThreshold ||
          sizeDifference < -pctBoundsThreshold
        ) {
          bounds = newBounds;
        }
        Imgproc.rectangle(
          output,
          newBounds.boundingRect(),
          new Scalar(0, 255, 0)
        );
        Imgproc.rectangle(output, bounds.boundingRect(), new Scalar(255, 0, 0));
      }
      return output;
    }

    private MatOfPoint max() {
      if (contours.size() > 0) {
        MatOfPoint largest = contours.get(0);
        for (MatOfPoint point : contours.subList(1, contours.size())) {
          if (point.size().area() > largest.size().area()) {
            largest = point;
          }
        }
        return largest;
      }
      return null;
    }
  }
}
