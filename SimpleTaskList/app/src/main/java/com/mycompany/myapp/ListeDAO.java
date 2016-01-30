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
		ContentValues values = new ContentValues();
		values.put(MyListDBHandler.LIST_DESCRIPTION, liste.getDescription());
		mDb.insert(MyListDBHandler.LIST_TABLE_NAME,null,values);
		close();
	}

	public ListeDTO read(int id){
		String description;
		open();
		Cursor listes = mDb.rawQuery("select ? from ? where ? = ?",
				new String[]{MyListDBHandler.LIST_DESCRIPTION,
				MyListDBHandler.LIST_TABLE_NAME,MyListDBHandler.LIST_ID,
				String.valueOf(id)});
		if(listes.moveToFirst()) {
			description = listes.getString(0);
			close();
			return new ListeDTO(id, description);
		}
		return null;
	}

	public void update(ListeDTO liste){
		open();
		ContentValues values = new ContentValues();
		values.put(MyListDBHandler.LIST_DESCRIPTION, liste.getDescription());
		mDb.update(MyListDBHandler.LIST_TABLE_NAME,values,MyListDBHandler.LIST_ID +
				" = " + String.valueOf(liste.getId()),null);
		close();
	}

	public void delete(int id){
		//first delete items
		open();
		ContentValues values = new ContentValues();
		mDb.delete(MyListDBHandler.LIST_ITEM_TABLE_NAME,
				MyListDBHandler.LIST_ITEM_LIST + " = " + String.valueOf(id), null);
		mDb.delete(MyListDBHandler.LIST_TABLE_NAME,
				MyListDBHandler.LIST_ID + " = " + String.valueOf(id), null);
		close();
	}

	public ArrayList<ListeDTO> readAll(){
		ArrayList<ListeDTO> listesCollection=null;
		open();
		Cursor items = mDb.query(MyListDBHandler.LIST_TABLE_NAME,
				new String[]{MyListDBHandler.LIST_ID, MyListDBHandler.LIST_DESCRIPTION},
				null, null, null, null, null, null);
		while(items.moveToNext()) {
			if (null == listesCollection)
				listesCollection = new ArrayList<ListeDTO>();
			ListeDTO liste = new ListeDTO(items.getInt(0), items.getString(1));
			listesCollection.add(liste);
		}
		close();
		return listesCollection;
	}
}
