package com.mditservices.scheduler;
/*package com.webinfoways.blackbelt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mayank.jsonparsar.JSONParser;
import com.mayank.pojo.ArticlePojo;

@SuppressWarnings("deprecation")
public class ReviewScreen extends ActivityGroup {

	EditText search;
	int textlength = 0;
	private ArrayList<String> array_sort = new ArrayList<String>();
	private ArrayList<String> array_sortname = new ArrayList<String>();
	ArrayList<String> searchNames = new ArrayList<String>();
	ListView list;
	ListAdapter adapter;
	private ProgressDialog pDialog;
	
	private static final String TAG_NAME = "Name";
	private static final String TAG_ID = "Id";
	private static final String TAG_ARTICLE = "data";
	
	String str_search;
	JSONArray data = null;
	
	String u_mid;
	String u_id;
	
	List<String> listtitle = new ArrayList<String>();
	List<String> listdetail = new ArrayList<String>();
	
	public ArrayList<ArticlePojo> _userInfo = new ArrayList<ArticlePojo>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.review_list);

		search = (EditText) findViewById(R.id.edt_search);

		str_search= search.getText().toString();
		search.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = search.getText().length();
				array_sort.clear();

				for (int i = 0; i < searchNames.size(); i++) {
					if (textlength <= searchNames.get(i).length()) {
						if (search
								.getText()
								.toString()
								.equalsIgnoreCase(
										(String) searchNames.get(i)
												.subSequence(0, textlength))) {
							array_sort.add(searchNames.get(i));
							// array_sortname.add(searchcity.get(i));

						}
					}
				}
				// list.setAdapter(new ArrayAdapter<String>(ListItemName.this,
				// android.R.layout.simple_list_item_1, array_sort));

				list.setAdapter(new MyListAdapter(ReviewScreen.this,
						array_sort, array_sortname));

			}
		});
		new task().execute();
	}
	class task extends AsyncTask<String, String, Void> {
		int success;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(getParent(), "", "Please wait...",true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			try {
				String a = "'";
				String url = "http://webplanex.co.in/projects/blackbelt/search.php?method=search&name=ab"
						+ str_search;
				JSONObject json = JSONParser.getJSONfromURL(url);

				data = json.getJSONArray(TAG_ARTICLE);
				ArticlePojo getdata = new ArticlePojo();

				for (int i = 0; i < data.length(); i++) {

					JSONObject c = data.getJSONObject(i);

					// Storing each json item in variable
					u_mid = c.getString(TAG_NAME);
					u_id = c.getString(TAG_ID);

					listdetail.add(u_mid);
					listtitle.add(u_id);

					getdata.setFOOD_NAME(u_mid);
					getdata.setFOOD_STATE(u_id);

					searchNames.add(u_mid);

					ArticlePojo setdata = new ArticlePojo();

					setdata.setFOOD_NAME(getdata.getFOOD_NAME());
					setdata.setFOOD_STATE(getdata.getFOOD_STATE());
					setdata.setFOOD_DESC(getdata.getFOOD_DESC());
					setdata.setFOOD_DIRECTION(getdata.getFOOD_DIRECTION());

					setdata.setFOOD_IMGURL(getdata.getFOOD_IMGURL());

					setdata.setFOOD_ID(getdata.getFOOD_ID());

					_userInfo.add(setdata);

				}
				for (int i = 0; i < listtitle.size(); i++) {

					titlestring = listtitle.toArray(new String[i]);
				}
				for (int i = 0; i < listdetail.size(); i++) {

					detistring = listdetail.toArray(new String[i]);
				}
				for (int i = 0; i < listdeSC.size(); i++) {

					descstring = listdeSC.toArray(new String[i]);
				}
				for (int i = 0; i < listdirection.size(); i++) {

					directionstring = listdirection.toArray(new String[i]);
				}
				for (int i = 0; i < listIMGURL.size(); i++) {

					imgstring = listIMGURL.toArray(new String[i]);
				}
				for (int i = 0; i < listId.size(); i++) {

					foodid = listId.toArray(new String[i]);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//
			return null;
		}

		protected void onPostExecute(Void v) {
			pDialog.dismiss();
			adapter = new ListAdapter(ReviewScreen.this);
			list.setAdapter(adapter);

		}
	}
	class MyListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;

		ArrayList<String> _array;
		ArrayList<String> _array1;

		public MyListAdapter(Context context, ArrayList<String> array,
				ArrayList<String> arrayname) {
			inflater = LayoutInflater.from(context);

			this._array = array;

			this._array1 = arrayname;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _array.size();
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

				convertView = inflater.inflate(R.layout.reviewscreen, null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.soupstate);

				viewHolder.title1 = (TextView) convertView
						.findViewById(R.id.soupdesc);
				
				// viewHolder.title1.setVisibility(View.GONE);
				
				 * viewHolder.imageview = (ImageView) convertView
				 * .findViewById(R.id.imgview);
				 

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				viewHolder.title.setText(Html.fromHtml(_array.get(position)));
				viewHolder.title1.setText(Html.fromHtml(_array1.get(position)));

				viewHolder.title1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						// Log.i(">>>click>>", ">>>");
						String name, desc, direction, state, url, id;

						name = detistring[position];
						state = titlestring[position];
						desc = descstring[position];
						direction = directionstring[position];
						url = imgstring[position];
						id = foodid[position];

						System.out.println("direction>>>" + desc);

						Intent i = new Intent(getParent(), Details.class);
						Bundle bundle = new Bundle();
						bundle.putString(TAG_ID, id);
						bundle.putString(TAG_NAME, name);

						Log.i("DESC >>", ">>" + desc);

						bundle.putString(TAG_DESC, desc);

						bundle.putString(TAG_DIRECTION, direction);
						bundle.putString(TAG_STATE, state);
						bundle.putString(TAG_URL, url);

						i.putExtras(bundle);
						// startActivity(i);

						// Intent inn = new Intent(getParent(),
						// ListItemName.class);
						
						 * Bundle bundle = new Bundle(); bundle.putString("id",
						 * id); bundle.putString("catname", catname);
						 * inn.putExtras(bundle);
						 
						WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
						parantActivity.startChildActivity("Details", i);

					}
				});

				// convertView.setOnClickListener(new
				// OnItemClickListener(position));
			} catch (Exception e) {
				// TODO: handle exception
			}
			return convertView;

		}

		private class ViewHolder {
			TextView title, title1;

			// Dynamic view

		}
	}
f
	class ListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;

		public ListAdapter(Context context) {
			inflater = LayoutInflater.from(context);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _userInfo.size();
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

				convertView = inflater.inflate(R.layout.soups, null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.soupstate);

				viewHolder.title1 = (TextView) convertView
						.findViewById(R.id.soupdesc);

				
				 * viewHolder.imageview = (ImageView) convertView
				 * .findViewById(R.id.imgview);
				 

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				viewHolder.title.setText(Html.fromHtml(_userInfo.get(position)
						.getFOOD_NAME()));

				viewHolder.title1.setText(Html.fromHtml(_userInfo.get(position)
						.getFOOD_STATE()));
				viewHolder.title1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String name, desc, direction, state, url, id;

						name = detistring[position];
						state = titlestring[position];
						desc = descstring[position];
						direction = directionstring[position];
						url = imgstring[position];
						id = foodid[position];

						System.out.println("direction>>>" + direction);

						Intent i = new Intent(getParent(), Details.class);
						Bundle bundle = new Bundle();
						bundle.putString(TAG_ID, id);
						bundle.putString(TAG_NAME, name);
						bundle.putString(TAG_DESC, desc);

						bundle.putString(TAG_DIRECTION, direction);
						bundle.putString(TAG_STATE, state);
						bundle.putString(TAG_URL, url);

						i.putExtras(bundle);
						// startActivity(i);

						// Intent inn = new Intent(getParent(),
						// ListItemName.class);
						
						 * Bundle bundle = new Bundle(); bundle.putString("id",
						 * id); bundle.putString("catname", catname);
						 * inn.putExtras(bundle);
						 
						WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
						// parantActivity.startChildActivity("Details", i);

					}
				});

				System.out.println(position);
				// convertView.setOnClickListener(new
				// OnItemClickListener(position));
			} catch (Exception e) {
				// TODO: handle exception
			}
			return convertView;

		}

		private class ViewHolder {
			TextView title, title1;
		}
	}
}
*/