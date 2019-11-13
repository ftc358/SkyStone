package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Config
@TeleOp
public class cvThings extends LinearOpMode {
    OpenCvCamera phoneCam;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry telemetry = dashboard.getTelemetry();

    final private static int frameHeight = 320;
    final private static int frameWidth = 240;

    public static int fromBottom = 0;
    public static int stoneHeight = 40;

    Bitmap bmp = null;

    Runnable submitImage = new Runnable() {
        @Override
        public void run() {
            dashboard.sendImage(bmp);
        }
    };

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new SamplePipeline());
        phoneCam.startStreaming(frameHeight, frameWidth, OpenCvCameraRotation.UPRIGHT);

        ExecutorService thingy = Executors.newSingleThreadExecutor();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                phoneCam.stopStreaming();
            } else if (gamepad1.x) {
                phoneCam.pauseViewport();
            } else if (gamepad1.y) {
                phoneCam.resumeViewport();
            }

            thingy.submit(submitImage);

            sleep(100);
        }
    }


    class SamplePipeline extends OpenCvPipeline {
//        cols() = 240 = frameHeight
//        rows() = 320 = frameWidth

        final private int rectWidth = frameWidth / 3;
        final private int rectHeight = frameHeight / 3;

        Scalar green = new Scalar(0, 255, 0);

        @Override
        public Mat processFrame(Mat input) {

            Rect rectLeft = new Rect(fromBottom, 0, stoneHeight, rectHeight);
            Rect rectMiddle = new Rect(fromBottom, rectHeight, stoneHeight, rectHeight);
            Rect rectRight = new Rect(fromBottom, 2 * rectHeight, stoneHeight, rectHeight);

            Mat left = new Mat(input, rectLeft);
            Mat middle = new Mat(input, rectMiddle);
            Mat right = new Mat(input, rectRight);

            Scalar leftMean = Core.mean(left);
            Scalar middleMean = Core.mean(middle);
            Scalar rightMean = Core.mean(right);

            float[] leftHsv = new float[3];
            float[] middleHsv = new float[3];
            float[] rightHsv = new float[3];

            Color.RGBToHSV((int) leftMean.val[0], (int) leftMean.val[1], (int) leftMean.val[2], leftHsv);
            Color.RGBToHSV((int) middleMean.val[0], (int) middleMean.val[1], (int) middleMean.val[2], middleHsv);
            Color.RGBToHSV((int) rightMean.val[0], (int) rightMean.val[1], (int) rightMean.val[2], rightHsv);

//            telemetry.addData("leftHSV", "%.2f, %.2f, %.2f", leftHsv[0], leftHsv[1], leftHsv[2]);
//            telemetry.addData("middleHSV", "%.2f, %.2f, %.2f", middleHsv[0], middleHsv[1], middleHsv[2]);
//            telemetry.addData("rightHSV", "%.2f, %.2f, %.2f", rightHsv[0], rightHsv[1], rightHsv[2]);

            ArrayList<Float> values = new ArrayList<>();

            values.add(leftHsv[2]);
            values.add(middleHsv[2]);
            values.add(rightHsv[2]);

            telemetry.addData("skystone", values.indexOf(Collections.min(values)));

            telemetry.update();

            // drawing things on the screen so that things can be seen on screen
            Imgproc.rectangle(input, rectLeft, green, 2);
            Imgproc.rectangle(input, rectMiddle, green, 2);
            Imgproc.rectangle(input, rectRight, green, 2);

            try {
                bmp = Bitmap.createBitmap(input.cols(), input.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(input, bmp);
            } catch (CvException e) {
                Log.d("OH NO", e.getMessage());
            }

            return input;

        }
    }
}