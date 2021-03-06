#include <iostream>
#include <stdio.h>
#include <jni.h>
#include <string.h>
#include <chrono>
#include <thread>
using namespace std;


JNIEnv* create_vm(JavaVM ** jvm, string path) {

    JNIEnv *env;
    JavaVMInitArgs vm_args;
    JavaVMOption options[1];
    path.insert(0,"-Djava.class.path=");
    options[0].optionString = (char *)path.c_str(); //Path to the java source code
    cout<<path.c_str()<<endl;
    vm_args.version = JNI_VERSION_1_6; //JDK version. This indicates version 1.6
    vm_args.nOptions = 1;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = JNI_TRUE;

    int ret = JNI_CreateJavaVM(jvm, (void**)&env, &vm_args);
    if(ret < 0)
    	printf("\nUnable to Launch JVM\n");
	return env;
}

int main(int argc, char* argv[])
{
	string pathToJavaClass = "../../Java/mechioTest/target/classes:../../Java/mechioTest/dependencies";
	JNIEnv *env;
	JavaVM * jvm;
	env = create_vm(&jvm, pathToJavaClass);
	if (env == NULL)
		return 1;

    cout << "Hello world!" << endl;


    /*jclass testClass = env->FindClass("Test");
    if (testClass) {
        jmethodID testMethod = env->GetStaticMethodID(testClass, "test", "()V");
        env->CallStaticVoidMethod(testClass, testMethod);
    } else {
        printf("Class not Found");
    }
    return 0;
    */
    jclass mechIOClass = env->FindClass("com/mycompany/mechiotest/Zeno");
    if (mechIOClass != NULL)
    {
        jstring robotIP = env->NewStringUTF("192.168.187.1");
        jmethodID zenoConstructor = env->GetMethodID(mechIOClass, "<init>", "(Ljava/lang/String;)V");
        jmethodID zenoConnectRobot = env->GetMethodID(mechIOClass, "connectRobot", "()V");
        //jmethodID mechIOTestRun = env->GetMethodID(mechIOClass, "test", "()V");
        //jmethodID mechIOMoveNeckYaw = env->GetMethodID(mechIOClass, "moveNeckYaw", "(DI)V");
        jmethodID zenoShowEmotion = env->GetMethodID(mechIOClass, "showEmotion", "(Ljava/lang/String;I)V");
        //jmethodID mechIOsleep = env->GetMethodID(mechIOClass, "sleep", "(J)V");
        //jmethodID mechIOplayAnim = env->GetMethodID(mechIOClass, "playAnim", "(Ljava/lang/String;)Lorg/mechio/api/animation/player/AnimationJob;");
        //jmethodID mechIOplayAnimTime = env->GetMethodID(mechIOClass, "playAnimTime", "(Ljava/lang/String;)I");
        //construct new Remote Robot and connect it
        jobject zeno = env->NewObject(mechIOClass, zenoConstructor, robotIP);
        env->CallVoidMethod(zeno, zenoConnectRobot);

        //Move Neck
        while(zenoShowEmotion) {
            printf("Neutral\n");
            env->CallVoidMethod(zeno, zenoShowEmotion, env->NewStringUTF("Neutral"), 500);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            printf("SlightSmile\n");
            env->CallVoidMethod(zeno, zenoShowEmotion, env->NewStringUTF("SmileSlight"), 500);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            printf("Smile\n");
            env->CallVoidMethod(zeno, zenoShowEmotion, env->NewStringUTF("SmileFull"), 500);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));

            printf("Lets do it again\n");
        }


            /*printf("moveHead\n");
            env->CallVoidMethod(mechIO, mechIOMoveNeckYaw, 0.2, (int)1000);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            env->CallVoidMethod(mechIO, mechIOMoveNeckYaw, 0.8, 1000);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            */


    }
	else
    {
    	printf("\nUnable to find the requested class\n");
    }
    int n = jvm->DestroyJavaVM();
    return 0;
}
