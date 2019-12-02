package wilfridlaurier.chunxiang.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    static final String TABLE_NAME = "Messages";
    static final String KEY_ID = "id";
    static final String KEY_MESSAGE = "MESSAGE";

    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists  "
            + TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        Log.i("ChatDatabaseHelper", "run constructor function ChatDatabaseHelper()");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("ChatDatabaseHelper", "Calling onCreate");

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        // Let's write the function, in case we wrong small version number in the future
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
