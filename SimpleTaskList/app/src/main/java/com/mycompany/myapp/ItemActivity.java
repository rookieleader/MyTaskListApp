package com.mycompany.myapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gunseb on 24/01/16.
 */
public class ItemActivity extends Activity implements ItemCreateDialogFragment.ItemCreateDialogListener{

    ItemDAO itemDAO;
    ListView listViewItems;
    ActionMode mActionMode;
    int listId;
    int selectedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemactivitylayout);
        itemDAO = new ItemDAO(this);
        listViewItems = (ListView) findViewById(R.id.ListViewItems);

        Intent in = getIntent();
        listId = in.getIntExtra(MyListDBHandler.LIST_ID, -1);

        if (listId != -1) {
            ArrayList<ItemDTO> items = itemDAO.getListItems(listId);

            if(items!=null) {
                ItemArrayAdapter itemAdapter = new ItemArrayAdapter(this, items);
                listViewItems.setAdapter(itemAdapter);
            }
        }
        listViewItems.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mActionMode == null) {
                    selectedPosition = position;
                    listViewItems.setItemChecked(position, true);
                    mActionMode = ItemActivity.this.startActionMode(mActionModeCallback);
                    view.setSelected(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                DialogFragment newFragment = new ItemCreateDialogFragment();
                newFragment.show(getFragmentManager(),"add_item");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ItemCreateDialogFragment lcdf = (ItemCreateDialogFragment) dialog;
        Toast.makeText(getApplicationContext(), lcdf.itemname.getText().toString(),
                Toast.LENGTH_SHORT).show();
        ItemDTO itemDTO = new ItemDTO(0,listId,lcdf.itemname.getText().toString(),false);
        itemDAO.create(itemDTO);
        ItemArrayAdapter itemAdapter = (ItemArrayAdapter) listViewItems.getAdapter();
        if(itemAdapter==null) {
            ArrayList<ItemDTO> items = itemDAO.getListItems(listId);
            itemAdapter = new ItemArrayAdapter(this, items);
            listViewItems.setAdapter(itemAdapter);
        }else{
            itemAdapter.add(itemDTO);
            itemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.maincontextmenu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteItem();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            listViewItems.setItemChecked(listViewItems.getSelectedItemPosition(),false);
            selectedPosition = -1;
        }
    };

    public void deleteItem(){
        ItemDTO item = (ItemDTO) listViewItems.getItemAtPosition(selectedPosition);
        itemDAO.delete(item.getId());
        ItemArrayAdapter itemArrayAdapter = (ItemArrayAdapter) listViewItems.getAdapter();
        itemArrayAdapter.remove(item);
        itemArrayAdapter.notifyDataSetChanged();
    }
}