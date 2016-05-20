package com.mditservices.scheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class NextStep extends Activity {
	GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupgrid);

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
	}
	public class ImageAdapter extends BaseAdapter 
	{
		private Context mContext;

		public ImageAdapter(Context c) 
		{
			mContext = c;
		}
		public int getCount() {
			return mThumbIds.length;
		}
		public Object getItem(int position) 
		{
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}
		// create a new ImageView for each item referenced by the Adapter
		public View getView(final int position, View convertView, ViewGroup parent) {
			View v;

			if (convertView == null) { // if it’s not recycled, initialize some
										// attributes
				LayoutInflater li = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = li.inflate(R.layout.group_grid_item, null);
				TextView tv = (TextView) v.findViewById(R.id.icon_text);
				tv.setText(mTextsIds[position]);
				ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
				// iv.setLayoutParams(new GridView.LayoutParams(85, 85));
				// iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
				iv.setPadding(8, 8, 8, 8);
				iv.setImageResource(mThumbIds[position]);
				
				iv.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						Intent screen = new Intent(getApplicationContext(),EnterUserData.class);
						screen.putExtra("Text", mTextsIds[position]);
						screen.putExtra("Image", mThumbIds[position]);
						screen.putExtra("Pos", position);
						System.out.println(position);
						//startActivity(screen);
					}
				});
			} else 
			{
				v = (View) convertView;
			}
			return v;
		}
		// references to our images
		private Integer[] mThumbIds = { R.drawable.ic_launcher,
				R.drawable.ic_launcher };

		// references to our texts
		private String[] mTextsIds = { "Batman", "Caillou" };
	}
}
