package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;

public class VuforiaLocalizerPlus extends VuforiaLocalizerImpl {

    public VuforiaLocalizerPlus(Parameters parameter) {
        super(parameter);
    }

    public void close() {
        super.close();
    }
}
