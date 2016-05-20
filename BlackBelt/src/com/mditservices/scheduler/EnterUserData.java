package com.mditservices.scheduler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.airpush.android.Airpush;

public class EnterUserData extends Activity {
	ImageButton back, map, save;
	ImageView group, plan, imgentername, imgreview, imgmemory;
	String text;
	Bitmap image;
	String pos;
	DataBaseHandler db;
	Button nextscreen, cancelabjust, btn_camera, btn_gallery;
	String str_name, str_facial, str_usernotes, userdata, reqData,
			str_fileplan, str_cameranew, emailvalidate, passwordaboutuser,
			errormessages;
	Drawable str_file;
	EditText username, outstanding, notes;

	Dialog dialog;
	GridView gridView;
	ImageView img_dialog, img_savedialog, img_camera;

	SlidingDrawer slidingDrawerabjust;
	ImageView addimg, imgViewFlag;
	Contact item = new Contact();
	ByteArrayOutputStream bs;
	private Bitmap imagebm;
	private String selectedImagePath;
	AppConfig config;
	Context context;
	private ArrayList<String> textfield;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 3;
	Bitmap img_call, bm_;
	ByteArrayOutputStream stream;
	// private ArrayList<Integer> group_grid;
	ProgressDialog pDialog;

	Airpush airpush;
	
	private static final int ID_CLIENT = 1;
	private static final int ID_FAITH = 2;
	private static final int ID_FAMILY = 3;

	private static final int ID_FRIENDS = 4;
	private static final int ID_PROSPECT = 5;
	private static final int ID_SCHOOL = 6;

	private static final int ID_VENDOR = 7;
	private static final int ID_WORK = 8;

	// plan text id
	private static final int ID_CALL = 1;
	private static final int ID_COFFEE = 2;
	private static final int ID_DRINKS = 3;

	private static final int ID_EMAIL = 4;
	private static final int ID_LUNCH = 5;
	private static final int ID_INTRO = 6;

	private static final int ID_MEETING = 7;
	private static final int ID_RESEARCH = 8;
	private static final int ID_SOCIAL = 7;

	private static final int ID_SPORT = 8;
	private static final int ID_STOP = 7;

	TextView txt_map;
	String s1, s2, s3;

	ImageView review, memory, img_group, img_plan;

