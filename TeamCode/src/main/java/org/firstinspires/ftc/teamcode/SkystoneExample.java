package org.firstinspires.ftc.teamcode;
//
//import com.disnodeteam.dogecv.CameraViewDisplay;
//import com.disnodeteam.dogecv.DogeCV;
//import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.opencv.core.Rect;
//
//@Autonomous(group = "DogeCV")
public class SkystoneExample extends OpMode {
//    private SkystoneDetector detector;
//
//    @Override
    public void init() {
//        // Set up detector:
//        detector = new SkystoneDetector();
//        // Initialize it with the app context and camera
//        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
//        // Set detector to use default settings
//        detector.useDefaults();
//
//        // Can also be PERFECT_AREA
//        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
//        // if using PERFECT_AREA scoring
//        //detector.perfectAreaScorer.perfectArea = 10000;
//
//        // Setting the weight of the area scorer and ratio scorer
//        detector.maxAreaScorer.weight = 0.005;
//        detector.ratioScorer.weight = 5;
//
//        // Ratio adjustment for ratio scorer
//        detector.ratioScorer.perfectRatio = 1.0;
//
//        // Start the detector
//        detector.enable();
    }
//
//    @Override
    public void loop() {
//        // Gets the rectangle from what the detector found
//        Rect rect = detector.foundRectangle();
//
//        // Adds to telemetry about what the detector detected
//        // (whether or not the detector found anything, and if so it's location)
//        telemetry.addLine()
//                .addData("Is Found", detector.isDetected())
//                .addData("Location",
//                        detector.isDetected() ?
//                                (int) (rect.x + rect.width * 0.5) + ", "
//                                        + (int) (rect.y + 0.5 * rect.height)
//                                : "N/A");
//
//        // Update telemetry
//        telemetry.update();
    }
//
//    @Override
//    public void stop() {
//        // Disable the detector
//        if (detector != null) detector.disable();
//    }
}
