package com.mycompany.myapp;

import android.content.*;
import android.database.Cursor;

public class ListeDAO extends DAOBase
{
	public ListeDAO(Context pContext){
		super(pContext);
	}

	public void create(ListeDTO liste){
		mDb.rawQuery("insert into "+MyListDBHandler.LIST_TABLE_NAME+
					", "+MyListDBHandler.LIST_DESCRIPTION+
					" values (?)", new String[]{liste.getDescription()});
	}

	public ListeDTO read(long id){
		String description;
		Cursor listes = mDb.rawQuery("select "+MyListDBHandler.LIST_DESCRIPTION+
									" from "+MyListDBHandler.LIST_TABLE_NAME+
									" where "+MyListDBHandler.LIST_ID+
									" = ?",new String[]{String.valueOf(id)});
		if(listes.moveToFirst())
			description = listes.getString(0);
		else
			return null;
		return new ListeDTO(id,description);
	}

	public void update(ListeDTO liste){
		mDb.rawQuery("update "+MyListDBHandler.LIST_TABLE_NAME+
					" set "+MyListDBHandler.LIST_DESCRIPTION+" = ?"+
					" where "+MyListDBHandler.LIST_ID+" = ?",
					new String[]{liste.getDescription(),String.valueOf(liste.getId())});
	}

	public void delete(long id){
		//first delete items
		mDb.rawQuery("delete from "+MyListDBHandler.LIST_ITEM_TABLE_NAME+
					" where "+MyListDBHandler.LIST_ITEM_LIST+
					" = ?",new String[]{String.valueOf(id)});
		mDb.rawQuery("delete from "+MyListDBHandler.LIST_TABLE_NAME+
					" where "+MyListDBHandler.LIST_ID+
					" = ?",new String[]{String.valueOf(id)});
	}
}
