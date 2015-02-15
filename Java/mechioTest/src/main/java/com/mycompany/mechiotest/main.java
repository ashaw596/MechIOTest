/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.mechiotest;

import org.jflux.api.common.rk.position.NormalizedDouble;
import org.mechio.api.motion.*;
import org.mechio.api.motion.Robot.*;
import org.mechio.api.motion.messaging.RemoteRobot;
import org.mechio.client.basic.*;

/**
 *
 * @author Albert
 */
public class main {
    
    private RemoteRobot robot;
    public main (String robotIP) {
        System.out.println("Constructor("+robotIP+")");
        UserSettings.setRobotAddress(robotIP);
    }
    public void connectRobot() {
        System.out.println("Connect Robot");
        robot = MechIO.connectRobot();
    }
    public void moveNeckYaw(double goalPosition, int time) {
        JointId neckYaw = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.NECK_YAW));
        RobotPositionMap goalPositions = new RobotPositionHashMap();
        goalPositions.put(neckYaw, new NormalizedDouble(goalPosition));
        robot.move(goalPositions, time);
        System.out.println("position:" + goalPosition + " Time:" + time);
    }
    public static void sleep(long seconds) {
        MechIO.sleep(seconds);
    }
    public void test() {
        JointId waist = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.WAIST));
        JointId leg = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.RIGHT_HIP_YAW));
        JointId neckYaw = new JointId(robot.getRobotId(), new Joint.Id(R50RobotJoints.NECK_YAW));
        
        RobotPositionMap goalPositions = new RobotPositionHashMap();
        goalPositions.put(neckYaw, new NormalizedDouble(0.75));
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
    }
}
