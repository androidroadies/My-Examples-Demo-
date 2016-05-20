package com.mditservices.scheduler;

import java.util.ArrayList;

import com.mditservices.scheduler.Favorate.ListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class DialogNew extends Activity {

	ArrayList<Contact> _items = new ArrayList<Contact>();
	ArrayList<String> _items_string = new ArrayList<String>();
	DataBaseHandler db;
	String name, names_notes, group, plan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		Context ctx = this;

		db = new DataBaseHandler(DialogNew.this);

		_items = (ArrayList<Contact>) db.getAllContacts();
		for (int i = 0; i < _items.size(); i++) {
			_items_string.add(_items.get(i).get_name());
			names_notes = _items.get(i).get_notes();
			group = _items.get(i).get_name_group();
			plan = _items.get(i).get_name_plan();
		}

		final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
		alert.setTitle("Message");
		for (int i = 0; i < _items_string.size(); i++) {
			name = _items_string.get(i);
			alert.setMessage(name + ">>" + names_notes + ">>" + group + ">>"
					+ plan);
		}

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent in = new Intent(getApplicationContext(), Favorate.class);
				startActivity(in);
				finish();
			}
		});
		alert.create();
		alert.show();
	}
}
