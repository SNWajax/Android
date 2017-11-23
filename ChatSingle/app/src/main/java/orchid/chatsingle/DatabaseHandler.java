package orchid.chatsingle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ajays on 5/7/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Messenger";
    private static final String TABLE_MESSAGES = "messages";

 //   private static final Key_ID = "Insert Mobile Number Later";
    private static final String RECEIVER_NAME = "receiver";
    private static final String SENDER_NAME = "sender";
    private static final String MESSAGE = "message";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SAVE_MESSAGE = "CREATE TABLE" + TABLE_MESSAGES+
                "(" + SENDER_NAME + " TEXT" + RECEIVER_NAME +
                " TEXT," + MESSAGE + " TEXT," + ")";
        db.execSQL(SAVE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }
    void addUsers(Chat chat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENDER_NAME, chat.getID());
        values.put(RECEIVER_NAME, chat.getID());
        values.put(MESSAGE, chat.getMessage());

        db.insert(TABLE_MESSAGES, null, values);
        db.close();
    }
}
