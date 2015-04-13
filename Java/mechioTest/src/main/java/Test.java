/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jflux.api.common.rk.position.NormalizedDouble;
import org.mechio.api.motion.*;
import org.mechio.api.motion.Robot.*;
import org.mechio.api.motion.messaging.RemoteRobot;
import org.mechio.client.basic.*;

/**
 *
 * @author Albert
 */
public class Test {
    public static void test() {
        System.out.println("TEST");
    }
    public static void test1() {
    
        
        System.out.println("test1");
        String robotIP = "143.215.107.169";
        
        UserSettings.setRobotAddress(robotIP);
        
        UserSettings.setRobotId("myRobot");
        RemoteRobot robot = MechIO.connectRobot();
        
        JointId waist = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.WAIST));
        JointId neckYaw = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.NECK_YAW));
        JointId leg = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.RIGHT_HIP_YAW));

        RobotPositionMap goalPositions = new RobotPositionHashMap();
        goalPositions.put(waist, new NormalizedDouble(0.5));
        goalPositions.put(neckYaw, new NormalizedDouble(.75));
        RobotPositionMap goalPositions2 = new RobotPositionHashMap();
        goalPositions2.put(neckYaw, new NormalizedDouble(0.25));

        //Moves the joints to the specified goal positions over 1000 milliseconds
        
        while (true) {
            System.out.println("running");
            robot.move(goalPositions, 1000);
            MechIO.sleep(1000);
            robot.move(goalPositions2, 1000);
            MechIO.sleep(1000);
        }
       
        
    }
    public static void main(String args[]) {
        test1();
    }
}
