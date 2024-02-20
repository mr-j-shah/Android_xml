package com.crestinfosystems_jinay.happyplaces.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.crestinfosystems_jinay.happyplaces.model.HappyPlace
import java.lang.String
import kotlin.Int
import kotlin.Long
import kotlin.arrayOf

private val DATABASE_VERSION = 1
private val DATABASE_NAME = "contactsManager"

class HappyPlaceDatabase(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private val TABLE_NAME = "happy_places"
    private val KEY_ID = "id"
    private val KEY_TITLE = "name"
    private val KEY_DESCRIPTION = "description"
    private val KEY_LOCATION = "location"
    private val KEY_DATE = "date"
    private val KEY_IMAGE_URL = "imageURL"


    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_IMAGE_URL + " TEXT" + ")")
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        // Create tables again
        onCreate(db)
    }

    // code to add the new contact
    fun addPlace(place: HappyPlace): Long {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val values = ContentValues()
        values.put(KEY_TITLE, place.title) // Contact Name
        values.put(KEY_DESCRIPTION, place.description) // Contact Phone
        values.put(KEY_LOCATION, place.location) // Contact Name
        values.put(KEY_DATE, place.date) //
        values.put(KEY_IMAGE_URL, place.imageURL)
        // Inserting Row
        val result = db.insert(TABLE_NAME, null, values)
        //2nd argument is String containing nullColumnHack
        db.close()
        return result// Closing database connection
    }

    // code to get the single contact
    fun getPlace(id: Int): HappyPlace? {
        val db: SQLiteDatabase = this.getReadableDatabase()
        val cursor = db.query(
            TABLE_NAME, arrayOf(
                KEY_ID,
                KEY_TITLE, KEY_DESCRIPTION, KEY_LOCATION, KEY_DATE, KEY_IMAGE_URL
            ),
            "$KEY_ID=?", arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        // return contact
        return HappyPlace(
            id = cursor!!.getString(0).toInt(),
            title = cursor.getString(1),
            description = cursor.getString(2),
            location = cursor.getString(3),
            date = cursor.getString(4),
            imageURL = cursor.getString(5)
        )
    }

    // code to get all contacts in a list view
    fun getAllPlaces(): List<HappyPlace>? {
        val contactList: MutableList<HappyPlace> = ArrayList<HappyPlace>()
        // Select All Query
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db: SQLiteDatabase = this.getWritableDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val place = HappyPlace(
                    id = cursor.getString(0).toInt(),
                    title = cursor.getString(1),
                    description = cursor.getString(2),
                    location = cursor.getString(3),
                    date = cursor.getString(4),
                    imageURL = cursor.getString(5)
                )
                contactList.add(place)
            } while (cursor.moveToNext())
        }

        // return contact list
        return contactList
    }

    // code to update the single contact
    fun updatePlace(place: HappyPlace): Int {
        val db: SQLiteDatabase = this.getWritableDatabase()
        val values = ContentValues()
        values.put(KEY_TITLE, place.title) // Contact Name
        values.put(KEY_DESCRIPTION, place.description) // Contact Phone
        values.put(KEY_LOCATION, place.location) // Contact Name
        values.put(KEY_DATE, place.date) //
        values.put(KEY_IMAGE_URL, place.imageURL)

        // updating row
        return db.update(
            TABLE_NAME,
            values,
            "$KEY_ID = ?",
            arrayOf(String.valueOf(place.id))
        )
    }

    // Deleting single contact
    fun deletePlace(place: HappyPlace) {
        val db: SQLiteDatabase = this.getWritableDatabase()
        db.delete(
            TABLE_NAME, "$KEY_ID = ?", arrayOf(
                String.valueOf(place.id)
            )
        )
        db.close()
    }

    // Getting contacts Count
    fun getPlacesCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NAME"
        val db: SQLiteDatabase = this.getReadableDatabase()
        val cursor = db.rawQuery(countQuery, null)
        cursor.close()

        // return count
        return cursor.count
    }
}