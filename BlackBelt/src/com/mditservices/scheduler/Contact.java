package com.mditservices.scheduler;

import android.graphics.drawable.Drawable;

public class Contact {

	// private variables
	int _id ;
	String _name;
	String _facial;
	String _notes;

	String _file;
	String _file_plan;
	String _date;
	

	public String get_date() {
		return _date;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_camera() {
		return _camera;
	}

	public void set_camera(String _camera) {
		this._camera = _camera;
	}

	String _name_group;
	String _camera;
	public String get_name_group() {
		return _name_group;
	}

	public void set_name_group(String _name_group) {
		this._name_group = _name_group;
	}

	public String get_name_plan() {
		return _name_plan;
	}

	public void set_name_plan(String _name_plan) {
		this._name_plan = _name_plan;
	}

	String _name_plan;
	
	public String get_file_plan() {
		return _file_plan;
	}

	public void set_file_plan(String _file_plan) {
		this._file_plan = _file_plan;
	}

	public String get_file() {
		return _file;
	}

	public void set_file(String _file) {
		this._file = _file;
	}

	byte[] _imageplan;
	byte[] _imagegroup;

	/*public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}*/

	public String get_name() {
		return _name;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_facial() {
		return _facial;
	}

	public void set_facial(String _facial) {
		this._facial = _facial;
	}

	public String get_notes() {
		return _notes;
	}

	public void set_notes(String _notes) {
		this._notes = _notes;
	}

	public byte[] get_imageplan() {
		return _imageplan;
	}

	public void set_imageplan(byte[] _imageplan) {
		this._imageplan = _imageplan;
	}

	public byte[] get_imagegroup() {
		return _imagegroup;
	}

	public void set_imagegroup(byte[] _imagegroup) {
		this._imagegroup = _imagegroup;
	}

	// Empty constructor
	public Contact() {

	}

	// constructor
	public Contact(int keyId, String name, byte[] image, byte[] imageplan) {
		this._id = keyId;
		this._name = name;
		this._imagegroup = image;
		this._imageplan = imageplan;

	}

	// constructor
	public Contact(String name, byte[] image, byte[] imageplan) {
		this._name = name;
		this._imagegroup = image;
		this._imageplan = imageplan;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int string) {
		this._id = string;
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

	
}
