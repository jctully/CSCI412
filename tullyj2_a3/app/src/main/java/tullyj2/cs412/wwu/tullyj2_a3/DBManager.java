package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by tullyj2 on 6/5/19.
 */

public class DBManager {
    private String TABLE_DRINKS = DatabaseHelper.TABLE_DRINKS;

    private String _ID = DatabaseHelper._ID;
    private String KEY_DRINK_NAME = DatabaseHelper.KEY_DRINK_NAME;
    private String KEY_DRINK_DESC = DatabaseHelper.KEY_DRINK_DESC;
    private String KEY_DRINK_SCORE = DatabaseHelper.KEY_DRINK_SCORE;


    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Drink drink) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(KEY_DRINK_NAME, drink.name);
        contentValue.put(KEY_DRINK_DESC, drink.desc);
        contentValue.put(KEY_DRINK_SCORE, drink.score);

        database.insert(DatabaseHelper.TABLE_DRINKS, null, contentValue);
    }

    public Cursor fetch() {
        System.out.println(KEY_DRINK_NAME + "IN FETCH");
        String[] columns = new String[] { _ID, KEY_DRINK_NAME, KEY_DRINK_DESC, KEY_DRINK_SCORE };
        Cursor cursor = database.query(TABLE_DRINKS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, Drink drink) {
        ContentValues values = new ContentValues();
        values.put(KEY_DRINK_NAME, drink.name);
        values.put(KEY_DRINK_DESC, drink.desc);
        values.put(KEY_DRINK_SCORE, drink.score);

        int i = database.update(DatabaseHelper.TABLE_DRINKS, values, _ID + " = " + _id, null);
        return i;
    }

    public void deleteDrink(long _id) {
        database.delete(DatabaseHelper.TABLE_DRINKS, DatabaseHelper._ID + "=" + _id, null);
    }

}
