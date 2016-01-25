package com.mycompany.myapp;

import android.content.*;
import android.database.sqlite.*;

public class MyListDBHandler extends SQLiteOpenHelper
{
	public static final String LIST_TABLE_NAME = "Liste";
	public static final String LIST_ID = "id";
	public static final String LIST_DESCRIPTION = "description";
	public static final String LIST_ITEM_TABLE_NAME = "Items";
	public static final String LIST_ITEM_ID = "id";
	public static final String LIST_ITEM_LIST = "list_id";
	public static final String LIST_ITEM_DONE = "done";
	public static final String LIST_ITEM_DESC = "description";

	public static final String LIST_TABLE_CREATE = 
	"CREATE TABLE " + LIST_TABLE_NAME+ " (" +
	LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	LIST_DESCRIPTION + " TEXT);";

	public static final String LIST_ITEM_TABLE_CREATE = 
			"CREATE TABLE "+LIST_ITEM_TABLE_NAME+ " ("+
			LIST_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
			LIST_ITEM_DESC+" TEXT, "+LIST_ITEM_DONE+" INTEGER, "+
			LIST_ITEM_LIST+" INTEGER, "+
			"FOREIGN KEY("+LIST_ITEM_LIST+") REFERENCES "+
			LIST_TABLE_NAME+"("+LIST_ID+") );";

	public static final String DROP_LIST_TABLE =
	"DROP TABLE IF EXISTS "+LIST_ITEM_TABLE_NAME+";";

	public static final String DROP_LIST_ITEMS =
	"DROP TABLE IF EXISTS "+LIST_TABLE_NAME+";";

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO: Implement this method
		db.execSQL(LIST_TABLE_CREATE);
		db.execSQL(LIST_ITEM_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO: Implement this method
		db.execSQL(DROP_LIST_TABLE);
		db.execSQL(DROP_LIST_ITEMS);
		onCreate(db);
	}

	public MyListDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
		super(context,name,factory,version);
	}
}
