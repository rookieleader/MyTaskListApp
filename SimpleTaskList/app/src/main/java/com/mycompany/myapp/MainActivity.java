package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends Activity implements ListeCreateDialogFragment.ListeCreateDialogListener
{
    ListeDAO listeDAO;
    ListView listViewListes;
    ActionMode mActionMode;
    int selectedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listeDAO = new ListeDAO(this);
        listViewListes = (ListView) findViewById(R.id.ListViewListes);
        ArrayList<ListeDTO> listes = listeDAO.readAll();

        if(listes != null) {
            ListeArrayAdapter listeAdapter = new ListeArrayAdapter(this, listes);
            listViewListes.setAdapter(listeAdapter);
        }
        listViewListes.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined above
                selectedPosition = position;
                listViewListes.setItemChecked(position,true);
                mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
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
                DialogFragment newFragment = new ListeCreateDialogFragment();
                newFragment.show(getFragmentManager(),"add_liste");
                return true;
            case R.id.action_delete:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ListeCreateDialogFragment lcdf = (ListeCreateDialogFragment) dialog;
        Toast.makeText(getApplicationContext(), lcdf.listname.getText().toString(),
                Toast.LENGTH_SHORT).show();
        ListeDTO listeDTO = new ListeDTO(0,lcdf.listname.getText().toString());
        listeDAO.create(listeDTO);
        ListeArrayAdapter listeAdapter = (ListeArrayAdapter) listViewListes.getAdapter();
        if(listeAdapter==null) {
            ArrayList<ListeDTO> listes = listeDAO.readAll();
            listeAdapter = new ListeArrayAdapter(this, listes);
            listViewListes.setAdapter(listeAdapter);
        }else{
            listeAdapter.add(listeDTO);
            listeAdapter.notifyDataSetChanged();
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
                    deleteListe();
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
            listViewListes.setItemChecked(selectedPosition,false);
            selectedPosition = -1;
            //listViewListes.setOnItemClickListener(this);
        }
    };
    private void deleteListe(){
        ListeDTO liste = (ListeDTO)listViewListes.getItemAtPosition(selectedPosition);
        listeDAO.delete(liste.getId());
        ListeArrayAdapter listeAdapter = (ListeArrayAdapter) listViewListes.getAdapter();
        listeAdapter.remove(liste);
        listeAdapter.notifyDataSetChanged();
    }
}
