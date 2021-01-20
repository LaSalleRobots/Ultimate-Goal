package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import java.util.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.openftc.easyopencv.*;

public class OpenCVPipelineRunner {

    private OpenCvTrackerApiPipeline openCvTrackerApiPipeline;
    /**
     * The phonecam module @see org.openftc.easyopencv.OpenCvInternalCamera
     */
    public OpenCvInternalCamera phoneCam;

    OpenCVPipelineRunner(HardwareMap hardwareMap, OpenCvTracker... trackers) {

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
        openCvTrackerApiPipeline = new OpenCvTrackerApiPipeline();
        phoneCam.setPipeline(openCvTrackerApiPipeline);

        // Set the viewport renderer to use the gpu so we have better handling
        phoneCam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);

        for (OpenCvTracker tracker : trackers) {
            openCvTrackerApiPipeline.addTracker(tracker);
        }
    }

    public void start(boolean showData) {

        phoneCam.showFpsMeterOnViewport(showData);
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
    }


    public void start() {
        start(false);
    }

    public void addTracker(OpenCvTracker tracker) {
        openCvTrackerApiPipeline.addTracker(tracker);
    }

    public void removeTracker(OpenCvTracker tracker) {
        openCvTrackerApiPipeline.removeTracker(tracker);
    }

}
