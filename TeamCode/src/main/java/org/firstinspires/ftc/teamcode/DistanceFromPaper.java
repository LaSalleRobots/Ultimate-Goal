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

import java.util.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import org.opencv.imgproc.*;
import org.opencv.core.*;
import org.openftc.easyopencv.*;


/**
 * In this sample, we demonstrate how to use the advanced features provided
 * by the {@link OpenCvInternalCamera} interface
 */
@TeleOp(name="OpenCV",group="AI")
public class DistanceFromPaper extends LinearOpMode
{
    /**
     * NB: we declare our camera as the {@link OpenCvInternalCamera} type,
     * as opposed to simply {@link OpenCvCamera}. This allows us to access
     * the advanced features supported only by the internal camera.
     */
    OpenCvInternalCamera phoneCam;

    @Override
    public void runOpMode()
    {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCV,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()); // for camera preview
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        UselessColorBoxDrawingPipeline pipeline = new UselessColorBoxDrawingPipeline(new Scalar(255, 255, 255));
        phoneCam.setPipeline(pipeline);

        /*
         * We use the most verbose version of #startStreaming(), which allows us to specify whether we want to use double
         * (default) or single buffering. See the JavaDoc for this method for more details
         */
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT, OpenCvInternalCamera.BufferMethod.DOUBLE);

        /*
         * Demonstrate how to turn on the flashlight
         */
        //phoneCam.setFlashlightEnabled(true);

        /*
         * Demonstrate how to use the zoom. Here we zoom
         * in as much as is supported.
         */
        //phoneCam.setZoom(phoneCam.getMaxSupportedZoom());

        /*
         * Demonstrate how to set the recording hint on the
         * camera hardware. See the JavDoc for this method
         * for more details.
         */
        phoneCam.setRecordingHint(true);

        /*
         * Demonstrate how to lock the camera hardware to sending frames at 30FPS, if it supports that
         */
        for (OpenCvInternalCamera.FrameTimingRange r : phoneCam.getFrameTimingRangesSupportedByHardware())
        {
            if(r.max == 30 && r.min == 30)
            {
                phoneCam.setHardwareFrameTimingRange(r);
                break;
            }
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("big", pipeline.big);
            telemetry.addData("bounds", pipeline.bounds);
            telemetry.update();
            sleep(100);
        }
    }

    class UselessColorBoxDrawingPipeline extends OpenCvPipeline
    {
        Scalar color;
        Mat grey = new Mat();
        Mat display = new Mat();

        List<MatOfPoint> contors = new ArrayList<>();
        MatOfPoint big = new MatOfPoint();
        Rect bounds = new Rect();

        UselessColorBoxDrawingPipeline(Scalar color)
        {
            this.color = color;
        }


        private MatOfPoint max() {
            if (contors.size() > 0) {
                MatOfPoint largest = contors.get(0);
                for (MatOfPoint point : contors) {
                    if (point.size().area() > largest.size().area()) {
                        largest = point;
                    }
                }
                return largest;
            }
            return null;
        }

        @Override
        public Mat processFrame(Mat input)
        {
            input.copyTo(display);
            Imgproc.cvtColor(input, grey, Imgproc.COLOR_RGB2GRAY);
            Imgproc.blur(grey, grey, new Size(7.0, 7.0));
            Imgproc.Canny(grey, grey, 60, 125);
            contors.clear();
            Imgproc.findContours(grey, contors, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            big = max();
            contors.clear();
            if (big != null && opModeIsActive()) {
                contors.add(big);
                bounds = Imgproc.boundingRect(big);
                Imgproc.rectangle(display, bounds, new Scalar(0, 255, 255));

            }
            Imgproc.drawContours(display, contors, -1, new Scalar(225, 0,0));
            return display;
        }
    }
}