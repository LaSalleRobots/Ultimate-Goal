package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp
public class ServoTester extends LinearOpMode {

	public static double servoStart = 0.0;
	public static double servoEnd = 1.0;
	public static double waitTimeS = 1.0;

	private ElapsedTime runtime = new ElapsedTime();

	@Override
	public void runOpMode() {
		Servo servo = hardwareMap.get(Servo.class, "servo");
		runtime.reset();
		waitForStart();

		while (opModeIsActive()) {
			servo.setPosition(servoStart);
			Utils.Time.sleep( waitTimeS);
			servo.setPosition(servoEnd);
			Utils.Time.sleep(waitTimeS);
		}
	}

}
