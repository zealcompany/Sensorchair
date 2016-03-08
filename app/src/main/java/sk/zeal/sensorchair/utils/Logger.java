package sk.zeal.sensorchair.utils;

import android.util.Log;

/**
 * Created by denis.valdman on 8. 3. 2016.
 */
public class Logger {
    private static boolean isDebugEnabled = true;
    private static final String TAG = "SensorChair";
    private static int bufferSize = 4055;

    public  static void initialize(boolean isDebugEnabled){
        Logger.isDebugEnabled = isDebugEnabled;
    }

    public static void i(String tag, String msg){
        if(isDebugEnabled) {
            do {
                Log.i(tag, msg.substring(0, msg.length() > bufferSize ? bufferSize : msg.length()));
                msg = msg.substring(msg.length() > bufferSize?bufferSize:msg.length(),msg.length());
            }while(msg.length() > 0);
        }
    }

    public static void i(String msg){
        i(TAG, msg);
    }

    public static void e(String tag, String msg){
        if(isDebugEnabled) {
            do {
                Log.e(tag, msg.substring(0,msg.length() > bufferSize?bufferSize:msg.length()));
                msg = msg.substring(msg.length() > bufferSize?bufferSize:msg.length(),msg.length());
            }while(msg.length() > 0);
        }
    }

    public static void e(String msg){
        e(TAG, msg);
    }

    public static void e(Exception e){
        e(TAG, e.getMessage());
        e.printStackTrace();
    }
}

