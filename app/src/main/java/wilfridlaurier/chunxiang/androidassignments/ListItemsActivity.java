package wilfridlaurier.chunxiang.androidassignments;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected CheckBox checkBox_ListItem=null;
    protected Switch switchBtn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        checkBox_ListItem=findViewById(R.id.checkBox);
        switchBtn=findViewById(R.id.switch1);

        checkBox_ListItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );
                                CharSequence text = getString(R.string.response_info);// return information
                                resultIntent.putExtra("Response", text);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                //do nothing
                            }
                        })
                        .show();
            }
        });
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // Auto-generated method stub
                if(isChecked){
                    CharSequence text = getString(R.string.switch_on);// "Switch is On"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                    Toast toast = Toast.makeText(ListItemsActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message box
                }else{
                    CharSequence text = getString(R.string.switch_off);// "Switch is Off"
                    int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                    Toast toast = Toast.makeText(ListItemsActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message box
                }
            }
        });
    }


    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    public void myClick(View view) {
        // Check if there is a camera.

        Context context = this;
        PackageManager packageManager = context.getPackageManager();

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){

            Log.i(ACTIVITY_NAME, "This device has a camera.");
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        else {
            // If no camera is detected, let's show toast message
            CharSequence text = getString(R.string.no_camera);// "no camera"
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if(null != extras)
            {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ((ImageView) findViewById(R.id.imageButton)).setImageBitmap(imageBitmap);
            }
        }
    }

}
