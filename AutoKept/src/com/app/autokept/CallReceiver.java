package com.app.autokept;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by Mayank on 5/13/2015.
 */
public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {

        System.out.println("call 1" + number);

//        Intent buttonDown = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        buttonDown.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
//        ctx.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");
//
//        // froyo and beyond trigger on buttonUp instead of buttonDown
//        Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
//        ctx.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");

//        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//
//        Method m1 = tm.getClass().getDeclaredMethod("getITelephony");
//        m1.setAccessible(true);
//        Object iTelephony = m1.invoke(tm);
//
//        Method m2 = iTelephony.getClass().getDeclaredMethod("silenceRinger");
//        Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");
//
//        m2.invoke(iTelephony);
//        m3.invoke(iTelephony);

        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (tm == null) {
                // this will be easier for debugging later on
                throw new NullPointerException("tm == null");
            }

            System.out.println("call2" + tm);
            // do reflection magic
            tm.getClass().getMethod("answerRingingCall").invoke(tm);//answerRingingCall
        } catch (Exception e) {
            Log.e("sdsd", "Unable to use the Telephony Manager directly.", e);
        }


    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
    }

}