/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
@TeleOp(name = "OpenCV", group = "AI")
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
    PaperDetectionPipeline pipeline = new PaperDetectionPipeline(20);
    phoneCam.setPipeline(pipeline);

    // Set the viewport renderer to use the gpu so we have better handling
    phoneCam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);

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
      telemetry.addData("bounds", pipeline.bounds);
      telemetry.update();
      sleep(100);
    }
  }

  class PaperDetectionPipeline extends OpenCvPipeline {

    Mat grey = new Mat();
    Mat display = new Mat();

    List<MatOfPoint> contours = new ArrayList<>();
    MatOfPoint big = new MatOfPoint();
    MatOfPoint2f pointBig = new MatOfPoint2f();
    RotatedRect bounds = new RotatedRect();
    private RotatedRect newBounds = new RotatedRect();
    private int thresh;


    PaperDetectionPipeline(int similarityThresh) {
      thresh = similarityThresh;
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

    @Override
    public Mat processFrame(Mat input) {
      input.copyTo(display);
      Imgproc.cvtColor(input, grey, Imgproc.COLOR_RGB2GRAY);
      Imgproc.blur(grey, grey, new Size(3.0, 3.0)); // Clean up the image for edge detection
      Imgproc.Canny(grey, grey, 120, 150); // find the edges with a canny edge detector
      contours.clear(); // clear our contours list as it doesn't reset every loop
      Imgproc.findContours(
        grey,
        contours,
        new Mat(),
        Imgproc.RETR_LIST,
        Imgproc.CHAIN_APPROX_SIMPLE
      );
      big = max();
      Imgproc.drawContours(display, contours, -1, new Scalar(225, 0, 0));
      if (big != null && opModeIsActive()) {
        big.convertTo(pointBig, CvType.CV_32F);

        newBounds = Imgproc.minAreaRect(pointBig);
        // Decide if the biggest bounds we have found are similar to the last frame processed
        if (
                (bounds.size.width - thresh <= newBounds.size.width && newBounds.size.width < bounds.size.width + thresh)
                        &&
                (bounds.size.height - thresh <= newBounds.size.height && newBounds.size.height < bounds.size.height + thresh)
        ) {
          bounds = newBounds;
         } else if (bounds.size.equals(new Size(0,0))) {
          bounds = newBounds;
        }

        /* This is for drawing the body of the rotated rect as that isn't supported officially
         * Point[] vertices = new Point[4];
         * bounds.points(vertices);
         * List<MatOfPoint> boxContours = new ArrayList<>();
         * boxContours.add(new MatOfPoint(vertices));
         * Imgproc.drawContours(display, boxContours, 0, new Scalar(128, 128, 128), -1);
         */

        Imgproc.rectangle(
          display,
          bounds.boundingRect(),
          new Scalar(0, 255, 255)
        );
      }

      return display;
    }
  }
}
