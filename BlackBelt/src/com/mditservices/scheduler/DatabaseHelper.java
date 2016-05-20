package com.mditservices.scheduler;
/*package com.webinfoways.blackbelt;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static String DATABASENAME = "namericknew.db";
	public static String PRODUCTTABLE = "producttable";

	public static String colProductId = "productId";
	public static String colProductName = "productname";
	public static String colProductDesc = "productdesc";

	public static String colProductState = "productstate";
	public static String colProductDirection = "productdirection";
	public static String colProductURL = "producturl";

	private ArrayList<Item> cartList = new ArrayList<Item>();
	Context c;

	public DatabaseHelper(Context context) {
		super(context, DATABASENAME, null, 33);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + PRODUCTTABLE + "(" + colProductId
				+ " TEXT ," + colProductName + "	TEXT ," + colProductDesc
				+ "	TEXT ," + colProductState + " TEXT ," + colProductDirection
				+ " BLOB ," + colProductURL + " BLOB )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + PRODUCTTABLE);
		onCreate(db);
	}

	public void addProductToCart(Contact productitem) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(colProductId, productitem._id);
		contentValues.put(colProductName, productitem._name);
	//	contentValues.put(colProductState, productitem.facial);

		//contentValues.put(colProductURL, productitem.plan);

		db.insert(PRODUCTTABLE, colProductName, contentValues);
//		 db.insert(PRODUCTTABLE, colProductDesc, contentValues);
//		 db.insert(PRODUCTTABLE, colProductDirection, contentValues);
//		 db.insert(PRODUCTTABLE, colProductURL, contentValues);

		
		 * colProductId, , , colProductDesc,,
		 
		db.close();

	}

	// update

	public void removeProductFromCart(int productid) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from producttable where productId=" + productid);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Item> getCartItems() 
	{
		cartList.clear();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + PRODUCTTABLE, null);
		
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					Item item = new Item();
					item.id = cursor.getString(cursor
							.getColumnIndex(colProductId));
					item.name = cursor.getString(cursor
							.getColumnIndex(colProductName));

					item.notes = cursor.getString(cursor
							.getColumnIndex(colProductDesc));  

					item.facial = cursor.getString(cursor
							.getColumnIndex(colProductState));

					item.group = cursor.getString(cursor
							.getColumnIndex(colProductDirection));
					item.plan = cursor.getString(cursor
							.getColumnIndex(colProductURL));
					cartList.add(item);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return cartList;
	}

}
*/