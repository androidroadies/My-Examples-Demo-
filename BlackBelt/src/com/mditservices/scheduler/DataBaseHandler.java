package com.mditservices.scheduler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "blackbelt";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_FACIAL = "facial";
	private static final String KEY_NOTES = "notes";
	private static final String KEY_DRW = "filegroup";
	private static final String KEY_DRW_PLAN = "fileplan";
	private static final String KEY_GROUP = "imagegroup";
	private static final String KEY_PLAN = "imageplan";
	private static final String KEY_TEXT_GROUP = "textgroup";
	private static final String KEY_TEXT_PLAN = "textplan";
	private static final String KEY_CAMERA = "camera";
	private static final String KEY_DATE = "date";

	public DataBaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_FACIAL + " TEXT," + KEY_NOTES + " TEXT," + KEY_DRW
				+ " TEXT," + KEY_DRW_PLAN + " TEXT," 
				+ KEY_TEXT_GROUP + " TEXT,"
				+ KEY_TEXT_PLAN + " TEXT,"
				+ KEY_CAMERA + " TEXT,"
				+ KEY_GROUP + " BLOB,"
				+ KEY_DATE + " TEXT,"
				+ KEY_PLAN + " BLOB" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact._name); // Contact Name
		values.put(KEY_FACIAL, contact._facial); // Contact Name
		values.put(KEY_NOTES, contact._notes); // Contact Name
		values.put(KEY_DRW, contact._file); // Contact Name
		values.put(KEY_DRW_PLAN, contact._file_plan); // Contact Name
		values.put(KEY_TEXT_GROUP, contact._name_group); // Contact Name
		values.put(KEY_TEXT_PLAN, contact._name_plan); // Contact Name
		values.put(KEY_CAMERA, contact._camera); // Contact Name
		values.put(KEY_GROUP, contact._imagegroup); // Contact Name
		values.put(KEY_DATE, contact._date); // Contact Name
		values.put(KEY_PLAN, contact._imageplan); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	public void addimag(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(KEY_NAME, contact._name); // Contact Name
		// values.put(KEY_FACIAL, contact._facial); // Contact Name
		// values.put(KEY_NOTES, contact._notes); // Contact Name
		values.put(KEY_GROUP, contact._imagegroup); // Contact Name
		// values.put(KEY_PLAN, contact._imageplan); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) 
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_NAME, KEY_FACIAL, KEY_NOTES, KEY_DRW, KEY_DRW_PLAN,KEY_TEXT_GROUP,KEY_TEXT_PLAN,KEY_CAMERA,
				KEY_GROUP,KEY_DATE,KEY_PLAN }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getBlob(1), null);
		// return contact
		return contact;
	}
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
	//	String selectQuery = "SELECT * FROM contacts ORDER BY name";
		String selectQuery = "SELECT * FROM contacts";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.set_facial(cursor.getString(2));
				contact.set_notes(cursor.getString(3));
				contact.set_file(cursor.getString(4));
				contact.set_file_plan(cursor.getString(5));
				contact.set_name_group(cursor.getString(6));
				contact.set_name_plan(cursor.getString(7));
				contact.set_camera(cursor.getString(8));
				contact.set_date(cursor.getString(9));
				contact.setImage(cursor.getBlob(10));

				// contact.setImage1(cursor.getBlob(5));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		// return contact list
		return contactList;
	}
	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_FACIAL, contact.get_facial());
		values.put(KEY_NOTES, contact.get_notes());
		values.put(KEY_DRW, contact.get_file());
		values.put(KEY_DRW_PLAN, contact.get_file_plan());
		values.put(KEY_TEXT_GROUP, contact.get_name_group());
		values.put(KEY_TEXT_PLAN, contact.get_name_plan());
		values.put(KEY_CAMERA, contact.get_camera());
		values.put(KEY_GROUP, contact.getImage());
		values.put(KEY_DATE, contact.get_date());

		// values.put(KEY_PLAN, contact.getImage1());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from contacts where id=" + id);
		db.close();
		/*
		 * db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] {
		 * String.valueOf(contact.getID()) }); db.close();
		 */
	}
	
	public void singlecontact(int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("select from contacts where id=" + id);
		db.close();
		/*
		 * db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] {
		 * String.valueOf(contact.getID()) }); db.close();
		 */
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}
