package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public Jaguar frontLeft;
    public Jaguar frontRight;
    public Jaguar rearLeft;
    public Jaguar rearRight;
    
    public Victor midBelt;
    public Victor bigBelt;
    public Jaguar littleBelt;
    
    public Jaguar topSpindle;
    public Jaguar bottomSpindle;
    
    public Joystick leftJoy;
    public Joystick rightJoy;
    //  |
    // -O-
    //  |
    
    public static final int FL_PORT = 8;
    public static final int FR_PORT = 4;
    public static final int RL_PORT = 7;
    public static final int RR_PORT = 5;
    
    public static final int MID_PORT = 10; // RELAY 1
    public static final int BIG_PORT = 1; // PWM 1
    public static final int TOPBELT_PORT = 2; // PWM 2
    
    public static final int SPINDLE_PORT = 2;
    
    // Spindle is RELAY 2
    // Mid Riser RELAY 1
    
    public static final int BOTSPIN_PORT = 6;
    public static final int TOPSPIN_PORT = 9;
    
    private double topSpinSpeed = 0;
    private double botSpinSpeed = 0;
    
    private double lbspinspeed = 0;
    private double mbspinspeed = 0;
    private double bbspinspeed = 0;
    
    public void robotInit() 
    {
        frontLeft = new Jaguar(FL_PORT);
        frontRight = new Jaguar(FR_PORT);
        rearLeft = new Jaguar(RL_PORT);
        rearRight = new Jaguar(RR_PORT);
        
        leftJoy = new Joystick(1);
        rightJoy = new Joystick(2);
        
        bigBelt = new Victor(BIG_PORT);
        midBelt = new Victor(MID_PORT);
        littleBelt = new Jaguar(TOPBELT_PORT);
        
        topSpindle = new Jaguar(TOPSPIN_PORT);
        bottomSpindle = new Jaguar(BOTSPIN_PORT);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        
        drive(thresh(rightJoy.getX(),0.2), thresh(rightJoy.getY(), 0.2), thresh(rightJoy.getZ(), 0.2));
        moveBelt(leftJoy.getY());
        
        
        if(leftJoy.getZ() <= 0)
        {
            windUp(0,0);
        }
        else
        {
            windUp(leftJoy.getZ(), leftJoy.getZ());
        }
        
        if(leftJoy.getRawButton(11))
        {
            if(topSpinSpeed != 1)
                topSpinSpeed += 0.1;
        }
        else if(leftJoy.getRawButton(10))
        {
            if(topSpinSpeed != 0)
                topSpinSpeed -= 0.1;
        }
        else if(leftJoy.getRawButton(6))
        {
            if(botSpinSpeed != 1)
                botSpinSpeed += 0.1;
        }
        else if(leftJoy.getRawButton(7))
        {
            if(botSpinSpeed != 0)
                botSpinSpeed -= 0.1;
        }
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void drive(double x, double y, double z)
    {
        
        System.out.println("Y Value " + y + " X Value " + x + " Z Value " + z);
        frontLeft.set(-x - y + z);
        rearLeft.set(x - y + z);
        frontRight.set(-x + y + z);
        rearRight.set(x + y + z);
    }
    
    public void moveBelt(double power)
    {
//        if(rightJoy.getRawButton(7))
//            lbspinspeed += 0.1;
//        else if(rightJoy.getRawButton(8))
//            lbspinspeed -= 0.1;
//        if(rightJoy.getRawButton(9))
//            mbspinspeed += 0.1;
//        else if(rightJoy.getRawButton(10))
//            mbspinspeed -= 0.1;
//        if(rightJoy.getRawButton(11))
//            bbspinspeed += 0.1;
//        else if(rightJoy.getRawButton(12))
//            bbspinspeed -= 0.1;
        midBelt.set(power);
        bigBelt.set(-power);
        littleBelt.set(power);
    }
    
    public void windUp(double top, double bot)
    {
        topSpindle.set(-top);
        bottomSpindle.set(bot * 1.5);
    }
    
    public double thresh(double a, double b)
    {
        double returnval = a;
        if(a > b)
        {
            returnval -= b;
        }
        else if(a < -b)
        {
            returnval +=b;
        }
        else
        {
            returnval = 0;
        }
        
        return returnval;
    }
}