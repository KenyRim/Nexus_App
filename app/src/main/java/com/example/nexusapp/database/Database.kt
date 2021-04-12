package com.example.nexusapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.nexusapp.models.CategoryModel

class Database(context: Context) {

    private var database: SQLiteDatabase
    private val context: Context

    companion object {
        private const val DATABASE_NAME = "saved_data.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "newsTable"
        private const val ID = "_id"
        private const val COL_TIT: String = "title"
        private const val COL_URL: String = "url"
        private const val COL_IMG: String = "image"
        private const val COL_DES: String = "description"

        private const val NUM_ID = 0
        private const val NUM_TIT = 1
        private const val NUM_URL = 2
        private const val NUM_IMG = 3
        private const val NUM_DES = 4
    }

    private var id: Long = 0
    private lateinit var title: String
    private lateinit var url: String
    private lateinit var image: String
    private lateinit var description: String

    fun insert(model: CategoryModel): Long {
        val cv = ContentValues()
        cv.put(COL_TIT, model.title)
        cv.put(COL_URL, model.url)
        cv.put(COL_IMG, model.image)
        cv.put(COL_DES, model.description)
        return database.insert(TABLE_NAME, null, cv)
    }

    fun delete(id: Long) {
        database.delete(TABLE_NAME, "$ID = ?", arrayOf(id.toString()))
    }

    fun deleteAll() {
        database.delete(TABLE_NAME, null, null)
        context.deleteDatabase(DATABASE_NAME)
    }

    fun selectAll(): ArrayList<CategoryModel> {
        val cursor: Cursor = database.query(TABLE_NAME, null, null, null, null, null, ID)
        val arr: ArrayList<CategoryModel> = ArrayList()
        cursor.moveToFirst()
        if (!cursor.isAfterLast) {
            do {
                id = cursor.getLong(NUM_ID)
                title = cursor.getString(NUM_TIT)
                url = cursor.getString(NUM_URL)
                image = cursor.getString(NUM_IMG)
                description = cursor.getString(NUM_DES)
                arr.add(CategoryModel(0, image, title, description, url, 0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return arr
    }

    private inner class ResultOpenHelper(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            database = db
            val sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TIT TEXT, " +
                    "$COL_URL TEXT, " +
                    "$COL_IMG TEXT, " +
                    "$COL_DES TEXT, " +
                    "UNIQUE( " + COL_URL + " ) ON CONFLICT REPLACE);"
            db.execSQL(sql)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }


    init {
        val openHelper = ResultOpenHelper(context)
        database = openHelper.writableDatabase
        this.context = context
    }

    fun close() {
        database.close()
    }

    fun open(context: Context?) {
        val openHelper = ResultOpenHelper(context)
        database = openHelper.writableDatabase
    }

}