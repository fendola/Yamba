package com.example.yamba;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Status;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.ecosystem.ThirdParty;

import com.example.yamba.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StatusActivity extends AppCompatActivity implements OnClickListener, TextWatcher{
    private static final String TAG = "StatusActivity";
    EditText editText;
    Button updateButton;
    Twitter twitter;
    TextView textCount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        editText = (EditText) findViewById(R.id.editText);
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);
        
        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText("140");
        textCount.setTextColor(Color.GREEN);
        
        editText.addTextChangedListener(this);
        
        twitter = new Twitter("learningandroid", "pass2010");
        twitter.setAPIRootUrl("http://learningandroid.status.net/api");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.itemPrefs:
            startActivity(new Intent(this, PrefsActivity.class));
            break;
        default:
            break;
        }
        return true;
    }

    class PostToTwitter extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Toast.makeText(StatusActivity.this, "ready to post status to twitter", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... statuses) {
            // TODO Auto-generated method stub
            try {
                winterwell.jtwitter.Status status = twitter.updateStatus(statuses[0]);
                return status.text;
            } catch (TwitterException e) {
                e.printStackTrace();
                return "Fail to post to twitter!";
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String statuses = editText.getText().toString();
        new PostToTwitter().execute(statuses);
        Log.d(TAG, "onClick");
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        int count = 140 - s.length();
        textCount.setText(Integer.toString(count));
        if (count < 20)
            textCount.setTextColor(Color.YELLOW);
        if (count < 10){
            textCount.setTextColor(Color.RED);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
    }

}
