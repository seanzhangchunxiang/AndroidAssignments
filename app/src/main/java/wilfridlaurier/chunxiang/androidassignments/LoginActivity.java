package wilfridlaurier.chunxiang.androidassignments;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_login);

        String preference_login_name = getString(R.string.login_name);
        SharedPreferences mPrefs = getSharedPreferences(preference_login_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.apply();

        // Load the user email
        String new_login_name = mPrefs.getString("DefaultEmail", "email@domain.com");
        String password_key = getString(R.string.password);
        String new_password = mPrefs.getString(password_key, "");
        ((EditText) findViewById(R.id.editText)).setText(new_login_name);
        ((EditText) findViewById(R.id.editText2)).setText(new_password);
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

    public void myClick(View view)
    {
        // Save the data for future use
        String preference_login_name = getString(R.string.login_name);
        SharedPreferences mPrefs = getSharedPreferences(preference_login_name, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();

        // Load the user email
        String login_name_key = getString(R.string.login_name);
        String password_key = getString(R.string.password);
        String new_password = mPrefs.getString(password_key, "");

        // Save email information
        String new_login_name_entered =  ((EditText) findViewById(R.id.editText))
                .getText().toString();
        String new_password_entered = ((EditText) findViewById(R.id.editText2))
                .getText().toString();
        mEditor.putString(login_name_key, new_login_name_entered);
        mEditor.putString(password_key, new_password_entered);

        // Commit all the changes into the shared preference
        mEditor.apply();

        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
