package wilfridlaurier.chunxiang.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    // Add class variables for the ListView, EditText, and Button
    protected EditText textInput=null;
    protected ListView listView=null;
    protected Button sendBtn=null;
    protected ArrayList<String> sentMmessage = new ArrayList<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        textInput = findViewById(R.id.simpleEditText);
        listView = findViewById(R.id.chatView);
        sendBtn = findViewById(R.id.button4);

        //in this case, “this” is the ChatWindow, which is-A Context object
        ChatAdapter messageAdapter = new ChatAdapter(this, sentMmessage);
        listView.setAdapter (messageAdapter );
    }

    public void sendBtnClick(View view)
    {
        // Get the message and save it to array
        String textInEditor = textInput.getText().toString();

        // If the length is 0, don't send null message
        if (textInEditor.length() != 0) {
            sentMmessage.add(textInput.getText().toString());

            ChatAdapter messageAdapter = (ChatAdapter)listView.getAdapter();
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
            textInput.setText("");
        }
    }
}


