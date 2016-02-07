package com.mycompany.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gunseb on 22/01/16.
 */
public class ItemDAO extends DAOBase {
    public ItemDAO(Context pContext) {
        super(pContext);
    }

    public void create(ItemDTO item){
        int isDone = (item.isDone())? 1 : 0;
        open();
        ContentValues values = new ContentValues();
        values.put(MyListDBHandler.LIST_ITEM_DESC,item.getDescription());
        values.put(MyListDBHandler.LIST_ITEM_DONE,String.valueOf(item.isDone()));
        values.put(MyListDBHandler.LIST_ITEM_LIST, String.valueOf(item.getListeId()));
        mDb.insert(MyListDBHandler.LIST_ITEM_TABLE_NAME, null, values);
        close();
    }

    public ItemDTO read(int id){
        open();
        Cursor items = mDb.query(MyListDBHandler.LIST_ITEM_TABLE_NAME,
                new String[]{MyListDBHandler.LIST_ITEM_LIST,MyListDBHandler.LIST_ITEM_DESC,
                MyListDBHandler.LIST_ITEM_DONE},MyListDBHandler.LIST_ITEM_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null);
        if(items.moveToFirst()) {
            return new ItemDTO(id,items.getInt(0),items.getString(1),
                                (items.getInt(2)==1));
        }
        close();
        return null;
    }

    public void update(ItemDTO item){
        int isDone = (item.isDone())? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(MyListDBHandler.LIST_ITEM_DESC, item.getDescription());
        values.put(MyListDBHandler.LIST_ITEM_DONE, String.valueOf(isDone));
        values.put(MyListDBHandler.LIST_ITEM_LIST, String.valueOf(item.getListeId()));
        open();
        mDb.update(MyListDBHandler.LIST_ITEM_TABLE_NAME, values,
                MyListDBHandler.LIST_ITEM_ID + " = " + String.valueOf(item.getId()), null);
        close();
    }

    public void delete(int id){
        open();
        mDb.delete(MyListDBHandler.LIST_ITEM_TABLE_NAME,
                MyListDBHandler.LIST_ITEM_ID + " = " + String.valueOf(id), null);
        close();
    }

    public ArrayList<ItemDTO> getListItems(int listeId){
        ArrayList<ItemDTO> itemsCollection=null;
        open();
        Cursor items = mDb.query(MyListDBHandler.LIST_ITEM_TABLE_NAME,
                new String[]{MyListDBHandler.LIST_ITEM_ID,MyListDBHandler.LIST_ITEM_DESC,
                MyListDBHandler.LIST_ITEM_DONE}, MyListDBHandler.LIST_ITEM_LIST + "=?",
                new String[]{String.valueOf(listeId)}, null, null,null);
        while(items.moveToNext()) {
            if (null == itemsCollection)
                itemsCollection = new ArrayList<ItemDTO>();
            itemsCollection.add(new ItemDTO(items.getInt(0), listeId, items.getString(1),
                    (items.getInt(2) == 1)));
        }
        close();
        return itemsCollection;
    }
    public Collection<ItemDTO> getListItems(ListeDTO liste){
        return getListItems(liste.getId());
    }
}
