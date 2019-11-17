package wilfridlaurier.chunxiang.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {

    // Add class variables for the ListView, EditText, and Button
    protected EditText textInput=null;
    protected ListView listView=null;
    protected Button sendBtn=null;
    protected ArrayList<String> sentMmessage = new ArrayList<>();
    protected SQLiteDatabase database;
    protected ChatDatabaseHelper dbHelper;
    public static final String ACTIVITY_NAME = "ChatWindow";

    private class ChatAdapter extends ArrayAdapter<String> {
        private ArrayList<String> list;

        ChatAdapter(Context ctx, ArrayList<String> messages) {
            super(ctx, 0);
//  super(context, android.R.layout.simple_list_item_1,messages);
            this.list = messages;
        }

        public int getCount(){
            return list.size();
        }
        public String getItem(int position) {
            if (position < getCount())
            {
                return list.get(position);
            }
            else {
                return null;
            }
        }

        public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View result;
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            //In order to remove warnings, Chunxiang Zhang use the original function inflate()
            if(position%2 == 0) {
                //result = inflater.inflate(R.layout.chat_row_incoming, null);
                result = inflater.inflate(R.layout.chat_row_incoming, parent, false);
            }
            else {
                //result = inflater.inflate(R.layout.chat_row_outgoing, null);
                result = inflater.inflate(R.layout.chat_row_outgoing, parent, false);
            }

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    public void openWritableDB() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        textInput = findViewById(R.id.simpleEditText);
        listView = findViewById(R.id.chatView);
        sendBtn = findViewById(R.id.button4);

        //in this case, “this” is the ChatWindow, which is-A Context object
        ChatAdapter messageAdapter = new ChatAdapter(this, sentMmessage);
        listView.setAdapter(messageAdapter);

        // 1. call to create database. Here onCreate() method is called implicitly,
        //  otherwise the onOpen() method gets called.
        dbHelper = new ChatDatabaseHelper(this);

        // 2. open Database for reading and writing
        //database = dbHelper.getWritableDatabase();
        openWritableDB();

        Cursor cursor = database.rawQuery("SELECT * from " + ChatDatabaseHelper.TABLE_NAME, null);

        //Let's move the cursor to the first one, and go through all the messages
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            String textInEditor = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + textInEditor );
            sentMmessage.add(textInEditor);
            cursor.moveToNext();
        }

        // The following section prints the cursor's information.
        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );
        int columnIndex = 0;
        while (columnIndex < cursor.getColumnCount()) {
            Log.i(ACTIVITY_NAME, "Cursor's column " +columnIndex+ " name is '" + cursor.getColumnName(columnIndex) + "'");
            columnIndex = columnIndex + 1;
        }

        //Let's close it for safety.
        cursor.close();

        ChatAdapter mAdapter = (ChatAdapter)listView.getAdapter();
        mAdapter.notifyDataSetChanged();
    }


    public void sendBtnClick(View view)
    {
        // Get the message and save it to array
        String textInEditor = textInput.getText().toString();

        // If the length is 0, don't send null message
        if (textInEditor.length() != 0) {
            sentMmessage.add(textInEditor);

            ChatAdapter messageAdapter = (ChatAdapter)listView.getAdapter();
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
            textInput.setText("");

            // Put the information in the database.
            ContentValues cValues = new ContentValues();
            cValues.put(ChatDatabaseHelper.KEY_MESSAGE, textInEditor);
            database.insert(ChatDatabaseHelper.TABLE_NAME, null, cValues);
        }
    }

    public void onDestroy(View view) {
        database.close();
        super.onDestroy();
    }
}


