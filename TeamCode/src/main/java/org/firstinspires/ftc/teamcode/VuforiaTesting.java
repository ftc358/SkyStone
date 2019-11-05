package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VuforiaTesting extends VuforiaThings {

    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                lookForThings();
            }
            else if (gamepad1.b) {
                telemetry.addData("WTF", "oh no");
                telemetry.update();
            }


        }

//        targetsSkyStone.deactivate();
    }
}
