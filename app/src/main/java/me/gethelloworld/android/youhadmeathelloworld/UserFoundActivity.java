package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import me.gethelloworld.android.youhadmeathelloworld.R;

public class UserFoundActivity extends Activity {

    TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        setContentView(R.layout.activity_user_found);
        doStuff(userId);
    }

    public void doStuff(String userId){
        tv  = (TextView) findViewById(R.id.userfound_textview);
        tv2 = (TextView) findViewById(R.id.user_name);
        tv.setText("User Found");
        tv2.setText(userId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_found, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
