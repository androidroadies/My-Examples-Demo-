package com.mditservices.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
    		//Toast.makeText(context, "Alarm Received after 10 seconds.", Toast.LENGTH_SHORT).show();
    		Intent trIntent = new Intent("android.intent.action.MAIN");
     		trIntent.setClass(context, com.mditservices.scheduler.DialogNew.class);
     		trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        			
     	    context.startActivity(trIntent); 
    }
    
}