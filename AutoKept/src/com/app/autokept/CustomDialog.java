package com.app.autokept;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class CustomDialog {

	Context context;
	Dialog dialog;

	public CustomDialog(Context context) {
		this.context = context;
		dialog = new Dialog(context);
	}

	public void ShowDialog(String msg) {

		AlertDialog.Builder builder = new Builder(context);

		builder.setMessage(msg);
		builder.setPositiveButton("OK", null);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog = builder.create();
		dialog.show();

	}

	public void ShowDialog(String title, String msg) {

		AlertDialog.Builder builder = new Builder(context);

		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("OK", null);

		dialog = builder.create();
		dialog.show();

	}

	public void ShowDialog(String title, String msg, int iconID,
			Boolean showTitle) {

		AlertDialog.Builder builder = new Builder(context);

		builder.setMessage(msg);
		builder.setIcon(context.getResources().getDrawable(iconID));
		builder.setPositiveButton("OK", null);

		if (showTitle)
			builder.setTitle(title);
		else
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog = builder.create();
		dialog.show();

	}
}