	EditText edt_name, edt_facial, edt_notes;
	String str_name1, str_facial1, str_notes, str_group, str_plan, str_camera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enterdata);

	/*	AndroidSDKProvider.initSDK(this);
				if(airpush==null)
					airpush=new Airpush(getApplicationContext(), this);

				airpush.startDialogAd();
				airpush.startAppWall();
				
				airpush.startLandingPageAd();
				airpush.showRichMediaInterstitialAd();*/

		
		config = new AppConfig(context);
		context = getApplicationContext();
		if (config.maptext == false) {
			txt_map = (TextView) findViewById(R.id.txt_map);
			txt_map.setText(AppConfig.mapaddress);
		}

		imgentername = (ImageView) findViewById(R.id.img_entername);
		imgreview = (ImageView) findViewById(R.id.img_reviewname);
		imgreview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),Favorate.class);
				startActivity(inreview);
				finish();
			}
		});
		imgmemory = (ImageView) findViewById(R.id.img_memory);
		imgmemory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inmemroy = new Intent(getApplicationContext(),
						Memory.class);
				startActivity(inmemroy);
				
				airpush.showRichMediaInterstitialAd();
				finish();
			}
		});

		username = (EditText) findViewById(R.id.edt_typename);
		if (AppConfig.viewdata == true) {
			str_name1 = getIntent().getStringExtra("Name");
			username.setText(str_name1);
		}
		outstanding = (EditText) findViewById(R.id.edt_outstanding);
		if (AppConfig.viewdata == true) {
			str_facial1 = getIntent().getStringExtra("Facial");
			outstanding.setText(str_facial1);
		}
		notes = (EditText) findViewById(R.id.edt_notes);
		if (AppConfig.viewdata == true) {
			str_notes = getIntent().getStringExtra("Notes");
			notes.setText(str_notes);
		}
		save = (ImageButton) findViewById(R.id.imgbtn_save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_name = username.getText().toString();
				config.name = str_name;
				str_facial = outstanding.getText().toString();
				str_usernotes = notes.getText().toString();
				errormessages = "";
				if (AppConfig.filesdcard == null || AppConfig.file_plan == null
						|| AppConfig.camera == null) {
					Toast.makeText(getApplicationContext(),
							"Fields are empty..please fill it",
							Toast.LENGTH_LONG).show();
				} else {

					validateControls();
				}
			}
		});

		img_savedialog = (ImageView) findViewById(R.id.img_savename);
		img_savedialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				img_savedialog.setVisibility(View.GONE);
			}
		});
		img_camera = (ImageView) findViewById(R.id.imgbtn_camera);
		if (AppConfig.viewdata == true) {
			str_camera = getIntent().getStringExtra("Camera");
			byte[] encodeByte = Base64.decode(str_camera, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			img_camera.setImageBitmap(bitmap);
		}
		img_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slidingDrawerabjust = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
				slidingDrawerabjust.animateOpen();
				slidingDrawerabjust.bringToFront();
			}
		});

		// this.img_camera = (ImageView) this.findViewById(R.id.image_camera);
		btn_camera = (Button) this.findViewById(R.id.btn_camera);
		btn_gallery = (Button) this.findViewById(R.id.btn_gallery);

		btn_gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");

				startActivityForResult(
						Intent.createChooser(intent, "Complete action using"),
						PICK_FROM_FILE);
			}
		});
		btn_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				try {
					// intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		cancelabjust = (Button) findViewById(R.id.btn_cancel);
		cancelabjust.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slidingDrawerabjust.close();
			}
		});

		map = (ImageButton) findViewById(R.id.imgbtn_map);
		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// map("com.google.android.apps.maps");
				/*Intent map = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(map);
				finish();*/
			}

			// TODO Auto-generated method stub

		});
		back = (ImageButton) findViewById(R.id.img_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		img_dialog = (ImageView) findViewById(R.id.img_dialog);
		img_dialog.setVisibility(View.VISIBLE);
		img_dialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				img_dialog.setVisibility(View.GONE);
				img_savedialog.setVisibility(View.VISIBLE);
			}
		});
		final QuickAction mQuickAction = new QuickAction(this);
		ActionItem client = new ActionItem(ID_CLIENT, "Client", getResources()
				.getDrawable(R.drawable.client));
		ActionItem faithgroup = new ActionItem(ID_FAITH, "Faith Group",
				getResources().getDrawable(R.drawable.faith_group));
		ActionItem family = new ActionItem(ID_FAMILY, "Family", getResources()
				.getDrawable(R.drawable.family));

		ActionItem friends = new ActionItem(ID_FRIENDS, "Friends",
				getResources().getDrawable(R.drawable.friends));
		ActionItem prospect = new ActionItem(ID_PROSPECT, "Prospect",
				getResources().getDrawable(R.drawable.prospect));
		ActionItem school = new ActionItem(ID_SCHOOL, "School", getResources()
				.getDrawable(R.drawable.school));

		ActionItem vendor = new ActionItem(ID_VENDOR, "Vendor", getResources()
				.getDrawable(R.drawable.vendor));
		ActionItem work = new ActionItem(ID_WORK, "Work", getResources()
				.getDrawable(R.drawable.work));

		work.setSticky(true);

		// plan action item

		final QuickAction mQuickActionplan = new QuickAction(this);

		ActionItem call = new ActionItem(ID_CALL, "Call", getResources()
				.getDrawable(R.drawable.call_text));
		ActionItem coffee = new ActionItem(ID_COFFEE, "Coffee", getResources()
				.getDrawable(R.drawable.coffee));
		ActionItem drinks = new ActionItem(ID_DRINKS, "Drinks", getResources()
				.getDrawable(R.drawable.drinks));

		ActionItem email = new ActionItem(ID_EMAIL, "Email", getResources()
				.getDrawable(R.drawable.email));
		ActionItem lunch = new ActionItem(ID_LUNCH, "Lunch", getResources()
				.getDrawable(R.drawable.lunch_dinner));
		ActionItem intro = new ActionItem(ID_INTRO, "Intro", getResources()
				.getDrawable(R.drawable.make_introductions));

		ActionItem meeting = new ActionItem(ID_MEETING, "Meeting",
				getResources().getDrawable(R.drawable.meeting));
		ActionItem research = new ActionItem(ID_RESEARCH, "Research",
				getResources().getDrawable(R.drawable.resaerch));
		ActionItem social = new ActionItem(ID_SOCIAL, "Social", getResources()
				.getDrawable(R.drawable.social_media));

		ActionItem sport = new ActionItem(ID_SPORT, "Sport", getResources()
				.getDrawable(R.drawable.sporting_event));
		ActionItem stop = new ActionItem(ID_STOP, "Stop", getResources()
				.getDrawable(R.drawable.stop_by));

		// plan action item

		mQuickActionplan.addActionItem(call);
		mQuickActionplan.addActionItem(coffee);
		mQuickActionplan.addActionItem(drinks);

		mQuickActionplan.addActionItem(email);
		mQuickActionplan.addActionItem(lunch);
		mQuickActionplan.addActionItem(intro);

		mQuickActionplan.addActionItem(meeting);
		mQuickActionplan.addActionItem(research);
		mQuickActionplan.addActionItem(social);

		mQuickActionplan.addActionItem(sport);
		mQuickActionplan.addActionItem(stop);

		mQuickActionplan
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						if (actionId == ID_CALL) {
							/*
							 * img_call = BitmapFactory.decodeResource(
							 * getResources(), R.drawable.call_text);
							 * 
							 * stream = new ByteArrayOutputStream();
							 * img_call.compress(Bitmap.CompressFormat.JPEG,
							 * 100, stream); byte imageInByte[] =
							 * stream.toByteArray();
							 */
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.call = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.call_text);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"call.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// config.image = imageInByte;

						}
						if (actionId == ID_COFFEE) {
							Drawable str = actionItem.getIcon();

							config.name_on_plan = actionItem.getTitle();
							// config.coffee = str;
							plan.setBackgroundDrawable(str);

							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.coffee);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"coffee.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_DRINKS) {

							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.name_on_plan = actionItem.getTitle();
							// config.drink = str;
							// download drawable to sdcard

							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.drinks);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"drink.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_EMAIL) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.email = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.email);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"email.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_LUNCH) {

							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.lunch = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.lunch_dinner);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"lunch.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_INTRO) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.intro = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(),
									R.drawable.make_introductions);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"intro.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_MEETING) {

							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.meeting = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.meeting);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"meeting.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_RESEARCH) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.research = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.resaerch);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"research.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_SOCIAL) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.social = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.social_media);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"social.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_SPORT) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.sport = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.sporting_event);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"sport.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_STOP) {
							Drawable str = actionItem.getIcon();
							plan.setBackgroundDrawable(str);
							config.stop = str;
							config.name_on_plan = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.stop_by);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"stop.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.file_plan = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// slidingDrawerabjust.open();
						}
					}
				});

		mQuickActionplan
				.setOnDismissListener(new QuickAction.OnDismissListener() {
					@Override
					public void onDismiss() {
						Toast.makeText(getApplicationContext(),
								"Ups..dismissed", Toast.LENGTH_SHORT).show();
					}
				});
		// Group action item

		mQuickAction.addActionItem(client);
		mQuickAction.addActionItem(faithgroup);
		mQuickAction.addActionItem(family);

		mQuickAction.addActionItem(friends);
		mQuickAction.addActionItem(prospect);
		mQuickAction.addActionItem(school);

		mQuickAction.addActionItem(vendor);
		mQuickAction.addActionItem(work);

		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						if (actionId == ID_CLIENT) {

							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.client = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.client);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"client.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_FAITH) {
							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.faith = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.faith_group);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"faith.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_FAMILY) {

							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.family = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.family);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"family.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_FRIENDS) {
							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.friends = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.friends);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"friends.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_PROSPECT) {

							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.prospect = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.prospect);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"prospect.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (actionId == ID_SCHOOL) {
							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.school = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.school);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"school.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_VENDOR) {

							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.vendor = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.vendor);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"vendor.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (actionId == ID_WORK) {

							Drawable str = actionItem.getIcon();
							group.setBackgroundDrawable(str);
							config.work = str;
							config.name_on_group = actionItem.getTitle();
							Bitmap bm = BitmapFactory.decodeResource(
									getResources(), R.drawable.work);
							String extStorageDirectory = Environment
									.getExternalStorageDirectory().toString();
							File file = new File(extStorageDirectory,
									"work.PNG");

							OutputStream outStream;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(Bitmap.CompressFormat.PNG, 100,
										outStream);

								config.filesdcard = file.toString();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				});

		mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {
				Toast.makeText(getApplicationContext(), "Ups..dismissed",
						Toast.LENGTH_SHORT).show();
			}
		});
		group = (ImageView) findViewById(R.id.imgbnt_user);
		if (AppConfig.viewdata == true) {
			str_group = getIntent().getStringExtra("Group");
			group.setImageBitmap(BitmapFactory.decodeFile(str_group));
		}
		group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mQuickAction.show(v);
			}
		});
		/*
		 * if (AppConfig.groupgrid == true) {
		 * group.setImageResource(AppConfig.group_path); config.groupgrid =
		 * false; }
		 */

		plan = (ImageView) findViewById(R.id.imgbtn_plan);
		if (AppConfig.viewdata == true) {
			str_plan = getIntent().getStringExtra("Plan");
			plan.setImageBitmap(BitmapFactory.decodeFile(str_plan));
		}
		plan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mQuickActionplan.show(v);
			}
		});
		if (AppConfig.plangrid == true) {
			plan.setImageResource(AppConfig.plan_path);
			config.plangrid = false;
		}
		
	}

	public void validateControls() {
		if (str_name.equals("") == true || str_facial.equals("") == true
				|| str_usernotes.equals("") == true) {
			passwordaboutuser = "Fields are empty..please fill it";
			errormessages = passwordaboutuser;
		} else {

		}
		if (errormessages.compareTo("") != 0) {
			Toast.makeText(getApplicationContext(), errormessages,
					Toast.LENGTH_LONG).show();
		} else {
			new performBackgroundTask().execute();
		}
	}

	public class performBackgroundTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog Dialog = new ProgressDialog(EnterUserData.this);

		protected void onPreExecute() {
			Dialog.setMessage("Please Wait...");
			Dialog.show();
		}

		protected void onPostExecute(Void unused) {
			try {
				if (Dialog.isShowing()) {

					Dialog.dismiss();
				}
			} catch (Exception e) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			SaveUserData();
			return null;
		}

		private void SaveUserData() {

			db = new DataBaseHandler(EnterUserData.this);

			item._name = str_name;
			item._facial = str_facial;
			item._notes = str_usernotes;

			item._file = config.filesdcard;
			item._file_plan = config.file_plan;

			item._name_group = config.name_on_group;
			item._name_plan = config.name_on_plan;

			item._camera = config.camera;

			config.viewdata = false;
			// item._gallery = config.gallery;

			// item._imagegroup = config.image;
			db.addContact(item);
			db.close();

			Intent save = new Intent(getApplicationContext(), Favorate.class);
			startActivity(save);
			finish();

		}
	}

	private void ConvertToEncoding() {
		// TODO Auto-generated method stub

		try {
			str_name = URLEncoder.encode(str_name, "utf-8");
			str_facial = URLEncoder.encode(str_facial, "utf-8");
			str_usernotes = URLEncoder.encode(str_usernotes, "utf-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:

			Bitmap photo = (Bitmap) data.getExtras().get("data");

			bs = new ByteArrayOutputStream();

			byte[] bsto = bs.toByteArray();
			photo.compress(Bitmap.CompressFormat.PNG, 100, bs);

			Drawable dr = new BitmapDrawable(photo);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] b = baos.toByteArray();
			String camera = Base64.encodeToString(b, Base64.DEFAULT);

			config.camera = camera;

			img_camera.setImageDrawable(dr);

			slidingDrawerabjust.close();
			break;

		case PICK_FROM_FILE:

			// config.test_bool2 = true;

			Uri targetUri = data.getData();

			InputStream is = null;
			try {
				is = getContentResolver().openInputStream(targetUri);
				Bitmap bitmapgallery = BitmapFactory.decodeStream(is);

				Drawable dr_gallery = new BitmapDrawable(bitmapgallery);

				ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
				bitmapgallery.compress(Bitmap.CompressFormat.PNG, 100, baos1);
				byte[] b1 = baos1.toByteArray();
				String gallery = Base64.encodeToString(b1, Base64.DEFAULT);

				config.camera = gallery;

				img_camera.setImageDrawable(dr_gallery);
				slidingDrawerabjust.close();
				try {
					is.close();
					// data =new byte[is.available()];
					// is.read(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
	}

	protected boolean map(String packageName) {
		Intent mIntent = getPackageManager().getLaunchIntentForPackage(
				packageName);
		if (mIntent != null) {

			System.out.println("success");
			startActivity(mIntent);
			return true;
		} else {
			System.out.println("success false");
			String url = "https://play.google.com/store/apps/details?id=com.google.android.apps.maps";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
			return false;
		}
	}

}
