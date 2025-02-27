package org.firstinspires.ftc.teamcode;

import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "JakeCode", group = "TeleOp")
public class Test extends LinearOpMode {
    DcMotorEx FrontLeft,FrontRight,BackLeft,BackRight,Slider;
    Servo grabber;

    double driveSpeed = 0.6;

    public void runOpMode() throws InterruptedException
    {
        FrontLeft = hardwareMap.get(DcMotorEx.class,"FrontLeft");
        FrontRight = hardwareMap.get(DcMotorEx.class, "FrontRight");
        BackLeft = hardwareMap.get(DcMotorEx.class, "RearLeft");
        BackRight = hardwareMap.get(DcMotorEx.class, "RearRight");

        Slider = hardwareMap.get(DcMotorEx.class, "Slider");

        grabber = hardwareMap.get(Servo.class,"Grabber");

        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE  );

        waitForStart();

        while(opModeIsActive()){
            double vert = -gamepad1.left_stick_y;
            double horz = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            if(Math.abs(vert) > .1 || Math.abs(horz) > .1 || Math.abs(rotate) > .1){
                Drive(vert,horz,rotate);
            }
            else{
                Drive(0,0,0);
            }

            if(gamepad1.a){
                grabber.setPosition(0);
            }
            if(gamepad1.b){
                grabber.setPosition(0.1);
            }


            if(gamepad1.dpad_up){
                Slider.setPower(0.3);
            }
            else if(gamepad1.dpad_down){
                Slider.setPower(-0.3);
            }
            else{
                Slider.setPower(0.0);
            }

            if(gamepad1.a){
                grabber.setPosition(.8);
            }
            if(gamepad1.b){
                grabber.setPosition(.85);
            }
        }
    }

    public void Drive(double vert, double horz, double rotate){
        double frdrive = vert - horz - rotate;
        double fldrive = vert + horz + rotate;
        double brdrive = vert + horz - rotate;
        double bldrive = vert - horz + rotate;

        double max = Math.abs(Math.max(Math.abs(frdrive),Math.max(Math.abs(fldrive),Math.max(Math.abs(brdrive),Math.abs(bldrive)))));

        FrontRight.setPower(driveSpeed * frdrive / max);
        FrontLeft.setPower(driveSpeed * fldrive / max);
        BackRight.setPower(driveSpeed * brdrive / max);
        BackLeft.setPower(driveSpeed * bldrive / max);
    }
}
