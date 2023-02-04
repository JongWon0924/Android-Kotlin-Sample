package com.example.sqlitesample

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import com.example.sqlitesample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dbHelper by lazy { FeedReaderContract.FeedReaderDbHelper(this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnInsert.setOnClickListener {
            val db = dbHelper.writableDatabase
            
            val title = binding.editTitle.text.toString()
            val subTitle = binding.editSubTitle.text.toString()
            
            if (title.isEmpty() || subTitle.isEmpty()) {
                Snackbar.make(it, "All fields are required.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subTitle)
            }
            
            val rowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
            if (rowId == -1L) {
                Snackbar.make(it, "Something went wrong...", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            binding.editTitle.setText("")
            binding.editSubTitle.setText("")
            Snackbar.make(it, "Successfully inserted.", Snackbar.LENGTH_SHORT).show()
        }
        
        binding.btnList.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}

object FeedReaderContract {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
    
    private const val SQL_CREATE_ENTRIES = "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedEntry.COLUMN_NAME_TITLE} TEXT," +
            "${FeedEntry.COLUMN_NAME_SUBTITLE} TEXT)"
    
    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
    
    class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }
        
        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "FeedReader.db"
        }
    }
}
