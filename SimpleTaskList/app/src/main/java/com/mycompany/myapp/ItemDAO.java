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
        mDb.rawQuery("insert into " + MyListDBHandler.LIST_ITEM_TABLE_NAME +
                ", " + MyListDBHandler.LIST_ITEM_DESC +
                ", " + MyListDBHandler.LIST_ITEM_DONE +
                ", " + MyListDBHandler.LIST_ITEM_LIST +
                " values (?, ?, ?)", new String[]{item.getDescription(),
                String.valueOf(item.isDone()), String.valueOf(item.getListeId())});
    }

    public ItemDTO read(long id){
        Cursor items = mDb.rawQuery("select "+MyListDBHandler.LIST_ITEM_DESC+
                ", "+MyListDBHandler.LIST_ITEM_DONE+
                ", "+MyListDBHandler.LIST_ITEM_LIST+
                " from "+MyListDBHandler.LIST_TABLE_NAME+
                " where "+MyListDBHandler.LIST_ITEM_ID+
                " = ?",new String[]{String.valueOf(id)});
        if(items.moveToFirst()) {
            return new ItemDTO(id,items.getLong(2),items.getString(0),
                                (items.getLong(1)==1));
        }
        else
            return null;
    }

    public void update(ItemDTO item){
        int isDone = (item.isDone())? 1 : 0;
        mDb.rawQuery("update "+MyListDBHandler.LIST_ITEM_TABLE_NAME+
                        " set "+MyListDBHandler.LIST_ITEM_DESC+" = ?"+
                        ", "+MyListDBHandler.LIST_ITEM_DONE+" = ?"+
                        ", "+MyListDBHandler.LIST_ITEM_LIST+" = ?"+
                        " where "+MyListDBHandler.LIST_ITEM_ID+" = ?",
                        new String[]{item.getDescription(),String.valueOf(isDone),
                        String.valueOf(item.getListeId()), String.valueOf(item.getId())});
    }

    public void delete(long id){
        mDb.rawQuery("delete from "+MyListDBHandler.LIST_ITEM_TABLE_NAME+
                    " where "+MyListDBHandler.LIST_ITEM_ID+
                    " = ?",new String[]{String.valueOf(id)});
    }

    public Collection<ItemDTO> getListItems(long listeId){
        ArrayList<ItemDTO> itemsCollection=null;
        Cursor items = mDb.rawQuery("select "+MyListDBHandler.LIST_ITEM_DESC+
                ", "+MyListDBHandler.LIST_ITEM_DONE+
                ", "+MyListDBHandler.LIST_ITEM_ID+
                " from "+MyListDBHandler.LIST_TABLE_NAME+
                " where "+MyListDBHandler.LIST_ITEM_ID+
                " = ?",new String[]{String.valueOf(listeId)});
        while(items.moveToNext()) {
            if (null == itemsCollection)
                itemsCollection = new ArrayList<ItemDTO>();
            itemsCollection.add(new ItemDTO(items.getLong(2), listeId, items.getString(0),
                    (items.getLong(1) == 1)));
        }
        return itemsCollection;
    }
    public Collection<ItemDTO> getListItems(ListeDTO liste){
        return getListItems(liste.getId());
    }
}
