package com.mditservices.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airpush.android.Airpush;
import com.mayank.pojo.ImageLoader;

public class Favorate extends Activity {

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
	private static final int ID_SOCIAL = 9;

	private static final int ID_SPORT = 10;
	private static final int ID_STOP = 10;

	int layoutResourceId;
	Context context;
	AppConfig config;
	DataBaseHandler db;
	Contact item = new Contact();
	ListAdapter adapter;
	ListView list_favorite;
	String id, name, state, desc, direction, url;
	ImageLoader imgImageLoader;
	ImageView favname, review, memory, img_group_search, img_plan_search;
	ImageButton img_setting;
	Button btn_map, btn_reset;
	private TextView tvDisplayDate;
	private DatePicker dpResult;
	private Button btnChangeDate;

	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 999;
	Button calender;

	AlertDialog alertDialog1;
	Airpush airpush;
	File imgFile, imgFileplan;
	EditText et;
	int textlength = 0;
	private ArrayList<String> array_sortnametest = new ArrayList<String>();
	private ArrayList<String> array_sortname = new ArrayList<String>();
	private ArrayList<String> array_sortnotes = new ArrayList<String>();
	private ArrayList<String> array_sortgroup = new ArrayList<String>();
	private ArrayList<String> array_sortplan = new ArrayList<String>();

	private ArrayList<String> array_sort_new = new ArrayList<String>();
	private ArrayList<String> array_sort_new_plan = new ArrayList<String>();

	ArrayList<String> searchNames = new ArrayList<String>();
	ArrayList<String> searchNamesnote = new ArrayList<String>();
	ArrayList<String> searchNamesgroup = new ArrayList<String>();
	ArrayList<String> searchNamesplan = new ArrayList<String>();

	ArrayList<String> searchNames_group = new ArrayList<String>();
	ArrayList<String> searchNames_plan = new ArrayList<String>();

