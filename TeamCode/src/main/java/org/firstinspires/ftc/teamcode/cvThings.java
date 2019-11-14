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

    SamplePipeline samplePipeline;
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
        samplePipeline = new SamplePipeline();
        phoneCam.setPipeline(samplePipeline);

        ExecutorService networking = Executors.newSingleThreadExecutor();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                phoneCam.startStreaming(frameHeight, frameWidth, OpenCvCameraRotation.UPRIGHT);
            } else if (gamepad1.b) {
                phoneCam.stopStreaming();
            } else if (gamepad1.x) {
                phoneCam.closeCameraDevice();
            }

            networking.submit(submitImage);

            telemetry.addData("skystonePosition", samplePipeline.getPosition());
            telemetry.update();
        }
    }


    class SamplePipeline extends OpenCvPipeline {

        int skystonePosition;
        Scalar green = new Scalar(0, 255, 0);

        @Override
        public Mat processFrame(Mat input) {

            Rect rectLeft = new Rect(fromBottom, 0, stoneHeight, frameHeight / 3);
            Rect rectMiddle = new Rect(fromBottom, frameHeight / 3, stoneHeight, frameHeight / 3);
            Rect rectRight = new Rect(fromBottom, 2 * frameHeight / 3, stoneHeight, frameHeight / 3);

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

            ArrayList<Float> values = new ArrayList<>();

            values.add(leftHsv[2]);
            values.add(middleHsv[2]);
            values.add(rightHsv[2]);

            skystonePosition = values.indexOf(Collections.min(values));

            // drawing things on the screen so that things can be seen on screen
            Imgproc.rectangle(input, rectLeft, green, 1);
            Imgproc.rectangle(input, rectMiddle, green, 1);
            Imgproc.rectangle(input, rectRight, green, 1);

            try {
                bmp = Bitmap.createBitmap(input.cols(), input.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(input, bmp);
            } catch (CvException e) {
                Log.d("OH NO", e.getMessage());
            }

            return input;
        }

        public int getPosition() {
            return skystonePosition;
        }


    }
}

