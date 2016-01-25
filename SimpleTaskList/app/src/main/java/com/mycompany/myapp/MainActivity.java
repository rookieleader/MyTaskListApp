package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends Activity 
{
    ListeDAO listeDAO;
    ListView listViewListes;
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
    }
}
