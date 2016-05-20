package com.mditservices.scheduler;
/*package com.webinfoways.blackbelt;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

*//**
 * @author Adil Soomro
 * 
 *//*
@SuppressWarnings("deprecation")
public class TabSample extends TabActivity {
	*//** Called when the activity is first created. *//*

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		setTabs();
	}

	private void setTabs() {
		addTab("", R.drawable.tab_entername, WorkoutTabGroup.class);
		addTab("", R.drawable.tab_reviewname, ReviewGroup.class);

		addTab("", R.drawable.tab_memory, MemoryGroup.class);
	//	addTab("", R.drawable.tab_settings, SettingGroup.class);
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}*/