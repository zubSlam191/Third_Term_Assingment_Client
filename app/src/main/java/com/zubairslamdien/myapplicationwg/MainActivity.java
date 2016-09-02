package com.zubairslamdien.myapplicationwg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button viewButton;
    private Button delButton;
    private Button searchButton;
    private Button createButton;
    private String m_Text = "";
    private ScrollView scrlvw;
    private TextView scrlVwTxt;
    private Button mButton;
    final Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = (Button) findViewById(R.id.create);
        viewButton = (Button) findViewById(R.id.viewAll);
        delButton = (Button) findViewById(R.id.delete);
        searchButton = (Button) findViewById(R.id.search);
        scrlVwTxt = (TextView)  findViewById(R.id.scrlVwTxt);

        createButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateMail.class);
                startActivity(i);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener(){public void onClick(View v) {

            new GetAllEmail().execute();
        }});

        delButton.setOnClickListener(new View.OnClickListener(){public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), DeleteMail.class);
            startActivity(i);
        }});

        searchButton.setOnClickListener(new View.OnClickListener(){public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), SearchMail.class);
            startActivity(i);
        }});

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class GetAllEmail extends AsyncTask<Void, Void, Email[]> {
        @Override
        protected Email[] doInBackground(Void... params) {
            try {
                final String url = "http://148.100.4.101:8080/emails/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Email[] emails = restTemplate.getForObject(url, Email[].class);
                return emails;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Email[] emails) {
            scrlVwTxt.setText("");
            for(Email e: emails)
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
                try {
                    Date date = dateFormat.parse(e.getDate());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                scrlVwTxt.append(e.getId() + " " + e.getAddress() + " " + e.getDescription() + " " + e.getDate() + "\n");
            }
        }
    }
}
