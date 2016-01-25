package com.mycompany.myapp;

import android.content.*;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collection;

public class ListeDAO extends DAOBase
{
	public ListeDAO(Context pContext){
		super(pContext);
	}

	public void create(ListeDTO liste){
		open();
		mDb.rawQuery("insert into "+MyListDBHandler.LIST_TABLE_NAME+
					", "+MyListDBHandler.LIST_DESCRIPTION+
					" values (?)", new String[]{liste.getDescription()});
		close();
	}

	public ListeDTO read(long id){
		String description;
		open();
		Cursor listes = mDb.rawQuery("select "+MyListDBHandler.LIST_DESCRIPTION+
									" from "+MyListDBHandler.LIST_TABLE_NAME+
									" where "+MyListDBHandler.LIST_ID+
									" = ?",new String[]{String.valueOf(id)});
		if(listes.moveToFirst()) {
			description = listes.getString(0);
			close();
			return new ListeDTO(id, description);
		}
		return null;
	}

	public void update(ListeDTO liste){
		open();
		mDb.rawQuery("update " + MyListDBHandler.LIST_TABLE_NAME +
						" set " + MyListDBHandler.LIST_DESCRIPTION + " = ?" +
						" where " + MyListDBHandler.LIST_ID + " = ?",
				new String[]{liste.getDescription(), String.valueOf(liste.getId())});
		close();
	}

	public void delete(long id){
		//first delete items
		open();
		mDb.rawQuery("delete from " + MyListDBHandler.LIST_ITEM_TABLE_NAME +
				" where " + MyListDBHandler.LIST_ITEM_LIST +
				" = ?", new String[]{String.valueOf(id)});
		mDb.rawQuery("delete from "+MyListDBHandler.LIST_TABLE_NAME+
					" where "+MyListDBHandler.LIST_ID+
					" = ?",new String[]{String.valueOf(id)});
		close();
	}

	public ArrayList<ListeDTO> readAll(){
		ArrayList<ListeDTO> listesCollection=null;
		open();
		Cursor items = mDb.rawQuery("select "+MyListDBHandler.LIST_ID+
				", "+MyListDBHandler.LIST_DESCRIPTION+
				" from "+MyListDBHandler.LIST_TABLE_NAME,null);
		while(items.moveToNext()) {
			if (null == listesCollection)
				listesCollection = new ArrayList<ListeDTO>();
			ListeDTO liste = new ListeDTO(items.getLong(0), items.getString(1));
			listesCollection.add(liste);
		}
		close();
		return listesCollection;
	}
}
