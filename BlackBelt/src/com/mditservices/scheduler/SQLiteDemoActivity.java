package com.mditservices.scheduler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SQLiteDemoActivity extends Activity {
	ArrayList<Contact> imageArry = new ArrayList<Contact>();

	ContactImageAdapter adapter;
	Bitmap image, image1;
	Button btn_calender;
	static final int DATE_DIALOG_ID = 999;
	TextView txtdate;
	private int year;
	private int month;
	private int day;
	ImageButton imageButton1;
	AppConfig config;
	Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_list);

		imageArry.clear();

		btn_calender = (Button) findViewById(R.id.btn_calender);
		btn_calender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});

		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent isIntent = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(isIntent);
			}
		});

		config = new AppConfig(context);
		context = getApplicationContext();
		DataBaseHandler db = new DataBaseHandler(this);
		// get image from drawable
			image = BitmapFactory.decodeResource(getResources(),
					R.drawable.client);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte imageInByte[] = stream.toByteArray();
			Log.d("Insert: ", "Inserting ..");
			db.addContact(new Contact(config.name,imageInByte, imageInByte));

		// convert bitmap to byte

		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts

		// db.addContact(new Contact("FaceBook1", imageInByte1, imageInByte));
		// display main List view bcard and contact name

		// Reading all contacts from database
		List<Contact> contacts = db.getAllContacts();
		for (Contact cn : contacts) {
			String log = "ID:" + cn.getID() + " Name: " + cn.getName()
					+ " ,Image: " + cn.getImage();

			// Writing Contacts to log
			Log.d("Result: ", log);
			// add contacts data in arrayList
			imageArry.add(cn);

		}
		adapter = new ContactImageAdapter(this, R.layout.favorite_row,
				imageArry);
		ListView dataList = (ListView) findViewById(R.id.list1);
		dataList.setAdapter(adapter);
		setCurrentDateOnView();
	}

	public void setCurrentDateOnView() {

		txtdate = (TextView) findViewById(R.id.txt_date);
		// dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		txtdate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

		// set current date into datepicker
		// dpResult.init(year, month, day, null);

	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			txtdate.setText(new StringBuilder().append(month + 1).append("-")
					.append(day).append("-").append(year).append(" "));

			// set selected date into datepicker also
			// dpResult.init(year, month, day, null);

		}
	};
}
