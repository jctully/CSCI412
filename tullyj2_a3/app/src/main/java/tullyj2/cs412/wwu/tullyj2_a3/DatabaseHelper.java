package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tullyj2 on 6/5/19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    // Database Info
    private static final String DATABASE_NAME = "drinksDatabase";

    private static final int DATABASE_VERSION = 2;

    // Table Name
    public static final String TABLE_DRINKS = "drinks";

    // Table Columns
    public static final String _ID = "_id";
    public static final String KEY_DRINK_NAME = "name";
    public static final String KEY_DRINK_DESC = "description";
    public static final String KEY_DRINK_SCORE = "score";

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_DRINKS +
            "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DRINK_NAME + " TEXT, " +
            KEY_DRINK_DESC + " TEXT, " +
            KEY_DRINK_SCORE + " INTEGER" +
            ")";

    private static final String s = "CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY);";


    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASEHELPER", "CREATED DATABASE");

        db.execSQL(CREATE_TABLE);
        System.out.println(CREATE_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINKS);
            onCreate(db);
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    public void addPost(Drink drink) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(KEY_DRINK_NAME, drink.name);
            values.put(KEY_DRINK_DESC, drink.desc);
            values.put(KEY_DRINK_SCORE, drink.score);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_DRINKS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ADD DRINK ERROR", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();

        // SELECT * FROM DRINKS
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_DRINKS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                while(cursor.moveToNext()) {
                    Drink newDrink = new Drink();
                    newDrink.name = cursor.getString(cursor.getColumnIndex(KEY_DRINK_NAME));
                    newDrink.desc = cursor.getString(cursor.getColumnIndex(KEY_DRINK_DESC));
                    newDrink.score = cursor.getInt(cursor.getColumnIndex(KEY_DRINK_SCORE));

                }
            }
        } catch (Exception e) {
            Log.d("DB HELPER QUERY", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return drinks;
    }

    public int updateDrink(Drink drink) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRINK_DESC, drink.desc);
        values.put(KEY_DRINK_SCORE, drink.score);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_DRINKS, values, KEY_DRINK_NAME + " = ?",
                new String[] { String.valueOf(drink.name) });
    }

    public void deleteDrink(Drink drink) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_DRINKS, KEY_DRINK_NAME + " = ?", new String[] { String.valueOf(drink.name) });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DB DELETE ENTRY", "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
