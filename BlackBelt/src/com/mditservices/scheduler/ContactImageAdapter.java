package com.mditservices.scheduler;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactImageAdapter extends ArrayAdapter<Contact> {
	Context context;
	int layoutResourceId;
	ArrayList<Contact> data = new ArrayList<Contact>();

	public ContactImageAdapter(Context context, int layoutResourceId,
			ArrayList<Contact> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ImageHolder holder = null;
		if (row == null) {

			// convertView = inflater.inflate(R.layout.favorite_row, null);
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ImageHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.txt_favview);
			holder.imgIcon = (ImageView) row
					.findViewById(R.id.img_group_listraw);
			row.setTag(holder);
		} else {
			holder = (ImageHolder) row.getTag();
		}
		Contact picture = data.get(position);
		holder.txtTitle.setText(picture._name);

		System.out.println(">>>>>>>>>>>>>" + data.get(position));
		// convert byte to bitmap take from contact class
		byte[] outImage = picture._imageplan;
		ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		holder.imgIcon.setImageBitmap(theImage);

		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>>>>>>>>>>" + position);
			}
		});
		return row;
	}

	static class ImageHolder {
		ImageView imgIcon, imgiconplan;
		TextView txtTitle, txtfacial, txtnotes;
	}
}
