package wilfridlaurier.chunxiang.androidassignments;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    protected Button startButton=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        startButton=findViewById(R.id.button);
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
    public void clickHandler(View view) {
        Log.i(ACTIVITY_NAME, "In clickHandler()");
        startActivityForResult(new Intent(StartActivity.this, ListItemsActivity.class), 10);
    }

    public void startChatButtonHandler(View view) {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        startActivityForResult(new Intent(StartActivity.this, ChatWindow.class), 9);
    }

    public void startTestToolbarHandler(View view) {
        Log.i(ACTIVITY_NAME,   "User clicked start test tool bar.");
        startActivity(new Intent( StartActivity.this, TestToolbar.class));
    }

    public void startWeatherForecastHandler(View view) {
        Log.i(ACTIVITY_NAME,   "User clicked weather forecast button.");
        startActivity(new Intent( StartActivity.this, WeatherForecast.class));
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        super.onActivityResult(requestCode, responseCode, data);

        //requestCode 10 means the program returned from ListItemActivity for function clickHandler()
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

            // Step 11 of assignment 2-2: show the message if OK is clicked for checkbox in ListItemActivity
            if(Activity.RESULT_OK == responseCode) {
                String messagePassed = "ListItemsActivity passed:"+data.getStringExtra("Response");
                int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(this, messagePassed, duration); //this is the StartActivity
                toast.show(); //display your message box
            }
        }
    }
}
