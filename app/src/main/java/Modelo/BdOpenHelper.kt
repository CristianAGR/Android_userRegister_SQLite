package Modelo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "Usuarios.db"
val DATABASE_VERSION = 1

class BdOpenHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(UsuariosDataSource.CREATE_USUARIOS_SCRIPT)
        db.execSQL(UsuariosDataSource.INSERT_USUARIOS_SCRIPT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not implemented")
    }
}