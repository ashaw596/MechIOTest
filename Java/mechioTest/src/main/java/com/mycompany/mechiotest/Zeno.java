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
public class Zeno {
    
    private RemoteRobot robot;
    private RemoteAnimationPlayerClient animPlayer;
    private Animation happyAnim;
    private Animation sadAnim;
    private Animation panicAnim;
    private Animation surpriseAnim;
    private Animation angryAnim;
    private boolean isReal;
    public Zeno (String robotIP, boolean isReal) {
        System.out.println("Constructor("+robotIP+")");
        UserSettings.setRobotAddress(robotIP);

        UserSettings.setRobotId("myRobot");
        this.isReal = isReal;
    }
    public boolean connectRobot() {
        System.out.println("Connect Robot");
        robot = MechIO.connectRobot();
        if (robot == null) {
            return false;
        }
        return true;
        //animPlayer = MechIO.connectAnimationPlayer();
        //loadAnims();
    }
    
    public void loadAnims() {
        String prefix="../../Java/mechioTest/";
        happyAnim = MechIO.loadAnimation(prefix + "animations/AZR25_happy_01.anim.xml"); 
        sadAnim = MechIO.loadAnimation(prefix + "animations/AZR25_sad_01.anim.xml");
        surpriseAnim = MechIO.loadAnimation(prefix + "animations/AZR25_surprise_01.anim.xml");
        angryAnim = MechIO.loadAnimation(prefix + "animations/AZR25_angry_01.anim.xml");
        panicAnim = MechIO.loadAnimation(prefix + "animations/AZR25_panic_01.anim.xml");
    }
    
    
    public void moveFace(int time, double goalMouth, double goalSmile, double goalEyebrows, double goalEyelids, double goalEyeYaw, double goalHeadPitch, double goalHeadYaw){
        RobotPositionMap goalPositions = new RobotPositionHashMap();
        JointId mouth = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.MOUTH));
        JointId smile = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.SMILE));
        JointId eyebrows = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.EYBROWS));
        JointId eyelids = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.EYELIDS));
        JointId eyeYaw = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.EYE_YAW));
        JointId headPitch = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.HEAD_PITCH));
        JointId headYaw = new JointId(robot.getRobotId(), new Joint.Id(R25.Joints.HEAD_YAW));
        if (NormalizedDouble.isValid(goalMouth)) {
            goalPositions.put(mouth, new NormalizedDouble(goalMouth));
        }
        
        if (NormalizedDouble.isValid(goalSmile)) {
            goalPositions.put(smile, new NormalizedDouble(goalSmile));
        }
        
        if (NormalizedDouble.isValid(goalEyebrows)) {
            goalPositions.put(eyebrows, new NormalizedDouble(goalEyebrows));
        }
        
        if (NormalizedDouble.isValid(goalEyelids)) {
            goalPositions.put(eyelids, new NormalizedDouble(goalEyelids));
        }
        
        if (NormalizedDouble.isValid(goalEyeYaw)) {
            goalPositions.put(eyeYaw, new NormalizedDouble(goalEyeYaw));
        }
        if (NormalizedDouble.isValid(goalHeadPitch)) {
            goalPositions.put(headPitch, new NormalizedDouble(goalHeadPitch));
        }
        
        if (NormalizedDouble.isValid(goalHeadYaw)) {
            goalPositions.put(headYaw, new NormalizedDouble(goalHeadYaw));
        }
        
        robot.move(goalPositions, time);
        System.out.println("position:" + goalPositions + " Time:" + time);

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
    
    public void showNeutral(int time) {
        if (isReal) {
            moveFace(time, 0.5 - 7.0/8.0/2, 0.5 - 3.0/8.0/2, 0.5, 0.5 + 7.0/8.0/2, 0.5, 0.5, -2);
        //moveFace(double goalMouth, double goalSmile, double goalEyebrows, double goalEyelids, double goalEyeYaw, double goalHeadPitch, double goalHeadYaw){

        } else {
            moveFace(time, 0.5 + 7.0/8.0/2, 0.5 + 2.0/8.0/2, 0.5, 0.5 + 4.0/8.0/2, 0.5, 0.5, -2);
        }
    }
    
    public void showVerySlightSmile(int time) {
        if (isReal) {
            moveFace(time, 0.5 - 7.0/8.0/2, 0.5 + 0.0/8.0/2, 0.5, 0.5 + 7.0/8.0/2, 0.5, 0.5, -2);
        } else {
            moveFace(time, 0.5 + 7.0/8.0/2, 0.5 + 4.0/8.0/2, 0.5, 0.5 + 4.0/8.0/2, 0.5, 0.5, -2);
        }
    }
    public void showSmileSlight(int time) {
        if (isReal) {
             moveFace(time, 0.5 - 7.0/8.0/2, 0.5 + 4.0/8.0/2, 0.5, 0.5 + 4.0/8.0/2, 0.5, 0.5, -2);
        } else {
            moveFace(time, 0.5 + 7.0/8.0/2, 0.5 + 6.0/8.0/2, 0.5, 0.5 + 2.0/8.0/2, 0.5, 0.5, -2);
        }
        //moveFace(double goalMouth, double goalSmile, double goalEyebrows, double goalEyelids, double goalEyeYaw, double goalHeadPitch, double goalHeadYaw){
    }
    
    public void showSmileAlmostFull(int time) {
        if (isReal) {
             moveFace(time, 0.5 - 4.0/8.0/2, 0.5 + 5.0/8.0/2, 0.5, 0.5 + 2.0/8.0/2, 0.5, 0.5, -2);
        } else {
            moveFace(time, 0.5 + 4.0/8.0/2, 0.5 + 6.5/8.0/2, 0.5, 0.5 + 2.0/8.0/2, 0.5, 0.5, -2);
        }
    }
    
    public void showSmileFull(int time) {
        if (isReal) {
            moveFace(time, 0.5 - 0/8.0/2, 0.5 + 8.0/8.0/2, 0.5, 0.5 + 2.0/8.0/2, 0.5, 0.5, -2);        
        } else {
            moveFace(time, 0.5 + 3.0/8.0/2, 0.5 + 8.0/8.0/2, 0.5, 0.5 + 1.0/8.0/2, 0.5, 0.5, -2);
        }
        
    }
    
    public boolean showEmotion(String emotion, int time) {
        switch (emotion) {
            case "Neutral":
                showNeutral(time);
                break;
            
            case "SmileVerySlight":
                showVerySlightSmile(time);
                break;
            
            case "SmileSlight":
                showSmileSlight(time);
                break;
            
            case "SmileAlmostFull":
                showSmileAlmostFull(time);
                break;
               
            case "SmileFull":
                showSmileFull(time);
                break;
            default:
                return false;
        }
        return true;
    }
    public static void cycleSmile(Zeno robot) {
        while(true) {
            System.out.println("Show Neutral");
            robot.showEmotion("Neutral", 500);
            MechIO.sleep(2000 + 500);
            
            System.out.println("Show Very Slight Smile");
            robot.showEmotion("SmileVerySlight", 500);
            MechIO.sleep(2000 + 500);
            
            System.out.println("Show Slight Smile");
            robot.showEmotion("SmileSlight", 500);
            MechIO.sleep(2000 + 500);
            
            System.out.println("Show Smile Almost Full");
            robot.showEmotion("SmileAlmostFull", 500);
            MechIO.sleep(2000 + 500);
            
            System.out.println("Show Full Smile");
            robot.showEmotion("SmileFull", 500);
            MechIO.sleep(2000 + 500);
        }
    }
    
    public static void main(String args[]) {
        Zeno robot = new Zeno("143.215.107.169", true);
        robot.connectRobot();
        System.out.println("connected");
        cycleSmile(robot);
        while (true) {
            /*
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
            */
            //robot.moveNeckYaw(.2, 1000);
           // MechIO.sleep(1000);
            //ro^ot.moveNeckYaw(.8, 1000);
            //MechIO.sleep(1000);
            
            
        }
    }
}
