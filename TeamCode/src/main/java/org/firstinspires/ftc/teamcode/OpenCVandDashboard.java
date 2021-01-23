package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvTrackerApiPipeline;

@Config
@TeleOp
public class OpenCVandDashboard extends LinearOpMode {

    VuforiaLocalizer vuforia = null;
    OpenCvCamera openCvPassthrough;

    public static double pctThreshold = 0.5;
    public static int thresholdThresh = 200;
    public static int thresholdMaxVal = 255;
    public static int cannyMinThreshold = 120;
    public static int cannyMaxThreshold = 150;

    @Override
    public void runOpMode() throws InterruptedException {



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance().splitLayoutForMultipleViewports(cameraMonitorViewId, 2, OpenCvCameraFactory.ViewportSplitMethod.VERTICALLY);



        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(viewportContainerIds[0]);
        parameters.vuforiaLicenseKey = "ATEunI3/////AAABmTcVGyhDpk/vjQi7sL+G3bkNZLIZNtw/qLByk8N+aFHflTAH7VSKoR4Cmzmgq62zIwg4ijmn/bzp1aCFu3u3S2aGfK0fBzUmddZV8n1+vO2sA4RRALRfeGnv/5UWQHXcqPg1Jz3yysRBhu4ur0g7FadQJq0sTfcoWWsELgQYAeFwsZSl+ktrswOc+SUyhrlJUDJijBL4y2kH4/3aeGYsQhQRVW/0EvSmbPuwMa+6Yo7u1f13PKEOdPWbJYybeGPjybwKrptzgTyNLhSoFqIFiUA8Ft1UD9IalVhGlEsHy6KBcWkvCxGJZBvANancvdTJ0O2Ux2pYsAllxQ+h2oR925ND/oeK5Mgno13LfBKkrm2B";
        parameters.cameraDirection   = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        openCvPassthrough = OpenCvCameraFactory.getInstance().createVuforiaPassthrough(vuforia, parameters, viewportContainerIds[1]);

        DistanceFromPaperTracker data = new DistanceFromPaperTracker(pctThreshold, thresholdThresh, thresholdMaxVal, cannyMinThreshold, cannyMaxThreshold);
        final OpenCvTrackerApiPipeline runner = new OpenCvTrackerApiPipeline();
        runner.addTracker(data);

        openCvPassthrough.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                // Using GPU acceleration can be particularly helpful when using Vuforia passthrough
                // mode, because Vuforia often chooses high resolutions (such as 720p) which can be
                // very CPU-taxing to rotate in software. GPU acceleration has been observed to cause
                // issues on some devices, though, so if you experience issues you may wish to disable it.
                openCvPassthrough.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
                //openCvPassthrough.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                openCvPassthrough.setPipeline(runner);

                // We don't get to choose resolution, unfortunately. The width and height parameters
                // are entirely ignored when using Vuforia passthrough mode. However, they are left
                // in the method signature to provide interface compatibility with the other types
                // of cameras.
                openCvPassthrough.showFpsMeterOnViewport(false);
                openCvPassthrough.startStreaming(0,0, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
        });
        FtcDashboard.getInstance().startCameraStream(vuforia, 0);
        telemetry = FtcDashboard.getInstance().getTelemetry();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Detected width", data.bounds.size.width);
            telemetry.addData("Predicted distance", data.computeDistance());
            telemetry.update();
        }
    }
}
