package wilfridlaurier.chunxiang.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {
    TextView messageDetail;
    TextView msgID;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Intent intentThis = getIntent();
        String message = intentThis.getStringExtra("message");
        String id = intentThis.getStringExtra("ID");
        String extraPosition = intentThis.getStringExtra("position");

        if (null != extraPosition) {
            position = Integer.parseInt(extraPosition);
        }
        else {
            position = 0;
        }

        messageDetail = findViewById(R.id.textView4);
        msgID = findViewById(R.id.textView5);
        messageDetail.setText(message);
        msgID.setText(id);

        Button btnDelete = findViewById(R.id.button6);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("position", Integer.toString(position));
                intent.putExtra("ID", msgID.getText());
                MessageDetails.this.setResult(RESULT_OK, intent);
                MessageDetails.this.finish();
            }
        });
    }
}
