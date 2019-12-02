package wilfridlaurier.chunxiang.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {
    private OnDeleteButtonListener callback;
    private int position = 0;
    private TextView messageInfo;
    private TextView messageId;
    private Button deleteBtn;
    private String msgPassedDetail;
    private String msgPassedID;
    ArrayAdapter<String> itemsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
                msgPassedDetail = getArguments().getString("messageDetail");
                msgPassedID = getArguments().getString("messageID");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.msgfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        messageInfo = getView().findViewById(R.id.messageDetail);
        messageId = getView().findViewById(R.id.messageId);
        deleteBtn = getView().findViewById(R.id.deleteMessage);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete
                if (null != msgPassedID) {
                    Log.i("Fragment onViewCreated", "delete button clicked.");
                    //OnDeleteButtonListener();
                    callback.OnDeleteItemSelectedListener(position);

                    //Clear the output
                    msgPassedDetail = getString(R.string.messageDetail);
                    msgPassedID = null;
                    updateView();
                }
                else {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), "No message is selected.", duration); //this is the ListActivity
                    toast.show(); //display your message box
                }
            }
        });
        updateView();
    }

    // Activity is calling this to update view on Fragment
    private void updateView(){
        if (null == msgPassedDetail)
        {
            messageInfo.setText(getString(R.string.messageDetail));
        }
        else {
            messageInfo.setText(msgPassedDetail);
        }

        if (null == msgPassedID) {
            messageId.setText(getString(R.string.messageID));
        }
        else {
            messageId.setText(getString(R.string.messageID) + msgPassedID);
        }
    }

    public void setOnDeleteButtonListoner(OnDeleteButtonListener callback) {
        this.callback = callback;
    }


    public interface OnDeleteButtonListener {
        void OnDeleteItemSelectedListener(int position);
    }
}
