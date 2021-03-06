package com.mycompany.myapp;

import android.content.*;
import android.database.sqlite.*;

public abstract class DAOBase
{
	// Nous sommes à la première version de la base
	// Si je décide de la mettre à jour, il faudra changer cet attribut
	protected final static int VERSION = 1;
	// Le nom du fichier qui représente ma base
	protected final static String NOM = "database.db";

	protected SQLiteDatabase mDb = null;
	protected MyListDBHandler mHandler = null;

	public DAOBase(Context pContext) {
		this.mHandler = new MyListDBHandler(pContext, NOM, null, VERSION);
	}

	public SQLiteDatabase open() {
		// Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
		mDb = mHandler.getWritableDatabase();
		return mDb;
	}

	public void close() {
		mDb.close();
	}

	public SQLiteDatabase getDb() {
		return mDb;
	}
}

