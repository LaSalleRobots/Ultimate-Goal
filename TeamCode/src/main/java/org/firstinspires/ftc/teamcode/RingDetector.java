package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvTracker;

import java.util.ArrayList;

public class RingDetector extends OpenCvTracker {

    private static Mat rgb = new Mat();
    private final Mat output = new Mat();
    private final Mat orange = new Mat();
    private final Mat edges = new Mat();
    private final MatOfPoint2f largestContour2f = new MatOfPoint2f();

    // Tunables
    public Scalar lower = new Scalar(164, 80, 7);
    public Scalar upper = new Scalar(255, 255, 103.4);
    public Scalar cannyThresh = new Scalar(200, 255);
    public Scalar blur = new Scalar(7);

    public Scalar crop = new Scalar(100, 100);

    /** The bounds of the detected ring */
    public RotatedRect bounds = null;
    private ArrayList<MatOfPoint> contors = new ArrayList<>();




    @Override
    public Mat processFrame(Mat input) {
        contors.clear();
        bounds = null;

        Rect cropBox = new Rect((int)((input.width()/2)-(crop.val[0]/2)), (int)((input.height()/2)-(crop.val[1]/2)), (int)crop.val[0], (int)crop.val[1]);

        input = new Mat(input, cropBox);

        input.copyTo(output);

        Imgproc.cvtColor(input, rgb, Imgproc.COLOR_RGBA2RGB);
        Imgproc.blur(rgb, rgb, new Size(blur.val[0], blur.val[0]));
        Core.inRange(rgb, lower, upper, orange);

        // for display
        // output.release();
        // Core.bitwise_and(input, input, output, orange); // input, input, output, mask

        Imgproc.Canny(orange, edges, cannyThresh.val[0], cannyThresh.val[1]);
        Imgproc.findContours(edges, contors, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        // Imgproc.drawContours(output, contors, -1, new Scalar(255,0,0));

        MatOfPoint largestContour = max();

        if (largestContour != null) {
            largestContour.convertTo(largestContour2f, CvType.CV_32F);
            bounds = Imgproc.minAreaRect(largestContour2f);
            Imgproc.rectangle(output, bounds.boundingRect(), new Scalar(255, 0, 0));

        }
        return output;
    }


    /**
     * Finds and returns the largest point in the contours. Preforms a linear search
     *
     * @return MatofPoint the largest point in the contours
     * @see MatOfPoint
     */
    private MatOfPoint max() {
        if (contors.size() > 0) {
            MatOfPoint largest = contors.get(0);
            for (MatOfPoint point : contors.subList(1, contors.size())) {
                if (point.size().area() > largest.size().area()) {
                    largest = point;
                }
            }
            return largest;
        }
        return null;
    }

    public enum Position {
        A,
        B,
        C
    }


    public Position getPosition() {
        if (bounds == null) {
            return Position.A;
        } else if (bounds.size.width >= 8 && bounds.size.width <= 10) {
            return Position.B;
        } else {
            return Position.C;
        }
    }
}
