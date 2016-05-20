package com.mditservices.scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AppConfig {

	Context context;

	public AppConfig(Context mxt) {

		context = mxt;
	}
	public static String str_date;
	// Group drawable

	public static Drawable client;
	public static String str_client;
	public static String str_faith;
	public static String str_family;
	public static String str_friends;
	public static String str_prospect;
	public static String str_school;
	public static String str_vendor;
	public static String str_work;
	
	public static Drawable faith;
	public static Drawable family;
	public static Drawable friends;
	public static Drawable prospect;
	public static Drawable school;
	public static Drawable vendor;
	public static Drawable work;

	// Plan drawable

	public static String camera;
	public static Drawable gallery;
	
	
	public static Drawable call;
	public static String name_on_group;
	public static String name_on_plan;
	
	public static Drawable drink;
	public static Drawable email;
	public static Drawable lunch;
	public static Drawable intro;
	public static Drawable meeting;
	public static Drawable research;
	public static Drawable social;
	public static Drawable sport;
	public static Drawable stop;
	
	public static String name;
	public static String filesdcard;
	public static String file_plan;

	//public static Boolean callid = false;
	public static String mapaddress;

	public static int int1 ;
	public static int int2 ;
	public static int int3 ;
	public static int int4 ;
	public static int int5 ;
	public static int int6 ;

	public byte[] image;
	public byte[] byte_image;

	public static Integer group_path;
	public static Integer plan_path;
	ArrayList<Bitmap> gallery_bitmap = new ArrayList<Bitmap>();

	public static int position = 0;

	public static int gallery_position = 0;

	public static int celeb_position = 0;

	public static int test_positionswipeleft = 0;

	public static String test_positiongrid;

	public static String facebookname = null;

	static boolean groupgrid = false;

	static boolean plangrid = false;
	static boolean viewdata = false;
	
	boolean maptext = false;
	static boolean bundledata = false;

	public static ArrayList<Integer> plan_grid = new ArrayList<Integer>();

	public static ArrayList<Integer> group_grid = new ArrayList<Integer>();

	static ArrayList<HashMap<String, String>> celebritylist = new ArrayList<HashMap<String, String>>();

	static ArrayList<HashMap<String, String>> colorlist = new ArrayList<HashMap<String, String>>();

	static ArrayList<HashMap<String, String>> styleList = new ArrayList<HashMap<String, String>>();

	public static ArrayList<HashMap<String, String>> hairstylelist_hair = new ArrayList<HashMap<String, String>>();

	public static String livechat = "https://secure.livechatinc.com/licence/1187021/open_chat.cgi?groups=0#http%3A%2F%2Fwww.donnabellahair.com%2F%23";
}
