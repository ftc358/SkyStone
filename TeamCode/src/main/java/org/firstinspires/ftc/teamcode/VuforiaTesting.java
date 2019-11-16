package org.firstinspires.ftc.teamcode;

<<<<<<< Updated upstream
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VuforiaTesting extends VuforiaThings {

=======
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.concurrent.TimeUnit;

@Disabled
@TeleOp
public class VuforiaTesting extends VuforiaThings {

    private float things = 0;

>>>>>>> Stashed changes
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

<<<<<<< Updated upstream
            if (gamepad1.a) {
                dashboardTelemetry.addData("Y (in)", "%.2f", lookForThings());
                dashboardTelemetry.update();
            }
            else if (gamepad1.b) {
                dashboardTelemetry.addData("WTF", "oh no");
                dashboardTelemetry.update();
            }

        }

    }
}
=======
            telemetry.addData("wantToDie()", true);
            telemetry.update();

            if (gamepad1.a) {
                try {
                    TimeLimitedCodeBlock.runWithTimeout(new Runnable() {
                        @Override
                        public void run() {
                            things = lookForThings();
                        }
                    }, 5, TimeUnit.SECONDS);
                    telemetry.addData("Y (in)", "%.2f", things);
                } catch (Exception e) {
                    telemetry.addData("Timed out detecting", "Oh NO!");
                    Log.d("Timed out detecting", "randomly guessing");
                    things = 114514;
                    telemetry.addData("Y (in)", "%.2f", things);
                    telemetry.addData("isStopRequested", isStopRequested());
                }

            }

            else if (gamepad1.b) {
                telemetry.addData("WTF", "oh no");
            }

            if (gamepad1.x) {
                telemetry.addData("Y (in)", things);
//                telemetry.update();
            }

            telemetry.addData("wantToDie()", true);
            telemetry.update();

        }

    }

}

>>>>>>> Stashed changes
