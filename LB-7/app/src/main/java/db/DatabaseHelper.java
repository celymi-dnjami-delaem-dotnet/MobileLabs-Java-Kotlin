package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DatabaseVersion = 2;
    public static final String DatabaseName = "contactsDb";
    public static final String TableName = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_SELECTED = "selected";

    private static final String sqlDbCreation = "create table " + TableName + " (" + KEY_ID + " integer primary key," + KEY_NAME + " text, " + KEY_PHONE + " text," + KEY_SELECTED +  " integer" + ")";
    private static final String sqlDbRemove = "drop table if exists " + TableName;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlDbCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(sqlDbRemove);
        onCreate(sqLiteDatabase);
    }
}
