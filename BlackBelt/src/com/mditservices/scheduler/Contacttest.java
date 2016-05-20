package com.mditservices.scheduler;

import android.graphics.drawable.Drawable;

public class Contacttest {

	// private variables
	int _id;
	public byte[] get_imageplan() {
		return _imageplan;
	}

	public void set_imageplan(byte[] _imageplan) {
		this._imageplan = _imageplan;
	}

	String _name;
	String _facial;
	String _notes;
	byte[] _imageplan;
	byte[] _imagegroup;
	String dr_group;
	
	
	// Empty constructor
		public Contacttest() {

		}

		// constructor
		public Contacttest(int keyId, String name,String facial,String notes, byte[] image, byte[] imageplan) {
			this._id = keyId;
			this._name = name;
			this._facial = facial;
			this._notes = notes;
			this._imagegroup = image;
			this._imageplan = imageplan;

		}
		
		public String get_notes() {
			return _notes;
		}

		public void set_notes(String _notes) {
			this._notes = _notes;
		}

		public String get_facial() {
			return _facial;
		}

		public void set_facial(String _facial) {
			this._facial = _facial;
		}

		// getting ID
		public int getID() {
			return this._id;
		}

		// setting id
		public void setID(int keyId) {
			this._id = keyId;
		}

		// getting name
		public String getName() {
			return this._name;
		}

		// setting name
		public void setName(String name) {
			this._name = name;
		}

		// getting phone number
		public byte[] getImage() {
			return this._imagegroup;
		}

		// setting phone number
		public void setImage(byte[] image) {
			this._imagegroup = image;
		}
		
		public byte[] getImage1() {
			return this._imageplan;
		}

		// setting phone number
		public void setImage1(byte[] image1) {
			this._imageplan = image1;
		}

		public Object get(byte[] _imagegroup2) {
			// TODO Auto-generated method stub
			return null;
		}



}
