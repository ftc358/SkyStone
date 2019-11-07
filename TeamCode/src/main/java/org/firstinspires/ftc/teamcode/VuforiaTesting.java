package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VuforiaTesting extends VuforiaThings {

    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

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
