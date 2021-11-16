package pl.wikdev.expirydatetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ExpiryDateTrackerDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "product_title";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_EXPIRY_DATE + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addProduct(String title, String expiry_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); // DATA FROM APP TO DATABASE

        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_EXPIRY_DATE,expiry_date);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            {
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteProduct(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
