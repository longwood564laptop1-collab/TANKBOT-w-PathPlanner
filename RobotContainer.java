// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


// PathPlanner imports
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.commands.PathPlannerAuto;

public class RobotContainer {
  public final DriveTrain drive = new DriveTrain();

  private final CommandXboxController m_DriverController = new CommandXboxController(0);
    
    public RobotContainer() {


     Sendable autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);

        ConfigureBindings();
      drive.setDefaultCommand(
          new InstantCommand(
              () -> drive.move(
                  m_DriverController.getLeftY() / 3,
                  m_DriverController.getRightX() / 3
              ),
              drive
          )
      );
    }
  
    public void ConfigureBindings(){
      
     }


    public Command getAutonomousCommand() {
      return new PathPlannerAuto("x");
    }

    
}



 //QUICK FIX WARRIOR//QUICK FIX WARRIOR//QUICK FIX WARRIOR//QUICK