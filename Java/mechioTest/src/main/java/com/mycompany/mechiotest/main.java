/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.mechiotest;

import org.jflux.api.common.rk.position.NormalizedDouble;
import org.mechio.api.animation.Animation;
import org.mechio.api.animation.messaging.RemoteAnimationPlayerClient;
import org.mechio.api.animation.player.AnimationJob;
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
    private RemoteAnimationPlayerClient animPlayer;
    private Animation happyAnim;
    private Animation sadAnim;
    private Animation panicAnim;
    private Animation surpriseAnim;
    private Animation angryAnim;
    public main (String robotIP) {
        System.out.println("Constructor("+robotIP+")");
        UserSettings.setRobotAddress(robotIP);
    }
    public void connectRobot() {
        System.out.println("Connect Robot");
        robot = MechIO.connectRobot();
        animPlayer = MechIO.connectAnimationPlayer();
        loadAnims();
    }
    
    public void loadAnims() {
        String prefix="../../Java/mechioTest/";
        happyAnim = MechIO.loadAnimation(prefix + "animations/AZR25_happy_01.anim.xml"); 
        sadAnim = MechIO.loadAnimation(prefix + "animations/AZR25_sad_01.anim.xml");
        surpriseAnim = MechIO.loadAnimation(prefix + "animations/AZR25_surprise_01.anim.xml");
        angryAnim = MechIO.loadAnimation(prefix + "animations/AZR25_angry_01.anim.xml");
        panicAnim = MechIO.loadAnimation(prefix + "animations/AZR25_panic_01.anim.xml");
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
    
    public int playAnimTime(String name) {
        return playAnim(name).getAnimationLength().intValue();
    }
    public AnimationJob playAnim(String name) {
        System.out.println("playAnim");
        System.out.println(name);
        switch (name) {
            case "happy":
                return playHappyAnim();
            case "sad":
                return playSadAnim();
            case "surprise":
                return playSurpriseAnim();
            case "angry":
                return playAngryAnim();
            case "panic":
                return playPanicAnim();
            default:
                break;
        }
        return null;
    }
    
    public AnimationJob playHappyAnim() {
        return playAnimation(happyAnim);
    }
    public AnimationJob playSadAnim() {
        return playAnimation(sadAnim);
    }
    public AnimationJob playPanicAnim() {
        return playAnimation(panicAnim);
    }
    public AnimationJob playAngryAnim() {
        return playAnimation(angryAnim);
    }
    public AnimationJob playSurpriseAnim() {
        return playAnimation(surpriseAnim);
    }
    
    public AnimationJob playAnimation(Animation anim) {
        return animPlayer.playAnimation(anim);
    }

    public static void main(String args[]) {
        main robot = new main("127.0.0.1");
        robot.connectRobot();
        System.out.println("connected");
        while (true) {
            System.out.println("Happy");
            AnimationJob job = robot.playHappyAnim();
            MechIO.sleep(500 + job.getAnimationLength());
            System.out.println("Sad");
            job = robot.playSadAnim();
            MechIO.sleep(500 + job.getAnimationLength());
            System.out.println("Angry");
            job = robot.playAngryAnim();
            MechIO.sleep(500 + job.getAnimationLength());
            
            System.out.println("Panic");
            job = robot.playPanicAnim();
            MechIO.sleep(500 + job.getAnimationLength());
            System.out.println("Surprise");
            job = robot.playSurpriseAnim();
            MechIO.sleep(500 + job.getAnimationLength());
            //robot.moveNeckYaw(.2, 1000);
           // MechIO.sleep(1000);
            //ro^ot.moveNeckYaw(.8, 1000);
            //MechIO.sleep(1000);
            
            
        }
    }
}
