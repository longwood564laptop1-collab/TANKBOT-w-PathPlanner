// package frc.robot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.studica.frc.AHRS;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

// import edu.wpi.first.apriltag.AprilTagDetector.Config;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
// import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.math.geometry.Rotation2d;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;  
import edu.wpi.first.wpilibj.DriverStation;
//QUICK FIX WARRIOR
//QUICK FIX WARRIOR
//QUICK FIX WARRIOR
//GRADLE ISSUE
//STOP USING AI U CHEATER. STOP COPYING
public class DriveTrain extends SubsystemBase{
    public final static int kPortFrontRight = 1;
    public final static int kPortFrontLeft = 4;
    public final static int kPortBackRight = 2;
    public final static int kPortBackLeft  = 3;
   
    private final SparkMax FrontRight = new SparkMax(kPortFrontRight, MotorType.kBrushless);
    private final SparkMax FrontLeft = new SparkMax(kPortFrontLeft, MotorType.kBrushless);
    private final SparkMax BackRight = new SparkMax(kPortBackRight, MotorType.kBrushless);
    private final SparkMax BackLeft = new SparkMax(kPortBackLeft, MotorType.kBrushless);
    

    // bc he hit too much shit :((((
    private final SparkMaxConfig motorConfig = new SparkMaxConfig();
    // Encoders (for PathPlanner)
    private final RelativeEncoder m_LeftEncoder = FrontLeft.getEncoder();
    private final RelativeEncoder m_RightEncoder = FrontRight.getEncoder();

    // Gyro - bro had the wrong bc of chatgpt //dam not like its in every bit bucket ever
    final AHRS m_Gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);

    // Sensors 
    private final DigitalInput m_LimitSwitch = new DigitalInput(1);
    private final DigitalInput m_BeamBreak = new DigitalInput(2);
    private final AnalogPotentiometer m_Ultrasonic = new AnalogPotentiometer(6);
    private final Ultrasonic m_ultrasonic = new Ultrasonic(4, 5); 

    final double kTrackWidthMeters = 0.6;
    final double kWheelDiameterMeters = 0.1524;
    final double kGearRatio = 10.71;
    final double kEncoderDistancePerRotation =
        (kWheelDiameterMeters * Math.PI) / kGearRatio;

    private final DifferentialDriveKinematics m_Kinematics =
     new DifferentialDriveKinematics(kTrackWidthMeters);

     //explain
// private final DifferentialDriveOdometry m_Odometry =
//         new DifferentialDriveOdometry(
//             getGyroRotation(),
//             0,
//             0
//         );

private final DifferentialDrive m_DifferentialDrive =
    new DifferentialDrive(this::LeftDriveMotors, this::RightDriveMotors);

     public void RightDriveMotors(double speed) {
        FrontRight.set(speed);
        BackRight.set(speed);
    }


        public void LeftDriveMotors(double speed) {
            FrontLeft.set(speed);
            BackLeft.set(speed);
    }


private DifferentialDriveOdometry odometry
    = new DifferentialDriveOdometry(
        new Rotation2d(),
        -m_LeftEncoder.getPosition(),
        -m_RightEncoder.getPosition()
    );

    /*
     * CONSTRUCTOR THIS GUY IS NAME IS JEREMIAH 
     */
    public DriveTrain() {

        motorConfig.idleMode(IdleMode.kBrake);
        
        FrontLeft.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        FrontRight.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        BackLeft.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        BackRight.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        FrontRight.setInverted(false);
        FrontLeft.setInverted(true);
        BackRight.setInverted(false);
        BackLeft.setInverted(true); //false (what is false?)



      
    final DifferentialDrivePoseEstimator m_PoseEstimator =
            new DifferentialDrivePoseEstimator(
                    m_Kinematics,
                    m_Gyro.getRotation2d(),
                    -m_LeftEncoder.getPosition(),
                    -m_RightEncoder.getPosition(),
                    new Pose2d(),
                    VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
                    VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(30))
            );
           
    }

   Config pathplanner;

    public void move(double controllerLeftX, double controllerRightY){
        m_DifferentialDrive.arcadeDrive(controllerLeftX, controllerRightY);
    }


    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Encoder", -m_LeftEncoder.getPosition());
        SmartDashboard.putNumber("Right Encoder", -m_RightEncoder.getPosition());
        SmartDashboard.putNumber("gyro", m_Gyro.getRotation2d().getDegrees());
    }

    public Pose2d getPose() {
    return odometry.getPoseMeters();
}

public void resetPose(Pose2d pose) {
    odometry.resetPosition(
        new Rotation2d(),
        0.0,
        0.0,
        pose
    );
}



public ChassisSpeeds getRobotRelativeSpeeds() {
         DifferentialDriveWheelSpeeds speeds = new DifferentialDriveWheelSpeeds(
                -m_LeftEncoder.getVelocity(),
                -m_RightEncoder.getVelocity()
            );
    return m_Kinematics.toChassisSpeeds(speeds);
    
}

public void driveRobotRelative(ChassisSpeeds speeds) {
    m_DifferentialDrive.arcadeDrive(
        speeds.vxMetersPerSecond,
        speeds.omegaRadiansPerSecond
    );
}

      RobotConfig config; {
     try {
        RobotConfig config = RobotConfig.fromGUISettings();

// bro didnt check the docs
//KEEP
        AutoBuilder.configure(
            this::getPose,
            this::resetPose,
            this::getRobotRelativeSpeeds,
            (speeds, feedforwards) -> driveRobotRelative(speeds),
            new PPLTVController(0.02), //had at 5?? idk
            config,
            () -> DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == DriverStation.Alliance.Red, // had blue
            this
        );

//if i remove this syntax errors pop up and i dont feel like dealing with those
    } catch (Exception e) {
        DriverStation.reportError(
            "PathPlanner config failed",
            e.getStackTrace()
        );
    }
}
 }
