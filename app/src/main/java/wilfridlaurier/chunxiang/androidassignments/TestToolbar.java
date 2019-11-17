package wilfridlaurier.chunxiang.androidassignments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    String savedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This activity is used for test toobar.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        savedMessage = null;
    }

    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch (id) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                if (null == savedMessage) {
                    Snackbar.make(findViewById(R.id.action_one), "You selected item 1", Snackbar.LENGTH_LONG)
                            .show();
                }
                else
                {
                    //After getting the inputed message by action_three, we display it in action_one
                    Snackbar.make(findViewById(R.id.action_one), savedMessage, Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.pick_color);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            finish();
                        }
                    });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");

                //InputDialog.Builder builder1 = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();

               InputDialog newDialog = new InputDialog(TestToolbar.this, new InputDialog.OnEditInputFinishedListener(){
                            // We needs to override one function to get the inputed String.
                            @Override
                            public void editInputFinished(String inputedMessage) {
                                savedMessage = inputedMessage;
                            }
                        });

                //This line is added to avoid the input method's stuck
                newDialog.setView(inflater.inflate(R.layout.dialog_message, null));
                //newDialog.setView(new EditText(TestToolbar.this));
                newDialog.show();

                break;

            case R.id.action_Four:
                Log.d("Toolbar", "Option 4 selected");

                CharSequence text = "Version 1.0, by Chunxiang Zhang";// "Switch is Off"
                int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(TestToolbar.this , text, duration); //this is the ListActivity
                toast.show(); //display your message box
                break;
            default:
                break;
        }
        return true;
    }
}
