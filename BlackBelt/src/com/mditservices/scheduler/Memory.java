package com.mditservices.scheduler;

import android.app.ActivityGroup;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Memory extends ActivityGroup {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/embed/zbprdp-AMBs"));
         startActivity(browserIntent);
         finish();
	}
}
