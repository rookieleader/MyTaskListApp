package com.mycompany.myapp;

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
        mDb.rawQuery("insert into ? (?, ?, ?) values (?, ?, ?)",
                new String[]{MyListDBHandler.LIST_ITEM_TABLE_NAME,
                MyListDBHandler.LIST_ITEM_DESC,MyListDBHandler.LIST_ITEM_DONE,
                MyListDBHandler.LIST_ITEM_LIST,item.getDescription(),
                String.valueOf(item.isDone()), String.valueOf(item.getListeId())});
        close();
    }

    public ItemDTO read(int id){
        open();
        Cursor items = mDb.rawQuery("select ?, ?, ? from ? where ? = ?",
                new String[]{MyListDBHandler.LIST_ITEM_DESC,
                MyListDBHandler.LIST_ITEM_DONE,MyListDBHandler.LIST_ITEM_LIST,
                MyListDBHandler.LIST_TABLE_NAME,MyListDBHandler.LIST_ITEM_ID,
                String.valueOf(id)});
        if(items.moveToFirst()) {
            return new ItemDTO(id,items.getInt(2),items.getString(0),
                                (items.getInt(1)==1));
        }
        close();
        return null;
    }

    public void update(ItemDTO item){
        int isDone = (item.isDone())? 1 : 0;
        open();
        mDb.rawQuery("update ? set ? = ?, ? = ?, ? = ? where ? = ?",
                        new String[]{MyListDBHandler.LIST_ITEM_TABLE_NAME,
                        MyListDBHandler.LIST_ITEM_DESC,item.getDescription(),
                        MyListDBHandler.LIST_ITEM_DONE,String.valueOf(isDone),
                        MyListDBHandler.LIST_ITEM_LIST,String.valueOf(item.getListeId()),
                        MyListDBHandler.LIST_ITEM_ID,String.valueOf(item.getId())});
        close();
    }

    public void delete(long id){
        open();
        mDb.rawQuery("delete from ? where ? = ?",
                new String[]{MyListDBHandler.LIST_ITEM_TABLE_NAME,
                MyListDBHandler.LIST_ITEM_ID,String.valueOf(id)});
        close();
    }

    public ArrayList<ItemDTO> getListItems(int listeId){
        ArrayList<ItemDTO> itemsCollection=null;
        open();
        Cursor items = mDb.rawQuery("select ?, ?, ? from ? where ? = ?",
                new String[]{MyListDBHandler.LIST_ITEM_DESC,MyListDBHandler.LIST_ITEM_DONE,
                MyListDBHandler.LIST_ITEM_ID, MyListDBHandler.LIST_ITEM_TABLE_NAME,
                MyListDBHandler.LIST_ITEM_ID,String.valueOf(listeId)});
        while(items.moveToNext()) {
            if (null == itemsCollection)
                itemsCollection = new ArrayList<ItemDTO>();
            itemsCollection.add(new ItemDTO(items.getInt(2), listeId, items.getString(0),
                    (items.getInt(1) == 1)));
        }
        close();
        return itemsCollection;
    }
    public Collection<ItemDTO> getListItems(ListeDTO liste){
        return getListItems(liste.getId());
    }
}
