package com.zubairslamdien.myapplicationwg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Zubair Slamdien on 2016/09/02.
 */

public class SearchMail extends AppCompatActivity {

    private Button search;
    private Button cancel;
    private EditText text;
    private TextView result;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        search = (Button) findViewById(R.id.button3);
        cancel = (Button) findViewById(R.id.button4);
        text = (EditText) findViewById(R.id.editText3);
        result = (TextView) findViewById(R.id.resultText);

        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String idText = text.getText().toString();
                new SearchEmail(idText).execute();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private class SearchEmail extends AsyncTask<Void, Void, Email[]> {

        private String idT;


        public SearchEmail(String a){
            idT = a;
        }

        @Override
        protected Email[] doInBackground(Void... params) {
            try {
                final String url = "http://148.100.4.101:8080/emails/search/date/"+idT;
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
            for(Email e: emails)
            {
                Format formatter = new SimpleDateFormat("MM-dd-yyyy");
                String s = formatter.format(e.getDate());

                result.append(e.getId() + "address: " + e.getAddress() + " description: " + e.getDescription() + " date: " + s + "\n");
            }
        }
    }
}
