package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Servos extends OpMode {

    Servo servo1;
    Servo servo2;
    Servo servo3;

    public void init() {

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
        servo3 = hardwareMap.servo.get("servo3");

    }

    public void loop() {

        servo1.setPosition(gamepad1.left_trigger);
        servo2.setPosition(gamepad1.right_trigger);
        servo3.setPosition(gamepad1.left_stick_y);

    }
}
