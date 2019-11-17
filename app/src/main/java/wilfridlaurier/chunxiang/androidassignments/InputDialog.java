package wilfridlaurier.chunxiang.androidassignments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

import wilfridlaurier.chunxiang.androidassignments.R;

public class InputDialog extends AlertDialog implements OnClickListener{
    private EditText etInputMessage;  //EditText
    private Button btnConfrim, btnCancel;  //Confirm and Cancel buttons
    private OnEditInputFinishedListener insideListener; //interface for passing message
    private View dialogView;

    // The following interface passes the inputed message to activities
    // This interface needs to be overrided in activities
    public interface OnEditInputFinishedListener{
        void editInputFinished(String inputMessage);
    }

    InputDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.insideListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Save widgets information
        etInputMessage = findViewById(R.id.newinputmessage);
        btnConfrim = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);

        btnConfrim.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            //OK button was clicked
            if (insideListener != null) {
                String inputedMessage = etInputMessage.getText().toString();
                insideListener.editInputFinished(inputedMessage);
            }
            dismiss();
        }else {
            //Cancel button was clicked
            dismiss();
        }
    }

}