	ArrayList<String> idarray = new ArrayList<String>();
	ArrayList<Contact> _items = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.review_list);

		config.viewdata = false;
		btn_reset = (Button) findViewById(R.id.btn_reset);
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent same = new Intent(getApplicationContext(),
						Favorate.class);
				startActivity(same);
				finish();
			}
		});
		btn_map = (Button) findViewById(R.id.btn_map);
		btn_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		et = (EditText) findViewById(R.id.edt_search);

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.call = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							// config.coffee = str;
							img_plan_search.setBackgroundDrawable(str);

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.drink = str;
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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.email = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.lunch = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;

							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.intro = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.meeting = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.research = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.social = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.sport = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.stop = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.client = str;

							System.out.println("check data >>> "
									+ actionItem.getTitle());

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.faith = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.family = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.friends = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.prospect = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.school = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.vendor = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.work = str;

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

		setCurrentDateOnView();
		img_group_search = (ImageView) findViewById(R.id.img_group_search);
		img_group_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mQuickAction.show(v);
			}
		});
		img_plan_search = (ImageView) findViewById(R.id.img_plan_search);
		img_plan_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mQuickActionplan.show(v);
			}
		});
		et = (EditText) findViewById(R.id.edt_search);
		et.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = et.getText().length();
				array_sortnametest.clear();

				for (int i = 0; i < searchNames.size(); i++) {
					if (textlength <= searchNames.get(i).length()) {
						if (et.getText()
								.toString()
								.equalsIgnoreCase(
										(String) searchNames.get(i)
												.subSequence(0, textlength))) {
							array_sortnametest.add(searchNames.get(i));
							array_sortname.add(searchNames.get(i));
							array_sortnotes.add(searchNamesnote.get(i));
							array_sortgroup.add(searchNamesgroup.get(i));
							array_sortplan.add(searchNamesplan.get(i));

						}
					}
					if (textlength <=0) {
						System.out.println("asdasdas asdasd asdasd  " );
						
					}
				}
				list_favorite.setAdapter(new MyListAdapter(Favorate.this,
						array_sortnametest, array_sortname, array_sortnotes,
						array_sortgroup, array_sortplan));
			}
		});

		_items.clear();
		config = new AppConfig(context);
		context = getApplicationContext();

		calender = (Button) findViewById(R.id.btn_calender);
		calender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		img_setting = (ImageButton) findViewById(R.id.img_setting);
		img_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent set = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(set);
				finish();
			}
		});
		favname = (ImageView) findViewById(R.id.fav_entername);
		memory = (ImageView) findViewById(R.id.fav_memory);
		favname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						EnterUserData.class);
				startActivity(inreview);
			}
		});
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inmemroy = new Intent(getApplicationContext(),
						Memory.class);
				startActivity(inmemroy);
			}
		});

		db = new DataBaseHandler(Favorate.this);

		_items = (ArrayList<Contact>) db.getAllContacts();
		list_favorite = (ListView) findViewById(R.id.list1);

		adapter = new ListAdapter(Favorate.this);
		list_favorite.setAdapter(adapter);

		// Toast.makeText(Favorate.this, "Total favorites :" + _items.size(),
		// Toast.LENGTH_LONG).show();

		for (int i = 0; i < _items.size(); i++) {
		}

	}

	class MyListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;
		ArrayList<String> _arraynametest;
		ArrayList<String> _arrayname;
		ArrayList<String> _arraynotes;
		ArrayList<String> _arraygroup;
		ArrayList<String> _arrayplan;

		public MyListAdapter(Context context, ArrayList<String> arraynametest,
				ArrayList<String> arrayname, ArrayList<String> arraynotes,
				ArrayList<String> arraygroup, ArrayList<String> arrayplan) {
			inflater = LayoutInflater.from(context);

			this._arraynametest = arraynametest;
			this._arrayname = arrayname;
			this._arraynotes = arraynotes;
			this._arraygroup = arraygroup;
			this._arrayplan = arrayplan;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _arraynametest.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.favorite_row, null);

				viewHolder = new ViewHolder();

				viewHolder.titlefirst = (TextView) convertView
						.findViewById(R.id.txt_favview);

				viewHolder.title1first = (TextView) convertView
						.findViewById(R.id.txt_desc);

				viewHolder.txtTitle1first = (TextView) convertView
						.findViewById(R.id.txt_search);
				viewHolder.txtTitle2first = (TextView) convertView
						.findViewById(R.id.txt_search_plan);

				viewHolder.imgIcon = (ImageView) convertView
						.findViewById(R.id.img_group_listraw);
				viewHolder.imgiconplan = (ImageView) convertView
						.findViewById(R.id.img_plan);

				viewHolder.btn = (Button) convertView
						.findViewById(R.id.btn_delete);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				// Log.i(">>name>>", ">>>>" + _items.get(position).name);

				viewHolder.titlefirst.setText(_arraynametest.get(position));
				viewHolder.title1first.setText(_arraynotes.get(position));

				System.out.println("position >>>> " + _arrayname.get(position));

				// viewHolder.titlefirst.setText(_arrayname.get(position));
				// viewHolder.title1first.setText(_items.get(position)._notes);

				viewHolder.txtTitle1first
						.setText(_items.get(position)._name_group);
				viewHolder.txtTitle2first
						.setText(_items.get(position)._name_plan);

				viewHolder.imgIcon.setImageBitmap(BitmapFactory
						.decodeFile(_arraygroup.get(position)));
				viewHolder.imgiconplan.setImageBitmap(BitmapFactory
						.decodeFile(_arrayplan.get(position)));

			

				/*
				 * viewHolder.imgIcon.setImageBitmap(BitmapFactory
				 * .decodeFile(_items.get(position)._file));
				 * viewHolder.imgiconplan.setImageBitmap(BitmapFactory
				 * .decodeFile(_items.get(position)._file_plan));
				 */

				viewHolder.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
								Favorate.this);

						// Setting Dialog Title
						alertDialog2.setTitle("Confirm Delete...");

						// Setting Dialog Message
						alertDialog2
								.setMessage("Are you sure you want delete this file?");

						// Setting Icon to Dialog
						alertDialog2.setIcon(R.drawable.delete);

						// Setting Positive "Yes" Btn
						alertDialog2.setPositiveButton("YES",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										int id = _items.get(position)._id;
										db.deleteContact(id);
										my_oncreate();
										db.close();
									}
								});
						// Setting Negative "NO" Btn
						alertDialog2.setNegativeButton("NO",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										dialog.cancel();
									}
								});

						// Showing Alert Dialog
						alertDialog2.show();

					}
				});

			} catch (Exception e) {
				// TODO: handle exception
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(getApplicationContext(),
							ViewData.class);
					Bundle bundle = new Bundle();

					config.bundledata = true;
					bundle.putString("Name", _items.get(position)._name);
					bundle.putString("Notes", _items.get(position)._notes);
					bundle.putString("Facial", _items.get(position)._facial);
					bundle.putString("Group", _items.get(position)._file);
					bundle.putString("Plan", _items.get(position)._file_plan);
					bundle.putString("Camera", _items.get(position)._camera);
					i.putExtras(bundle);
					startActivity(i);
					// finish();
				}

			});
			return convertView;
		}

		private class ViewHolder {
			TextView titlefirst, txtTitle1first, txtTitle2first, title1first;
			ImageView imgIcon, imgiconplan;
			TextView txtTitle, txtfacial, txtnotes;
			Button btn;
			integer inid;
			// Dynamic view
		}
	}

	class Mynewadpter extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;
		ArrayList<String> _arraynew;
		ArrayList<String> _arraynew_plan;
		ArrayList<String> _arraynew_group;
		ArrayList<String> _arraynew_name;
		ArrayList<String> _arraynew_note;

		public Mynewadpter(Context context, ArrayList<String> array,
				ArrayList<String> arraygroup, ArrayList<String> arrayplan,
				ArrayList<String> arrayname, ArrayList<String> arraynote) {
			inflater = LayoutInflater.from(context);

			this._arraynew = array;
			this._arraynew_plan = arrayplan;
			this._arraynew_group = arraygroup;
			this._arraynew_name = arrayname;
			this._arraynew_note = arraynote;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _arraynew.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.favorite_row, null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.txt_favview);

				viewHolder.title1 = (TextView) convertView
						.findViewById(R.id.txt_desc);

				viewHolder.txtTitle1 = (TextView) convertView
						.findViewById(R.id.txt_search);
				viewHolder.txtTitle2 = (TextView) convertView
						.findViewById(R.id.txt_search_plan);

				viewHolder.imgIcon = (ImageView) convertView
						.findViewById(R.id.img_group_listraw);
				viewHolder.imgiconplan = (ImageView) convertView
						.findViewById(R.id.img_plan);

				viewHolder.btn = (Button) convertView
						.findViewById(R.id.btn_delete);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				// Log.i(">>name>>", ">>>>" + _items.get(position).name);

				viewHolder.title.setText(_arraynew_name.get(position));
				viewHolder.title1.setText(_arraynew_note.get(position));

				viewHolder.txtTitle1.setText(_arraynew.get(position));
				viewHolder.txtTitle2.setText(_arraynew.get(position));

				viewHolder.txtTitle1.setText(_items.get(position)._name_group);
				viewHolder.txtTitle2.setText(_items.get(position)._name_plan);

				viewHolder.imgIcon.setImageBitmap(BitmapFactory
						.decodeFile(_arraynew_group.get(position)));

				viewHolder.imgiconplan.setImageBitmap(BitmapFactory
						.decodeFile(_arraynew_plan.get(position)));

				viewHolder.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
								Favorate.this);

						// Setting Dialog Title
						alertDialog2.setTitle("Confirm Delete...");

						// Setting Dialog Message
						alertDialog2
								.setMessage("Are you sure you want delete this file?");

						// Setting Icon to Dialog
						alertDialog2.setIcon(R.drawable.delete);

						// Setting Positive "Yes" Btn
						alertDialog2.setPositiveButton("YES",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										int id = _items.get(position)._id;
										db.deleteContact(id);
										my_oncreate();
										db.close();
									}
								});
						// Setting Negative "NO" Btn
						alertDialog2.setNegativeButton("NO",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										dialog.cancel();
									}
								});

						// Showing Alert Dialog
						alertDialog2.show();
					}
				});

			} catch (Exception e) {
				// TODO: handle exception
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(getApplicationContext(),
							ViewData.class);
					Bundle bundle = new Bundle();

					config.bundledata = true;
					bundle.putString("Name", _items.get(position)._name);
					bundle.putString("Notes", _items.get(position)._notes);
					bundle.putString("Facial", _items.get(position)._facial);
					bundle.putString("Group", _items.get(position)._file);
					bundle.putString("Plan", _items.get(position)._file_plan);
					bundle.putString("Camera", _items.get(position)._camera);
					i.putExtras(bundle);
					startActivity(i);
					// finish();
				}

			});

			return convertView;

		}

		private class ViewHolder {
			TextView title, txtTitle1, txtTitle2, title1;
			ImageView imgIcon, imgiconplan;
			TextView txtTitle, txtfacial, txtnotes;
			Button btn;
			integer inid;
			// Dynamic view
		}
	}

	class Mynewadpterplan extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;

		ArrayList<String> _arraynew1;
		ArrayList<String> _arraynew_plan1;
		ArrayList<String> _arraynew_group1;
		ArrayList<String> _arraynew_name1;
		ArrayList<String> _arraynew_note1;

		public Mynewadpterplan(Context context, ArrayList<String> array1,
				ArrayList<String> arraygroup1, ArrayList<String> arrayplan1,
				ArrayList<String> arrayname1, ArrayList<String> arraynote1) {
			inflater = LayoutInflater.from(context);

			this._arraynew1 = array1;
			this._arraynew_plan1 = arrayplan1;
			this._arraynew_group1 = arraygroup1;
			this._arraynew_name1 = arrayname1;
			this._arraynew_note1 = arraynote1;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _arraynew1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.favorite_row, null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.txt_favview);

				viewHolder.title1 = (TextView) convertView
						.findViewById(R.id.txt_desc);

				viewHolder.txtTitle1 = (TextView) convertView
						.findViewById(R.id.txt_search);
				viewHolder.txtTitle2 = (TextView) convertView
						.findViewById(R.id.txt_search_plan);

				viewHolder.imgIcon = (ImageView) convertView
						.findViewById(R.id.img_group_listraw);
				viewHolder.imgiconplan = (ImageView) convertView
						.findViewById(R.id.img_plan);

				viewHolder.btn = (Button) convertView
						.findViewById(R.id.btn_delete);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				// Log.i(">>name>>", ">>>>" + _items.get(position).name);

				viewHolder.title.setText(_arraynew_name1.get(position));
				viewHolder.title1.setText(_arraynew_note1.get(position));

				viewHolder.txtTitle1.setText(_arraynew1.get(position));
				viewHolder.txtTitle2.setText(_arraynew1.get(position));

				viewHolder.txtTitle1.setText(_items.get(position)._name_group);
				viewHolder.txtTitle2.setText(_items.get(position)._name_plan);

				viewHolder.imgIcon.setImageBitmap(BitmapFactory
						.decodeFile(_arraynew_group1.get(position)));

				viewHolder.imgiconplan.setImageBitmap(BitmapFactory
						.decodeFile(_arraynew_plan1.get(position)));

				viewHolder.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
								Favorate.this);

						// Setting Dialog Title
						alertDialog2.setTitle("Confirm Delete...");

						// Setting Dialog Message
						alertDialog2
								.setMessage("Are you sure you want delete this file?");

						// Setting Icon to Dialog
						alertDialog2.setIcon(R.drawable.delete);

						// Setting Positive "Yes" Btn
						alertDialog2.setPositiveButton("YES",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										int id = _items.get(position)._id;
										db.deleteContact(id);
										my_oncreate();
										db.close();
									}
								});
						// Setting Negative "NO" Btn
						alertDialog2.setNegativeButton("NO",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										dialog.cancel();
									}
								});

						// Showing Alert Dialog
						alertDialog2.show();
					}
				});

			} catch (Exception e) {
				// TODO: handle exception
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(getApplicationContext(),
							ViewData.class);
					Bundle bundle = new Bundle();

					config.bundledata = true;
					bundle.putString("Name", _items.get(position)._name);
					bundle.putString("Notes", _items.get(position)._notes);
					bundle.putString("Facial", _items.get(position)._facial);
					bundle.putString("Group", _items.get(position)._file);
					bundle.putString("Plan", _items.get(position)._file_plan);
					bundle.putString("Camera", _items.get(position)._camera);
					i.putExtras(bundle);
					startActivity(i);
					// finish();
				}

			});

			return convertView;

		}

		private class ViewHolder {
			TextView title, txtTitle1, txtTitle2, title1;
			ImageView imgIcon, imgiconplan;
			TextView txtTitle, txtfacial, txtnotes;
			Button btn;
			integer inid;
			// Dynamic view
		}
	}

	class ListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ImageHolder viewHolder;

		public ListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public ListAdapter(Favorate favorate, ArrayList<String> array_sort) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {

				convertView = inflater.inflate(R.layout.favorite_row, null);

				viewHolder = new ImageHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.txt_favview);

				viewHolder.title1 = (TextView) convertView
						.findViewById(R.id.txt_desc);

				viewHolder.txtTitle1 = (TextView) convertView
						.findViewById(R.id.txt_search);
				viewHolder.txtTitle2 = (TextView) convertView
						.findViewById(R.id.txt_search_plan);

				viewHolder.imgIcon = (ImageView) convertView
						.findViewById(R.id.img_group_listraw);
				viewHolder.imgiconplan = (ImageView) convertView
						.findViewById(R.id.img_plan);

				viewHolder.btn = (Button) convertView
						.findViewById(R.id.btn_delete);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ImageHolder) convertView.getTag();
			}
			try {

				viewHolder.title.setText(_items.get(position)._name);
				viewHolder.title1.setText(_items.get(position)._notes);

				// viewHolder.txtTitle1.setText(_items.get(position)._name_group);
				// viewHolder.txtTitle2.setText(_items.get(position)._name_plan);

				viewHolder.imgIcon.setImageBitmap(BitmapFactory
						.decodeFile(_items.get(position)._file));

				searchNames_group.add(_items.get(position)._name_group);
				searchNames_plan.add(_items.get(position)._name_plan);

				// Bitmap myBitmapplan = BitmapFactory.decodeFile(_items
				// .get(position)._file_plan);
				viewHolder.imgiconplan.setImageBitmap(BitmapFactory
						.decodeFile(_items.get(position)._file_plan));

				searchNames.add(_items.get(position)._name);
				searchNamesnote.add(_items.get(position)._notes);
				searchNamesgroup.add(_items.get(position)._file);
				searchNamesplan.add(_items.get(position)._file_plan);

				viewHolder.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
								Favorate.this);

						// Setting Dialog Title
						alertDialog2.setTitle("Confirm Delete...");

						// Setting Dialog Message
						alertDialog2
								.setMessage("Are you sure you want delete this file?");

						// Setting Icon to Dialog
						alertDialog2.setIcon(R.drawable.delete);

						// Setting Positive "Yes" Btn
						alertDialog2.setPositiveButton("YES",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										int id = _items.get(position)._id;
										db.deleteContact(id);
										my_oncreate();
										db.close();
									}
								});
						// Setting Negative "NO" Btn
						alertDialog2.setNegativeButton("NO",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Write your code here to execute after
										// dialog

										dialog.cancel();
									}
								});

						// Showing Alert Dialog
						alertDialog2.show();

						// Showing Alert Message
						/*
						 * alertDialog1.show(); int id =
						 * _items.get(position)._id; db.deleteContact(id);
						 * my_oncreate(); db.close();
						 */
					}
				});

			} catch (Exception e) {
				// TODO: handle exception
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(getApplicationContext(),
							ViewData.class);
					Bundle bundle = new Bundle();

					config.bundledata = true;
					bundle.putString("Name", _items.get(position)._name);
					bundle.putString("Notes", _items.get(position)._notes);
					bundle.putString("Facial", _items.get(position)._facial);
					bundle.putString("Group", _items.get(position)._file);
					bundle.putString("Plan", _items.get(position)._file_plan);
					bundle.putString("Camera", _items.get(position)._camera);

					i.putExtras(bundle);
					startActivity(i);
					// finish();
				}

			});

			return convertView;
		}

		private class ImageHolder {
			TextView title, txtTitle1, txtTitle2, title1;
			ImageView imgIcon, imgiconplan;
			TextView txtTitle, txtfacial, txtnotes;
			Button btn;
			integer inid;
			// Dynamic view

		}
		/*
		 * static class ImageHolder { ImageView imgIcon,imgiconplan; TextView
		 * txtTitle,txtfacial,txtnotes; }
		 */

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));
			/*
			 * db = new DataBaseHandler(Favorate.this);
			 * 
			 * String strdate = tvDisplayDate.getText().toString();
			 * 
			 * AppConfig.str_date = strdate;
			 * 
			 * System.out.println("date trace >>>> " + AppConfig.str_date);
			 * 
			 * item._date = AppConfig.str_date; db.addContact(item); db.close();
			 */
			// set selected date into datepicker also
			// dpResult.init(year, month, day, null);

		}
	};

	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.txt_date);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

		/*
		 * db = new DataBaseHandler(Favorate.this);
		 * 
		 * String strdate = tvDisplayDate.getText().toString();
		 * 
		 * AppConfig.str_date = strdate;
		 * 
		 * System.out.println("date trace >>>> " + AppConfig.str_date);
		 * 
		 * item._date = AppConfig.str_date;
		 */
		// set current date into datepicker
		// dpResult.init(year, month, day, null);

	}

	public void my_oncreate() {
		// TODO Auto-generated method stub

		setContentView(R.layout.review_list);

		config.viewdata = false;
		btn_reset = (Button) findViewById(R.id.btn_reset);
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent same = new Intent(getApplicationContext(),
						Favorate.class);
				startActivity(same);
				finish();
			}
		});
		btn_map = (Button) findViewById(R.id.btn_map);
		btn_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		et = (EditText) findViewById(R.id.edt_search);

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.call = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							// config.coffee = str;
							img_plan_search.setBackgroundDrawable(str);

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.drink = str;
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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.email = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.lunch = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;

							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.intro = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.meeting = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.research = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.social = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();
							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.sport = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataPlan();

							Drawable str = actionItem.getIcon();
							img_plan_search.setBackgroundDrawable(str);
							config.stop = str;

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

							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.client = str;

							System.out.println("check data >>> "
									+ actionItem.getTitle());

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.faith = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.family = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.friends = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.prospect = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.school = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();

							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.vendor = str;

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
							String call = actionItem.getTitle();
							config.str_client = call;
							SearchDataGroup();
							Drawable str = actionItem.getIcon();
							img_group_search.setBackgroundDrawable(str);
							config.work = str;

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

		setCurrentDateOnView();
		img_group_search = (ImageView) findViewById(R.id.img_group_search);
		img_group_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mQuickAction.show(v);
			}
		});
		img_plan_search = (ImageView) findViewById(R.id.img_plan_search);
		img_plan_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mQuickActionplan.show(v);
			}
		});
		et = (EditText) findViewById(R.id.edt_search);
		et.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = et.getText().length();
				array_sortnametest.clear();

				for (int i = 0; i < searchNames.size(); i++) {
					if (textlength <= searchNames.get(i).length()) {
						if (et.getText()
								.toString()
								.equalsIgnoreCase(
										(String) searchNames.get(i)
												.subSequence(0, textlength))) {
							array_sortnametest.add(searchNames.get(i));
							array_sortname.add(searchNames.get(i));
							array_sortnotes.add(searchNamesnote.get(i));
							array_sortgroup.add(searchNamesgroup.get(i));
							array_sortplan.add(searchNamesplan.get(i));

						}
					}
					if (textlength <=0) {
						System.out.println("asdasdas asdasd asdasd  " );
						my_oncreate();
					}
				}
				list_favorite.setAdapter(new MyListAdapter(Favorate.this,
						array_sortnametest, array_sortname, array_sortnotes,
						array_sortgroup, array_sortplan));

			}
		});

		_items.clear();
		config = new AppConfig(context);
		context = getApplicationContext();

		calender = (Button) findViewById(R.id.btn_calender);
		calender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		img_setting = (ImageButton) findViewById(R.id.img_setting);
		img_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent set = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(set);
				finish();
			}
		});
		favname = (ImageView) findViewById(R.id.fav_entername);
		memory = (ImageView) findViewById(R.id.fav_memory);
		favname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						EnterUserData.class);
				startActivity(inreview);
			}
		});
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inmemroy = new Intent(getApplicationContext(),
						Memory.class);
				startActivity(inmemroy);
			}
		});

		db = new DataBaseHandler(Favorate.this);

		_items = (ArrayList<Contact>) db.getAllContacts();
		list_favorite = (ListView) findViewById(R.id.list1);

		adapter = new ListAdapter(Favorate.this);
		list_favorite.setAdapter(adapter);

		// Toast.makeText(Favorate.this, "Total favorites :" + _items.size(),
		// Toast.LENGTH_LONG).show();

		for (int i = 0; i < _items.size(); i++) {
		}

	}

	public void SearchDataGroup() {
		array_sort_new.clear();

		et.setText(AppConfig.str_client);

		textlength = et.getText().length();

		for (int i = 0; i < searchNames_group.size(); i++) {
			if (textlength <= searchNames_group.get(i).length()) {
				if (et.getText()
						.toString()
						.equalsIgnoreCase(
								(String) searchNames_group.get(i).subSequence(
										0, textlength))) {
					array_sort_new.add(searchNames_group.get(i));
					array_sortname.add(searchNames.get(i));
					array_sortnotes.add(searchNamesnote.get(i));
					array_sortgroup.add(searchNamesgroup.get(i));
					array_sortplan.add(searchNamesplan.get(i));
				}
			}
			/*
			 * if (textlength <= 0) {
			 * System.out.println("my cre sdasdjkahsdh ajoh"); my_oncreate(); }
			 */
		}
		list_favorite.setAdapter(new Mynewadpter(Favorate.this, array_sort_new,
				array_sortgroup, array_sortplan, array_sortname,
				array_sortnotes));

		// =================================================f
	}

	public void SearchDataPlan() {
		array_sort_new.clear();

		et.setText(AppConfig.str_client);

		textlength = et.getText().length();

		for (int i = 0; i < searchNames_plan.size(); i++) {
			if (textlength <= searchNames_plan.get(i).length()) {
				if (et.getText()
						.toString()
						.equalsIgnoreCase(
								(String) searchNames_plan.get(i).subSequence(0,
										textlength))) {
					array_sort_new.add(searchNames_plan.get(i));
					array_sortname.add(searchNames.get(i));
					array_sortnotes.add(searchNamesnote.get(i));
					array_sortgroup.add(searchNamesgroup.get(i));
					array_sortplan.add(searchNamesplan.get(i));
					System.out.println("Array sotr data >>>> "
							+ array_sort_new_plan);
				}
			}
			/*
			 * if (textlength <= 0) {
			 * System.out.println("my cre sdasdjkahsdh ajoh"); my_oncreate(); }
			 */
		}
		list_favorite.setAdapter(new Mynewadpter(Favorate.this,
				array_sort_new, array_sortgroup, array_sortplan,
				array_sortname, array_sortnotes));

		// =================================================
	}

}
