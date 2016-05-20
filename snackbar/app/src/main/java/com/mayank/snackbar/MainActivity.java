package com.mayank.snackbar;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.newrelic.agent.android.NewRelic;

public class MainActivity extends AppCompatActivity {

    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = (Button) findViewById( R.id.btnClick);
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(coordinatorLayoutView, R.string.app_name, Snackbar.LENGTH_LONG)
                        .setAction("Done", clickListener)
                        .show();
            }
        });



        NewRelic.withApplicationToken(
                "AAeb1cc2496bc23ece8b15bd7446b9cd617ff0c806"
        ).start(this.getApplication());

    }
    final View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
